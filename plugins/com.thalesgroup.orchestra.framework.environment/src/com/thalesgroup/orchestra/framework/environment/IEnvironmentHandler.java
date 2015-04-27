/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubMonitor;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler;

/**
 * The environment handler is responsible for creating user interactions and returning data that represents the environment parameters.
 * @author t0076261
 */
public interface IEnvironmentHandler {
  /**
   * Get environment creation attributes.<br>
   * Returned structure will be used to create a new environment instance.<br>
   * This is up to the environment implementation to be able to handle this {@link Map}.
   * @return A not <code>null</code> {@link Map} of attributes.
   */
  public Map<String, String> getAttributes();

  /**
   * Is the environment type compliant with using a baseline ?<br>
   * The raw parameters of the baselined environment are provided in parameter as a provision.<br>
   * In most cases, it should be up to the environment type (statically speaking) to decide whether or not it is compliant with using a baseline mechanism.<br>
   * Note that it collides with the need to implement {@link IEnvironment#isBaselineCompliant()} method.<br>
   * An environment that is not compatible with the baseline process should not answer <code>true</code> here.<br>
   * This method is required at use time, where no environment is instantiated yet, so the process can't use the previously mentioned method instead.
   * @param environmentContext_p
   * @return
   */
  public boolean isUseBaselineCompliant(EnvironmentContext environmentContext_p);

  /**
   * Get use baseline as reference attributes from specified baseline ones.<br>
   * @param context_p Contains the environment attributes as they were set by the environment at baseline creation time.
   * @param monitor_p A progress monitor, possibly <code>null</code> (if no progress is expected).<br>
   *          Rules of thumb about progress reporting (taken from http://wiki.eclipse.org/Progress_Reporting) :
   *          <ul>
   *          <li>Use {@link SubMonitor}.
   *          <li>Always begin by converting the monitor you get to a {@link SubMonitor}.
   *          <li>Use <code>newChild(n)</code> to create a sub monitor to pass to methods that take an {@link IProgressMonitor} as argument.
   *          <li>Never call <code>done</code>, but document that the callers must do so unless they used a {@link SubMonitor}.
   *          </ul>
   * @return Couple value of
   *         <ul>
   *         <li>A not <code>null</code> attributes map, filled with using the baseline as a reference values.<br>
   *         Referencing a baseline means
   *         <li>A return status
   *         </ul>
   */
  public Couple<Map<String, String>, IStatus> getBaselineReferenceAttributes(UseBaselineEnvironmentContext context_p, IProgressMonitor progressMonitor_p);

  /**
   * Get use baseline as starting point attributes from specified baseline.<br>
   * @param startingPointContext_p Contains the baselined environment attributes {@link EnvironmentAttributes#_attributes} AND the starting point attributes
   *          {@link UseBaselineEnvironmentContext#getUseBaselineAttributes()} as they are filled by the {@link IEnvironmentUseBaselineHandler} viewer, if any.
   *          If no {@link IEnvironmentUseBaselineHandler} implementation is provided (ie no interaction with the user at starting point time), then there is no
   *          starting point attributes (empty map).<br>
   * <br>
   *          AS SEVERAL ENVIRONMENTS CAN SHARE THE SAME STARTING POINT ATTRIBUTES (
   *          {@link IEnvironmentUseBaselineHandler#isSameUseBaselineContext(UseBaselineEnvironmentContext, UseBaselineEnvironmentContext)}), IT IS MANDATORY TO
   *          USE {@link UseBaselineEnvironmentContext#getUseBaselineAttributes()} TO GET THEM.
   * @param monitor_p A progress monitor, possibly <code>null</code> (if no progress is expected).<br>
   *          Rules of thumb about progress reporting (taken from http://wiki.eclipse.org/Progress_Reporting) :
   *          <ul>
   *          <li>Use {@link SubMonitor}.
   *          <li>Always begin by converting the monitor you get to a {@link SubMonitor}.
   *          <li>Use <code>newChild(n)</code> to create a sub monitor to pass to methods that take an {@link IProgressMonitor} as argument.
   *          <li>Never call <code>done</code>, but document that the callers must do so unless they used a {@link SubMonitor}.
   *          </ul>
   * @return Couple value of
   *         <ul>
   *         <li>A not <code>null</code> attributes map, filled with using the baseline as starting point values.<br>
   *         <li>A return status
   *         </ul>
   */
  public Couple<Map<String, String>, IStatus> getBaselineStartingPointAttributes(UseBaselineEnvironmentContext startingPointContext_p,
      IProgressMonitor progressMonitor_p);

  /**
   * Get the environment declaration id, as defined by its contributing extension. This is the environment type.
   * @return A not <code>null</code> {@link String} standing for the environment declaration ID.
   */
  public String getDeclarationId();

  /**
   * Handles the environment attributes at use baseline time.<br>
   * Note that the handler is responsible for user interactions for specific attributes.<br>
   * This method can not return a <code>null</code> implementation, but should instead at least return a {@link DefaultEnvironmentUseBaselineHandler} object.
   * @return A not <code>null</code> {@link IEnvironmentUseBaselineHandler} implementation (see {@link DefaultEnvironmentUseBaselineHandler} for a default one).
   */
  public IEnvironmentUseBaselineHandler getEnvironmentUseBaselineHandler();

