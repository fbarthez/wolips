/*
 * ====================================================================
 * 
 * The ObjectStyle Group Software License, Version 1.0
 * 
 * Copyright (c) 2005 The ObjectStyle Group and individual authors of the
 * software. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. The end-user documentation included with the redistribution, if any, must
 * include the following acknowlegement: "This product includes software
 * developed by the ObjectStyle Group (http://objectstyle.org/)." Alternately,
 * this acknowlegement may appear in the software itself, if and wherever such
 * third-party acknowlegements normally appear.
 * 
 * 4. The names "ObjectStyle Group" and "Cayenne" must not be used to endorse or
 * promote products derived from this software without prior written permission.
 * For written permission, please contact andrus@objectstyle.org.
 * 
 * 5. Products derived from this software may not be called "ObjectStyle" nor
 * may "ObjectStyle" appear in their names without prior written permission of
 * the ObjectStyle Group.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * OBJECTSTYLE GROUP OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 * 
 * This software consists of voluntary contributions made by many individuals on
 * behalf of the ObjectStyle Group. For more information on the ObjectStyle
 * Group, please see <http://objectstyle.org/>.
 *  
 */
package org.objectstyle.wolips.ruleeditor.editor;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.objectstyle.wolips.ruleeditor.actions.CopyRuleAction;
import org.objectstyle.wolips.ruleeditor.actions.CutRuleAction;
import org.objectstyle.wolips.ruleeditor.actions.DeleteRuleAction;
import org.objectstyle.wolips.ruleeditor.actions.PasteTableRuleAction;
import org.objectstyle.wolips.ruleeditor.listener.D2WModelChangeListener;
import org.objectstyle.wolips.ruleeditor.model.D2WModel;

/**
 * @author omar
 * @author uli
 * @author <a href="mailto:frederico@moleque.com.br">Frederico Lellis</a>
 * @author <a href="mailto:georg@moleque.com.br">Georg von Bülow</a>
 */
public class RuleEditorPart extends EditorPart {

	private IFileEditorInput fileEditorInput;

	private boolean isDirty;

	private D2WModel model;

	private RuleEditor ruleEditor;

    private Clipboard clipboard;

	public RuleEditorPart() {
		super();
	}

	@Override
	public void createPartControl(final Composite parent) {
		ruleEditor = new RuleEditor();

		File file = new File(fileEditorInput.getFile().getLocation().toOSString());

		model = new D2WModel(file);

		model.addPropertyChangeListener(new D2WModelChangeListener(this));

		ruleEditor.setD2WModel(model);
		ruleEditor.createContents(parent);
		
		// add cut and paste support
		clipboard = new Clipboard(getSite().getShell().getDisplay());
		IActionBars bars = getEditorSite().getActionBars();
		bars.setGlobalActionHandler(IWorkbenchActionConstants.CUT, new CutRuleAction(ruleEditor.tableViewer(), clipboard));
		bars.setGlobalActionHandler(IWorkbenchActionConstants.COPY, new CopyRuleAction(ruleEditor.tableViewer(), clipboard));
		bars.setGlobalActionHandler(IWorkbenchActionConstants.PASTE, new PasteTableRuleAction(ruleEditor.tableViewer(), clipboard));
		bars.setGlobalActionHandler(IWorkbenchActionConstants.DELETE, new DeleteRuleAction(ruleEditor.tableViewer()));
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		model.saveChanges();
	}

	@Override
	public void doSaveAs() {
		throw new IllegalStateException();
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		fileEditorInput = (IFileEditorInput) input;
		super.setSite(site);
		super.setInput(input);
	}

	@Override
	public boolean isDirty() {
		return isDirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * @param isDirty
	 *            The isDirty to set.
	 */
	public void setDirty(final boolean isDirty) {
		this.isDirty = isDirty;
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public void setFocus() {
		ruleEditor.setFocus();
	}
}
