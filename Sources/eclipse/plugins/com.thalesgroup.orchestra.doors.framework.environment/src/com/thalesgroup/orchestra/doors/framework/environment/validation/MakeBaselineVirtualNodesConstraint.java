package com.thalesgroup.orchestra.doors.framework.environment.validation;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironment;
import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironment.ProjectEnvironmentData;
import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironmentHandler;
import com.thalesgroup.orchestra.doors.framework.environment.model.DoorsProject;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.EnvironmentAttributes;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.validation.AbstractEnvironmentRule;
import com.thalesgroup.orchestra.framework.environment.validation.EnvironmentRuleContext;
import com.thalesgroup.orchestra.framework.exchange.StatusHandler;
import com.thalesgroup.orchestra.framework.exchange.status.Status;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.gef.Element;
import com.thalesgroup.orchestra.framework.gef.GEF;
import com.thalesgroup.orchestra.framework.gef.GenericExportFormat;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

public class MakeBaselineVirtualNodesConstraint extends AbstractEnvironmentRule {
  /**
   * DOORS Environment unique ID
   */
  private final String _doorsEnvironmentId = "com.thalesgroup.orchestra.doors.framework.environment"; //$NON-NLS-1$

  /**
   * Test if there is at least one virtual project in the environment. If so, it's not a valid environment for MakeBaseline feature.
   * @see com.thalesgroup.orchestra.framework.environment.validation.AbstractEnvironmentRule#doValidate()
   */
  @Override
  protected void doValidate() {
    // The Doors projects map to merge content of database and environment.
    Map<String, DoorsProject> doorsProjectsMap = new HashMap<String, DoorsProject>();
    // Get all Doors environment.
    Collection<EnvironmentRuleContext> contexts = getContextsFor(_doorsEnvironmentId);
    // Get DoorsEnvironnementHandler.
    Couple<IStatus, IEnvironmentHandler> environmentHandlerCouple =
        EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironmentHandler(_doorsEnvironmentId);
    DoorsEnvironmentHandler doorsEnvironmentHandler = null;
    // Precondition on handler.
    if (environmentHandlerCouple.getKey().isOK() || (environmentHandlerCouple.getValue() instanceof DoorsEnvironmentHandler)) {
      doorsEnvironmentHandler = (DoorsEnvironmentHandler) environmentHandlerCouple.getValue();
    }
    for (EnvironmentRuleContext envRulecontext : contexts) {
      if (null == doorsEnvironmentHandler) {
        addFailure(envRulecontext._attributes.get(0), "DoorsEnvironmentHandler is unreachable"); //$NON-NLS-1$
      }
      // Retrieve all the DoorsEnvironments
      List<EnvironmentAttributes> environmentList = envRulecontext._attributes;
      int size = environmentList.size();
      // Check all Doors environments.
      for (int i = 0; i < size; i++) {
        EnvironmentAttributes doorsEnvAttributes = envRulecontext._attributes.get(i);
        // Clear the projectMap.
        doorsProjectsMap.clear();
        // Get the projects list from Doors Database.
        OrchestraURI uri =
            doorsEnvironmentHandler.getMinimalistDoorsUriToExecuteSpecificCommand(doorsEnvAttributes._expandedAttributes.get(
                DoorsEnvironmentHandler.ATTRIBUTE_KEY_DOORS_DATABASE_FULL_ADRESS).get(0));
        StatusDefinition statusDefinition = doorsEnvironmentHandler.executeDoorsSpecificCommand(uri, DoorsEnvironmentHandler.GET_PROJECTS_COMMAND);
        if ((null == statusDefinition)) {
          addFailure(doorsEnvAttributes, "Validation MakeBaseline: GetProjectCommand return no status definition"); //$NON-NLS-1$
        }

        StatusHandler handlerStatus = new StatusHandler();
        Status commandStatus = handlerStatus.getStatusForUri(statusDefinition, uri.getUri());
        if (null == commandStatus) {
          addFailure(doorsEnvAttributes, "Validation MakeBaseline: Impossible to get Status from StatusDefinition"); //$NON-NLS-1$
        }

        String gefFilePath = commandStatus.getExportFilePath();
        if (null == gefFilePath) {
          addFailure(doorsEnvAttributes, "Validation MakeBaseline: No GEF file path"); //$NON-NLS-1$
        }

        GEF gef = doorsEnvironmentHandler.getGefHandler().loadModel(gefFilePath);
        // Retrieve the data from the GEF file
        for (GenericExportFormat databaseContent : gef.getGENERICEXPORTFORMAT()) {
          for (Element environment : databaseContent.getELEMENT()) {
            // Get every project
            for (Element project : environment.getELEMENT()) {
              if ("Project".equals(project.getType())) { //$NON-NLS-1$
                String projectId = new OrchestraURI(project.getUri()).getObjectId();
                String name = project.getFullName();
                DoorsProject projectModel = new DoorsProject(project.getUri(), name, false, projectId, name);
                doorsProjectsMap.put(projectId, projectModel);
              }
            }
          }
        }
        // Unload the model
        doorsEnvironmentHandler.getGefHandler().unloadModel(gef);

        // Get the projects list attributes from environment.
        List<ProjectEnvironmentData> projectsEnvironmentData =
            DoorsEnvironment.deserializeProjectList(environmentList.get(i)._attributes.get(DoorsEnvironment.ATTRIBUT_KEY_PROJECT_LIST));
        for (ProjectEnvironmentData projectEnvironmentData : projectsEnvironmentData) {
          String doorsProjectId = projectEnvironmentData._doorsProjectId;

          DoorsProject currentDoorsProject = doorsProjectsMap.get(doorsProjectId);
          // There is a virtual node in the context. The context is not valid for MakeBaseline.
          if (null == currentDoorsProject) {
            addFailure(doorsEnvAttributes, "The Doors environment contains a virtual node. This context is not valid for MakeBaseline feature."); //$NON-NLS-1$
          }
        }
      }
    }
  }
}
