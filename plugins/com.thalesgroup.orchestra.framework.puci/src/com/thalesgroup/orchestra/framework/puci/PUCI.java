/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.puci;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.thalesgroup.orchestra.framework.lib.constants.ICommandConstants;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.BadOrchestraURIException;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

/**
 * <p>
 * PUCI : Papeete Universal Client Interface.
 * </p>
 * <br>
 * The PUCI defines a standard client API to access services related to an artifact. </br> <br>
 * @author S0024585
 */
public final class PUCI {
  /**
   * Change Framework current context to specified one.<br>
   * Ask user if an error occurs while changing context.
   * @param contextName_p The context to apply, specified by its (unique) name.
   * @return A map containing the results data, or <code>null</code> if the {@link OrchestraURI} {@link List} is <code>null</code>, empty or contains a
   *         <code>null</code> {@link OrchestraURI}.
   * @throws Exception
   */
  public static Map<String, String> changeContext(String contextName_p) throws Exception {
    return changeContext(contextName_p, true);
  }

  /**
   * Change Framework current context to specified one.
   * @param contextName_p The context to apply, specified by its (unique) name.
   * @param allowUserInteractions_p <code>true</code> to allow user interactions while switching and display dialogs, <code>false</code> not to present user
   *          interactions.<br>
   *          In both cases, the code is run synchronously, but in the second one (ie <code>false</code>) an invalid context is still applied, whereas this is
   *          not possible from UI behavior.
   * @return A map containing the results data, or <code>null</code> if the {@link OrchestraURI} {@link List} is <code>null</code>, empty or contains a
   *         <code>null</code> {@link OrchestraURI}.
   * @throws Exception
   */
  @SuppressWarnings("nls")
  public static Map<String, String> changeContext(String contextName_p, boolean allowUserInteractions_p) throws Exception {
    if (null == contextName_p) {
      Map<String, String> result = new HashMap<String, String>(1);
      result.put(PapeeteHTTPKeyRequest.__MESSAGE, "A context name is required so as to change current Framework context.");
      return result;
    }
    OrchestraURI uri = new OrchestraURI("FrameworkCommands", "FC");
    uri.addParameter("ContextName", contextName_p);
    uri.addParameter("AskUser", Boolean.toString(allowUserInteractions_p));
    return doRequest("ChangeContext", Collections.singletonList(uri));
  }

  /**
   * Change ODM mode to specified mode.
   * @param mode_p The new mode to apply.
   * @return A map containing the results data, or <code>null</code> if the {@link OrchestraURI} {@link List} is <code>null</code>, empty or contains a
   *         <code>null</code> {@link OrchestraURI}.
   * @throws Exception
   */
  @SuppressWarnings("nls")
  public static Map<String, String> changeODMMode(OdmMode mode_p) throws Exception {
    if (null == mode_p) {
      Map<String, String> result = new HashMap<String, String>(1);
      result.put(PapeeteHTTPKeyRequest.__MESSAGE, "A mode is required so as to change current ODM mode.");
      return result;
    }
    OrchestraURI uri = new OrchestraURI("FrameworkCommands", "FC");
    String mode = null;
    switch (mode_p) {
      case ADMINISTRATOR:
        mode = "Administrator";
      break;
      case USER:
        mode = "User";
      break;
    }
    uri.addParameter("Mode", mode);
    return doRequest("ChangeMode", Collections.singletonList(uri));
  }

  /**
   * Create a compressed value for an URI parameter based on specified ordered ones.
   * @param values_p
   * @return <code>null</code> if parameter is <code>null</code>, an empty {@link String} if parameter is empty, the compressed value otherwise.<br>
   *         Note that the process makes use of the {@link ICommandConstants#DEFAULT_PARAMETER_SEPARATOR} separator to queue values within compressed one.
   */
  private static String compressParameterValues(List<String> values_p) {
    // Precondition.
    if (null == values_p) {
      return null;
    }
    // Precondition.
    if (values_p.isEmpty()) {
      return ""; //$NON-NLS-1$
    }
    StringBuilder result = new StringBuilder();
    Iterator<String> pathIterator = values_p.iterator();
    while (pathIterator.hasNext()) {
      result.append(pathIterator.next());
      if (pathIterator.hasNext()) {
        result.append(ICommandConstants.DEFAULT_PARAMETER_SEPARATOR);
      }
    }
    return result.toString();
  }

