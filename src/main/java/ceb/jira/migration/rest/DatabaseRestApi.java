package ceb.jira.migration.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * A resource of message.
 */
@Path("/update")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DatabaseRestApi {

	@GET
	@Path("/issue/dates/${issueDbId}")
	public DatabaseRestApiModel dates(@PathParam("issueDbId") int id) {

		return new DatabaseRestApiModel(id);
	}

	@POST
	@Path("/issue/createAndUpdateDate")
	public DatabaseRestApiModel createAndUpdateDate(DatabaseRestApiModel model) {

		System.out.println(model.getIssueDbId() + ", " + model.getCreationDate() + ", " + model.getLastUpdateDate());

		List<String> messages = new ArrayList<String>();
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://172.22.26.20/jiradb";
			String user = "jiradbuser";
			String password = "j1r$d8us3x!";
			String updateString =
					"update JIRAISSUE " +
							"set CREATED = ? , UPDATED = ? where ID = ?";
			if (model.isStateChanged()) {
				updateString =
						"update JIRAISSUE " +
								"set CREATED = ? , UPDATED = ?, RESOLUTIONDATE = ? where ID = ?";

			}
			Connection con = null;
			PreparedStatement updateIssue = null;
			try {
				con = DriverManager.getConnection(url, user, password);
				updateIssue = con.prepareStatement(updateString);
				con.setAutoCommit(false);
				updateIssue.setDate(1, new java.sql.Date(model.getCreationDate().getTime()));
				updateIssue.setDate(2, new java.sql.Date(model.getLastUpdateDate().getTime()));
				if (model.isStateChanged()) {
					updateIssue.setDate(3, new java.sql.Date(model.getLastUpdateDate().getTime()));
					updateIssue.setInt(4, model.getIssueDbId());
				} else {
					updateIssue.setInt(3, model.getIssueDbId());
				}
				updateIssue.executeUpdate();
				con.commit();
				model.addMessage("Success");
			} catch (SQLException e) {
				e.printStackTrace();
				messages.add(e.getMessage());
			} finally {
				if (updateIssue != null) {
					try {
						updateIssue.close();
					} catch (SQLException e) {
						e.printStackTrace();
						model.addMessage((e.getMessage()));
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
						model.addMessage((e.getMessage()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addMessage((e.getMessage()));
		}
		return model;
	}

}