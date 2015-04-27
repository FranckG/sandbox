// ----------------------------------------------------------------------------------------------------
// File Name: Artefact.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Core
{
    using System;
    using System.Collections;
    using System.Collections.Generic;
    using System.Collections.Specialized;
    using System.Runtime.InteropServices;
    using System.Text;
    using System.Text.RegularExpressions;
    using System.Xml.Serialization;
    using Orchestra.Framework.Utilities;

    ///<summary>
    /// Class for Papeete Uri Management
    ///</summary>
    /// <example>
    /// Decode Papeete Uri example
    /// <code>
    /// // C#
    /// // ----------------------------------------------
    /// Uri oUri = new Uri();
    /// oUri.URI = strURN;
    /// strObjectID = oUri.ObjectId;
    /// strObjectType = oUri.ObjectType;
    /// strObjectName = oUri.ObjectName;
    /// strParameters = oUri.Parameters;
    /// </code>
    /// Build Papeete Uri example
    /// <code>
    /// // C#
    /// // ----------------------------------------------
    /// Uri oUri = new Uri();
    /// oUri.ToolName = "Rhapsody";
    /// oUri.ProjectName = strProjectName;
    /// oUri.ObjectId = strObjectID;
    /// oUri.ObjectType = strObjectType;
    /// oUri.ObjectName = strObjectName;
    /// 
    /// String URI = oUri.URI;
    /// </code>
    /// </example>
    [ComVisible(true)]
    [Guid("FE2A8D41-C523-4070-8093-7FEDB40C7201")]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId("Orchestra.IUri")]
    public partial class Artefact : IUri, IComparer<Artefact>
    {
        private const string UriFormatWithObject = "orchestra:{0}//{1}/{2}/{3}/{4}";
        private const string UriFormatWithoutObject = "orchestra:{0}//{1}/{2}";

        private static readonly Regex _RegexUri =
            new Regex(
                @"^orchestra:(?:(?<command>[^:/]*):)?//(?<rootType>[^/?]*)/(?<rootName>[^/?]*)"
                + @"(?:/(?<objectType>[^/?]*)/(?<objectId>[^/?]*))?" + @"(?:\?(?<parameters>(?:([^=&]*)=([^&]*)&?)*))?",
                RegexOptions.Compiled);

        private NameValueCollection _parameters;

        ///<summary>
        /// Gets or sets parameters for the current <see cref="Artefact"/> object.
        ///</summary>
        [XmlIgnore]
        public NameValueCollection Parameters
        {
            get
            {
                return this._parameters;
            }
            set
            {
                this._parameters = value;
            }
        }

        #region IComparer<Artefact> Members
        /// <summary>
        /// Compares two objects and returns a value indicating whether one is less than, equal to, or greater than the other.
        /// </summary>
        /// <returns>
        /// Value Condition Less than zerox is less than y.Zerox equals y.Greater than zerox is greater than y.
        /// </returns>
        /// <param name="y">The second object to compare.</param><param name="x">The first object to compare.</param>
        int IComparer<Artefact>.Compare(Artefact x, Artefact y)
        {
            return CompareProject(x, y);
        }
        #endregion

        #region IUri Members
        ///<summary>
        /// Gets or sets the command for the current <see cref="Artefact"/> object.
        ///</summary>
        public string Command { get; set; }

        ///<summary>
        /// Gets or sets RootType for the current <see cref="Artefact"/> object.
        ///</summary>
        public string RootType { get; set; }

        ///<summary>
        /// Gets or sets Root name (or logical name) for the current <see cref="Artefact"/> object.
        ///</summary>
        public string RootName { get; set; }

        ///<summary>
        /// Gets or sets type for the current <see cref="Artefact"/> object. 
        ///</summary>
        public string Type { get; set; }

        ///<summary>
        /// Gets or sets identifier for the current <see cref="Artefact"/> object.
        ///</summary>
        public string Id { get; set; }

        ///<summary>
        ///  Gets parameters (not encoded) for the current <see cref="Artefact"/> object.
        ///</summary>
        ///<returns>
        /// parameters (not encoded) for the current <see cref="Artefact"/> object.
        /// <para>
        /// <example>
        /// viewName=10. Document view
        /// </example>
        /// </para>
        /// </returns>
        public string GetParametersLabel()
        {
            if (this._parameters == null || !this._parameters.HasKeys())
            {
                return string.Empty;
            }

            StringBuilder returnedValue = new StringBuilder();

            bool addingParamSeparator = false;
            string[] keys = (string[])this._parameters.AllKeys.Clone();
            Array.Sort(keys);
            foreach (string key in keys)
            {
                if (addingParamSeparator)
                {
                    returnedValue.Append(", ");
                }
                else
                {
                    addingParamSeparator = true;
                }
                returnedValue.Append(key).Append("=").Append(this._parameters.Get(key));
            }
            return returnedValue.ToString();
        }

        ///<summary>
        /// Indicates whether the current <see cref="Artefact"/> object is a project (a root artefact). 
        ///</summary>
        ///<returns><c>true</c> if the current <see cref="Artefact"/> object is a project; otherwise, <c>false</c>.</returns>
        public bool IsProject()
        {
            return string.IsNullOrEmpty(this.Id) && string.IsNullOrEmpty(this.Type);
        }

        ///<summary>
        /// Sets the Uri for the current <see cref="Artefact"/> object.
        ///</summary>
        ///<param name="uri"></param>
        public void SetUri(string uri)
        {
            // orchestra:[<command>:]//<RootType>/<RootName>[/<ObjectType>/<ObjectId>][?<name1>=<value1>[&<name2>=<value2>]…]
            Match m = _RegexUri.Match(uri);
            if (m.Success)
            {
                this.Command = Rfc3986.Decode(m.Groups["command"].Value);
                this.RootType = Rfc3986.Decode(m.Groups["rootType"].Value);
                this.RootName = Rfc3986.Decode(m.Groups["rootName"].Value);
                this.Type = Rfc3986.Decode(m.Groups["objectType"].Value);
                this.Id = Rfc3986.Decode(m.Groups["objectId"].Value);
                this.ParseQueryString(m.Groups["parameters"].Value);
            }
            else
            {
                this.Command = String.Empty;
                this.RootType = String.Empty;
                this.RootName = String.Empty;
                this.Type = String.Empty;
                this.Id = String.Empty;
                this.ClearParameters();
            }
        }

        ///<summary>
        /// Adds new parameter for the current <see cref="Artefact"/> object.
        ///</summary>
        ///<param name="name"></param>
        ///<param name="value"></param>
        ///<returns><c>true</c> if the parameter is added to the current <see cref="Artefact"/> object; otherwise, <c>false</c>. </returns>
        public bool AddParameter(string name, string value)
        {
            try
            {
                if (this._parameters == null)
                {
                    this._parameters = new NameValueCollection();
                }
                this._parameters.Add(name, value);
            }
            catch (Exception)
            {
                return false;
            }
            return true;
        }

        ///<summary>
        /// Determines whether the current <see cref="Artefact"/> object contains the specified parameter. 
        ///</summary>
        ///<param name="name"></param>
        ///<returns><c>true</c> if the current <see cref="Artefact"/> object contains a parameter with the specified name; otherwise, <c>false</c>. </returns>
        public bool ExistParameter(string name)
        {
            if (String.IsNullOrEmpty(name) || (this._parameters == null))
            {
                return false;
            }
            return (this._parameters.Get(name) != null);
        }

        ///<summary>
        /// Clear parameters for the current <see cref="Artefact"/> object.
        ///</summary>
        public void ClearParameters()
        {
            if (this._parameters != null)
            {
                this._parameters.Clear();
            }
        }

        ///<summary>
        /// Sets the Uri for the current <see cref="Artefact"/> object.
        ///</summary>
        ///<param name="rootType">The logical type for the current <see cref="Artefact"/> object.</param>
        ///<param name="rootName">The Logical name for the current <see cref="Artefact"/> object.</param>
        ///<param name="type">The type for the current <see cref="Artefact"/> object.</param>
        ///<param name="id">The identifier for the current <see cref="Artefact"/> object.</param>
        ///<param name="formattedParameters">Parameters for the current <see cref="Artefact"/> object.
        /// <para>
        /// Parameters string must be encoded according RFC 3986.
        /// </para>
        /// <example>
        /// <c>viewName=10%2E%20Document%20view</c>
        /// </example>
        /// </param>
        public void SetUriValues(string rootType, string rootName, string type, string id, string formattedParameters)
        {
            this.RootType = rootType;
            this.RootName = rootName;
            this.Type = type;
            this.Id = id;
            this.ParseQueryString(formattedParameters);
        }

        ///<summary>
        /// Gets parameters (formatted and encoded) for the current <see cref="Artefact"/> object.
        ///</summary>
        public string GetFormattedParameters
        {
            get
            {
                return this.GetQueryString();
            }
        }

        ///<summary>
        /// Retrieve parameter <paramref name="name"/>
        ///</summary>
        ///<param name="name">parameter name to retrieve</param>
        ///<returns>value of parameter <paramref name="name"/>
        /// Raise en error if <paramref name="name"/> doesn't exist</returns>
        public string Parameter(string name)
        {
            return this._parameters[name];
        }

        ///<summary>
        /// Gets Artefact absolute Uri
        ///</summary>
        public string AbsoluteUri
        {
            get
            {
                return this.ToString().Split('?')[0];
            }
        }
        #endregion

        /// <summary>
        /// Compares two objects and returns a value indicating whether one is less than, equal to, or greater than the other.
        /// </summary>
        /// <returns>
        /// Value Condition Less than zerox is less than y.Zerox equals y.Greater than zerox is greater than y.
        /// </returns>
        /// <param name="y">The second object to compare.</param><param name="x">The first object to compare.</param>
        public static int CompareProject(Artefact x, Artefact y)
        {
            return String.CompareOrdinal(x.RootName, y.RootName);
        }

        ///<summary>
        /// Gets query for the current <see cref="Artefact"/> object.
        ///</summary>
        ///<returns>
        /// query for the current <see cref="Artefact"/> object.
        /// </returns>
        public string GetQueryString()
        {
            if (this._parameters == null || !this._parameters.HasKeys())
            {
                return string.Empty;
            }

            StringBuilder returnedValue = new StringBuilder();

            bool addingParamSeparator = false;
            string[] keys = (string[])this._parameters.AllKeys.Clone();
            SensitiveComparer sensitiveComparer = new SensitiveComparer();
            Array.Sort(keys, sensitiveComparer);
            foreach (string key in keys)
            {
                if (addingParamSeparator)
                {
                    returnedValue.Append("&");
                }
                else
                {
                    addingParamSeparator = true;
                }

                string encodedKey = Rfc3986.Encode(key);
                string encodedValue = Rfc3986.Encode(this._parameters.Get(key));
                returnedValue.Append(encodedKey).Append("=").Append(encodedValue);
            }
            return returnedValue.ToString();
        }

        ///<summary>
        /// Parse the query and sets the parameters of the Uri for the current <see cref="Artefact"/> object.
        ///</summary>
        ///<param name="parameters">Parameters for the current <see cref="Artefact"/> object.
        /// <para>
        /// Parameters string must be encoded according RFC 3986.
        /// </para>
        /// <example>
        /// <c>viewName=10%2E%20Document%20view</c>
        /// </example>
        /// </param>
        ///<exception cref="FormatException"></exception>
        public void ParseQueryString(string parameters)
        {
            this.ClearParameters();
            this._parameters = new NameValueCollection();

            if (string.IsNullOrEmpty(parameters))
            {
                return;
            }

            string[] listParams = parameters.Split('&');

            foreach (string s in listParams)
            {
                string[] values = s.Split('=');

                if (values.Length != 2)
                {
                    throw new FormatException(s + " is not a key-value pair");
                }

                string key = Rfc3986.Decode(values[0]);

                if (string.IsNullOrEmpty(key))
                {
                    throw new FormatException("Key cannot be null or empty");
                }

                string value = Rfc3986.Decode(values[1]);

                this._parameters.Add(key, value);
            }
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"/> that represents the current <see cref="Artefact"/> object.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"/> that represents the current <see cref="Artefact"/> object.
        /// </returns>
        public override string ToString()
        {
            string rootType = Rfc3986.Encode(this.RootType);
            string rootName = Rfc3986.Encode(this.RootName);
            StringBuilder uri = new StringBuilder();
            string command = this.Command;

            if (!String.IsNullOrEmpty(command))
            {
                command += ':';
            }

            if (!String.IsNullOrEmpty(this.Type))
            {
                string objectType = Rfc3986.Encode(this.Type);
                string objectId = Rfc3986.Encode(this.Id);
                uri.AppendFormat(UriFormatWithObject, command, rootType, rootName, objectType, objectId);
            }
            else
            {
                uri.AppendFormat(UriFormatWithoutObject, command, rootType, rootName);
            }

            if (this._parameters != null && this._parameters.HasKeys())
            {
                uri.Append("?").Append(this.GetQueryString());
            }

            return uri.ToString();
        }

        internal bool UnderProject(Artefact x)
        {
            return String.CompareOrdinal(this.RootName, x.RootName) == 0 && !x.IsProject();
        }

        #region Nested type: SensitiveComparer
        private sealed class SensitiveComparer : IComparer
        {
            #region IComparer Members
            public int Compare(object x, object y)
            {
                string s1 = ((string)x);
                string s2 = ((string)y);
                int len1 = s1.Length;
                int len2 = s2.Length;
                int n = Math.Min(len1, len2);
                char[] v1 = s1.ToCharArray();
                char[] v2 = s2.ToCharArray();

                int k = 0;
                int lim = n;
                while (k < lim)
                {
                    char c1 = v1[k];
                    char c2 = v2[k];
                    if (c1 != c2)
                    {
                        return c1 - c2;
                    }
                    k++;
                }
                return len1 - len2;
            }
            #endregion
        }
        #endregion
    }
}