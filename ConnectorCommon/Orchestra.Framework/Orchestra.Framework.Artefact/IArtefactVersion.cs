// ----------------------------------------------------------------------------------------------------
// File Name: IArtefactVersion.cs
// Project: Orchestra.Framework.Artefact
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Artefact
{
    using System;
    using System.Runtime.InteropServices;

    ///<summary>
    /// COM Interface for Artefact Version Management
    ///</summary>
    [ComVisible(true)]
    [Guid("2CBC5EA9-9EF6-445a-889C-E1D1C15EE1BD")]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface IVersion
    {
        /// <summary>
        /// Computes hash for a string
        /// </summary>
        /// <param name="stringToHash">string to hash</param>
        /// <returns>Returns the hash string for stringToHash</returns>
        [DispId(1)]
        string GetHash(string stringToHash);

        /// <summary>
        /// Compute hash for a file
        /// </summary>
        /// <param name="fileFullPathToHash">Full path to the file to hash</param>
        /// <returns>Returns the hash string of the file</returns>
        [DispId(2)]
        string GetFileHash(string fileFullPathToHash);

        /// <summary>
        /// Obsolete Method Use the service getArtifactsMetadata
        /// </summary>
        /// <param name="fileFullPath"> </param>
        /// <returns> </returns>
        [DispId(3)]
        [Obsolete("Use the service getArtifactsMetadata")]
        string GetFileVersionA(string fileFullPath);

        /// <summary>
        /// Obsolete Method Use the service getArtifactsMetadata
        /// </summary>
        /// <param name="fileFullPath"> </param>
        /// <param name="relativeFilePath"> </param>
        /// <returns> </returns>
        [DispId(4)]
        [Obsolete("Use the service getArtifactsMetadata")]
        string GetFileVersion(string fileFullPath, out string relativeFilePath);

        /// <summary>
        /// Obsolete Method Use the service getArtifactsMetadata
        /// </summary>
        /// <returns> </returns>
        [DispId(5)]
        [Obsolete("Use the service getArtifactsMetadata")]
        string GetRelativeFilePath();
    }
}