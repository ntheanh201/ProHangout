package prohangout;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;


public class SignUp extends JFrame {

    ScreenResolution screenResolution = new ScreenResolution();
    private JPanel jPanelSignUp;
    private JPanel jPanel;
    private JLabel signUpLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel repeatPasswordLabel;
    private JLabel dontHaveAccount;
    private JTextField usernameInput;
    private JPasswordField jPasswordField;
    private JPasswordField jRepeatPasswordField;
    private JButton jButton;
    private JButton loginButton;

    private JLabel aboutMeLabel;
    private JLabel ntaLabel;
    private static int x = 20;
    Image img2 = ImageIO.read(ChatClient.class.getResource("background2.jpg"));

    public boolean isExistUsername(String username) {
        boolean isExistUser = false;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "SELECT * FROM `users` WHERE `username` =?";

        try {
            preparedStatement = Connect.getMySQLConnection().prepareStatement(query);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isExistUser = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return isExistUser;
    }

    public SignUp() throws IOException {

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
        Image icon = Toolkit.getDefaultToolkit().getImage("resource/images/hangouts.png");
        this.setIconImage(icon);

        setjPanel();
        setjPanelSignUp();
        setSignUpLabel();
        setUsernameLabel();
        setUsernameInput();
        setPasswordLabel();
        setjPasswordField();
        setRepeatPasswordLabel();
        setjRepeatPasswordField();
        setjButton();
        setDontHaveAccount();
        setLoginButton();


        this.repaint();
        this.validate();
    }

    public void setjPanel(){
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

    public void setjPanelSignUp() {
        jPanelSignUp = new JPanel();
        jPanelSignUp.setLayout(null);
        jPanelSignUp.setVisible(true);
        jPanelSignUp.setBounds(290, 70, 460, 500);

        jPanel.add(jPanelSignUp);
    }

    public void setSignUpLabel() {
        signUpLabel = new JLabel();
        signUpLabel.setBounds(85, 10, 300, 100);
        signUpLabel.setBackground(Color.WHITE);
        ImageIcon imgThisImg = new ImageIcon("resource/images/label.png");
        signUpLabel.setIcon(imgThisImg);
        jPanelSignUp.add(signUpLabel);
    }

    public void setUsernameLabel() {
        usernameLabel = new JLabel();
        usernameLabel.setText("Username: ");
        usernameLabel.setBounds(x, 120, 150, 80);
        usernameLabel.setFont(new Font("Courier New", Font.BOLD, 16));
        jPanelSignUp.add(usernameLabel);
    }

    public void setUsernameInput() {
        usernameInput = new JTextField();
        usernameInput.setBounds(x, 170, 420, 50);
        usernameInput.setFont(new Font("Courier New", Font.PLAIN, 16));

        jPanelSignUp.add(usernameInput);
    }

    public void setPasswordLabel() {
        passwordLabel = new JLabel();
        passwordLabel.setBounds(x, 200, 150, 80);
        passwordLabel.setText("Password: ");
        passwordLabel.setFont(new Font("Courier New", Font.BOLD, 16));
        jPanelSignUp.add(passwordLabel);
    }

    public void setjPasswordField() {
        jPasswordField = new JPasswordField();
        jPasswordField.setBounds(x, 250, 420, 50);
        jPasswordField.setFont(new Font("Courier New", Font.PLAIN, 16));
        jPanelSignUp.add(jPasswordField);
    }

    public void setRepeatPasswordLabel() {
        repeatPasswordLabel = new JLabel();
        repeatPasswordLabel.setBounds(x, 280, 350, 80);
        repeatPasswordLabel.setText("Re-Enter Password: ");
        repeatPasswordLabel.setFont(new Font("Courier New", Font.BOLD, 16));
        jPanelSignUp.add(repeatPasswordLabel);

    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (usernameInput.getText().trim().equals("") || jPasswordField.getPassword().equals("")) {
                JOptionPane.showMessageDialog(null, "Please insert username/password", "Login Failed", 3);
            } else if (!Arrays.equals(jPasswordField.getPassword(), jRepeatPasswordField.getPassword())) {
                JOptionPane.showMessageDialog(null, "Passwords do not match.", "Oops", JOptionPane.ERROR_MESSAGE);
            } else if (isExistUsername(usernameInput.getText())) {
                JOptionPane.showMessageDialog(null, "The username already exist. Please choose another one!", "NTA", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    //Creating Connection Object
                    Connection connection = Connect.getMySQLConnection();
                    //Preapared Statement
                    PreparedStatement Pstatement = connection.prepareStatement("insert into users (username, password) values(?,?)");
                    //Specifying the values of it's parameter
                    Pstatement.setString(1, usernameInput.getText());
                    Pstatement.setString(2, String.valueOf(jPasswordField.getPassword()));


                    Pstatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data registered successfully");
                    dispose();
                    new Login();

                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    };

    public void setjRepeatPasswordField() {
        jRepeatPasswordField = new JPasswordField();
        jRepeatPasswordField.setBounds(x, 330, 420, 50);
        jRepeatPasswordField.setFont(new Font("Courier New", Font.PLAIN, 16));
        jRepeatPasswordField.addActionListener(actionListener);
        jPanelSignUp.add(jRepeatPasswordField);
    }

    public void setjButton() {
        jButton = new JButton();
        jButton.setText("SIGN UP");
        jButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jButton.setForeground(Color.decode("#4580c2"));
        jButton.setFont(new Font("Courier New", Font.BOLD, 16));
        jButton.setBounds(x + 165, 390, 100, 50);

        jButton.addActionListener(actionListener);

        jPanelSignUp.add(jButton);
    }

    public void setDontHaveAccount() {
        dontHaveAccount = new JLabel();
        dontHaveAccount.setText("Have an account?");
        dontHaveAccount.setBounds(x + 70, 420, 300, 80);
        dontHaveAccount.setFont(new Font("Courier New", Font.BOLD, 16));
        jPanelSignUp.add(dontHaveAccount);
    }

    public void setLoginButton() {
        loginButton = new JButton();
        loginButton.setText("LOGIN");
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setForeground(Color.decode("#4580c2"));
        loginButton.setBounds(x+240, 445, 100, 30);
        loginButton.setFont(new Font("Courier New", Font.BOLD, 16));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                try {
                    new Login();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });

        jPanelSignUp.add(loginButton);

    }

}
