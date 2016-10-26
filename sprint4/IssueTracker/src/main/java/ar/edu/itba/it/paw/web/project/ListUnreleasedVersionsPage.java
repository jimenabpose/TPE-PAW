package ar.edu.itba.it.paw.web.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ar.edu.itba.it.paw.domain.project.Version;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;

public class ListUnreleasedVersionsPage extends BasePage{

	public ListUnreleasedVersionsPage() {
		addVersionsList();
		addUnreleasedVersionsLink();
	}
	
	private void addVersionsList() {
		IModel<List<Version>> versionModel = new LoadableDetachableModel<List<Version>>(){
			@Override
			protected List<Version> load(){
				List<Version> versions = new ArrayList<Version>(WicketSession.get().getProject().getUnreleasedVersions());
				Collections.sort(versions, new Comparator<Version>(){

					@Override
					public int compare(Version version1, Version version2) {
						if(version1.getReleaseDate().after(version2.getReleaseDate()))
								return -1;
						else if(version1.getReleaseDate().before(version2.getReleaseDate()))
							return 1;
						else
							return 0;
					}

				});
				
				return versions;
			}
		};
		add(new VersionListPanel("versionList", versionModel, true));
	}
	
	private void addUnreleasedVersionsLink() {
		add(new Link<Void>("listAll") {
			@Override
			public void onClick() {
				setResponsePage(new ListVersionsPage());
			}
		});
	}
}
