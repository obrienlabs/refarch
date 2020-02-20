package cloud.containerization.reference.nbi;

import java.io.IOException;
import java.util.Base64;
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
		// Note: the query string does not include the anchor hash (clent) content
		String queryString = req.getQueryString();
        LOG.info("Pre request + querystring: " + req.getRequestURI() + "?" + queryString);
        //LOG.info(encode(queryString));
        // decode base64 encoded parameters high in the chain
        String decoded = decode(queryString);
        LOG.info("decoded: " + decoded);
        
        // 3 solutions to emulate a setQueryParameter() call (wrapping request, request attributes, or custom parameters)
        req.setAttribute("qs", decoded);
        chain.doFilter(request, response);
        LOG.info("Post request: " + req.getRequestURI());
	}
	
	
	/**
	 * execution=e1s1 = ZXhlY3V0aW9uPWUxczE= 
	 */
	private String encode(String url) {
		return Base64.getUrlEncoder().encodeToString(url.getBytes());
	}
	
	private String decode(String url) {
		if(null != url) {
			byte[] decodedBytes = Base64.getUrlDecoder().decode(url);
			return new String(decodedBytes);
		} else {
			return "";
		}
	}
}
