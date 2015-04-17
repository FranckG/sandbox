// ----------------------------------------------------------------------------------------------------
// File Name: UriFile.cs
// Project: DoorsConnector
// Copyright (c) Thales, 2013 - 2013. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Connector.Doors
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using Orchestra.Framework.Connector.Doors.DxlCommands;
    using Orchestra.Framework.Core;
    using Orchestra.Framework.Environment.Doors;

    internal class UriFile
    {
        private readonly Command _command;
        private readonly string _filePath;
        private readonly StreamWriter _uriFile;

        public UriFile(Command command)
        {
            this._filePath = Path.GetTempFileName();
            this._uriFile = new StreamWriter(this._filePath);
            this._command = command;
        }

        public string GenerateFile()
        {
            #region Write ODM Variables
            List<OdmVariable> odmVariablesNeeded = this._command.OdmVariablesNeeded;
            if (odmVariablesNeeded != null)
            {
                foreach (OdmVariable variable in odmVariablesNeeded)
                {
                    string valueToWrite = variable.ToString();
                    if (!string.IsNullOrEmpty(valueToWrite))
                    {
                        this._uriFile.WriteLine(valueToWrite);
                    }
                }
            }
            #endregion

            #region Write Artefacts
            foreach (Artefact artefact in this._command.Artefacts)
            {
                this._uriFile.WriteLine(artefact.Uri);
            }
            #endregion

            #region Write Environments properties
            string databaseId = this._command.DoorsParameters.Database;
            foreach (KeyValuePair<string, Dictionary<string, DoorsDatabase>> env in
                ContextConnector.Environments.List)
            {
                if (env.Value.ContainsKey(databaseId))
                {
                    if (env.Value[databaseId].Projects != null)
                    {
                        foreach (KeyValuePair<string, DoorsProject> project in env.Value[databaseId].Projects)
                        {
                            this._uriFile.WriteLine(project.Value.ToString());
                            if (project.Value.Items != null)
                            {
                                foreach (KeyValuePair<string, DoorsItem> item in project.Value.Items)
                                {
                                    this._uriFile.WriteLine(item.Value.ToString());
                                }
                            }
                        }
                    }
                }
            }
            #endregion

            this._uriFile.Close();
            return this._filePath;
        }
    }

    internal class OdmVariable
    {
        private const string StringFormat = "variable!{0}={1}";
        private const string XmlTrue = "true";
        private readonly bool _isBoolean;

        private readonly string _name;
        private readonly string _path;

        public OdmVariable(string name, string path)
            : this(name, path, false)
        {
        }

        public OdmVariable(string name, string path, bool isBoolean)
        {
            this._name = name;
            this._path = path;
            this._isBoolean = isBoolean;
        }

        public override string ToString()
        {
            string value = string.Empty;
            try
            {
                value = VariableManagerSingleton.Instance.VariableManager.GetVariableSeparatedBySemicolons(this._path);
            }
            catch (Exception)
            {
                // DO NOTHING
            }
            if (this._isBoolean)
            {
                bool b = value != null && value.Equals(XmlTrue);
                value = b.ToString().ToLower();
            }
            return string.Format(StringFormat, this._name, value);
        }
    }
}