namespace Orchestra.Framework.Connector.Common
{
    using System.IO;
    using Orchestra.Framework.Artefact;
    using Orchestra.Framework.Core;

    /// <summary>
    /// Class ContextConnector
    /// </summary>
    public static class ContextConnector
    {
        private static StatusDefinition _statusDefinition = new StatusDefinition();
        private static string _outputDirectory;
        /// <summary>
        /// Gets or sets the diagram directory.
        /// </summary>
        /// <value>The diagram directory.</value>
        public static string DiagramDirectory { get; set; }
        /// <summary>
        /// Gets or sets the diagram directory relative path.
        /// </summary>
        /// <value>The diagram directory relative path.</value>
        public static string DiagramDirectoryRelativePath { get; set; }
        /// <summary>
        /// Gets the gef file.
        /// </summary>
        /// <value>The gef file.</value>
        public static Gef GefFile { get; private set; }
        /// <summary>
        /// Gets or sets the name of the root artefact.
        /// </summary>
        /// <value>The name of the root artefact.</value>
        public static string RootName { get; set; }
        /// <summary>
        /// Gets or sets the type of the root artefact.
        /// </summary>
        /// <value>The type of the root artefact.</value>
        public static string RootType { get; set; }
        /// <summary>
        /// Gets or sets the service.
        /// </summary>
        /// <value>The service.</value>
        public static Constants.ServiceEnum Service { get; set; }
        /// <summary>
        /// Gets or sets the environment id.
        /// </summary>
        /// <value>The environment id.</value>
        public static string EnvironmentId { get; set; }

        /// <summary>
        /// Gets the status definition.
        /// </summary>
        /// <value>The status definition.</value>
        public static StatusDefinition StatusDefinition
        {
            get
            {
                return _statusDefinition;
            }
        }

        /// <summary>
        /// Closes the gef file.
        /// </summary>
        public static void CloseGefFile()
        {
            if (GefFile != null)
            {
                GefFile.CloseOutputFile();
            }
        }

        /// <summary>
        /// Initializes the gef file.
        /// </summary>
        /// <param name="exportFilePath">The export file path.</param>
        public static void InitializeGefFile(string exportFilePath)
        {
            GefFile = new Gef();
            _outputDirectory = GefFile.InitOutputFile(exportFilePath);
        }

        /// <summary>
        /// Updates the connector Context with the specified artefact.
        /// Call this method only if the root artefact changes
        /// </summary>
        /// <param name="rootArtefact">The root artefact.</param>
        public static void Update(Artefact rootArtefact)
        {
            EnvironmentId = rootArtefact.environmentId;
            RootName = rootArtefact.RootName;
            RootType = rootArtefact.RootType;
            if (Service == Constants.ServiceEnum.DocumentaryExport)
            {
                GefFile.ExtraFileDirectory = rootArtefact.RootName;
                DiagramDirectoryRelativePath = GefFile.ExtraFileDirectory;
                DiagramDirectory = _outputDirectory + Path.DirectorySeparatorChar + DiagramDirectoryRelativePath;
            }
        }

        /// <summary>
        /// Re-initialize the context.
        /// </summary>
        public static void ReInitialize()
        {
            _statusDefinition = new StatusDefinition();
            _outputDirectory = null;
            DiagramDirectory = null;
            DiagramDirectoryRelativePath = null;
            GefFile = null;
            RootName = null;
            RootType = null;
            EnvironmentId = null;
            MiscObject = null;
        }

        /// <summary>
        /// Gets or sets an object.
        /// Allow a connector to store a miscelleanous object in this field.
        /// </summary>
        /// <value>The object.</value>
        public static object MiscObject;
    }
}