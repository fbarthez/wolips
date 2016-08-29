/*
 * ====================================================================
 * 
 * The ObjectStyle Group Software License, Version 1.0
 * 
 * Copyright (c) 2005 The ObjectStyle Group and individual authors of the
 * software. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. The end-user documentation included with the redistribution, if any, must
 * include the following acknowlegement: "This product includes software
 * developed by the ObjectStyle Group (http://objectstyle.org/)." Alternately,
 * this acknowlegement may appear in the software itself, if and wherever such
 * third-party acknowlegements normally appear.
 * 
 * 4. The names "ObjectStyle Group" and "Cayenne" must not be used to endorse or
 * promote products derived from this software without prior written permission.
 * For written permission, please contact andrus@objectstyle.org.
 * 
 * 5. Products derived from this software may not be called "ObjectStyle" nor
 * may "ObjectStyle" appear in their names without prior written permission of
 * the ObjectStyle Group.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * OBJECTSTYLE GROUP OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 * 
 * This software consists of voluntary contributions made by many individuals on
 * behalf of the ObjectStyle Group. For more information on the ObjectStyle
 * Group, please see <http://objectstyle.org/>.
 *  
 */
package org.objectstyle.wolips.ruleeditor.model;

import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.objectstyle.wolips.preferences.Preferences;

/**
 * @author uli
 * @author <a href="mailto:hprange@moleque.com.br">Henrique Prange</a>
 */
public class Rule extends AbstractRuleElement implements Comparable<Rule> {

	protected static final String AUTHOR_KEY = "author";

	protected static final String UUID_KEY = "uuid";

	protected static final String DEFAULT_ASSIGNMENT_CLASS_NAME = "com.webobjects.directtoweb.Rule";

	protected static final String DEFAULT_AUTHOR = "100";

	protected static final String LHS_KEY = "lhs";

	protected static final String RHS_KEY = "rhs";

	protected static final String DOCUMENTATION = "documentation";

	private String author;
	
	private UUID uuid;

	private final LeftHandSide leftHandSide;

	private final RightHandSide rightHandSide;

	private String documentation;

	protected Rule() {
		super(Collections.<String, Object> emptyMap());

		setAssignmentClassName(DEFAULT_ASSIGNMENT_CLASS_NAME);
		setAuthor(DEFAULT_AUTHOR);
		if (Preferences.shouldRuleeditorAddUUID()) {
			setUUID(UUID.randomUUID());
		}

		leftHandSide = new LeftHandSide();

		rightHandSide = new RightHandSide();
	}

	protected Rule(final Map properties) {
		super(properties);

		setAssignmentClassName(DEFAULT_ASSIGNMENT_CLASS_NAME);

		Map<String, Object> lhsProperties = (Map<String, Object>) properties.get(LHS_KEY);

		if (lhsProperties == null) {
			leftHandSide = new LeftHandSide();
		} else {
			leftHandSide = new LeftHandSide(lhsProperties);
		}

		properties.remove(LHS_KEY);

		Map<String, Object> rhsProperties = (Map<String, Object>) properties.get(RHS_KEY);

		rightHandSide = new RightHandSide(rhsProperties);

		properties.remove(RHS_KEY);

		setAuthor(properties.get(AUTHOR_KEY).toString());
		
		if (properties.get(UUID_KEY) != null) {
			setUUID(UUID.fromString((String) properties.get(UUID_KEY)));
		} else {
			setUUID(UUID.randomUUID());
		}
		
		if (properties.get(DOCUMENTATION) != null) {
			setDocumentation(properties.get(DOCUMENTATION).toString());
		}
	}

	/**
	 * Create a new instance of <code>Rule</code> as a copy of the rule taken.
	 * 
	 * @param rule
	 *            A D2W rule
	 */
	protected Rule(Rule rule) {
		super(Collections.<String, Object> emptyMap());

		Map lhsMap = rule.getLeftHandSide().toMap();

		leftHandSide = new LeftHandSide(lhsMap != null ? lhsMap : Collections.EMPTY_MAP);

		Map rhsMap = rule.getRightHandSide().toMap();

		rightHandSide = new RightHandSide(rhsMap != null ? rhsMap : Collections.EMPTY_MAP);

		setAssignmentClassName(rule.getAssignmentClassName());
		setAuthor(rule.getAuthor());
		if (rule.getUUID() != null) {
			setUUID(rule.getUUID());
		} else {
			setUUID(UUID.randomUUID());
		}
		setDocumentation(rule.getDocumentation());
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		super.addPropertyChangeListener(listener);

		leftHandSide.addPropertyChangeListener(listener);
		rightHandSide.addPropertyChangeListener(listener);
	}
	
	public int compareTo(Rule other) {
		int c;

        try {
        	c = Integer.valueOf(getAuthor()).compareTo(Integer.valueOf(other.getAuthor()));
			if (c != 0)
				return c;
        } catch (NumberFormatException e) {
        	// do nothing, just move on
        }
        
        if (Preferences.shouldRuleeditorAddUUID()) {
			/*
			 * we compare the string representation, as the UUID comparison
			 * refers to the most significant field in which the UUIDs differ,
			 * the result of which results in a non-intuitive sort order
			 */
			// c = getUUID().compareTo(other.getUUID());
			c = getUUID().toString().compareTo(other.getUUID().toString());
			if (c != 0)
				return c;
        }
		
		c = getLeftHandSide().toString().compareTo(other.getLeftHandSide().toString());
		if (c != 0)
			return c;

		return getRightHandSide().toString().compareTo(other.getRightHandSide().toString());
	}

	public String getAuthor() {
		return author;
	}

	public UUID getUUID() {
		return uuid;
	}
	
	public LeftHandSide getLeftHandSide() {
		return leftHandSide;
	}

	public RightHandSide getRightHandSide() {
		return rightHandSide;
	}
	
	public String getDocumentation() {
		return documentation;
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		super.removePropertyChangeListener(listener);

		leftHandSide.removePropertyChangeListener(listener);
		rightHandSide.removePropertyChangeListener(listener);
	}

	public void setAuthor(final String author) {
		String oldValue = this.author;

		this.author = author;

		firePropertyChange(AUTHOR_KEY, oldValue, this.author);
	}

	private void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public void setDocumentation(final String documentation) {
		String oldValue = this.documentation;

		this.documentation = documentation;

		firePropertyChange(DOCUMENTATION, oldValue, this.documentation);
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> ruleMap = new HashMap<String, Object>();

		ruleMap.put(CLASS_KEY, getAssignmentClassName());
		ruleMap.put(AUTHOR_KEY, getAuthor());
		if (Preferences.shouldRuleeditorAddUUID()) {
			ruleMap.put(UUID_KEY, getUUID());
		}

		Map<String, Object> lhsMap = leftHandSide.toMap();

		ruleMap.put(Rule.LHS_KEY, lhsMap);

		Map<String, Object> rhsMap = rightHandSide.toMap();

		ruleMap.put(Rule.RHS_KEY, rhsMap);

		if (StringUtils.isNotBlank(getDocumentation())) {
			ruleMap.put(Rule.DOCUMENTATION, getDocumentation());
		}
		return ruleMap;
	}
}
