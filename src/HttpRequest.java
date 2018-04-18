import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class HttpRequest {

    String filename;
    String host;
    String referer;
    Method method;
    Map<String, String> parameters;
    ArrayList<String> contentTypes;

    /**
     * Costructor
     * @param filename Name of the file
     * @param host Name of the host
     * @param referer Name of the referer
     * @param method Name of the method
     * @param parameters The parameters
     * @param contentTypes Array with the accepted content types
     */
    public HttpRequest(String filename, String host, String referer, Method method, Map<String, String> parameters, ArrayList<String> contentTypes) {
        this.filename = filename;
        this.host = host;
        this.referer = referer;
        this.method = method;
        this.parameters = parameters;
        this.contentTypes = contentTypes;
        try {
            this.writeLog();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Writes on the log.
     * @throws IOException Can't open file.
     */
    public void writeLog() throws IOException{
        Log l = new Log();
        l.WriteLog(this);
    }



}
