// ----------------------------------------------------------------------------------------------------
// File Name: ArtifactDescriptionFile.cs
// Project: DoorsConnector
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Connector.Doors
{
    using System;
    using System.IO;
    using System.Xml;
    using Orchestra.Framework.Connector.Doors.Properties;

    internal class ArtifactDescriptionFile
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="ArtifactDescriptionFile"/> class.
        /// </summary>
        public ArtifactDescriptionFile()
        {
            try
            {
                if (string.IsNullOrEmpty(ContextConnector.ConfigurationDirectory))
                {
                    ContextConnector.ConfigurationDirectory =
                        VariableManagerSingleton.Instance.VariableManager.GetVariableConfigurationDirectory();
                }
                string filePath = Path.Combine(
                    ContextConnector.ConfigurationDirectory, @"Framework\Config\artifacts_description_Doors.xml");
                if (!File.Exists(filePath))
                {
                    throw new ApplicationException(string.Format(Resources.E64001_01, filePath));
                }
                XmlDocument xml = new XmlDocument();
                xml.Load(filePath);
                XmlNode node = xml.SelectSingleNode("//objectType[@name='Module']");
                if (node == null)
                {
                    throw new ApplicationException(string.Format(Resources.E64001_02, filePath));
                }
                if (node.Attributes != null)
                {
                    XmlAttribute attribute = node.Attributes["isExpandable"];
                    if (attribute != null)
                    {
                        string isExpandable = attribute.InnerText;
                        this.ExpandView = isExpandable == @"true" ? @"false" : @"true";
                    }
                    else
                    {
                        throw new ApplicationException(
                            string.Format(Resources.E64001_03, filePath));
                    }
                }
            }
            catch (ApplicationException ex)
            {
                ContextConnector.StatusDefinition.WriteStatus(StatusSeverity.ERROR, "", ex.Message, 64001, true, null);
                throw;
            }
            catch (Exception ex)
            {
                ContextConnector.StatusDefinition.WriteStatus(
                    StatusSeverity.ERROR,
                    "",
                    string.Format(Resources.E61006, ex.Message, ex.StackTrace),
                    61006,
                    true,
                    null);
                throw new ApplicationException();
            }
        }

        /// <summary>
        /// Gets if the module is expandable.
        /// </summary>
        public string ExpandView { get; private set; }
    }
}