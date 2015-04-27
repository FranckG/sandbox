/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.artefacts;

import com.thalesgroup.orchestra.framework.lib.utils.uri.BadOrchestraURIException;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;

/**
 * Factory to build {@link IArtefact}s.
 * @author S0024585
 */
public class ArtefactFactory {
  /**
   * @param uri_p The {@link OrchestraURI} of the artefact
   * @return an {@link IArtefact} from the {@link OrchestraURI}
   */
  public static IArtefact buildArtefact(OrchestraURI uri_p) {
    boolean rootArtefact = (uri_p.getObjectType() != null) && (!uri_p.getObjectType().isEmpty());
    return new Artefact(uri_p, rootArtefact);
  }

  /**
   * @param uri_p The 'readable' URI string of the artefact
   * @return an {@link IArtefact} from an URI string
   */
  public static IArtefact buildArtefact(String uri_p) throws BadOrchestraURIException {
    OrchestraURI uri = new OrchestraURI(uri_p);
    return buildArtefact(uri);
  }
}
