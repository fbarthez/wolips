/* ====================================================================
 * 
 * The ObjectStyle Group Software License, Version 1.0 
 *
 * Copyright (c) 2002 - 2006 The ObjectStyle Group 
 * and individual authors of the software.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:  
 *       "This product includes software developed by the 
 *        ObjectStyle Group (http://objectstyle.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "ObjectStyle Group" and "Cayenne" 
 *    must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written 
 *    permission, please contact andrus@objectstyle.org.
 *
 * 5. Products derived from this software may not be called "ObjectStyle"
 *    nor may "ObjectStyle" appear in their names without prior written
 *    permission of the ObjectStyle Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE OBJECTSTYLE GROUP OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the ObjectStyle Group.  For more
 * information on the ObjectStyle Group, please see
 * <http://objectstyle.org/>.
 *
 */

package org.objectstyle.wolips.datasets.resources;

import java.util.List;

import org.eclipse.core.resources.IResource;

/**
 * @author ulrich
 * @deprecated Use org.objectstyle.wolips.core.* instead.
 */
public interface IWOLipsResource {
	/**
	 * Comment for <code>WOCOMPONENT_BUNDLE</code>
	 */
	public static final int WOCOMPONENT_BUNDLE = 0;

	/**
	 * Comment for <code>WOCOMPONENT_WOD</code>
	 */
	public static final int WOCOMPONENT_WOD = 1;

	/**
	 * Comment for <code>WOCOMPONENT_HTML</code>
	 */
	public static final int WOCOMPONENT_HTML = 2;

	/**
	 * Comment for <code>WOCOMPONENT_WOO</code>
	 */
	public static final int WOCOMPONENT_WOO = 3;

	/**
	 * Comment for <code>WOCOMPONENT_API</code>
	 */
	public static final int WOCOMPONENT_API = 4;

	/**
	 * Comment for <code>EOMODEL</code>
	 */
	public static final int EOMODEL = 5;

	/**
	 * @return Returns the IResource;
	 */
	public abstract IResource getCorrespondingResource();

	/**
	 * @return Returns the type.
	 */
	public abstract int getType();

	/**
	 * @return Returns a List of related IWOLipsResource s.
	 */
	public abstract List getRelatedResources();

	/**
	 * Opens the resource in a Editor.
	 */
	public abstract void open();
}