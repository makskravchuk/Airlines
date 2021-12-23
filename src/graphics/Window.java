package graphics;

import javax.swing.*;
import java.awt.*;

public abstract class Window {
    protected static Image image = new ImageIcon("resources\\plane.png").getImage();
    protected static Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    public abstract void openWindow();
    protected abstract JFrame getFrame();
}
