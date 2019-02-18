package prohangout;

import java.awt.*;

public class ScreenResolution {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = (int) screenSize.getWidth();
    private int height = (int) screenSize.getHeight();

    public int getWidth() {
        return (int) (width*0.55);
    }

    public int getHeight() {
        return (int) (height*0.6);
    }
}
