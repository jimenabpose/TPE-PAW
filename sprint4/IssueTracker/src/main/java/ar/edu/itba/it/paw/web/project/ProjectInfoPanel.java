package ar.edu.itba.it.paw.web.project;

import java.util.List;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.domain.user.UserRepo;

public class ProjectInfoPanel extends Panel {

	@SpringBean
	private UserRepo userRepo;

	public ProjectInfoPanel(String id) {
		super(id);

		add(new TextField<String>("name").setRequired(true));
		add(new TextField<String>("code").setRequired(true));
		add(new TextField<String>("description").add(new IValidator<String>() {
			@Override
			public void validate(IValidatable<String> validatable) {
				if(validatable.getValue().length() >= 256) {
					validatable.error(new ValidationError().addMessageKey("descriptionError"));
				}
			}
		}));
		
		IModel<List<User>> usersModel = new LoadableDetachableModel<List<User>>() {
			@Override
			protected List<User> load() {
				return userRepo.getActiveUsers();
			}
		};
		add(new DropDownChoice<User>("leader", usersModel).setRequired(true));

		add(new CheckBox("isPublic"));
	}
}
