// ----------------------------------------------------------------------------------------------------
// File Name: OrchestraHTTPConsts.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.ServerAccess
{
    ///<summary>
    /// Constants used the http server of the Orchestra Framework
    ///</summary>
    public static class OrchestraHttpConsts
    {
        internal const string CommandNameKey = "command";
        internal const string UrisKey = "uris";
        internal const string FileSystemTool = "FILESYSTEM";

        ///<summary>
        /// key for the response file path
        ///</summary>
        public const string ResponseFilePath = "responseFilePath";

        /// <summary>
        /// key for error code
        /// </summary>
        public const string ErrorCode = "errorCode";

        /// <summary>
        /// key for the message
        /// </summary>
        public const string Message = "message";

        #region Nested type: Command
        ///<summary>
        /// Lists of commands
        ///</summary>
        public static class Command
        {
            /// <summary>
            /// Search service keep open URI parameter.
            /// </summary>
            public const string UriParameterSearchKeepOpen = "keepOpen";

            /// <summary>
            /// Default separator to use inside URI parameters, when there is a need to throw several values within a single parameter.
            /// </summary>
            public const string DefaultParameterSeparator = "|$|";

            /// <summary>
            /// Search service relativePath URI parameter.
            /// </summary>
            public const string UriParameterSearchRelativePath = "relativePath";

            /// <summary>
            /// Search command.
            /// </summary>
            public const string Search = "Search";

            /// <summary>
            /// Expand artefact set command.
            /// </summary>
            public const string ExpandArtefactSet = "ExpandArtifactSet";

            ///<summary>
            /// Create command
            ///</summary>
            public const string Create = "Create";

            ///<summary>
            /// Expand Command
            ///</summary>
            public const string Expand = "Expand";

            ///<summary>
            /// Documentary Export command
            ///</summary>
            public const string ExportDoc = "ExportDoc";

            ///<summary>
            /// Link Manager Command
            ///</summary>
            public const string ExportLm = "ExportLM";

            ///<summary>
            /// Get root artefacts command
            ///</summary>
            public const string GetRootArtifacts = "GetRootArtifacts";

            ///<summary>
            /// Navigate command
            ///</summary>
            public const string Navigate = "Navigate";

            ///<summary>
            /// Transcription (logical to physical or physical to logical) command
            ///</summary>
            public const string Transcription = "Transcription";

            /// <summary>
            /// Get artefact metadata command
            /// </summary>
            public const string GetArtefactsMetadata = "GetArtifactsMetadata";

            /// <summary>
            /// Change context command
            /// </summary>
            public const string ChangeContext = "ChangeContext";

            /// <summary>
            /// Change ODM mode command
            /// </summary>
            public const string ChangeMode = "ChangeMode";

            /// <summary>
            /// Uri migration command.
            /// </summary>
            public const string UriMigration = "UriMigration";

            /// <summary>
            /// Get credentials command
            /// </summary>
            public const string GetCredentials = "GetCredentials";
            
            /// <summary>
            /// Reset credentials command
            /// </summary>
            public const string ResetCredentials = "ResetCredentials";

        }
        #endregion
    }
}