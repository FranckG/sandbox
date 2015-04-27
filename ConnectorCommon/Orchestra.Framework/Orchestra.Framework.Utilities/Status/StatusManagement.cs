// ----------------------------------------------------------------------------------------------------
// File Name: StatusManagement.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Runtime.InteropServices;
    using System.Text;
    using System.Xml.Serialization;

    ///<summary>
    /// This class implements the Status Definition management
    ///</summary>
    /// <remarks>Xml file is compliant with Status.xsd</remarks>
    [ComVisible(true)]
    [Guid("96E1C3B9-2D4F-45F2-B967-3A278E447E51")]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId("Orchestra.StatusDefinition")]
    public partial class StatusDefinition : IStatusDefinition
    {
        [XmlIgnore]
        private List<Status> _finding;

        ///<summary>
        /// Constructor
        ///</summary>
        public StatusDefinition()
        {
            this.status = new Status();
        }

        #region IStatusDefinition Members
        ///<summary>
        /// Serialize the Status Definition
        ///</summary>
        ///<returns>Status Definition serialized in XML.</returns>
        public override string ToString()
        {
            TextWriter writer = new StringWriterUTF8();
            XmlSerializer serializer = new XmlSerializer(typeof(StatusDefinition));
            XmlSerializerNamespaces xsns = new XmlSerializerNamespaces();
            xsns.Add("status", "http://www.thalesgroup.com/orchestra/framework/4_0_25/Status");
            serializer.Serialize(writer, this, xsns);
            string returnedValue = writer.ToString();
            writer.Close();
            return returnedValue;
        }

        ///<summary>
        /// Deserialize a Status Definition
        ///</summary>
        ///<param name="s"></param>
        public void SetStatusDefinition(string s)
        {
            XmlSerializer serializer = new XmlSerializer(typeof(StatusDefinition));
            TextReader reader = null;
            try
            {
                reader = new StringReader(s);
                StatusDefinition sd = (StatusDefinition)serializer.Deserialize(reader);
                this.status = sd.status;
            }
            finally
            {
                if (reader != null)
                {
                    reader.Close();
                }
            }
        }

        ///<summary>
        /// Add a new <see cref="Status"/> in StatusDefinition
        ///</summary>
        ///<param name="statusSeverity">See <see cref="StatusSeverity"/></param>
        ///<param name="uri">Artefact Uri</param>
        ///<param name="message">Message</param>
        ///<param name="code">Code</param>
        ///<param name="codeSpecified"><c>true</c> if <paramref name="code"/> is specified otherwise <c>false</c></param>
        ///<param name="parentStatus">parent Status (see <see cref="Status"/>)</param>
        ///<returns>The added <see cref="Status"/></returns>
        public Status WriteStatus(
            StatusSeverity statusSeverity, string uri, string message, int code, bool codeSpecified, Status parentStatus)
        {
            try
            {
                if (statusSeverity > this.status.severity)
                {
                    this.status.severity = statusSeverity;
                }
                if (uri == string.Empty)
                {
                    uri = null;
                }
                if (parentStatus == null)
                {
                    this.status.severity = statusSeverity;
                    this.status.uri = uri;
                    this.status.message = message ?? string.Empty;
                    if (!codeSpecified)
                    {
                        this.status.codeSpecified = false;
                    }
                    else
                    {
                        this.status.codeSpecified = true;
                        this.status.code = code;
                    }
                    return this.status;
                }
                Status s = new Status
                    {
                        severity = statusSeverity,
                        uri = uri,
                        message = message,
                        codeSpecified = codeSpecified,
                        code = code
                    };
                parentStatus.AddStatus(s);
                return s;
            }
            catch (Exception)
            {
            }
            return null;
        }

        ///<summary>
        /// Finds all statuses with uri attribute equals to <paramref name="uriToFound"/>.
        ///</summary>
        ///<param name="uriToFound">Uri to found.</param>
        ///<returns>Count of statuses founded.</returns>
        public int FindStatusesByUri(string uriToFound)
        {
            this._finding = null;
            if (uriToFound == null)
            {
                return 0;
            }
            this._finding = this.status.FindStatusesByUri(uriToFound);
            return this._finding.Count;
        }

        ///<summary>
        /// Gets Status at <paramref name="index"/>.
        /// <para>
        /// This method must be called after <see cref="IStatusDefinition.FindStatusesByUri"/>
        /// </para>
        ///</summary>
        ///<param name="index">Index.</param>
        ///<returns>Status at <paramref name="index"/>, returns null if there is no status at <paramref name="index"/>.</returns>
        public Status GetStatusByUriAtIndex(int index)
        {
            if (this._finding != null && index >= 0 && index < this._finding.Count)
            {
                return this._finding[index];
            }
            return null;
        }
        #endregion
    }

    ///<summary>
    /// This class implements the Status management
    ///</summary>
    [ComVisible(true)]
    [Guid("1091111D-4837-49AA-96D4-EF63E3B37B8A")]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId("Orchestra.Status")]
    public partial class Status : IStatus
    {
        #region IStatus Members
        ///<summary>
        /// Add a child status
        ///</summary>
        ///<param name="s">Status to add</param>
        public void AddStatus(Status s)
        {
            if (ReferenceEquals(this.status, null))
            {
                this.status = new[] { s };
            }
            else
            {
                Status[] temp = this.status;
                Append(ref temp, s);
                this.status = temp;
            }
        }

        ///<summary>
        /// Property to get the count of children statuses
        ///</summary>
        public int ChildCount
        {
            get
            {
                Status[] statuses = this.statusField;
                if (statuses != null)
                {
                    return statuses.Length;
                }
                return 0;
            }
        }

        ///<summary>
        /// Returns the child Status at <paramref name="index"/>
        ///</summary>
        ///<param name="index">index of child status</param>
        ///<returns>Status at <paramref name="index"/></returns>
        public Status GetChildStatus(int index)
        {
            Status[] statuses = this.statusField;
            if (statuses != null && (index >= 0 && index < statuses.Length))
            {
                return this.statusField[index];
            }
            return null;
        }
        #endregion

        /// <summary>
        /// Finds the statuses by URI.
        /// </summary>
        /// <param name="uriToFound">The URI to found.</param>
        /// <returns>List with statuses found</returns>
        public List<Status> FindStatusesByUri(string uriToFound)
        {
            List<Status> statuses = new List<Status>();
            if (this.uriField != null && this.uriField == uriToFound)
            {
                statuses.Add(this);
            }
            if (this.statusField != null)
            {
                foreach (Status s in this.statusField)
                {
                    statuses.AddRange(s.FindStatusesByUri(uriToFound));
                }
            }
            return statuses;
        }

        /// <summary>
        /// Finds the statuses with partial URI.
        /// </summary>
        /// <param name="partialUri">The partial URI.</param>
        /// <returns>List with statuses found</returns>
        public List<Status> FindStatusesWithPartialUri(string partialUri)
        {
            List<Status> statuses = new List<Status>();
            if (this.uriField != null && this.uriField.Contains(Utilities.OrchestraEncoding.SEncodeString(partialUri)))
            {
                statuses.Add(this);
            }
            if (this.statusField != null)
            {
                foreach (Status s in this.statusField)
                {
                    statuses.AddRange(s.FindStatusesWithPartialUri(partialUri));
                }
            }
            return statuses;
        }  

        private static void Append(ref Status[] array, Status item)
        {
            int oldLength = array.Length;
            Array.Resize(ref array, oldLength + 1);
            array[oldLength] = item;
        }
    }

    internal class StringWriterUTF8 : StringWriter
    {
        public override Encoding Encoding
        {
            get
            {
                return Encoding.UTF8;
            }
        }
    }
}