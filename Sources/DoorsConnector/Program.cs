// ----------------------------------------------------------------------------------------------------
// File Name: Program.cs
// Project: RhapsodyConnector
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace DoorsConnector
{
    using System;
    using CSExeComServer;

    internal class Program
    {
        [STAThread]
        private static void Main(string[] args)
        {
            ExeComServer.Instance.Run();
        }
    }
}