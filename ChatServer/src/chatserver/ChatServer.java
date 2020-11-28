package chatserver;

import utils.PortConfig;

import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class ChatServer {
    private static ServerSocket serverSocket = null;

    private static final int maxClientsCount = 100;
    private static final clientThread[] threads = new clientThread[maxClientsCount];

    public static void main(String[] args) {

        int portNumber = PortConfig.chatPort;
        if (args.length < 1) {
            System.out.println("Usage: java MultiThreadChatServerSync <portNumber>\n"
                    + "Now using port number = " + portNumber);
        } else {
            portNumber = Integer.parseInt(args[0]);
        }

        /*
         * Open a server socket on the portNumber default 2222
         */
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException | NullPointerException e) {
            System.out.println(e);
        }

        /*
         * Create a client socket for each connection and pass it to a new client
         * thread.
         */
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                int i = 0;
                for (i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == null) {
                        (threads[i] = new clientThread(clientSocket, threads)).start();
                        break;
                    }
                }
                if (i == maxClientsCount) {
                    PrintStream os = new PrintStream(clientSocket.getOutputStream());
                    os.println("Server too busy. Try later.");
                    os.close();
                    clientSocket.close();
                }
            } catch (IOException | NullPointerException e) {
                System.out.println(e);
            }
        }
    }
}