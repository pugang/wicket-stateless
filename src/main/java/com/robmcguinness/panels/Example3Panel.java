package com.robmcguinness.panels;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robmcguinness.stateless.StatelessAjaxFormComponentUpdatingBehavior;
import com.robmcguinness.stateless.StatelessLabel;
import com.robmcguinness.stateless.utils.Javascript;

public class Example3Panel extends Panel {

	private static final Logger logger = LoggerFactory.getLogger(Example3Panel.class);

	final static LinkedHashMap<String, String> PREFERENCES = new LinkedHashMap<String, String>();

	private final String selected;

	static {
		PREFERENCES.put("Tebowing", "to get down on a knee and start praying");
		PREFERENCES.put("Gronking", "the act of ferociously spiking an object into the ground");
		PREFERENCES.put("Planking", "an activity consisting of lying face down in an unusual or incongruous location");
		PREFERENCES.put("Conaning", "to crush your enemies, see them driven before you, and to hear the lamentation of their women");
	}

	private DropDownChoice<String> preferenceDDC;
	private StatelessLabel definition;

	public Example3Panel(String id) {
		super(id);
		selected = PREFERENCES.entrySet().iterator().next().getKey();
		preferenceDDC = new DropDownChoice<String>("preference", new PropertyModel<String>(this, "selected"), new DropDownModel(), new IChoiceRenderer<String>() {

			@Override
			public Object getDisplayValue(String object) {
				return object;
			}

			@Override
			public String getIdValue(String object, int index) {
				return object;
			}

		});
		preferenceDDC.add(new StatelessAjaxFormComponentUpdatingBehavior("onchange") {

			@Override
			protected void onUpdate(final AjaxRequestTarget target) {
				PageParameters params = target.getPageParameters();
				logger.debug("onChange pageParamters {}", params);
				target.add(preferenceDDC);
				target.add(definition);
				target.appendJavaScript(Javascript.highlight(definition));
			}

		});
		preferenceDDC.setMarkupId(preferenceDDC.getId());

		add(definition = new StatelessLabel("definition", new AbstractReadOnlyModel<String>() {

			@Override
			public String getObject() {
				return PREFERENCES.get(selected);
			}
		}));

		add(preferenceDDC);
	}

	private class DropDownModel extends LoadableDetachableModel<List<String>> {

		@Override
		protected List<String> load() {
			return new ArrayList<String>(PREFERENCES.keySet());
		}

	}

}
