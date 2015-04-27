// ----------------------------------------------------------------------------------------------------
// File Name: ClientRequest.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.ServerAccess
{
    using System;
    using System.IO;
    using System.Net;
    using System.Text;
    using Orchestra.Framework.Utilities;
    using Orchestra.Framework.Utilities.ServerAccess;
    using Version = System.Version;

    internal class ClientRequest
    {
        private readonly UriQuery _query;

        public ClientRequest(UriQuery query)
        {
            this._query = query;
        }

        public HttpResponse SendPostRequest()
        {
            ServerConfigurationFile serverConf = new ServerConfigurationFile();

            string requestContent = this._query.CreatePostQuery();

            Uri uri = new Uri("http://localhost:" + serverConf.ServerPort + "/execute");

            HttpWebRequest request = (HttpWebRequest)WebRequest.CreateDefault(uri);
            request.Method = "POST";
            request.ContentLength = requestContent.Length;
            request.ContentType = "application/x-www-form-urlencoded";
            request.ProtocolVersion = new Version(1, 0);
            request.Headers.Add("Orchestra", "orchestra");
            request.Timeout = 24 * 24 * 60 * 60 * 1000; // 24j de timeout
            request.KeepAlive = false;

            try
            {
                StreamWriter s = new StreamWriter(request.GetRequestStream(), Encoding.ASCII);
                s.Write(requestContent);
                s.Close();
            }
            catch (Exception)
            {
                throw new ApplicationException(
                    "No Framework could be detected.\nMake sure the Orchestra Framework is running");
            }

            HttpWebResponse response;
            try
            {
                response = (HttpWebResponse)request.GetResponse();
            }
            catch (WebException we)
            {
                response = (HttpWebResponse)we.Response;
            }

            HttpResponse resp = new HttpResponse(response);

            return resp;
        }
    }
}