package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.value.ValueMap;

import ar.edu.itba.it.paw.domain.user.UserSolicitation;
import ar.edu.itba.it.paw.domain.user.UserSolicitationRepo;
import ar.edu.itba.it.paw.web.base.BasePage;

public class UserSolicitationPage extends BasePage {

	private transient String username;
	private transient String captchaText;
	private transient String pass;
	private transient String rep_password;
	private transient String firstName;
	private transient String lastName;

	@SpringBean
	UserSolicitationRepo userSolicitationRepo;

	private final CaptchaImageResource captchaImageResource;
	private final String imagePass = randomString(6, 8);
	private final ValueMap properties = new ValueMap();

	public UserSolicitationPage() {
		captchaImageResource = new CaptchaImageResource(imagePass);

		add(new FeedbackPanel("feedback"));
		Form<UserSolicitationPage> form = new Form<UserSolicitationPage>(
				"form", new CompoundPropertyModel<UserSolicitationPage>(this));
		form.add(new UserInfoPanel("usersInfo"));

		form.add(new Image("captchaImage", captchaImageResource));
		form.add(new RequiredTextField<String>("captchaText",
				new PropertyModel<String>(properties, "captchaText")) {
			@Override
			protected final void onComponentTag(final ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("value", "");
			}
		});
		form.add(new PasswordTextField("pass").setRequired(true));
		form.add(new PasswordTextField("rep_password").setRequired(false));
		form.add(new Button("register", new ResourceModel("register")) {
			@Override
			public void onSubmit() {
				if (!imagePass.equals(getPassword())) {
					error(getString("captchaErrorMessage"));
					return;
				} else {
					userSolicitationRepo.saveSolicitation(new UserSolicitation(
							username, firstName, lastName, pass));
					setResponsePage(getApplication().getHomePage());
					setRedirect(true);
				}
			}
		});
		add(form);
	}

	private static String randomString(int min, int max) {
		int num = randomInt(min, max);
		byte b[] = new byte[num];
		for (int i = 0; i < num; i++)
			b[i] = (byte) randomInt('a', 'z');
		return new String(b);
	}

	private static int randomInt(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}

	private String getPassword() {
		return properties.getString("captchaText");
	}
}
