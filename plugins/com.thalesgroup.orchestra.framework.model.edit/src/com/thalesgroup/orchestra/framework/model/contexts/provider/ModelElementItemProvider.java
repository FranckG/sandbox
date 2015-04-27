/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;

/**
 * This is the item provider adapter for a {@link com.thalesgroup.orchestra.framework.model.contexts.ModelElement} object.
 * <!-- begin-user-doc --> <!--
 * end-user-doc -->
 * @generated
 */
public class ModelElementItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider, IStructuredItemContentProvider,
    ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

  private Map<EObject, Object> _images;

  /**
   * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated NOT
   */
  public ModelElementItemProvider(AdapterFactory adapterFactory) {
    super(adapterFactory);
    _images = new HashMap<EObject, Object>();
  }

  /**
   * This adds a property descriptor for the Id feature.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  protected void addIdPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_ModelElement_id_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_ModelElement_id_feature", "_UI_ModelElement_type"),
         ContextsPackage.Literals.MODEL_ELEMENT__ID,
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
   * Get the background image of the specified child by adaptation and overlays the plus.
   * 
   * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getCreateChildImage(java.lang.Object, java.lang.Object, java.lang.Object, java.util.Collection)
   */
  @Override
  public Object getCreateChildImage(Object owner_p, Object feature_p, Object child_p, Collection<?> selection_p) {
    IItemLabelProvider labelProvider = (IItemLabelProvider) adapterFactory.adapt(child_p, IItemLabelProvider.class);
    if (null != labelProvider) {
      Object image = labelProvider.getImage(child_p);
      return getOverlayImage(child_p, image);
    }
    return super.getCreateChildImage(owner_p, feature_p, child_p, selection_p);
  }
  
  /**
   * Get the text of the specified child by adaptation.
   * 
   * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getCreateChildText(java.lang.Object, java.lang.Object, java.lang.Object, java.util.Collection)
   */
  @Override
  public String getCreateChildText(Object owner_p, Object feature_p, Object child_p, Collection<?> selection_p) {
    if (child_p instanceof EObject) {
      EObject child = (EObject) child_p;
      return child.eClass().getName();
    }
    return super.getCreateChildText(owner_p, feature_p, child_p, selection_p);
  }
  /**
   * Compose the image for the specified feature with a 'plus' overlay.
   * @param child_p
   * @param image_p background image.
   * @return
   */
  private Object getOverlayImage(Object child_p, Object image_p) {
    EObject child = (EObject) child_p;
    if (!_images.containsKey(child)) {
      List<Object> images = new ArrayList<Object>();
      images.add(image_p);
      images.add(getResourceLocator().getImage("full/ovr16/plus")); //$NON-NLS-1$
      ComposedImage image = new ComposedImage(images) {
        @Override
        public List<Point> getDrawPoints(Size size_p) {
          List<Point> drawPoints = super.getDrawPoints(size_p);
          Point p = drawPoints.get(1);
          p.x = 9;
          return drawPoints;
        }
      };
      _images.put(child, image);
    }
    return _images.get(child_p);
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

      addIdPropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * Return the resource locator for this item provider's resources.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ResourceLocator getResourceLocator() {
    return ContextsEditPlugin.INSTANCE;
  }

  /**
   * This returns the label text for the adapted class.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getText(Object object) {
    String label = ((ModelElement)object).getId();
    return label == null || label.length() == 0 ?
      getString("_UI_ModelElement_type") :
      getString("_UI_ModelElement_type") + " " + label;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean hasChildren(Object object) {
    return hasChildren(object, true);
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

    switch (notification.getFeatureID(ModelElement.class)) {
      case ContextsPackage.MODEL_ELEMENT__ID:
        fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
    }
    super.notifyChanged(notification);
  }

}
