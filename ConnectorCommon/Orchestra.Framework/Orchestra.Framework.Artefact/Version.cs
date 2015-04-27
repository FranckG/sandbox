// ----------------------------------------------------------------------------------------------------
// File Name: Version.cs
// Project: Orchestra.Framework.Artefact
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Artefact
{
    using System;
    using System.Reflection;
    using System.Runtime.InteropServices;
    using Orchestra.Framework.Artefact.Properties;

    ///<summary>
    ///  This class implements the IVersion interface and provides static methods for .NET connectors
    ///</summary>
    [ComVisible(true)]
    [Guid("F8E342D9-C83E-44cc-ABB3-E5BC9AEC29EA")]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId("ArtifactVersion.Artifact")]
    public class Version : IVersion
    {
        private string _relativeFilePath;

        #region IVersion Members
        /// <summary>
        ///   Computes hash for a string
        /// </summary>
        /// <param name="stringToHash"> string to hash </param>
        /// <returns> Returns the hash string for stringToHash </returns>
        public string GetHash(string stringToHash)
        {
            return SGetHash(stringToHash);
        }

        /// <summary>
        ///   Compute hash for a file
        /// </summary>
        /// <param name="fileFullPathToHash"> Full path to the file to hash </param>
        /// <returns> Returns the hash string of the file </returns>
        public string GetFileHash(string fileFullPathToHash)
        {
            return SGetFileHash(fileFullPathToHash);
        }

        /// <summary>
        /// Obsolete Method Use the service getArtifactsMetadata
        /// </summary>
        /// <param name="fileFullPath"> </param>
        /// <param name="relativeFilePath"> </param>
        /// <returns> </returns>
        [Obsolete("Use the service getArtifactsMetadata")]
        public string GetFileVersion(string fileFullPath, out string relativeFilePath)
        {
            relativeFilePath = string.Empty;
            return string.Empty;
        }

        /// <summary>
        /// Obsolete Method Use the service getArtifactsMetadata
        /// </summary>
        /// <param name="fileFullPath"> </param>
        /// <returns> </returns>
        [Obsolete("Use the service getArtifactsMetadata")]
        public string GetFileVersionA(string fileFullPath)
        {
            return string.Empty;
        }

        /// <summary>
        /// Obsolete Method Use the service getArtifactsMetadata
        /// </summary>
        /// <returns> </returns>
        [Obsolete("Use the service getArtifactsMetadata")]
        public string GetRelativeFilePath()
        {
            return string.Empty;
        }
        #endregion

        /// <summary>
        ///   Give the file version. If the file is under configuration file management, return the configuration file management version If the file is under the file system, return the modified time
        /// </summary>
        /// <param name="fileFullPath"> Full path to the file </param>
        /// <returns> Returns the file version </returns>
        [Obsolete("Use the service getArtifactsMetadata")]
        public string GetFileVersion(string fileFullPath)
        {
            return SGetFileVersion(fileFullPath, out this._relativeFilePath);
        }

        ///<summary>
        ///  Compute hash for a file
        ///</summary>
        ///<param name="fileFullPathToHash"> Full path to the file to hash </param>
        ///<returns> Returns the hash string of the file </returns>
        public static string SGetFileHash(string fileFullPathToHash)
        {
            string result = string.Empty;
            try
            {
                Type ty = Type.GetTypeFromProgID(Resources.CRCCalculLibrary, true);
                Object oCrc = Activator.CreateInstance(ty);
                Object[] args = new object[] { fileFullPathToHash };
                result = (string)ty.InvokeMember(Resources.GetCRCMethod, BindingFlags.InvokeMethod, null, oCrc, args);
            }
            catch (COMException ex)
            {
                EventLogOrchestra.WriteError(60102, ex.Message, "Orchestra.Framework.Artefact.Version");
            }
            catch (ArgumentException ex)
            {
                EventLogOrchestra.WriteError(60103, ex.Message, "Orchestra.Framework.Artefact.Version");
            }
            return result;
        }

        /// <summary>
        /// Obsolete Method Use the service getArtifactsMetadata
        /// </summary>
        /// <param name="fileFullPath"> </param>
        /// <param name="relativeFilePath"> </param>
        /// <returns> </returns>
        [Obsolete("Use the service getArtifactsMetadata")]
        public static string SGetFileVersion(string fileFullPath, out string relativeFilePath)
        {
            relativeFilePath = string.Empty;
            return string.Empty;
        }

        ///<summary>
        ///  Computes hash for a string
        ///</summary>
        ///<param name="stringToHash"> string to hash </param>
        ///<returns>Returns the hash string for stringToHash </returns>
        ///<example>
        ///  <code>// C#
        ///    // ----------------------------------------------
        ///    string hash;
        /// 
        ///    hash = Version.SGetHash(stringToHash);</code>
        ///</example>
        public static string SGetHash(string stringToHash)
        {
            string result = string.Empty;
            if (stringToHash.Length > 0)
            {
                try
                {
                    Type ty = Type.GetTypeFromProgID(Resources.CRCCalculLibrary, true);
                    Object oCrc = Activator.CreateInstance(ty);
                    Object[] args = new object[] { stringToHash };
                    result =
                        (string)
                        ty.InvokeMember(Resources.GetCRCForAStringMethod, BindingFlags.InvokeMethod, null, oCrc, args);
                }
                catch (COMException ex)
                {
                    EventLogOrchestra.WriteError(60100, ex.Message, "Orchestra.Framework.Artefact.Version");
                }
                catch (ArgumentException ex)
                {
                    EventLogOrchestra.WriteError(60101, ex.Message, "Orchestra.Framework.Artefact.Version");
                }
            }
            return result;
        }
    }
}