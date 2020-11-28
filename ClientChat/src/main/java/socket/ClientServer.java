package socket;

import utils.ServerIP;
import view.ChatClient;

import java.io.IOException;

public class ClientServer {

    public ClientServer() throws IOException {
        String[] arguments = new String[] {ServerIP.server};
        new ChatClient().main(arguments);
    }

}