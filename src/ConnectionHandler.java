import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler extends Thread {

    private Socket client;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private HttpHandler httpHandler;

    public ConnectionHandler(Socket socket, MimeTypes mimeTypes) throws Exception{
        this.client = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        this.printWriter = new PrintWriter(this.client.getOutputStream());
        this.httpHandler = new HttpHandler(mimeTypes);
    }

    @Override
    public void run() {
        try {
            String requestString = "";
            while(this.bufferedReader.ready() || requestString.length() == 0){
                requestString += (char) this.bufferedReader.read();
            }

            this.httpHandler.handleHttp(requestString);
            String response = httpHandler.createResponse();
            this.printWriter.write(response.toCharArray());

            this.printWriter.close();
            this.bufferedReader.close();
            this.client.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
