// ----------------------------------------------------------------------------------------------------
// File Name: VariableManager.cs
// Project: Orchestra.Framework.VariableManager
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.VariableManager
{
    using System;
    using System.Collections.Generic;
    using System.Diagnostics;
    using System.Globalization;
    using System.Runtime.InteropServices;
    using Orchestra.Framework.Utilities;
    using Orchestra.Framework.VariableManager.VariableManagerServer;

    ///<summary>
    /// This class implements communication with the Orchestra Variable Manager
    ///</summary>
    [ComVisible(true)]
    [Guid("48EF0646-378F-43fc-8693-322D980D59AB")]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId("Orchestra.VariableManager")]
    public class Client : IVariableManagerClient
    {
        private const string ConfigurationDirectoriesPath = @"Orchestra\ConfigurationDirectories";
        private const string SegmentVariablePath = @"Orchestra\Segment";
        ///<summary>
        /// string format for product install location
        ///</summary>
        public const string OrchestraProductHomeFormat = @"\Orchestra installation\Products\{0}\InstallLocation";

        private readonly VariableManagerWSAdapterService _variableManagerServer;

        private readonly UriBuilder _wsUri;

        ///<summary>
        /// Constructor
        ///</summary>
        public Client()
        {
            try
            {
                this._variableManagerServer = new VariableManagerWSAdapterService();
                this._wsUri = new UriBuilder(this._variableManagerServer.Url) { Port = new ServerConfigurationFile().ServerPort };
                Trace.TraceInformation("Variable Manager Address: {0}", this._wsUri);
                this._variableManagerServer.Url = this._wsUri.ToString();
            }
            catch (Exception ex)
            {
                EventLogOrchestra.WriteError(
                    60060,
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message,
                    "Orchestra Variable Manager Client");
            }
        }

        #region IVariableManagerClient Members
        ///<summary>
        /// Gets the current context name
        ///</summary>
        public string GetCurrentContextName
        {
            get
            {
                try
                {
                    string name = this._variableManagerServer.getCurrentContextName();
                    Trace.TraceInformation("Current Context: {0}", name);
                    return name;
                }
                catch (Exception ex)
                {
                    Trace.TraceError(ex.ToString());
                    throw new ApplicationException(
                        "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
                }
            }
        }

        ///<summary>
        /// Gets all variables
        ///</summary>
        ///<returns>A string array with all variables</returns>
        public string[] GetAllVariables()
        {
            try
            {
                VariableWS[] variables = this._variableManagerServer.getAllVariables();
                if (variables == null || variables.Length < 1)
                {
                    return null;
                }
                return VariableWsToListOfPath(variables);
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
        }

        ///<summary>
        /// Gets all categories under <paramref name="path"/>
        ///</summary>
        ///<param name="path">Category path
        /// <example>\Orchestra</example>
        /// </param>
        ///<returns>A string array</returns>
        public string[] GetCategories(string path)
        {
            try
            {
                string[] categories = this._variableManagerServer.getCategories(path);
                if (categories == null || categories.Length < 1)
                {
                    return null;
                }
                return categories;
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
        }

        ///<summary>
        /// Gets the values of a variable
        ///</summary>
        ///<param name="variablePathName">Variable path
        /// <example>\Orchestra\ArtefactPath</example>
        /// </param>
        ///<returns>A string array containing the variable values list</returns>
        public string[] GetVariable(string variablePathName)
        {
            if (string.IsNullOrEmpty(variablePathName))
            {
                return null;
            }
            VariableWS variableWs;
            try
            {
                variableWs = this._variableManagerServer.getVariable(variablePathName);
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
            if (variableWs == null)
            {
                Trace.TraceWarning("GetVariable({0}) returns null", variablePathName);
                return null;
            }
            Trace.TraceInformation("GetVariable({0}) returns\n{1}", variablePathName, variableWs.ToString());
            return variableWs.values;
        }

        ///<summary>
        /// Gets the values of a variable according <paramref name="iso6391Language"/>
        /// <para>
        /// For future use
        /// </para>
        ///</summary>
        ///<param name="variablePathName">Variable path
        /// <example>\Orchestra\ArtefactPath</example>
        /// </param>
        ///<param name="iso6391Language"></param>
        ///<returns>A string array</returns>
        public string[] GetVariableLocalized(string variablePathName, string iso6391Language)
        {
            try
            {
                if (!string.IsNullOrEmpty(iso6391Language))
                {
                    CultureInfo.GetCultureInfo(iso6391Language);
                }
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                return null;
            }
            try
            {
                if (string.IsNullOrEmpty(variablePathName))
                {
                    Trace.TraceWarning("GetVariable({0},{1}) returns null", variablePathName, iso6391Language);
                    return null;
                }
                VariableWS variableWs = this._variableManagerServer.getVariableLocalized(
                    variablePathName, iso6391Language);
                if (variableWs == null)
                {
                    return null;
                }
                return variableWs.values;
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
        }

        ///<summary>
        /// Gets variables under <paramref name="categoryPath"/>
        ///</summary>
        ///<param name="categoryPath">Category path
        /// <example>\Orchestra</example>
        /// </param>
        ///<returns>A string array containing the variable values list</returns>
        public string[] GetVariables(string categoryPath)
        {
            try
            {
                VariableWS[] variables = this._variableManagerServer.getVariables(categoryPath);
                if (variables == null || variables.Length < 1)
                {
                    return null;
                }
                return VariableWsToListOfPath(variables);
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
        }

        ///<summary>
        /// Register a tool as a Orchestra Variable Manager consumer.
        ///</summary>
        ///<param name="toolInstanceId">The tool identifier
        /// <example>
        /// Mozart
        /// </example>
        /// </param>
        public void RegisterAsVariableConsumer(string toolInstanceId)
        {
            try
            {
                this._variableManagerServer.registerAsVariableConsumer(toolInstanceId);
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
        }

        ///<summary>
        /// Unregister a tool as a Orchestra Variable Manager consumer.
        ///</summary>
        ///<param name="toolInstanceId">The tool identifier
        /// <example>
        /// Mozart
        /// </example>
        /// </param>
        public void UnregisterAsVariableConsumer(string toolInstanceId)
        {
            try
            {
                this._variableManagerServer.unregisterAsVariableConsumer(toolInstanceId);
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
        }

        ///<summary>
        /// Gets all contexts.
        ///</summary>
        ///<returns>A string array containing the context list</returns>
        public string[] GetAllContextNames()
        {
            string[] contexts = this._variableManagerServer.getAllContextNames();
            if (contexts.Length < 1)
            {
                return null;
            }
            return contexts;
        }

        ///<summary>
        /// Gets the Artefact path variable value.
        ///</summary>
        ///<returns>A string array containing the Artefact path list</returns>
        public string[] GetVariableArtefactPath()
        {
            try
            {
                EnvironmentVariableWS environmentVariable =
                    this._variableManagerServer.getVariableArtefactPath() as EnvironmentVariableWS;
                if (environmentVariable == null)
                {
                    Trace.TraceWarning("GetVariableArtefactPath returns null");
                    return null;
                }
                return ExtractDirectoriesFromEnvironment(environmentVariable);
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
        }

        ///<summary>
        /// Gets the configuration directory (extracted).
        ///</summary>
        ///<returns>The configuration directory (extracted).</returns>
        public string GetVariableConfigurationDirectory()
        {
            try
            {
                VariableWS configurationDirectory = this._variableManagerServer.getVariableConfigurationDirectory();
                if (configurationDirectory == null)
                {
                    Trace.TraceWarning("GetVariableConfigurationDirectory returns null");
                    return null;
                }
                return configurationDirectory.values[0];
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
        }

        ///<summary>
        /// Gets the temporary directory variable value.
        ///</summary>
        ///<returns>The temporary directory.</returns>
        public string GetVariableTemporaryDirectory()
        {
            try
            {
                VariableWS temporaryDirectory = this._variableManagerServer.getVariableTemporaryDirectory();
                if (temporaryDirectory == null)
                {
                    Trace.TraceWarning("GetVariableTemporaryDirectory returns null");
                    return null;
                }
                return temporaryDirectory.values[0];
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
        }

        ///<summary>
        /// Gets the shared directory variable value.
        ///</summary>
        ///<returns>The shared directory.</returns>
        public string GetVariableSharedDirectory()
        {
            try
            {
                VariableWS sharedDirectory = this._variableManagerServer.getVariableSharedDirectory();
                if (sharedDirectory == null)
                {
                    Trace.TraceWarning("GetVariableSharedDirectory returns null");
                    return null;
                }
                return sharedDirectory.values[0];
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
        }

        ///<summary>
        /// Gets the category separator.
        ///</summary>
        ///<returns>The category separator</returns>
        /// <remarks>
        /// The value of this method is a backslash ("\").
        /// </remarks>
        public string GetCategorySeparator()
        {
            try
            {
                return this._variableManagerServer.getCategorySeparator();
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
        }

        ///<summary>
        /// Gets the Artefact path variable value separated by semicolons.
        ///</summary>
        ///<returns>The Artefact path variable value separated by semicolons.</returns>
        public string GetVariableArtefactPathSeparatedBySemicolons()
        {
            return string.Join(";", this.GetVariableArtefactPath());
        }

        ///<summary>
        /// Gets the Configuration directories separated by semicolons.
        ///</summary>
        ///<returns>The Configuration directories separated by semicolons.</returns>
        public string GetVariableConfigurationDirectoriesSeparatedBySemicolons()
        {
            EnvironmentVariableWS environmentVariable =
                this._variableManagerServer.getVariable(ConfigurationDirectoriesPath) as EnvironmentVariableWS;
            string[] paths = ExtractDirectoriesFromEnvironment(environmentVariable);
            return paths == null ? null : string.Join(";", paths);
        }

        ///<summary>
        /// Gets the upper index Configuration directory.
        ///</summary>
        ///<returns>The upper index Configuration directory.</returns>
        public string GetVariableLastConfigurationDirectory()
        {
            try
            {
                string[] configurationDirectories = this.GetVariable(ConfigurationDirectoriesPath);
                return configurationDirectories[configurationDirectories.GetUpperBound(0)];
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
        }

        ///<summary>
        /// Gets the values of a variable  separated by semicolons.
        ///</summary>
        ///<param name="variablePathName">Variable path
        /// <example>\Orchestra\ArtefactPath</example>
        /// </param>
        ///<returns>A string containing the variable values list separated by semicolons.</returns>
        public string GetVariableSeparatedBySemicolons(string variablePathName)
        {
            return string.Join(";", this.GetVariable(variablePathName));
        }

        /// <summary>
        /// Get the Segment name.
        /// </summary>
        /// <returns>The segment name: <c>Gold</c> or <c>Silver</c></returns>
        public string GetSegmentName()
        {
            try
            {
                string[] variableValues = this.GetVariable(SegmentVariablePath);
                if (variableValues != null && variableValues.Length > 0)
                {
                    return variableValues[0];
                }
                return null;
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
        }
        #endregion

        #region static method
        private static string[] ExtractDirectoriesFromEnvironment(EnvironmentVariableWS input)
        {
            if (input == null)
            {
                return null;
            }
            List<string> paths = new List<string>();
            foreach (EnvironmentWS environment in input.environments)
            {
                if (environment.environmentCategory.Equals("File System"))
                {
                    foreach (EnvironmentValueWS value in environment.values)
                    {
                        if (value.key.Equals("directories"))
                        {
                            paths.AddRange(value.values);
                            break;
                        }
                    }
                }
            }
            if (paths.Count == 0)
            {
                return null;
            }
            return paths.ToArray();
        }

        private static string[] VariableWsToListOfPath(IList<VariableWS> input)
        {
            if (input == null)
            {
                return null;
            }
            int variableCount = input.Count;
            if (variableCount == 0)
            {
                return null;
            }
            string[] returnValue = new string[variableCount];

            for (int i = 0; i < variableCount; i++)
            {
                returnValue[i] = input[i].path;
            }
            return returnValue;
        }
        #endregion

        ///<summary>
        /// Gets the values of a variable according <paramref name="iso6391Language"/>
        /// <remarks>
        /// For future use.
        /// </remarks>
        ///</summary>
        ///<param name="iso6391Language"></param>
        ///<returns>An array of <see cref="VariableWS"/>.</returns>
        public VariableWS[] GetAllVariablesLocalized(string iso6391Language)
        {
            try
            {
                try
                {
                    if (!string.IsNullOrEmpty(iso6391Language))
                    {
                        CultureInfo.GetCultureInfo(iso6391Language);
                    }
                }
                catch (Exception ex)
                {
                    Trace.TraceError(ex.ToString());
                    return null;
                }
                VariableWS[] localizedVariables = this._variableManagerServer.getAllVariablesLocalized(iso6391Language);
                if (localizedVariables == null || localizedVariables.Length < 1)
                {
                    return null;
                }
                return localizedVariables;
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
        }

        ///<summary>
        /// Gets the value of a variable.
        ///</summary>
        ///<param name="variablePathName">Variable path
        /// <example>\Orchestra\MyCategory\MyVariable</example>
        /// </param>
        ///<returns>A string array containing the variable value</returns>
        public string GetSubstitutedValue(string variablePathName)
        {
            try
            {
                if (string.IsNullOrEmpty(variablePathName))
                {
                    Trace.TraceInformation("GetSubstitutedValue({0}) returns null", variablePathName);
                    return null;
                }
                string value = this._variableManagerServer.getSubstitutedValue(variablePathName);
                Trace.TraceInformation("GetSubstitutedValue({0}) returns\n{1}", variablePathName, value);
                return value;
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
        }

        ///<summary>
        /// Gets the values of a variable according <paramref name="iso6391Language"/>
        /// <remarks>
        /// For future use.
        /// </remarks>
        ///</summary>
        ///<param name="variablePathName">Variable path
        /// <example>\Orchestra\ArtefactPath</example></param>
        ///<param name="iso6391Language"></param>
        ///<returns></returns>
        public VariableWS[] GetVariablesLocalized(string variablePathName, string iso6391Language)
        {
            try
            {
                if (!string.IsNullOrEmpty(iso6391Language))
                {
                    CultureInfo.GetCultureInfo(iso6391Language);
                }
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                return null;
            }
            try
            {
                VariableWS[] localizedVariables = this._variableManagerServer.getVariablesLocalized(
                    variablePathName, iso6391Language);
                if (localizedVariables == null || localizedVariables.Length < 1)
                {
                    return null;
                }
                return localizedVariables;
            }
            catch (Exception ex)
            {
                Trace.TraceError(ex.ToString());
                throw new ApplicationException(
                    "Orchestra Variable Manager Error!" + System.Environment.NewLine + ex.Message);
            }
        }
    }
}