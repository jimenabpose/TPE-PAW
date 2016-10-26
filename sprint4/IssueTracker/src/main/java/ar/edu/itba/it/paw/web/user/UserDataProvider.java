package ar.edu.itba.it.paw.web.user;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import ar.edu.itba.it.paw.domain.issue.Issue;

public class UserDataProvider extends SortableDataProvider{

	private List<Issue> list;
	
	public UserDataProvider(List<Issue> list){
		this.list = list;
	}
	
	@Override
	public Iterator iterator(int first, int count) {
		return list.subList(first, first + count).iterator();
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public IModel<Issue> model(final Object object) {
		return new AbstractReadOnlyModel<Issue>() {
			@Override
			public Issue getObject() {
				return (Issue) object;
			}
		};
	}

}
