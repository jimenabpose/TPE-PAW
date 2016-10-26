package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.exceptions.RegisteredUsernameException;
import ar.edu.itba.it.paw.domain.user.Type;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.domain.user.UserRepo;
import ar.edu.itba.it.paw.web.base.BasePage;


public class RegisterUserPage extends BasePage{

	private transient String username;
	private transient String password;
	private transient String rep_password;
	private transient String firstName;
	private transient String lastName;
	
	@SpringBean
	private UserRepo userRepo;
	
	public RegisterUserPage(){
		add(new FeedbackPanel("feedback"));
		Form<RegisterUserPage> form = new Form<RegisterUserPage>("form", new CompoundPropertyModel<RegisterUserPage>(this));
		form.add(new UserInfoPanel("usersInfo"));
		
		FormComponent<String> pass = new PasswordTextField("password").setRequired(true);
		FormComponent<String> repPass = new PasswordTextField("rep_password").setRequired(false);
		form.add(pass);
		form.add(repPass);
		form.add(new EqualPasswordInputValidator(pass, repPass));
		form.add(new Button("register", new ResourceModel("register")){
			@Override
			public void onSubmit() {
				User user = new User(username, firstName, lastName, password, Type.REGULAR, true);
				try{
					userRepo.saveUser(user);
					setResponsePage(new ConfirmationMessagePage());
					setRedirect(true);
				} catch(RegisteredUsernameException e){
					error(getString("registeredUsernameException"));
					return;
				}
			}
		});
		add(form);
	}
}
