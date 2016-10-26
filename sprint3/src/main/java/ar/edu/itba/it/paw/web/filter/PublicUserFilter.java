package ar.edu.itba.it.paw.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.repo.ProjectRepo;
import ar.edu.itba.it.paw.repo.UserRepo;

@Component
public class PublicUserFilter extends GenericFilterBean {

	private UserRepo userRepo;
	private ProjectRepo projectRepo;

	@Autowired
	public PublicUserFilter(UserRepo userRepo, ProjectRepo projectRepo) {
		this.userRepo = userRepo;
		this.projectRepo = projectRepo;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		String url = ((HttpServletRequest) req).getRequestURL().toString();
		String suburl = url
				.substring(url.indexOf("/bin/", 0) + 5, url.length());
		User user = null;
		List<String> list = new ArrayList<String>();
		list.add("issue/list");
		list.add("project/view");
		list.add("project/select");
		list.add("user/index");
		list.add("user/logged_index");
		list.add("project/listVersions");
		list.add("project/listUnreleasedVersions");
		list.add("user/logout");
		list.add("user/register");
		

		/*
		 * Siempre preguntar si la sesión es null y después si el atributo que
		 * busco es null
		 */
		HttpSession session = ((HttpServletRequest) req).getSession();
		if (session != null && session.getAttribute("userId") != null) {
			user = userRepo.getUser((Integer) session.getAttribute("userId"));
		}

		/*
		 * Si no es un usuario registrado y las url son las de arriba puede
		 * acceder
		 */
		if (user == null
				&& (list.contains(suburl) || url.contains("issue/view"))) {
			chain.doFilter(req, resp);
			/* Si es un usuario registrado puede acceder a todo */
		} else if (user != null) {
			if(session.getAttribute("projectId") != null){
			Project project = projectRepo.getProjectById((Integer)session.getAttribute("projectId"));
			
				if(project.isPublic()){
					/*si el usuario no pertenece al proyecto y la url no está permitida*/
					if((!user.isAdmin() && !project.getUsers().contains(user)) && ! (list.contains(suburl) ||
							url.contains("issue/view") || url.contains("issue/addVoter") ||
							url.contains("issue/removeVoter"))){
	
						req.setAttribute("error_message", "Debe pertenecer al proyecto " +
							"para realizar la operacion");
						((HttpServletRequest) req).getRequestDispatcher("/WEB-INF/jsp/error.jsp").
							forward(req, resp);
						return;
					}
				}
			}
			chain.doFilter(req, resp);
		}
		/* En otro caso da error */
		else {
			req.setAttribute("error_message",
					"Debe loguearse para realizar la operación");
			((HttpServletRequest) req).getRequestDispatcher(
					"/WEB-INF/jsp/error.jsp").forward(req, resp);
		}
	}

	@Override
	public void destroy() {
	}

}
