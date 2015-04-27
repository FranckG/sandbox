// ----------------------------------------------------------------------------------------------------
// File Name: VariableManagerSingleton.cs
// Project: ConnectorWord
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Connector.Common
{
    using Orchestra.Framework.VariableManager;

    /// <summary>
    /// Singleton instance for the ODM Variable Manager
    /// </summary>
    public sealed class VariableManagerSingleton
    {
        private static readonly VariableManagerSingleton _instance = new VariableManagerSingleton();

        // Explicit static constructor to tell C# compiler
        // not to mark type as beforefieldinit
        // ReSharper disable EmptyConstructor
        /// <summary>
        /// Initializes the <see cref="VariableManagerSingleton"/> class.
        /// </summary>
        static VariableManagerSingleton() // ReSharper restore EmptyConstructor
        {
        }

        /// <summary>
        /// Prevents a default instance of the <see cref="VariableManagerSingleton"/> class from being created.
        /// </summary>
        private VariableManagerSingleton()
        {
            this.VariableManager = new Client();
        }

        /// <summary>
        /// Gets the variable manager.
        /// </summary>
        public Client VariableManager { get; private set; }

        /// <summary>
        /// Gets the instance.
        /// </summary>
        public static VariableManagerSingleton Instance
        {
            get
            {
                return _instance;
            }
        }
    }
}