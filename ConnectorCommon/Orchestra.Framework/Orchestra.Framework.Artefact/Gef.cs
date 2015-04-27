// ----------------------------------------------------------------------------------------------------
// File Name: Gef.cs
// Project: Orchestra.Framework.Artefact
// Copyright (c) Thales, 2010 - 2014. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Artefact
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Runtime.InteropServices;
    using System.Xml;
    using Orchestra.Framework.Core;
    using Orchestra.Framework.Utilities;
    using Orchestra.Framework.VariableManager;
    using Environment = System.Environment;

    ///<summary>
    /// This class implements the Gef export
    /// <para>The xml file generated is compliant with Gef.xsd</para>
    ///</summary>
    [ComVisible(true)]
    [Guid("352E5881-CF23-4a63-B428-AAB54894097E")]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId("Orchestra.Gef")]
    public class Gef : IExport
    {
        private const string GefDefinition = "http://www.thalesgroup.com/orchestra/framework/4_0_24/Gef";
        private const string InLink = "in";
        private const string MimeTypesFileName = "MimeTypes.properties";
        private const string OutLink = "out";
        private static readonly List<string> _LdataType = new List<string> { "rtf", "string", "html" };
        internal static string ErrorMessage;
        internal static short ErrorMessageCode;
        private readonly Dictionary<string, string> _mimeTypes;
        private bool _descriptionTagOpen;
        private bool _externalLinksTagOpen;
        private string _extraFileDirectory;
        private bool _internalLinksTagOpen;
        private string _outputDirectory;
        private bool _propertiesTagOpen;
        private XmlWriter _xmlFile;

        ///<summary>
        /// Constructor
        ///</summary>
        public Gef()
        {
            try
            {
                string orchestraServerHome = string.Format(Client.OrchestraProductHomeFormat, "OrchestraFramework");
                string mimeTypesFile = (new Client().GetSubstitutedValue(orchestraServerHome))
                                       + Path.DirectorySeparatorChar + "lib" + Path.DirectorySeparatorChar
                                       + MimeTypesFileName;
                this._mimeTypes = new Dictionary<string, string>();
                foreach (string row in File.ReadAllLines(mimeTypesFile))
                {
                    this._mimeTypes.Add(row.Split('=')[0], row.Split('=')[1]);
                }
            }
            catch (FileNotFoundException)
            {
                EventLogOrchestra.WriteError(
                    60020, "File:" + MimeTypesFileName + " not found!", "Orchestra.Framework.Artefact.Gef");
            }
            catch (PathTooLongException)
            {
                EventLogOrchestra.WriteError(
                    60020, "Path:" + MimeTypesFileName + " too long!", "Orchestra.Framework.Artefact.Gef");
            }
            catch (Exception ex)
            {
                EventLogOrchestra.WriteError(
                    60020, ex.Message, "Orchestra.Framework.Artefact.Gef" + Environment.NewLine + ex.Source);
            }
        }

        #region IExport Members
        /// <summary>
        /// This property creates and returns the path of the directory for the extra files.
        /// </summary>
        /// <value>The extra file directory.</value>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// using Orchestra.Framework.Artefact;
        /// 
        /// Gef oGef = new Gef();
        /// string extraFilesDirectory;
        /// oGef.ExtraFileDirectory = extraFilesDirectory; // the directory is created (the name of the directory can be changed)
        /// extraFilesDirectory = oGef.ExtraFileDirectory; // returns the name of directory created
        /// </code>
        /// </example>
        public string ExtraFileDirectory
        {
            get
            {
                return this._extraFileDirectory;
            }
            set
            {
                this._extraFileDirectory = MakeValidFileName(value);
                string path = this._outputDirectory + Path.DirectorySeparatorChar + this._extraFileDirectory;
                try
                {
                    Directory.CreateDirectory(path);
                }
                catch (PathTooLongException) //The specified path, file name, or both exceed the system-defined maximum length
                {
                    EventLogOrchestra.WriteError(
                        60000,
                        "During '" + path + "' directory creation, the path exceed the system-defined maximum length",
                        "Orchestra.Framework ExtraFileDirectory property set");
                }
                catch (DirectoryNotFoundException) //The specified path is invalid (for example, it is on an unmapped drive)
                {
                    EventLogOrchestra.WriteError(
                        60000,
                        "During '" + path
                        + "' directory creation, the path is invalid (for example, it is on an unmapped drive)",
                        "Orchestra.Framework ExtraFileDirectory property set");
                }
                catch (IOException) //The directory specified by path is read-only.
                {
                    EventLogOrchestra.WriteError(
                        60000,
                        "During '" + path + "' directory creation, the path is in read-only mode",
                        "Orchestra.Framework ExtraFileDirectory property set");
                }
                catch (Exception ex)
                {
                    EventLogOrchestra.WriteError(
                        60000,
                        "During '" + path + "' directory creation, the following error occurs:" + Environment.NewLine
                        + ex.Message,
                        "Orchestra.Framework ExtraFileDirectory property set");
                }
            }
        }

        /// <summary>
        /// Initialize the xml file.
        /// </summary>
        /// <param name="outputFileName">full name of the xml file to create.
        /// <para>The file name must have .gef extension.</para>
        /// </param>
        /// <returns>Directory path of the outputFileName</returns>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// Orchestra.Framework.Artefact.Gef oGef = new Orchestra.Framework.Artefact.Gef();
        /// string outputDirectory;
        /// 
        /// outputDirectory = oGef.InitOutputFile(@"D:\Shareddir\temp.gef");
        /// </code>
        /// </example>
        public string InitOutputFile(string outputFileName)
        {
            this._outputDirectory = Path.GetDirectoryName(outputFileName);
            XmlWriterSettings settings = new XmlWriterSettings
                {
                    CheckCharacters = false,
                    Indent = true,
                    Encoding = XmlEncoding.Class
                };
            this._xmlFile = XmlWriter.Create(outputFileName, settings);

            this._xmlFile.WriteStartDocument();
            this._xmlFile.WriteStartElement("gef", "GEF", GefDefinition);
            this._xmlFile.WriteStartElement("GENERIC_EXPORT_FORMAT");
            return this._outputDirectory;
        }

        /// <summary>
        /// Start export of an artefact.
        /// </summary>
        /// <param name="uri">papeete Uri of the artefact (see <see cref="IUri" /> for more information)</param>
        /// <param name="name">artefact name</param>
        /// <param name="type">artefact type</param>
        /// <param name="label">artefact label</param>
        /// <param name="hash">hash of the artefact</param>
        /// <param name="filePath">relative file path of the artefact container (See <see cref="Version" /> for more information)</param>
        /// <param name="version">version of the artefact container (See <see cref="Version" /> for more information)</param>
        /// <param name="fullLabel">if true then the object label is "(projectName objectType) <paramref name="label" />" else the object label is "<paramref name="label" />"</param>
        /// <exception cref="System.ApplicationException"></exception>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// Orchestra.Framework.Artefact.Gef oGef = new Orchestra.Framework.Artefact.Gef();
        /// string outputDirectory = oGef.InitOutputFile(@"D:\Shareddir\temp.gef");
        /// string logicalName;
        /// string queryId;
        /// string queryName;
        /// 
        /// Orchestra.Framework.Core.Artefact artefact = new Orchestra.Framework.Core.Artefact
        ///                                      {
        ///                                          RootType = "Sql",
        ///                                          RootName = logicalName,
        ///                                          Type = "Query"
        ///                                      };
        /// artefact.Id = queryId;
        /// oGef.WriteStartArtefact(artefact.Uri, queryName, QueryType, "Requete:" + queryName, "", "", "", false);
        /// oGef.WriteEndArtefact();
        /// oGef.CloseOutputFile();
        /// </code>
        /// </example>
        public void WriteStartArtefact(
            string uri,
            string name,
            string type,
            string label,
            string hash,
            string filePath,
            string version,
            bool fullLabel)
        {
            if (this._descriptionTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._descriptionTagOpen = false;
            }
            if (this._propertiesTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._propertiesTagOpen = false;
            }
            if (this._internalLinksTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._internalLinksTagOpen = false;
            }
            if (this._externalLinksTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._externalLinksTagOpen = false;
            }
            Artefact artefact = new Artefact { Uri = uri };
            ErrorMessage = string.Empty;
            ErrorMessageCode = 0;
            string rootName = artefact.RootName;
            if (string.IsNullOrEmpty(type))
            {
                label = rootName;
                type = string.Empty;
            }
            else if (fullLabel)
            {
                label = "(" + rootName + " " + type + ") " + label;
            }
            this._xmlFile.WriteStartElement("ELEMENT");
            this._xmlFile.WriteAttributeString("type", type);
            this._xmlFile.WriteAttributeString("label", XmlEncoding.CheckString(label, false));
            if (!string.IsNullOrEmpty(name))
            {
                this._xmlFile.WriteAttributeString("fullName", XmlEncoding.CheckString(name, false));
            }
            if (!string.IsNullOrEmpty(hash))
            {
                this._xmlFile.WriteAttributeString("hash", hash);
            }
            if (!string.IsNullOrEmpty(uri))
            {
                this._xmlFile.WriteAttributeString("uri", uri);
            }
            if (!string.IsNullOrEmpty(version) && !string.IsNullOrEmpty(filePath))
            {
                this._xmlFile.WriteStartElement("VERSION");
                this._xmlFile.WriteAttributeString("filePath", filePath);
                this._xmlFile.WriteAttributeString("version", version);
                this._xmlFile.WriteEndElement();
            }
            if (ErrorMessageCode > 0)
            {
                throw new ApplicationException(ErrorMessage);
            }
        }

        /// <summary>
        /// End export of an artefact.
        /// </summary>
        public void WriteEndArtefact()
        {
            if (this._descriptionTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._descriptionTagOpen = false;
            }
            if (this._propertiesTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._propertiesTagOpen = false;
            }
            if (this._internalLinksTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._internalLinksTagOpen = false;
            }
            if (this._externalLinksTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._externalLinksTagOpen = false;
            }
            this._xmlFile.WriteEndElement();
        }

        ///<summary>
        /// Orchestra Internal Use.
        /// reserved for the Surrogate Module Migration To LM
        ///</summary>
        ///<param name="uriTarget"></param>
        ///<param name="typeLink"></param>
        /// <exception cref="System.ApplicationException"></exception>
        public void WriteReferenceForSurrogateModuleMigrationToLM(string uriTarget, string typeLink)
        {
            if (this._descriptionTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._descriptionTagOpen = false;
            }
            if (this._propertiesTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._propertiesTagOpen = false;
            }
            if (this._internalLinksTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._internalLinksTagOpen = false;
            }
            ErrorMessage = string.Empty;
            ErrorMessageCode = 0;
            if (string.IsNullOrEmpty(uriTarget))
            {
                return;
            }
            if (!this._externalLinksTagOpen)
            {
                this._externalLinksTagOpen = true;
                this._xmlFile.WriteStartElement("LINKS_TO_ARTIFACTS");
            }
            this._xmlFile.WriteStartElement("ARTIFACT_REFERENCE");
            this._xmlFile.WriteAttributeString("uri", uriTarget);
            if (!string.IsNullOrEmpty(typeLink))
            {
                this._xmlFile.WriteAttributeString("linkType", typeLink);
            }
            this._xmlFile.WriteAttributeString("linkDirection", OutLink);
            this._xmlFile.WriteEndElement();
            if (ErrorMessageCode > 0)
            {
                throw new ApplicationException(ErrorMessage);
            }
        }

        /// <summary>
        /// Write a property of an artefact.
        /// </summary>
        /// <param name="name">name of the property</param>
        /// <param name="type">type of the property</param>
        /// <param name="content">value of the property</param>
        /// <param name="nature">not used</param>
        /// <exception cref="System.ApplicationException"></exception>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// Orchestra.Framework.Artefact.Gef oGef;
        /// oGef = new Orchestra.Framework.Artefact.Gef();
        /// 
        /// // your export code
        /// 
        /// oGef.WriteProperty("Stereotype", "string", "Interface", "");
        /// </code>
        /// <code>
        /// Visual Basic
        /// ----------------------------------------------
        /// Dim oGef As New Orchestra.Gef
        /// 
        /// ' your export code
        /// 
        /// oGef.WriteProperty "Stereotype", "string", "Interface", ""
        /// </code>
        /// </example>
        public void WriteProperty(string name, string type, string content, string nature)
        {
            if (this._descriptionTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._descriptionTagOpen = false;
            }
            if (this._internalLinksTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._internalLinksTagOpen = false;
            }
            if (this._externalLinksTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._externalLinksTagOpen = false;
            }
            ErrorMessage = string.Empty;
            ErrorMessageCode = 0;
            if (string.IsNullOrEmpty(name))
            {
                return;
            }
            if (!this._propertiesTagOpen)
            {
                this._propertiesTagOpen = true;
                this._xmlFile.WriteStartElement("PROPERTIES");
            }
            this._xmlFile.WriteStartElement("PROPERTY");
            this._xmlFile.WriteAttributeString("name", name);
            if (!string.IsNullOrEmpty(type))
            {
                this._xmlFile.WriteAttributeString("type", type);
            }
            if (!string.IsNullOrEmpty(nature))
            {
                this._xmlFile.WriteAttributeString("nature", nature);
            }
            content = XmlEncoding.CheckString(content, true);
            if (type != null && !string.IsNullOrEmpty(content) && _LdataType.Contains(type.ToLower())
                && (content.Contains("<") || content.Contains("&")))
            {
                if (content.Contains("]]>"))
                {
                    this._xmlFile.WriteRaw("<![CDATA[" + content.Replace("]]>", "]]]]><![CDATA[>") + "]]>");
                }
                else
                {
                    this._xmlFile.WriteCData(content);
                }
            }
            else
            {
                if (content == null)
                {
                    content = string.Empty;
                }
                this._xmlFile.WriteString(content);
            }
            this._xmlFile.WriteEndElement();
            if (ErrorMessageCode > 0)
            {
                throw new ApplicationException(ErrorMessage);
            }
        }

        /// <summary>
        /// Writes the multivalued property.
        /// </summary>
        /// <param name="name">The name.</param>
        /// <param name="type">The type.</param>
        /// <param name="content">The content.</param>
        /// <param name="nature">not used</param>
        /// <exception cref="System.ApplicationException"></exception>
        public void WriteMultivaluedProperty(string name, string type, string[] content, string nature)
        {
            if (this._descriptionTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._descriptionTagOpen = false;
            }
            if (this._internalLinksTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._internalLinksTagOpen = false;
            }
            if (this._externalLinksTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._externalLinksTagOpen = false;
            }
            ErrorMessage = string.Empty;
            ErrorMessageCode = 0;
            if (string.IsNullOrEmpty(name))
            {
                return;
            }
            if (content == null || content.Length == 0)
            {
                return;
            }
            if (!this._propertiesTagOpen)
            {
                this._propertiesTagOpen = true;
                this._xmlFile.WriteStartElement("PROPERTIES");
            }
            this._xmlFile.WriteStartElement("MPROPERTY");
            this._xmlFile.WriteAttributeString("name", name);
            if (!string.IsNullOrEmpty(type))
            {
                this._xmlFile.WriteAttributeString("type", type);
            }
            if (!string.IsNullOrEmpty(nature))
            {
                this._xmlFile.WriteAttributeString("nature", nature);
            }
            foreach (string value in content)
            {
                this._xmlFile.WriteStartElement("VALUES");
                string newValue = XmlEncoding.CheckString(value, true);
                if (type != null && !string.IsNullOrEmpty(newValue) && _LdataType.Contains(type.ToLower())
                    && (newValue.Contains("<") || newValue.Contains("&")))
                {
                    if (newValue.Contains("]]>"))
                    {
                        this._xmlFile.WriteRaw("<![CDATA[" + newValue.Replace("]]>", "]]]]><![CDATA[>") + "]]>");
                    }
                    else
                    {
                        this._xmlFile.WriteCData(newValue);
                    }
                }
                else
                {
                    if (newValue == null)
                    {
                        newValue = string.Empty;
                    }
                    this._xmlFile.WriteString(newValue);
                }
                this._xmlFile.WriteEndElement();
            }
            this._xmlFile.WriteEndElement();
            if (ErrorMessageCode > 0)
            {
                throw new ApplicationException(ErrorMessage);
            }
        }

        /// <summary>
        /// Write a description (special property) of an artefact.
        /// This description can be a file or directly write in the xml file.
        /// </summary>
        /// <param name="name">name of the property</param>
        /// <param name="type">type of the property (rtf, string, html, xml ...)
        /// <para>if <paramref name="url"/> is not null, the type must be declared in file MimeTypes.properties located in Orchestra Server Home</para>
        /// (<c>txt, cpp, xml, htm, html, xsl, ppt, pdf, eps, jpeg, jpg, png, bmp, gif, wmf, emf, zip</c>)</param>
        /// <param name="url">relafive path of the file containing the description<para>Optional if <paramref name="content"/> is not null.</para></param>
        /// <param name="nature">not used</param>
        /// <param name="content">value of the description<para>Optional if <paramref name="url"/> is not null.</para></param>
        /// <exception cref="System.ApplicationException"></exception>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// Orchestra.Framework.Artefact.Gef oGef;
        /// oGef = new Orchestra.Framework.Artefact.Gef();
        /// 
        /// // your export code
        /// 
        /// oGef.WriteTextualDescription("Description", "string", "", "", "", "my description content");
        /// </code>
        /// </example>
        public void WriteTextualDescription(string name, string type, string url, string nature, string content)
        {
            if (this._propertiesTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._propertiesTagOpen = false;
            }
            if (this._internalLinksTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._internalLinksTagOpen = false;
            }
            if (this._externalLinksTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._externalLinksTagOpen = false;
            }
            ErrorMessage = string.Empty;
            ErrorMessageCode = 0;
            if (string.IsNullOrEmpty(type) || (string.IsNullOrEmpty(url) && string.IsNullOrEmpty(content)))
            {
                return;
            }
            if (!this._descriptionTagOpen)
            {
                this._descriptionTagOpen = true;
                this._xmlFile.WriteStartElement("DESCRIPTION");
            }
            if (!string.IsNullOrEmpty(url))
            {
                this.WriteFileReferenceTag(type, url, nature);
            }
            if (!string.IsNullOrEmpty(content))
            {
                this._xmlFile.WriteStartElement("TEXTUAL_DESCRIPTION");
                this._xmlFile.WriteAttributeString("type", type);
                if (content.Contains("]]>"))
                {
                    this._xmlFile.WriteRaw("<![CDATA[" + content.Replace("]]>", "]]]]><![CDATA[>") + "]]>");
                }
                else
                {
                    this._xmlFile.WriteCData(content);
                }
                this._xmlFile.WriteEndElement();
            }
            if (ErrorMessageCode > 0)
            {
                throw new ApplicationException(ErrorMessage);
            }
        }

        /// <summary>
        /// Write a reference to a file (special property)
        /// <para>if the file is a description use <see cref="IExport.WriteTextualDescription"/></para>
        /// </summary>
        /// <param name="type">type of the property.
        /// <para>The type must be declared in file MimeTypes.properties located in Orchestra Server Home</para>
        /// (<c>txt, cpp, xml, htm, html, xsl, ppt, pdf, eps, jpeg, jpg, png, bmp, gif, wmf, emf, zip</c>)</param>
        /// <param name="url">relafive path of the file containing the value of the property</param>
        /// <param name="nature"></param>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// Orchestra.Framework.Artefact.Gef oGef;
        /// oGef = new Orchestra.Framework.Artefact.Gef();
        /// 
        /// // your export code
        /// 
        /// oGef.WriteFileReference("emf", "87e97ad0\diagram.emf", "");
        /// </code>
        /// </example>
        public void WriteFileReference(string type, string url, string nature)
        {
            if (this._descriptionTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._descriptionTagOpen = false;
            }
            if (this._propertiesTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._propertiesTagOpen = false;
            }
            if (this._internalLinksTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._internalLinksTagOpen = false;
            }
            if (this._externalLinksTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._externalLinksTagOpen = false;
            }
            this.WriteFileReferenceTag(type, url, nature);
        }

        /// <summary>
        /// write a link to an internal artefact
        /// </summary>
        /// <param name="uriTarget">papeete Uri of the target artefact (see <see cref="IUri"/> for more information)</param>
        /// <param name="typeLink">type of the link</param>
        /// <param name="outLink"><c>true</c> if the link is an OUT link, <c>false</c> if the link is an IN link</param>
        /// <param name="fullname"></param>
        /// <param name="label"></param>
        /// <exception cref="System.ApplicationException"></exception>
        public void WriteInternalReference(
            string uriTarget, string typeLink, bool outLink, string fullname, string label)
        {
            if (this._descriptionTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._descriptionTagOpen = false;
            }
            if (this._propertiesTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._propertiesTagOpen = false;
            }
            if (this._externalLinksTagOpen)
            {
                this._xmlFile.WriteEndElement();
                this._externalLinksTagOpen = false;
            }
            ErrorMessage = string.Empty;
            ErrorMessageCode = 0;
            if (string.IsNullOrEmpty(uriTarget))
            {
                return;
            }
            if (!this._internalLinksTagOpen)
            {
                this._internalLinksTagOpen = true;
                this._xmlFile.WriteStartElement("LINKS_TO_ELEMENTS");
            }
            this._xmlFile.WriteStartElement("ELEMENT_REFERENCE");
            this._xmlFile.WriteAttributeString("uri", uriTarget);
            if (!string.IsNullOrEmpty(fullname))
            {
                this._xmlFile.WriteAttributeString("fullName", fullname);
            }
            if (!string.IsNullOrEmpty(label))
            {
                this._xmlFile.WriteAttributeString("label", label);
            }
            if (!string.IsNullOrEmpty(typeLink))
            {
                this._xmlFile.WriteAttributeString("linkType", typeLink);
            }
            this._xmlFile.WriteAttributeString("linkDirection", outLink ? OutLink : InLink);
            this._xmlFile.WriteEndElement();
            if (ErrorMessageCode > 0)
            {
                throw new ApplicationException(ErrorMessage);
            }
        }

        /// <summary>
        /// Used to format each part of full name of an artefact
        /// </summary>
        /// <param name="part"></param>
        /// <returns>formatted string</returns>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// Orchestra.Framework.Artefact.Gef oGef;
        /// oGef = new Orchestra.Framework.Artefact.Gef();
        /// string viewName;
        /// string encodedViewName
        /// 
        /// strViewName = "10.Document View";
        /// 
        /// strEncodedViewName = FormatFullnamePart(strViewName);
        /// //return "10%2EDocument View"
        /// </code>
        /// </example>
        public virtual string FormatFullnamePart(string part)
        {
            return SFormatFullnamePart(part);
        }

        ///<summary>
        /// Not implemented.
        /// For future use.
        ///</summary>
        ///<param name="type">Artefact type</param>
        ///<returns>Always true</returns>
        public bool IsAllowedArtefact(string type)
        {
            return true;
        }

        /// <summary>
        /// Test if a file extension is managed by Orchestra
        /// </summary>
        /// <param name="fileExtension"></param>
        /// <returns><c>>true</c> if the <paramref name="fileExtension"/> is managed by Orchestra, <c>false</c> otherwise</returns>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// Orchestra.Framework.Artefact.Gef oGef;
        /// oGef = new Orchestra.Framework.Artefact.Gef();
        /// if (oGef.IsMimeTypeExisting("rtf")) {
        ///     
        /// }
        /// else
        /// {
        ///     throw new ApplicationException("mime type not managed by Orchestra");
        /// }
        /// </code>
        /// </example>
        public bool IsMimeTypeExisting(string fileExtension)
        {
            return this._mimeTypes.ContainsKey(fileExtension);
        }

        /// <summary>
        /// Get the mime type for a file extension.
        /// </summary>
        /// <param name="fileExtension">The file extension.</param>
        /// <returns>the mime type associated with the <paramref name="fileExtension"/></returns>
        public string MimeTypeForExtension(string fileExtension)
        {
            string returnValue = null;
            if (fileExtension != null)
            {
                this._mimeTypes.TryGetValue(fileExtension, out returnValue);
            }
            return returnValue;
        }

        /// <summary>
        /// Close the xml file.
        /// </summary>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// Orchestra.Framework.Artefact.Gef oGef;
        /// oGef = new Orchestra.Framework.Artefact.Gef();
        /// 
        /// // your export code
        /// 
        /// oGef.CloseOutputFile();
        /// </code>
        /// </example>
        public void CloseOutputFile()
        {
            this._xmlFile.WriteEndElement();
            this._xmlFile.WriteEndElement();
            this._xmlFile.WriteEndDocument();
            this._xmlFile.Close();
        }
        #endregion

        ///<summary>
        /// UTF-8 Encode <paramref name="part"/>. The char '.' is encoding to "%2E".
        ///</summary>
        ///<param name="part"></param>
        ///<returns>UTF-8 encoding string</returns>
        public static string SFormatFullnamePart(string part)
        {
            if (string.IsNullOrEmpty(part))
            {
                return "";
            }
            string encoded = XmlEncoding.Encode(part);
            // ReSharper disable PossibleNullReferenceException
            return encoded.Replace(".", "%2E");
            // ReSharper restore PossibleNullReferenceException   
        }

        private static string MakeValidFileName(string name)
        {
            return Version.SGetHash(name);
        }

        private void WriteFileReferenceTag(string type, string url, string nature)
        {
            string mimeType;
            ErrorMessage = string.Empty;
            ErrorMessageCode = 0;
            if (this._mimeTypes == null || !this._mimeTypes.TryGetValue(type, out mimeType) || string.IsNullOrEmpty(url))
            {
                return;
            }
            this._xmlFile.WriteStartElement("FILE_REFERENCE");
            this._xmlFile.WriteAttributeString("mimeType", mimeType);
            this._xmlFile.WriteAttributeString("url", url);
            if (!string.IsNullOrEmpty(nature))
            {
                this._xmlFile.WriteAttributeString("nature", nature);
            }
            this._xmlFile.WriteEndElement();
        }
    }
}