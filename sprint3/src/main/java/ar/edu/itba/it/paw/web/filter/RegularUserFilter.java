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

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.repo.UserRepo;

@Component
public class RegularUserFilter extends GenericFilterBean {

	private UserRepo userRepo;
	
	@Autowired
	public RegularUserFilter(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
		throws IOException, ServletException {
		
		String url = ((HttpServletRequest)req).getRequestURL().toString();
		String suburl = url.substring(url.indexOf("/bin/", 0) + 5, url.length());
		User user = null;
		
		List<String> list = new ArrayList<String>();
		list.add("project/create");
		list.add("project/edit");
		list.add("user/register");
		list.add("user/list");
		list.add("user/administrate");
		list.add("user/accept");
		list.add("user/decline");
		list.add("user/delete");
		
		/*Siempre preguntar si la sesión es null y después si el atributo que busco es null*/
		HttpSession session = ((HttpServletRequest)req).getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		if(userId != null)
			user = userRepo.getUser(userId);
		
		if (user!= null && !user.isAdmin() && list.contains(suburl)) {
			req.setAttribute("error_message", "Debe ser usuario administrador para realizar la operación");
			((HttpServletRequest)req).getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
		} else {
			chain.doFilter(req, resp);
		}
	}
	
	@Override
	public void destroy() {}
	
}
