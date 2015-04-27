// ----------------------------------------------------------------------------------------------------
// File Name: ServerConfigurationFile.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2013. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Utilities
{
    using System;
    using System.IO;
    using System.Xml;

    ///<summary>
    /// Represents the Orchestra Framework configuration File located in 
    /// <para>
    /// %APPDATA%\Orchestra\OrchestraFramework\serverConfParam.xml
    /// </para>
    ///</summary>
    public class ServerConfigurationFile
    {
        private const String ErrorReadingConfigurationFile =
            "Error during reading configuration file\nSee Windows Event log for more information!";

        private const String ModeAttribute = "mode";
        private const String OrchestraName = "Orchestra";
        private const String OrchestraServerProductName = "OrchestraFramework";
        private const String PapeetePortTag = "papeetePort";
        private const String PapeeteServerTag = "papeeteServer";
        private const String ServerConfParamFilename = "serverConfParam.xml";
        private const String ServerPortAttribute = "port";

        private static readonly String _ServerConfParamFilePath =
            Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData) + Path.DirectorySeparatorChar
            + OrchestraName + Path.DirectorySeparatorChar + OrchestraServerProductName + Path.DirectorySeparatorChar
            + ServerConfParamFilename;

        ///<summary>
        /// Initializes a new instance of the ServerConfigurationFile class.
        ///</summary>
        public ServerConfigurationFile()
        {
            XmlDocument document = new XmlDocument();
            try
            {
                document.Load(_ServerConfParamFilePath);
            }
            catch (Exception ex)
            {
                EventLogOrchestra.WriteError(60054, ex.Message, ex.StackTrace);
                throw new ApplicationException(ErrorReadingConfigurationFile);
            }
            XmlNodeList nodeList = document.GetElementsByTagName(PapeeteServerTag);
            if (nodeList.Count == 1)
            {
                try
                {
                    XmlAttributeCollection xmlAttributeCollection = nodeList[0].Attributes;
                    if (xmlAttributeCollection != null)
                    {
                        this.ServerPort = Convert.ToInt32(xmlAttributeCollection[ServerPortAttribute].Value);
                    }
                    else
                    {
                        throw new NullReferenceException(ErrorReadingConfigurationFile);
                    }
                }
                catch (FormatException ex)
                {
                    EventLogOrchestra.WriteError(60050, ex.Message, ex.StackTrace);
                    throw new ApplicationException(ErrorReadingConfigurationFile);
                }
                catch (NullReferenceException ex)
                {
                    EventLogOrchestra.WriteError(60051, ex.Message, ex.StackTrace);
                    throw new ApplicationException(ErrorReadingConfigurationFile);
                }
            }
            else
            {
                EventLogOrchestra.WriteError(
                    60052,
                    "File: " + _ServerConfParamFilePath + " is malformed!",
                    "Orchestra.Framework.Utilities.ServerConfigurationFile");
                throw new ApplicationException(ErrorReadingConfigurationFile);
            }
            nodeList = document.GetElementsByTagName(PapeetePortTag);
            if (nodeList.Count == 1)
            {
                var xmlAttributeCollection = nodeList[0].Attributes;
                if (xmlAttributeCollection != null)
                {
                    this.ServerMode = xmlAttributeCollection[ModeAttribute].Value;
                }
                else
                {
                    throw new ApplicationException(ErrorReadingConfigurationFile);
                }
            }
            else
            {
                EventLogOrchestra.WriteError(
                    60053,
                    "File: " + _ServerConfParamFilePath + " is malformed!",
                    "Orchestra.Framework.Utilities.ServerConfigurationFile");
                throw new ApplicationException(ErrorReadingConfigurationFile);
            }
        }

        ///<summary>
        /// Gets the Orchestra Framework server port.
        ///</summary>
        public int ServerPort { get; private set; }

        ///<summary>
        /// Gets the Orchestra Framework server mode (static or dynamic).
        ///</summary>
        public String ServerMode { get; private set; }
    }
}