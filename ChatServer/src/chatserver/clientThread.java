package chatserver;

import java.io.*;
import java.net.Socket;

public class clientThread extends Thread{
    private String clientName = null;
    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;
    private int maxClientsCount;

    public clientThread(Socket clientSocket, clientThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
    }

    public void run() throws NullPointerException {
        int maxClientsCount = this.maxClientsCount;
        clientThread[] threads = this.threads;

        try {
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
            String name;

            name = is.readLine();
            /* Welcome the new the client. */
            os.println("Welcome " + name + " to PROPTIT's chat room.\nTo leave enter /quit in a new line.");
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] == this) {
                        clientName = "@" + name;
                        break;
                    }
                }
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] != this) {
                        threads[i].os.println("*** A new user " + name + " entered the chat room !!! ***");
                    }
                }
            }

            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream(), "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            String line1, line = "";

            /* Start the conversation. */
            while (true) {
//                while((line1 = br.readLine()) != null){
//                    line += (line1);
//                }
                line = br.readLine();
                if (line.startsWith("/quit")) {
                    break;
                }

//                String line = is.readLine();
                /* If the message is private sent it to the given client. */
                if (line.startsWith("@")) {
                    String[] words = line.split("\\s", 2);
                    if (words.length > 1 && words[1] != null) {
                        words[1] = words[1].trim();
                        if (!words[1].isEmpty()) {
                            synchronized (this) {
                                for (int i = 0; i < maxClientsCount; i++) {
                                    if (threads[i] != null && threads[i] != this && threads[i].clientName != null && threads[i].clientName.equals(words[0])) {
                                        threads[i].os.println("<" + name + "> " + words[1]);
                                        /*
                                         * Echo this message to let the client know the private
                                         * message was sent.
                                         */
                                        this.os.println(">" + name + "> " + words[1]);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    /* The message is public, broadcast it to all other clients. */
                    synchronized (this) {
                        for (int i = 0; i < maxClientsCount; i++) {
                            if (threads[i] != null && threads[i].clientName != null) {
                                if(threads[i] != this){ //khac voi user hien tai
                                    threads[i].os.println("" + name + ": " + line);
                                } else {
                                    threads[i].os.println("Tôi: " + line);
                                }
                            }

                        }
                    }
                }
            }
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] != this
                            && threads[i].clientName != null) {
                        threads[i].os.println("*** The user " + name
                                + " is leaving the chat room !!! ***");
                    }
                }
            }
            os.println("*** Bye " + name + " ***");

            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == this) {
                        threads[i] = null;
                    }
                }
            }

            //close socket
            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
        }
    }
}