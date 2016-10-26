package ar.edu.itba.it.paw.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.dao.IssueDAO;
import ar.edu.itba.it.paw.dao.ProjectDAO;
import ar.edu.itba.it.paw.dao.UserDAO;
import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.ConnectionManager;
import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.IssueFilter;
import ar.edu.itba.it.paw.domain.Job;
import ar.edu.itba.it.paw.domain.Priority;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.Resolution;
import ar.edu.itba.it.paw.domain.State;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.Visit;
import ar.edu.itba.it.paw.domain.exceptions.DatabaseException;

@Repository
public class DatabaseIssueDAO implements IssueDAO {

	private ConnectionManager manager;
	private UserDAO userDAO;
	private ProjectDAO projectDAO;

	@Autowired
	public DatabaseIssueDAO(ConnectionManager manager, UserDAO userDAO,
			ProjectDAO projectDAO) {
		this.manager = manager;
		this.userDAO = userDAO;
		this.projectDAO = projectDAO;
	}

	@Override
	public void saveIssue(Issue issue) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;

		Date date = null;
		if (issue.getCreationDate() != null) {
			date = new Date(issue.getCreationDate().getTime());
		}

		Float estimatedTime = issue.getEstimatedTime();

		try {
			if (issue.isNew()) {
				stmt = conn
						.prepareStatement("insert into issues (title, description, creationDate, reporterId, assigneeId, projectId, estimatedTime, state, priority, resolution) values (?,?,?,?,?,?,?,?,?,?)");
				stmt.setString(1, issue.getTitle());
				stmt.setString(2, issue.getDescription());
				stmt.setDate(3, date);
				stmt.setInt(4, issue.getReporter().getId());
				if (issue.getAssignee() == null) {
					stmt.setNull(5, java.sql.Types.INTEGER);
				} else {
					stmt.setInt(5, issue.getAssignee().getId());
				}
				stmt.setInt(6, issue.getProject().getId());
				if (estimatedTime != null) {
					stmt.setFloat(7, estimatedTime);
				} else {
					stmt.setNull(7, java.sql.Types.FLOAT);
				}
				stmt.setString(8, issue.getState().toString());
				stmt.setString(9, issue.getPriority().toString());
				if (issue.getResolution() != null) {
					stmt.setString(10, issue.getResolution().toString());
				} else {
					stmt.setString(10, null);
				}
			} else {
				stmt = conn
						.prepareStatement("update issues set title=?, description=?, reporterId=?, assigneeId=?, projectId=?, estimatedTime=?, state=?, priority=?, resolution=? where id=?");
				stmt.setString(1, issue.getTitle());
				stmt.setString(2, issue.getDescription());
				stmt.setInt(3, issue.getReporter().getId());
				if (issue.getAssignee() == null) {
					stmt.setNull(4, java.sql.Types.INTEGER);
				} else {
					stmt.setInt(4, issue.getAssignee().getId());
				}
				stmt.setInt(5, issue.getProject().getId());
				if (estimatedTime != null) {
					stmt.setFloat(6, estimatedTime);
				} else {
					stmt.setNull(6, java.sql.Types.FLOAT);
				}
				stmt.setString(7, issue.getState().toString());
				stmt.setString(8, issue.getPriority().toString());
				if (issue.getResolution() != null) {
					stmt.setString(9, issue.getResolution().toString());
				} else {
					stmt.setString(9, null);
				}
				stmt.setInt(10, issue.getId());
			}
			stmt.execute();
		} catch (Exception e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
	}

	@Override
	public Issue getIssue(int id) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		Issue issue = null;

		try {
			stmt = conn.prepareStatement("select * from issues where id = ?");
			stmt.setInt(1, id);
			ResultSet cur = stmt.executeQuery();

			if (cur.next()) {
				issue = getNewIssue(cur);
			}
		} catch (Exception e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return issue;
	}

	public List<Issue> getProjectIssues(Project project) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		List<Issue> issues = new ArrayList<Issue>();

