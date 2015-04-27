/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.wizard;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.EnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentUseBaselineHandler;
import com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.UseBaselineType;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.ui.dialog.ChooseBaselineDialog.BaselineSelectionType;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.UseBaselineEnvConfPage;

/**
 * @author T0052089
 */
public class NewUseBaselineContextWizard extends NewContextWizard {
  /**
   * The name of Orchestra Baseline from which we are starting.
   */
  protected String _baselineName;

  /**
   * The parent context from the baseline repository.
   */
  protected RootContextsProject _baselineRootContextsProject;

  /**
   * Model of the wizard.
   */
  protected UseBaselineContextsModel _useBaselineContextsModel;

  /**
   * Starting point environments configuration page.
   */
  protected UseBaselineEnvConfPage _useBaselineEnvConfPage;

  /**
   * Baseline type
   */
  protected BaselineSelectionType _baselineSelection;

  /**
   * Constructor.
   * @param parentProjectContext_p
   */
  public NewUseBaselineContextWizard(RootContextsProject baselineRootContextsProject_p, String baselineName_p, BaselineSelectionType baselineSelection_p) {
    _baselineRootContextsProject = baselineRootContextsProject_p;
    _baselineName = baselineName_p;
    _baselineSelection = baselineSelection_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.wizard.NewContextWizard#addPages()
   */
  @Override
  public void addPages() {
    String pagesDescription;
    String pagesTitle;
    if (BaselineSelectionType.STARTING_POINT == _baselineSelection) {
      pagesTitle = MessageFormat.format(Messages.NewUseBaselineContextWizard_StartingPointPagesTitle, _baselineName);
      pagesDescription = Messages.NewUseBaselineContextWizard_StartingPointContextCreationPagesDescription;
      // Page used to get new context name (parent selection is disabled).
      _newContextParametersPage = new NewContextWizardParentContextPage(false);
      _newContextParametersPage.setTitle(pagesTitle);
      _newContextParametersPage.setDescription(pagesDescription);
      addPage(_newContextParametersPage);
      // Page used to get new context location.
      _newContextWizardLocationPage = new NewContextWizardLocationPage();
      _newContextWizardLocationPage.setTitle(pagesTitle);
      _newContextWizardLocationPage.setDescription(pagesDescription);
      addPage(_newContextWizardLocationPage);
      pagesDescription = Messages.NewUseBaselineContextWizard_StartingPointContextCreationPagesDescription;
    } else {
      pagesTitle = MessageFormat.format(Messages.NewUseBaselineContextWizard_ReferencePagesTitle, _baselineName);
      pagesDescription = Messages.NewUseBaselineContextWizard_ReferenceContextCreationPagesDescription;
    }
    // Page used to specify some parameters needed by environments.
    _useBaselineEnvConfPage = new UseBaselineEnvConfPage(this, _baselineRootContextsProject, _baselineName, _baselineSelection);
    _useBaselineEnvConfPage.setTitle(pagesTitle);
    _useBaselineEnvConfPage.setDescription(pagesDescription);
    addPage(_useBaselineEnvConfPage);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.wizard.NewContextWizard#canFinish()
   */
  @Override
  public boolean canFinish() {
    return _useBaselineEnvConfPage.isPageComplete();
  }

  /**
   * @return the startingPointContextsModel
   */
  public UseBaselineContextsModel getUseBaselineContextsModel() {
    return _useBaselineContextsModel;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#getWindowTitle()
   */
  @Override
  public String getWindowTitle() {
    if (BaselineSelectionType.STARTING_POINT == _baselineSelection) {
      return Messages.NewUseBaselineContextWizard_StartingPointWindowTitle;
    }

    return Messages.NewUseBaselineContextWizard_ReferenceWindowTitle;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performCancel()
   */
  @Override
  public boolean performCancel() {
    _useBaselineContextsModel = null;
    _useBaselineEnvConfPage.performCancel();
    return super.performCancel();
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    _useBaselineContextsModel = _useBaselineEnvConfPage.getEnvValuesUseBaselineContexts();
    return true;
  }

  /**
   * Use baseline contexts model.
   * @author T0052089
   */
  public static class UseBaselineContextsModel {
    /**
     * The starting point context.
     */
    protected final Context _context;
    /**
     * EnvironmentValue to UseBaselineContext map.
     */
    protected final Map<EnvironmentVariableValue, UseBaselineEnvironmentContext> _envValueToUseBaselineContext;
    /**
     * EnvironmentVariable to MASTER EnvironmentVariableValues.
     */
    protected final Map<EnvironmentVariable, List<EnvironmentVariableValue>> _envVariableToMasterEnvValues;
    /**
     * EnvironmentVariable to SLAVE EnvironmentVariableValues.
     */
    protected final Map<EnvironmentVariable, List<EnvironmentVariableValue>> _envVariableToSlaveEnvValues;

    public UseBaselineContextsModel(Context sourceContext_p) {
      // Initialize attributes.
      _context = sourceContext_p;
      _envVariableToMasterEnvValues = new HashMap<EnvironmentVariable, List<EnvironmentVariableValue>>();
      _envVariableToSlaveEnvValues = new HashMap<EnvironmentVariable, List<EnvironmentVariableValue>>();
      _envValueToUseBaselineContext = new HashMap<EnvironmentVariableValue, UseBaselineEnvironmentContext>();
      // Fill a list of Couples (EnvironmentVariable, EnvironmentVariableValue).
      List<Couple<EnvironmentVariable, EnvironmentVariableValue>> allEnvVariableValues = new ArrayList<Couple<EnvironmentVariable, EnvironmentVariableValue>>();
      final String[] envVarPaths = new String[] { DataUtil.__ARTEFACTPATH_VARIABLE_NAME, DataUtil.__CONFIGURATIONDIRECTORIES_VARIABLE_NAME };
      for (int envVarIndex = 0; envVarIndex < envVarPaths.length; ++envVarIndex) {
        AbstractVariable envVar = DataUtil.getVariable(envVarPaths[envVarIndex], sourceContext_p);
        if (envVar instanceof EnvironmentVariable) {
          // Create lists for environment variables in the master and slave cases.
          _envVariableToMasterEnvValues.put((EnvironmentVariable) envVar, new ArrayList<EnvironmentVariableValue>());
          _envVariableToSlaveEnvValues.put((EnvironmentVariable) envVar, new ArrayList<EnvironmentVariableValue>());
          // Cycle through environments.
          List<VariableValue> variableValues = DataUtil.getRawValues(envVar, sourceContext_p);
          for (VariableValue variableValue : variableValues) {
            // Invalid content, skip it (unlikely).
            if (!(variableValue instanceof EnvironmentVariableValue)) {
              continue;
            }
            // Make sure environment is compliant with MakeBaseline process before picking it.
            EnvironmentVariableValue environmentVariableValue = (EnvironmentVariableValue) variableValue;
            if (isUseBaselineCompliant(environmentVariableValue)) {
              allEnvVariableValues.add(new Couple<EnvironmentVariable, EnvironmentVariableValue>((EnvironmentVariable) envVar, environmentVariableValue));
            }
          }
        }
      }
      // Go through (EnvironmentVariable, EnvironmentVariableValue) couples to create StartingPointContexts.
      for (Couple<EnvironmentVariable, EnvironmentVariableValue> envVariableValue : allEnvVariableValues) {
        // Create a new starting point context for current environment variable.
        UseBaselineEnvironmentContext newUseBaselineContext = new UseBaselineEnvironmentContext();
        newUseBaselineContext._environmentId = envVariableValue.getValue().getEnvironmentId();
        newUseBaselineContext._attributes = ModelUtil.convertEnvironmentVariableValues(envVariableValue.getValue());

        // Is there already a "SameUseBaselineContext" ?
        UseBaselineEnvironmentContext foundMasterStartingPointContext = findSameUseBaselineContext(envVariableValue.getValue(), newUseBaselineContext);
        if (null == foundMasterStartingPointContext) {
          // No "same" starting point context found -> create a master starting point context.
          newUseBaselineContext._masterContext = null;
          newUseBaselineContext._useBaselineAttributes = new HashMap<String, String>();
          newUseBaselineContext._useBaselineType = UseBaselineType.REFERENCE_ENVIRONMENT;
          newUseBaselineContext._allowUserInteractions = false;
          // Keep it in the masters map.
          _envVariableToMasterEnvValues.get(envVariableValue.getKey()).add(envVariableValue.getValue());
        } else {
          // A "same" starting point context found -> create a slave starting point context (with reference to the master one).
          newUseBaselineContext._masterContext = foundMasterStartingPointContext;
          newUseBaselineContext._useBaselineAttributes = null;
          newUseBaselineContext._useBaselineType = null;
          newUseBaselineContext._allowUserInteractions = false;
          // Keep it in the slaves map.
          _envVariableToSlaveEnvValues.get(envVariableValue.getKey()).add(envVariableValue.getValue());
        }

        _envValueToUseBaselineContext.put(envVariableValue.getValue(), newUseBaselineContext);
      }
    }

    /**
     * Find a "same" starting point context amongst already created starting point contexts.
     * @param environmentValue_p
     * @param newStartingPointContext_p
     * @return
     */
    public UseBaselineEnvironmentContext findSameUseBaselineContext(EnvironmentVariableValue environmentValue_p,
        UseBaselineEnvironmentContext newStartingPointContext_p) {
      // Get environment handler of given environment value.
      Couple<IStatus, IEnvironmentHandler> couple =
          EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironmentHandler(environmentValue_p.getEnvironmentId());
      if (!couple.getKey().isOK()) {
        // No handler found, can't compare StartingPointContexts.
        return null;
      }
      // Get EnvironmentUseBaseline handler.
      IEnvironmentUseBaselineHandler useBaselineHandler = couple.getValue().getEnvironmentUseBaselineHandler();
      // Go through created StartingPointContext to find a "same" as given one.
      for (UseBaselineEnvironmentContext existingUseBaselineContext : _envValueToUseBaselineContext.values()) {
        if (useBaselineHandler.isSameUseBaselineContext(existingUseBaselineContext, newStartingPointContext_p)) {
          // A same starting point found.
          return existingUseBaselineContext;
        }
      }
      return null;
    }

    /**
     * @return the context
     */
    public Context getContext() {
      return _context;
    }

    /**
     * @return the envValueToStartingPointContext
     */
    public Map<EnvironmentVariableValue, UseBaselineEnvironmentContext> getEnvValueToUseBaselineContext() {
      return _envValueToUseBaselineContext;
    }

    /**
     * @return the envVariableToEnvValues
     */
    public Map<EnvironmentVariable, List<EnvironmentVariableValue>> getEnvVariableToMasterEnvValues() {
      return _envVariableToMasterEnvValues;
    }

    /**
     * Get slave values for specified master context, if any.
     * @param context_p Seed master context.
     * @return <code>null</code> if specified parameter is <code>null</code>. A not <code>null</code> map of model elements to use otherwise.
     */
    public Map<EnvironmentVariable, List<EnvironmentVariableValue>> getEnvVariableToSlaveEnvValues(UseBaselineEnvironmentContext context_p) {
      // Precondition.
      if (null == context_p) {
        return null;
      }
      // Resulting structure.
      Map<EnvironmentVariable, List<EnvironmentVariableValue>> result =
          new HashMap<EnvironmentVariable, List<EnvironmentVariableValue>>(_envVariableToSlaveEnvValues.size());
      // Then remove useless values : ie values that are not slaved to specified master.
      for (Entry<EnvironmentVariable, List<EnvironmentVariableValue>> entry : _envVariableToSlaveEnvValues.entrySet()) {
        // Create selected values structure.
        List<EnvironmentVariableValue> values = new ArrayList<EnvironmentVariableValue>(0);
        // Cycle through potential slave values.
        for (EnvironmentVariableValue environmentVariableValue : entry.getValue()) {
          // Get context.
          UseBaselineEnvironmentContext currentContext = _envValueToUseBaselineContext.get(environmentVariableValue);
          // Make sure this is a slave to specified context.
          if ((null != currentContext) && (currentContext._masterContext == context_p)) {
            // Keep it.
            values.add(environmentVariableValue);
          }
        }
        // Retain result.
        result.put(entry.getKey(), values);
      }
      return result;
    }

    /**
     * Is specified environment (as pointed by an environment variable value) compliant with the starting point mechanism ?
     * @param environmentValue_p
     * @return <code>true</code> if so, <code>false</code> if not, or an error occurred.
     */
    protected boolean isUseBaselineCompliant(EnvironmentVariableValue environmentValue_p) {
      // Get environment handler of given environment value.
      Couple<IStatus, IEnvironmentHandler> couple =
          EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironmentHandler(environmentValue_p.getEnvironmentId());
      if (!couple.getKey().isOK()) {
        // No handler found, can't tell (default to false).
        return false;
      }
      // Get EnvironmentStartingPoint handler.
      IEnvironmentHandler handler = couple.getValue();
      // Create context.
      // TODO Damien
      // Add a specific constructor here.
      EnvironmentContext context = new EnvironmentContext();
      context._attributes = ModelUtil.convertEnvironmentVariableValues(environmentValue_p);
      context._environmentId = environmentValue_p.getEnvironmentId();
      context._runtimeId = environmentValue_p.getId();
      return handler.isUseBaselineCompliant(context);
    }
  }
}
