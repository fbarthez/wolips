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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.objectstyle.wolips.datasets.DataSetsPlugin;

/**
 * The main plugin class to be used in the desktop.
 */
public class TemplateEnginePlugin extends AbstractUIPlugin {
	//The shared instance.
	private static TemplateEnginePlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
	public static final String WOApplicationProject = "WOApplicationProject";
	public static final String D2W_ApplicationProject = "D2W_ApplicationProject";
	public static final String WOFrameworkProject = "WOFrameworkProject";
	public static final String JarProject = "JarProject";
	public static final String WOComponent = "WOComponent";
	public static final String EOModel = "EOModel";
	
	/**
	 * The constructor.
	 * @param descriptor
	 */
	public TemplateEnginePlugin(IPluginDescriptor descriptor) {
		super(descriptor);
		plugin = this;
		try {
			resourceBundle= ResourceBundle.getBundle("org.objectstyle.wolips.templateengine.TemplateenginePluginResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	/**
	 * Returns the shared instance.
	 * @return
	 */
	public static TemplateEnginePlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the workspace instance.
	 * @return
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 * @param key
	 * @return
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle= TemplateEnginePlugin.getDefault().getResourceBundle();
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 * @return
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	/**
	 * Method baseURL.
	 * @return URL
	 */
	public static URL baseURL() {
		return TemplateEnginePlugin.getDefault().getDescriptor().getInstallURL();
	}

	private static IPath templatesPath() {
		URL url = null;
		try {
			url = Platform.resolve(TemplateEnginePlugin.baseURL());
		} catch (IOException e) {
			e.printStackTrace();
			url = null;
		}
		if(url == null)
			return null;
		IPath path = new Path(url.getPath());
		path = path.append("templates");
		return path;
	}
	
	private static IPath userTemplatesPath() {
		IPath path = DataSetsPlugin.UserHomeLibrayWOLipsPath();
		path = path.append("Templates");
		return path;
	}
	/**
	 * @return The roots.
	 */
	protected static TemplateFolderRoot[] getTemplateFolderRoots() {
		TemplateFolderRoot[] templateFolderRoots = new TemplateFolderRoot[2];
		templateFolderRoots[0] = new TemplateFolderRoot(TemplateEnginePlugin.templatesPath());
		templateFolderRoots[1] = new TemplateFolderRoot(TemplateEnginePlugin.userTemplatesPath());
		return templateFolderRoots;
	}

	private static TemplateFolder[] getTemplateFolder(TemplateFolderRoot templateFolderRoot) {
		ArrayList templateFolderList = new ArrayList();
		IPath root = templateFolderRoot.getPath();
		File file = new File(root.toOSString());
		if (file.exists() && file.isDirectory()) {
			File[] files = file.listFiles();
			for (int j = 0; j < files.length; j++) {
				File fileInRootFolder = files[j];
				if(fileInRootFolder.isDirectory()) {
					IPath path = new Path(fileInRootFolder.getAbsolutePath());
					TemplateFolder templateFolder = new TemplateFolder(path);
					templateFolderList.add(templateFolder);
				}
			}
		}
		return (TemplateFolder[])templateFolderList.toArray(new TemplateFolder[templateFolderList.size()]);
	}
	/**
	 * @param templateFolderRoots
	 * @param string
	 * @return The of templates folder array for the given type.
	 */
	protected static TemplateFolder[] getTemplateFolder(TemplateFolderRoot[] templateFolderRoots, String type) {
		ArrayList templateFolderList = new ArrayList();
		for(int i = 0; i < templateFolderRoots.length; i++) {
			TemplateFolderRoot templateFolderRoot = templateFolderRoots[i];
			TemplateFolder[] templateFolders = TemplateEnginePlugin.getTemplateFolder(templateFolderRoot);
			for (int j = 0; j < templateFolders.length; j++) {
				TemplateFolder templateFolder = templateFolders[j];
				if(templateFolder.isOfType(type))
					templateFolderList.add(templateFolder);	
			}
		}
		return (TemplateFolder[])templateFolderList.toArray(new TemplateFolder[templateFolderList.size()]);
	}
	
	/**
	 * @param string
	 * @return The of templates folder array for the given type.
	 */
	public static TemplateFolder[] getTemplateFolder(String type) {
		return TemplateEnginePlugin.getTemplateFolder(TemplateEnginePlugin.getTemplateFolderRoots(), type);
	}
	
}
