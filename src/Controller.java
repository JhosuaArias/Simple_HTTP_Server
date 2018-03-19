import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Controller {

    Server server;
    Terminal terminal;

    public Controller(){

        this.server = new Server();
        this.terminal = new Terminal();

    }

    private void init () throws Exception{

        this.server.start();

    }

    public static void main(String[] args) throws Exception {

        Controller controller = new Controller();
        controller.init();

    }
}
