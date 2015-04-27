/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.command;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.AbortExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.PlatformUI;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.command.ModelModifyingCommand;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain.ClipboardElement;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;

/**
 * Paste from clipboard command.<br>
 * This command is undoable.<br>
 * It does not flush the clipboard. Paste can be invoked several times with the same clipboard contents.
 * @author t0076261
 */
public class PasteFromClipboardCommand extends ModelModifyingCommand {
  /**
   * Used to create new statutes.
   */
  public static final String PLUGIN_ID = CommonActivator.getInstance().getPluginId();
  /**
   * Edition context.<br>
   * That is the context which holds the responsibility at the time of selection.
   */
  protected Context _editionContext;
  /**
   * Selected elements.
   */
  protected Collection<?> _selectedElements;
  /**
   * Extracted single selection.
   */
  protected ModelElement _singleSelection;

  /**
   * Constructor.
   * @param label_p
   * @param editingDomain_p
   */
  public PasteFromClipboardCommand(Collection<?> selectedElements_p, Context context_p) {
    super(Messages.PasteFromClipboardCommand_Label, ModelHandlerActivator.getDefault().getEditingDomain());
    setDescription(Messages.PasteFromClipboardCommand_Description);
    _selectedElements = selectedElements_p;
    _editionContext = context_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.command.ModelModifyingCommand#doExecute()
   */
  @Override
  protected void doExecute() {
    // Get editing domain.
    ContextsEditingDomain editingDomain = getEditingDomain();
    try {
      ClipboardElement clipboardElement = editingDomain.getClipboardContent();
      if (null == clipboardElement) {
        return;
      }
      // Preconditions.
      // Can't cut & paste an element to its current location (same source and destination context, same source and destination container).
      if (CutToClipboardCommand.class.equals(clipboardElement._commandClass) && _editionContext.equals(clipboardElement._context)) {
        // Collect erroneous elements.
        List<EObject> erroneousElements = new ArrayList<EObject>();
        for (EObject modelElement : clipboardElement._elements) {
          if (_singleSelection.equals(ModelUtil.getLogicalContainer(modelElement, clipboardElement._context))) {
            erroneousElements.add(modelElement);
          }
        }
        // Display an error dialog if needed.
        if (!erroneousElements.isEmpty()) {
          MultiStatus multiStatus = new MultiStatus(PLUGIN_ID, 0, Messages.PasteFromClipboardCommand_ErrorMessage_CantCutAndPasteInTheSameDestination, null);
          multiStatus.add(new Status(IStatus.ERROR, PLUGIN_ID, Messages.PasteFromClipboardCommand_ErrorMessage_CantCutAndPasteInTheSameDestination_Details));
          for (EObject erroneousElement : erroneousElements) {
            multiStatus.add(new Status(IStatus.ERROR, PLUGIN_ID, generateTextualDescription(erroneousElement)));
          }
          ErrorDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), Messages.PasteFromClipboardCommand_Error_Title, null, multiStatus);
          // Stop paste command.
          throw new AbortExecutionException();
        }
      }
      // Can't cut/copy & paste a category in itself (paste a category in itself or in one of its ancestors, in the same context).
      if (_editionContext.equals(clipboardElement._context)) {
        for (EObject modelElement : clipboardElement._elements) {
          if (modelElement instanceof Category && ModelUtil.isLogicalAncestor(modelElement, _singleSelection, _editionContext)) {
            IStatus status =
                new Status(IStatus.ERROR, PLUGIN_ID, 0, MessageFormat.format(Messages.PasteFromClipboardCommand_ErrorMessage_CantPasteCategoryInItself,
                    ((Category) modelElement).getName()), null);
            ErrorDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), Messages.PasteFromClipboardCommand_Error_Title, null, status);
            // Stop paste command.
            throw new AbortExecutionException();
          }
        }
      }
      // Past'em all !
      for (EObject modelElement : clipboardElement._elements) {
        EObject elementToPaste = modelElement;
        // Clipboard element is designing a copy.
        // If it's a Copy action -> make a fresh new copy before proceeding,
        // If it's a Cut action -> keep the given element and put it in its new location.
        if (CopyToClipboardCommand.class.equals(clipboardElement._commandClass)) {
          elementToPaste = DataUtil.makeFlatCopy(modelElement, clipboardElement._context, true);
        }
        // When pasting an element -> rename it with "Copy Of" if the name is already used amongst its future siblings.
        if (modelElement instanceof NamedElement) {
          Collection<? extends NamedElement> futureSiblings = getFutureSiblings((NamedElement) modelElement, _singleSelection);
          if (null != futureSiblings) {
            ((NamedElement) elementToPaste).setName(generateCopyOfName((NamedElement) elementToPaste, futureSiblings));
          }
        }
        // There is no easy way to know impacted features.
        // So hard-code elements and relationships.
        if (elementToPaste instanceof Variable) {
          Variable copiedVariable = (Variable) elementToPaste;
          // Paste variable to category.
          if (_singleSelection instanceof Category) {
            Category selectedCategory = (Category) _singleSelection;
            Context categoryContext = ModelUtil.getContext(selectedCategory);
            if (categoryContext != _editionContext) {
              // Copying a variable to a foreign category.
              // Reference category from variable.
              copiedVariable.setSuperCategory(selectedCategory);
              // Add variable to context then.
              _editionContext.getSuperCategoryVariables().add(copiedVariable);
              // WARNING :
              // This should be kept in this order.
              // If the super category is set after the variable is added to the context, then the whole
              // add event is lost, and the UI won't update accordingly.
            } else {
              // Erase potential pending link.
              copiedVariable.setSuperCategory(null);
              // Add variable to category directly.
              selectedCategory.getVariables().add(copiedVariable);
            }
          }
        } else if (elementToPaste instanceof Category) {
          Category categoryToPaste = (Category) elementToPaste;
          // Paste category to category.
          if (_singleSelection instanceof Category) {
            Category destinationCategory = (Category) _singleSelection;
            Context contextOwnerOfDestinationCategory = ModelUtil.getContext(destinationCategory);
            if (contextOwnerOfDestinationCategory != _editionContext) {
              // Destination category belongs to another context -> category to paste becomes a contribution.
              categoryToPaste.setSuperCategory(destinationCategory);
              // Add category directly to context then.
              _editionContext.getSuperCategoryCategories().add(categoryToPaste);
            } else {
              // Destination category belongs to destination context : remove super category (it is set only if category to paste was
              // contributed before paste action). A category can't be cut & paste in itself, this case is filtered by a precondition.
              categoryToPaste.setSuperCategory(null);
              destinationCategory.getCategories().add(categoryToPaste);
            }
            // Paste category to context.
          } else if (_singleSelection instanceof Context) {
            // This category is pasted to a context, remove super category (it is set only if category to paste was contributed before paste action).
            categoryToPaste.setSuperCategory(null);
            ((Context) _singleSelection).getCategories().add(categoryToPaste);
          }
        }
        // Remove element to paste if it was cut.
        if (CutToClipboardCommand.class.equals(clipboardElement._commandClass)) {
          editingDomain.setClipboardContent(null);
        }
      }
    } finally {
      // Force update for coupled commands/actions.
      ICommandUpdater commandUpdater = editingDomain.getCommandUpdater();
      if (null != commandUpdater) {
        commandUpdater.forceUpdate();
      }
    }
  }

  /**
   * Generate a name for elementToRename prefixing it with Copy Of, Copy (2) Of... if it is already used.
   * @param elementToRename_p
   * @param siblings_p
   * @return the new name of the element to rename.
   */
  protected String generateCopyOfName(NamedElement elementToRename_p, Collection<? extends NamedElement> siblings_p) {
    // Generate used name collection.
    Set<String> usedNamesSet = new HashSet<String>();
    for (NamedElement sibling : siblings_p) {
      usedNamesSet.add(sibling.getName());
    }
    // If name is not already used, keep it.
    String elementOriginalName = elementToRename_p.getName();
    if (!usedNamesSet.contains(elementOriginalName)) {
      return elementOriginalName;
    }
    // Create Copy Of.
    String elementNewName = MessageFormat.format(Messages.PasteFromClipboardCommand_First_CopyOf, elementOriginalName);
    int copyOfCounter = 2;
    // Create Copy Of (2), (3), ...
    while (usedNamesSet.contains(elementNewName)) {
      elementNewName = MessageFormat.format(Messages.PasteFromClipboardCommand_Multiple_CopyOf, new Integer(copyOfCounter), elementOriginalName);
      copyOfCounter++;
    }
    return elementNewName;
  }

  /**
   * Generate a textual description for the given element.
   * @param modelElement_p
   * @return
   */
  protected String generateTextualDescription(EObject modelElement_p) {
    if (modelElement_p instanceof NamedElement) {
      return modelElement_p.eClass().getName() + " " + ((NamedElement) modelElement_p).getName(); //$NON-NLS-1$
    } else if (modelElement_p instanceof ModelElement) {
      return modelElement_p.eClass().getName() + " " + ((ModelElement) modelElement_p).getId(); //$NON-NLS-1$
    } else {
      return modelElement_p.toString();
    }
  }

  /**
   * Get editing domain as {@link ContextsEditingDomain}.
   * @return
   */
  protected ContextsEditingDomain getEditingDomain() {
    return (ContextsEditingDomain) _editingDomain;
  }

  /**
   * Get future siblings of element to paste in edition context.<br>
   * - If pasting a variable to a category -> gives variables of destination category,<br>
   * - If pasting a category to a category -> gives categories of destination category,<br>
   * - If pasting a category to a context -> gives categories of destination context.
   * @param elementToPaste_p
   * @param destination_p
   * @return
   */
  protected Collection<? extends NamedElement> getFutureSiblings(ModelElement elementToPaste_p, ModelElement destination_p) {
    if (elementToPaste_p instanceof Variable && destination_p instanceof Category) {
      return DataUtil.getVariables((Category) destination_p, _editionContext);
    } else if (elementToPaste_p instanceof Category) {
      if (destination_p instanceof Category) {
        return DataUtil.getCategories((Category) destination_p, _editionContext);
      } else if (destination_p instanceof Context) {
        return ModelUtil.getCategories((Context) destination_p);
      }
    }
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.command.ChangeDescriptionCommand#prepare()
   */
  @Override
  protected boolean prepare() {
    // Make sure there is something to paste.
    Collection<Object> clipboard = getEditingDomain().getClipboard();
    if ((null == clipboard) || (clipboard.isEmpty())) {
      return false;
    }
    // Make sure there is only one element selected.
    if (1 != _selectedElements.size()) {
      return false;
    }
    // No edition context is set, paste is impossible.
    if (null == _editionContext) {
      return false;
    }
    // Extract element.
    // Make sure this is a ModelElement and that it is modifiable.
    Object element = _selectedElements.iterator().next();
    boolean result = ContextsPackage.Literals.MODEL_ELEMENT.isInstance(element) && !DataUtil.isUnmodifiable(ModelElement.class.cast(element), _editionContext);
    // Disable action when trying to paste variables (AbstractVariable) directly to a context.
    if (result && ContextsPackage.Literals.CONTEXT.isInstance(element)) {
      ClipboardElement clipboardElement = getEditingDomain().getClipboardContent();
      for (EObject modelElement : clipboardElement._elements) {
        if (ContextsPackage.Literals.ABSTRACT_VARIABLE.isInstance(modelElement)) {
          result = false;
          break;
        }
      }
    }
    if (!result) {
      return false;
    }
    _singleSelection = ModelElement.class.cast(element);
    return super.prepare();
  }
}