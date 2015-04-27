// ----------------------------------------------------------------------------------------------------
// File Name: IOrchestraClient.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2014 - 2014. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Client
{
    using System.Runtime.InteropServices;
    using Orchestra.Framework.Core;
    using Orchestra.Framework.ServerAccess;
    using Orchestra.Framework.Utilities;
    using Orchestra.Framework.Utilities.Security;

    /// <summary>
    /// COM Interface for Orchestra Client
    /// See <see cref="OrchestraClient"/> for more informations.
    /// </summary>
    [ComVisible(true)]
    [Guid("4C2C2273-A01F-44eb-84FB-F8EC45723C93")]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface IOrchestraClient
    {
        ///<summary>
        /// Create artefact(s) from <paramref name="uris"/>
        ///</summary>
        ///<param name="uris">List of Orchestra Uri, <see cref="Artefact"/></param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        IOrchestraResponse create(string[] uris);

        ///<summary>
        /// Do a documentary export for <paramref name="uris"/>
        ///</summary>
        ///<param name="uris">List of Orchestra Uri, <see cref="Artefact"/></param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        IOrchestraResponse documentaryExport(string[] uris);

        ///<summary>
        /// Do an expand export for <paramref name="uris"/>
        ///</summary>
        ///<param name="uris">List of Orchestra Uri, <see cref="Artefact"/></param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        IOrchestraResponse expand(string[] uris);

        ///<summary>
        /// Retrieve the logical name of each <paramref name="projectsPaths"/>
        ///</summary>
        ///<param name="projectsPaths">List of projects paths</param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        IOrchestraResponse getLogicalName(string[] projectsPaths);

        ///<summary>
        /// Retrieve the projects paths of each <paramref name="uris"/>
        ///</summary>
        ///<param name="uris">List of Orchestra Uri, <see cref="Artefact"/></param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        IOrchestraResponse getPhysicalPath(string[] uris);

        ///<summary>
        /// Retrieve root artefacts
        ///</summary>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        IOrchestraResponse getRootArtifacts();

        ///<summary>
        /// Do an export for the link manager for <paramref name="uris"/>
        ///</summary>
        ///<param name="uris">List of Orchestra Uri, <see cref="Artefact"/></param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        IOrchestraResponse lmExport(string[] uris);

        ///<summary>
        /// Navigate to each <paramref name="uris"/>
        ///</summary>
        ///<param name="uris">List of Orchestra Uri, <see cref="Artefact"/></param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        IOrchestraResponse navigate(string[] uris);

        ///<summary>
        /// Execute the <paramref name="command"/> for <paramref name="uris"/>
        ///</summary>
        ///<param name="command">The specific command to execute</param>
        ///<param name="uris">List of Orchestra Uri, <see cref="Artefact"/></param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        IOrchestraResponse executeSpecificCommand(string command, string[] uris);

        ///<summary>
        /// Get metadata from environment to each <paramref name="uris"/>
        ///</summary>
        ///<param name="uris">List of Orchestra Uri, <see cref="Artefact"/></param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        IOrchestraResponse getArtefactsMetadata(string[] uris);

        /// <summary>
        ///  Retrieve credentials data by it's <c>string</c> identifier.
        /// </summary>
        /// <param name="credentialsId"><c>string</c> identifier of credentials data asked</param>
        /// <param name="credentialsUIPasswordConfirmation">Display optional password confirmation field on user input credentials dialog box.</param>
        /// <param name="credentialsUIOnTopMessage">Display optional <c>string</c> message on top of user input credentials dialog box.</param>
        /// <param name="persistence"><c>true</c> if credentials must be persisted, otherwise <c>false</c>. <c>true</c> is default</param>
        /// <returns><see cref="ICredentialsResponse">ICredentialsResponse</see></returns>
        ICredentialsResponse GetCredentials(
            string credentialsId,
            bool credentialsUIPasswordConfirmation = false,
            string credentialsUIOnTopMessage = "",
            bool persistence = true);

        /// <summary>
        ///  Reset credentials data by <c>string</c> identifer.
        /// </summary>
        /// <param name="credentialsId"><c>string</c> identifier of credentials to reset</param>
        /// <param name="persistence"><c>true</c> if credentials must be persisted, otherwise <c>false</c>. <c>true</c> is default</param>
        /// <returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        IOrchestraResponse ResetCredentials(string credentialsId, bool persistence = true);

        ///<summary>
        /// Retrieve the logical name for a single project path
        ///</summary>
        ///<param name="projectPath">Physical path of the project</param>
        ///<returns>Logical Name</returns>
        string getLogicalNameSingleResult(string projectPath);

        /// <summary>
        /// Change Framework current context to specified one.
        /// </summary>
        /// <param name="contextName">The context to apply, specified by its (unique) name.</param>
        /// <param name="askUserOnError"><c>true</c> to display an error dialog to the user - when needed -,
        /// <c>false</c> not to present errors to user.</param>
        /// <returns> <see cref="IOrchestraResponse">IOrchestraResponse</see> </returns>
        IOrchestraResponse ChangeContext(string contextName, bool askUserOnError);

        /// <summary>
        /// Change ODM mode to specified mode.
        /// </summary>
        /// <param name="modeName">Name of the mode. Allowed values are "Administrator" and "User".</param>
        /// <returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        IOrchestraResponse ChangeODMMode(string modeName);

        /// <summary>
        /// Create a set of artifacts URI from specified logical folder path and root type.
        /// A logical folder path is a prefix path in a root artefact logical name (as displayed by the Orchestra Explorer in folding mode).
        /// It is recommended to use the '/' character as a separator in logical folder path, instead of the '\' one.
        /// A set of artifacts is expandable (through a dedicated service) and returns all matching (logical) sub-artifacts.
        /// </summary>
        /// <param name="logicalFolderPath">The logical folder path.</param>
        /// <param name="rootTypes">A list of <see cref="string"/> root types that are to be taken into account into this search URI.
        /// Only root types specified here will be returned by the corresponding <see cref="ExpandArtefactsSet"/> service.
        /// Note that if no root type should be specified, either <code>null</code> or an empty list are acceptable values.
        /// These parameter is compressed into a single URI parameter made of the different provided types (in their original order), separated by the
        /// <see cref="OrchestraHttpConsts.Command.DefaultParameterSeparator"/> separator (hopefully unlikely to be found in the types content).</param>
        /// <returns><see cref="IUri">IUri</see></returns>
        IUri CreateArtefactSetUri(string logicalFolderPath, [In] ref string[] rootTypes);

        /// <summary>
        /// Create a migration URI from specified textual representation of an existing URI to migrate.
        /// The existing URI can either be an Orchestra V4 URI/URL or an Orchestra V5.X URI that needs migration.
        /// </summary>
        /// <param name="uriToMigrate">A not <c>null</c> textual URI to migrate.</param>
        /// <returns><c>null</c> if parameter is invalid, a new migration URI otherwise.</returns>
        IUri CreateMigrationUri(string uriToMigrate);

        /// <summary>
        /// Create a search URI from specified one and search path.
        /// </summary>
        /// <param name="seedUri">The artefact that is the seed of the search.
        /// It doesn't have to be a root artefact, it can be any artefact.
        /// Only its subtree is considered while searching (seed artefact included).The seed artefact can not be <c>null</c>.</param>
        /// <param name="paths">A list of <see cref="string"/> segments that are naming rules making sense to access to a specific artefact from seed one.
        /// This is the decomposition of the path that leads to the expected artefact. This is a list as the order is required.
        /// This path is compressed into a single URI parameter made of the different provided segments (in their original order), separated by the
        /// <see cref="OrchestraHttpConsts.Command.DefaultParameterSeparator"/> separator (hopefully unlikely to be found in the segments content).</param>
        /// <returns><c>null</c> if parameter is invalid, a new migration URI otherwise.</returns>
        IUri CreateSearchUri(string seedUri, [In] ref string[] paths);

        /// <summary>
        /// Expand specified artefacts set <paramref name="uris"/>.
        /// </summary>
        /// <param name="uris">A list of artifacts set URIs.
        /// See <see cref="CreateArtefactSetUri"/> for details about the format of such an URI.</param>
        /// <returns>
        /// <see cref="IOrchestraResponse">IOrchestraResponse</see>
        /// </returns>
        IOrchestraResponse ExpandArtefactsSet([In] ref string[] uris);

        /// <summary>
        /// Migrate specified migration URIs.
        /// Note that the URIs are to comply with what the <see cref="CreateMigrationUri"/> method returns.
        /// </summary>
        /// <param name="uris">A list of migration URIs. See <see cref="CreateMigrationUri"/> for details about the format of such an URI.</param>
        /// <returns>
        /// <see cref="IOrchestraResponse">IOrchestraResponse</see>
        /// </returns>
        IOrchestraResponse Migrate([In] ref string[] uris);

        /// <summary>
        /// Do invoke search service for specified URIs.
        /// </summary>
        /// <param name="uris">A list of search URIs. See <see cref="CreateSearchUri"/> for details about the format of such an URI.</param>
        /// <param name="keepOpen"><c>true</c> to keep any required application opened,
        /// because it is likely another service will be issued in a near future (after the search).
        /// <c>false</c> to force applications to close when the search as ended.
        /// </param>
        /// <returns>
        /// <see cref="IOrchestraResponse">IOrchestraResponse</see>
        /// </returns>
        IOrchestraResponse search([In] ref string[] uris, bool keepOpen);
    }

    ///<summary>
    /// COM Interface for Orchestra Response
    /// <see cref="OrchestraResponse"/> for more informations.
    /// </summary>
    [ComVisible(true)]
    [Guid("49F82390-E768-4730-8D94-7582B811161D")]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface IOrchestraResponse
    {
        ///<summary>
        /// Retrieve all keys of response
        ///</summary>
        ///<returns>a list of keys</returns>
        string[] AllKeys();

        ///<summary>
        /// Retrieve all values of response
        ///</summary>
        ///<returns>a list of values</returns>
        string[] AllValues();

        ///<summary>
        /// Retrieve if a key exists
        ///</summary>
        ///<param name="key">key</param>
        ///<returns><c>true</c> if <paramref name="key"/> exists otherwise <c>false</c></returns>
        bool ExistsKey(string key);

        ///<summary>
        /// Retrieve value for the <paramref name="key"/>
        ///</summary>
        ///<param name="key">key</param>
        ///<returns>Value of <paramref name="key"/></returns>
        string Value(string key);

        ///<summary>
        /// Retrieve status definition of response
        ///</summary>
        ///<returns>See <see cref="IStatusDefinition"/></returns>
        StatusDefinition GetStatusDefinition();

        /// <summary>
        /// Retrieve full path of Gef file.
        /// Warning! Use this method only to retrieve the Gef file after using <see cref="OrchestraClient.getRootArtifacts"/>
        /// </summary>
        /// <returns>Full path of Gef file</returns>
        string GetGefFileFullPath();

        /// <summary>
        /// Gets the gef file full path for an URI.
        /// </summary>
        /// <param name="uri">The URI.</param>
        /// <returns>Full path of Gef file</returns>
        string GetGefFileFullPathForAnUri(string uri);
    }
}