  /**
   * Create artifact(s) from URI(s)
   * @param URIs_p: List of URIs to create. - at least one non <code>null</code> URI is required.
   * @return A map containing the results data, or <code>null</code> if the {@link OrchestraURI} {@link List} is <code>null</code>, empty or contains a
   *         <code>null</code> {@link OrchestraURI}.
   * @throws Exception
   */
  public static Map<String, String> create(List<OrchestraURI> URIs_p) throws Exception {
    return doRequest(ICommandConstants.CREATE, URIs_p);
  }

  /**
   * Create a set of artifacts URI from specified logical folder path and root type.<br>
   * A logical folder path is a prefix path in a root artifact logical name (as displayed by the Orchestra Explorer in folding mode).<br>
   * It is recommended to use the '/' character as a separator in logical folder path, instead of the '\' one.<br>
   * A set of artifacts is expandable (through a dedicated service) and returns all matching (logical) sub-artifacts.
   * @param logicalFolderPath_p
   * @param rootTypes_p A list of {@link String} root types that are to be taken into account into this search URI.<br>
   *          Only root types specified here will be returned by the corresponding {@link #expandArtifactSet(List)} service.<br>
   *          Note that if no root type should be specified, either <code>null</code> or an empty list are acceptable values.<br>
   *          These parameter is compressed into a single URI parameter made of the different provided types (in their original order), separated by the
   *          {@link ICommandConstants#DEFAULT_PARAMETER_SEPARATOR} separator (hopefully unlikely to be found in the types content).
   * @return
   * @throws Exception
   */
  public static OrchestraURI createArtifactSetUri(String logicalFolderPath_p, List<String> rootTypes_p) throws Exception {
    // Preconditions.
    if ((null == logicalFolderPath_p) || logicalFolderPath_p.trim().isEmpty()) {
      return null;
    }
    OrchestraURI result = new OrchestraURI("ArtefactSet", "ASU"); //$NON-NLS-1$ //$NON-NLS-2$
    // Add path.
    result.addParameter("LogicalFolderPath", logicalFolderPath_p); //$NON-NLS-1$
    // Add root type, if any.
    if ((null != rootTypes_p) && !rootTypes_p.isEmpty()) {
      result.addParameter("RootTypes", compressParameterValues(rootTypes_p)); //$NON-NLS-1$
    }
    return result;
  }

  /**
   * Create a migration URI from specified textual representation of an existing URI to migrate.<br>
   * The existing URI can either be an Orchestra V4 URI/URL or an Orchestra V5.X URI that needs migration.
   * @param uriToMigrate_p A not <code>null</code> textual URI to migrate.
   * @return <code>null</code> if parameter is invalid, a new migration URI otherwise.
   * @throws Exception
   */
  public static OrchestraURI createMigrationUri(String uriToMigrate_p) throws Exception {
    // Precondition.
    if (null == uriToMigrate_p) {
      return null;
    }
    OrchestraURI result = new OrchestraURI("Migration", "Migration"); //$NON-NLS-1$ //$NON-NLS-2$
    result.addParameter("UriToMigrate", uriToMigrate_p); //$NON-NLS-1$
    return result;
  }

  /**
   * Create a search URI from specified one and search path.
   * @param seedArtifact_p The artifact that is the seed of the search. It doesn't have to be a root artifact, it can be any artifact. Only its subtree is
   *          considered while searching (seed artifact included).<br>
   *          The seed artifact can not be <code>null</code>.
   * @param path_p A list of {@link String} segments that are naming rules making sense to access to a specific artifact from seed one.<br>
   *          This is the decomposition of the path that leads to the expected artifact. This is a list as the order is required.<br>
   *          This path is compressed into a single URI parameter made of the different provided segments (in their original order), separated by the
   *          {@link ICommandConstants#DEFAULT_PARAMETER_SEPARATOR} separator (hopefully unlikely to be found in the segments content).
   * @return A new URI composed of the seed absolute URI and the encoded path as the <code>relativePath</code> parameter. <code>null</code> if parameters are
   *         not valid.
   * @throws Exception Most probably a {@link BadOrchestraURIException} if the resulting URI could not be created.
   */
  public static OrchestraURI createSearchUri(OrchestraURI seedArtifact_p, List<String> path_p) throws Exception {
    // Preconditions.
    if ((null == seedArtifact_p) || (null == path_p)) {
      return null;
    }
    if (path_p.isEmpty()) {
      return seedArtifact_p;
    }
    // Resulting URI.
    OrchestraURI result = new OrchestraURI(seedArtifact_p.getAbsoluteUri());
    result.addParameter(ICommandConstants.URI_PARAMETER_SEARCH_RELATIVE_PATH, compressParameterValues(path_p));
    return result;
  }

