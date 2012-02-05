package com.robmcguinness.panels;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;

import com.robmcguinness.models.SessionModel;
import com.robmcguinness.stateless.StatelessLabel;

/**
 * This is the fixed header bar.
 * 
 * @author robertmcguinness
 * 
 */
public class HeaderPanel extends Panel {

	public HeaderPanel(String id) {
		super(id);

		add(new StatelessLabel("version", new AbstractReadOnlyModel<String>() {

			@Override
			public String getObject() {
				return getVersion();
			}
		}));

		add(new SessionLabel("sessionLabel", new SessionModel()).setOutputMarkupId(true));
	}

	private class SessionLabel extends StatelessLabel {

		public SessionLabel(String id, IModel<?> model) {
			super(id, model);
		}

		@Override
		protected void onBeforeRender() {
			super.onBeforeRender();
			SessionModel model = (SessionModel) getDefaultModel();
			if (model.isStateless()) {
				add(new AttributeModifier("class", new Model<String>("btn-success btn-large session")));
			} else {
				add(new AttributeModifier("class", new Model<String>("btn-danger btn-large session")));
			}
		}

		@Override
		public void onEvent(IEvent<?> event) {
			// update the session label on every ajax request
			if (event.getPayload() instanceof AjaxRequestTarget) {
				((AjaxRequestTarget) event.getPayload()).add(this);
			}
		}

	}

	public String getVersion() {
		String implVersion = null;
		Package pkg = getClass().getPackage();
		if (pkg != null) {
			implVersion = pkg.getImplementationVersion();
		}
		return Strings.isEmpty(implVersion) ? "n/a" : implVersion;
	}

}
