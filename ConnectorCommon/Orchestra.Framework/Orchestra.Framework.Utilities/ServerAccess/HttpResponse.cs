// ----------------------------------------------------------------------------------------------------
// File Name: HttpResponse.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.ServerAccess
{
    using System.IO;
    using System.Net;
    using Orchestra.Framework.Client;

    internal class HttpResponse
    {
        public readonly OrchestraResponse Response;

        public HttpResponse(WebResponse response)
        {
            Stream st = response.GetResponseStream();
            if (st != null)
            {
                StreamReader sr = new StreamReader(st);

                string s = sr.ReadLine();

                this.Response = new OrchestraResponse();

                if (s != null)
                {
                    string[] pairs = s.Split('&');

                    foreach (string pair in pairs)
                    {
                        string[] keyValue = pair.Split('=');
                        if (keyValue.Length == 2)
                        {
                            this.Response.AddKeyValue(keyValue[0], keyValue[1]);
                        }
                    }
                }
            }
        }
    }
}