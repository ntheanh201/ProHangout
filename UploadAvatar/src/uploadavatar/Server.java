/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uploadavatar;

/**
 *
 * @author ntheanh201
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket serverSocket;
    private static Socket clientSocket = null;

    public static void main(String[] args) throws IOException {

        try {
            serverSocket = new ServerSocket(4444);
            System.out.println("Server started.");
        } catch (Exception e) {
            System.err.println("Port already in use.");
            System.exit(1);
        }

        while (true) {
            try {
                clientSocket = serverSocket.accept();
//                System.out.println(clientSocket.getPort());
//                System.out.println("Accepted connection : " + clientSocket);
                Thread t = new Thread(new CLIENTConnection(clientSocket));
                t.start();

            } catch (Exception e) {
                System.err.println("Error in connection attempt.");
            }
        }
    }
}

