package com.robmcguinness.domain;

import java.util.List;

public class GitHubCommits {
	private List<GithubCommit> commits;

	public List<GithubCommit> getCommits() {
		return commits;
	}

	public void setCommits(List<GithubCommit> commits) {
		this.commits = commits;
	}

}
