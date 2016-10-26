package ar.edu.itba.it.paw.web.converter;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.repo.IssueRepo;


public class IssueFormatter implements Formatter<Issue> {

	private IssueRepo issueRepo;

	@Autowired
	public IssueFormatter(IssueRepo issueRepo) {
		this.issueRepo = issueRepo;
	}

	@Override
	public String print(Issue arg0, Locale arg1) {
		return arg0.getId() + "";
	}

	@Override
	public Issue parse(String arg0, Locale arg1)  {
		return issueRepo.getIssue(Integer.valueOf(arg0));
	}
}
