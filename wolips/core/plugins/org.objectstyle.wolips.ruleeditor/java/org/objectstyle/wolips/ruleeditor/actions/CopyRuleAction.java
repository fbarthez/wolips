package org.objectstyle.wolips.ruleeditor.actions;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.objectstyle.wolips.ruleeditor.model.Rule;

/**
 * Action for copying a selection of rules to the clipboard
 */
public class CopyRuleAction extends Action {
	protected Clipboard clipboard;

	protected StructuredViewer viewer;

	public CopyRuleAction(StructuredViewer viewer, Clipboard clipboard) {
		super("Copy");
		this.viewer = viewer;
		this.clipboard = clipboard;
	}

	public void run() {
		IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
		@SuppressWarnings("unchecked")
		ArrayList<Rule> rules = new ArrayList<Rule>(sel.toList());
		StringBuilder stringRepresentation = new StringBuilder();
		for (Iterator i = rules.iterator(); i.hasNext();) {
			Rule aRule = (Rule) i.next();
			stringRepresentation.append(aRule.toString());
			if (i.hasNext()) {
				stringRepresentation.append(",\n");
			}
		}
		Object[] data = new Object[] { stringRepresentation.toString() };
		clipboard.setContents(data, new Transfer[] { TextTransfer.getInstance() });
	}
}