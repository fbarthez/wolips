package org.objectstyle.wolips.ruleeditor.actions;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.objectstyle.wolips.ruleeditor.model.D2WModel;
import org.objectstyle.wolips.ruleeditor.model.Rule;

/**
 * Class for cutting a selection of rules from a view, and placing them on the
 * clipboard.
 */
public class CutRuleAction extends Action {
	protected Clipboard clipboard;

	protected StructuredViewer viewer;

	public CutRuleAction(StructuredViewer viewer, Clipboard clipboard) {
		super("Cut");
		this.viewer = viewer;
		this.clipboard = clipboard;
	}

	public void run() {
		IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
		@SuppressWarnings("unchecked")
		ArrayList<Rule> rules = new ArrayList<Rule>(sel.toList());
		StringBuilder stringRepresentation = new StringBuilder();
		for (Iterator<Rule> i = rules.iterator(); i.hasNext();) {
			Rule aRule = i.next();
			stringRepresentation.append(aRule.toString());
			if (i.hasNext()) {
				stringRepresentation.append(",\n");
			}
			D2WModel model = (D2WModel) viewer.getInput();
			model.removeRule(aRule);
		}
		Object[] data = new Object[] { stringRepresentation.toString() };
		clipboard.setContents(data, new Transfer[] { TextTransfer.getInstance() });
		viewer.refresh();
	}
}