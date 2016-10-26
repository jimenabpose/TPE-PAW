package ar.edu.itba.it.paw.web.converter;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.services.IssueServices;


public class IssueFormatter implements Formatter<Issue> {

	private IssueServices service;

	@Autowired
	public IssueFormatter(IssueServices service) {
		this.service = service;
	}

	@Override
	public String print(Issue arg0, Locale arg1) {
		return arg0.getId() + "";
	}

	@Override
	public Issue parse(String arg0, Locale arg1)  {
		if(arg0.equals("-1")) {
			return null;
		}
		return service.getIssue(Integer.valueOf(arg0));
	}
}
