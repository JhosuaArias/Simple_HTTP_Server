import java.util.ArrayList;
import java.util.HashMap;

public class HttpHandler {

    private MimeTypes mimeTypes;
    private HttpRequest httpRequest;

    /**
     * Constructor of HttpHandler that defines a MimeTypes Object.
     * @param mimeTypes
     */
    public HttpHandler(MimeTypes mimeTypes) {
        this.mimeTypes = mimeTypes;
    }

    /**
     * This Method process a HTTP request, splits the request and create a HTTPRequest Object with the data.
     * @param message
     */
    public void handleHttp(String message) {

        HashMap<String, String> hashHeaders = new HashMap<>();
        HashMap<String, String> hashParameters = new HashMap<>();
        String splitMessage[] = message.split("\n");
        String splitFirstLine[] = splitMessage[0].split("\\s+");
        String method = splitFirstLine[0];
        String parameters[];
        String filename = "";

        for (int i = 1; i < splitMessage.length; i++) {
            String splitHeader[] = splitMessage[i].split(":");
            try {
                hashHeaders.put(splitHeader[0].trim(), splitHeader[1].trim());
            } catch (Exception ignored) {
            }
        }

        String referer = hashHeaders.get("Referer");
        if (referer == null) {
            referer = "";
        }

        String acceptContentTypes = hashHeaders.get("Accept");
        ArrayList<String> contentTypes;
        contentTypes = new ArrayList<>();
        if(acceptContentTypes == null){
            acceptContentTypes= "";
        } else {
            String[] splitContents = acceptContentTypes.split(",");
            for (String element: splitContents) {
                contentTypes.add(element);
            }
        }

        if (method.equalsIgnoreCase("GET") || method.equalsIgnoreCase("HEAD")) { //Como eran lo mismo
            String splitUrl[] = splitFirstLine[1].split("\\?");
            filename = splitUrl[0];

            if(filename.equalsIgnoreCase("/")){
                filename = "index.html";
            }else {
                filename = filename.substring(1,filename.length());
            }

            if (splitUrl.length == 2) {
                parameters = splitUrl[1].split("&");

                for (int i = 0; i < parameters.length; i++) {
                    String parameter[] = parameters[i].split("=");
                    hashParameters.put(parameter[0],parameter[1]);
                }
            }
            if (method.equalsIgnoreCase("GET")) {
                httpRequest = new HttpRequest(filename, hashHeaders.get("Host"), referer, Method.GET, hashParameters,contentTypes);
            } else {
                httpRequest = new HttpRequest(filename, hashHeaders.get("Host"), referer, Method.HEAD, hashParameters,contentTypes);
            }
        } else {

            if (method.equalsIgnoreCase("POST")) {
                String splitUrl[] = splitFirstLine[1].split("\\?");
                filename = splitUrl[0];
                if(filename.equalsIgnoreCase("/")){
                    filename = "index.html";
                }else {
                    filename = filename.substring(1,filename.length());
                }
                parameters = splitMessage[splitMessage.length-1].split("&");
                for (int i = 0; i < parameters.length; i++) {
                    String parameter[] = parameters[i].split("=");
                    hashParameters.put(parameter[0],parameter[1]);
                }
                httpRequest = new HttpRequest(filename, hashHeaders.get("Host"), referer, Method.POST, hashParameters,contentTypes);
            } else {
                httpRequest = new HttpRequest(filename, hashHeaders.get("Host"), referer, Method.UNKWOWN, hashParameters,contentTypes);
            }
        }

    }

    /**
     * When the response is done, this method returns a bytes array with the response
     * @return the response of the HTTP request.
     */
    public byte[] createResponse() {
        HttpResponse httpResponse = new HttpResponse(this.httpRequest);
        return httpResponse.getResponse();
    }

}