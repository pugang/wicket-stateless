package com.robmcguinness.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.util.string.Strings;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubCommit {
	private String id;
	private String message;
	private Date committed_date;

	public static SimpleDateFormat ISO8601FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSanitizedMessage() {
		if (!Strings.isEmpty(message)) {
			int index = message.indexOf("Signed-off-by:");
			if (index != -1) {
				return message.substring(0, index);
			}
		}

		return message;
	}

	public Date getCommitted_date() {
		return committed_date;
	}

	public void setCommitted_date(Date committed_date) {
		this.committed_date = committed_date;
	}

	public String getIso8601Date() {
		if (committed_date != null) {
			return ISO8601FORMAT.format(committed_date);
		}

		return null;
	}
}