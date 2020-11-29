package view;

import model.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UsernameInformation extends JFrame {
    private JPanel jPanel;
    private JLabel jLabel;
    private JLabel editInfor;
    private JLabel usernameLabel;
    User username = new User();

    public UsernameInformation() throws IOException {
        this.setLayout(null);
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle(username.getUsername());
        this.setResizable(false);

        setjPanel();
        setjLabel();
        setUsernameLabel();
        setEditInfor();

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

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public void setjLabel() throws IOException {
        jLabel = new JLabel();
        User user = new User();
        ImageIcon imgThisImg;
        try {
//            BufferedImage img = ImageIO.read(new URL(("http://s2bzen.me/theanhdz/images/" + user.getUsername() + ".png")));
//            InputStream path = this.getClass().getClassLoader().getResourceAsStream("avatars/" + user.getUsername() + ".png");
            String path = "/Users/macbook/Downloads/TheAnh/PTIT/NetworkProg/java-swing-prohangout/ClientChat/src/main/resources/avatars/" + user.getUsername() + ".png";
            BufferedImage img = ImageIO.read(new File(path));
            BufferedImage bufferedImage = resize((img), 256, 256);

            //        URL url = new URL(("http://s2bzen.me/theanhdz/images/"+username.getUsername()+".png"));
            imgThisImg = new ImageIcon(bufferedImage);
        } catch (Exception e){
            InputStream path = this.getClass().getClassLoader().getResourceAsStream("images/avt.png");
            imgThisImg= new ImageIcon(ImageIO.read(path));
        }

        jLabel.setIcon(imgThisImg);
        jLabel.setBounds(65, 10, 256, 256);
        jPanel.add(jLabel);
    }

    public void setUsernameLabel(){
        usernameLabel = new JLabel(username.getUsername(), JLabel.CENTER);
        usernameLabel.setBounds(50, 260, 300, 100);
        usernameLabel.setFont(new Font("Courier New", Font.BOLD, 40));
        jPanel.add(usernameLabel);
    }

    public void setEditInfor() throws IOException {
        editInfor = new JLabel();
        InputStream path = this.getClass().getClassLoader().getResourceAsStream("images/edit.png");
        ImageIcon imgThisImg= new ImageIcon(ImageIO.read(path));
        editInfor = new JLabel();
        editInfor.setIcon(imgThisImg);
        editInfor.setBounds(100, jPanel.getHeight()-70, 400, 50);
        editInfor.setText("Edit Profile");
        editInfor.setFont(new Font("Tahoma", Font.BOLD, 24));
        editInfor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        editInfor.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
                try {
                    new EditInformation();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
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
        jPanel.add(editInfor);
    }

}
