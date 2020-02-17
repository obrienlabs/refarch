package cloud.containerization.reference.nbi;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
// http://localhost:8080/nbi/api
public class ApiController {
	
	private final static Logger LOG = Logger.getLogger(ApiController.class.getName());

    private final AtomicLong counter = new AtomicLong();
	private static final CharSequence PASS = "PASS";
    
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody Api process(
    		@RequestParam(value="action", required=true, defaultValue="undefined") String action,
    		HttpServletRequest request) {
    	StringBuffer message = new StringBuffer(String.valueOf(counter.incrementAndGet()));
    	// expecting non-base64 query string from the filter chain before this call
    	String queryString = request.getQueryString();
    	String decodedQueryString = (String)request.getAttribute("qs");
    	LOG.info("queryString decoded: " + decodedQueryString);
    	
    	message.append(" ").append(PASS.toString());
    	message.append(" ").append(this.getClass().getCanonicalName())
    			//.append(" remoteAddr: ").append(request.getRemoteAddr())
    			//.append(" localAddr: ").append(request.getLocalAddr())
    			//.append(" remoteHost: ").append(request.getRemoteHost())
    			//.append(" serverName: ").append(request.getServerName())
    			.append(" queryString: ").append(queryString)
    			.append(" decodedQueryString: ").append(decodedQueryString);
    	
     	Api api = new Api(counter, message.toString());
    	LOG.info(this.getClass().getCanonicalName() + " " + message);
    	return api;
    } 
    
}
