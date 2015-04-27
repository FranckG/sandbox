// ----------------------------------------------------------------------------------------------------
// File Name: Command.cs
// Project: OrchestraFrameworkCLI
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace OrchestraFrameworkCLI
{
    using System;
    using System.Collections.Generic;

    internal abstract class Command
    {
        protected string UsageMessage;

        internal abstract void Execute(List<string> arguments);

        internal void Usage()
        {
            Console.WriteLine(UsageMessage);
        }
    }
}