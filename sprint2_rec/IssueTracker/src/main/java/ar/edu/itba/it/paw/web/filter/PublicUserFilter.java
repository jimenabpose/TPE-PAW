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

import ar.edu.itba.it.paw.domain.User;

public class PublicUserFilter implements Filter{

		@Override
		public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
			
			String url = ((HttpServletRequest)req).getRequestURL().toString();
			String suburl = url.substring(url.indexOf("/bin/", 0) + 5, url.length());
			User user = null;
			List<String> list = new ArrayList<String>();
			list.add("issue/list");
			list.add("project/view");
			list.add("project/select");
			list.add("user/index");
			list.add("user/logged_index");
			list.add("issue/listPopular");
			
			/*Siempre preguntar si la sesión es null y después si el atributo que busco es null*/
			HttpSession session = ((HttpServletRequest)req).getSession();
			if(session != null && session.getAttribute("user")!= null){
				user = (User)session.getAttribute("user");
			}
			
			/*Si no es un usuario registrado y las url son las de arriba puede acceder*/
			if (user == null && (list.contains(suburl) || url.contains("issue/view"))) {
				chain.doFilter(req, resp);
			/*Si es un usuario registrado puede acceder a todo*/
			}else if(user != null){
				chain.doFilter(req, resp);
			}
			/*En otro caso da error*/
			else {
				req.setAttribute("error_message", "Debe loguearse para realizar la operación");
				((HttpServletRequest)req).getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
			}
		}
		
		@Override
		public void destroy() {}
		
		@Override
		public void init(FilterConfig arg0) throws ServletException {}
		
}
