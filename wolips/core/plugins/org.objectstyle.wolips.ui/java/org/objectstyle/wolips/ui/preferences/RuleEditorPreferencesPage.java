package org.objectstyle.wolips.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.objectstyle.wolips.preferences.Preferences;
import org.objectstyle.wolips.preferences.PreferencesMessages;

/**
 * @author fpeters
 */
public class RuleEditorPreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	/**
	 * Constructor
	 */
	public RuleEditorPreferencesPage() {
		super(GRID);
		setPreferenceStore(Preferences.getPreferenceStore());
		Preferences.setDefaults();
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	public void createFieldEditors() {
		addField(new BooleanFieldEditor(Preferences.PREF_RULEEDITOR_SAVE_SINGLE_LINES, PreferencesMessages.getString("Preferences.RuleEditorSaveSingleLines.Label"), getFieldEditorParent()));
		addField(new BooleanFieldEditor(Preferences.PREF_RULEEDITOR_SAVE_TXT_FILE, PreferencesMessages.getString("Preferences.RuleEditorSaveTxtFile.Label"), getFieldEditorParent()));
		addField(new BooleanFieldEditor(Preferences.PREF_RULEEDITOR_ADD_UUID, PreferencesMessages.getString("Preferences.RuleEditorAddUUID.Label"), getFieldEditorParent()));
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		return;
	}

	/**
	 * Method performOK.
	 * 
	 * @return boolean
	 */
	public boolean performOk() {
		return super.performOk();
	}
}