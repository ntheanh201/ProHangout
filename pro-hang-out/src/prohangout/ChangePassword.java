package prohangout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    Username username = new Username();
    private int x = 10;

    public ChangePassword() {
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

    public void setLabel() {
        ImageIcon imgThisImg = new ImageIcon("resource/images/label.png");
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
                    Connection connection = Connect.getMySQLConnection();
                    PreparedStatement Pstatement = connection.prepareStatement("update users set password=? where username=?");
                    Pstatement.setString(1, String.valueOf(newPassword.getPassword()));
                    Pstatement.setString(2, username.getUsername());
                    Pstatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Password update successfully!");
                    dispose();

                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
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

    public static void main(String[] args) {
        new ChangePassword();
    }
}
