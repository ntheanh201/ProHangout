package view;

import utils.ConnectDB;
import model.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangePassword extends JFrame {
    private JPanel jPanel;
    private JLabel jLabel;
    private JLabel label;
    private JPasswordField oldPassword;
    private JPasswordField newPassword;
    private JPasswordField retypeNewPassword;
    private JButton confirm;
    User username = new User();
    private int x = 10;

    public ChangePassword() throws IOException {
        this.setLayout(null);
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Change Password");
        this.setResizable(false);

        setjPanel();
        setLabel();
        setjLabel();
        setOldPassword();
        setNewPassword();
        setRetypeNewPassword();
        setConfirm();

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

    public void setLabel() throws IOException {
        InputStream path = this.getClass().getClassLoader().getResourceAsStream("images/label.png");
        ImageIcon imgThisImg= new ImageIcon(ImageIO.read(path));
        label = new JLabel();
        label.setIcon(imgThisImg);
        label.setBounds(50, 20, 300, 80);
        jPanel.add(label);
    }

    public void setjLabel() {
        jLabel = new JLabel();
        jLabel.setText("Type your current password: ");
        jLabel.setBounds(x, 100, 380, 50);
        jPanel.add(jLabel);
    }

    public void setOldPassword() {
        oldPassword = new JPasswordField();
        oldPassword.setBounds(x, 140, 380, 50);

        jPanel.add(oldPassword);
    }

    public void setNewPassword() {

        jLabel.setText("Type new password: ");
        jLabel.setBounds(x, 190, 380, 50);
        setjLabel();

        newPassword = new JPasswordField();
        newPassword.setBounds(x, 230, 380, 50);
        jPanel.add(newPassword);
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String oldPass = String.valueOf(oldPassword.getPassword());
            if (oldPass == "") {
                JOptionPane.showMessageDialog(null, "Insert your password.", "Oops", JOptionPane.ERROR_MESSAGE);
            } else if (!oldPass.equals(username.getPassword())) {
                JOptionPane.showMessageDialog(null, "Password incorrect!", "Change Password Failed", 3);
            } else if (!String.valueOf(newPassword.getPassword()).equals(String.valueOf(retypeNewPassword.getPassword()))) {
                JOptionPane.showMessageDialog(null, "Passwords do not match.", "Oops", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Connection connection = ConnectDB.getMySQLConnection();
                    PreparedStatement Pstatement = connection.prepareStatement("UPDATE users SET password=? WHERE username=?");
                    Pstatement.setString(1, String.valueOf(newPassword.getPassword()));
                    Pstatement.setString(2, username.getUsername());
                    Pstatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Password update successfully!");
                    dispose();

                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        }
    };

    public void setRetypeNewPassword() {

        jLabel.setText("Re-type new password: ");
        jLabel.setBounds(x, 280, 380, 50);
        setjLabel();

        retypeNewPassword = new JPasswordField();
        retypeNewPassword.setBounds(x, 320, 380, 50);
        retypeNewPassword.addActionListener(actionListener);
        jPanel.add(retypeNewPassword);
    }

    public void setConfirm() {
        confirm = new JButton();
        confirm.setText("CONFIRM");
        confirm.setToolTipText("CONFIRM");
        confirm.setBounds(x + 140, 400, 100, 50);
        confirm.addActionListener(actionListener);
        jPanel.add(confirm);
    }

    public static void main(String[] args) throws IOException {
        new ChangePassword();
    }
}
