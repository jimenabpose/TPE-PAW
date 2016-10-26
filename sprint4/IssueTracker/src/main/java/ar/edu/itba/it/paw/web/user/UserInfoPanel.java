package ar.edu.itba.it.paw.web.user;


import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;

public class UserInfoPanel extends Panel{
	
	public UserInfoPanel(String id){
		super(id);
		
		add(new TextField<String>("username").setRequired(true));
		add(new TextField<String>("firstName").setRequired(true));
		add(new TextField<String>("lastName").setRequired(true));
	}
}