package ar.edu.itba.it.paw.web.command;

import ar.edu.itba.it.paw.domain.User;

public class SelectUsersForm {

		private User[] usuarios;

		public void setUsuarios(User[] usuarios) {
			this.usuarios = usuarios;
		}

		public User[] getUsuarios() {
			return usuarios;
		}
}