  /**
   * Do a documentary export for {@link OrchestraURI} {@link List}
   * @param URIs_p: URI(s) to export. - at least one non <code>null</code> URI is required.
   * @return The Orchestra Framework response data as a {@link Map}, or <code>null</code> if the {@link OrchestraURI} {@link List} is <code>null</code>, empty
   *         or contains a <code>null</code> {@link OrchestraURI}.
   * @throws Exception
   */
  public static Map<String, String> documentaryExport(List<OrchestraURI> URIs_p) throws Exception {
    return doRequest(ICommandConstants.EXPORT_DOC, URIs_p);
  }

  /**
   * Creates and executes the query.
   * @param command_p: the command to execute
   * @param URIs_p: URI(s) to export. - at least one URI is required.
   * @return The Orchestra Framework response data as a {@link Map}
   * @throws Exception
   */
  private static Map<String, String> doRequest(String command_p, List<OrchestraURI> URIs_p) throws Exception {
    if (!command_p.equals(ICommandConstants.GET_ROOT_ARTIFACTS)) {
      // Check the list.
      if ((URIs_p == null) || (URIs_p.size() == 0)) {
        return null;
      }

      // Check the list content
      for (OrchestraURI orchestraURI : URIs_p) {
        if (orchestraURI == null) {
          return null;
        }
      }
    }

    Map<String, String> result;
    Query query = new Query(command_p, URIs_p);
    RequestClient request = new RequestClient(query);
    result = request.sendPostRequest();

    return result;
  }

  /**
   * Execute the <code>specificCommand</code> for {@link OrchestraURI} {@link List}
   * @param specificCommand_p the specific command to execute.
   * @param URIs_p {@link OrchestraURI}(s) to export. - at least one non <code>null</code> URI is required.
   * @return The Orchestra Framework response data as a {@link Map}, or <code>null</code> if the {@link OrchestraURI} {@link List} is <code>null</code>, empty
   *         or contains a <code>null</code> {@link OrchestraURI}.
   * @throws Exception
   */
  public static Map<String, String> executeSpecificCommand(String specificCommand_p, List<OrchestraURI> URIs_p) throws Exception {
    return doRequest(specificCommand_p, URIs_p);
  }

  /**
   * Expands a {@link List} of {@link OrchestraURI}.
   * @param URIs_p: URI(s) to expand. - at least one URI is required.
   * @return The Orchestra Framework response data as a {@link Map}, or <code>null</code> if the {@link OrchestraURI} {@link List} is <code>null</code>, empty
   *         or contains a <code>null</code> {@link OrchestraURI}.
   * @throws Exception
   */
  public static Map<String, String> expand(List<OrchestraURI> URIs_p) throws Exception {
    return doRequest(ICommandConstants.EXPAND, URIs_p);
  }

  /**
   * Expand specified artifacts set URIs.
   * @param URIs_p A list of artifacts set URIs. See {@link #createArtifactSetUri(String, String)} for details about the format of such an URI.
   * @return The Orchestra Framework response data as a {@link Map}, or <code>null</code> if the {@link OrchestraURI} {@link List} is <code>null</code>, empty
   *         or contains a <code>null</code> {@link OrchestraURI}.<br>
   *         The only parameter of interest is the path to the status which contains the result of the expand set service.<br>
   *         Its structure is made of :<br>
   *         <ul>
   *         <li>The Framework first status (Info), which is to be ignored, then :</li>
   *         <ul>
   *         <li>A status with overall result (Ok, Warning, Error).
   *         <ul>
   *         <li>One status per provided URI with result (Ok, Error).
   *         <ul>
   *         <li>N sub-statuses with resulting URIs, where <code>0 == N</code> when parent status result is Error, <code>1 == N</code> when the expand returned
   *         only one possible result, and <code>1 < N</code> when more than one result is returned.
   *         </ul>
   *         </li>
   *         </ul>
   *         </li>
   *         </ul>
   *         </ul>
   * @throws Exception
   */
  public static Map<String, String> expandArtifactSet(List<OrchestraURI> URIs_p) throws Exception {
    return executeSpecificCommand("ExpandArtifactSet", URIs_p); //$NON-NLS-1$
  }

