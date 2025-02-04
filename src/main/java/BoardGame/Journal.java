package BoardGame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Journal extends JPanel {
    private BufferedImage backgroundImage;

    public Journal() {
        final int width = 700;
        final int height = 500;
        
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/journalBackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(null);
        setBounds((BoardGameUI.WINDOW_WIDTH/2 - width/2), (650/2 - height/2) + 6, width, height);
        setBackground(new java.awt.Color(0, 0, 0, 0));
        setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    
}