import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private HttpHandler httpHandler;

    public Server() {
        this.httpHandler = new HttpHandler();
    }

    public void start () throws Exception {
        final ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");
        while (true) {
            final Socket client = server.accept();
            InputStreamReader isr = new InputStreamReader(client.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while ( line != null && !line.isEmpty()) {
                stringBuilder.append(line + "\n");
                //System.out.println(line);
                line = reader.readLine();
            }
            System.out.println(stringBuilder.toString());
            httpHandler.handleHttp(stringBuilder.toString());
            //client.close();

        }
    }

}
