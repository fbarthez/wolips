package org.objectstyle.wolips.ruleeditor.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.objectstyle.wolips.ruleeditor.model.Rule;

/**
 * Class for cutting a selection of rules from a view,
 * and placing them on the clipboard.
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
      IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
      Rule[] rules = (Rule[])sel.toList().toArray(new Rule[sel.size()]);
      clipboard.setContents(
         new Object[] { rules },
         new Transfer[] { TextTransfer.getInstance()});
      // TODO is this necessary?
//      for (int i = 0; i < rules.length; i++) {
//         rules[i].setParent(null);
//      }
      viewer.refresh();
   }
}