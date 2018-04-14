import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private MimeTypes mimeTypes;


    public Server() {
        this.mimeTypes = new MimeTypes();
    }

    public void start () throws Exception {
        this.serverSocket = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");
        this.acceptRequests();
    }

    public void acceptRequests() throws Exception{
        while (true) {
            Socket client = this.serverSocket.accept();
            ConnectionHandler connectionHandler = new ConnectionHandler(client, this.mimeTypes);
            connectionHandler.start();
        }
    }

}
