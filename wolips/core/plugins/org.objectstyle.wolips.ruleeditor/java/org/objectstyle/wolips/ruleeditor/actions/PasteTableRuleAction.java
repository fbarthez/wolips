package org.objectstyle.wolips.ruleeditor.actions;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.objectstyle.wolips.ruleeditor.model.D2WModel;
import org.objectstyle.wolips.ruleeditor.model.Rule;

/**
 * Action for pasting a selection of rules from the clipboard into a table
 * viewer.
 */
public class PasteTableRuleAction extends Action {
	protected Clipboard clipboard;

	protected StructuredViewer viewer;

	public PasteTableRuleAction(StructuredViewer viewer, Clipboard clipboard) {
		super("Paste");
		this.viewer = viewer;
		this.clipboard = clipboard;
	}

	public void run() {
		String stringRepresentation = (String) clipboard.getContents(TextTransfer.getInstance());
		if (!StringUtils.isEmpty(stringRepresentation)) {
			D2WModel model = (D2WModel) viewer.getInput();
			String lines[] = stringRepresentation.split("\\r?\\n");
			for (String aLine : lines) {
				Rule pastedRule = Rule.fromString(aLine);
				System.out.println("PasteTableRuleAction.run: " + pastedRule);
				if (pastedRule != null) {
					model.addRule(pastedRule);
				}
			}
			viewer.refresh();
		}
	}
}
