// ----------------------------------------------------------------------------------------------------
// File Name: Metadata.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Utilities
{
    using System;
    using System.IO;
    using System.Xml.Serialization;
    using Orchestra.Framework.Client;
    using Orchestra.Framework.Core;

    /// <summary>
    /// </summary>
    public class EnvironmentMetadata
    {
        /// <summary>
        /// </summary>
        /// <param name="uri"> uri of artefact </param>
        public EnvironmentMetadata(string uri)
            : this(new[] { uri })
        {
        }

        /// <summary>
        /// </summary>
        /// <param name="artefact"> artefact </param>
        public EnvironmentMetadata(Artefact artefact)
            : this(artefact.Uri)
        {
        }

        /// <summary>
        /// </summary>
        /// <param name="uris"> </param>
        public EnvironmentMetadata(string[] uris)
        {
            try
            {
                this.Properties = null;
                OrchestraClient client = new OrchestraClient();
                IOrchestraResponse response = client.getArtefactsMetadata(uris);
                string gefFilePath = response.GetGefFileFullPath();
                if (!string.IsNullOrEmpty(gefFilePath))
                {
                    XmlSerializer serializer = new XmlSerializer(typeof(GEF));
                    TextReader reader = new StreamReader(gefFilePath);
                    this.Properties = (GEF)serializer.Deserialize(reader);
                }
            }
            catch (Exception ex)
            {
                throw new ApplicationException(ex.Message, ex);
            }
        }

        /// <summary>
        ///   Gets a List of property of environment.
        /// </summary>
        public GEF Properties { get; private set; }
    }
}