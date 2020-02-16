package cloud.dev9.reference.nbi;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
// http://localhost:8080/nbi/api
public class ApiController {

    private final AtomicLong counter = new AtomicLong();
	private static final CharSequence pass = "PASS";
	private static final CharSequence fail = "FAIL";
    
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody Api process(@RequestParam(value="action", required=true, defaultValue="undefined") String action) {
    	String message = "pass";
    	System.out.println(this.getClass().getCanonicalName());
    	// external call
    	//message = externalAPICall(action);
    	Api api = new Api(counter.incrementAndGet(), message);
    	return api;
    } 
    
}
