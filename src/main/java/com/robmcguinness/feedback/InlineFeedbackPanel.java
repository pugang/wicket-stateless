package com.robmcguinness.feedback;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;

public class InlineFeedbackPanel extends ComponentFeedbackPanel {

	public InlineFeedbackPanel(String id, Component filter) {
		super(id, filter);
	}

}
