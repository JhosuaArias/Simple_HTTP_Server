import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.util.Map;

public class HttpRequest {

    String filename;
    String host;
    String referer;
    Method method;
    Map<String, String> parameters;


    public HttpRequest(String filename, String host, String referer, Method method, Map<String, String> parameters) {
        this.filename = filename;
        this.host = host;
        this.referer = referer;
        this.method = method;
        this.parameters = parameters;
        try {
            this.writeLog();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeLog() throws IOException{
        Log l = new Log();
        l.WriteLog(this);
    }



}
