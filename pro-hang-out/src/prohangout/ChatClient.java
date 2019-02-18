package prohangout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;


public class ChatClient {

    static class ChatAccess extends Observable {
        private Socket socket;
        private OutputStream outputStream;
        private DataOutputStream dataOutputStream;
        Username username = new Username();

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
            dataOutputStream.writeBytes(username.getUsername() + '\n');

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
//                outputStream.w
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
    }

    /**
     * Chat client UI
     */
    static class ChatFrame extends JFrame implements Observer {

        private JLabel emojiLabel;
        private JLabel flowerLabel;
        private JPanel jPanel;
        private JPanel jPanelSide;
        private JLabel usernameLabel;
        private JLabel settingsLabel;
        private JTextPane textPane;
        private JTextField inputTextField;
        private JButton sendButton;
        private ChatAccess chatAccess;
        private JScrollPane jScrollPane;
        private JLabel exitLabel;
        ScreenResolution screenResolution = new ScreenResolution();
        Image img2 = ImageIO.read(ChatClient.class.getResource("background.jpg"));

        public ChatFrame(ChatAccess chatAccess) throws IOException {
            this.chatAccess = chatAccess;
            chatAccess.addObserver(this);
            buildGUI();
        }

        /**
         * Builds the user interface
         */
        private void buildGUI() {
            jPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(img2, 0, 0, null);
                }
            };
            jPanel.setVisible(true);
            jPanel.setLayout(null);
            jPanel.getAutoscrolls();
            jPanel.setBounds(100, 0, screenResolution.getWidth() - 100, screenResolution.getHeight());
            this.add(jPanel);

            buildJPanelSide();
            chatGUI();

