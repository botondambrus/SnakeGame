import javax.swing.*;
import java.awt.*;

public class GameMenu extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon background = new ImageIcon("src/background/blue.gif");
        Graphics2D g2d = (Graphics2D) g;
        background.paintIcon(this, g2d, -400, 0);
    }
}
