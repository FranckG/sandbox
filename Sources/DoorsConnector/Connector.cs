// ----------------------------------------------------------------------------------------------------
// File Name: Connector.cs
// Project: DoorsConnector
// Copyright (c) Thales, 2014 - 2014. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Connector.Doors
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Runtime.InteropServices;
    using System.Threading;
    using System.Xml;
    using Orchestra.Framework.Client;
    using Orchestra.Framework.Connector.Doors.DxlCommands;
    using Orchestra.Framework.Connector.Doors.Properties;
    using Orchestra.Framework.Core;
    using Environment = Orchestra.Framework.Environment.Doors.Environment;

    [ComVisible(true)]
    [Guid(ClassId)]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId(ProgId)]
    public partial class Connector : IConnectorDoors
    {
        #region COM Component Registration
        internal const string ClassId = "17D2A027-B7A3-4d70-93C5-141EDFD0BD3B";
        internal const string InterfaceId = "5D4B64CB-79C2-45c6-BE03-DE677CE84387";
        internal const string ProgId = "Orchestra.ConnectorDoors";
        #endregion

        #region Constants
        private const char IdentifierSeparator = '!';
        private const string ModuleType = "Module";
        private const string ViewType = "View";
        private const string FolderType = "Folder";
        private const string RowType = "Row";
        private const string XmlFalse = "false";
        private const string XmlTrue = "true";
        private const string ModuleIdentifierSeparator = ".";

        #region Nested type: Parameter
        private static class Parameter
        {
            public const string ViewName = "viewName";
            public const string KeepOpen = "keepOpen";
            public const string Database = "database";
            public const string OldUri = "oldUri";
            public const string Parameters = "parameters";
            public const string AutoLogin = "autologin";
        }
        #endregion

        #region Nested type: SpecificCommand
        private static class SpecificCommand
        {
            public const string ReinitializeLogin = "reinitializeLogin";
            public const string UriMigration = "UriMigration";
            public const string GetProjects = "getProjects";
            public const string GetArtefactsMetadata = "GetArtifactsMetadataInternal";
            public const string RefreshLinkManagerStatus = "refreshLMStatus";
            public const string MakeBaseline = "makeBaseline";
            public const string GetOutLinkedModules = "GetOutLinkedModules";
        }
        #endregion

        #endregion

        private static readonly Mutex _MyLock = new Mutex(false, "Orchestra.Framework.Connector.Doors");

        #region IConnectorDoors Members
        public string executeSpecificCommand(string context)
        {
            try
            {
                _MyLock.WaitOne();
                ContextConnector.StatusDefinition = new StatusDefinition();
                Context c = ReadContext(context);
                switch (c.type)
                {
                    case SpecificCommand.UriMigration:
                        MigrateUris(c.artefact, c.exportFilePath);
                        break;
                    case SpecificCommand.ReinitializeLogin:
                        Dictionary<string, List<Artefact>> artefacts = Populate(c.artefact, null);
                        foreach (KeyValuePair<string, List<Artefact>> pair in artefacts)
                        {
                            this.ReinitializeDoorsLogin(pair.Key);
                        }
                        break;
                    case SpecificCommand.GetProjects:
                        if (c.artefact != null && c.artefactCount == 1)
                        {
                            ContextConnector.StatusDefinition.WriteStatus(
                                StatusSeverity.OK, c.artefact[0].Uri, "", 0, false, null);
                        }
                        PrepareAndExecuteCommandForDoors(c, new GetProjects(), XmlFalse);
                        break;
                    case SpecificCommand.GetArtefactsMetadata:
                        PrepareAndExecuteCommandForDoors(c, new GetArtefactsMetadata(), XmlFalse);
                        break;
                    case SpecificCommand.RefreshLinkManagerStatus:
                        PrepareAndExecuteCommandForDoors(c, new Navigate(), XmlFalse);
                        break;
                    case SpecificCommand.MakeBaseline:
                        PrepareAndExecuteCommandForDoors(c, new MakeBaseline(), XmlFalse);
                        break;
                    case SpecificCommand.GetOutLinkedModules:
                        PrepareAndExecuteCommandForDoors(c, new GetOutLinkedModules(), XmlFalse);
                        break;
                    default:
                        return NotImplementedInterface(context, c.type);
                }
            }
            catch (ApplicationException applicationException)
            {
                ContextConnector.StatusDefinition.WriteStatus(
                    StatusSeverity.ERROR,
                    "",
                    string.Format(Resources.E64014, applicationException.Message),
                    64014,
                    false,
                    null);
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
            }
            finally
            {
                _MyLock.ReleaseMutex();
            }
            string s = ContextConnector.StatusDefinition.ToString();
            return s;
        }

        public string expand(string context)
        {
            try
            {
                _MyLock.WaitOne();
                ContextConnector.StatusDefinition = new StatusDefinition();
                ArtifactDescriptionFile artifactDescriptionFile = new ArtifactDescriptionFile();
                return PrepareAndExecuteCommandForDoors(context, new Expand(), artifactDescriptionFile.ExpandView);
            }
            finally
            {
                _MyLock.ReleaseMutex();
            }
        }

        public string navigate(string context)
        {
            try
            {
                _MyLock.WaitOne();
                ContextConnector.StatusDefinition = new StatusDefinition();
                return PrepareAndExecuteCommandForDoors(context, new Navigate(), XmlFalse);
            }
            finally
            {
                _MyLock.ReleaseMutex();
            }
        }

        public string search(string context)
        {
            try
            {
                _MyLock.WaitOne();
                ContextConnector.StatusDefinition = new StatusDefinition();
                return PrepareAndExecuteCommandForDoors(context, new Search(), XmlFalse);
            }
            finally
            {
                _MyLock.ReleaseMutex();
            }
        }

        public string create(string context)
        {
            try
            {
                _MyLock.WaitOne();
                ContextConnector.StatusDefinition = new StatusDefinition();
                return NotImplementedInterface(context, @"create");
            }
            finally
            {
                _MyLock.ReleaseMutex();
            }
        }

        public string documentaryExport(string context)
        {
            try
            {
                _MyLock.WaitOne();
                ContextConnector.StatusDefinition = new StatusDefinition();
                return PrepareAndExecuteCommandForDoors(context, new DocumentaryExport(), XmlFalse);
            }
            finally
            {
                _MyLock.ReleaseMutex();
            }
        }

        public string lmExport(string context)
        {
            try
            {
                _MyLock.WaitOne();
                ContextConnector.StatusDefinition = new StatusDefinition();
                return PrepareAndExecuteCommandForDoors(context, new LmExport(), XmlFalse);
            }
            finally
            {
                _MyLock.ReleaseMutex();
            }
        }

        public void ReinitializeDoorsLogin(string doorsDatabase)
        {
            try
            {
                string credentialsId = ContextConnector.ToolName + doorsDatabase + ContextConnector.Key;
                string label = string.Format("{0} ({1})", ContextConnector.ToolName, doorsDatabase);
                OrchestraClient orchestraClient = new OrchestraClient();
                orchestraClient.ResetCredentials(credentialsId);
                orchestraClient.GetCredentials(credentialsId, true, label);
            }
            catch (Exception)
            {
            }
        }
        #endregion

        /// <summary>
        /// Concats the TMP file to result gef file.
        /// </summary>
        /// <param name="tmpFile">The TMP file.</param>
        /// <param name="resultGefFile">The result Gef file.</param>
        private static void ConcatTmpFileToResultGefFile(string tmpFile, string resultGefFile)
        {
            if (tmpFile == null || resultGefFile == null || !File.Exists(tmpFile))
            {
                return;
            }
            if (!File.Exists(resultGefFile))
            {
                // ReSharper disable AssignNullToNotNullAttribute
                if (!Directory.Exists(Path.GetDirectoryName(resultGefFile)))
                {
                    Directory.CreateDirectory(Path.GetDirectoryName(resultGefFile));
                }
                File.Copy(tmpFile, resultGefFile);
                // ReSharper restore AssignNullToNotNullAttribute
            }
            else
            {
                XmlDocument xmlResult = new XmlDocument();
                xmlResult.Load(resultGefFile);
                XmlDocument xmlTmp = new XmlDocument();
                xmlTmp.Load(tmpFile);
                XmlNode nodeResult = xmlResult.SelectSingleNode("//GENERIC_EXPORT_FORMAT");
                XmlNode nodeTmp = xmlTmp.SelectSingleNode("//GENERIC_EXPORT_FORMAT");
                if (nodeTmp != null && nodeResult != null)
                {
                    foreach (
                        XmlNode importedNode in
                            from XmlNode node in nodeTmp.ChildNodes select xmlResult.ImportNode(node, true))
                    {
                        nodeResult.AppendChild(importedNode);
                    }
                }
                xmlResult.Save(resultGefFile);
            }
            try
            {
                File.Delete(tmpFile);
            }
            catch (Exception)
            {
                // DO NOTHING
            }
        }

        /// <summary>
        /// Executes the command for doors.
        /// </summary>
        /// <param name="artefacts">The artefacts.</param>
        /// <param name="pathToGefFile">The path to gef file.</param>
        /// <param name="command">The command.</param>
        /// <param name="expandView">if set to <c>true</c> its allowed to expand view.</param>
        private static void ExecuteCommandForDoors(
            Dictionary<string, List<Artefact>> artefacts, string pathToGefFile, Command command, string expandView)
        {
            if (artefacts.Count == 0)
            {
                ContextConnector.StatusDefinition.WriteStatus(
                    StatusSeverity.ERROR, "", Resources.E64012, 64012, false, null);
            }
            else
            {
                bool firstDatabase = true;
                bool? makeDoorsVisible = null;
                string directoryPath = Path.GetDirectoryName(pathToGefFile);
                if (directoryPath == null)
                {
                    ContextConnector.StatusDefinition.WriteStatus(
                        StatusSeverity.ERROR, "", string.Format(Resources.E64005, pathToGefFile), 64005, false, null);
                    throw new ApplicationException();
                }
                foreach (KeyValuePair<string, List<Artefact>> pair in artefacts)
                {
                    try
                    {
                        ContextConnector.ProcessedArtefact = pair.Value[0];
                    }
                    catch (Exception)
                    {
                        // DO NOTHING
                    }
                    string resultFile = (firstDatabase
                                             ? pathToGefFile
                                             : Path.Combine(directoryPath, Path.GetRandomFileName())) ?? "";
                    command.ExpandView = expandView;
                    command.ResultFilePath = resultFile;
                    command.Artefacts = pair.Value;
                    if (command.EnvironmentCommand)
                    {
                        command.DoorsParameters = new DoorsParameters(
                            pair.Value[0].Parameter(Parameter.Parameters), true) { Database = pair.Key };
                    }
                    else
                    {
                        command.DoorsParameters =
                            new DoorsParameters(
                                ContextConnector.Environments.List[pair.Value[0].environmentId][pair.Key]
                                    .LaunchParameters,
                                true) { Database = pair.Key };
                        command.AutoLogin =
                            ContextConnector.Environments.List[pair.Value[0].environmentId][pair.Key].AutoLogin;
                    }
                    UriFile uriFile = new UriFile(command);
                    command.UriFilePath = uriFile.GenerateFile();

                    if (!command.MakeDoorsVisible)
                    {
                        foreach (Artefact artefact in pair.Value)
                        {
                            if (makeDoorsVisible == null && artefact.ExistParameter(Parameter.KeepOpen))
                            {
                                makeDoorsVisible = artefact.Parameter(Parameter.KeepOpen) == XmlTrue;
                                break;
                            }
                        }
                        if (makeDoorsVisible != null)
                        {
                            command.MakeDoorsVisible = (bool)makeDoorsVisible;
                        }
                    }
                    try
                    {
                        command.Create();
                    }
                    catch (ApplicationException)
                    {
                        throw;
                    }
                    catch (Exception ex)
                    {
                        ContextConnector.StatusDefinition.WriteStatus(
                            StatusSeverity.ERROR,
                            ContextConnector.ProcessedArtefact == null ? "" : ContextConnector.ProcessedArtefact.Uri,
                            string.Format(Resources.E64010, ex.InnerException.Message),
                            64010,
                            false,
                            null);
                        throw new ApplicationException();
                    }
                    command.Execute();
                    if (!firstDatabase)
                    {
                        ConcatTmpFileToResultGefFile(resultFile, pathToGefFile);
                    }
                    else
                    {
                        firstDatabase = false;
                    }
                }
            }
        }

        /// <summary>
        /// Migrates the uris.
        /// </summary>
        /// <param name="artefacts">The artefacts to migrate.</param>
        /// <param name="pathToGefFile">The path to gef file.</param>
        private static void MigrateUris(IEnumerable<Artefact> artefacts, string pathToGefFile)
        {
            if (string.IsNullOrEmpty(pathToGefFile))
            {
                pathToGefFile = Path.GetTempFileName();
            }
            string[] value =
                VariableManagerSingleton.Instance.VariableManager.GetVariable(
                    @"\Orchestra\Connectors\Doors\Use IE PUID as identifier");
            bool useIEPUIDAsIdentifier = value[0].Equals(XmlTrue);
            List<Artefact> migrateToOrchestraModuleIdArtefactsList = new List<Artefact>();
            foreach (Artefact artefact in artefacts)
            {
                ContextConnector.ProcessedArtefact = artefact;
                string oldUri = artefact.Uri;
                try
                {
                    string viewName = artefact.ExistParameter(Parameter.ViewName)
                                          ? artefact.Parameter(Parameter.ViewName)
                                          : null;
                    string[] ids;
                    artefact.ClearParameters();
                    switch (artefact.Type)
                    {
                        case ViewType:
                        case ModuleType:
                        case "":
                            break;
                        case RowType:
                            string localId = artefact.Id;
                            ids = localId.Split(IdentifierSeparator);
                            string replacementString;
                            string localViewName = null;
                            string moduleId = ids[0];
                            if (moduleId == ids[1])
                            {
                                replacementString = moduleId + IdentifierSeparator;
                            }
                            else
                            {
                                replacementString = moduleId + IdentifierSeparator + ids[1] + IdentifierSeparator;
                                localViewName = ids[1];
                            }
                            localId = localId.Replace(replacementString, "");
                            ids = localId.Split(IdentifierSeparator);
                            artefact.Id = moduleId + IdentifierSeparator + ids.First() + IdentifierSeparator
                                          + ids.Last();
                            if (!string.IsNullOrEmpty(localViewName))
                            {
                                artefact.AddParameter(Parameter.ViewName, localViewName);
                            }
                            break;
                        default:
                            ids = artefact.Id.Split(IdentifierSeparator);
                            if (ids.Length == 3)
                            {
                                artefact.AddParameter(Parameter.ViewName, ids[1]);
                                artefact.Id = ids[0] + IdentifierSeparator + ids[2];
                            }
                            break;
                    }
                    if (viewName != null && !artefact.ExistParameter(Parameter.ViewName))
                    {
                        artefact.AddParameter(Parameter.ViewName, viewName);
                    }
                    ids = artefact.Id.Split(IdentifierSeparator);
                    if (!ids[0].Contains(ModuleIdentifierSeparator) && !artefact.IsProject()
                        && artefact.Type != FolderType)
                    {
                        artefact.AddParameter(Parameter.OldUri, oldUri);
                        if (artefact.environmentProperties == null || !artefact.environmentProperties.Any())
                        {
                            ContextConnector.StatusDefinition.WriteStatus(
                                StatusSeverity.ERROR,
                                oldUri,
                                string.Format(Resources.E64008, artefact.RootName),
                                64008,
                                false,
                                ContextConnector.StatusDefinition.status);
                        }
                        else
                        {
                            migrateToOrchestraModuleIdArtefactsList.Add(artefact);
                        }
                    }
                    else
                    {
                        int doorsAbsoluteNumber;
                        if (useIEPUIDAsIdentifier && ids.Length > 1 && int.TryParse(ids[1], out doorsAbsoluteNumber))
                        {
                            artefact.AddParameter(Parameter.OldUri, oldUri);
                            migrateToOrchestraModuleIdArtefactsList.Add(artefact);
                        }
                        else if (!useIEPUIDAsIdentifier && ids.Length > 1
                                 && !int.TryParse(ids[1], out doorsAbsoluteNumber))
                        {
                            artefact.AddParameter(Parameter.OldUri, oldUri);
                            migrateToOrchestraModuleIdArtefactsList.Add(artefact);
                        }
                        else
                        {
                            ContextConnector.StatusDefinition.WriteStatus(
                                StatusSeverity.OK,
                                oldUri,
                                artefact.Uri,
                                0,
                                false,
                                ContextConnector.StatusDefinition.status);
                        }
                    }
                }
                catch (Exception ex)
                {
                    ContextConnector.StatusDefinition.WriteStatus(
                        StatusSeverity.ERROR, oldUri, ex.Message, 64013, false, ContextConnector.StatusDefinition.status);
                }
            }
            if (migrateToOrchestraModuleIdArtefactsList.Count > 0)
            {
                Dictionary<string, List<Artefact>> sortedListOfArtefacts =
                    Populate(migrateToOrchestraModuleIdArtefactsList, null);
                ExecuteCommandForDoors(sortedListOfArtefacts, pathToGefFile, new Migrate(), XmlFalse);
            }
        }

        /// <summary>
        /// Generates a status for not implemented interface.
        /// </summary>
        /// <param name="context">The context string.</param>
        /// <param name="command">The command.</param>
        /// <returns>the status</returns>
        private static string NotImplementedInterface(string context, string command)
        {
            ContextDefinition c = new ContextDefinition();
            string message;
            if (command != null)
            {
                message = string.Format(Resources.E64000, command);
            }
            else
            {
                message = Resources.E64000_01;
                try
                {
                    c.Load(context);
                    if (c.context.artefact != null && c.context.artefact.Length > 0)
                    {
                        message = string.Format(Resources.E64000, c.context.artefact[0].Command);
                    }
                }
                catch (Exception)
                {
                    //DO NOTHING"
                }
            }
            ContextConnector.StatusDefinition.WriteStatus(StatusSeverity.ERROR, "", message, 64000, true, null);
            return ContextConnector.StatusDefinition.ToString();
        }

        private static Dictionary<string, List<Artefact>> Populate(IEnumerable<Artefact> a, Command command)
        {
            Dictionary<string, List<Artefact>> artefacts = new Dictionary<string, List<Artefact>>();
            ContextConnector.Environments = new Environment();
            foreach (Artefact artefact in a)
            {
                ContextConnector.ProcessedArtefact = artefact;
                if (command != null && command.EnvironmentCommand)
                {
                    if (!artefact.ExistParameter(Parameter.Database))
                    {
                        ContextConnector.StatusDefinition.WriteStatus(
                            StatusSeverity.WARNING,
                            artefact.Uri,
                            Resources.W64085,
                            64085,
                            true,
                            ContextConnector.StatusDefinition.status);
                        continue;
                    }
                    if (!artefact.ExistParameter(Parameter.AutoLogin))
                    {
                        ContextConnector.StatusDefinition.WriteStatus(
                            StatusSeverity.WARNING,
                            artefact.Uri,
                            Resources.W64086,
                            64086,
                            true,
                            ContextConnector.StatusDefinition.status);
                        continue;
                    }
                    string database = artefact.Parameter(Parameter.Database);
                    command.AutoLogin = artefact.Parameter(Parameter.AutoLogin).Equals(XmlTrue);

                    if (!artefacts.ContainsKey(database))
                    {
                        artefacts.Add(database, new List<Artefact>());
                    }
                    artefacts[database].Add(artefact);
                }
                else if (string.IsNullOrEmpty(artefact.RootPhysicalPath))
                {
                    if (artefact.environmentId != null)
                    {
                        if (!ContextConnector.Environments.IsExists(artefact.environmentId))
                        {
                            ContextConnector.Environments.Add(artefact.environmentId, artefact.environmentProperties);
                        }
                       ContextConnector.Environments.PrepareArtefactForDxl(artefact, ref artefacts);
                    }
                    else
                    {
                        ContextConnector.StatusDefinition.WriteStatus(
                            StatusSeverity.ERROR,
                            artefact.Uri,
                            string.Format(Resources.E64004, artefact.RootName),
                            64004,
                            true,
                            ContextConnector.StatusDefinition.status);
                    }
                }
                else
                {
                    ContextConnector.StatusDefinition.WriteStatus(
                        StatusSeverity.ERROR,
                        artefact.Uri,
                        string.Format(Resources.E64086, artefact.RootName),
                        64086,
                        true,
                        ContextConnector.StatusDefinition.status);
                }
            }
            return artefacts;
        }

        /// <summary>
        /// Prepares the and execute command for doors.
        /// </summary>
        /// <param name="context">The context.</param>
        /// <param name="command">The command.</param>
        /// <param name="expandView">if set to <c>true</c> its allowed to expand view.</param>
        /// <returns>the status</returns>
        private static string PrepareAndExecuteCommandForDoors(Context context, Command command, string expandView)
        {
            try
            {
                string pathToGefFile = context.exportFilePath;
                IEnumerable<Artefact> inputList;
                if (command.EnvironmentCommand)
                {
                    inputList = context.artefact;
                }
                else
                {
                    inputList = VerifyArtefacts(context.artefact);
                }
                if (!string.IsNullOrEmpty(context.keepOpen) && context.keepOpen.Equals(XmlTrue))
                {
                    command.MakeDoorsVisible = true;
                }
                Dictionary<string, List<Artefact>> artefacts = Populate(inputList, command);
                ExecuteCommandForDoors(artefacts, pathToGefFile, command, expandView);
            }
            catch (ApplicationException)
            {
                //DO NOTHING
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
            }
            string s = ContextConnector.StatusDefinition.ToString();
            return s;
        }

        /// <summary>
        /// Prepares the and execute command for doors.
        /// </summary>
        /// <param name="context">The context.</param>
        /// <param name="command">The command.</param>
        /// <param name="expandView">if set to <c>true</c> its allowed to expand view.</param>
        /// <returns>the status</returns>
        private static string PrepareAndExecuteCommandForDoors(string context, Command command, string expandView)
        {
            try
            {
                Context c = ReadContext(context);
                return PrepareAndExecuteCommandForDoors(c, command, expandView);
            }
            catch (ApplicationException)
            {
                //DO NOTHING
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
            }
            string s = ContextConnector.StatusDefinition.ToString();
            return s;
        }

        /// <summary>
        /// Reads the context.
        /// </summary>
        /// <param name="context">The context string.</param>
        /// <returns>A context object</returns>
        private static Context ReadContext(string context)
        {
            ContextDefinition contextDefinition = new ContextDefinition();
            try
            {
                if (contextDefinition.Load(context))
                {
                    return contextDefinition.context;
                }
            }
            catch (Exception)
            {
            }
            ContextConnector.StatusDefinition.WriteStatus(StatusSeverity.ERROR, "", Resources.E61012, 61012, true, null);
            throw new ApplicationException();
        }

        /// <summary>
        /// Verifies the artefacts.
        /// </summary>
        /// <param name="artefacts">The artefacts.</param>
        /// <returns>A list of valid artefacts</returns>
        private static IEnumerable<Artefact> VerifyArtefacts(Artefact[] artefacts)
        {
            if (artefacts != null)
            {
                List<Artefact> list = new List<Artefact>(artefacts);
                bool removingOneArtefact = false;
                foreach (Artefact artefact in artefacts)
                {
                    ContextConnector.ProcessedArtefact = artefact;
                    bool removeIt = false;
                    if (artefact.Type == ViewType)
                    {
                        string id = artefact.Id.Split(new[] { IdentifierSeparator })[0];
                        if (!id.Contains(ModuleIdentifierSeparator))
                        {
                            removeIt = true;
                        }
                    }
                    else if (artefact.Type != FolderType && !string.IsNullOrEmpty(artefact.Type)
                             && !artefact.Id.Contains(ModuleIdentifierSeparator))
                    {
                        removeIt = true;
                    }
                    if (removeIt)
                    {
                        ContextConnector.StatusDefinition.WriteStatus(
                            StatusSeverity.ERROR,
                            artefact.Uri,
                            string.Format(Resources.E70000, artefact.Uri),
                            70000,
                            true,
                            ContextConnector.StatusDefinition.status);
                        list.Remove(artefact);
                        removingOneArtefact = true;
                    }
                }
                if (list.Count == 0)
                {
                    throw new ApplicationException();
                }
                if (removingOneArtefact)
                {
                    ContextConnector.StatusDefinition.status.severity = StatusSeverity.WARNING;
                }
                return list;
            }
            return null;
        }
    }
}