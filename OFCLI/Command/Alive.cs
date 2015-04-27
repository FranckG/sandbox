// ----------------------------------------------------------------------------------------------------
// File Name: Alive.cs
// Project: OrchestraFrameworkCLI
// Copyright (c) Thales, 2010 - 2013. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace OrchestraFrameworkCLI
{
    using System;
    using System.Collections.Generic;
    using System.Diagnostics;
    using System.IO;
    using System.Net;
    using System.Text;
    using Orchestra.Framework.Utilities;

    internal class Alive : Command
    {
        public Alive()
        {
            this.UsageMessage = "Alive [timeout (s)]";
        }

        #region Overrides of Command
        internal override void Execute(List<string> arguments)
        {
            if (arguments.Count > 1 || (arguments.Count == 1 && arguments[0].Equals("-help")))
            {
                this.Usage();
            }
            else
            {
                Stopwatch stopwatch = null;
                try
                {
                    int timeOut = 300;
                    if (arguments.Count == 1)
                    {
                        timeOut = Convert.ToInt32(arguments[0]);
                    }

                    string currentUserName = Environment.UserName;
                    stopwatch = new Stopwatch();
                    stopwatch.Start();
                    using (WebClient client = new WebClient())
                    {
                        while (stopwatch.Elapsed < TimeSpan.FromSeconds(timeOut))
                        {
                            try
                            {
                                int variableManagerPort = (new ServerConfigurationFile()).ServerPort;
                                string connectString = "http://localhost:" + variableManagerPort + "/ping";
                                byte[] readBytes = client.DownloadData(connectString);
                                string response = Encoding.ASCII.GetString(readBytes);
                                if (response.Equals(currentUserName))
                                {
                                    Console.WriteLine("Alive");
                                    return;
                                }
                            }
                            catch (WebException)
                            {
                            }
                            catch (FileNotFoundException)
                            {
                            }
                        }
                        Console.WriteLine("Not alive");
                    }
                }
                catch (Exception ex)
                {
                    this.Usage();
                }
                finally
                {
                    if (stopwatch != null && stopwatch.IsRunning)
                    {
                        stopwatch.Stop();
                    }
                }
            }
        }
        #endregion
    }
}