// ----------------------------------------------------------------------------------------------------
// File Name: DxlCommand.cs
// Project: DoorsConnector
// Copyright (c) Thales, 2010 - 2014. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Connector.Doors.DxlCommands
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel;
    using System.Diagnostics;
    using System.IO;
    using System.Linq;
    using System.Reflection;
    using System.Runtime.InteropServices;
    using System.Security.Permissions;
    using System.Security.Principal;
    using System.Text;
    using DoorsTypeLibrary;
    using Orchestra.Framework.Client;
    using Orchestra.Framework.Connector.Doors.Properties;
    using Orchestra.Framework.Core;
    using Orchestra.Framework.Utilities;
    using Orchestra.Framework.Utilities.Security;

    internal abstract class CommandFromEnvironment : Command
    {
        /// <summary>
        /// Gets a value indicating whether [environment command].
        /// </summary>
        /// <value><c>true</c> if [environment command]; otherwise, <c>false</c>.</value>
        public override bool EnvironmentCommand
        {
            get
            {
                return true;
            }
        }
    }

    internal abstract class CommandFromFramework : Command
    {
        /// <summary>
        /// Gets a value indicating whether [environment command].
        /// </summary>
        /// <value><c>true</c> if [environment command]; otherwise, <c>false</c>.</value>
        public override bool EnvironmentCommand
        {
            get
            {
                return false;
            }
        }
    }

    internal abstract class Command
    {
        private const string DoorsCurrentExePath = @"\Orchestra installation\COTS\Doors\Current\Executable";
        private const string DxlExtension = "dxl";
        private const string DxlExtensionNameForInclude = ".inc";
        private const string DxlIncludeSyntax = "#include <{0}>";
        private const string PragmaLine = "pragma runLim, 0";
        private const uint TokenQuery = 0x0008;
        private const string DxlMethodArguments = "{0}(\"{1}\",\"{2}\",\"{3}\",\"{4}\",{5},{6})";

        private readonly string _pathOfConnectorAddin =
            Path.Combine(new Uri(Path.GetDirectoryName(Assembly.GetExecutingAssembly().CodeBase)).LocalPath, "Doors");

        private List<Artefact> _artefacts;
        private string _dxlFilePath;
        private string _pathToDoorsExecutable;
        private string _pathToStatusFile;

        /// <summary>
        /// Gets or sets the doors parameters.
        /// </summary>
        /// <value>The doors parameters.</value>
        public DoorsParameters DoorsParameters { get; set; }

        public virtual bool AutoLogin { get; set; }

        /// <summary>
        /// Gets or sets the artefacts.
        /// </summary>
        /// <value>
        /// The artefacts.
        /// </value>
        public List<Artefact> Artefacts
        {
            get
            {
                return this._artefacts;
            }
            set
            {
                this._artefacts = value;
                if (this.EnvironmentCommand && value != null && value.Count > 0)
                {
                    if (value[0].ExistParameter(@"DoorsExecutable"))
                    {
                        this._pathToDoorsExecutable = value[0].Parameter(@"DoorsExecutable");
                    }
                    else
                    {
                        ContextConnector.StatusDefinition.WriteStatus(
                            StatusSeverity.ERROR, "", Resources.E64084, 64084, true, null);
                        this.GenerateSubError();
                        throw new ApplicationException();
                    }
                }
            }
        }

        /// <summary>
        /// Gets or sets the odm variables needed.
        /// </summary>
        /// <value>The odm variables needed.</value>
        public List<OdmVariable> OdmVariablesNeeded { get; protected set; }

        /// <summary>
        /// Gets or sets the result file path.
        /// </summary>
        /// <value>
        /// The result file path.
        /// </value>
        public string ResultFilePath { get; set; }

        /// <summary>
        /// Gets or sets the URI file path.
        /// </summary>
        /// <value>
        /// The URI file path.
        /// </value>
        public string UriFilePath { get; set; }

        /// <summary>
        /// Gets or sets the expand view.
        /// </summary>
        /// <value>
        /// The expand view.
        /// </value>
        public string ExpandView { get; set; }

        /// <summary>
        /// Gets the include.
        /// </summary>
        protected abstract string Include { get; }

        /// <summary>
        /// Gets the name of the method.
        /// </summary>
        /// <value>
        /// The name of the method.
        /// </value>
        protected abstract string MethodName { get; }

        /// <summary>
        /// Gets or sets a value indicating whether [make doors visible].
        /// </summary>
        /// <value>
        ///   <c>true</c> if [make doors visible]; otherwise, <c>false</c>.
        /// </value>
        public virtual bool MakeDoorsVisible { get; set; }

        /// <summary>
        /// Gets a value indicating whether [environment command].
        /// </summary>
        /// <value><c>true</c> if [environment command]; otherwise, <c>false</c>.</value>
        public abstract bool EnvironmentCommand { get; }

        /// <summary>
        /// Creates the Doors command.
        /// </summary>
        public void Create()
        {
            string pathToTempFileForStatus = Path.GetTempFileName();
            this._pathToStatusFile = Path.ChangeExtension(pathToTempFileForStatus, "status");
            try
            {
                File.Delete(pathToTempFileForStatus);
            }
            catch (Exception)
            {
                //DO NOTHING
            }
            this.FindDoorsExecutable();
            try
            {
                string temp = this.Include + DxlExtensionNameForInclude;
                temp = temp.Replace(@"\", @"/");
                temp = temp.Replace(@"//", @"/");
                StringBuilder commandDxl = new StringBuilder(String.Format(DxlIncludeSyntax, temp));
                commandDxl.Append(Environment.NewLine);
                commandDxl.AppendLine(PragmaLine);
                this._dxlFilePath = Path.GetTempFileName();
                try
                {
                    File.Delete(this._dxlFilePath);
                }
                catch (Exception)
                {
                    //DO NOTHING
                }
                this._dxlFilePath = Path.ChangeExtension(this._dxlFilePath, DxlExtension);
                string isLmExport = this is LmExport ? @"true" : @"false";
                commandDxl.AppendFormat(
                    DxlMethodArguments,
                    this.MethodName,
                    this.UriFilePath.Replace(@"\", @"\\"),
                    this.ResultFilePath.Replace(@"\", @"\\"),
                    this._pathToStatusFile.Replace(@"\", @"\\"),
                    this._dxlFilePath.Replace(@"\", @"\\"),
                    this.ExpandView,
                    isLmExport);
                StreamWriter file = new StreamWriter(this._dxlFilePath);
                file.Write(commandDxl.ToString());
                file.Close();
            }
            catch (Exception ex)
            {
                throw new ApplicationException(String.Empty, ex);
            }
        }

        /// <summary>
        /// Executes the command on the specified database doors.
        /// </summary>
        internal void Execute()
        {
            try
            {
                DoorsDXL oDoorsDxl = this.IsRunningDoorsOnDatabase();
                if (oDoorsDxl != null)
                {
                    if (this.RunDxlOnVisibleDoorsInstance(oDoorsDxl))
                    {
                        return;
                    }
                }
                StringBuilder options = new StringBuilder(this.DoorsParameters.ToString());

                if (!this.AutoLogin)
                {
                    OrchestraClient orchestraClient = new OrchestraClient();
                    string credentialsId = ContextConnector.ToolName + this.DoorsParameters.Database + ContextConnector.Key;
                    string label = string.Format("{0} ({1})", ContextConnector.ToolName, this.DoorsParameters.Database);
                    ICredentialsResponse credentialsResponse = orchestraClient.GetCredentials(credentialsId, true, label);
                    CredentialsUIStatus credentialsUIStatus = credentialsResponse.CredentialsUIStatus();
                    if (credentialsResponse.Error())
                    {
                    }
                    if (credentialsUIStatus == CredentialsUIStatus.DISPLAYED_AND_CANCEL
                        || credentialsUIStatus == CredentialsUIStatus.DISPLAYED_AND_CLOSE)
                    {
                        ContextConnector.StatusDefinition.WriteStatus(
                            StatusSeverity.ERROR, "", Resources.W64009, 64009, true, null);
                        this.GenerateSubError();
                        throw new ApplicationException();
                    }
                    ICredentials credentials = credentialsResponse.Credentials();
                    try
                    {
                        string userName = credentials.Login();
                        string passwd = credentials.Password();
                        options.Append(@" -u """ + userName + @""" -P """ + passwd + @"""");
                    }
                    catch (Exception e)
                    {
                        ContextConnector.StatusDefinition.WriteStatus(
                            StatusSeverity.ERROR, "", e.Message, 64009, true, null);
                        this.GenerateSubError();
                        throw new ApplicationException();
                    }
                }
                if (!this.MakeDoorsVisible)
                {
                    options.Append(@" -b """ + this._dxlFilePath + @"""");
                }

                if (File.Exists(this._pathToDoorsExecutable))
                {
                    Process doorsProcess = new Process
                        {
                            StartInfo = { FileName = this._pathToDoorsExecutable, Arguments = options.ToString() }
                        };
                    doorsProcess.Start();
                    bool doorsProcessExistedAbnormally = false;
                    if (!doorsProcess.HasExited)
                    {
                        if (doorsProcess.WaitForInputIdle(200000))
                        {
                            if (!this.MakeDoorsVisible)
                            {
                                doorsProcess.WaitForExit();
                                this.ParseReturnedStatusFile();
                            }
                            else
                            {
                                oDoorsDxl = this.IsRunningDoorsOnDatabase();
                                if (oDoorsDxl != null)
                                {
                                    this.RunDxlOnVisibleDoorsInstance(oDoorsDxl);
                                }
                                else
                                {
                                    doorsProcessExistedAbnormally = true;
                                }
                            }
                        }
                        else
                        {
                            doorsProcessExistedAbnormally = true;
                        }
                    }
                    else
                    {
                        doorsProcessExistedAbnormally = true;
                    }
                    if (doorsProcessExistedAbnormally)
                    {
                        ContextConnector.StatusDefinition.WriteStatus(
                            StatusSeverity.ERROR, "", Resources.E64081, 64081, true, null);
                        this.GenerateSubError();
                        throw new ApplicationException();
                    }
                }
                else
                {
                    ContextConnector.StatusDefinition.WriteStatus(
                        StatusSeverity.ERROR,
                        "",
                        string.Format(Resources.E64011, this._pathToDoorsExecutable),
                        64011,
                        true,
                        null);
                    this.GenerateSubError();
                    throw new ApplicationException();
                }
            }
            finally
            {
                this.RemoveAllTemporaryFiles();
            }
        }

        [Localizable(false)]
        [DllImport("kernel32.dll", SetLastError = true)]
        [return: MarshalAs(UnmanagedType.Bool)]
        private static extern bool CloseHandle(IntPtr hObject);

        /// <summary>
        /// Finds the doors executable path.
        /// </summary>
        private void FindDoorsExecutable()
        {
            if (this.EnvironmentCommand || this._pathToDoorsExecutable != null)
            {
                return;
            }
            try
            {
                string[] exeName = VariableManagerSingleton.Instance.VariableManager.GetVariable(DoorsCurrentExePath);
                this._pathToDoorsExecutable = exeName[0];
            }
            catch (FileNotFoundException ex)
            {
                ContextConnector.StatusDefinition.WriteStatus(StatusSeverity.ERROR, "", ex.Message, 61014, true, null);
                this.GenerateSubError();
                throw new ApplicationException();
            }
            catch (NullReferenceException)
            {
                ContextConnector.StatusDefinition.WriteStatus(
                    StatusSeverity.ERROR, "", Resources.E64084, 61015, true, null);
                this.GenerateSubError();
                throw new ApplicationException();
            }
            catch (ApplicationException)
            {
                ContextConnector.StatusDefinition.WriteStatus(
                    StatusSeverity.ERROR, "", Resources.E64083, 64083, true, null);
                this.GenerateSubError();
                throw new ApplicationException();
            }
        }

        private void GenerateSubError()
        {
            if (this.Artefacts != null)
            {
                foreach (Artefact artefact in this.Artefacts)
                {
                    ContextConnector.StatusDefinition.WriteStatus(
                        StatusSeverity.ERROR,
                        artefact.Uri,
                        string.Format(Resources.E64007, this.MethodName),
                        64007,
                        true,
                        ContextConnector.StatusDefinition.status);
                }
            }
        }

        private DoorsParameters GetLaunchingParametersForADoorsInstance(DoorsDXL oDoorsInstance)
        {
            if (oDoorsInstance == null)
            {
                return null;
            }
            try
            {
                oDoorsInstance.runStr(Resources.GetDoorsParameters);
            }
            catch (Exception)
            {
                ContextConnector.StatusDefinition.WriteStatus(
                    StatusSeverity.ERROR, "", Resources.E64015, 64015, true, null);
                this.GenerateSubError();
                throw new ApplicationException();
            }
            string doorsResult = oDoorsInstance.result;
            DoorsParameters doorsParameters = new DoorsParameters(doorsResult, false);
            return doorsParameters;
        }

        /// <summary>
        /// Determines whether doors is running on Doors database.
        /// </summary>
        /// <returns>
        ///   Instance to Doors COM Interface if Doors is running on database; otherwise, <c>null</c>.
        /// </returns>
        [SecurityPermission(SecurityAction.Demand, Flags = SecurityPermissionFlag.ControlPrincipal)]
        private DoorsDXL IsRunningDoorsOnDatabase()
        {
            WindowsIdentity currentUser = WindowsIdentity.GetCurrent();
            DoorsDXL result = null;
            int doorsProcessCount = 0;
            foreach (Process process in Process.GetProcesses().Where(process => process.ProcessName.Equals(@"doors")))
            {
                try
                {
                    FileInfo processPath = new FileInfo(process.MainModule.FileName);
                    if (DirectoryHelper.FileNamesPointToSameFile(processPath.FullName, this._pathToDoorsExecutable))
                    {
                        IntPtr ptr = IntPtr.Zero;
                        try
                        {
                            OpenProcessToken(process.Handle, TokenQuery, out ptr);
                            WindowsIdentity ownerOfDoors = new WindowsIdentity(ptr);
                            if (currentUser != null && ownerOfDoors.User == currentUser.User)
                            {
                                doorsProcessCount++;
                            }
                        }
                        finally
                        {
                            if (ptr != IntPtr.Zero)
                            {
                                CloseHandle(ptr);
                            }
                        }
                    }
                }
                catch (Exception)
                {
                }
            }
            if (doorsProcessCount > 0)
            {
                DoorsDXL oDoorsDxl = new DoorsDXL();
                DoorsParameters doorsParameters = this.GetLaunchingParametersForADoorsInstance(oDoorsDxl);
                if (doorsParameters != null)
                {
                    if (doorsParameters.Equals(this.DoorsParameters))
                    {
                        result = oDoorsDxl;
                    }
                    else if (!string.IsNullOrEmpty(doorsParameters.AddinsInRegistry))
                    {
                        List<string> listOfAddins = new List<string>(doorsParameters.AddinsInRegistry.Split(';'));
                        if (
                            !listOfAddins.Any(
                                path => DirectoryHelper.FileNamesPointToSameFile(path, this._pathOfConnectorAddin)))
                        {
                            ContextConnector.StatusDefinition.WriteStatus(
                                StatusSeverity.ERROR,
                                "",
                                string.Format(Resources.E64078, doorsParameters.AddinsInRegistry),
                                64078,
                                true,
                                null);
                            this.GenerateSubError();
                            throw new ApplicationException();
                        }
                    }
                }
            }
            if (result == null && doorsProcessCount > 1)
            {
                ContextConnector.StatusDefinition.WriteStatus(
                    StatusSeverity.ERROR, "", Resources.E64082, 64082, true, null);
                this.GenerateSubError();
                throw new ApplicationException();
            }
            return result;
        }

        [Localizable(false)]
        [DllImport("advapi32.dll", SetLastError = true)]
        private static extern bool OpenProcessToken(IntPtr processHandle, UInt32 desiredAccess, out IntPtr tokenHandle);

        /// <summary>
        /// Parses the returned status file.
        /// </summary>
        private void ParseReturnedStatusFile()
        {
            if (File.Exists(this._pathToStatusFile))
            {
                string content = File.ReadAllText(this._pathToStatusFile);
                StatusDefinition tmp = new StatusDefinition();
                tmp.SetStatusDefinition(content);
                if (tmp.status != null)
                {
                    if (tmp.status.status != null)
                    {
                        foreach (Status statuse in tmp.status.status)
                        {
                            if (statuse.status != null)
                            {
                                foreach (Status subStatus in statuse.status)
                                {
                                    if (subStatus.severity == StatusSeverity.ERROR)
                                    {
                                        statuse.severity = StatusSeverity.ERROR;
                                        break;
                                    }
                                    if (subStatus.severity == StatusSeverity.WARNING)
                                    {
                                        statuse.severity = StatusSeverity.WARNING;
                                    }
                                }
                            }
                            if (statuse.severity > ContextConnector.StatusDefinition.status.severity)
                            {
                                ContextConnector.StatusDefinition.status.severity = statuse.severity;
                            }
                            ContextConnector.StatusDefinition.status.AddStatus(statuse);
                        }
                    }
                }
                try
                {
                    File.Delete(this._pathToStatusFile);
                }
                catch (Exception)
                {
                    //DO NOTHING
                }
            }
            else
            {
                ContextConnector.StatusDefinition.WriteStatus(
                    StatusSeverity.ERROR, "", Resources.E64006, 64006, true, null);
                this.GenerateSubError();
                throw new ApplicationException();
            }
        }

        /// <summary>
        /// Removes all temporary files.
        /// </summary>
        private void RemoveAllTemporaryFiles()
        {
            try
            {
                File.Delete(this._dxlFilePath);
            }
            catch (Exception)
            {
                //DO NOTHING
            }
            try
            {
                File.Delete(this.UriFilePath);
            }
            catch (Exception)
            {
                //DO NOTHING
            }
        }

        /// <summary>
        /// Runs the command on visible doors instance.
        /// </summary>
        /// <param name="oDoorsDxl">The database doors.</param>
        /// <returns><c>true</c> if the command is OK ; otherwise, <c>false</c>.</returns>
        private bool RunDxlOnVisibleDoorsInstance(DoorsDXL oDoorsDxl)
        {
            bool done = false;
            try
            {
                if (oDoorsDxl != null)
                {
                    oDoorsDxl.runFile(this._dxlFilePath);
                    this.ParseReturnedStatusFile();
                    done = true;
                }
            }
            catch (Exception)
            {
            }
            return done;
        }
    }

    /// <summary>
    /// Navigate command
    /// </summary>
    [Localizable(false)]
    internal class Navigate : CommandFromFramework
    {
        public Navigate()
        {
            this.OdmVariablesNeeded = new List<OdmVariable>
                {
                    new OdmVariable("UseIEPUIDAsIdentifier", @"\Orchestra\Connectors\Doors\Use IE PUID as identifier", true),
                    new OdmVariable(
                        "DisplayLinkStatusDelimiter", @"\Orchestra\LinkManager\Config\displayLinkStatusDelimiter"),
                    new OdmVariable("SegmentName", @"\Orchestra\Segment")
                };
        }

        protected override string Include
        {
            get
            {
                return "OrchestraNavigate";
            }
        }

        protected override string MethodName
        {
            get
            {
                return "Navigate";
            }
        }

        public override bool MakeDoorsVisible
        {
            get
            {
                return true;
            }
        }
    }

    /// <summary>
    /// Documentary Export command
    /// </summary>
    [Localizable(false)]
    internal class DocumentaryExport : CommandFromFramework
    {
        public DocumentaryExport()
        {
            this.OdmVariablesNeeded = new List<OdmVariable>
                {
                    new OdmVariable("UseIEPUIDAsIdentifier", @"\Orchestra\Connectors\Doors\Use IE PUID as identifier", true),
                    new OdmVariable("ConfigurationDirectory", @"\Orchestra\ConfigurationDirectory"),
                    new OdmVariable("AddedProperties", @"\Orchestra\Connectors\Doors\Added Properties"),
                    new OdmVariable("SegmentName", @"\Orchestra\Segment")
                };
        }

        protected override string Include
        {
            get
            {
                return "OrchestraDocumentaryExport";
            }
        }

        protected override string MethodName
        {
            get
            {
                return "OrchestraDocumentaryExport";
            }
        }
    }

    /// <summary>
    /// Expand command
    /// </summary>
    [Localizable(false)]
    internal class Expand : CommandFromFramework
    {
        public Expand()
        {
            this.OdmVariablesNeeded = new List<OdmVariable>
                {
                    new OdmVariable("SegmentName", @"\Orchestra\Segment")
                };
        }

        protected override string Include
        {
            get
            {
                return "OrchestraExpand";
            }
        }

        protected override string MethodName
        {
            get
            {
                return "OrchestraExpand";
            }
        }
    }

    /// <summary>
    /// Link Manager Export Command
    /// </summary>
    [Localizable(false)]
    internal class LmExport : CommandFromFramework
    {
        public LmExport()
        {
            this.OdmVariablesNeeded = new List<OdmVariable>
                {
                    new OdmVariable("UseIEPUIDAsIdentifier", @"\Orchestra\Connectors\Doors\Use IE PUID as identifier", true),
                    new OdmVariable("ConfigurationDirectory", @"\Orchestra\ConfigurationDirectory"),
                    new OdmVariable("AddedProperties", @"\Orchestra\Connectors\Doors\Added Properties"),
                    new OdmVariable("SegmentName", @"\Orchestra\Segment")
                };
        }

        protected override string Include
        {
            get
            {
                return "OrchestraDocumentaryExport";
            }
        }

        protected override string MethodName
        {
            get
            {
                return "OrchestraDocumentaryExport";
            }
        }
    }

    /// <summary>
    /// Search command
    /// </summary>
    [Localizable(false)]
    internal class Search : CommandFromFramework
    {
        public Search()
        {
            this.OdmVariablesNeeded = new List<OdmVariable>
                {
                    new OdmVariable("SegmentName", @"\Orchestra\Segment")
                };
        }

        protected override string Include
        {
            get
            {
                return "OrchestraSearchByFullName";
            }
        }

        protected override string MethodName
        {
            get
            {
                return "OrchestraSearchByFullName";
            }
        }
    }

    /// <summary>
    /// Migrate command
    /// </summary>
    [Localizable(false)]
    internal class Migrate : CommandFromFramework
    {
        public Migrate()
        {
            this.OdmVariablesNeeded = new List<OdmVariable>
                {
                    new OdmVariable("SegmentName", @"\Orchestra\Segment")
                };
        }

        protected override string Include
        {
            get
            {
                return "OrchestraMigrate";
            }
        }

        protected override string MethodName
        {
            get
            {
                return "OrchestraMigrate";
            }
        }
    }

    /// <summary>
    /// Get list of projects command
    /// </summary>
    [Localizable(false)]
    internal class GetProjects : CommandFromEnvironment
    {
        public GetProjects()
        {
            this.OdmVariablesNeeded = new List<OdmVariable>
                {
                    new OdmVariable("SegmentName", @"\Orchestra\Segment")
                };
        }

        protected override string Include
        {
            get
            {
                return "Environment/OrchestraGetProjects";
            }
        }

        protected override string MethodName
        {
            get
            {
                return "OrchestraGetProjects";
            }
        }
    }

    /// <summary>
    /// Get Artefacts Metadata command
    /// </summary>
    [Localizable(false)]
    internal class GetArtefactsMetadata : CommandFromFramework
    {
        public GetArtefactsMetadata()
        {
            this.OdmVariablesNeeded = new List<OdmVariable>
                {
                    new OdmVariable("SegmentName", @"\Orchestra\Segment")
                };
        }

        #region Overrides of Command
        protected override string Include
        {
            get
            {
                return "OrchestraGetArtefactsMetadata";
            }
        }

        protected override string MethodName
        {
            get
            {
                return "OrchestraGetArtefactsMetadata";
            }
        }
        #endregion
    }

    /// <summary>
    /// Make Baseline command.
    /// </summary>
    [Localizable(false)]
    internal class MakeBaseline : CommandFromEnvironment
    {
        public MakeBaseline()
        {
            this.OdmVariablesNeeded = new List<OdmVariable>
                {
                    new OdmVariable("SegmentName", @"\Orchestra\Segment")
                };
        }

        #region Overrides of Command
        protected override string Include
        {
            get
            {
                return "OrchestraMakeBaseline";
            }
        }

        protected override string MethodName
        {
            get
            {
                return "OrchestraMakeBaseline";
            }
        }
        #endregion
    }

    /// <summary>
    /// GetOutLinkedModules command.
    /// </summary>
    [Localizable(false)]
    internal class GetOutLinkedModules : CommandFromFramework
    {
        public GetOutLinkedModules()
        {
            this.OdmVariablesNeeded = new List<OdmVariable>
                {
                    new OdmVariable("ConfigurationDirectory", @"\Orchestra\ConfigurationDirectory"),
                    new OdmVariable("SegmentName", @"\Orchestra\Segment")
                };
        }

        #region Overrides of Command
        protected override string Include
        {
            get
            {
                return "OrchestraGetOutLinkedModules";
            }
        }

        protected override string MethodName
        {
            get
            {
                return "OrchestraGetOutLinkedModules";
            }
        }
        #endregion
    }
}