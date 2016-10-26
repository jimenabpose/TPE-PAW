package ar.edu.itba.it.paw.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filtro que captura excepciones y muestra una página de error.
 */
@Component
public class ErrorFilter extends GenericFilterBean {

	@Override
	public void destroy() {
		// Do nothing
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		
		try {
			chain.doFilter(req, resp);
		} catch (Exception e) {
			req.setAttribute("error_message", "Ha ocurrido un error");
			req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
		}
	}
}
