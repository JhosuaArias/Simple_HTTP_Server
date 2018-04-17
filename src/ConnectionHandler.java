import java.io.*;
import java.net.Socket;

public class ConnectionHandler extends Thread {

    private Socket client;
    private OutputStream printWriter;
    private BufferedReader bufferedReader;
    private HttpHandler httpHandler;

    public ConnectionHandler(Socket socket, MimeTypes mimeTypes) throws Exception{
        this.client = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        this.printWriter = this.client.getOutputStream();
        this.httpHandler = new HttpHandler(mimeTypes);
    }

    @Override
    public void run() {
        try {
            String requestString = "";
            while(this.bufferedReader.ready() || requestString.length() == 0){
                requestString += (char) this.bufferedReader.read();
            }
            System.out.println(requestString);
            this.httpHandler.handleHttp(requestString);
            byte[] response = httpHandler.createResponse();
            System.out.println(response);
            this.printWriter.write(response);

            this.printWriter.close();
            this.bufferedReader.close();
            this.client.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
