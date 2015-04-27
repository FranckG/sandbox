// ----------------------------------------------------------------------------------------------------
// File Name: Variable.cs
// Project: OrchestraFrameworkCLI
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace OrchestraFrameworkCLI
{
    using System;
    using System.Collections.Generic;
    using Orchestra.Framework.VariableManager;

    internal class Variable : Command
    {
        #region Overrides of Command
        internal override void Execute(List<string> arguments)
        {
            if (arguments.Count == 0 || (arguments.Count == 1 && arguments[0].Equals("-help")))
            {
                this.Usage();
            }
            else
            {
                string variablePath = string.Join(" ", arguments);
                Client client = new Client();
                string[] variableValues = client.GetVariable(variablePath);
                if (variableValues != null)
                {
                    Console.WriteLine(string.Join("\n", variableValues));
                }
            }
        }
        #endregion

        public Variable()
        {
            this.UsageMessage = "Variable variable-path";
        }
    }
}