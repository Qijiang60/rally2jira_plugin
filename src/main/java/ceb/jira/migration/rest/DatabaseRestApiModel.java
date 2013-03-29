package ceb.jira.migration.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseRestApiModel {

	private int issueDbId;
	private Date creationDate;
	private Date lastUpdateDate;
	private boolean stateChanged;
	private List<String> messages = new ArrayList<String>();

	public DatabaseRestApiModel() {
	}

	public DatabaseRestApiModel(int id) {
		this.issueDbId = id;
	}

	public int getIssueDbId() {
		return issueDbId;
	}

	public void setIssueDbId(int issueId) {
		this.issueDbId = issueId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public boolean isStateChanged() {
		return stateChanged;
	}

	public boolean getStateChanged() {
		return stateChanged;
	}

	public void setStateChanged(boolean stateChanged) {
		this.stateChanged = stateChanged;
	}

	public void addMessage(String message) {
		messages.add(message);
	}

	public List<String> getMessages() {
		return messages;
	}
}