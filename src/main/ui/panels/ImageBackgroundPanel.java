package ui.panels;

import javax.swing.*;
import java.awt.*;

// represents a panel that has been set to have an image as its background
// code modeled after http://comexile.blogspot.com/2015/07/how-to-add-background-image-to-jpanel.html
public class ImageBackgroundPanel extends JPanel {

    private static final String TARGET_IMAGE = "data/BackgroundResized.png";
    private Image background;

    // EFFECTS: creates a JPanel which has the TARGET_IMAGE as its background
    public ImageBackgroundPanel() {
        this.background = (new ImageIcon(TARGET_IMAGE).getImage());
        Dimension imageSize = new Dimension(background.getWidth(null), background.getHeight(null));
        setPreferredSize(imageSize);
        setMinimumSize(imageSize);
        setMaximumSize(imageSize);
        setSize(imageSize);
        setLayout(null);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        graphics.drawImage(background, 0, 0, null);
    }

}
