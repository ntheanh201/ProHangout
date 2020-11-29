package view;

import utils.ServerIP;

import java.io.IOException;

public class ChatMain {
    public ChatMain() throws IOException {
        String[] arguments = new String[] {ServerIP.server};
        new ChatClient().main(arguments);
    }
}
