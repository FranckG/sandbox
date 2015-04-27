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
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
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
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;

/**
 * This is the item provider adapter for a {@link com.thalesgroup.orchestra.framework.model.contexts.Category} object. <!-- begin-user-doc --> <!-- end-user-doc
 * -->
 * @generated
 */
public class CategoryItemProvider extends NamedElementItemProvider implements IEditingDomainItemProvider, IStructuredItemContentProvider,
    ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
  public static final String IMAGE_PATH_ENABLED_CATEGORY = "full/obj16/Category"; //$NON-NLS-1$
  public static final String IMAGE_PATH_DISABLED_CATEGORY = "full/obj16/DCategory"; //$NON-NLS-1$

  /**
   * This constructs an instance from a factory and a notifier.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public CategoryItemProvider(AdapterFactory adapterFactory) {
    super(adapterFactory);
  }

  /**
   * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createAddCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject,
   *      org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection, int)
   */
  @Override
  protected Command createAddCommand(EditingDomain domain_p, EObject owner_p, EStructuralFeature feature_p, Collection<?> collection_p, int index_p) {
    // Test if only one element is created, most likely from an action.
    if ((null != collection_p) && (1 == collection_p.size())) {
      Object newElement = collection_p.iterator().next();
      if (!(newElement instanceof EObject)) {
        return UnexecutableCommand.INSTANCE;
      }
      EObject newObject = (EObject) newElement;
      // Compare containing context to edition one.
      if (domain_p instanceof IAdaptable) {
        IEditionContextProvider provider = (IEditionContextProvider) ((IAdaptable) domain_p).getAdapter(IEditionContextProvider.class);
        if (null != provider) {
          Context editionContext = provider.getEditionContext();
          Context currentContext = ModelUtil.getContext(owner_p);
          // This is an element contributed to a super category.
          // Do not add it to the asking category directly.
          // Instead, add it to edition context.
          if ((null != editionContext) && (currentContext != editionContext)) {
            // Pick containing reference.
            EReference reference = null;
            if (newObject instanceof Variable) {
              reference = ContextsPackage.Literals.CONTEXT__SUPER_CATEGORY_VARIABLES;
            } else if (newObject instanceof Category) {
              reference = ContextsPackage.Literals.CONTEXT__SUPER_CATEGORY_CATEGORIES;
            }
            // Forbidden contribution so far, stop here.
            if (null == reference) {
              return UnexecutableCommand.INSTANCE;
            }
            CompoundCommand command = new CompoundCommand();
            // Do set category before adding element to edition context.
            command.append(new SetCommand(domain_p, newObject, ContextsPackage.Literals.CONTRIBUTED_ELEMENT__SUPER_CATEGORY, owner_p));
            if (newObject instanceof Variable) {
              // Add new empty variable value.
              VariableValue newValue = null;
              if (newObject instanceof EnvironmentVariable) {
                newValue = ContextsFactory.eINSTANCE.createEnvironmentVariableValue();
              } else {
                newValue = ContextsFactory.eINSTANCE.createVariableValue();
              }
              command.append(new AddCommand(domain_p, newObject, ContextsPackage.Literals.ABSTRACT_VARIABLE__VALUES, newValue));
            }
            // Add to edition context.
            command.append(super.createAddCommand(domain_p, editionContext, (EStructuralFeature) reference, collection_p, index_p));
            return command;
          }
        }
      }
    }
    // Default behavior.
    CompoundCommand result = new CompoundCommand();
    // Add a new empty variable value for each created variable.
    for (Object object : collection_p) {
      if (object instanceof Variable) {
        VariableValue newValue = null;
        if (object instanceof EnvironmentVariable) {
          newValue = ContextsFactory.eINSTANCE.createEnvironmentVariableValue();
        } else {
          newValue = ContextsFactory.eINSTANCE.createVariableValue();
        }
        result.append(new AddCommand(domain_p, (EObject) object, ContextsPackage.Literals.ABSTRACT_VARIABLE__VALUES, newValue));
      }
    }
    // Default behavior.
    result.append(super.createAddCommand(domain_p, owner_p, feature_p, collection_p, index_p));
    return result;
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

      addSuperCategoryPropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Super Category feature.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  protected void addSuperCategoryPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_ContributedElement_superCategory_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_ContributedElement_superCategory_feature", "_UI_ContributedElement_type"),
         ContextsPackage.Literals.CONTRIBUTED_ELEMENT__SUPER_CATEGORY,
         true,
         false,
         true,
         null,
         null,
         null));
  }

  /**
   * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
   * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
   * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
    if (childrenFeatures == null) {
      super.getChildrenFeatures(object);
      childrenFeatures.add(ContextsPackage.Literals.CATEGORY__VARIABLES);
      childrenFeatures.add(ContextsPackage.Literals.CATEGORY__CATEGORIES);
    }
    return childrenFeatures;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EStructuralFeature getChildFeature(Object object, Object child) {
    // Check the type of the specified child object and return the proper feature to use for
    // adding (see {@link AddCommand}) it as a child.

    return super.getChildFeature(object, child);
  }

  /**
   * This returns Category.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public Object getImage(Object object) {
    Category category = (Category) object;
    if (ModelUtil.isTransientElement(category)) {
      return overlayImage(object, getResourceLocator().getImage(IMAGE_PATH_DISABLED_CATEGORY));
    }
    return overlayImage(object, getResourceLocator().getImage(IMAGE_PATH_ENABLED_CATEGORY));
  }

  /**
   * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String getText(Object object) {
    return super.getText(object);
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

    switch (notification.getFeatureID(Category.class)) {
      case ContextsPackage.CATEGORY__VARIABLES:
      case ContextsPackage.CATEGORY__CATEGORIES:
        fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
        return;
    }
    super.notifyChanged(notification);
  }

  /**
   * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that can be created under this object. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
    super.collectNewChildDescriptors(newChildDescriptors, object);

    newChildDescriptors.add(createChildParameter(ContextsPackage.Literals.CATEGORY__VARIABLES, ContextsFactory.eINSTANCE.createVariable()));

    newChildDescriptors.add(createChildParameter(ContextsPackage.Literals.CATEGORY__VARIABLES, ContextsFactory.eINSTANCE.createFileVariable()));

    newChildDescriptors.add(createChildParameter(ContextsPackage.Literals.CATEGORY__VARIABLES, ContextsFactory.eINSTANCE.createFolderVariable()));

    newChildDescriptors.add(createChildParameter(ContextsPackage.Literals.CATEGORY__CATEGORIES, ContextsFactory.eINSTANCE.createCategory()));
  }
}