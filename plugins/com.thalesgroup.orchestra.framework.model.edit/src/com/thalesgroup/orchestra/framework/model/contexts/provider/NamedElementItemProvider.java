/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.thalesgroup.orchestra.framework.model.IEditionContextProvider;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;
import com.thalesgroup.orchestra.framework.model.edit.internal.NameProviderHolder;

/**
 * This is the item provider adapter for a {@link com.thalesgroup.orchestra.framework.model.contexts.NamedElement} object.
 * <!-- begin-user-doc --> <!--
 * end-user-doc -->
 * @generated
 */
public class NamedElementItemProvider extends ModelElementItemProvider implements IEditingDomainItemProvider, IStructuredItemContentProvider,
    ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
  /**
   * This constructs an instance from a factory and a notifier.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public NamedElementItemProvider(AdapterFactory adapterFactory) {
    super(adapterFactory);
  }

  /**
   * This adds a property descriptor for the Name feature.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  protected void addNamePropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_NamedElement_name_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_NamedElement_name_feature", "_UI_NamedElement_type"),
         ContextsPackage.Literals.NAMED_ELEMENT__NAME,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
   * that can be created under this object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
    super.collectNewChildDescriptors(newChildDescriptors, object);
  }

  /**
   * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createAddCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject,
   *      org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection, int)
   */
  @Override
  protected Command createAddCommand(EditingDomain domain_p, EObject owner_p, EStructuralFeature feature_p, Collection<?> collection_p, int index_p) {
    CompoundCommand command = new CompoundCommand();
    command.append(super.createAddCommand(domain_p, owner_p, feature_p, collection_p, index_p));
    for (Object element : collection_p) {
      if (element instanceof NamedElement) {
        final NamedElement namedElement = (NamedElement) element;
        // Do set only if element has no name yet.
        if (null == namedElement.getName()) {
          // Set default name.
          command.append(new SetNewNameCommand(domain_p, namedElement, owner_p));
        }
      }
    }
    return command;
  }

  /**
   * This returns the property descriptors for the adapted class.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
    if (itemPropertyDescriptors == null) {
      super.getPropertyDescriptors(object);

      addNamePropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String getText(Object object) {
    return ((NamedElement) object).getName();
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update any cached
   * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void notifyChanged(Notification notification) {
    updateChildren(notification);

    switch (notification.getFeatureID(NamedElement.class)) {
      case ContextsPackage.NAMED_ELEMENT__NAME:
        fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
    }
    super.notifyChanged(notification);
  }

  /**
   * Set new name command.
   * @author T0052089
   */
  protected class SetNewNameCommand extends SetCommand {
    /**
     * Edition context.
     */
    private Context _editionContext;
    /**
     * Parent element.
     */
    private EObject _parent;

    /**
     * Constructor.
     * @param domain_p
     * @param elementToName_p
     * @param parent_p
     */
    public SetNewNameCommand(EditingDomain domain_p, EObject elementToName_p, EObject parent_p) {
      super(domain_p, elementToName_p, ContextsPackage.Literals.NAMED_ELEMENT__NAME, ""); //$NON-NLS-1$
      _parent = parent_p;
      if (domain_p instanceof IAdaptable) {
        IEditionContextProvider provider = (IEditionContextProvider) ((IAdaptable) domain_p).getAdapter(IEditionContextProvider.class);
        if (null != provider) {
          _editionContext = provider.getEditionContext();
        }
      }
    }

    /**
     * @see org.eclipse.emf.edit.command.SetCommand#doExecute()
     */
    @Override
    public void doExecute() {
      if ((null != _editionContext) && (null != _parent)) {
        value = getNewName(_editionContext, _parent, (NamedElement) owner);
      }
      super.doExecute();
    }

    /**
     * Get a new name for specified element in specified container for specified edition context.
     * @param editionContext_p
     * @param parent_p
     * @param element_p
     * @return
     */
    protected String getNewName(Context editionContext_p, EObject parent_p, NamedElement element_p) {
      return NameProviderHolder.getInstance().getNameProvider().computeUniqueName(editionContext_p, element_p, parent_p);
    }
  }
}