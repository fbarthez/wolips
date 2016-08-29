package org.objectstyle.wolips.ruleeditor.sorter;

import org.eclipse.jface.viewers.Viewer;
import org.objectstyle.wolips.ruleeditor.model.Rule;

public class TextSorter extends AbstractInvertableTableSorter {

	private final int columnIdx;

	public TextSorter(final int index) {
		columnIdx = index;
	}

	@Override
	public int compare(final Viewer viewer, final Object e1, final Object e2) {

		if (columnIdx == 0) {
			if (((Rule) e1).getLeftHandSide() == null || ((Rule) e2).getLeftHandSide() == null) {
				return 0;
			}
			return ((Rule) e1).getLeftHandSide().toString().compareTo(((Rule) e2).getLeftHandSide().toString());
		} else if (columnIdx == 1) {
			if (((Rule) e1).getRightHandSide().getKeyPath() == null || ((Rule) e2).getRightHandSide().getKeyPath() == null) {
				return 0;
			}
			return ((Rule) e1).getRightHandSide().getKeyPath().compareTo(((Rule) e2).getRightHandSide().getKeyPath());
		} else if (columnIdx == 2) {
			if (((Rule) e1).getRightHandSide().getValue() == null || ((Rule) e2).getRightHandSide().getValue() == null) {
				return 0;
			}
			return ((Rule) e1).getRightHandSide().getValue().compareTo(((Rule) e2).getRightHandSide().getValue());
		} else if (columnIdx == 3) {
			if (((Rule) e1).getRightHandSide().getAssignmentClassName() == null || ((Rule) e2).getRightHandSide().getAssignmentClassName() == null) {
				return 0;
			}
			return ((Rule) e1).getRightHandSide().getAssignmentClassName().compareTo(((Rule) e2).getRightHandSide().getAssignmentClassName());
		} else {
			if (((Rule) e1).getAuthor() == null || ((Rule) e2).getAuthor() == null) {
				return 0;
			}
			return Integer.valueOf(((Rule) e1).getAuthor()).compareTo(Integer.valueOf(((Rule) e2).getAuthor()));
		}

	}

}
