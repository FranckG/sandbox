// ----------------------------------------------------------------------------------------------------
// File Name: Exit.cs
// Project: OrchestraFrameworkCLI
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace OrchestraFrameworkCLI
{
    using System;
    using System.Collections.Generic;

    internal class Exit : Command
    {
        #region Overrides of Command
        internal override void Execute(List<string> arguments)
        {
            Console.Write("Bye");
        }
        #endregion
    }
}