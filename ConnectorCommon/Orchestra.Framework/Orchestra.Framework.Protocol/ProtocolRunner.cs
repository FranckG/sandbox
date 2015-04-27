// ----------------------------------------------------------------------------------------------------
// File Name: ProtocolRunner.cs
// Project: Orchestra.Framework.Protocol
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Protocol
{
    using System.Collections.Generic;
    using System.IO;
    using System.Text;
    using System.Windows.Forms;
    using Orchestra.Framework.Client;
    using Orchestra.Framework.Core;

    internal class ProtocolRunner
    {
        private static bool CheckArgs(ICollection<string> args)
        {
            if (args.Count != 1)
            {
                string appName = Path.GetFileName(Application.ExecutablePath);
                StringBuilder message = new StringBuilder(appName);
                message.Append(" <Orchestra_URL>");
                MessageBox.Show(message.ToString(), "Usage", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }
            return true;
        }

        private static void ExecuteRequest(string argument)
        {
            Artefact uriParam = new Artefact { Uri = argument };

            string uri = argument;

            string command = "NAVIGATE";

            if (!string.IsNullOrEmpty(uriParam.Command))
            {
                command = uriParam.Command;
                uri = argument.Replace(string.Format(":{0}:", uriParam.Command), ":");
            }

            OrchestraClient client = new OrchestraClient();

            switch (command)
            {
                case "NAVIGATE":
                    client.navigate(new[] { uri });
                    break;
                case "EXPAND":
                    client.expand(new[] { uri });
                    break;
                case "EXPORT_DOC":
                    client.documentaryExport(new[] { uri });
                    break;
                case "EXPORT_LM":
                    client.lmExport(new[] { uri });
                    break;
                default:
                    client.executeSpecificCommand(command, new[] { uri });
                    break;
            }
        }

        private static void Main(string[] args)
        {
            if (CheckArgs(args))
            {
                ExecuteRequest(args[0]);
            }
        }
    }
}