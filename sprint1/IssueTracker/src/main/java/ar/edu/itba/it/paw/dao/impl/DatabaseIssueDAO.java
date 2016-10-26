package ar.edu.itba.it.paw.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.it.paw.dao.IssueDAO;
import ar.edu.itba.it.paw.domain.ConnectionManager;
import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Priority;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.Resolution;
import ar.edu.itba.it.paw.domain.State;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.exceptions.DatabaseException;

public class DatabaseIssueDAO implements IssueDAO{

	private static IssueDAO instance;
	
	public static synchronized IssueDAO getInstance(){
		if(instance == null)
			instance = new DatabaseIssueDAO();
		return instance;
	}

	@Override
	public void saveIssue(Issue issue) {
		ConnectionManager manager = ConnectionManager.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		
		Date date = new Date(issue.getCreationDate().getTime());
		
		Float eTime = issue.getEstimatedTime();
		float estimatedTime = (eTime == null? -1:eTime);
		
		try{
			if(issue.getId() == -1) {
				stmt = conn.prepareStatement("insert into issues (title, description, creationDate, reporterId, assigneeId, projectId, estimatedTime, state, priority, resolution) values (?,?,?,?,?,?,?,?,?,?)");
				stmt.setString(1, issue.getTitle());
				stmt.setString(2, issue.getDescription()); 
				stmt.setDate(3, date);
				stmt.setInt(4, issue.getReporter().getId());
				if(issue.getAssignee() == null) {
					stmt.setNull(5, java.sql.Types.INTEGER);
				} else {
					stmt.setInt(5, issue.getAssignee().getId());
				}
				stmt.setInt(6, issue.getProject().getId());
				stmt.setFloat(7, estimatedTime);
				stmt.setString(8, issue.getState().toString());
				stmt.setString(9, issue.getPriority().toString());
				if (issue.getResolution() != null){
					stmt.setString(10, issue.getResolution().toString());
				}else{
					stmt.setString(10, null);
				}
			} else {
				stmt = conn.prepareStatement("update issues set title=?, description=?, creationDate=?, reporterId=?, assigneeId=?, projectId=?, estimatedTime=?, state=?, priority=?, resolution=? where id=?");
				stmt.setString(1, issue.getTitle());
				stmt.setString(2, issue.getDescription()); 
				stmt.setDate(3, date);
				stmt.setInt(4, issue.getReporter().getId());
				if(issue.getAssignee() == null) {
					stmt.setNull(5, java.sql.Types.INTEGER);
				} else {
					stmt.setInt(5, issue.getAssignee().getId());
				}
				stmt.setInt(6, issue.getProject().getId());
				stmt.setFloat(7, estimatedTime);
				stmt.setString(8, issue.getState().toString());
				stmt.setString(9, issue.getPriority().toString());
				if (issue.getResolution() != null){
					stmt.setString(10, issue.getResolution().toString());
				}else{
					stmt.setString(10, null);
				}
				stmt.setInt(11, issue.getId());
			}
			stmt.execute();
		} catch(Exception e){
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
	}

	
	@Override
	public Issue getIssue(int id) {
		ConnectionManager manager = ConnectionManager.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		Issue issue = null;
		
		try{
			stmt = conn.prepareStatement("select * from issues where id = ?");
			stmt.setInt(1, id);
			ResultSet cur = stmt.executeQuery();
			
			if(cur.next()){
				User reporter = DatabaseUserDAO.getInstance().getUser(cur.getInt("reporterId"));
				User assignee = DatabaseUserDAO.getInstance().getUser(cur.getInt("assigneeId"));
				Project project = DatabaseProjectDAO.getInstance().getProjectById(cur.getInt("projectId"));
				
				float eTime = cur.getFloat("estimatedTime");
				Float estimatedTime = (eTime == -1? null: Float.valueOf(eTime));
				
				issue = new Issue(cur.getString("title"), 
						cur.getString("description"), estimatedTime, 
						cur.getDate("creationDate"), reporter, 
						assignee, project);
				issue.setId(id);
				issue.setCode(this.calculateCode(issue));
				issue.setState(State.getEnum(cur.getString("state")));
				issue.setPriority(Priority.getEnum(cur.getString("priority")));
				issue.setResolution(Resolution.getEnum(cur.getString("resolution")));
			}
			else{
				manager.closeConnection();
				return null;
			}
		} catch(Exception e){
			throw new DatabaseException();
		}
		
		manager.closeConnection();
		return issue;
	}

	public List<Issue> getProjectIssues(Project project) {
		ConnectionManager manager = ConnectionManager.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		List<Issue> issues = new ArrayList<Issue>();
		
		try {
			stmt = conn.prepareStatement("select * from issues where projectid = ?");
			stmt.setInt(1, project.getId());
			ResultSet cur = stmt.executeQuery();
			
			while(cur.next()){
				User reporter = DatabaseUserDAO.getInstance().getUser(cur.getInt("reporterid"));
				User assignee = DatabaseUserDAO.getInstance().getUser(cur.getInt("assigneeid"));
				float eTime = cur.getFloat("estimatedTime");
				Float estimatedTime = (eTime == -1? null: Float.valueOf(eTime));
				Issue issue = new Issue(cur.getString("title"), cur.getString("description"), estimatedTime,
						cur.getDate("creationdate"), reporter, assignee, project);
				issue.setId(cur.getInt("id"));
				issue.setCode(this.calculateCode(issue));
				issue.setState(State.getEnum(cur.getString("state")));
				issue.setPriority(Priority.getEnum(cur.getString("priority")));
				issue.setResolution(Resolution.getEnum(cur.getString("resolution")));
				issues.add(issue);
			}
		} catch (SQLException e) {
			throw new DatabaseException();
		}
		
		manager.closeConnection();
		return issues;
	}

	public List<Issue> getUserIssues(User user) {
		ConnectionManager manager = ConnectionManager.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		List<Issue> issues = new ArrayList<Issue>();

		try {
			stmt = conn.prepareStatement("select * from issues where assigneeid = ?");
			stmt.setInt(1, user.getId());
			ResultSet cur = stmt.executeQuery();

			while (cur.next()) {
				User reporter = DatabaseUserDAO.getInstance().getUser(
						cur.getInt("reporterid"));
				Project project = DatabaseProjectDAO.getInstance().getProjectById(
						cur.getInt("projectid"));

				float eTime = cur.getFloat("estimatedTime");
				Float estimatedTime = (eTime == -1? null: Float.valueOf(eTime));
				Issue issue = new Issue(cur.getString("title"),
						cur.getString("description"),
						estimatedTime,
						cur.getDate("creationDate"), reporter, user, project);
				issue.setState(State.getEnum(cur.getString("state")));
				issue.setPriority(Priority.getEnum(cur.getString("priority")));
				issue.setCode(this.calculateCode(issue));
				issue.setId(cur.getInt("id"));
				issue.setResolution(Resolution.getEnum(cur.getString("resolution")));
				issues.add(issue);
			}
		} catch (SQLException e) {
			throw new DatabaseException();
		}
		
		manager.closeConnection();
		return issues;
	}
	
	private String calculateCode(Issue issue) {
		return issue.getProject().getCode() + "-" + issue.getId();
	}

}