  /**
   * @param credentials_id_p : A non-empty, not <code>null</code>, {@link String} identifier of credentials data to retrieve.
   * @param credentials_ui_password_confirmation_p : A boolean for asking a optional password confirmation field on the user input credentials dialog box
   *          displayed if credentials data do not exist.
   * @param credentials_ui_on_top_message_p : A optional {@link String} message to display on the top of the user input credentials dialog box displayed if
   *          credentials data do not exist. NB: This {@link String} message can be HTML formatted.
   * @return The Orchestra Framework response data as a {@link Map}.
   * @throws Exception
   */
  public static Map<String, String> getCredentials(final String credentials_id_p, final boolean credentials_ui_password_confirmation_p,
      final String credentials_ui_on_top_message_p) throws Exception {
    return getCredentials(credentials_id_p, credentials_ui_password_confirmation_p, credentials_ui_on_top_message_p, true);
  }

  /**
   * @param credentials_id_p : A non-empty, not <code>null</code>, {@link String} identifier of credentials data to retrieve.
   * @param credentials_ui_password_confirmation_p : A boolean for asking a optional password confirmation field on the user input credentials dialog box
   *          displayed if credentials data do not exist.
   * @param credentials_ui_on_top_message_p : A optional {@link String} message to display on the top of the user input credentials dialog box displayed if
   *          credentials data do not exist. NB: This {@link String} message can be HTML formatted.
   * @param isPersistent_p : <code>true</code> if credentials are persisted, <code>false</code> otherwise
   * @return The Orchestra Framework response data as a {@link Map}.
   * @throws Exception
   */
  @SuppressWarnings("nls")
  public static Map<String, String> getCredentials(final String credentials_id_p, final boolean credentials_ui_password_confirmation_p,
      final String credentials_ui_on_top_message_p, boolean isPersistent_p) throws Exception {
    if (null == credentials_id_p) {
      Map<String, String> result = new HashMap<String, String>(1);
      result.put(PapeeteHTTPKeyRequest.__MESSAGE, "A credentials id is required to get the credentials.");
      return result;
    }

    OrchestraURI uri = new OrchestraURI("FrameworkCommands", "FC");
    uri.addParameter("CredentialsId", credentials_id_p);
    uri.addParameter("CredentialsUIPasswordConfirmation", Boolean.toString(credentials_ui_password_confirmation_p));
    uri.addParameter("CredentialsUIOnTopMessage", credentials_ui_on_top_message_p);
    uri.addParameter("IsPersistent", Boolean.toString(isPersistent_p));

    return doRequest(ICommandConstants.GET_CREDENTIALS, Collections.singletonList(uri));
  }

  /**
   * Reset {@link Credentials} data by identifier.
   * @param credentials_id_p : A non-empty, not <code>null</code>, {@link String} identifier of credentials data to reset.
   * @return The Orchestra Framework response data as a {@link Map}, or <code>null</code> if the {@link List} is <code>null</code>, empty or contains a
   *         <code>null</code> or invalid path.<br>
   * @throws Exception
   */
  public static Map<String, String> resetCredentials(String credentials_id_p) throws Exception {
    return resetCredentials(credentials_id_p, true);
  }

