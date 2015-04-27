// ----------------------------------------------------------------------------------------------------
// File Name: UriQuery.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Utilities.ServerAccess
{
    using System.Collections.Generic;
    using System.Text;
    using System.Web;
    using Orchestra.Framework.ServerAccess;

    internal class UriQuery
    {
        private readonly Dictionary<string, string> _data = new Dictionary<string, string>();

        public UriQuery(string command, IEnumerable<string> uris)
        {
            this._data.Add(OrchestraHttpConsts.CommandNameKey, command);
            if (uris != null)
            {
                StringBuilder sb = new StringBuilder();
                foreach (string uri in uris)
                {
                    if (sb.Length > 0)
                    {
                        sb.Append("|");
                    }
                    sb.Append(uri);
                }
                this._data.Add(OrchestraHttpConsts.UrisKey, sb.ToString());
            }
        }

        public string CreatePostQuery()
        {
            StringBuilder postQuery = new StringBuilder();

            bool isFirst = true;
            foreach (string k in this._data.Keys)
            {
                if (isFirst)
                {
                    isFirst = false;
                }
                else
                {
                    postQuery.Append("&");
                }
                postQuery.Append(HttpUtility.UrlEncode(k));
                postQuery.Append("=");
                postQuery.Append(HttpUtility.UrlEncode(this._data[k]));
            }
            return postQuery.ToString();
        }
    }
}