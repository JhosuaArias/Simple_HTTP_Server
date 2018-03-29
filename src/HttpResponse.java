import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HttpResponse {

    private HttpRequest request;
    private String response;
    private String serverPath;

    public HttpResponse(HttpRequest request){

        this.request = request;
        this.GenerateResponse();
    }

    public String getResponse(){
        return this.response;
    }

    private void GenerateResponse () {
        switch (this.request.method){
            case GET:
                this.HandleGet();
                break;
            case POST:
                this.HandlePost();
                break;
            case PUT:
                break;
            case HEAD:
                break;
        }
    }

    private void HandleGet(){
        File file = new File(this.serverPath + this.request.filename);

        this.response = "HTTP/1.0 status \r\n"; //HTTP Version and Status
        this.response += "Server: TP1 \r\n"; //Server ID
        this.response += "Content-type: " + this.getContentType(this.request.filename) + " \r\n"; //Content type
        this.response += "Connection: close \r\n";
        this.response += "Content-length: " + String.valueOf(file.length()) + " \r\n"; //Length of the content
        this.response += "\r\n";

        try{
            FileInputStream fileInputStream = new FileInputStream(file);

            int endOfFile = fileInputStream.read();
            while (endOfFile != -1){
                response += (char) endOfFile;
                endOfFile = fileInputStream.read();
            }

            this.response = this.response.replace("status", "200");

        } catch (FileNotFoundException e) {

            this.response = this.response.replace("status", "404");

        } catch (Exception e) {

            this.response = this.response.replace("status", "500");

        }
    }

    private void HandlePost(){

    }

    private String getContentType(String fileName) {
        if (fileName.endsWith(".htm") || fileName.endsWith(".html")
                || fileName.endsWith(".txt")) {
            return "text/html";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".gif")) {
            return "image/gif";
        } else {
            return "application/octet-stream";
        }
    }
}
