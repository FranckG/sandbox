// ----------------------------------------------------------------------------------------------------
// File Name: Version.cs
// Project: OrchestraFrameworkCLI
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace OrchestraFrameworkCLI
{
    using System;
    using System.Collections.Generic;
    using System.Reflection;

    internal class Version : Command
    {
        internal override void Execute(List<string> arguments)
        {
            Console.WriteLine(Assembly.GetExecutingAssembly().GetName().Version);
        }
    }
}