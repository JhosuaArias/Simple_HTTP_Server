import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class HttpResponse {

    private HttpRequest request;
    private String strResponse;
    private MimeTypes contentTypes;
    private byte[] response;
    private byte[] headerData;
    private byte[] bodyData;

    /**
     * Constructor
     * @param request The request you want to generate a response to.
     */
    public HttpResponse(HttpRequest request){

        this.contentTypes = new MimeTypes();
        this.request = request;
        this.GenerateResponse();
    }

    public String getStrResponse(){
        return this.strResponse;
    }

    /**
     * Generates the response based on the method.
     */
    private void GenerateResponse () {
        switch (this.request.method){
            case GET:
                this.HandleMethod(true);
                break;
            case POST:
                this.HandleMethod(true);
                break;
            case UNKWOWN:
                this.HandleUnknown();
                break;
            case HEAD:
                this.HandleMethod(false);
                break;
        }
    }

    /**
     * Handles unknown methods.
     */
    private void HandleUnknown(){
        this.strResponse = "HTTP/1.1 501 \r\n"; //HTTP Version and Status
        this.strResponse += "Date: " + this.getServerTime() + "\r\n";
        this.strResponse += "Server: TP1/1.0 \r\n"; //Server ID
        this.strResponse += "Connection: close \r\n";
        this.strResponse += "\r\n";

        this.response = strResponse.getBytes();
    }

    /**
     * Handles HEAD, POST, and GET methods.
     * @param body If the method is POST or GET.
     */
    private void HandleMethod(boolean body){
        File file = new File(this.request.filename);

        //Basic response.
        this.strResponse = "HTTP/1.1 status \r\n"; //HTTP Version and Status
        this.strResponse += "Date: " + this.getServerTime() + "\r\n";
        this.strResponse += "Server: TP1/1.0 \r\n"; //Server ID
        this.strResponse += "Content-Length: " + String.valueOf(file.length()) + " \r\n"; //Length of the content
        this.strResponse += "Content-Type: " + this.getContentType(this.request.filename) + " \r\n"; //Content type
        this.strResponse += "Connection: close \r\n";
        this.strResponse += "\r\n";

        //If the accept types match the requested type.
        if(this.request.contentTypes.contains(this.getContentType(this.request.filename)) || this.request.contentTypes.contains("*/*")){
            try{
                FileInputStream fileInputStream = new FileInputStream(file);
                if (body) {
                    Path path = Paths.get(this.request.filename);
                    bodyData = Files.readAllBytes(path);
                }

                this.strResponse = this.strResponse.replace("status", "200 OK");

            } catch (FileNotFoundException e) {

                this.strResponse = this.strResponse.replace("status", "404");

            } catch (Exception e) {

                this.strResponse = this.strResponse.replace("status", "500");

            }
        } else {
            this.strResponse = this.strResponse.replace("status", "406");
        }

        //Created the response
        headerData = strResponse.getBytes();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        try {
            outputStream.write(headerData);
            if(bodyData != null) {
                outputStream.write(bodyData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        response = outputStream.toByteArray();
    }

    /**
     * Gets the content type of a file
     * @param fileName String with the file's name.
     * @return String with the content type.
     */
    private String getContentType(String fileName) {
        String [] format = fileName.split("\\.");
        return this.contentTypes.getMimeType(format[1]);
    }

    /**
     * Generates a string with the current time
     * @return String with the current time.
     */
    private String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }

    /**
     * Returns the response.
     * @return The response.
     */
    public byte[] getResponse() {
        return response;
    }
}
