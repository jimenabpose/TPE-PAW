package ar.edu.itba.it.paw.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;

public class NotLeaderFilter implements Filter{

		@Override
		public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
			
			String url = ((HttpServletRequest)req).getRequestURL().toString();
			String suburl = url.substring(url.indexOf("/bin/", 0) + 5, url.length());
			User user = null;
			Project project = null;
			
			List<String> list = new ArrayList<String>();
			list.add("project/consultState");
			list.add("project/listUsers");
			
			/*Siempre preguntar si la sesión es null y después si el atributo que busco es null*/
			HttpSession session = ((HttpServletRequest)req).getSession();
			user = (User)session.getAttribute("user");
			project = (Project)session.getAttribute("project");
			
			/*Si no es el lider del proyecto y son las url de arriba entonces no puede acceder*/
			if (project!= null && !project.getLeader().equals(user) && list.contains(suburl)) {
				req.setAttribute("error_message", "Debe ser usuario lider para realizar la operación");
				((HttpServletRequest)req).getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
			} else {
				chain.doFilter(req, resp);
			}
		}
		
		@Override
		public void destroy() {}
		
		@Override
		public void init(FilterConfig arg0) throws ServletException {}
}
