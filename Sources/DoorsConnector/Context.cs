// ----------------------------------------------------------------------------------------------------
// File Name: Context.cs
// Project: DoorsConnector
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Connector.Doors
{
    using Orchestra.Framework.Connector.Doors.Properties;
    using Orchestra.Framework.VariableManager;
    using Environment = Orchestra.Framework.Environment.Doors.Environment;

    internal static class ContextConnector
    {
        internal const string Key = @"ljhdfqyty eruiy_è879\à_à*#kljsodi fy";
        internal static string ConfigurationDirectory;
        internal static StatusDefinition StatusDefinition;
        internal static string ToolName = Resources.ContextConnector_ToolName_Doors;
        internal static Environment Environments;
        internal static Core.Artefact ProcessedArtefact;
    }

    internal sealed class VariableManagerSingleton
    {
        private static readonly VariableManagerSingleton _Instance = new VariableManagerSingleton();

        // Explicit static constructor to tell C# compiler
        // not to mark type as beforefieldinit
        // ReSharper disable EmptyConstructor
        static VariableManagerSingleton() // ReSharper restore EmptyConstructor
        {
        }

        private VariableManagerSingleton()
        {
            this.VariableManager = new Client();
        }

        public Client VariableManager { get; private set; }

        public static VariableManagerSingleton Instance
        {
            get
            {
                return _Instance;
            }
        }
    }
}