// ----------------------------------------------------------------------------------------------------
// File Name: DoorsParameters.cs
// Project: DoorsConnector
// Copyright (c) Thales, 2014 - 2014. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Connector.Doors
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel;
    using System.Diagnostics;
    using System.Linq;
    using System.Text;
    using Microsoft.Win32;
    using Orchestra.Framework.Utilities;

    /// <summary>
    /// Class DoorsParameters
    /// </summary>
    internal class DoorsParameters
    {
        private const string KeyDoorsFormat = @"SOFTWARE\Telelogic\DOORS\{0}\Config\";

        private const string AddinsOption = "Addins";
        private const string AttributeaddinsOption = "AttributeAddins";
        private const string CachingOption = "Caching";
        private const string CertnameOption = "CertName";
        private const string DefopenmodeOption = "DefOpenMode";
        private const string DefopenlinkmodeOption = "DefOpenLinkMode";
        private const string LayoutaddinsOption = "LayoutAddins";
        private const string LocaldataOption = "LocalData";
        private const string LogfileOption = "LogFile";

        [Localizable(false)]
        private static readonly Dictionary<String, String> _DoorsAllowedArguments = new Dictionary<string, string>
            {
                { "-a", AddinsOption },
                { "-addins", AddinsOption },
                { "-A", AttributeaddinsOption },
                { "-attributeaddins", AttributeaddinsOption },
                { "-k", CachingOption },
                { "-caching", CachingOption },
                { "-certName", CertnameOption },
                { "-o", DefopenmodeOption },
                { "-defopenmode", DefopenmodeOption },
                { "-O", DefopenlinkmodeOption },
                { "-defopenlinkmode", DefopenlinkmodeOption },
                { "-L", LayoutaddinsOption },
                { "-layoutaddins", LayoutaddinsOption },
                { "-f", LocaldataOption },
                { "-localdata", LocaldataOption },
                { "-l", LogfileOption },
                { "-logfile", LogfileOption },
            };

        #region Equality members
        /// <summary>
        /// Serves as a hash function for a particular type. 
        /// </summary>
        /// <returns>
        /// A hash code for the current <see cref="T:System.Object"/>.
        /// </returns>
        public override int GetHashCode()
        {
            unchecked
            {
                int hashCode = (this.Database != null ? this.Database.GetHashCode() : 0);
                hashCode = (hashCode * 397) ^ (this.Addins != null ? this.Addins.GetHashCode() : 0);
                hashCode = (hashCode * 397) ^ (this.AttributeAddins != null ? this.AttributeAddins.GetHashCode() : 0);
                hashCode = (hashCode * 397) ^ (this.DefOpenMode != null ? this.DefOpenMode.GetHashCode() : 0);
                hashCode = (hashCode * 397) ^ (this.DefOpenLinkMode != null ? this.DefOpenLinkMode.GetHashCode() : 0);
                hashCode = (hashCode * 397) ^ (this.LayoutAddins != null ? this.LayoutAddins.GetHashCode() : 0);
                hashCode = (hashCode * 397) ^ (this.LocalData != null ? this.LocalData.GetHashCode() : 0);
                hashCode = (hashCode * 397) ^ (this.LogFile != null ? this.LogFile.GetHashCode() : 0);
                return hashCode;
            }
        }

        protected bool Equals(DoorsParameters other)
        {
            if (!string.Equals(this.Database, other.Database, StringComparison.OrdinalIgnoreCase))
            {
                Trace.TraceInformation("The databases are different ({0} <> {1})", this.Database, other.Database);
                return false;
            }
            List<string> list1 = new List<string>(this.Addins.Replace("\"", "").Split(';'));
            List<string> list2 = string.IsNullOrEmpty(other.Addins)
                                     ? null
                                     : new List<string>(other.Addins.Replace("\"", "").Split(';'));
            List<string> addinsInRegistry = new List<string>(this.AddinsInRegistry.Replace("\"", "").Split(';'));
            if (list2 != null)
            {
                if (!list1.SequenceEqual(list2))
                {
                    Trace.TraceInformation(
                        "Opening Doors has Addins={0}\nDoors Environment has Addins={1}", this.Addins, other.Addins);
                    return false;
                }
            }
            else if (!list1.SequenceEqual(addinsInRegistry))
            {
                Trace.TraceInformation(
                    "Opening Doors has Addins={0}\nDoors Environment has no Addins parameter\nBut in registry Addins={1}",
                    this.Addins,
                    addinsInRegistry);
                return false;
            }
            list1 = new List<string>(this.AttributeAddins.Replace("\"", "").Split(';'));
            list2 = new List<string>(other.AttributeAddins.Replace("\"", "").Split(';'));
            if (!list1.SequenceEqual(list2))
            {
                Trace.TraceInformation(
                    "Opening Doors has AttributeAddins={0}\nDoors Environment has AttributeAddins={1}",
                    this.AttributeAddins,
                    other.AttributeAddins);
                return false;
            }
            if (!string.Equals(this.DefOpenMode, other.DefOpenMode))
            {
                Trace.TraceInformation(
                    "Opening Doors has DefOpenMode={0}\nDoors Environment has DefOpenMode={1}",
                    this.DefOpenMode,
                    other.DefOpenMode);
                return false;
            }
            if (!string.Equals(this.DefOpenLinkMode, other.DefOpenLinkMode))
            {
                Trace.TraceInformation(
                    "Opening Doors has DefOpenLinkMode={0}\nDoors Environment has DefOpenLinkMode={1}",
                    this.DefOpenLinkMode,
                    other.DefOpenLinkMode);
                return false;
            }
            list1 = new List<string>(this.LayoutAddins.Replace("\"", "").Split(';'));
            list2 = new List<string>(other.LayoutAddins.Replace("\"", "").Split(';'));
            if (!list1.SequenceEqual(list2))
            {
                Trace.TraceInformation(
                    "Opening Doors has LayoutAddins={0}\nDoors Environment has LayoutAddins={1}",
                    this.LayoutAddins,
                    other.LayoutAddins);
                return false;
            }
            string localdataInRegistry = null;
            try
            {
                using (
                    RegistryKey key = Registry.CurrentUser.OpenSubKey(string.Format(KeyDoorsFormat, this.DoorsVersion)))
                {
                    if (key != null)
                    {
                        localdataInRegistry = key.GetValue("LOCALDATA") as string;
                    }
                }
            }
            catch (Exception)
            {
                //DO NOTHING
            }
            if (!string.IsNullOrEmpty(other.LocalData))
            {
                if (!DirectoryHelper.FileNamesPointToSameFile(this.LocalData, other.LocalData))
                {
                    Trace.TraceInformation(
                        "Opening Doors has LocalData={0}\nDoors Environment has LocalData={1}",
                        this.LocalData,
                        other.LocalData);
                    return false;
                }
            }
            else if (localdataInRegistry != null
                     && !DirectoryHelper.FileNamesPointToSameFile(localdataInRegistry, this.LocalData))
            {
                Trace.TraceInformation(
                    "Opening Doors has LocalData={0}\nDoors Environment has no LocalData parameter\nBut in registry LocalData={1}",
                    this.LocalData,
                    localdataInRegistry);
                return false;
            }
            if (
                !(string.IsNullOrEmpty(this.LogFile) && string.IsNullOrEmpty(other.LogFile)
                  || DirectoryHelper.FileNamesPointToSameFile(this.LogFile, other.LogFile)))
            {
                Trace.TraceInformation(
                    "Opening Doors has LogFile={0}\nDoors Environment has LogFile={1}", this.LogFile, other.LogFile);
                return false;
            }
            return true;
        }
        #endregion

        private string _addins = string.Empty;
        private string _addinsInRegistry = string.Empty;
        private string _attributeAddins = string.Empty;
        private string _certName = string.Empty;
        private string _database = string.Empty;
        private string _defOpenLinkMode = string.Empty;
        private string _defOpenMode = string.Empty;
        private string _layoutAddins = string.Empty;
        private string _localData = string.Empty;
        private string _logFile = string.Empty;

        public DoorsParameters(string arguments, bool fromEnvironment)
        {
            if (fromEnvironment)
            {
                this.PopulateFromEnvironment(arguments);
            }
            else
            {
                this.PopulateFromDoors(arguments);
            }
        }

        /// <summary>
        /// Gets the doors version.
        /// </summary>
        /// <value>The doors version.</value>
        public string DoorsVersion { get; private set; }

        /// <summary>
        /// Gets the database.
        /// </summary>
        /// <value>The database.</value>
        public string Database
        {
            get
            {
                return this._database;
            }
            internal set
            {
                this._database = value;
            }
        }

        /// <summary>
        /// Gets or private sets the addins parameter.
        /// </summary>
        /// <value>The addins.</value>
        public string Addins
        {
            get
            {
                return this._addins;
            }
            private set
            {
                this._addins = value;
            }
        }

        /// <summary>
        /// Gets the addins in registry.
        /// </summary>
        /// <value>The addins in registry.</value>
        public string AddinsInRegistry
        {
            get
            {
                return this._addinsInRegistry;
            }
            private set
            {
                this._addinsInRegistry = value;
            }
        }

        /// <summary>
        /// Gets or private sets the attribute addins parameter.
        /// </summary>
        /// <value>The attribute addins.</value>
        public string AttributeAddins
        {
            get
            {
                return this._attributeAddins;
            }
            private set
            {
                this._attributeAddins = value;
            }
        }

        /// <summary>
        /// Gets or private sets the def open mode parameter.
        /// </summary>
        /// <value>The def open mode.</value>
        public string DefOpenMode
        {
            get
            {
                return this._defOpenMode;
            }
            private set
            {
                this._defOpenMode = value;
            }
        }

        /// <summary>
        /// Gets or private sets the def open link mode parameter.
        /// </summary>
        /// <value>The def open link mode.</value>
        public string DefOpenLinkMode
        {
            get
            {
                return this._defOpenLinkMode;
            }
            private set
            {
                this._defOpenLinkMode = value;
            }
        }

        /// <summary>
        /// Gets or private sets the layout addins parameter.
        /// </summary>
        /// <value>The layout addins.</value>
        public string LayoutAddins
        {
            get
            {
                return this._layoutAddins;
            }
            private set
            {
                this._layoutAddins = value;
            }
        }

        /// <summary>
        /// Gets or private sets the local data parameter.
        /// </summary>
        /// <value>The local data.</value>
        public string LocalData
        {
            get
            {
                return this._localData;
            }
            private set
            {
                this._localData = value;
            }
        }

        /// <summary>
        /// Gets or private sets the log file parameter.
        /// </summary>
        /// <value>The log file.</value>
        public string LogFile
        {
            get
            {
                return this._logFile;
            }
            private set
            {
                this._logFile = value;
            }
        }

        /// <summary>
        /// Gets the name of the cert.
        /// </summary>
        /// <value>The name of the cert.</value>
        public string CertName
        {
            get
            {
                return this._certName;
            }
            private set
            {
                this._certName = value;
            }
        }

        /// <summary>
        /// Gets a value indicating whether the Doors client use the caching.
        /// </summary>
        /// <value><c>true</c> if caching; otherwise, <c>false</c>.</value>
        public bool Caching { get; private set; }

        /// <summary>
        /// Determines whether the specified <see cref="System.Object" /> is equal to this instance.
        /// </summary>
        /// <param name="obj">The <see cref="T:System.Object" /> to compare with the current <see cref="T:System.Object" />.</param>
        /// <returns><c>true</c> if the specified <see cref="System.Object" /> is equal to this instance; otherwise, <c>false</c>.</returns>
        public override bool Equals(object obj)
        {
            if (ReferenceEquals(null, obj))
            {
                return false;
            }
            if (ReferenceEquals(this, obj))
            {
                return true;
            }
            if (obj.GetType() != this.GetType())
            {
                return false;
            }
            return this.Equals((DoorsParameters)obj);
        }

        [Localizable(false)]
        public override string ToString()
        {
            StringBuilder commandLineOptions = new StringBuilder();
            commandLineOptions.Append(" -d " + this.Database);
            if (!string.IsNullOrEmpty(this.Addins))
            {
                commandLineOptions.Append(" -a " + this.Addins);
            }
            if (!string.IsNullOrEmpty(this.AttributeAddins))
            {
                commandLineOptions.Append(" -A " + this.AttributeAddins);
            }
            if (!string.IsNullOrEmpty(this.DefOpenMode))
            {
                commandLineOptions.Append(" -o " + this.DefOpenMode);
            }
            if (!string.IsNullOrEmpty(this.DefOpenLinkMode))
            {
                commandLineOptions.Append(" -O " + this.DefOpenLinkMode);
            }
            if (!string.IsNullOrEmpty(this.LayoutAddins))
            {
                commandLineOptions.Append(" -L " + this.LayoutAddins);
            }
            if (!string.IsNullOrEmpty(this.LocalData))
            {
                commandLineOptions.Append(" -f " + this.LocalData);
            }
            if (!string.IsNullOrEmpty(this.LogFile))
            {
                commandLineOptions.Append(" -l " + this.LogFile);
            }
            if (!string.IsNullOrEmpty(this.CertName))
            {
                commandLineOptions.Append(" -certName " + this.CertName);
            }
            if (this.Caching)
            {
                commandLineOptions.Append(" -k");
            }
            return commandLineOptions.ToString();
        }

        private static string GetOptionName(string option)
        {
            if (_DoorsAllowedArguments.ContainsKey(option))
            {
                return _DoorsAllowedArguments[option];
            }
            return null;
        }

        private void PopulateFromDoors(string arguments)
        {
            if (string.IsNullOrEmpty(arguments))
            {
                return;
            }
            string[] argument = arguments.Split('\n');
            try
            {
                this.Database = argument[0];
            }
            catch (Exception)
            {
                //DO NOTHING
            }
            try
            {
                this.Addins = argument[1];
            }
            catch (Exception)
            {
                //DO NOTHING
            }
            try
            {
                this.AddinsInRegistry = argument[2];
            }
            catch (Exception)
            {
                //DO NOTHING
            }
            try
            {
                this.AttributeAddins = argument[3];
            }
            catch (Exception)
            {
                //DO NOTHING
            }
            try
            {
                this.DefOpenMode = argument[4];
            }
            catch (Exception)
            {
                //DO NOTHING
            }
            try
            {
                this.DefOpenLinkMode = argument[5];
            }
            catch (Exception)
            {
                //DO NOTHING
            }
            try
            {
                this.LayoutAddins = argument[6];
            }
            catch (Exception)
            {
                //DO NOTHING
            }
            try
            {
                this.LocalData = argument[7];
            }
            catch (Exception)
            {
                //DO NOTHING
            }
            try
            {
                this.LogFile = argument[8];
            }
            catch (Exception)
            {
                //DO NOTHING
            }
            try
            {
                this.DoorsVersion = argument[9];
            }
            catch (Exception)
            {
                //DO NOTHING
            }
        }

        private void PopulateFromEnvironment(string arguments)
        {
            if (string.IsNullOrEmpty(arguments))
            {
                return;
            }
            string[] tmp = arguments.Split('-');
            for (int i = 0; i < tmp.Length; i++)
            {
                if (tmp[i].Length == 0)
                {
                    continue;
                }
                tmp[i] = @"-" + tmp[i];
                string argument = null;
                string[] tmp2 = tmp[i].Split(new[] { ' ' }, 2);
                if (tmp2[0].Length > 1)
                {
                    string option = GetOptionName(tmp2[0]);
                    if (!string.IsNullOrEmpty(option))
                    {
                        if (tmp2.Length > 1 && tmp2[0].Length > 0)
                        {
                            argument = tmp2[1].Trim();
                        }
                        if (!string.IsNullOrEmpty(argument))
                        {
                            switch (option)
                            {
                                case AddinsOption:
                                    this.Addins = argument;
                                    break;
                                case AttributeaddinsOption:
                                    this.AttributeAddins = argument;
                                    break;
                                case DefopenlinkmodeOption:
                                    this.DefOpenLinkMode = argument;
                                    break;
                                case DefopenmodeOption:
                                    this.DefOpenMode = argument;
                                    break;
                                case LayoutaddinsOption:
                                    this.LayoutAddins = argument;
                                    break;
                                case LocaldataOption:
                                    this.LocalData = argument;
                                    break;
                                case LogfileOption:
                                    this.LogFile = argument;
                                    break;
                                case CertnameOption:
                                    this.CertName = argument;
                                    break;
                                case CachingOption:
                                    this.Caching = true;
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }
}