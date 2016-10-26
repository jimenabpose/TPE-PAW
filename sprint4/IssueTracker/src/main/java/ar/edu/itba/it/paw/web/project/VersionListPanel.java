package ar.edu.itba.it.paw.web.project;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.exceptions.NotDeletableVersionException;
import ar.edu.itba.it.paw.domain.project.Version;
import ar.edu.itba.it.paw.web.WicketSession;

public class VersionListPanel extends Panel {

	public VersionListPanel(String id, final IModel<List<Version>> versionModel, final boolean showProgress) {
		super(id);
		setDefaultModel(versionModel);
		
		add(new FeedbackPanel("feedback"));
		
		add(new Label("progressTitle", getString("progress")){
			@Override
			public boolean isVisible() {
				return showProgress;
			}
		});

		ListView<Version> versionView = new PropertyListView<Version>("versionList", versionModel){
			@Override
			protected void populateItem(ListItem<Version> item){
				item.add(new Label("name"));
				item.add(new Label("description"));
				item.add(new Label("releaseDate"));
				item.add(new Label("state"));
				item.add(new Label("progress") {
					@Override
					public boolean isVisible() {
						return showProgress;
					}
				});
				item.add(new Link<Version>("edit", item.getModel()) {
					@Override
					public void onClick() {
						setResponsePage(new EditVersionPage(getModelObject()));
					}
					
					@Override
					public boolean isVisible() {
						return WicketSession.get().getUser() != null &&
							WicketSession.get().getUser().equals(WicketSession.get().getProject().getLeader());
					}
				});
				item.add(new Link<Version>("delete", new EntityModel<Version>(Version.class, item.getModelObject())) {
					@Override
					public void onClick() {
						try {
							WicketSession.get().getProject().delete(getModelObject());
						} catch (NotDeletableVersionException e) {
							error(getString("notDeletableVersionException"));
							return;
						}
					}
					
					@Override
					public boolean isVisible() {
						return WicketSession.get().getUser() != null &&
							WicketSession.get().getUser().equals(WicketSession.get().getProject().getLeader());
					}
				});
				item.add(new Link<Version>("view", item.getModel()) {
					@Override
					public void onClick() {
						setResponsePage(new VersionViewPage(getModelObject()));
					}
					
					@Override
					public boolean isVisible() {
						return WicketSession.get().getUser() != null &&
							WicketSession.get().getProject().userBelongs(WicketSession.get().getUser());
					}
				});
			}
		};
		
		add(versionView);
	}
	
}
