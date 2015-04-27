using System;
using System.Collections.Specialized;
using System.Text;
using System.Text.RegularExpressions;

namespace Orchestra.Framework.Utilities
{
    /// <summary>
    /// 
    /// </summary>
    public class OrchestraUri
    {

        /// <summary>
        /// 
        /// </summary>
        public OrchestraUri()
        {
        }

        /// <summary>
        /// 
        ///
        /// </summary>
        /// <param name="uri"></param>
        public OrchestraUri(string uri)
        {
            SetUri(uri);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="rootType"></param>
        /// <param name="rootName"></param>
        public OrchestraUri(string rootType, string rootName)
        {
            Init(String.Empty, rootType, rootName, String.Empty, String.Empty);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="rootType"></param>
        /// <param name="rootName"></param>
        /// <param name="objectType"></param>
        /// <param name="objectId"></param>
        public OrchestraUri(string rootType, string rootName, string objectType, string objectId)
        {
            Init(String.Empty, rootType, rootName, objectType, objectId);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="command"></param>
        /// <param name="rootType"></param>
        /// <param name="rootName"></param>
        public OrchestraUri(string command, string rootType, string rootName)
        {
            Init(command, rootType, rootName, String.Empty, String.Empty);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="command"></param>
        /// <param name="rootType"></param>
        /// <param name="rootName"></param>
        /// <param name="objectType"></param>
        /// <param name="objectId"></param>
        public OrchestraUri(string command, string rootType, string rootName, string objectType, string objectId)
        {
            Init(command, rootType, rootName, objectType, objectId);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="command"></param>
        /// <param name="rootType"></param>
        /// <param name="rootName"></param>
        /// <param name="objectType"></param>
        /// <param name="objectId"></param>
        private void Init(string command, string rootType, string rootName, string objectType, string objectId)
        {
            Command = command;
            RootType = rootType;
            RootName = rootName;
            if (String.IsNullOrEmpty(objectType) || String.IsNullOrEmpty(objectId))
            {
                ObjectType = String.Empty;
                ObjectId = String.Empty;
            }
            else
            {
                ObjectType = objectType;
                ObjectId = objectId;
            }
            Parameters = new NameValueCollection();
        }

        /// <summary>
        /// The command part of the URI
        /// </summary>
        public string Command { get; set; }

        ///<summary>
        ///</summary>
        public string RootType { get; set; }

        ///<summary>
        ///</summary>
        public string RootName { get; set; }

        ///<summary>
        ///</summary>
        public string ObjectType { get; set; }

        ///<summary>
        ///</summary>
        public string ObjectId { get; set; }

        ///<summary>
        ///</summary>
        public NameValueCollection Parameters { get; set; }

 




    }
}
