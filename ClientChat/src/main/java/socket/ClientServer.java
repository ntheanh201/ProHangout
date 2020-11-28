package socket;

import view.ChatClient;

import java.io.IOException;

public class ClientServer {

    public ClientServer() throws IOException {
        String IPServer = "localhost";
//        String IPServer = "172.104.63.169";
        String[] arguments = new String[] {IPServer};
        new ChatClient().main(arguments);
    }

}