  /**
   * Reset {@link Credentials} data by identifier.
   * @param credentials_id_p : A non-empty, not <code>null</code>, {@link String} identifier of credentials data to reset.
   * @param isPersistent_p : <code>true</code> if credentials must be persisted, <code>false</code> otherwise
   * @return The Orchestra Framework response data as a {@link Map}, or <code>null</code> if the {@link List} is <code>null</code>, empty or contains a
   *         <code>null</code> or invalid path.<br>
   * @throws Exception
   */
  @SuppressWarnings("nls")
  public static Map<String, String> resetCredentials(String credentials_id_p, boolean isPersistent_p) throws Exception {

    if (null == credentials_id_p) {
      Map<String, String> result = new HashMap<String, String>(1);
      result.put(PapeeteHTTPKeyRequest.__MESSAGE, "A credentials id is required to reset specific credentials data.");
      return result;
    }

    OrchestraURI uri = new OrchestraURI("FrameworkCommands", "FC");
    uri.addParameter("CredentialsId", credentials_id_p);
    uri.addParameter("IsPersistent", Boolean.toString(isPersistent_p));
    Map<String, String> response = new HashMap<String, String>(1);
    response = doRequest(ICommandConstants.RESET_CREDENTIALS, Collections.singletonList(uri));

    if ((null == response) || (response.isEmpty())) {
      return null;
    }

    return response;
  }

  /**
   * Get the logical name of each provided physical path (if any).
   * @param rootArtifactsPaths_p A non-empty, not <code>null</code>, list of absolute paths to artifacts.
   * @return The Orchestra Framework response data as a {@link Map}, or <code>null</code> if the {@link List} is <code>null</code>, empty or contains a
   *         <code>null</code> or invalid path.<br>
   *         Additionally, this map also contains (absolute path, technical URI) pairs, that can be used to extract resulting URI from returned status (using
   *         the technical URI of an absolute path).
   * @throws Exception
   */
  public static Map<String, String> getLogicalName(List<String> rootArtifactsPaths_p) throws Exception {
    // Precondition.
    if ((rootArtifactsPaths_p == null) || (rootArtifactsPaths_p.size() == 0)) {
      return null;
    }
    // Build special URIs like: orchestra://FILSYSTEM/projectPath
    List<OrchestraURI> uris = new ArrayList<OrchestraURI>();
    // Retain created URIs.
    Map<String, String> pathToTechnicalUri = new HashMap<String, String>(rootArtifactsPaths_p.size());
    for (String rootArtifactAbsolutePath : rootArtifactsPaths_p) {
      File projectFile = new File(rootArtifactAbsolutePath);
      // Precondition.
      if (!projectFile.exists()) {
        return null;
      }
      // Create technical URI.
      OrchestraURI uri = new OrchestraURI(PapeeteHTTPKeyRequest._FILESYSTEM_TOOL_NAME, projectFile.getAbsolutePath());
      uris.add(uri);
      // Retain textual form.
      pathToTechnicalUri.put(rootArtifactAbsolutePath, uri.getUri());
    }
    // Do transcript.
    Map<String, String> result = transcript(uris);
    if (null != result) {
      result.putAll(pathToTechnicalUri);
    }
    return result;
  }

  /**
   * Get artefacts metadata for specified artefacts.
   * @param URIs_p The URIs to artefacts whose metadata are looked for.
   * @return The Orchestra Framework response data as a {@link Map}, or <code>null</code> if the {@link OrchestraURI} {@link List} is <code>null</code>, empty
   *         or contains a <code>null</code> {@link OrchestraURI}.
   * @throws Exception
   */
  public static Map<String, String> getMetadata(List<OrchestraURI> URIs_p) throws Exception {
    return doRequest(ICommandConstants.GET_ARTIFACTS_METADATA, URIs_p);
  }

  /**
   * Retrieve the physical path for each {@link OrchestraURI} in the list
   * @param URIs_p
   * @return The Orchestra Framework response data as a {@link Map}, or <code>null</code> if the {@link OrchestraURI} {@link List} is <code>null</code>, empty
   *         or contains a <code>null</code> {@link OrchestraURI}.
   * @throws Exception
   */
  public static Map<String, String> getPhysicalPath(List<OrchestraURI> URIs_p) throws Exception {
    return transcript(URIs_p);
  }

  /**
   * Retrieve root artifacts
   * @return The Orchestra Framework response data as a {@link Map}.
   * @throws Exception
   */
  public static Map<String, String> getRootArtifacts() throws Exception {
    return doRequest(ICommandConstants.GET_ROOT_ARTIFACTS, null);
  }

