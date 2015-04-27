/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper.ValidationException;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.RootElement;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.ui.wizard.Messages;

/**
 * @author s0011584
 */
public abstract class AbstractNewContextPage extends ImportContextsPage {
	
	/**
	 * The context name.
	 */
	protected Text _nameText;
	/**
	 * The name text field decoration.
	 */
	protected ControlDecoration _nameTextDecoration;
	/**
	 * The selected parent.
	 */
	protected RootContextsProject _selectedParent;
	
	/**
	 * Set of available contexts within the workspace
	 */
	protected Set<String> _availableContextsSet = null;
	
	/**
	 * @param pageId_p
	 */
	public AbstractNewContextPage(final String pageId_p) {
		super(pageId_p);
	}
	
	/**
	 * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.ImportContextsPage#createImportAsCopyPart(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.forms.widgets.FormToolkit)
	 */
	@Override
	protected void createImportAsCopyPart(final Composite parent_p, final FormToolkit toolkit_p) {
		// Do nothing here, for this does not make sense.
	}
	
	protected void createNameSelection(final Composite parent_p, final String label_p) {
		final Label label = new Label(parent_p, SWT.NONE);
		label.setText(label_p);
		this._nameText = new Text(parent_p, SWT.BORDER | SWT.SINGLE);
		this._nameTextDecoration = this.createDecoration(this._nameText, null, FieldDecorationRegistry.DEC_ERROR);
		// Set decoration not visible when starting wizard.
		this._nameTextDecoration.hide();
		final GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = 10;
		this._nameText.setLayoutData(data);
		
		// Check context value only when the user has stopped typing for 500 ms
		final ISWTObservableValue observeProjectText = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(this._nameText, SWT.Modify));
		observeProjectText.addValueChangeListener(new IValueChangeListener() {
			
			/**
			 * @see org.eclipse.core.databinding.observable.value.IValueChangeListener#handleValueChange(org.eclipse.core.databinding.observable.value.ValueChangeEvent)
			 */
			@Override
			public void handleValueChange(final ValueChangeEvent event_p) {
				// Display a busy indicator when the checks takes a while
				BusyIndicator.showWhile(AbstractNewContextPage.this._nameText.getDisplay(), new Runnable() {
					
					@Override
					public void run() {
						AbstractNewContextPage.this.setPageComplete(AbstractNewContextPage.this.isPageComplete());
					}
				});
			}
		});
	}
	
	/**
	 * @param composite_p
	 */
	protected Composite createParentSelection(final Composite parent_p) {
		final Group group = new Group(parent_p, SWT.NONE);
		group.setText(Messages.NewContextWizard_2);
		final GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		group.setLayout(layout);
		final GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		group.setLayoutData(data);
		this.createParentSelectionButton(group, layout.numColumns);
		return group;
	}
	
	/**
	 * @param group_p
	 */
	protected abstract void createParentSelectionButton(Composite parent_p, int numColumns);
	
	/**
	 * @return the newContextName
	 */
	public String getNewContextName() {
		return this._nameText.getText();
	}
	
	/**
	 * @return the selectedParent
	 */
	public RootContextsProject getSelectedParent() {
		return this._selectedParent;
	}
	
	/**
	 * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionPage#handleCheckEvent(org.eclipse.jface.viewers.CheckStateChangedEvent)
	 */
	@Override
	protected void handleCheckEvent(final CheckStateChangedEvent event_p) {
		final Object element = event_p.getElement();
		final boolean checked = event_p.getChecked();
		// Is element a directory or a context ?
		if (element instanceof DirectoryTreeNode) {
			if (checked) {
				// Directory selection is forbidden.
				this._viewer.setGrayChecked(element, false);
			}
			else {
				// Directory unselection unselects all subtree.
				this._viewer.setSubtreeChecked(element, false);
			}
		}
		else if (element instanceof RootContextTreeNode) {
			if (checked) {
				// Make sure one context is selected at utmost.
				this._viewer.setCheckedElements(new Object[] { element });
			}
			// If a child is selected, set parent directory gray checked.
			final Object parentElement = ((RootContextTreeNode) element).getParent();
			this._viewer.setGrayChecked(parentElement, checked);
		}
		// Update _selectedParent.
		final List<RootContextsProject> checkedProjects = this.getCheckedProjects();
		if (null == checkedProjects || checkedProjects.isEmpty()) {
			this._selectedParent = null;
		}
		else {
			this._selectedParent = checkedProjects.get(0);
		}
		this.postHandleCheckEvent(event_p);
		this.setPageComplete(this.isPageComplete());
	}
	
	/**
	 * Is chosen context name a valid one ?
	 * @return
	 */
	public boolean isContextNameValid() {
		final String contextName = this.getNewContextName();
		if (null == contextName || contextName.isEmpty()) {
			this.setMessage(Messages.NewContextWizard_Message_PromptForContextNameMessage, IMessageProvider.NONE);
			this._nameTextDecoration.hide();
			return false;
		}
		
		try {
			ProjectHelper.validateProjectName(contextName);
		}
		catch (final ValidationException ve_p) {
			this.setMessage(ve_p.getMessage(), IMessageProvider.ERROR);
			this._nameTextDecoration.setDescriptionText(ve_p.getMessage());
			this._nameTextDecoration.show();
			return false;
		}
		
		// Build a set of available contexts in the workspace that will be
		// optimize furthers checks against the context name
		if (this._availableContextsSet == null) {
			this._availableContextsSet = new HashSet<String>();
			final RootElement rootElement = ModelHandlerActivator.getDefault().getDataHandler().getAllContextsInWorkspace();
			final List<Context> contextsList = rootElement.getContexts();
			for (final Context context : contextsList) {
				this._availableContextsSet.add(context.getName());
			}
		}
		// Is there a context with the same name already loaded ?
		if (this._availableContextsSet.contains(contextName)) {
			this.setMessage(Messages.NewContextWizard_contextExistsMessage, IMessageProvider.ERROR);
			this._nameTextDecoration.setDescriptionText(Messages.NewContextWizard_contextExistsMessage);
			this._nameTextDecoration.show();
			return false;
		}
		this._nameTextDecoration.hide();
		return true;
	}
	
	/**
	 * Called when the root parent is chosen.
	 * @param event_p
	 */
	protected void postHandleCheckEvent(final CheckStateChangedEvent event_p) {
		// Do nothing by default.
	}
}