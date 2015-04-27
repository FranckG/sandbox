// ----------------------------------------------------------------------------------------------------
// File Name: PapeeteException.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

using System.ComponentModel;
using System.Configuration.Install;
using System.Diagnostics;

///<summary>
/// Installer of Event Log Source named Orchestra
/// Internale Use
///</summary>
[RunInstaller(true)]
public class OrchestraEventLogInstaller : Installer
{
    private readonly EventLogInstaller _myEventLogInstaller;

    ///<summary>
    /// Internal Use
    ///</summary>
    public OrchestraEventLogInstaller()
    {
        this._myEventLogInstaller = new EventLogInstaller { Source = "Orchestra", Log = "Application" };

        this.Installers.Add(this._myEventLogInstaller);
    }

    ///<summary>
    /// Internale Use
    ///</summary>
    public static void Main()
    {
    }
}

namespace Orchestra.Framework
{
    using System;

    ///<summary>
    /// Class for writing in Windows Event Log
    ///</summary>
    public static class EventLogOrchestra
    {
        private const String Source = "Orchestra";

        ///<summary>
        /// Write error into the Windows Event Log.
        ///</summary>
        ///<param name="number">error number</param>
        ///<param name="message">message</param>
        ///<param name="source">error source</param>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// Orchestra.Framework.EventLogOrchestra.WriteError(60005, "My Error", "Orchestra.Framework.Connector.myConnector myMethod");
        /// </code>
        /// </example>
        public static void WriteError(Int32 number, String message, String source)
        {
            try
            {
                message = "Source: " + source + Environment.NewLine + message;
                EventLog.WriteEntry(Source, message, EventLogEntryType.Error, number);
            }
            catch (Exception)
            {
            }
        }

        ///<summary>
        /// Write information into the Windows Event Log.
        ///</summary>
        ///<param name="number">information number</param>
        ///<param name="message">message</param>
        ///<param name="source">information source</param>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// Orchestra.Framework.EventLogOrchestra.WriteInfo(60005, "My message", "Orchestra.Framework.Connector.myConnector myMethod");
        /// </code>
        /// </example>
        public static void WriteInfo(Int32 number, String message, String source)
        {
            try
            {
                message = "Source: " + source + Environment.NewLine + message;
                EventLog.WriteEntry(Source, message, EventLogEntryType.Information, number);
            }
            catch (Exception)
            {
            }
        }

        ///<summary>
        /// Write warning into the Windows Event Log.
        ///</summary>
        ///<param name="number">warning number</param>
        ///<param name="message">message</param>
        ///<param name="source">warning source</param>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// Orchestra.Framework.EventLogOrchestra.WriteWarning(60005, "My Warning", "Orchestra.Framework.Connector.myConnector myMethod");
        /// </code>
        /// </example>
        public static void WriteWarning(Int32 number, String message, String source)
        {
            try
            {
                message = "Source: " + source + Environment.NewLine + message;
                EventLog.WriteEntry(Source, message, EventLogEntryType.Warning, number);
            }
            catch (Exception)
            {
            }
        }
    }
}