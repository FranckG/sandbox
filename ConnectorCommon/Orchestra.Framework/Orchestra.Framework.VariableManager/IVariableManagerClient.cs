// ----------------------------------------------------------------------------------------------------
// File Name: IVariableManagerClient.cs
// Project: Orchestra.Framework.VariableManager
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.VariableManager
{
    using System;
    using System.Runtime.InteropServices;

    ///<summary>
    /// COM interface for Orchestra Variable Manager
    ///</summary>
    [ComVisible(true)]
    [Guid("E85473BB-0CBB-40b2-99C4-5C0DF23BDBFC")]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface IVariableManagerClient
    {
        ///<summary>
        /// Gets the current context name
        ///</summary>
        [DispId(1)]
        string GetCurrentContextName { get; }

        ///<summary>
        /// Gets all variables
        ///</summary>
        ///<returns>A string array with all variables</returns>
        [DispId(2)]
        string[] GetAllVariables();

        ///<summary>
        /// Gets all categories under <paramref name="path"/>
        ///</summary>
        ///<param name="path">Category path
        /// <example>\Orchestra</example>
        /// </param>
        ///<returns>A string array</returns>
        [DispId(4)]
        string[] GetCategories(string path);

        ///<summary>
        /// Gets the values of a variable
        ///</summary>
        ///<param name="variablePathName">Variable path
        /// <example>\Orchestra\ArtefactPath</example>
        /// </param>
        ///<returns>A string array containing the variable values list</returns>
        [DispId(6)]
        string[] GetVariable(string variablePathName);

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
        [DispId(7)]
        string[] GetVariableLocalized(string variablePathName, string iso6391Language);

        ///<summary>
        /// Gets variables under <paramref name="categoryPath"/>
        ///</summary>
        ///<param name="categoryPath">Category path
        /// <example>\Orchestra</example>
        /// </param>
        ///<returns>A string array containing the variable values list</returns>
        [DispId(8)]
        string[] GetVariables(string categoryPath);

        ///<summary>
        /// Register a tool as a Orchestra Variable Manager consumer.
        ///</summary>
        ///<param name="toolInstanceId">The tool identifier
        /// <example>
        /// Mozart
        /// </example>
        /// </param>
        [DispId(10)]
        void RegisterAsVariableConsumer(string toolInstanceId);

        ///<summary>
        /// Unregister a tool as a Orchestra Variable Manager consumer.
        ///</summary>
        ///<param name="toolInstanceId">The tool identifier
        /// <example>
        /// Mozart
        /// </example>
        /// </param>
        [DispId(11)]
        void UnregisterAsVariableConsumer(string toolInstanceId);

        ///<summary>
        /// Gets all contexts.
        ///</summary>
        ///<returns>A string array containing the context list</returns>
        [DispId(12)]
        string[] GetAllContextNames();

        ///<summary>
        /// Gets the Artefact path variable value.
        ///</summary>
        ///<returns>A string array containing the Artefact path list</returns>
        [DispId(13)]
        string[] GetVariableArtefactPath();

        ///<summary>
        /// Gets the configuration directory (extracted).
        ///</summary>
        ///<returns>The configuration directory (extracted).</returns>
        [DispId(14)]
        string GetVariableConfigurationDirectory();

        ///<summary>
        /// Gets the temporary directory variable value.
        ///</summary>
        ///<returns>The temporary directory.</returns>
        [DispId(15)]
        string GetVariableTemporaryDirectory();

        ///<summary>
        /// Gets the shared directory variable value.
        ///</summary>
        ///<returns>The shared directory.</returns>
        [DispId(16)]
        string GetVariableSharedDirectory();

        ///<summary>
        /// Gets the category separator.
        ///</summary>
        ///<returns>The category separator</returns>
        /// <remarks>
        /// The value of this method is a backslash ("\").
        /// </remarks>
        [DispId(18)]
        string GetCategorySeparator();

        ///<summary>
        /// Gets the Artefact path variable value separated by semicolons.
        ///</summary>
        ///<returns>The Artefact path variable value separated by semicolons.</returns>
        [DispId(19)]
        string GetVariableArtefactPathSeparatedBySemicolons();

        ///<summary>
        /// Gets the Configuration directories separated by semicolons.
        ///</summary>
        ///<returns>The Configuration directories separated by semicolons.</returns>
        [DispId(20)]
        string GetVariableConfigurationDirectoriesSeparatedBySemicolons();

        ///<summary>
        /// Gets the upper index Configuration directory.
        ///</summary>
        ///<returns>The upper index Configuration directory.</returns>
        [DispId(21)]
        string GetVariableLastConfigurationDirectory();

        ///<summary>
        /// Gets the values of a variable  separated by semicolons.
        ///</summary>
        ///<param name="variablePathName">Variable path
        /// <example>\Orchestra\ArtefactPath</example>
        /// </param>
        ///<returns>A string containing the variable values list separated by semicolons.</returns>
        [DispId(22)]
        string GetVariableSeparatedBySemicolons(string variablePathName);

        /// <summary>
        /// Get the Segment name.
        /// </summary>
        /// <returns>The segment name: <c>Gold</c> or <c>Silver</c></returns>
        [DispId(23)]
        string GetSegmentName();
    }
}