// ----------------------------------------------------------------------------------------------------
// File Name: OrchestraClient.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2014 - 2014. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Client
{
    using System;
    using System.Collections.Generic;
    using System.Collections.Specialized;
    using System.Globalization;
    using System.IO;
    using System.Linq;
    using System.Runtime.InteropServices;
    using System.Xml.Linq;
    using Orchestra.Framework.Core;
    using Orchestra.Framework.ServerAccess;
    using Orchestra.Framework.Utilities;
    using Orchestra.Framework.Utilities.Security;
    using Orchestra.Framework.Utilities.ServerAccess;

    ///<summary>
    /// This class implements a standard client API to access services related to an artefact.
    ///</summary>
    [ComVisible(true)]
    [Guid("27A3B72E-DCB2-4072-8B71-84146F9F65F0")]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId("OrchestraClientForTool.OrchestraClient")]
    public class OrchestraClient : IOrchestraClient
    {
        #region IOrchestraClient Members
        /// <summary>
        ///  Retrieve credentials data by it's <c>string</c> identifier.
        /// </summary>
        /// <param name="credentialsId"><c>string</c> identifier of credentials data to retrieve.</param>
        /// <param name="credentialsUIPasswordConfirmation">Display optional password confirmation field on user credentials dialog box.</param>
        /// <param name="credentialsUIOnTopMessage">Display optional <c>string</c> message (message can be html formatted (e.g: "<html><b>bold</b></html>") on top of user input credentials dialog box.</param>
        /// <param name="persistence"><c>true</c> if credentials must be persisted, otherwise <c>false</c>. <c>true</c> is default</param>
        /// <returns><see cref="ICredentialsResponse">ICredentialsResponse</see></returns>
        public ICredentialsResponse GetCredentials(
            string credentialsId,
            bool credentialsUIPasswordConfirmation = false,
            string credentialsUIOnTopMessage = "",
            bool persistence = true)
        {
            if (string.IsNullOrEmpty(credentialsId))
            {
                return null;
            }

            Artefact uri = new Artefact { RootType = "FrameworkCommands", RootName = "FC" };
            uri.AddParameter("CredentialsId", credentialsId);
            uri.AddParameter("CredentialsUIPasswordConfirmation", credentialsUIPasswordConfirmation.ToString());
            uri.AddParameter("CredentialsUIOnTopMessage", credentialsUIOnTopMessage);
            uri.AddParameter("IsPersitent", persistence.ToString());

            IOrchestraResponse response = ExecuteUriQuery(
                OrchestraHttpConsts.Command.GetCredentials, new[] { uri.ToString() }, false);

            Credentials credentials = null;
            if (response.ExistsKey("login") && response.ExistsKey("password"))
            {
                credentials = new Credentials(response.Value("login"), response.Value("password"));
            }

            CredentialsUIStatus uiDialogboxStatus;
            if (!response.ExistsKey("uistatus") || !Enum.TryParse(response.Value("uistatus"), out uiDialogboxStatus))
            {
                uiDialogboxStatus = CredentialsUIStatus.UNDEFINED;
            }
            CredentialsResponse credentialsResponse = new CredentialsResponse(credentials, uiDialogboxStatus);
            if (response.ExistsKey("error"))
            {
                credentialsResponse.SetError(true, response.Value("error"));
            }
            return credentialsResponse;
        }

        /// <summary>
        ///  Reset credentials data by <c>string</c> identifer.
        /// </summary>
        /// <param name="credentialsId"><c>string</c> identifier of credentials to reset</param>
        /// <param name="persistence"><c>true</c> if credentials must be persisted, otherwise <c>false</c>. <c>true</c> is default</param>
        /// <returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        public IOrchestraResponse ResetCredentials(string credentialsId, bool persistence = true)
        {
            if (string.IsNullOrEmpty(credentialsId))
            {
                OrchestraResponse response = new OrchestraResponse();
                NameValueCollection nameValue = new NameValueCollection
                    {
                        { OrchestraHttpConsts.Message, "A credential id is required so as to reset credentials data." }
                    };
                response.Add(nameValue);
                return response;
            }

            Artefact uri = new Artefact { RootType = "FrameworkCommands", RootName = "FC" };
            uri.AddParameter("CredentialsId", credentialsId);
            uri.AddParameter("IsPersitent", persistence.ToString());

            return ExecuteUriQuery(OrchestraHttpConsts.Command.ResetCredentials, new[] { uri.ToString() }, false);
        }
        #endregion

        /// <summary>
        /// Change Framework current context to specified one
        /// Ask user if an error occurs while changing context.
        /// </summary>
        /// <param name="contextName">The context to apply, specified by its (unique) name.</param>
        /// <returns><see cref="IOrchestraResponse"/></returns>
        public IOrchestraResponse ChangeContext(string contextName)
        {
            return this.ChangeContext(contextName, true);
        }

        private static string CompressParameterValues(string[] rootTypes)
        {
            if (rootTypes == null)
            {
                return null;
            }
            if (!rootTypes.Any())
            {
                return null;
            }
            return string.Join(OrchestraHttpConsts.Command.DefaultParameterSeparator, rootTypes);
        }

        private static OrchestraResponse ExecuteUriQuery(string command, ICollection<string> uris, bool verifyingUris)
        {
            if (verifyingUris)
            {
                if (uris == null || uris.Count == 0)
                {
                    return null;
                }
                if (uris.Any(string.IsNullOrEmpty))
                {
                    return null;
                }
            }
            UriQuery query = new UriQuery(command, uris);
            ClientRequest request = new ClientRequest(query);

            try
            {
                HttpResponse httpResponse = request.SendPostRequest();
                return httpResponse.Response;
            }
            catch (Exception ex)
            {
                OrchestraResponse response = new OrchestraResponse();

                response.AddKeyValue(OrchestraHttpConsts.ErrorCode, "1400");
                response.AddKeyValue(OrchestraHttpConsts.Message, ex.Message);
                return response;
            }
        }

        #region Implementation of IOrchestraClient
        ///<summary>
        /// Create artefact(s) from <paramref name="uris"/>
        ///</summary>
        ///<param name="uris">List of Orchestra Uri, <see cref="Artefact"/></param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        public IOrchestraResponse create(string[] uris)
        {
            return ExecuteUriQuery(OrchestraHttpConsts.Command.Create, uris, true);
        }

        ///<summary>
        /// Do a documentary export for <paramref name="uris"/>
        ///</summary>
        ///<param name="uris">List of Orchestra Uri, <see cref="Artefact"/></param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        public IOrchestraResponse documentaryExport(string[] uris)
        {
            return ExecuteUriQuery(OrchestraHttpConsts.Command.ExportDoc, uris, true);
        }

        ///<summary>
        /// Do an expand export for <paramref name="uris"/>
        ///</summary>
        ///<param name="uris">List of Orchestra Uri, <see cref="Artefact"/></param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        public IOrchestraResponse expand(string[] uris)
        {
            return ExecuteUriQuery(OrchestraHttpConsts.Command.Expand, uris, true);
        }

        ///<summary>
        /// Retrieve the logical name of each <paramref name="projectsPaths"/>
        ///</summary>
        ///<param name="projectsPaths">List of projects paths</param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        public IOrchestraResponse getLogicalName(string[] projectsPaths)
        {
            List<string> uris = new List<string>();
            NameValueCollection pathToTechnicalUri = new NameValueCollection();

            foreach (string path in projectsPaths)
            {
                if (!File.Exists(path))
                {
                    return null;
                }
                Artefact artefact = new Artefact { RootType = OrchestraHttpConsts.FileSystemTool, RootName = path };
                uris.Add(artefact.ToString());
                pathToTechnicalUri.Add(path, artefact.Uri);
            }
            OrchestraResponse response = ExecuteUriQuery(OrchestraHttpConsts.Command.Transcription, uris, true);
            if (response != null)
            {
                response.Add(pathToTechnicalUri);
            }
            return response;
        }

        ///<summary>
        /// Retrieve the projects paths of each <paramref name="uris"/>
        ///</summary>
        ///<param name="uris">List of Orchestra Uri, <see cref="Artefact"/></param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        public IOrchestraResponse getPhysicalPath(string[] uris)
        {
            return ExecuteUriQuery(OrchestraHttpConsts.Command.Transcription, uris, true);
        }

        ///<summary>
        /// Retrieve root artefacts
        ///</summary>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        public IOrchestraResponse getRootArtifacts()
        {
            return ExecuteUriQuery(OrchestraHttpConsts.Command.GetRootArtifacts, null, false);
        }

        ///<summary>
        /// Do an export for the link manager for <paramref name="uris"/>
        ///</summary>
        ///<param name="uris">List of Orchestra Uri, <see cref="Artefact"/></param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        public IOrchestraResponse lmExport(string[] uris)
        {
            return ExecuteUriQuery(OrchestraHttpConsts.Command.ExportLm, uris, true);
        }

        ///<summary>
        /// Navigate to each <paramref name="uris"/>
        ///</summary>
        ///<param name="uris">List of Orchestra Uri, <see cref="Artefact"/></param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        public IOrchestraResponse navigate(string[] uris)
        {
            return ExecuteUriQuery(OrchestraHttpConsts.Command.Navigate, uris, true);
        }

        ///<summary>
        /// Execute the <paramref name="command"/> for <paramref name="uris"/>
        ///</summary>
        ///<param name="command">The specific command to execute</param>
        ///<param name="uris">List of Orchestra Uri, <see cref="Artefact"/></param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        public IOrchestraResponse executeSpecificCommand(string command, string[] uris)
        {
            return ExecuteUriQuery(command, uris, true);
        }

        ///<summary>
        /// Get metadata from environment to each <paramref name="uris"/>
        ///</summary>
        ///<param name="uris">List of Orchestra Uri, <see cref="Artefact"/></param>
        ///<returns><see cref="IOrchestraResponse">IOrchestraResponse</see></returns>
        public IOrchestraResponse getArtefactsMetadata(string[] uris)
        {
            return ExecuteUriQuery(OrchestraHttpConsts.Command.GetArtefactsMetadata, uris, true);
        }

        ///<summary>
        /// Retrieve the logical name for a single project path
        ///</summary>
        ///<param name="projectPath">Physical path of the project</param>
        ///<returns>Logical Name</returns>
        public string getLogicalNameSingleResult(string projectPath)
        {
            try
            {
                IOrchestraResponse response = this.getLogicalName(new[] { projectPath });
                StatusDefinition sd = response.GetStatusDefinition();
                string rootName = "";
                if (sd != null && sd.status != null)
                {
                    List<Status> statuses = sd.status.FindStatusesWithPartialUri(projectPath);
                    if (statuses.Count == 1)
                    {
                        Artefact a = new Artefact { Uri = statuses[0].message };
                        rootName = a.RootName;
                    }
                }
                return rootName;
            }
            catch (Exception)
            {
                return "";
            }
        }

        /// <summary>
        /// Change Framework current context to specified one.
        /// </summary>
        /// <param name="contextName">The context to apply, specified by its (unique) name.</param>
        /// <param name="askUserOnError"><c>true</c> to display an error dialog to the user - when needed -,
        /// <c>false</c> not to present errors to user.</param>
        /// <returns>
        ///   <see cref="IOrchestraResponse">IOrchestraResponse</see>
        /// </returns>
        public IOrchestraResponse ChangeContext(string contextName, bool askUserOnError)
        {
            if (string.IsNullOrEmpty(contextName))
            {
                OrchestraResponse response = new OrchestraResponse();
                NameValueCollection nameValue = new NameValueCollection
                    {
                        {
                            OrchestraHttpConsts.Message,
                            "A context name is required so as to change current Framework context."
                        }
                    };
                response.Add(nameValue);
                return response;
            }
            Artefact uri = new Artefact { RootType = "FrameworkCommands", RootName = "FC" };
            uri.AddParameter("ContextName", contextName);
            uri.AddParameter("AskUser", askUserOnError.ToString(CultureInfo.InvariantCulture));
            return ExecuteUriQuery(OrchestraHttpConsts.Command.ChangeContext, new[] { uri.ToString() }, false);
        }

        /// <summary>
        /// Change ODM mode to specified mode.
        /// </summary>
        /// <param name="modeName">Name of the mode. Allowed values are "Administrator" and "User".</param>
        /// <returns>
        ///   <see cref="IOrchestraResponse">IOrchestraResponse</see>
        /// </returns>
        public IOrchestraResponse ChangeODMMode(string modeName)
        {
            if (string.IsNullOrEmpty(modeName))
            {
                OrchestraResponse response = new OrchestraResponse();
                NameValueCollection nameValue = new NameValueCollection
                    {
                        { OrchestraHttpConsts.Message, "A mode is required so as to change current ODM mode." }
                    };
                response.Add(nameValue);
                return response;
            }
            if (!modeName.Equals("Administrator") && !modeName.Equals("User"))
            {
                OrchestraResponse response = new OrchestraResponse();
                NameValueCollection nameValue = new NameValueCollection
                    {
                        { OrchestraHttpConsts.Message, "Only mode \"Administrator\" or \"User\" are allowed." }
                    };
                response.Add(nameValue);
                return response;
            }
            Artefact uri = new Artefact { RootType = "FrameworkCommands", RootName = "FC" };
            uri.AddParameter("Mode", modeName);
            return ExecuteUriQuery(OrchestraHttpConsts.Command.ChangeMode, new[] { uri.ToString() }, false);
        }

        /// <summary>
        /// Create a set of artifacts URI from specified logical folder path and root type.
        /// A logical folder path is a prefix path in a root artifact logical name (as displayed by the Orchestra Explorer in folding mode).
        /// It is recommended to use the '/' character as a separator in logical folder path, instead of the '\' one.
        /// A set of artifacts is expandable (through a dedicated service) and returns all matching (logical) sub-artifacts.
        /// </summary>
        /// <param name="logicalFolderPath">The logical folder path.</param>
        /// <param name="rootTypes">A list of <see cref="string"/> root types that are to be taken into account into this search URI.
        /// Only root types specified here will be returned by the corresponding <see cref="ExpandArtefactsSet"/> service.
        /// Note that if no root type should be specified, either <code>null</code> or an empty list are acceptable values.
        /// These parameter is compressed into a single URI parameter made of the different provided types (in their original order), separated by the
        /// <see cref="OrchestraHttpConsts.Command.DefaultParameterSeparator"/> separator (hopefully unlikely to be found in the types content).</param>
        /// <returns>
        ///   <see cref="IUri">IUri</see>
        /// </returns>
        public IUri CreateArtefactSetUri(string logicalFolderPath, [In] ref string[] rootTypes)
        {
            if (string.IsNullOrEmpty(logicalFolderPath))
            {
                return null;
            }
            Artefact result = new Artefact { RootType = "FrameworkCommands", RootName = "ASU" };
            result.AddParameter("LogicalFolderPath", logicalFolderPath);
            if (rootTypes != null && rootTypes.Any())
            {
                result.AddParameter("RootTypes", CompressParameterValues(rootTypes));
            }
            return result;
        }

        /// <summary>
        /// Create a migration URI from specified textual representation of an existing URI to migrate.
        /// The existing URI can either be an Orchestra V4 URI/URL or an Orchestra V5.X URI that needs migration.
        /// </summary>
        /// <param name="uriToMigrate">A not <c>null</c> textual URI to migrate.</param>
        /// <returns>
        ///   <c>null</c> if parameter is invalid, a new migration URI otherwise.
        /// </returns>
        public IUri CreateMigrationUri(string uriToMigrate)
        {
            if (string.IsNullOrEmpty(uriToMigrate))
            {
                return null;
            }
            Artefact result = new Artefact { RootType = "Migration", RootName = "Migration" };
            result.AddParameter("UriToMigrate", uriToMigrate);
            return result;
        }

        /// <summary>
        /// Create a search URI from specified one and search path.
        /// </summary>
        /// <param name="seedUri">The artifact that is the seed of the search.
        /// It doesn't have to be a root artifact, it can be any artifact.
        /// Only its subtree is considered while searching (seed artifact included).The seed artifact can not be <c>null</c>.</param>
        /// <param name="paths">A list of <see cref="string"/> segments that are naming rules making sense to access to a specific artifact from seed one.
        /// This is the decomposition of the path that leads to the expected artifact. This is a list as the order is required.
        /// This path is compressed into a single URI parameter made of the different provided segments (in their original order), separated by the
        /// <see cref="OrchestraHttpConsts.Command.DefaultParameterSeparator"/> separator (hopefully unlikely to be found in the segments content).</param>
        /// <returns><see cref="IUri">IUri</see></returns>
        public IUri CreateSearchUri(string seedUri, [In] ref string[] paths)
        {
            if (string.IsNullOrEmpty(seedUri) || paths == null)
            {
                return null;
            }
            Artefact result = new Artefact { Uri = seedUri };
            if (paths.Any())
            {
                result.AddParameter(
                    OrchestraHttpConsts.Command.UriParameterSearchRelativePath, CompressParameterValues(paths));
            }
            result.ClearParameters();
            return result;
        }

        /// <summary>
        /// Expand specified artefacts set <paramref name="uris"/>.
        /// </summary>
        /// <param name="uris">A list of artifacts set URIs.
        /// See <see cref="CreateArtefactSetUri"/> for details about the format of such an URI.</param>
        /// <returns>
        ///   <see cref="IOrchestraResponse">IOrchestraResponse</see>
        /// </returns>
        public IOrchestraResponse ExpandArtefactsSet([In] ref string[] uris)
        {
            return ExecuteUriQuery(OrchestraHttpConsts.Command.ExpandArtefactSet, uris, true);
        }

        /// <summary>
        /// Migrate specified migration URIs.
        /// Note that the URIs are to comply with what the <see cref="CreateMigrationUri"/> method returns.
        /// </summary>
        /// <param name="uris">A list of migration URIs. See <see cref="CreateMigrationUri"/> for details about the format of such an URI.</param>
        /// <returns>
        ///   <see cref="IOrchestraResponse">IOrchestraResponse</see>
        /// </returns>
        public IOrchestraResponse Migrate([In] ref string[] uris)
        {
            return ExecuteUriQuery(OrchestraHttpConsts.Command.UriMigration, uris, true);
        }

        /// <summary>
        /// Do invoke search service for specified URIs.
        /// </summary>
        /// <param name="uris">A list of search URIs. See <see cref="CreateSearchUri"/> for details about the format of such an URI.</param>
        /// <param name="keepOpen"><c>true</c> to keep any required application opened,
        /// because it is likely another service will be issued in a near future (after the search).
        /// <c>false</c> to force applications to close when the search as ended.</param>
        /// <returns>
        ///   <see cref="IOrchestraResponse">IOrchestraResponse</see>
        /// </returns>
        public IOrchestraResponse search(ref string[] uris, bool keepOpen)
        {
            if (uris == null || !uris.Any())
            {
                return null;
            }
            if (uris.Any(string.IsNullOrEmpty))
            {
                return null;
            }
            List<string> realUris = new List<string>();
            foreach (string uri in uris)
            {
                Artefact artefact = new Artefact { Uri = uri };
                artefact.AddParameter(
                    OrchestraHttpConsts.Command.UriParameterSearchKeepOpen,
                    keepOpen.ToString(CultureInfo.InvariantCulture));
                realUris.Add(artefact.Uri);
            }
            return ExecuteUriQuery(OrchestraHttpConsts.Command.Search, realUris, false);
        }
        #endregion
    }

    ///<summary>
    /// This class implements the response of a <see cref="OrchestraClient"/> call.
    ///</summary>
    [ComVisible(true)]
    [Guid("7D20568D-BD2E-427D-831B-89B81DE43E46")]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId("OrchestraClientForTool.OrchestraResponse")]
    public class OrchestraResponse : IOrchestraResponse
    {
        private readonly NameValueCollection _response;
        private XElement _status;

        ///<summary>
        /// Constructor
        ///</summary>
        public OrchestraResponse()
        {
            this._response = new NameValueCollection();
        }

        #region IOrchestraResponse Members
        ///<summary>
        /// Retrieve all keys of response
        ///</summary>
        ///<returns>a list of keys</returns>
        public string[] AllKeys()
        {
            return this._response.AllKeys;
        }

        ///<summary>
        /// Retrieve all values of response
        ///</summary>
        ///<returns>a list of values</returns>
        public string[] AllValues()
        {
            string[] result = new string[this._response.Count];
            this._response.CopyTo(result, 0);
            return result;
        }

        ///<summary>
        /// Retrieve if a key exists
        ///</summary>
        ///<param name="key">key</param>
        ///<returns><c>true</c> if <paramref name="key"/> exists otherwise <c>false</c></returns>
        public bool ExistsKey(string key)
        {
            if (String.IsNullOrEmpty(key) || (this._response == null))
            {
                return false;
            }
            return (this._response.Get(key) != null);
        }

        ///<summary>
        /// Retrieve value for the <paramref name="key"/>
        ///</summary>
        ///<param name="key">key</param>
        ///<returns>Value of <paramref name="key"/></returns>
        public string Value(string key)
        {
            return this._response[key];
        }

        ///<summary>
        /// Retrieve status definition of response
        ///</summary>
        ///<returns>See <see cref="IStatusDefinition"/></returns>
        public StatusDefinition GetStatusDefinition()
        {
            string responseFile = this.Value(OrchestraHttpConsts.ResponseFilePath);
            if (responseFile != null && File.Exists(responseFile))
            {
                string fileContent = File.ReadAllText(responseFile);
                StatusDefinition s = new StatusDefinition();
                s.SetStatusDefinition(fileContent);
                return s;
            }
            return null;
        }

        /// <summary>
        /// Retrieve the full path of Gef File.
        /// Warning! Use this method only to retrieve the Gef file after using
        /// <see cref="OrchestraClient.getRootArtifacts"/> or
        /// <see cref="OrchestraClient.getArtefactsMetadata"/>
        /// </summary>
        /// <returns>Gef file full path</returns>
        public string GetGefFileFullPath()
        {
            this.LoadStatusFile();
            if (this._status != null)
            {
                try
                {
                    return (from el in this._status.Descendants("status")
                            where el.Attribute("exportFilePath") != null
                            select el.Attribute("exportFilePath").Value).FirstOrDefault();
                }
                catch (Exception)
                {
                    //DO NOTHING
                }
            }
            return string.Empty;
        }

        /// <summary>
        /// Gets the gef file full path for an URI.
        /// </summary>
        /// <param name="uri">The URI.</param>
        /// <returns>Full path of Gef file</returns>
        public string GetGefFileFullPathForAnUri(string uri)
        {
            this.LoadStatusFile();
            if (this._status != null)
            {
                try
                {
                    IEnumerable<string> gefFilesFullPath = from el in this._status.Descendants("status")
                                                           where
                                                               el.Attribute("exportFilePath") != null
                                                               && el.DescendantsAndSelf("status")
                                                                    .Any(
                                                                        a =>
                                                                        a.Attribute("uri") != null
                                                                        && a.Attribute("severity") != null
                                                                        && a.Attribute("uri").Value == uri
                                                                        && a.Attribute("severity").Value
                                                                        != StatusSeverity.ERROR.ToString())
                                                           select el.Attribute("exportFilePath").Value;
                    IList<string> filesFullPath = gefFilesFullPath as IList<string> ?? gefFilesFullPath.ToList();
                    if (filesFullPath.Count() == 1)
                    {
                        return filesFullPath[0];
                    }
                }
                catch (Exception)
                {
                    //DO NOTHING
                }
            }
            return string.Empty;
        }
        #endregion

        internal void Add(NameValueCollection collection)
        {
            this._response.Add(collection);
        }

        internal void AddKeyValue(string key, string value)
        {
            this._response.Add(Rfc3986.Decode(key), Rfc3986.Decode(value));
        }

        private void LoadStatusFile()
        {
            if (this._status == null && this.ExistsKey(OrchestraHttpConsts.ResponseFilePath))
            {
                string statusFile = this.Value(OrchestraHttpConsts.ResponseFilePath);
                if (File.Exists(statusFile))
                {
                    try
                    {
                        this._status = XElement.Load(statusFile);
                    }
                    catch (Exception)
                    {
                        //DO NOTHING
                    }
                }
            }
        }
    }
}