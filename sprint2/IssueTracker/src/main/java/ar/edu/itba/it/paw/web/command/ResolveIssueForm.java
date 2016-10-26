package ar.edu.itba.it.paw.web.command;

import ar.edu.itba.it.paw.domain.Resolution;

public class ResolveIssueForm {

		private Resolution resolution;
		private int id;
		
		public void setResolution(Resolution resolution) {
			this.resolution = resolution;
		}

		public Resolution getResolution() {
			return resolution;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

}
