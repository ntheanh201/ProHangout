package prohangout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {

    ScreenResolution screenResolution = new ScreenResolution();
    private JPanel jPanel;
    private JPanel jPanelLogin;
    private JLabel loginLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel dontHaveAccount;
    private JTextField usernameInput;
    private JPasswordField jPasswordField;
    private JButton jButton;
    private JButton loginButton;

    private static int x = 20;


    public Login() throws IOException {
        try {
            BufferedImage sendImg = ImageIO.read(getClass().getResource("hangouts.png"));
            this.setIconImage(sendImg);
        } catch (IOException e){
            System.out.println("error");
        }

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(screenResolution.getWidth(), screenResolution.getHeight());
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("ProHangout");
        this.setResizable(false);

        setjPanel();
        setjPanelLogin();
        setLoginLabel();
        setUsernameLabel();
        setUsernameInput();
        setPasswordLabel();
        setjPasswordField();
        setjButton();
        setDontHaveAccount();
        setLoginButton();

        this.repaint();
        this.validate();
    }

    public void setjPanel() throws IOException {
        Image img2 = ImageIO.read(Login.class.getResource("background2.jpg"));
        jPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img2, 0, 0, null);
            }
        };
        jPanel.setLayout(null);
        jPanel.setVisible(true);
        jPanel.setBounds(0, 0, screenResolution.getWidth(), screenResolution.getHeight());
        this.add(jPanel);
    }

    public void setjPanelLogin() throws IOException {
        jPanelLogin = new JPanel();
        jPanelLogin.setLayout(null);
        jPanelLogin.setVisible(true);
        jPanelLogin.setOpaque(true);
        jPanelLogin.setBounds(290, 60, 460, 500);

        jPanel.add(jPanelLogin);
    }

    public void setLoginLabel(){
        loginLabel = new JLabel();
        loginLabel.setBounds(85, 10, 300, 100);
        ImageIcon imgThisImg = new ImageIcon("resource/images/label.png");
        loginLabel.setIcon(imgThisImg);
        loginLabel.setFont(new Font("Courier New", Font.BOLD, 40));

        jPanelLogin.add(loginLabel);
    }

    public void setUsernameLabel() {
        usernameLabel = new JLabel();
        usernameLabel.setText("Username: ");
        usernameLabel.setBounds(x, 120, 150, 80);
        usernameLabel.setFont(new Font("Courier New", Font.BOLD, 16));
        jPanelLogin.add(usernameLabel);
    }

    public void setUsernameInput() {
        usernameInput = new JTextField();
        usernameInput.setBounds(x, 170, 420, 50);
        usernameInput.setFont(new Font("Courier New", Font.PLAIN, 16));

        jPanelLogin.add(usernameInput);
    }

    public void setPasswordLabel() {
        passwordLabel = new JLabel();
        passwordLabel.setBounds(x, 200, 150, 80);
        passwordLabel.setText("Password: ");
        passwordLabel.setFont(new Font("Courier New", Font.BOLD, 16));

        jPanelLogin.add(passwordLabel);
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (usernameInput.getText().equals("") || jPasswordField.getPassword().equals("")) {
                JOptionPane.showMessageDialog(null, "Please insert username/password", "Login Failed", 3);
            } else try {
                ResultSet resultSet;
                Connection connection = Connect.getMySQLConnection();
                PreparedStatement Pstatement = connection.prepareStatement("SELECT * FROM `users` WHERE `username` =? AND `password` =?");
                Pstatement.setString(1, usernameInput.getText());
                Pstatement.setString(2, String.valueOf(jPasswordField.getPassword()));


                resultSet = Pstatement.executeQuery();

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "Login Successfully", "Login Successfully", 1);
                    Username username = new Username();
                    username.setUsername(usernameInput.getText());
                    username.setPassword(String.valueOf(jPasswordField.getPassword()));
                    dispose();
                    new ClientServer();
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Username Or Password", "Login Failed", 2);
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    };

    public void setjPasswordField() {
        jPasswordField = new JPasswordField();
        jPasswordField.setBounds(x, 250, 420, 50);
        jPasswordField.setFont(new Font("Courier New", Font.PLAIN, 16));

        jPasswordField.addActionListener(actionListener);
        jPanelLogin.add(jPasswordField);
    }

    public void setjButton() {
        jButton = new JButton();
        jButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jButton.setText("LOGIN");
        jButton.setForeground(Color.decode("#4580c2"));
        jButton.setFont(new Font("Courier New", Font.BOLD, 16));
        jButton.setBounds(x + 165, 330, 100, 50);

        jButton.addActionListener(actionListener);
        jPanelLogin.add(jButton);
    }

    public void setDontHaveAccount() {
        dontHaveAccount = new JLabel();
        dontHaveAccount.setText("Don't have an account?");
        dontHaveAccount.setBounds(x + 40, 400, 300, 80);
        dontHaveAccount.setFont(new Font("Courier New", Font.BOLD, 16));
        jPanelLogin.add(dontHaveAccount);
    }

    public void setLoginButton() {
        loginButton = new JButton();
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setText("SIGN UP");
        loginButton.setForeground(Color.decode("#4580c2"));
        loginButton.setBounds(x + 270, 425, 100, 30);
        loginButton.setFont(new Font("Courier New", Font.BOLD, 16));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new SignUp();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        jPanelLogin.add(loginButton);
    }

}
