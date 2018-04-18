import java.io.*;
import java.net.Socket;

public class ConnectionHandler extends Thread {

    private Socket client;
    private OutputStream printWriter;
    private BufferedReader bufferedReader;
    private HttpHandler httpHandler;

    /**
     * Constructor
     * @param socket the clients socket
     * @param mimeTypes One instance of Mimetypes object for all connections.
     * @throws Exception IO exception.
     */
    public ConnectionHandler(Socket socket, MimeTypes mimeTypes) throws Exception{
        this.client = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        this.printWriter = this.client.getOutputStream();
        this.httpHandler = new HttpHandler(mimeTypes);
    }

    /**
     * Method run, which is execute by each new thread. Creates an HTTP handler to handle its request.
     */
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
            this.printWriter.write(response);

            this.printWriter.close();
            this.bufferedReader.close();
            this.client.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
