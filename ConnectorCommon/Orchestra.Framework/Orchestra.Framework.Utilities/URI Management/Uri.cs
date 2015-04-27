using System;
using System.Runtime.InteropServices;

namespace Orchestra.Framework.Utilities
{
    public class Uri : IUri
    {
        private readonly Artefact _uri = new Artefact();

        #region Public Properties

        ///<summary>
        /// Papeete URI
        ///</summary>
        public String URI
        {
            get
            {
                return _uri.ToString();
            }
            set
            {
                _uri.SetUri(value);
            }
        }

        public void SetUri(string uri)
        {
            _uri.SetUri(uri);
        }

        public void SetUriValues(string rootType, string rootName, string objectType, string objectId, string formattedParameters)
        {
            _uri.RootType = rootType;
            _uri.RootName = rootName;
            _uri.Type = objectType;
            _uri.Id = objectId;
            _uri.ParseQueryString(formattedParameters);
        }

        ///<summary>
        /// Root name (or logical name) property
        ///</summary>
        public string RootName
        {
            get
            {
                return _uri.RootName;
            }
            set
            {
                _uri.RootName = value;
            }
        }

        ///<summary>
        /// RootType property
        ///</summary>
        public string RootType
        {
            get
            {
                return _uri.RootType;
            }
            set
            {
                _uri.RootType = value;
            }
        }

        ///<summary>
        /// Artifact type property
        ///</summary>
        public String ObjectType
        {
            get
            {
                return _uri.ObjectType;
            }
            set
            {
                _uri.ObjectType = value;
            }
        }


        ///<summary>
        /// Artifact tool identifier property
        ///</summary>
        public String ObjectId
        {
            get
            {
                return _uri.ObjectId;
            }
            set
            {
                _uri.ObjectId = value;
            }
        }

        ///<summary>
        /// Artifact parameters
        ///</summary>
        public String GetFormattedParameters
        {
            get
            {
                return _uri.GetQueryString();
            }
        }

        ///<summary>
        ///</summary>
        public void ClearParameters()
        {
            _uri.ClearParameters();
        }

        ///<summary>
        ///</summary>
        ///<param name="name"></param>
        ///<param name="value"></param>
        ///<returns></returns>
        public bool AddParameter(string name, string value)
        {
            return _uri.AddParameter(name, value);
        }

        ///<summary>
        ///</summary>
        ///<param name="name"></param>
        ///<returns></returns>
        public bool ExistParameter(string name)
        {
           return _uri.ExistParameter(name);
        }

        ///<summary>
        /// Retrieve parameter <paramref name="name"/>
        ///</summary>
        ///<param name="name">parameter name to retrieve</param>
        ///<returns>value of parameter <paramref name="name"/>
        /// Raise en error if <paramref name="name"/> doesn't exist</returns>
        public string Parameter(string name)
        {
            return _uri.Parameters[name];
        }

        ///<summary>
        /// Artifact absolute Uri
        ///</summary>
        public string AbsoluteUri
        {
            get
            {
                return _uri.ToString().Split('?')[0];
            }
        }

        #endregion
    }
}
