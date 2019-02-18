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

import java.io.*;
import java.net.Socket;

public class CLIENTConnection implements Runnable {

    private Socket clientSocket;
    private DataInputStream is = null;
    private BufferedReader in = null;
    private String clientSelection;

    public CLIENTConnection(Socket client) {
        this.clientSocket = client;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientSelection = in.readLine();
            System.out.println(clientSelection);
            receiveFile();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveFile() {
        try {
            int bytesRead;
            DataInputStream clientData = new DataInputStream(clientSocket.getInputStream());
            String fileName = clientData.readUTF();
                OutputStream output = new FileOutputStream(("/var/www/html/theanhdz/images/" + clientSelection + ".png"));
//            OutputStream output = new FileOutputStream(("/Users/ntheanh201/Desktop/THEANH/JAVA/"+clientSelection+".png"));
            long size = clientData.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
            output.close();
            clientData.close();

            System.out.println("File " + fileName + " received from client.");
        } catch (IOException ex) {
            System.err.println("Client error. Connection closed.");
        }
    }
}