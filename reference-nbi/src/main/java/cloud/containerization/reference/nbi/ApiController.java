package cloud.containerization.reference.nbi;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    	Map<Object, Object> parameterMap = parseParameters(decodedQueryString);
    	
    	message.append(" ").append(PASS.toString());
    	message.append(" ").append(this.getClass().getCanonicalName())
    			.append(" queryString: ").append(queryString)
    			.append(" decodedQueryString: ").append(decodedQueryString);
    	
     	Api api = new Api(counter, message.toString());
    	LOG.info(this.getClass().getCanonicalName() + " " + message);
    	return api;
    } 
    
    /**
     * execution=e1s1&action=test = ZXhlY3V0aW9uPWUxczEmYWN0aW9uPXRlc3Q=
     */
    private Map<Object, Object> parseParameters(String decoded) {
    	// split on & delimiter
    	List<String> kvPairs = Stream.of(decoded.split("&"))
				.map(elem -> new String(elem))
				.collect(Collectors.toList());
    	// split on = delimiter
    	Map<Object, Object> map = kvPairs.stream().map(s -> s.split("=")).collect(
    			Collectors.toMap(a -> a[0], a -> a[1]));
    	map.entrySet().stream().forEach(
    			e -> LOG.info("Attribute: " + e.getKey() + "=" + e.getValue()));
    	return map;
    }
}
