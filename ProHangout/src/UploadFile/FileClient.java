package UploadFile;

import prohangout.Username;
import prohangout.UsernameInformation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.Socket;

//author KINN :))
public class FileClient extends JFrame {

    private static Socket sock;
    private static String fileName;
    private static BufferedReader stdin;
    private DataOutputStream dataOutputStream;
    private static PrintStream os;
    private static String name;

    public static void main(String[] args) throws IOException {
        new FileClient();
    }

    private JPanel jPanel;
    private JButton sendButton;
    private JButton cancelButton;
    private JLabel jLabel;
    private JButton chooseButton;
    private JTextField urlFileTextField;

    public FileClient() throws IOException {
        try {
            sock = new Socket("localhost", 4444);
            stdin = new BufferedReader(new InputStreamReader(System.in));
        } catch (Exception e) {
            System.err.println("Cannot connect to the server, try again later.");
            System.exit(1);
        }

        Username username = new Username();
        os = new PrintStream(sock.getOutputStream());
        os.println(username.getUsername());

        this.setLayout(null);
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Avatar Upload");
        this.setResizable(false);

        setjPanel();
        setjLabel();
        setSendButtont();
        setCancelButton();
        setUrlFileTextField();
        setChooseButton();

        this.validate();
        this.repaint();
    }

    public void setjPanel() {
        jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setVisible(true);
        jPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.add(jPanel);
    }

    public void setjLabel() {
        ImageIcon imgThisImg = new ImageIcon("resource/images/label.png");
        jLabel = new JLabel();
        jLabel.setIcon(imgThisImg);
        jLabel.setBounds(50, 20, 300, 80);
        jPanel.add(jLabel);
    }

    public void setSendButtont() {
        sendButton = new JButton();
        sendButton.setText("Set");
        sendButton.setForeground(Color.BLUE);
        sendButton.setBounds(80, 300, 100, 50);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File myFile = new File(fileName);
                    byte[] mybytearray = new byte[(int) myFile.length()];

                    FileInputStream fis = new FileInputStream(myFile);
                    BufferedInputStream bis = new BufferedInputStream(fis);

                    DataInputStream dis = new DataInputStream(bis);
                    dis.readFully(mybytearray, 0, mybytearray.length);

                    OutputStream os = sock.getOutputStream();

                    DataOutputStream dos = new DataOutputStream(os);
                    dos.writeUTF(myFile.getName());
                    dos.writeLong(mybytearray.length);
                    dos.write(mybytearray, 0, mybytearray.length);
                    dos.flush();
                    JOptionPane.showMessageDialog(null, "Your Avatar has been uploaded", "ProHangout", 2);
                    new UsernameInformation();
                } catch (Exception ev) {
                    JOptionPane.showMessageDialog(null, "File does not exists!", "Failed", 2);
                }
                try {
                    sock.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        jPanel.add(sendButton);
    }

    public void setCancelButton() {
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        cancelButton.setBounds(220, 300, 100, 50);
        cancelButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                setVisible(false);
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
        jPanel.add(cancelButton);
    }

    public void setUrlFileTextField() {
        urlFileTextField = new JTextField();
        urlFileTextField.setBounds(40, 150, 200, 50);
        jPanel.add(urlFileTextField);
    }

    public void setChooseButton() {
        chooseButton = new JButton();
        chooseButton.setText("Choose");
        chooseButton.setBounds(260, 150, 100, 50);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showOpenDialog(jFileChooser);
                fileName = jFileChooser.getSelectedFile().getAbsolutePath();
                urlFileTextField.setText(fileName);

//                File file = new File(fileName);
//                name = file.getName();
            }
        };
        chooseButton.addActionListener(actionListener);
        jPanel.add(chooseButton);
    }
}