package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;

public class EditUserPage extends BasePage{

	private transient String username;
	private String password;
	private String rep_password;
	private transient String firstName;
	private transient String lastName;
	
	public EditUserPage(final IModel<User> userModel){
		firstName = userModel.getObject().getFirstName();
		lastName = userModel.getObject().getLastName();
		username = userModel.getObject().getUsername();
		add(new FeedbackPanel("feedback"));
		setDefaultModel(userModel);
		Form<RegisterUserPage> form = new Form<RegisterUserPage>("form", new CompoundPropertyModel<RegisterUserPage>(this));
		form.add(new UserInfoPanel("usersInfo"));
		FormComponent<String> pass = new PasswordTextField("password", new PropertyModel(this, "password")).setRequired(false);
		FormComponent<String> repPass = new PasswordTextField("rep_password", new PropertyModel(this, "rep_password")).setRequired(false);
		form.add(pass);
		form.add(repPass);
		form.add(new EqualPasswordInputValidator(pass, repPass));
		form.add(new Button("register", new ResourceModel("register")){

			@Override
			public void onSubmit() {
				WicketSession.get().getUser().update(username, firstName, lastName, password);
				setResponsePage(new UserProfilePage(userModel));
				setRedirect(true);
			}
		});
		add(form);
	}
}
