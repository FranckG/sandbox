// ----------------------------------------------------------------------------------------------------
// File Name: Element.cs
// Project: Orchestra.Framework.Connector.Common
// Copyright (c) Thales, 2010 - 2013. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Connector.Common
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Text;
    using Orchestra.Framework.Artefact;
    using Orchestra.Framework.Core;
    using Orchestra.Framework.Utilities;
    using VersionTag = Orchestra.Framework.Utilities.Version;

    /// <summary>
    /// Class Element
    /// </summary>
    public abstract class Element : Artefact
    {
        private const string MessageInvalidXmlCharOnProperty = "{0} On property: {1}";
        private const string MessageInvalidXmlCharOnElement = "{0} On artefact: {1}";
        private static readonly Dictionary<string, string> _ListOfFilesHash = new Dictionary<string, string>();
        private static readonly Dictionary<string, VersionOfFile> _ListOfFilesVersion = new Dictionary<string, VersionOfFile>();
        private readonly List<ElementLink> _linksToExport;
        private readonly List<ElementProperty> _propertiesToExport;
        private readonly StringBuilder _stringToHash;

        #region Configuration of Element
        /// <summary>
        /// Configure the load links policy
        /// By Default <c>true</c> if <see cref="ContextConnector.Service"/> is DocumentaryExport or LinkManagerExport, otherwise <c>false</c>.
        /// </summary>
        public static bool CanLoadLinks = ContextConnector.Service == Constants.ServiceEnum.DocumentaryExport
                                  || ContextConnector.Service == Constants.ServiceEnum.LinkManagerExport;

        /// <summary>
        /// Configure the load properties policy
        /// By Default <c>true</c> if <see cref="ContextConnector.Service"/> is DocumentaryExport otherwise <c>false</c>.
        /// </summary>
        public static bool CanLoadProperties = ContextConnector.Service == Constants.ServiceEnum.DocumentaryExport;

        /// <summary>
        /// Configure the hash computation policy
        /// By Default <c>true</c> if <see cref="ContextConnector.Service"/> is DocumentaryExport otherwise <c>false</c>.
        /// </summary>
        public static bool CanComputeHash = ContextConnector.Service == Constants.ServiceEnum.DocumentaryExport;

        /// <summary>
        /// Configure the version computation policy
        /// By Default <c>true</c> if <see cref="ContextConnector.Service"/> is DocumentaryExport or LinkManagerExport, otherwise <c>false</c>.
        /// </summary>
        public static bool CanComputeVersion = ContextConnector.Service == Constants.ServiceEnum.DocumentaryExport
                                  || ContextConnector.Service == Constants.ServiceEnum.LinkManagerExport;

        /// <summary>
        /// Configure the FullLabel computation policy
        /// By Default <c>true</c> if <see cref="ContextConnector.Service"/> is not Expand, otherwise <c>false</c>.
        /// </summary>
        public static bool CanComputeFullLabel = ContextConnector.Service != Constants.ServiceEnum.Expand;

        #endregion

        /// <summary>
        /// Set to true to export this element.
        /// Set to false doesn't export this element, but parse the children
        /// </summary>
        protected bool IsExportableElement;

        private string _name;

        /// <summary>
        /// Initializes a new instance of the <see cref="Element" /> class.
        /// In this constructor, you need to initialize the uri, label of the artefact.
        /// </summary>
        protected Element()
        {
            if (CanComputeHash)
            {
                this._stringToHash = new StringBuilder();
            }
            this._propertiesToExport = new List<ElementProperty>();
            this._linksToExport = new List<ElementLink>();
            this.RootName = ContextConnector.RootName;
            this.RootType = ContextConnector.RootType;
            this.Label = "";
            this._name = "";
        }

        /// <summary>
        /// Gets or sets the label.
        /// </summary>
        /// <value>The label.</value>
        public string Label { get; set; }

        /// <summary>
        /// Exports the element.
        /// </summary>
        public void ExportElement()
        {
            if (!this.IsAllowedToExport())
            {
                return;
            }
            if (this.IsExportableElement)
            {
                string hash = "";
                string filePath = "";
                string fileVersion = "";

                if (CanLoadLinks)
                {
                    this.LoadLink();
                }
                if (CanLoadProperties)
                {
                    this.LoadProperties();
                }
                if (CanComputeHash)
                {
                    hash = this.IsProject() ? this.ContainerFileHash() : this.GetHash();
                }
                if (CanComputeVersion)
                {
                    string elementPath = this.GetFileContainer();
                    if (elementPath != null)
                    {
                        fileVersion = Version(elementPath, this, out filePath);
                    }
                }
                try
                {
                    ContextConnector.GefFile.WriteStartArtefact(
                        this.ToString(),
                        this.GetName(),
                        this.Type,
                        this.Label,
                        hash,
                        filePath,
                        fileVersion,
                        CanComputeFullLabel);
                }
                catch (ApplicationException ex)
                {
                    ContextConnector.StatusDefinition.WriteStatus(
                        StatusSeverity.ERROR,
                        this.Uri,
                        string.Format(MessageInvalidXmlCharOnElement, ex.Message, this.Label),
                        0,
                        false,
                        ContextConnector.StatusDefinition.status);
                    throw new ApplicationException();
                }
                if (CanLoadProperties)
                {
                    this.WriteAllProperties();
                }
                if (CanLoadLinks)
                {
                    this.WriteAllLinks();
                }
                else if (ContextConnector.Service == Constants.ServiceEnum.Expand && !this.IsProject())
                {
                    try
                    {
                        ContextConnector.GefFile.WriteProperty(
                            "fragmentName",
                            "string",
                            string.Format("({0} {1}) {2}", this.RootName, this.Type, this.Label),
                            "");
                    }
                    catch (ApplicationException)
                    {
                        //DO NOTHING
                    }
                }
            }
            this.ExportChildren();
            if (this.IsExportableElement)
            {
                ContextConnector.GefFile.WriteEndArtefact();
            }
        }

        /// <summary>
        /// Gets the file container of this artefact.
        /// </summary>
        /// <returns>System.String.</returns>
        public virtual string GetFileContainer()
        {
            return "";
        }

        /// <summary>
        /// Gets the name.
        /// </summary>
        /// <returns>System.String.</returns>
        public string GetName()
        {
            return this._name;
        }

        /// <summary>
        /// Adds the link for export.
        /// </summary>
        /// <param name="link">The link see <see cref="ElementLink"/>.</param>
        protected void AddLinkForExport(ElementLink link)
        {
            if (link != null)
            {
                this._linksToExport.Add(link);
                if (CanComputeHash)
                {
                    this._stringToHash.Append(link);
                }
            }
        }

        /// <summary>
        /// Adds the property for export.
        /// </summary>
        /// <param name="property">The property.</param>
        protected void AddPropertyForExport(ElementProperty property)
        {
            if (property != null)
            {
                this._propertiesToExport.Add(property);
                if (CanComputeHash)
                {
                    this._stringToHash.Append(property);
                }
            }
        }

        /// <summary>
        /// Return the hash of the conternaier file of the artefact.
        /// By default, return the hash of the container file of the artefact.
        /// Can be overrided.
        /// </summary>
        /// <returns>the hash.</returns>
        protected virtual string ContainerFileHash()
        {
            if (File.Exists(this.GetFileContainer()))
            {
                return FileHash(this.GetFileContainer());
            }
            return "";
        }

        /// <summary>
        /// Exports the children.
        /// By default, do nothing.
        /// Can be overrided.
        /// </summary>
        protected virtual void ExportChildren()
        {
        }

        /// <summary>
        /// Gets the hash.
        /// </summary>
        /// <returns>System.String.</returns>
        protected string GetHash()
        {
            return Framework.Artefact.Version.SGetHash(this._stringToHash.ToString());
        }

        /// <summary>
        /// Determines whether the property name is allowed.
        /// By default, always return true.
        /// Can be overrided.
        /// </summary>
        /// <param name="propertyName">Name of the property.</param>
        /// <returns><c>true</c> if the property name is allowed; otherwise, <c>false</c>.</returns>
        protected virtual bool IsAllowedProperty(string propertyName)
        {
            return true;
        }

        /// <summary>
        /// Determines whether the artefact is allowed to export.
        /// By default, always return true.
        /// Can be overrided.
        /// </summary>
        /// <returns><c>true</c> if the artefact is allowed to export; otherwise, <c>false</c>.</returns>
        protected virtual bool IsAllowedToExport()
        {
            return true;
        }

        /// <summary>
        /// Loads the link.
        /// By default, do nothing.
        /// Can be overrided.
        /// </summary>
        protected virtual void LoadLink()
        {
        }

        /// <summary>
        /// Loads the properties.
        /// By default, initialize the hash with the artefact.
        /// Can be overrided.
        /// </summary>
        protected virtual void LoadProperties()
        {
            if (CanComputeHash)
            {
                this._stringToHash.Append(this.Id + this.GetName() + this.Type);
            }
        }

        /// <summary>
        /// Sets the name and replace <paramref name="namePartSeparators"/> with '.'
        /// </summary>
        /// <param name="value">The value.</param>
        /// <param name="namePartSeparators">The name part separators.</param>
        protected void SetName(string value, string[] namePartSeparators)
        {
            if (namePartSeparators != null && namePartSeparators.Length == 0)
            {
                this._name = value;
            }
            else
            {
                this._name = FormatFullName(value, namePartSeparators);
            }
        }

        /// <summary>
        /// Sets the name.
        /// </summary>
        /// <param name="value">The value.</param>
        protected void SetName(string value)
        {
            this._name = value;
        }

        private static string FormatFullName(string fullName, string[] stringSeparators)
        {
            string[] tmp = fullName.Split(stringSeparators, StringSplitOptions.RemoveEmptyEntries);
            StringBuilder result = new StringBuilder();
            foreach (string s in tmp)
            {
                if (result.Length > 0)
                {
                    result.Append('.');
                }
                result.Append(Gef.SFormatFullnamePart(s));
            }
            return result.ToString();
        }

        private void WriteAllLinks()
        {
            if (this._linksToExport == null)
            {
                return;
            }
            foreach (ElementLink l in this._linksToExport)
            {
                ContextConnector.GefFile.WriteInternalReference(l.Id, l.Type, true, l.Fullname, l.Label);
            }
        }

        private void WriteAllProperties()
        {
            if (this._propertiesToExport == null)
            {
                return;
            }
            foreach (ElementProperty e in this._propertiesToExport)
            {
                try
                {
                    if (e.Name.Equals("native_doc"))
                    {
                        ContextConnector.GefFile.WriteFileReference(e.Type, e.Value, e.Name);
                    }
                    else if (e.Name.Equals("description"))
                    {
                        ContextConnector.GefFile.WriteTextualDescription(e.Name, e.Type, "", "", e.Value);
                    }
                    else
                    {
                        ContextConnector.GefFile.WriteProperty(e.Name, e.Type, e.Value, e.Nature);
                    }
                }
                catch (ApplicationException ex)
                {
                    ContextConnector.StatusDefinition.WriteStatus(
                        StatusSeverity.WARNING,
                        this.Uri,
                        string.Format(MessageInvalidXmlCharOnProperty, ex.Message, e.Name),
                        0,
                        false,
                        ContextConnector.StatusDefinition.status);
                }
            }
        }

        #region Files Hash and Version
        internal static string FileHash(string filePath)
        {
            if (filePath == null || !CanComputeHash)
            {
                return "";
            }
            string hash;
            if (!_ListOfFilesHash.TryGetValue(filePath, out hash))
            {
                if (File.Exists(filePath))
                {
                    hash = Framework.Artefact.Version.SGetFileHash(filePath);
                    _ListOfFilesHash.Add(filePath, hash);
                }
            }
            return hash;
        }

        private static string Version(string filePath, Artefact artefact, out string relativePath)
        {
            relativePath = "";
            if (filePath == null)
            {
                return "";
            }
            VersionOfFile o = ComputeVersion(filePath, artefact);
            relativePath = o.RelativePath;
            return o.VersionInEnvironment;
        }

        private static VersionOfFile ComputeVersion(string filePath, Artefact artefact)
        {
            VersionOfFile o;
            if (!_ListOfFilesVersion.TryGetValue(filePath, out o))
            {
                if (File.Exists(filePath))
                {
                    o = new VersionOfFile();
                    Artefact artefactForMetadata = new Artefact { Uri = artefact.Uri };
                    artefactForMetadata.AddParameter(Constants.Parameters.PhysicalPath, filePath);
                    if (!string.IsNullOrEmpty(ContextConnector.EnvironmentId))
                    {
                        artefactForMetadata.AddParameter(
                            Constants.Parameters.EnvironmentId, ContextConnector.EnvironmentId);
                    }
                    EnvironmentMetadata environment = new EnvironmentMetadata(artefactForMetadata);
                    if (environment.Properties == null || environment.Properties.GENERIC_EXPORT_FORMAT.Length == 0)
                    {
                        ContextConnector.StatusDefinition.WriteStatus(
                            StatusSeverity.ERROR,
                            artefact.Uri,
                            "The request for information on the artifact to environment failed.",
                            64013,
                            true,
                            ContextConnector.StatusDefinition.status);
                        throw new ApplicationException();
                    }
                    foreach (VersionTag item in from element in environment.Properties.GENERIC_EXPORT_FORMAT
                                                where element.Uri == artefact.Uri
                                                from item in element.Items.OfType<VersionTag>()
                                                select item)
                    {
                        o.RelativePath = item.FilePath;
                        o.VersionInEnvironment = item.version;
                        _ListOfFilesVersion.Add(filePath, o);
                        break;
                    }
                }
            }
            return o;
        }
        #endregion

        #region Nested type: VersionOfFile
        private sealed class VersionOfFile
        {
            public string RelativePath = "";
            public string VersionInEnvironment = "";
        }
        #endregion
    }

    /// <summary>
    /// Class ElementProperty
    /// </summary>
    public class ElementProperty
    {

        /// <summary>
        /// 
        /// </summary>
        public const string NativeDoc = "native_doc";
        /// <summary>
        /// 
        /// </summary>
        public const string TypeString = "string";
        /// <summary>
        /// 
        /// </summary>
        public const string TypeBoolean = "boolean";
        /// <summary>
        /// 
        /// </summary>
        public const string TypeRtf = "rtf";
        /// <summary>
        /// 
        /// </summary>
        public const string TypeHtml = "html";
        /// <summary>
        /// GEF relative path of property external resource
        /// </summary>
        public string FilePath = "";
        /// <summary>
        /// Name of the property
        /// </summary>
        public string Name = "";
        /// <summary>
        /// Nature of the property.
        /// Value PapeeteDocInternal indicates that the property is not visible in Mozart
        /// </summary>
        public string Nature = "";
        /// <summary>
        /// Type of the property
        /// </summary>
        public string Type = "";
        /// <summary>
        /// Value of the property
        /// </summary>
        public string Value = "";

        /// <summary>
        /// Returns a <see cref="System.String" /> that represents this instance.
        /// It's used to compute the hash of the artefact
        /// </summary>
        /// <returns>A <see cref="System.String" /> that represents this instance.</returns>
        public override string ToString()
        {
            if (this.Name.Equals("native_doc") && !String.IsNullOrEmpty(this.FilePath) && File.Exists(this.FilePath))
            {
                return this.Name + this.Type + Element.FileHash(this.FilePath);
            }
            return this.Name + this.Type + this.Value;
        }
    }

    /// <summary>
    /// Class ElementLink
    /// </summary>
    public class ElementLink
    {
        /// <summary>
        /// Full name of the link
        /// </summary>
        public string Fullname = "";
        /// <summary>
        /// Orchestra Uri of the target of the link
        /// </summary>
        public string Id = "";
        /// <summary>
        /// Label of the link
        /// </summary>
        public string Label = "";
        /// <summary>
        /// type of the link
        /// </summary>
        public string Type = "";

        /// <summary>
        /// Returns a <see cref="System.String" /> that represents this instance.
        /// It's used to compute the hash of the artefact
        /// </summary>
        /// <returns>A <see cref="System.String" /> that represents this instance.</returns>
        public override string ToString()
        {
            return this.Id + this.Type + this.Fullname;
        }
    }
}