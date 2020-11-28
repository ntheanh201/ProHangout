package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class EmojiGuide extends JFrame {
    private JPanel jPanel;
    private JLabel jLabel;
    private JLabel moreInfor;
    private JLabel usernameLabel;

    public EmojiGuide() throws IOException {
        this.setLayout(null);
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Emoji");
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
        jPanel.setLayout(null);
        jPanel.setVisible(true);
        jPanel.setBackground(Color.WHITE);
        jPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.add(jPanel);
    }

    public void setMoreInfor(){
        moreInfor = new JLabel("Emoji");
        moreInfor.setBounds(170, 120, 200, 70);
        moreInfor.setFont(new Font("Courier New", Font.BOLD, 20));
        jPanel.add(moreInfor, BorderLayout.CENTER);
    }

    public void setjLabel() throws IOException {
        InputStream path = this.getClass().getClassLoader().getResourceAsStream("images/label.png");
        ImageIcon imgThisImg= new ImageIcon(ImageIO.read(path));
        jLabel = new JLabel();
        jLabel.setIcon(imgThisImg);
        jLabel.setBounds(50, 20, 300, 80);
        jPanel.add(jLabel);
    }

    public void setUsernameLabel() throws IOException {
        usernameLabel = new JLabel();
        InputStream path = this.getClass().getClassLoader().getResourceAsStream("images/guide.png");
        ImageIcon imgThisImg = new ImageIcon(ImageIO.read(path));
        usernameLabel.setIcon(imgThisImg);
        usernameLabel.setBounds(0, 170, 400, 300);

        jPanel.add(usernameLabel);
    }
}