            this.validate();
            this.repaint();
        }

        public void buildJPanelSide() {
            jPanelSide = new JPanel() {
                @Override
                protected void paintComponent(Graphics grphcs) {
                    super.paintComponent(grphcs);
                    Graphics2D g2d = (Graphics2D) grphcs;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                    GradientPaint gp = new GradientPaint(0, 0,
                            getBackground().brighter().brighter(), 0, getHeight(),
                            getBackground().darker());
                    g2d.setPaint(gp);
                    g2d.fillRect(0, 0, getWidth(), getHeight());

                }
            };

            jPanelSide.setLayout(null);
            jPanelSide.setVisible(true);
            jPanelSide.setBackground(Color.decode("#3e78df"));
            jPanelSide.setBounds(0, 0, 100, screenResolution.getHeight());
            this.add(jPanelSide);

            setUsernameLabel();
            setSettingsLabel();
            setFlowerLabel();
            setExit();
            setEmojiLabel();

        }

        public void setEmojiLabel(){
            emojiLabel = new JLabel();
            ImageIcon imgThisImg = new ImageIcon("resource/images/emoji.png");
            emojiLabel.setIcon(imgThisImg);
            emojiLabel.setBounds(32, screenResolution.getHeight() - 160, 32, 32);
            emojiLabel.setToolTipText("Emoji for ProHangout");
            emojiLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            emojiLabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    new EmojiGuide();
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            jPanelSide.add(emojiLabel);
        }

        public void setExit() {
            exitLabel = new JLabel();
            ImageIcon imgThisImg = new ImageIcon("resource/images/door.png");
            exitLabel.setIcon(imgThisImg);
            exitLabel.setToolTipText("Exit");
            exitLabel.setBounds(32, screenResolution.getHeight() - 60, 32, 32);
            exitLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            exitLabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure want to quit?", "NTA", dialogButton);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            jPanelSide.add(exitLabel);
        }

        public static BufferedImage resize(BufferedImage img, int newW, int newH) {
            Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();
            return dimg;
        }

        public void setUsernameLabel() {
            usernameLabel = new JLabel();

            Username username = new Username();
            ImageIcon imgThisImg;
            try {
                BufferedImage img = ImageIO.read(new URL(("http://s2bzen.me/theanhdz/images/" + username.getUsername() + ".png")));
                BufferedImage bufferedImage = resize((img), 64, 64);
                imgThisImg = new ImageIcon(bufferedImage);
            } catch (Exception e){
                imgThisImg = new ImageIcon("resource/images/user.png");
            }

            usernameLabel.setBounds(18, 18, 64, 64);
            usernameLabel.setToolTipText("Get more information.");
            usernameLabel.setIcon(imgThisImg);
            usernameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            usernameLabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        new UsernameInformation();
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            jPanelSide.add(usernameLabel);
        }

        public void setSettingsLabel() {
            settingsLabel = new JLabel();
            ImageIcon imgThisImg = new ImageIcon("resource/images/settings.png");
            settingsLabel.setBounds(32, screenResolution.getHeight() - 110, 32, 32);
            settingsLabel.setToolTipText("About Author");
            settingsLabel.setIcon(imgThisImg);
            settingsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            settingsLabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    new UserPopup();
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            jPanelSide.add(settingsLabel);
        }

        public void setFlowerLabel() {
            flowerLabel = new JLabel("", JLabel.CENTER);
            ImageIcon imgThisImg = new ImageIcon("resource/images/label.png");
            flowerLabel.setIcon(imgThisImg);
            flowerLabel.setBounds(300, 0, 300, 80);
            jPanel.add(flowerLabel);
        }

        public void chatGUI() {

            textPane = new JTextPane();
            textPane.setEditable(false);

//            textArea = new JTextArea();
//            textArea.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
//            textArea.setEditable(false);
//            textArea.setLineWrap(true);
//            textArea.setWrapStyleWord(true);
//            jPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
            jPanel.add(new JScrollPane(textPane), BorderLayout.CENTER);

//            jScrollPane = new JScrollPane(textArea);
            jScrollPane = new JScrollPane(textPane);
            jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            jScrollPane.setBounds(10, 80, jPanel.getWidth() - 20, jPanel.getHeight() - 150);

            setFlowerLabel();

            inputTextField = new JTextField();
            inputTextField.setBounds(5, jPanel.getHeight() - 70, jPanel.getWidth() - 100, 50);
            sendButton = new JButton("Send");
            sendButton.setBounds(jPanel.getWidth() - 105, jPanel.getHeight() - 70, 100, 50);

            ActionListener sendListener = e -> {
                String str = inputTextField.getText();
                if (str != null && str.trim().length() > 0)
                    chatAccess.send(str);
                inputTextField.selectAll();
                inputTextField.requestFocus();
                inputTextField.setText("");
            };
            inputTextField.addActionListener(sendListener);

            try {
                Image sendImg = ImageIO.read(getClass().getResource("chat.png"));
                sendButton.setIcon(new ImageIcon(sendImg));
            } catch (IOException e) {
                System.out.println("error");
            }
            sendButton.addActionListener(sendListener);

            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    chatAccess.close();
                }
            });

            jPanel.add(inputTextField);
            jPanel.add(sendButton);
            jPanel.add(jScrollPane);
        }

        public void addColoredText(JTextPane pane, String text, Color color) {
            StyledDocument doc = pane.getStyledDocument();

            Style style = pane.addStyle("Color Style", null);

            StyleConstants.setForeground(style, color);
            StyleConstants.setFontSize(style, 14);
            try {
                doc.insertString(doc.getLength(), text, style);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }

        public void addIcon(JTextPane pane, String text) {
            ImageIcon imgThisImg;
            if (text.equals(":3")) {
                imgThisImg = new ImageIcon("resource/icons/cat.png");
            } else if (text.equals(":v")) {
                imgThisImg = new ImageIcon("resource/icons/pacman.png");
            } else if (text.equals(":)")) {
                imgThisImg = new ImageIcon("resource/icons/smiley.png");
            } else if (text.equals(":(")) {
                imgThisImg = new ImageIcon("resource/icons/cry.png");
            } else if (text.equals("o.O")) {
                imgThisImg = new ImageIcon("resource/icons/oO.png");
            } else if (text.equals(":poop:")) {
                imgThisImg = new ImageIcon("resource/icons/poop.png");
            } else if (text.equals("(^^^)")) {
                imgThisImg = new ImageIcon("resource/icons/shark.png");
            } else if (text.equals("-_-")) {
                imgThisImg = new ImageIcon("resource/icons/squint.png");
            } else if (text.equals("<(')")) {
                imgThisImg = new ImageIcon("resource/icons/penguine.png");
            } else if (text.equals("><")) {
                imgThisImg = new ImageIcon("resource/icons/mean.png");
            } else if (text.equals(":kiss:")) {
                imgThisImg = new ImageIcon("resource/icons/kiss.png");
            } else if (text.equals("(y)")) {
                imgThisImg = new ImageIcon("resource/icons/like.png");
            } else if (text.equals(":love:")) {
                imgThisImg = new ImageIcon("resource/icons/love.png");
            } else if (text.equals("<3")) {
                imgThisImg = new ImageIcon("resource/icons/heart.png");
            } else if (text.equals(":crysmiley:")) {
                imgThisImg = new ImageIcon("resource/icons/khoc_cuoi.png");
            } else if (text.equals(":nervous:")) {
                imgThisImg = new ImageIcon("resource/icons/nervous.png");
            }
            else{
                imgThisImg = new ImageIcon("resource/icons/khoc_cuoi.png");
            }
            pane.insertIcon(imgThisImg);
        }

        public void update(Observable o, Object arg) {
            final Object finalArg = arg;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    //pop up by kinn :)))
                    String line = finalArg.toString();

                    String separate = line, text="";
                    int check = 0;
                    String[] words = separate.split(" ");
                    for (int i = 0; i < words.length; i++) {
                        if (words[i].equals(":v") || words[i].equals(":3") || words[i].equals(":)") || words[i].equals(":(") || words[i].equals("o.O") || words[i].equals(":poop:")
                                || words[i].equals("(^^^)") || words[i].equals("-_-") || words[i].equals("<(')") || words[i].equals("><") || words[i].equals(":kiss:") || words[i].equals("(y)")
                                || words[i].equals(":love:") || words[i].equals("<3") || words[i].equals(":crysmiley:") || words[i].equals(":nervous:")
                        ) {
                            check = 1;
                            text = words[i];
                            words[i] = "";
                        }
                    }
                    String ans = "";
                    for (String i : words) {
                        ans += (i + " ");
                    }
//                    textPane.setFont(new java.awt.Font("Arial", Font.PLAIN, 15));
                    if (line.startsWith("Tôi:")) {
                        addColoredText(textPane, ans, Color.BLACK);
                    } else if (ans.startsWith("***") || ans.startsWith("Welcome") || ans.startsWith("To")) {
                        addColoredText(textPane, ans, Color.red);
                    } else {
                        addColoredText(textPane, ans, Color.BLUE);
                        new Notifications();
                    }

                    if (check == 1) {
                        addIcon(textPane, text);
                    }
                    addColoredText(textPane, "\n", Color.red);

                }
            });
        }
    }

    public static void main(String[] args) throws IOException {
        String server = args[0];
        int port = 2222;
        ChatAccess access = new ChatAccess();

        JFrame frame = new ChatFrame(access);

        try {
            BufferedImage sendImg = ImageIO.read(ChatClient.class.getResource("hangouts.png"));
            frame.setIconImage(sendImg);
        } catch (IOException e) {
            System.out.println("Error");
        }

        ScreenResolution screenResolution = new ScreenResolution();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(screenResolution.getWidth(), screenResolution.getHeight());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("ProHangout");
        frame.setResizable(false);

        try {
            access.InitSocket(server, port);
        } catch (IOException ex) {
            System.out.println("Cannot connect to " + server);
            ex.printStackTrace();
            System.exit(0);
        }
    }
}