  /**
   * Export a project or an object in a provided format by providing a context.
   * @param URIs_p: URI(s) to export. - at least one URI is required.
   * @return The Orchestra Framework response data as a {@link Map}, or <code>null</code> if the {@link OrchestraURI} {@link List} is <code>null</code>, empty
   *         or contains a <code>null</code> {@link OrchestraURI}.
   * @throws Exception
   */
  public static Map<String, String> lmExport(List<OrchestraURI> URIs_p) throws Exception {
    return doRequest(ICommandConstants.EXPORT_LM, URIs_p);
  }

  /**
   * Migrate specified migration URIs.<br>
   * Note that the URIs are to comply with what the {@link #createMigrationUri(String)} method returns.
   * @param URIs_p A list of migration URIs. See {@link #createMigrationUri(String)} for details about the format of such an URI.
   * @return The Orchestra Framework response data as a {@link Map}, or <code>null</code> if the {@link OrchestraURI} {@link List} is <code>null</code>, empty
   *         or contains a <code>null</code> {@link OrchestraURI}.<br>
   *         The only parameter of interest is the path to the status which contains the result of the search service.<br>
   *         Its structure is made of :<br>
   *         <ul>
   *         <li>The Framework first status (Info), which is to be ignored, then :</li>
   *         <ul>
   *         <li>A status with overall result (Ok, Warning, Error).
   *         <ul>
   *         <li>One status per provided URI with result (Ok, Error), URI set to migration one, and message set to resulting migrated URI textual representation
   *         (see {@link OrchestraURI} on how to use it).</li>
   *         </ul>
   *         </li>
   *         </ul>
   *         </ul>
   * @throws Exception
   */
  public static Map<String, String> migrate(List<OrchestraURI> URIs_p) throws Exception {
    // Preconditions.
    if ((null == URIs_p) || URIs_p.isEmpty()) {
      return null;
    }
    return executeSpecificCommand("UriMigration", URIs_p); //$NON-NLS-1$
  }

  /**
   * Navigate to a project or an object.
   * @param uri_p: URI to navigate to.
   * @return The Orchestra Framework response data as a {@link Map}, or <code>null</code> if the {@link OrchestraURI} {@link List} is <code>null</code>, empty
   *         or contains a <code>null</code> {@link OrchestraURI}.
   * @throws Exception
   */
  public static Map<String, String> navigate(List<OrchestraURI> URIs_p) throws Exception {
    return doRequest(ICommandConstants.NAVIGATE, URIs_p);
  }

  /**
   * Do invoke search service for specified URIs.
   * @param URIs_p A list of search URIs. See {@link #createSearchUri(OrchestraURI, List)} for details about the format of such an URI.
   * @return The Orchestra Framework response data as a {@link Map}, or <code>null</code> if the {@link OrchestraURI} {@link List} is <code>null</code>, empty
   *         or contains a <code>null</code> {@link OrchestraURI}.<br>
   *         The only parameter of interest is the path to the status which contains the result of the search service.<br>
   *         Its structure is made of :<br>
   *         <ul>
   *         <li>The Framework first status (Info), which is to be ignored, then on a per connector basis :</li>
   *         <ul>
   *         <li>A status with overall result (Ok, Warning, Error).
   *         <ul>
   *         <li>One status per provided URI with result (Ok, Error).
   *         <ul>
   *         <li>N sub-statuses with resulting URIs, where <code>0 == N</code> when parent status result is Error, <code>1 == N</code> when the search returned
   *         only one possible result, and <code>1 < N</code> when more than one result is returned.
   *         </ul>
   *         </li>
   *         </ul>
   *         </li>
   *         </ul>
   *         </ul>
   * @throws Exception
   */
  public static Map<String, String> search(List<OrchestraURI> URIs_p) throws Exception {
    // Precondition.
    if ((null == URIs_p) || URIs_p.isEmpty()) {
      return null;
    }
    return doRequest(ICommandConstants.SEARCH, URIs_p);
  }

  /**
   * Execute a transcription request.
   * @param uri_p
   * @return The Orchestra Framework response data as a {@link Map}.
   * @throws Exception
   */
  private static Map<String, String> transcript(List<OrchestraURI> URIs_p) throws Exception {
    return doRequest(ICommandConstants.TRANSCRIPTION, URIs_p);
  }

  /**
   * ODM operating modes.
   * @author t0076261
   */
  public static enum OdmMode {
    ADMINISTRATOR, USER
  }
}