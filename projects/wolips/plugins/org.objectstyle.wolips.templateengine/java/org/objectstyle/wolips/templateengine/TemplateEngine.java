/* ====================================================================
 * 
 * The ObjectStyle Group Software License, Version 1.0 
 *
 * Copyright (c) 2002 The ObjectStyle Group 
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
package org.objectstyle.wolips.templateengine;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
/**
 * @author ulrich
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TemplateEngine implements IRunnableWithProgress {
	private VelocityContext context = null;
	private ArrayList templates = null;
	private VelocityEngine velocityEngine = null;
	private WOLipsContext wolipsContext;
	public void init() throws Exception {
		/*
		 * create a new instance of the engine
		 */
		velocityEngine = new VelocityEngine();
		/*
		 * configure the engine. In this case, we are using ourselves as a
		 * logger (see logging examples..)
		 */
		velocityEngine
				.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, this);
		/*
		 * initialize the engine
		 */
		String userHomeWOLipsPath = System.getProperty("user.home")
				+ File.separator + "Library" + File.separator + "WOLips";
		URL url = null;
		url = Platform.resolve(TemplateEnginePlugin.baseURL());
		String templatePaths = userHomeWOLipsPath + ", ";
		Path path = new Path(url.getPath());
		templatePaths = templatePaths + path.append("templates").toOSString();
		velocityEngine.setProperty("file.resource.loader.path", templatePaths);
		velocityEngine.init();
		context = new VelocityContext();
		templates = new ArrayList();
		wolipsContext = new WOLipsContext();
		this.setPropertyForKey(wolipsContext, WOLipsContext.Key);
		SAXBuilder builder;
		Document myContext = null;
		try {
			builder = new SAXBuilder();
			myContext = builder.build(userHomeWOLipsPath + File.separator
					+ "MyContext.xml");
		} catch (Exception ee) {
			//We can ignore this exception, it`s thrown if the xml document is
			// not found.
			//Per default there is no such file
			builder = null;
			myContext = null;
		}
		if (myContext != null) {
			this.setPropertyForKey(myContext, "MyContext");
		}
	}
	public void addTemplate(TemplateDefinition template) {
		templates.add(template);
	}
	public void addTemplates(TemplateDefinition[] templateDefinitions) {
		if (templates == null)
			return;
		for (int i = 0; i < templateDefinitions.length; i++) {
			TemplateDefinition templateDefinition = templateDefinitions[i];
			templates.add(templateDefinition);
		}
	}
	public void setPropertyForKey(Object property, String key) {
		context.put(key, property);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void run(IProgressMonitor monitor) throws InvocationTargetException {
		try {
			for (int i = 0; i < templates.size(); i++) {
				TemplateDefinition templateDefinition = (TemplateDefinition) templates
						.get(i);
				this.run(templateDefinition);
			}
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		}
	}
	
	private void run(TemplateDefinition templateDefinition) {
		FileWriter writer = null;
		try {
			/*
			 * make a writer, and merge the template 'against' the context
			 */
			String templateName = templateDefinition.getTemplateName();
			Template template = velocityEngine.getTemplate(templateName);
			writer = new FileWriter(templateDefinition.getDestinationPath());
			template.merge(context, writer);
		} catch (Exception e) {
			System.out.println("Exception : " + e);
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (Exception ee) {
					System.out.println("Exception : " + ee);
				}
			}
		}
	}
	/**
	 * @return Returns the wolipsContext.
	 */
	public WOLipsContext getWolipsContext() {
		return wolipsContext;
	}
}
