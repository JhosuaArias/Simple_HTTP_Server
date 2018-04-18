import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private MimeTypes mimeTypes;


    /**
     * Constructor
     */
    public Server() {
        this.mimeTypes = new MimeTypes();
    }

    public void start () throws Exception {
        this.serverSocket = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");
        this.acceptRequests();
    }

    /**
     * Accepts the requests to access the server.
     * @throws Exception Accept the socket.
     */
    public void acceptRequests() throws Exception{
        while (true) {
            Socket client = this.serverSocket.accept();
            ConnectionHandler connectionHandler = new ConnectionHandler(client, this.mimeTypes);
            connectionHandler.start();
        }
    }

}
