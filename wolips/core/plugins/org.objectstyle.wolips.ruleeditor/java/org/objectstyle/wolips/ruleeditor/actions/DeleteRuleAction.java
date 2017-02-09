package org.objectstyle.wolips.ruleeditor.actions;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.objectstyle.wolips.ruleeditor.model.D2WModel;
import org.objectstyle.wolips.ruleeditor.model.Rule;

/**
 * Class for deleting a selection of rules from a view.
 */
public class DeleteRuleAction extends Action {

	protected StructuredViewer viewer;

	public DeleteRuleAction(StructuredViewer viewer) {
		super("Delete");
		this.viewer = viewer;
	}

	public void run() {
		IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
		@SuppressWarnings("unchecked")
		ArrayList<Rule> rules = new ArrayList<Rule>(sel.toList());
		for (Iterator<Rule> i = rules.iterator(); i.hasNext();) {
			Rule aRule = i.next();
			D2WModel model = (D2WModel) viewer.getInput();
			model.removeRule(aRule);
		}
		viewer.refresh();
	}
}