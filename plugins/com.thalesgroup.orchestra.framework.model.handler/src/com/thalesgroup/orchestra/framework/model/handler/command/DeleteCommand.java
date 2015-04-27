/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.command;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;

/**
 * A model command that deletes specified elements, along with their references.<br>
 * This code is taken from {@link org.eclipse.emf.edit.command.DeleteCommand}, but differs at cross referencing time, and allow for sub-classing deletion
 * behavior.
 * @author t0076261
 */
public class DeleteCommand extends CompoundCommand {
  /**
   * Edition context.
   */
  protected Context _editionContext;
  /**
   * Collections of elements to be deleted.
   */
  protected Collection<ModelElement> _elementsToDelete;

  /**
   * Constructor.
   * @param collection_p
   * @param editionContext_p
   */
  public DeleteCommand(Collection<ModelElement> collection_p, Context editionContext_p) {
    _elementsToDelete = collection_p;
    _editionContext = editionContext_p;
  }

  /**
   * Confirm deletion of the specified elements, with specified impacted elements (usages) ?
   * @param deletedElements_p
   * @param usages_p
   * @return <code>true</code> to go on with deletion, <code>false</code> to stop it.
   */
  protected boolean confirmDeletion(Collection<EObject> deletedElements_p, Map<EObject, Collection<EStructuralFeature.Setting>> usages_p) {
    return true;
  }

  /**
   * Do add an initial command to this compound one.<br>
   * Default implementation adds an EMF {@link RemoveCommand}.
   */
  protected void doPrepare() {
    append(RemoveCommand.create(ModelHandlerActivator.getDefault().getEditingDomain(), _elementsToDelete));
  }

  /**
   * @see org.eclipse.emf.common.command.CompoundCommand#execute()
   */
  @Override
  public void execute() {
    // Unwrap elements to be deleted.
    Collection<EObject> eObjects = new UniqueEList<EObject>();
    for (Object wrappedObject : _elementsToDelete) {
      Object object = AdapterFactoryEditingDomain.unwrap(wrappedObject);
      if (object instanceof EObject) {
        eObjects.add((EObject) object);
        for (Iterator<EObject> j = ((EObject) object).eAllContents(); j.hasNext();) {
          eObjects.add(j.next());
        }
      } else if (object instanceof Resource) {
        for (Iterator<EObject> j = ((Resource) object).getAllContents(); j.hasNext();) {
          eObjects.add(j.next());
        }
      }
    }

    Map<EObject, Collection<EStructuralFeature.Setting>> usages = new HashMap<EObject, Collection<Setting>>(0);
    for (EObject eObject : eObjects) {
      // Precondition
      if (!(eObject instanceof ModelElement)) {
        continue;
      }
      // Find cross referencer to use from the edition context of the object to delete.
      ECrossReferenceAdapter crossReferencer = DataUtil.getCrossReferencer(getEditionContext(eObject));
      Collection<Setting> inverseReferences = crossReferencer.getInverseReferences(eObject, true);
      // Remove containment references out of inverse references, there are handled natively by the command.
      for (Iterator<Setting> settings = inverseReferences.iterator(); settings.hasNext();) {
        Setting setting = settings.next();
        try {
          // Test feature against reference, and containment.
          if (EReference.class.cast(setting.getEStructuralFeature()).isContainment()) {
            // Remove this feature from inverse references.
            settings.remove();
          }
        } catch (ClassCastException cce_p) {
          // Not a reference, skip it.
        }
      }
      usages.put(eObject, inverseReferences);
    }

    boolean proceed = confirmDeletion(eObjects, usages);
    if (!proceed) {
      return;
    }
    // Do something just before deleting elements.
    preExecute();

    // Execute composing commands.
    super.execute();

    // Do one last thing.
    postExecute();
  }

  /**
   * Gives edition context corresponding to given element.
   * @param object_p
   * @return
   */
  public Context getEditionContext(Object object_p) {
    return _editionContext;
  }

  /**
   * Execution is about to be finished.<br>
   * Default implementation does nothing.
   */
  protected void postExecute() {
    // Do nothing.
  }

  /**
   * Deletion is about to take place.<br>
   * At this point, the model has been unwrapped, but is not yet modified (in terms of deletions).
   */
  protected void preExecute() {
    // Do nothing.
  }

  /**
   * @see org.eclipse.emf.common.command.CompoundCommand#prepare()
   */
  @Override
  protected boolean prepare() {
    // Selection is empty, deletion is impossible.
    if (_elementsToDelete.isEmpty()) {
      return false;
    }
    boolean preConditions = preparePreConditions();
    // Preconditions not met, stop here.
    if (!preConditions) {
      return false;
    }

    // What should ODM do if all values of a variable are deleted ?
    // Firstly : find variables impacted by values deletion.
    Set<AbstractVariable> impactedVariables = new HashSet<AbstractVariable>();
    Collection<VariableValue> variablesValuesToDelete = EcoreUtil.getObjectsByType(_elementsToDelete, ContextsPackage.Literals.VARIABLE_VALUE);
    for (VariableValue variablesValue : variablesValuesToDelete) {
      EObject container = variablesValue.eContainer();
      if (container instanceof AbstractVariable) {
        impactedVariables.add((AbstractVariable) container);
      }
    }
    // Secondly : check if every values of the impacted variable are going to be deleted.
    for (AbstractVariable impactedVariable : impactedVariables) {
      // Are every values of the impacted variables in the list of elements to delete.
      if (_elementsToDelete.containsAll(impactedVariable.getValues())) {
        if (impactedVariable instanceof Variable) {
          if (_elementsToDelete.contains(impactedVariable)) {
            // It's a classical variable and it is going to be deleted too -> don't bother to delete the values (they will be deleted with the variable).
            _elementsToDelete.removeAll(impactedVariable.getValues());
          } else {
            // Every values are going to be deleted but not the variable itself -> forbidden operation (a variable can't have no value).
            return false;
          }
        } else if (impactedVariable instanceof OverridingVariable) {
          // It's an overriding variable -> add it to the list of elements to delete but don't bother to delete values (they will be deleted with the variable).
          _elementsToDelete.add(impactedVariable);
          _elementsToDelete.removeAll(impactedVariable.getValues());
        }
      }
    }

    // Do prepare command.
    doPrepare();
    return super.prepare();
  }

  /**
   * Prepare preconditions that can be overridden by sub-hierarchy.
   * @return <code>true</code> if prepare should proceed, <code>false</code> to stop preparation right away.
   */
  protected boolean preparePreConditions() {
    // Delete command is disabled if one of the elements to delete :
    // - doesn't have an edition context,
    // - is external (i.e. not contained by edition context),
    // - is not modifiable in the edition context.
    for (Object elementToDelete : _elementsToDelete) {
      Context editionContext = getEditionContext(elementToDelete);
      if (null == editionContext || (elementToDelete instanceof ModelElement && DataUtil.isExternalElement((ModelElement) elementToDelete, editionContext))
          || (elementToDelete instanceof EObject && DataUtil.isUnmodifiable((EObject) elementToDelete, editionContext))) {
        return false;
      }
    }
    return true;
  }
}