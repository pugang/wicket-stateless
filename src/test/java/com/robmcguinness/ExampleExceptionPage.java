package com.robmcguinness;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ContainerInfo;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.MarkupFactory;
import org.apache.wicket.markup.MarkupResourceStream;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.util.resource.StringResourceStream;

import com.robmcguinness.stateless.StatelessAjaxFallbackLink;

public class ExampleExceptionPage extends WebPage {

	private static final String HTML = "<html><head><title>testing excpetions</title></head><body><a wicket:id=\"link\">Throw Exception</a></body></html>";

	private boolean clicked = false;

	public ExampleExceptionPage() {

		add(new StatelessAjaxFallbackLink<String>("link") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				clicked = true;
				target.add(this);
			}

			@Override
			protected void onAfterRender() {
				super.onAfterRender();
				if (clicked) {
					throw new IllegalStateException("throw application in stateful mode");
				}
			}

		});
	}

	@Override
	public IMarkupFragment getMarkup() {
		StringResourceStream stream = new StringResourceStream(HTML);
		MarkupResourceStream mrs = new MarkupResourceStream(stream, new ContainerInfo(this), null);
		return MarkupFactory.get().loadMarkup(this, mrs, false);
	}
}
