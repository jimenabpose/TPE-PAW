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

public class RegularUserFilter implements Filter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
		throws IOException, ServletException {
		
		String url = ((HttpServletRequest)req).getRequestURL().toString();
		String suburl = url.substring(url.indexOf("/bin/", 0) + 5, url.length());
		User user = null;
		
		List<String> list = new ArrayList<String>();
		list.add("project/create");
		list.add("user/register");
		list.add("user/list");
		
		/*Siempre preguntar si la sesión es null y después si el atributo que busco es null*/
		HttpSession session = ((HttpServletRequest)req).getSession();
		user = (User)session.getAttribute("user");
		
		if (user!= null && !user.isAdmin() && list.contains(suburl)) {
			req.setAttribute("error_message", "Debe ser usuario administrador para realizar la operación");
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
