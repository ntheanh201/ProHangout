package socket;

import model.User;

import java.io.*;
import java.net.Socket;
import java.util.Observable;

public class ChatAccess extends Observable {
//    static class ChatAccess extends Observable {
        private Socket socket;
        private OutputStream outputStream;
        private DataOutputStream dataOutputStream;
        User user = new User();

        @Override
        public void notifyObservers(Object arg) {
            super.setChanged();
            super.notifyObservers(arg);
        }

        /**
         * Create socket, and receiving thread
         */
        public void InitSocket(String server, int port) throws IOException {
            socket = new Socket(server, port);
            outputStream = socket.getOutputStream();

            //truyen username len server
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeBytes(user.getUsername() + '\n');

            Thread receivingThread = new Thread() {
                @Override
                public void run() {
                    try {

                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null)
                            notifyObservers(line);
                    } catch (IOException ex) {
                        notifyObservers(ex);
                    }
                }
            };
            receivingThread.start();
        }

        private static final String CRLF = "\r\n"; // newline

        /**
         * Send a line of text
         */
        public void send(String text) {
            try {
                outputStream.write((text + CRLF).getBytes("UTF-8"));
                outputStream.flush();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }

        public void close() {
            try {
                socket.close();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
//    }
}
