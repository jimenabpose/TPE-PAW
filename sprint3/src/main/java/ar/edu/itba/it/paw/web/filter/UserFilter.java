package ar.edu.itba.it.paw.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.repo.ProjectRepo;
import ar.edu.itba.it.paw.repo.UserRepo;

@Component
public class UserFilter extends GenericFilterBean {

	private UserRepo userRepo;
	private ProjectRepo projectRepo;
	
	@Autowired
	public UserFilter(UserRepo userRepo, ProjectRepo projectRepo) {
		this.userRepo = userRepo;
		this.projectRepo = projectRepo;
	}
	
	public void destroy() {
		// Do nothing
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		String url = ((HttpServletRequest)req).getRequestURL().toString();
		String suburl = url.substring(url.indexOf("/bin/", 0) + 5, url.length());
		
		List<String> list = new ArrayList<String>();
		list.add("user/index");
		list.add("project/select");
		list.add("project/create");
		list.add("user/administrate");
		list.add("user/accept");
		list.add("user/decline");
		list.add("user/register");
		
		Project project = null;
		HttpSession session = ((HttpServletRequest)req).getSession();
		if(session != null && session.getAttribute("projectId") != null){
			project = projectRepo.getProjectById((Integer)session.getAttribute("projectId"));
			req.setAttribute("project", project);
		}
		User user = null;
		if(session != null && session.getAttribute("userId") != null) {
			user = userRepo.getUser((Integer) session.getAttribute("userId"));
			req.setAttribute("user", user);
		}
		
		if(project == null && !list.contains(suburl) ){
			((HttpServletResponse)resp).sendRedirect(getServletContext().getContextPath() + "/bin/user/index");
		}
		else{
			chain.doFilter(req, resp);
		}

	}


}
