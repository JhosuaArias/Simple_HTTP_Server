import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Controller {

    Server server;

    /**
     * Constructor Initialize the server
     */
    public Controller(){

        this.server = new Server();

    }

    /**
     * Init the server
     * @throws Exception
     */
    private void init () throws Exception{

        this.server.start();

    }

    /**
     * Main
     * @param args Usually no args.
     * @throws Exception Connection, IO Exception
     */
    public static void main(String[] args) throws Exception {

        Controller controller = new Controller();
        controller.init();

    }
}