		try {
			stmt = conn
					.prepareStatement("select * from issues where projectid = ?");
			stmt.setInt(1, project.getId());
			ResultSet cur = stmt.executeQuery();

			while (cur.next()) {
				Issue issue = getNewIssue(cur);
				issues.add(issue);
			}
		} catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return issues;
	}

	public List<Issue> getProjectIssuesWithFilter(Project project,
			IssueFilter filter) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		List<Issue> issues = new ArrayList<Issue>();

		boolean is_st = filter != null && filter.getDate_st() != null;
		String sql_st = (is_st) ? new java.sql.Date(filter.getDate_st()
				.getTime()).toString() : "";

		boolean is_et = filter != null && filter.getDate_et() != null;
		String sql_et = (is_et) ? new java.sql.Date(filter.getDate_et()
				.getTime()).toString() : "";

		try {
			stmt = conn
					.prepareStatement("select * from issues where projectid = ? and (? is null or title like ?)"
							+ "and (? is null or description like ?)"
							+ "and (? = 0 or reporterid = ?)"
							+ "and (? = 0 or assigneeid = ?)"
							+ "and (? is null or state = ?)"
							+ "and (? is null or resolution = ?)"
							+ ((is_st) ? "and ( creationdate >= DATE '"
									+ sql_st + "' )" : "")
							+ ((is_et) ? "and ( creationdate <= DATE '"
									+ sql_et + "' )" : ""));
			stmt.setInt(1, project.getId());
			if (filter != null && filter.getTitle() != null
					&& !filter.getTitle().isEmpty()) {
				stmt.setString(2, "%" + filter.getTitle() + "%");
				stmt.setString(3, "%" + filter.getTitle() + "%");
			} else {
				stmt.setString(2, null);
				stmt.setString(3, null);
			}
			if (filter != null && filter.getDescription() != null
					&& !filter.getDescription().isEmpty()) {
				stmt.setString(4, "%" + filter.getDescription() + "%");
				stmt.setString(5, "%" + filter.getDescription() + "%");
			} else {
				stmt.setString(4, null);
				stmt.setString(5, null);
			}
			if (filter != null && filter.getReporter() != null
					&& filter.getReporter().getId() > 0) {
				stmt.setInt(6, filter.getReporter().getId());
				stmt.setInt(7, filter.getReporter().getId());
			} else {
				stmt.setInt(6, 0);
				stmt.setInt(7, 0);
			}
			if (filter != null && filter.getAsignee() != null
					&& filter.getAsignee().getId() > 0) {
				stmt.setInt(8, filter.getAsignee().getId());
				stmt.setInt(9, filter.getAsignee().getId());
			} else {
				stmt.setInt(8, 0);
				stmt.setInt(9, 0);
			}
			if (filter != null && filter.getState() != null) {
				stmt.setString(10, filter.getState().toString());
				stmt.setString(11, filter.getState().toString());
			} else {
				stmt.setString(10, null);
				stmt.setString(11, null);
			}
			if (filter != null && filter.getResolution() != null) {
				stmt.setString(12, filter.getResolution().toString());
				stmt.setString(13, filter.getResolution().toString());
			} else {
				stmt.setString(12, null);
				stmt.setString(13, null);
			}
			ResultSet cur = stmt.executeQuery();

			while (cur.next()) {
				Issue issue = getNewIssue(cur);
				issues.add(issue);
			}
		} catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return issues;
	}

	public List<Issue> getUserIssues(User user) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		List<Issue> issues = new ArrayList<Issue>();

		try {
			stmt = conn
					.prepareStatement("select * from issues where assigneeid = ?");
			stmt.setInt(1, user.getId());
			ResultSet cur = stmt.executeQuery();

			while (cur.next()) {
				Issue issue = getNewIssue(cur);

				issues.add(issue);
			}
		} catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return issues;
	}

	private Issue getNewIssue(ResultSet cur) {
		Issue issue = null;

		try {
			User reporter = userDAO.getUser(cur.getInt("reporterId"));
			User assignee = userDAO.getUser(cur.getInt("assigneeId"));
			Project project = projectDAO
					.getProjectById(cur.getInt("projectId"));

			Float estimatedTime = cur.getFloat("estimatedTime");

			String dbres;
			Resolution resolution = null;
			if ((dbres = cur.getString("resolution")) != null) {
				resolution = Resolution.valueOf(dbres);
			}

			issue = new Issue(cur.getString("title"), cur
					.getString("description"), estimatedTime, cur
					.getDate("creationDate"), reporter, assignee, State
					.valueOf(cur.getString("state")), resolution, Priority
					.valueOf(cur.getString("priority")), project);

			issue.setId(cur.getInt("id"));

		} catch (SQLException e) {
			throw new DatabaseException();
		}

		return issue;
	}

	@Override
	public void saveComment(Comment comment) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;

		Date date = new Date(comment.getCreationDate().getTime());

		int index = 0;

		try {
			stmt = conn
					.prepareStatement("insert into issues_comments (user_fk, issue_fk, comment, creation_date) values (?,?,?,?)");
			stmt.setInt(++index, comment.getUser().getId());
			stmt.setInt(++index, comment.getIssue().getId());
			stmt.setString(++index, comment.getText());
			stmt.setDate(++index, date);

			stmt.execute();
		} catch (Exception e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}

	}

	@Override
	public List<Comment> getIssueComments(Issue issue) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		List<Comment> comments = new ArrayList<Comment>();

		try {
			stmt = conn
					.prepareStatement("select * from issues_comments where issue_fk = ?");
			stmt.setInt(1, issue.getId());
			ResultSet cur = stmt.executeQuery();

			while (cur.next()) {
				Comment comment = getNewComment(cur);
				comments.add(comment);
			}
		} catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return comments;
	}

	private Comment getNewComment(ResultSet cur) {

		Comment comment = null;

		try {

			User user = userDAO.getUser(cur.getInt("user_fk"));
			Issue issue = this.getIssue(cur.getInt("issue_fk"));

			comment = new Comment(cur.getDate("creation_date"), cur
					.getString("comment"), user, issue);

			comment.setId(cur.getInt("id"));

		} catch (SQLException e) {
			throw new DatabaseException();
		}

		return comment;
	}

	@Override
	public void saveJob(Job job) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;

		Timestamp timeStamp = new Timestamp(job.getDate().getTime());

		int index = 0;

		try {
			stmt = conn
					.prepareStatement("insert into jobs (user_fk, issue_fk, duration, resolved_date, description) values (?,?,?,?,?)");
			stmt.setInt(++index, job.getUser().getId());
			stmt.setInt(++index, job.getIssue().getId());
			stmt.setInt(++index, job.getElapsedTime());
			stmt.setTimestamp(++index, timeStamp);
			stmt.setString(++index, job.getDescription());

			stmt.execute();
		} catch (Exception e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
	}

	@Override
	public List<Job> getIssueJobs(Issue issue) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		List<Job> jobs = new ArrayList<Job>();

		try {
			stmt = conn
					.prepareStatement("select * from jobs where issue_fk = ?");
			stmt.setInt(1, issue.getId());
			ResultSet cur = stmt.executeQuery();

			while (cur.next()) {
				Job job = getNewJob(cur);
				jobs.add(job);
			}
		} catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return jobs;
	}

	private Job getNewJob(ResultSet cur) {

		Job job = null;

		try {

			User user = userDAO.getUser(cur.getInt("user_fk"));
			Issue issue = this.getIssue(cur.getInt("issue_fk"));

			job = new Job(cur.getInt("duration"), cur.getString("description"),
					user, cur.getTimestamp("resolved_date"), issue);

			job.setId(cur.getInt("id"));

		} catch (SQLException e) {
			throw new DatabaseException();
		}

		return job;
	}
	
	@Override
	public void saveVisit(Visit visit) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;

		int index = 0;

		try {
			stmt = conn
					.prepareStatement("insert into visits (issueId, accessDate) values (?,CURRENT_DATE)");
			stmt.setInt(++index, visit.getIssue().getId());
			stmt.execute();
		} catch (Exception e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
	}

	public List<Visit> getIssueVisits(Issue issue) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		List<Visit> visits = new ArrayList<Visit>();

		try {
			stmt = conn
					.prepareStatement("select * from visits where issueId = ?");
			stmt.setInt(1, issue.getId());
			ResultSet cur = stmt.executeQuery();

			while (cur.next()) {
				Visit visit = getNewVisit(cur);
				visits.add(visit);
			}
		} catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return visits;
	}

	private Visit getNewVisit(ResultSet cur) {
		Visit visit = null;

		try {
			Issue issue = this.getIssue(cur.getInt("issueId"));
			visit = new Visit(issue, cur.getDate("accessdate"));
			visit.setId(cur.getInt("id"));

		} catch (SQLException e) {
			throw new DatabaseException();
		}

		return visit;
	}

	@Override
	public List<Visit> getProjectVisits(Project project){
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		List<Visit> visits = new ArrayList<Visit>();
		
		try {
			stmt = conn.prepareStatement("select visits.id, visits.issueid, visits.accessdate from visits, issues " +
					"where visits.issueid = issues.id and issues.projectid = ? ");
			stmt.setInt(1, project.getId());
			ResultSet cur = stmt.executeQuery();
			
			while(cur.next()){
				Visit visit = getNewVisit(cur);
				visits.add(visit);
			}
		} catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return visits;
	}
	
}
