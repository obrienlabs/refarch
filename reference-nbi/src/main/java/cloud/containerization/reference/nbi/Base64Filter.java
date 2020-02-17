package cloud.containerization.reference.nbi;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class Base64Filter implements Filter {
	private final static Logger LOG = Logger.getLogger(Base64Filter.class.getName());

	@Override
	public void doFilter(ServletRequest request, javax.servlet.ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        LOG.info("Pre request: " + req.getRequestURI());
  
        chain.doFilter(request, response);
        LOG.info("Post request: " + req.getRequestURI());
	}
}
