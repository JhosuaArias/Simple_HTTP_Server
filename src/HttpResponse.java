import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class HttpResponse {

    private HttpRequest request;
    private String response;
    private MimeTypes contentTypes;

    public HttpResponse(HttpRequest request){

        this.contentTypes = new MimeTypes();
        this.request = request;
        this.GenerateResponse();
    }

    public String getResponse(){
        return this.response;
    }

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

    private void HandleUnknown(){
        this.response = "HTTP/1.1 501 \r\n"; //HTTP Version and Status
        this.response += "Date: " + this.getServerTime() + "\r\n";
        this.response += "Server: TP1/1.0 \r\n"; //Server ID
        //this.response += "Connection: close \r\n";
        this.response += "\r\n";
    }

    private void HandleMethod(boolean body){
        File file = new File(this.request.filename);

        this.response = "HTTP/1.1 status \r\n"; //HTTP Version and Status
        this.response += "Date: " + this.getServerTime() + "\r\n";
        this.response += "Server: TP1/1.0 \r\n"; //Server ID
        this.response += "Content-Length: " + String.valueOf(file.length()) + " \r\n"; //Length of the content
        this.response += "Content-Type: " + this.getContentType(this.request.filename) + " \r\n"; //Content type
        //this.response += "Connection: close \r\n";
        this.response += "\r\n";

        try{
            FileInputStream fileInputStream = new FileInputStream(file);

            if (body) {
                int endOfFile = fileInputStream.read();
                while (endOfFile != -1){
                    response += (char) endOfFile;
                    endOfFile = fileInputStream.read();
                }
            }

            this.response = this.response.replace("status", "200 OK");

        } catch (FileNotFoundException e) {

            this.response = this.response.replace("status", "404");

        } catch (Exception e) {

            this.response = this.response.replace("status", "500");

        }
    }

    private String getContentType(String fileName) {
        String [] format = fileName.split("\\.");
        return this.contentTypes.getMimeType(format[1]);
    }

    private String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }
}