  /**
   * Get a decoded/uncompressed {@link Map} representation of specified attributes.<br>
   * The fully decoded representation must be human readable.<br>
   * The {@link Map} is expected to hold the following data :
   * <ul>
   * <li>A key that represents a list of values (most likely the same key as in the specified attributes)</li>
   * <li>A list of decoded and substituted values, that is the values referenced by specified key. If the key does refer to only one value, this is a singleton
   * list. If the key is referring to a compressed list of values in the specified map, then this is the uncompressed substituted list of values.</li>
   * </ul>
   * @param context_p Contains the raw attributes as they are currently known by context being edited.
   * @return A not <code>null</code> {@link Map} with expected contents.<br>
   *         <b>WARNING</b> keys and contents might change along with the environment implementation !
   */
  public Map<String, List<String>> getExpandedAttributes(EnvironmentContext context_p);

  /**
   * Get environment creation/update first wizard page. This page is mandatory for any new environment to be able to provide with specific parameters values.
   * @return A not <code>null</code> {@link AbstractEnvironmentPage}.
   */
  public AbstractEnvironmentPage getFirstPage();

  /**
   * Get environment creation/update next page, from specified one.
   * @param currentPage_p The environment page in use.
   * @return A new {@link AbstractEnvironmentPage} for specific parameters, <code>null</code> if no page is required.
   */
  public AbstractEnvironmentPage getNextPage(AbstractEnvironmentPage currentPage_p);

  /**
   * Get a decoded/uncompressed {@link Map} representation of specified attributes.<br>
   * The {@link Map} is expected to hold the following data :
   * <ul>
   * <li>A key that represents a list of values (most likely the same key as in the specified attributes)</li>
   * <li>A list of decoded but <b>NOT substituted</b> values, that is the values referenced by specified key. If the key does refer to only one value, this is a
   * singleton list. If the key is referring to a compressed list of values in the specified map, then this is the uncompressed list of values.</li>
   * </ul>
   * <br>
   * Note that this implementation differs from {@link #getExpandedAttributes(Map)} in that the list of values <b>must not be substituted</b> in this case.<br>
   * <br>
   * @param context_p Contains the raw attributes as they are currently known by context being edited.
   * @return A not <code>null</code> {@link Map} with expected contents.<br>
   *         <b>WARNING</b> keys and contents might change along with the environment implementation !
   */
  public Map<String, List<String>> getUncompressedAttributes(EnvironmentContext context_p);

  /**
   * Get currently in use variables handler.<br>
   * Note that using this handler is mandatory in context where attributes values are supposed to be substituted.<br>
   * The handler also provides the caller with the ability to browse variables for reference and handle picked result.
   * @return
   */
  public IVariablesHandler getVariablesHandler();

  /**
   * Initialize handler for a new session with specified attributes.<br>
   * The handler might be used for as many sessions as required by the user.<br>
   * This is the only opportunity for the implementation to clear its internal state.<br>
   * A new session starts by invoking this method.<br>
   * @param context_p Contains the raw attributes as they are currently known by context being edited.
   * @return An {@link IStatus} with severity set to {@link IStatus#OK} if initialization with specified attributes was successful. If the severity is not OK,
   *         this is up to the implementation to provide with details (within children statuses) as to why there are errors.<br>
   *         In both cases, the returned {@link IStatus} must not be <code>null</code>.
   */
  public IStatus initialize(EnvironmentContext context_p);

  /**
   * Is creation possible at call time ?<br>
   * This is basically a check against current parameters values, and the ability to finish at this point for the user.<br>
   * This is not based on wizard pages, but rather the set of values already known.<br>
   * An OK result implies that the user can stop providing with values.<br>
   * An ERROR result implies that either current parameters are invalid, or not enough (or both).
   * @return An {@link IStatus} with severity set to {@link IStatus#OK} if creation parameters are valid and sufficient. If the severity is not OK, this is up
   *         to the implementation to provide with details (within children statuses) as to why there are errors. If the status is a {@link MultiStatus}, only
   *         first level children are taken into account in the reporting to the user.<br>
   *         In both cases, the returned {@link IStatus} must not be <code>null</code>.
   */
  public IStatus isCreationComplete();

  /**
   * Get a {@link String} representation of currently supported environment type for specified attributes.
   * @param attributes_p
   * @return A not <code>null</code> {@link String} representation.
   */
  public String toString(Map<String, String> attributes_p);

  /**
   * Update attributes with specified deltas.<br>
   * To remove an existing key from known attributes, provide a delta for this key with value set to <code>null</code>.
   * @param updatedAttributes_p A not <code>null</code> map of updated attributes.<br>
   *          The map does not need to include all attributes, but only those updated (hence the use of 'deltas').
   */
  public IStatus updateAttributes(Map<String, String> updatedAttributes_p);
}