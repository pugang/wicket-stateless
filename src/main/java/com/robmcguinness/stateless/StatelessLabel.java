package com.robmcguinness.stateless;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

/**
 * Same as {@link Label} but by default the <code>markupId</code> is set to the the id
 * of the component.
 * @author robertmcguinness
 *
 */
public class StatelessLabel extends Label {

	public StatelessLabel(String id, IModel<?> model) {
		super(id, model);
		init();
	}

	public StatelessLabel(String id, String label) {
		super(id, label);
		init();
	}

	public StatelessLabel(String id) {
		super(id);
		init();
	}
	
	protected void init() {
		setMarkupId(getId());
	}

}
