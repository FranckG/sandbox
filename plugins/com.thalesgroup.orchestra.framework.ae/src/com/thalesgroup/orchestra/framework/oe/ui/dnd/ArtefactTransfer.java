/**
 * <p>Copyright (c) 2002-2008 : Thales Services - Engineering & Process Management</p>
 * <p>Société : Thales Services - Engineering & Process Management</p>
 * <p>Thales Part Number 16 262 601</p>
 */
package com.thalesgroup.orchestra.framework.oe.ui.dnd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import com.thalesgroup.orchestra.framework.lib.utils.uri.BadOrchestraURIException;
import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;

/**
 * <p>
 * Title : ArtifactTransfer
 * </p>
 * <p>
 * Description : Class for serializing artifacts from/to a byte array
 * </p>
 * @author Orchestra Tool Suite developer
 * @version 3.7.0
 */
public class ArtefactTransfer extends ByteArrayTransfer {
  // The shared instance
  private static ArtefactTransfer _instance = new ArtefactTransfer();
  // The type of the format
  private static final String TYPE_NAME = "artifact-transfer-format"; //$NON-NLS-1$
  // ID of the format type
  private static final int TYPEID = registerType(TYPE_NAME);

  /**
   * Avoid explicit instantiation
   */
  private ArtefactTransfer() {
    // nothing
  }

  /**
   * Read all artefact from a byte array
   * @param bytes Byte array containing artefacts data
   * @return
   */
  protected IArtefact[] fromByteArray(byte[] bytes) {
    DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
    try {
      /* read number of artifacts */
      int n = in.readInt();
      /* read artifacts */
      IArtefact[] artifacts = new IArtefact[n];
      for (int i = 0; i < n; i++) {
        artifacts[i] = new Artefact(in);
      }
      return artifacts;
    } catch (IOException e) {
      return null;
    } catch (BadOrchestraURIException exception_p) {
      return null;
    }
  }

  /**
   * @see org.eclipse.swt.dnd.Transfer#getTypeIds()
   */
  @Override
  protected int[] getTypeIds() {
    return new int[] { TYPEID };
  }

  /**
   * @see org.eclipse.swt.dnd.Transfer#getTypeNames()
   */
  @Override
  protected String[] getTypeNames() {
    return new String[] { TYPE_NAME };
  }

  /**
   * @see org.eclipse.swt.dnd.ByteArrayTransfer#javaToNative(java.lang.Object, org.eclipse.swt.dnd.TransferData)
   */
  @Override
  protected void javaToNative(Object object, TransferData transferData) {
    byte[] bytes = toByteArray((Artefact[]) object);
    if (bytes != null)
      super.javaToNative(bytes, transferData);
  }

  /**
   * @see org.eclipse.swt.dnd.ByteArrayTransfer#nativeToJava(org.eclipse.swt.dnd.TransferData)
   */
  @Override
  protected Object nativeToJava(TransferData transferData) {
    byte[] bytes = (byte[]) super.nativeToJava(transferData);
    return fromByteArray(bytes);
  }

  /**
   * @param artifacts
   * @return
   */
  protected byte[] toByteArray(Artefact[] artifacts) {
    /**
     * Transfer data is an array of artifacts. Serialized version is: (String) papeeteUri (String) fragmentName (String) serviceParameters ... repeat for each
     * subsequent artifact see writeArtifact for the (Artifact) format.
     */
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(byteOut);
    byte[] bytes = null;
    try {
      /* write number of markers */
      out.writeInt(artifacts.length);
      /* write markers */
      for (int i = 0; i < artifacts.length; i++) {
        artifacts[i].export(out);
      }
      out.close();
      bytes = byteOut.toByteArray();
    } catch (IOException e) {
      // when in doubt send nothing
    }
    return bytes;
  }

  /**
   * Returns the singleton artifact transfer instance.
   */
  public static ArtefactTransfer getInstance() {
    return _instance;
  }
}