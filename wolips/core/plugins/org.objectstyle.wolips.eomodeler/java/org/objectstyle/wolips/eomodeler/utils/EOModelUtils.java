package org.objectstyle.wolips.eomodeler.utils;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.objectstyle.wolips.eomodeler.model.EOArgument;
import org.objectstyle.wolips.eomodeler.model.EODatabaseConfig;
import org.objectstyle.wolips.eomodeler.model.EOEntity;
import org.objectstyle.wolips.eomodeler.model.EOModel;
import org.objectstyle.wolips.eomodeler.model.EOModelObject;
import org.objectstyle.wolips.eomodeler.model.EOModelVerificationFailure;
import org.objectstyle.wolips.eomodeler.model.EOStoredProcedure;
import org.objectstyle.wolips.eomodeler.model.IEOEntityRelative;

public class EOModelUtils {
	public static Set<EOModelVerificationFailure> getReferenceFailures(Object[] selectedObjects) {
		Set<EOModelVerificationFailure> referenceFailures = new HashSet<EOModelVerificationFailure>();
		for (int selectedObjectNum = 0; selectedObjectNum < selectedObjects.length; selectedObjectNum++) {
			Object selectedObject = selectedObjects[selectedObjectNum];
			if (selectedObject instanceof EOModelObject) {
				referenceFailures.addAll(((EOModelObject) selectedObject).getReferenceFailures());
			}
		}
		return referenceFailures;
	}

	public static IUndoContext getUndoContext(Object obj) {
		return new ObjectUndoContext(EOModelUtils.getRelatedModel(obj));
	}

	public static EOModelObject getRelated(Class<? extends EOModelObject> type, EOModelObject obj) {
		EOModelObject relatedObj;
		if (EOModel.class.isAssignableFrom(type)) {
			relatedObj = EOModelUtils.getRelatedModel(obj);
		} else if (EOEntity.class.isAssignableFrom(type)) {
			relatedObj = EOModelUtils.getRelatedEntity(obj);
		} else if (EOStoredProcedure.class.isAssignableFrom(type)) {
			if (obj instanceof EOStoredProcedure) {
				relatedObj = obj;
			}
			else if (obj instanceof EOArgument) {
				relatedObj = ((EOArgument)obj).getStoredProcedure();
			}
			else {
				relatedObj = null;
			}
		} else {
			relatedObj = null;
		}
		return relatedObj;
	}

	public static EOModel getRelatedModel(Object obj) {
		EOModel model = null;
		if (obj instanceof EOModel) {
			model = (EOModel) obj;
		} else if (obj instanceof IEOEntityRelative) {
			model = ((IEOEntityRelative) obj).getEntity().getModel();
		} else if (obj instanceof EOStoredProcedure) {
			model = ((EOStoredProcedure) obj).getModel();
		} else if (obj instanceof EOArgument) {
			model = ((EOArgument) obj).getStoredProcedure().getModel();
		} else if (obj instanceof EODatabaseConfig) {
			model = ((EODatabaseConfig) obj).getModel();
		} else if (obj instanceof Object[]) {
			Object[] objs = (Object[]) obj;
			if (objs.length > 0) {
				model = EOModelUtils.getRelatedModel(objs[0]);
			}
		}
		return model;
	}

	public static EOEntity getRelatedEntity(Object obj) {
		EOEntity entity = null;
		if (obj instanceof IEOEntityRelative) {
			entity = ((IEOEntityRelative) obj).getEntity();
		}
		return entity;
	}

	public static EOStoredProcedure getRelatedStoredProcedure(Object obj) {
		EOStoredProcedure storedProcedure = null;
		if (obj instanceof EOStoredProcedure) {
			storedProcedure = (EOStoredProcedure) obj;
		} else if (obj instanceof EOArgument) {
			storedProcedure = ((EOArgument) obj).getStoredProcedure();
		}
		return storedProcedure;
	}
}
