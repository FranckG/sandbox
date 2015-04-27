/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMLSave;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMISaveImpl;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.model.ModelActivator;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.migration.IMigrationHandler;

/**
 * <!-- begin-user-doc --> The <b>Resource </b> associated with the package. <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceFactoryImpl
 * @generated
 */
public class ContextsResourceImpl extends XMIResourceImpl {
  /**
   * New notification type : context synchronized.
   */
  public static final int NOTIFICATION_TYPE_CONTEXT_SYNCHRONIZED = Notification.EVENT_TYPE_COUNT + 1000;
  /**
   * Save option to force save on default context.
   */
  public static final String OPTION_SAVE_FORCE_FOR_DEFAULT_CONTEXT = "ForceSaveDefaultContext"; //$NON-NLS-1$
  /**
   * Should external contributions from the context model be disabled ? <code>true</code> to disable contributions, <code>false</code> to enable them (default).
   */
  private volatile boolean _disableAllContributions;
  /**
   * Is resource currently being unloaded ?
   */
  private volatile boolean _isUnloading;
  /**
   * {@link List} of {@link AdditionalSaveOperation}s executed after save occurred.<br>
   * This list is flushed each time a save is successfully completed.<br>
   * This is not a permanent behavior.
   */
  private List<AdditionalSaveOperation> _additionalSaveOperations;

  /**
   * Creates an instance of the resource. <!-- begin-user-doc --> <!-- end-user-doc -->
   * @param uri_p the URI of the new resource.
   * @generated NOT
   */
  public ContextsResourceImpl(URI uri_p) {
    super(uri_p);
    _additionalSaveOperations = new ArrayList<AdditionalSaveOperation>(0);
    _disableAllContributions = false;
  }

  /**
   * Add a new additional save operation to this resource (if it isn't already present in the _additionalSaveOperations list).<br>
   * Note that there is no need for a removal method for {@link AdditionalSaveOperation} list is flushed each time a successful save occurs.<br>
   * This is up to the callable implementation not to break the save process.<br>
   * If an exception is thrown within the execute method of an additional save operation implementation, then the {@link List} isn't flushed.
   * @param callable_p A not <code>null</code> implementation.
   */
  public void addAdditionalSaveOperation(AdditionalSaveOperation callable_p) {
    if (null != callable_p && !_additionalSaveOperations.contains(callable_p)) {
      _additionalSaveOperations.add(callable_p);
    }
  }

  /**
   * Contained context has been synchronized.<br>
   * Notify listeners.
   */
  public void contextSynchronized() {
    // Create a context synchronized notification.
    Notification notification = new ENotificationImpl((InternalEObject) ModelUtil.getContext(this), NOTIFICATION_TYPE_CONTEXT_SYNCHRONIZED, null, null, null);
    // Fire it.
    for (Adapter adapter : eAdapters) {
      adapter.notifyChanged(notification);
    }
  }

  /**
   * @see org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl#createXMLSave()
   */
  @Override
  protected XMLSave createXMLSave() {
    return new XMISaveImpl(createXMLHelper()) {
      /**
       * @see org.eclipse.emf.ecore.xmi.impl.XMLSaveImpl#shouldSaveFeature(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
       */
      @Override
      protected boolean shouldSaveFeature(EObject o_p, EStructuralFeature f_p) {
        // Always save the ID feature.
        if (ContextsPackage.Literals.MODEL_ELEMENT__ID.equals(f_p)) {
          return true;
        }
        return super.shouldSaveFeature(o_p, f_p);
      }
    };
  }

  /**
   * Disable context model all external contributions for current resource.
   */
  public void disableContributions() {
    _disableAllContributions = true;
  }

  /**
   * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#doLoad(java.io.InputStream, java.util.Map)
   */
  @Override
  public void doLoad(InputStream inputStream_p, Map<?, ?> options_p) throws IOException {
    super.doLoad(inputStream_p, options_p);
    // Contributions disabled, stop here.
    if (_disableAllContributions) {
      return;
    }
    // Get context.
    Context context = ModelUtil.getContext(this);
    // Default context contributions.
    if (isDefault()) {
      // Add external contributions to default context.
      ModelUtil.addContributionsToContext(context);
      // Add local configuration to default context.
      ModelUtil.addInstallationStructureToSpecifiedContext(context);
      // ModelUtil.addVariablesFromWindowsRegistryToSpecifiedContext(context);
    } else { // Do not apply to default context.
      // Do apply automatic migration, if needed.
      IMigrationHandler migrationHandler = ModelActivator.getInstance().getMigrationHandler();
      if ((null != migrationHandler) && migrationHandler.isHandlingMigrationFor(context)) {
        migrationHandler.migrateContext(context);
      }
      // Find and collect pending elements for specified context.
      ModelUtil.addPendingElementsCategoryToSpecifiedContext(context);
      // Try and add current versions selection, if needed.
      ModelUtil.addCurrentVersionsCategories(context);
    }
  }

  /**
   * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#doUnload()
   */
  @Override
  protected void doUnload() {
    markUnloading();
    try {
      super.doUnload();
    } finally {
      _isUnloading = false;
    }
  }

  /**
   * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#getURI()
   * @generated NOT
   */
  @Override
  public URI getURI() {
    return FileHelper.correctURICase(super.getURI());
  }

  /**
   * Is current resource default context one ?
   * @return
   */
  public boolean isDefault() {
    return FileHelper.getFileFullUri(ICommonConstants.DEFAULT_CONTEXTS_PATH).equals(getURI());
  }

  /**
   * Is resource being currently unloaded ?
   * @return <code>true</code> if so, <code>false</code> otherwise.
   */
  public boolean isUnloading() {
    return _isUnloading;
  }

  /**
   * Mark resource as unloading.
   */
  public void markUnloading() {
    _isUnloading = true;
  }

  /**
   * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#save(java.util.Map)
   */
  @SuppressWarnings("unchecked")
  @Override
  public void save(Map<?, ?> options_p) throws IOException {
    Map<Object, Object> options = (Map<Object, Object>) options_p;
    if (null == options) {
      options = new HashMap<Object, Object>(1);
    }
    // Precondition.
    // Do not save default context resource.
    // Unless it is forced to.
    if (!Boolean.TRUE.equals(options.get(OPTION_SAVE_FORCE_FOR_DEFAULT_CONTEXT)) && isDefault()) {
      return;
    }
    // Check if AdditionalSaveOperations are executable, if not abort the save operation.
    for (AdditionalSaveOperation callable : _additionalSaveOperations) {
      IStatus isExecutableStatus = callable.isExecutable();
      if (!isExecutableStatus.isOK()) {
        throw new IOException(isExecutableStatus.getMessage(), isExecutableStatus.getException());
      }
    }
    // Save the .context file.
    options.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
    super.save(options);
    // Execute AdditionalSaveOperations.
    try {
      for (AdditionalSaveOperation callable : _additionalSaveOperations) {
        callable.execute();
      }
      // Everything worked fine, clean list of AdditionalSaveOperations.
      _additionalSaveOperations.clear();
    } catch (IOException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new IOException(ex);
    }
  }

  /**
   * This interface has to be implemented to define additional save operations (operations executed when a context resource is saved).
   * @author T0052089
   */
  public static interface AdditionalSaveOperation {
    /**
     * The operation code. Should execute successfully if isExecutable() returned OK.
     * @throws Exception
     */
    public void execute() throws Exception;

    /**
     * Is this additional operation executable ?
     * @return an {@link IStatus} with an OK severity if the operation is executable, a non OK severity otherwise.
     */
    public IStatus isExecutable();
  }
} // ContextsResourceImpl