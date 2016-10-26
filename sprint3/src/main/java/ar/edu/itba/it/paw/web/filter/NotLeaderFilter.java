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
public class NotLeaderFilter extends GenericFilterBean {

	private UserRepo userRepo;
	private ProjectRepo projectRepo;

	@Autowired
	public NotLeaderFilter(UserRepo userRepo, ProjectRepo projectRepo) {
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
		Project project = null;

		List<String> list = new ArrayList<String>();
		list.add("project/consultState");
		list.add("project/listUsers");
		list.add("project/createVersion");
		list.add("project/editVersion");
		list.add("project/deleteVersion");
		list.add("user/administrate");
		list.add("user/accept");
		list.add("user/decline");
		list.add("project/selectUser");
		list.add("project/deleteUser");
		list.add("issue/close");

		/*
		 * Siempre preguntar si la sesión es null y después si el atributo que
		 * busco es null
		 */
		HttpSession session = ((HttpServletRequest) req).getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		if(userId != null)
			user = userRepo.getUser(userId);
		Integer projectId = (Integer) session.getAttribute("projectId");
		if(projectId != null)
			project = projectRepo.getProjectById(projectId);

		/*
		 * Si no es el lider del proyecto y son las url de arriba entonces no
		 * puede acceder
		 */
		if (project != null && !project.getLeader().equals(user)
				&& list.contains(suburl)) {
			req.setAttribute("error_message",
					"Debe ser usuario lider para realizar la operación");
			((HttpServletRequest) req).getRequestDispatcher(
					"/WEB-INF/jsp/error.jsp").forward(req, resp);
		} else {
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void destroy() {
	}

}
