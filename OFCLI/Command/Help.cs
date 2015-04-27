// ----------------------------------------------------------------------------------------------------
// File Name: Help.cs
// Project: OrchestraFrameworkCLI
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace OrchestraFrameworkCLI
{
    using System;
    using System.Collections.Generic;
    using System.Reflection;

    internal class Help : Command
    {
        #region Overrides of Command
        internal override void Execute(List<string> arguments)
        {
            Type[] types = Assembly.GetExecutingAssembly().GetTypes();
            foreach (Type type in types)
            {
                if (type.BaseType == typeof(Command))
                {
                    Console.WriteLine(type.Name);
                }
            }
        }
        #endregion
    }
}