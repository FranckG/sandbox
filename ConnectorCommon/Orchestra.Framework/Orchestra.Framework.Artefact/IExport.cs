// ----------------------------------------------------------------------------------------------------
// File Name: IExport.cs
// Project: Orchestra.Framework.Artefact
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Artefact
{
    using System;
    using System.Runtime.InteropServices;
    using Orchestra.Framework.Utilities;

    /// <summary>
    /// COM Interface for Artefact export
    /// </summary>
    [ComVisible(true)]
    [Guid("D7B0ED86-6811-4fa0-8C9D-0523D8E530F8")]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface IExport
    {
        /// <summary>
        /// This property creates and returns the path of the directory for the extra files.
        /// </summary>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// using Orchestra.Framework.Artefact;
        /// 
        /// Gef oGef = new Gef();
        /// String extraFilesDirectory;
        /// oGef.ExtraFileDirectory = extraFilesDirectory; // the directory is created (the name of the directory can be changed)
        /// extraFilesDirectory = oGef.ExtraFileDirectory; // returns the name of directory created
        /// </code>
        /// </example>
        [DispId(2)]
        string ExtraFileDirectory { get; set; }

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
        /// String outputDirectory;
        /// 
        /// outputDirectory = oGef.InitOutputFile(@"D:\Shareddir\temp.gef");
        /// </code>
        /// </example>
        [DispId(3)]
        String InitOutputFile(string outputFileName);

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
        [DispId(4)]
        void CloseOutputFile();

        /// <summary>
        /// Start export of an artefact.
        /// </summary>
        /// <param name="uri">papeete Uri of the artefact (see <see cref="IUri"/> for more information)</param>
        /// <param name="name">artefact name</param>
        /// <param name="type">artefact type</param>
        /// <param name="label">artefact label</param>
        /// <param name="hash">hash of the artefact</param>
        /// <param name="filePath">relative file path of the artefact container (See <see cref="Version"/> for more information)</param>
        /// <param name="version">version of the artefact container (See <see cref="Version"/> for more information)</param>
        /// <param name="fullLabel">if true then the object label is "(projectName objectType) <paramref name="label"/>" else the object label is "<paramref name="label"/>"</param>
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
        [DispId(6)]
        void WriteStartArtefact(string uri, string name, string type, string label, string hash,
                                string filePath,
                                string version, bool fullLabel);

        /// <summary>
        /// End export of an artefact.
        /// </summary>
        [DispId(7)]
        void WriteEndArtefact();

        /// <summary>
        /// Write a property of an artefact.
        /// </summary>
        /// <param name="name">name of the property</param>
        /// <param name="type">type of the property</param>
        /// <param name="content">value of the property</param>
        /// <param name="nature">not used</param>
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
        /// </example>
        [DispId(9)]
        void WriteProperty(string name, string type, string content, string nature);

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
        [DispId(10)]
        void WriteTextualDescription(string name, string type, string url, string nature, string content);

        /// <summary>
        /// Write a reference to a file (special property)
        /// <para>if the file is a description use <see cref="WriteTextualDescription"/></para>
        /// </summary>
        /// <param name="name">name of the property</param>
        /// <param name="type">type of the property.
        /// <para>The type must be declared in file MimeTypes.properties located in Orchestra Server Home</para>
        /// (<c>txt, cpp, xml, htm, html, xsl, ppt, pdf, eps, jpeg, jpg, png, bmp, gif, wmf, emf, zip</c>)</param>
        /// <param name="url">relafive path of the file containing the value of the property</param>
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
        [DispId(11)]
        void WriteFileReference(string name, string type, string url);

        ///<summary>
        /// Orchestra Internal Use.
        /// reserved for the Surrogate Module Migration To LM
        ///</summary>
        ///<param name="uriTarget"></param>
        ///<param name="typeLink"></param>
        [DispId(12), Obsolete]
        void WriteReferenceForSurrogateModuleMigrationToLM(string uriTarget, string typeLink);

        /// <summary>
        /// write a link to an internal artefact
        /// </summary>
        /// <param name="uriTarget">papeete Uri of the target artefact (see <see cref="IUri"/> for more information)</param>
        /// <param name="typeLink">type of the link</param>
        /// <param name="outLink"><c>true</c> if the link is an OUT link, <c>false</c> if the link is an IN link</param>
        /// <param name="fullname"></param>
        /// <param name="label"></param>
        [DispId(13)]
        void WriteInternalReference(string uriTarget, string typeLink, bool outLink, string fullname, string label);

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
        /// String viewName;
        /// String encodedViewName
        /// 
        /// strViewName = "10.Document View";
        /// 
        /// strEncodedViewName = FormatFullnamePart(strViewName);
        /// //return "10%2EDocument View"
        /// </code>
        /// </example>
        [DispId(14)]
        string FormatFullnamePart(string part);

        ///<summary>
        /// Not implemented.
        /// For future use.
        ///</summary>
        ///<param name="type">Artefact type</param>
        ///<returns>Always true</returns>
        [DispId(15)]
        bool IsAllowedArtefact(string type);

        /// <summary>
        /// Test if a file extension is managed by Orchestra
        /// </summary>
        /// <param name="fileExtension"></param>
        /// <returns><c>>true</c> if the <paramref name="fileExtension"/> is managed by Orchestra, <c>false</c> otherwise</returns>
        [DispId(16)]
        bool IsMimeTypeExisting(string fileExtension);

        /// <summary>
        /// Get the mime type for a file extension.
        /// </summary>
        /// <param name="fileExtension">The file extension.</param>
        /// <returns>the mime type associated with the <paramref name="fileExtension"/></returns>
        [DispId(17)]
        string MimeTypeForExtension(string fileExtension);

        /// <summary>
        /// Writes the multivalued property.
        /// </summary>
        /// <param name="name">The name.</param>
        /// <param name="type">The type.</param>
        /// <param name="content">The content.</param>
        /// <param name="nature">not used</param>
        /// <exception cref="System.ApplicationException"></exception>
        [DispId(18)]
        void WriteMultivaluedProperty(string name, string type, string[] content, string nature);
    }
}