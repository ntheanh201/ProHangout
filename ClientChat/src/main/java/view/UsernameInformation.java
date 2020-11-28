package view;

import model.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
        User username = new User();
        ImageIcon imgThisImg;
        try {
            BufferedImage img = ImageIO.read(new URL(("http://s2bzen.me/theanhdz/images/" + username.getUsername() + ".png")));
            BufferedImage bufferedImage = resize((img), 256, 256);

            //        URL url = new URL(("http://s2bzen.me/theanhdz/images/"+username.getUsername()+".png"));
            imgThisImg = new ImageIcon(bufferedImage);
        } catch (Exception e){
            imgThisImg = new ImageIcon("resource/images/avt.png");
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

    public void setEditInfor(){
        editInfor = new JLabel();
        ImageIcon imgThisImg = new ImageIcon("resource/images/edit.png");
        editInfor = new JLabel();
        editInfor.setIcon(imgThisImg);
        editInfor.setBounds(75, jPanel.getHeight()-70, 400, 50);
        editInfor.setText("Edit Information");
        editInfor.setFont(new Font("Tahoma", Font.BOLD, 24));
        editInfor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        editInfor.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
                new EditInformation();
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
