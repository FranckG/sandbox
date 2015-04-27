package com.thalesgroup.orchestra.framework.connector.contribution;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.wizard.IWizardPage;

/**
 * /** A class providing information on how to implement a connector contributor. This contributor will add pages to the artifact creation wizard to manage the
 * parameters when needed.
 * @author s0018747
 */
public abstract class AbstractParametersHandler {
  
  /** 
   * Creation parameters handled for registered tool.
   */
  private HashMap<String, String> _parameters;

  /**
   * Give an array of pages to be used by the artifact creation wizard.
   * @return Wizard pages associated to one tool.
   */
  public abstract IWizardPage[] createParametersWizardPages(ICreationCallback callback_p);

  /**
   * Give a Map of key/values for the parameters entered through the added wizard pages.
   * @return Map of parameters.
   */
  public Map<String, String> getParametersWizardPages() {
    if (null == _parameters) {
      _parameters = new HashMap<String, String>(0);
    }
    return _parameters;
  }
}