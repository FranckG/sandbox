// ----------------------------------------------------------------------------------------------------
// File Name: MsiActions.cs
// Project: MsiActions
// Copyright (c) Thales, 2010 - 2014. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace MsiActions
{
    using System;
    using System.Collections;
    using System.ComponentModel;
    using System.Configuration.Install;
    using System.IO;
    using System.Reflection;
    using Microsoft.Win32;

    [RunInstaller(true)]
    public class MsiActions : Installer
    {
        //Assembly asm = Assembly.LoadFile(@"c:\temp\ImageConverter.dll");
        //readonly RegistrationServices regAsm = new RegistrationServices();
        //bool bResult = regAsm.RegisterAssembly(asm, AssemblyRegistrationFlags.SetCodeBase);
        private const string CrcGuid = "{1C8388F9-8A08-4F89-9B0F-0749119E2EC2}";
        private const string CrcClsidKey = @"CLSID\{0}";
        private const string AppIDKey = "AppID";
        private static string _logFilePath;

        public MsiActions()
        {
            string myPath = Assembly.GetExecutingAssembly().Location;
            if (!string.IsNullOrEmpty(myPath))
            {
                myPath = Path.GetDirectoryName(myPath);
                if (myPath != null)
                {
                    _logFilePath = Path.Combine(myPath, "MsiActions.log");
                }
            }
        }

        public override void Install(IDictionary stateSaver)
        {
            WriteLogFile("Start Install");
            base.Install(stateSaver);
            try
            {
                using (
                    RegistryKey classesRoot32BitsBaseKey = RegistryKey.OpenBaseKey(
                        RegistryHive.ClassesRoot, RegistryView.Registry32))
                {
                    string registryPath = string.Format(CrcClsidKey, CrcGuid);
                    RegistryKey registryKey = classesRoot32BitsBaseKey.OpenSubKey(registryPath, true);
                    if (registryKey != null)
                    {
                        registryKey.SetValue(AppIDKey, CrcGuid, RegistryValueKind.String);
                        WriteLogFile(@"Create registry value HKCR\CLSID\{1C8388F9-8A08-4F89-9B0F-0749119E2EC2}\AppID");
                    }
                    registryKey = classesRoot32BitsBaseKey.OpenSubKey(AppIDKey, true);
                    if (registryKey != null)
                    {
                        registryKey = registryKey.CreateSubKey(CrcGuid);
                        WriteLogFile(@"Create registry key HKCR\AppID\{1C8388F9-8A08-4F89-9B0F-0749119E2EC2}");
                        if (registryKey != null)
                        {
                            registryKey.SetValue("DllSurrogate", string.Empty, RegistryValueKind.String);
                            WriteLogFile(@"Create registry value HKCR\AppID\{1C8388F9-8A08-4F89-9B0F-0749119E2EC2}\DllSurrogate");
                        }
                    }
                }
                WriteLogFile("End Install without error");
            }
            catch (Exception ex)
            {
                WriteLogFile("End Install with error:" + Environment.NewLine + ex.Message + Environment.NewLine + ex.StackTrace);
            }
        }

        public override void Uninstall(IDictionary savedState)
        {
            WriteLogFile("Start Uninstall");
            base.Uninstall(savedState);
            try
            {
                using (
                    RegistryKey classesRoot32BitsBaseKey = RegistryKey.OpenBaseKey(
                        RegistryHive.ClassesRoot, RegistryView.Registry32))
                {
                    string registryPath = string.Format(CrcClsidKey, CrcGuid);
                    RegistryKey registryKey = classesRoot32BitsBaseKey.OpenSubKey(registryPath, true);
                    if (registryKey != null)
                    {
                        registryKey.DeleteValue(AppIDKey, false);
                        WriteLogFile(@"Delete registry value HKCR\CLSID\{1C8388F9-8A08-4F89-9B0F-0749119E2EC2}\AppID");
                    }
                    registryKey = classesRoot32BitsBaseKey.OpenSubKey(AppIDKey, true);
                    if (registryKey != null)
                    {
                        registryKey.DeleteSubKeyTree(CrcGuid, false);
                        WriteLogFile(@"Delete registry key HKCR\AppID\{1C8388F9-8A08-4F89-9B0F-0749119E2EC2}");
                    }
                }
                WriteLogFile("End Uninstall without error");
            }
            catch (Exception ex)
            {
                WriteLogFile("End Uninstall with error:" + Environment.NewLine + ex.Message + Environment.NewLine + ex.StackTrace);
            }
        }

        private static void WriteLogFile(string message)
        {
            try
            {
                if (!string.IsNullOrEmpty(_logFilePath))
                {
                    message += Environment.NewLine;
                    File.AppendAllText(_logFilePath, message);
                }
            }
            catch (Exception)
            {
                //DO NOTHING
            }
        }
    }
}