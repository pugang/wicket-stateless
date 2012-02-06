package com.robmcguinness.panels;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robmcguinness.domain.GitHubCommits;
import com.robmcguinness.domain.GithubCommit;
import com.robmcguinness.stateless.StatelessAjaxDataView;
import com.robmcguinness.stateless.utils.Parameters;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class Example4Panel extends Panel {

	private static final Logger logger = LoggerFactory.getLogger(Example4Panel.class);

	private static final String COMMIT_API_URL = "http://github.com/api/v2/json/commits/list/robmcguinness/wicket-stateless/master?page=";
	private static final String PER_PAGE = "&per_page=10";

	private int page;
	private final PageParameters params;

	public Example4Panel(String id, PageParameters params) {
		super(id);
		page = 1;
		this.params = params;

		StatelessAjaxDataView<GithubCommit> dataView = new StatelessAjaxDataView<GithubCommit>("commits", new GithubCommitsDataProvider(), StatelessAjaxDataView.ITEMS_PER_PAGE, params) {

			@Override
			protected void populateItem(Item<GithubCommit> item) {
				item.add(new Label("id"));
				item.add(new Label("sanitizedMessage"));
				Label date = new Label("committed_date");
				date.add(new AttributeAppender("title", new PropertyModel<String>(item.getModel(), "iso8601Date"), " "));
				item.add(date);
			}
		};

		dataView.setCurrentPage(getPageNumber());
		add(dataView);

	}

	private int getPageNumber() {
		StringValue _page = params.get(StatelessAjaxDataView.PAGE_PARAM);
		if (Parameters.isInteger(_page)) {
			page = _page.toInt();
		}
		return page;
	}

	private class GithubCommitsDataProvider implements IDataProvider<GithubCommit> {

		private transient List<GithubCommit> commits;

		@Override
		public void detach() {

		}

		@Override
		public Iterator<? extends GithubCommit> iterator(long first, long count) {
			return commits.iterator();
		}

		@Override
		public long size() {
			commits = getGithubCommits();
			return commits.size();
		}

		/**
		 * Retrieve the lastest commits for <a href="https://github.com/robmcguinness/wicket-stateless">wicket-stateless</a>
		 * 
		 * @return
		 */
		private List<GithubCommit> getGithubCommits() {

			ClientConfig config = new DefaultClientConfig();
			config.getClasses().add(JacksonJsonProvider.class);
			Client client = Client.create(config);
			WebResource webResource = client.resource(pageUrl());
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed to retrieve Github commits : HTTP error code : " + response.getStatus());
			}

			logger.debug("Github status: {}", response.getStatus());

			String json = response.getEntity(String.class);
			logger.debug("Github response {}", json);

			return parseJsonResponse(json);

		}

		private List<GithubCommit> parseJsonResponse(String json) {
			ObjectMapper mapper = new ObjectMapper();
			GitHubCommits _commits = null;
			try {
				_commits = mapper.readValue(json, GitHubCommits.class);
				return _commits.getCommits();
			} catch (Exception e) {
				logger.error("Failed to parse Github json", e);
			}

			return Collections.emptyList();
		}

		private String pageUrl() {

			return page <= 0 ? COMMIT_API_URL + 1 + PER_PAGE : COMMIT_API_URL + getPageNumber() + PER_PAGE;
		}

		@Override
		public IModel<GithubCommit> model(GithubCommit commit) {
			return new CompoundPropertyModel<GithubCommit>(commit);
		}

	}

}
