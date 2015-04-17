// ----------------------------------------------------------------------------------------------------
// File Name: MsiActions.cs
// Project: MsiActions
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration.Install;
using System.Diagnostics;
using System.IO;
using System.Reflection;
using System.Text;
using Microsoft.Win32;

[RunInstaller(true)]
public class MsiActions : Installer
{
    private const string KeyDoorsFormat = @"SOFTWARE\Telelogic\DOORS\{0}\Config\";

    private const string DoorsInstallLocationFormat = @"SOFTWARE\Telelogic\DOORS\{0}";

    private const string LinkManagerInstallLocation = @"SOFTWARE\Thales\EPM\Orchestra\Products\LinkManager";

    private string _targetDir;

    public override void Install(IDictionary stateSaver)
    {
        base.Install(stateSaver);
        try
        {
            //Read parameters
            string[] listOfVersionOfDoorsSupported = this.Context.Parameters["SV"].Split('|');

            this._targetDir = new Uri(Assembly.GetExecutingAssembly().CodeBase).LocalPath;
            this._targetDir = Path.GetDirectoryName(this._targetDir);

            #region open registry
            RegistryKey localMachine32BitsBaseKey = RegistryKey.OpenBaseKey(
                RegistryHive.LocalMachine, RegistryView.Registry32);
            RegistryKey localMachine64BitsBaseKey = RegistryKey.OpenBaseKey(
                RegistryHive.LocalMachine, RegistryView.Registry64);
            #endregion

            #region Determine Link Manager Install Location
            string linkManagerPath = "";
            bool lmPresent = false;
            try
            {
                RegistryKey linkManagerKey = localMachine32BitsBaseKey.OpenSubKey(LinkManagerInstallLocation)
                                             ?? localMachine64BitsBaseKey.OpenSubKey(LinkManagerInstallLocation);
                if (linkManagerKey != null)
                {
                    linkManagerPath = linkManagerKey.GetValue("InstallLocation") as string;
                }
                if (!string.IsNullOrEmpty(linkManagerPath))
                {
                    lmPresent = true;
                }
            }
            catch (Exception)
            {
                //DO NOTHING
            }
            #endregion

            #region Trace
            try
            {
                StringBuilder trace = new StringBuilder(
                    "Start custom actions for Doors Connector" + Environment.NewLine);
                trace.AppendLine("SupportedVersions: " + string.Join(", ", listOfVersionOfDoorsSupported));
                trace.AppendLine("Home: " + this._targetDir);
                trace.AppendLine(lmPresent ? "linkManagerPath: " + linkManagerPath : "No linkManagerPath");
                trace.AppendLine(
                    Environment.Is64BitOperatingSystem
                        ? "Installation on a 64 bits operating system"
                        : "Installation on a 32 bits operating system");
                trace.AppendLine(
                    Environment.Is64BitProcess
                        ? "Custom actions running on a 64 bits process"
                        : "Custom actions running on a 32 bits process");
                EventLog.WriteEntry("Installer", trace.ToString(), EventLogEntryType.Information);
            }
            catch (Exception)
            {
                //DO NOTHING
            }
            #endregion

            if (string.IsNullOrEmpty(this._targetDir))
            {
                try
                {
                    StringBuilder errorMessage = new StringBuilder();
                    if (string.IsNullOrEmpty(this._targetDir))
                    {
                        errorMessage.AppendLine("Impossible to determine the installation location.");
                    }
                    EventLog.WriteEntry("Installer", errorMessage.ToString(), EventLogEntryType.Error);
                }
                catch (Exception)
                {
                    //DO NOTHING
                }
                throw new ApplicationException("A mandatory parameter is empty. See Event Log for more details.");
            }

            string orchestraInstallFilePath = Path.Combine(this._targetDir, @"Doors\OrchestraInstall.inc");

            if (!File.Exists(orchestraInstallFilePath))
            {
                try
                {
                    EventLog.WriteEntry(
                        "Installer", "The file:" + orchestraInstallFilePath + " doesn't exists", EventLogEntryType.Error);
                }
                catch (Exception)
                {
                    //DO NOTHING
                }
                throw new ApplicationException("File " + orchestraInstallFilePath + " doesn't exists!");
            }
            //Declare the Doors connector Dxl to Doors
            foreach (string versionOfDoorsSupported in listOfVersionOfDoorsSupported)
            {
                string configRegistryPath = string.Format(KeyDoorsFormat, versionOfDoorsSupported);
                RegistryKey configKey = localMachine32BitsBaseKey.OpenSubKey(configRegistryPath, true)
                                        ?? localMachine64BitsBaseKey.OpenSubKey(configRegistryPath, true);
                if (configKey != null)
                {
                    object o = configKey.GetValue("Addins");
                    List<string> list;
                    if (o != null)
                    {
                        string[] addinsValue = ((string)o).Split(new[] { ';' }, StringSplitOptions.RemoveEmptyEntries);
                        list = new List<string>(addinsValue);
                        list.RemoveAll(this.IsTargetDir);
                    }
                    else
                    {
                        list = new List<string>();
                    }
                    list.Add(Path.Combine(this._targetDir, "Doors"));
                    configKey.SetValue("Addins", string.Join(";", list.ToArray()));
                }
            }

            //Install file OrchestraRCM.inc according with the IRDFMRAO installation
            bool? installWithRCMSupport = null;
            foreach (string versionOfDoorsSupported in listOfVersionOfDoorsSupported)
            {
                string pathOfDoorsInstallation = string.Format(DoorsInstallLocationFormat, versionOfDoorsSupported);
                RegistryKey doorsInstallationDirectory = localMachine32BitsBaseKey.OpenSubKey(pathOfDoorsInstallation, true)
                                        ?? localMachine64BitsBaseKey.OpenSubKey(pathOfDoorsInstallation, true);
                if (doorsInstallationDirectory != null)
                {
                    object o = doorsInstallationDirectory.GetValue("InstallationDirectory");
                    if (o != null)
                    {
                        string path = Path.Combine((string)o, "irdrmfao");
                        if (Directory.Exists(path) && installWithRCMSupport != false)
                        {
                            installWithRCMSupport = true;
                        }
                        else
                        {
                            installWithRCMSupport = false;
                        }
                    }
                }
            }
            string file1 = Path.Combine(this._targetDir, @"Doors\RCMManagement\OrchestraRCM.inc");
            string file2 = Path.Combine(this._targetDir, @"Doors\RCMManagement\OrchestraNoRCM.inc");
            if (installWithRCMSupport == true)
            {
                File.Delete(file2);
            }
            else
            {
                File.Delete(file1);
                File.Move(file2, file1);
            }

            //Modify Dxl file OrchestraInstall.inc
            string[] fileContent = File.ReadAllLines(orchestraInstallFilePath);
            for (int i = 0; i < fileContent.Length; i++)
            {
                if (fileContent[i].StartsWith("//"))
                {
                    continue;
                }
                if (fileContent[i].Contains("CInstallDirectory"))
                {
                    string pathToDxl = Path.Combine(this._targetDir, "Doors") + @"\";
                    fileContent[i] = fileContent[i].Replace("\"\"", "\"" + pathToDxl + "\"").Replace(@"\", @"\\");
                }
                else if (lmPresent && fileContent[i].Contains("COrchestraLMFullPath"))
                {
                    linkManagerPath = Path.Combine(linkManagerPath, "OrchestraLM.exe");
                    fileContent[i] = fileContent[i].Replace("\"\"", "\"" + linkManagerPath + "\"").Replace(@"\", @"\\");
                }
            }
            File.WriteAllLines(orchestraInstallFilePath, fileContent);
            try
            {
                //Save actions for Uninstall
                stateSaver.Add("SupportedVersions", string.Join("|", listOfVersionOfDoorsSupported));
                stateSaver.Add("Home", this._targetDir);
                EventLog.WriteEntry(
                    "Installer",
                    "Custom actions for installation of Doors Connector finished without error.",
                    EventLogEntryType.Information);

            }
            catch (Exception)
            {
                // DO NOTHING
            }
        }
        catch (ApplicationException)
        {
            throw;
        }
        catch (Exception ex)
        {
            try
            {
                EventLog.WriteEntry(
                    "Installer", ex.Message + Environment.NewLine + ex.StackTrace, EventLogEntryType.Error);
            }
            catch (Exception)
            {
                //DO NOTHING
            }
            throw;
        }
    }

    public override void Uninstall(IDictionary savedState)
    {
        base.Uninstall(savedState);
        try
        {
            string[] listOfVersionOfDoorsSupported = ((string)savedState["SupportedVersions"]).Split('|');
            this._targetDir = Path.Combine((string)savedState["Home"], "Doors");

            #region open registry
            RegistryKey localMachine32BitsBaseKey = RegistryKey.OpenBaseKey(
                RegistryHive.LocalMachine, RegistryView.Registry32);
            RegistryKey localMachine64BitsBaseKey = RegistryKey.OpenBaseKey(
                RegistryHive.LocalMachine, RegistryView.Registry64);
            #endregion

            //Remove registry keys
            foreach (string versionOfDoorsSupported in listOfVersionOfDoorsSupported)
            {
                string configRegistryPath = string.Format(KeyDoorsFormat, versionOfDoorsSupported);
                RegistryKey configKey = localMachine32BitsBaseKey.OpenSubKey(configRegistryPath, true)
                                        ?? localMachine64BitsBaseKey.OpenSubKey(configRegistryPath, true);
                if (configKey != null)
                {
                    string[] addinsValue = ((string)configKey.GetValue("Addins")).Split(
                        new[] { ';' }, StringSplitOptions.RemoveEmptyEntries);

                    List<string> list = new List<string>(addinsValue);
                    if (list.RemoveAll(this.IsTargetDir) > 0)
                    {
                        configKey.SetValue("Addins", string.Join(";", list.ToArray()));
                    }
                }
            }
        }
        catch
        {
        }
    }

    private bool IsTargetDir(string s)
    {
        return s.Trim().Equals(this._targetDir);
    }
}