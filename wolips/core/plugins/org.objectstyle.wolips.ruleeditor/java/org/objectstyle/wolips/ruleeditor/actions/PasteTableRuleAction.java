package org.objectstyle.wolips.ruleeditor.actions;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;

/**
 * Action for pasting a selection of rules from the clipboard
 * into a table viewer.
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
      if (StringUtils.isEmpty(stringRepresentation))
         return;
      
    	  System.out.println("PasteTableRuleAction.run: " + stringRepresentation);
      // TODO implement
//      Rule parent = (Rule)viewer.getInput();
//      for (int i = 0; i < rules.length; i++) {
//         //get the flat list of all rules in this tree
//         Rule[] flatList = rules[i].flatten();
//         for (int j = 0; j < flatList.length; j++) {
//            flatList[j].setParent(parent);
//         }
//      }
      viewer.refresh();
   }
}
