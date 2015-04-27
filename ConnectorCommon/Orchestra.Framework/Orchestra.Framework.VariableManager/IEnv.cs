// ----------------------------------------------------------------------------------------------------
// File Name: IEnv.cs
// Project: Orchestra.Framework.VariableManager
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.VariableManager
{
    using System;
    using System.Runtime.InteropServices;

    ///<summary>
    /// COM Interface for accessing Orchestra Variables.
    /// <para>
    /// Obsolete
    /// </para>
    ///</summary>
    [ComVisible(true)]
    [Guid("25978ED5-6678-400b-9044-DB0B76D4D7F6")]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    [Obsolete("Use Orchestra.Framework.VariableManager.IVariableManagerClient (Orchestra.VariableManager COM Object)")]
    public interface IEnv
    {
        ///<summary>
        /// Gets the Artefact directories
        ///</summary>
        [DispId(1)]
        String ArtifactsDir { get; }

        ///<summary>
        /// Gets the Configuration directory
        ///</summary>
        [DispId(2)]
        String ConfDir { get; }

        ///<summary>
        /// Gets the Temp directory
        ///</summary>
        [DispId(3)]
        String TempDir { get; }

        ///<summary>
        /// Gets the Shared directory
        ///</summary>
        [DispId(4)]
        String SharedDir { get; }
    }
}