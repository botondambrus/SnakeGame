import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon background = new ImageIcon("src/background/settings.gif");
        Graphics2D g2d = (Graphics2D) g;
        background.paintIcon(this, g2d, 0, 0);
    }
}
