package com.robmcguinness.panels;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robmcguinness.behaviors.ErrorClassBehavior;
import com.robmcguinness.feedback.InlineFeedbackPanel;
import com.robmcguinness.stateless.StatelessAjaxButton;
import com.robmcguinness.stateless.StatelessAjaxFormComponentUpdatingBehavior;
import com.robmcguinness.stateless.StatelessLabel;
import com.robmcguinness.stateless.utils.HTML;
import com.robmcguinness.stateless.utils.Javascript;
import com.robmcguinness.stateless.utils.Parameters;
import com.robmcguinness.stateless.utils.Validation;

/**
 * Various stateless form ajax behaviors.
 * 
 * @author robertmcguinness
 * 
 */
public class Example2Panel extends Panel {

	private static final Logger logger = LoggerFactory.getLogger(Example2Panel.class);
	private transient User user;
	private WebMarkupContainer yourNameContainer;
	private static String NAME = "name";

	public Example2Panel(String id) {
		super(id);

		user = new User();
		user.setName(Parameters.getParam(NAME));

		final TextField<String> name = new TextField<String>(NAME, new PropertyModel<String>(this, "user.name"));
		name.setMarkupId(name.getId());
		name.add(HTML.maxLength(30));
		name.setRequired(true);
		// TODO[rm3]: get label from label:for
		name.setLabel(new Model<String>("Name"));
		name.add(new Validation("[a-zA-Z][a-zA-Z -]+").setKey("name.valid"));

		final Form<String> inputForm = new StatelessForm<String>("inputForm");
		inputForm.setMarkupId(inputForm.getId());

		inputForm.add(new StatelessAjaxButton("inputFormButton", inputForm) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				PageParameters params = target.getPageParameters();
				logger.debug("onSubmit pageParamters {}", params);

				Example2Panel.this.user.setName(Parameters.getParam(params, NAME));

				target.add(yourNameContainer);
				target.appendJavaScript(Javascript.highlight(yourNameContainer));
				target.add(getFeedback(form));
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {

				target.addChildren(form, FormComponent.class);
				target.add(getFeedback(form));

			}

			private Component getFeedback(Form<?> form) {
				return form.get("nameFeedback");
			}

		});

		yourNameContainer = new WebMarkupContainer("yourNameContainer") {
			@Override
			public boolean isVisible() {
				return Example2Panel.this.user.getName() != null;
			}
		};
		yourNameContainer.setMarkupId(yourNameContainer.getId());
		yourNameContainer.setOutputMarkupPlaceholderTag(true);
		add(yourNameContainer);
		StatelessLabel yourNameLabel = new StatelessLabel("yourName", new PropertyModel<String>(this, "user.name"));
		yourNameLabel.setOutputMarkupPlaceholderTag(true);
		yourNameContainer.add(yourNameLabel);

		inputForm.add(name);
		add(inputForm);

		final DropDownChoice<String> preference = new DropDownChoice<String>("preference", new Model<String>("Tebowing"), Arrays.asList(new String[] { "Tebowing", "Gronking", "Other" }));
		preference.add(new StatelessAjaxFormComponentUpdatingBehavior("onchange") {

			@Override
			protected PageParameters getPageParameters() {
				return new PageParameters();
			}

			@Override
			protected void onUpdate(final AjaxRequestTarget target) {
				logger.debug("pageParamters {}", getPageParameters());
			}
		});
		preference.setMarkupId(preference.getId());
		add(preference);

		InlineFeedbackPanel feedback = new InlineFeedbackPanel("nameFeedback", name);
		feedback.setOutputMarkupId(true);
		feedback.setMarkupId(feedback.getId());

		inputForm.add(feedback);

		// https://www.packtpub.com/apache-wicket-cookbook/book#chapter_3"
		inputForm.visitChildren(FormComponent.class, new IVisitor<FormComponent<?>, Void>() {

			@Override
			public void component(FormComponent<?> component, IVisit<Void> visit) {
				component.add(new ErrorClassBehavior());
			}

		});

	}

	private class User {

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

}
