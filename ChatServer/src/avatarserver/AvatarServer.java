package avatarserver;

/**
 *
 * @author ntheanh201
 */

import utils.PortConfig;

import java.net.ServerSocket;
import java.net.Socket;

public class AvatarServer {

    private static ServerSocket serverSocket;

    public static void main(String[] args) {

        try {
            serverSocket = new ServerSocket(PortConfig.avatarPort);
            System.out.println("Server started.\n" + "AvatarServer using port number = " + PortConfig.avatarPort);
        } catch (Exception e) {
            System.err.println("Port already in use.");
            System.exit(1);
        }

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
//                System.out.println(clientSocket.getPort());
//                System.out.println("Accepted connection : " + clientSocket);
                Thread t = new Thread(new ClientConnection(clientSocket));
                t.start();

            } catch (Exception e) {
                System.err.println("Error in connection attempt.");
            }
        }
    }
}

