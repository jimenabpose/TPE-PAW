package ar.edu.itba.it.paw.web.project;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import ar.edu.itba.it.paw.domain.project.VersionState;
import ar.edu.itba.it.paw.domain.user.UserRepo;

public class VersionInfoPanel extends Panel {

	@SpringBean
	private UserRepo userRepo;

	public VersionInfoPanel(String id, final boolean stateIsEditable) {
		super(id);

		add(new TextField<String>("name").setRequired(true));
		add(new DateTextField("releaseDate", null, new PatternDateConverter("dd/MM/yyyy", false)).setRequired(true));
		add(new TextField<String>("description").add(new IValidator<String>() {
			@Override
			public void validate(IValidatable<String> validatable) {
				if(validatable.getValue().length() >= 256) {
					validatable.error(new ValidationError().addMessageKey("description_error"));
				}
			}
		}));
		
		IModel<List<VersionState>> versionStateModel = new LoadableDetachableModel<List<VersionState>>() {
			@Override
			protected List<VersionState> load() {
				List<VersionState> states = new ArrayList<VersionState>();
				for(VersionState s : VersionState.values()) {
					states.add(s);
				}
				return states;
			}
		};
		add(new DropDownChoice<VersionState>("state", versionStateModel) {
			@Override
			public boolean isVisible() {
				return !stateIsEditable;
			};
		});
		add(new Label("stateInfo", VersionState.OPEN.getName()) {
			@Override
			public boolean isVisible() {
				return stateIsEditable;
			}
		});

	}
}
