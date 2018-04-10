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
    }



}
