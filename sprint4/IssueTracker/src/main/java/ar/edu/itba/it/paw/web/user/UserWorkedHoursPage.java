package ar.edu.itba.it.paw.web.user;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;

public class UserWorkedHoursPage extends BasePage {
	
	private transient User user;
	private transient Date from;
	private transient Date to;
	
	private transient int workedHours;
	
	public UserWorkedHoursPage(){
		add(new FeedbackPanel("feedback"));
		
		Form<UserWorkedHoursPage> form = new Form<UserWorkedHoursPage>("form", new CompoundPropertyModel<UserWorkedHoursPage>(this));
		
		IModel<List<User>> usersModel = new LoadableDetachableModel<List<User>>() {
			@Override
			protected List<User> load() {
				return new ArrayList<User>(WicketSession.get().getProject().getUsers());
			}
		};
		form.add(new DropDownChoice<User>("user", usersModel).setRequired(true));
		form.add(new TextField<String>("from"));
		form.add(new TextField<String>("to"));
		
		form.add(new Button("generateReport", new ResourceModel("generateReport")) {
			@Override
			public void onSubmit() {
				if(from != null && to != null && from.after(to)) {
					error(getString("invalidDateRange"));
					return;
				} else {
					workedHours = WicketSession.get().getProject().timeWorked(user, from, to);
				}
			}
		});
		
		add(form);
		
		add(new Label("workedHours", new PropertyModel(this,"workedHours")));
	}
}