package ar.edu.itba.it.paw.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

public class FaultErrorPage extends WebPage {
	
	
	public FaultErrorPage() {

		add(new Link<Void>("gotoHome") {
			@Override
			public void onClick() {
				setResponsePage(new HomePage());
			}
		});

	}

}
