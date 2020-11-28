package view;

import model.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UserPopup extends JFrame {
    private JPanel jPanel;
    private JLabel jLabel;
    private JLabel moreInfor;
    private JLabel usernameLabel;
    User username = new User();

    public UserPopup() throws IOException {
        this.setLayout(null);
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("About Author");
        this.setResizable(false);

        setjPanel();
        setjLabel();
        setUsernameLabel();
        setMoreInfor();

        this.validate();
        this.repaint();
    }

    public void setjPanel(){
        jPanel = new JPanel();
        jPanel.setBackground(Color.WHITE);
        jPanel.setLayout(null);
        jPanel.setVisible(true);
        jPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.add(jPanel);
    }

    public void setjLabel() throws IOException {
        jLabel = new JLabel();
        InputStream path = this.getClass().getClassLoader().getResourceAsStream("images/kinn.jpg");
        ImageIcon imgThisImg= new ImageIcon(ImageIO.read(path));
        jLabel.setIcon(imgThisImg);
        jLabel.setBounds(65, 40, 260, 260);
        jPanel.add(jLabel);
    }

    public void setUsernameLabel(){
        usernameLabel = new JLabel("KINN", JLabel.CENTER);
        usernameLabel.setBounds(45, 300, 300, 100);
        usernameLabel.setFont(new Font("Courier New", Font.BOLD, 40));

        jPanel.add(usernameLabel);
    }

    public void setMoreInfor(){
        moreInfor = new JLabel("Don't just dream it. Let's do it!");
        moreInfor.setBounds(60, 300, 380, 200);
        moreInfor.setFont(new Font("Tahoma", Font.PLAIN, 20));
        jPanel.add(moreInfor);
    }

}
