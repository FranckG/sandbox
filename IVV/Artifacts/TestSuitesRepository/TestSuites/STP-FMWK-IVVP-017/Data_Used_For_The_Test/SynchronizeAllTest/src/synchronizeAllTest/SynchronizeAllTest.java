/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package synchronizeAllTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.puci.PUCI;

/**
 * @author s0040806
 */
public class SynchronizeAllTest {

  /**
   * @param args
   */
  public static void main(String[] args) {

    try {

      List<OrchestraURI> list = new ArrayList<OrchestraURI>();
      list.add(new OrchestraURI("FrameworkCommands", "FC")); //$NON-NLS-1$ //$NON-NLS-2$

      Map<String, String> a = PUCI.executeSpecificCommand("SynchronizeAllContexts", list); //$NON-NLS-1$
    } catch (Exception exception_p) {

    }
    System.exit(0);
  }
}
