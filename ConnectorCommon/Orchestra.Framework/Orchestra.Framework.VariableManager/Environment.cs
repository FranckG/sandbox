// ----------------------------------------------------------------------------------------------------
// File Name: Environment.cs
// Project: Orchestra.Framework.VariableManager
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.VariableManager
{
    using System;
    using System.Runtime.InteropServices;

    ///<summary>
    ///</summary>
    [ComVisible(true)]
    [Guid("D3F35853-7ABF-43f3-BACE-6B9D959E190A")]
    [ClassInterface(ClassInterfaceType.None)]
    [Obsolete("Use Orchestra.Framework.VariableManager.Client")]
    [ProgId("PapeeteEnv.IEnv")]
    public class Environment : IEnv
    {
        private readonly Client _ofvm;

        ///<summary>
        /// Constructor
        ///</summary>
        public Environment()
        {
            this._ofvm = new Client();
        }

        #region IEnv Members
        ///<summary>
        /// Gets the Artefact directories
        ///</summary>
        public String ArtifactsDir
        {
            get
            {
                return String.Join(";", this._ofvm.GetVariableArtefactPath());
            }
        }

        ///<summary>
        /// Gets the Configuration directory
        ///</summary>
        public String ConfDir
        {
            get
            {
                return this._ofvm.GetVariableConfigurationDirectory();
            }
        }

        ///<summary>
        /// Gets the Temp directory
        ///</summary>
        public String TempDir
        {
            get
            {
                return this._ofvm.GetVariableTemporaryDirectory();
            }
        }

        ///<summary>
        /// Gets the Shared directory
        ///</summary>
        public String SharedDir
        {
            get
            {
                return this._ofvm.GetVariableSharedDirectory();
            }
        }
        #endregion
    }
}