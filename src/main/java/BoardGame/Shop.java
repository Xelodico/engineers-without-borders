package BoardGame;

import javax.imageio.ImageIO;
import javax.swing.*;

import GameSystem.GameSystem;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Represents a shop component in the BoardGame UI.
 * This panel displays a background image with a list of items that the player can purchase.
 * Each item has a corresponding cost and description.
 * The shop panel is not visible by default.
 * 
 * @author Nathan Watkins
 */
public class Shop extends JPanel {

    private BufferedImage backgroundImage;

    /**
     * Default constructor for the Shop object.
     * Initializes the Shop object with a background image and sets the layout to null.
     * The Shop object is not visible by default.
     */
    public Shop() {
        try {
            // Load background image
            backgroundImage = ImageIO.read(getClass().getResource("/images/shop.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(null);
        setBounds(0, 0, BoardGameUI.WINDOW_WIDTH, BoardGameUI.WINDOW_HEIGHT);
        setBackground(new java.awt.Color(0, 0, 0, 0));
        setVisible(false);
        setOpaque(false);

        JButton button = newShopItem(new ImageIcon(getClass().getResource("/images/questionMark.png")));
        button.setBounds(428, 187, 41, 60);
        addHoverEffect(button);
        add(button);

        JLabel price = new JLabel("100");
        price.setFont(new Font("Arial", Font.BOLD, 20));
        price.setForeground(Color.WHITE);
        price.setHorizontalAlignment(SwingConstants.CENTER);
        price.setBounds(396, 288, 100, 39);
        add(price);

        JButton button2 = newShopItem(new ImageIcon(getClass().getResource("/images/questionMark.png")));
        button2.setBounds(617, 187, 41, 60);
        addHoverEffect(button2);
        add(button2);

        JButton button3 = newShopItem(new ImageIcon(getClass().getResource("/images/questionMark.png")));
        button3.setBounds(428, 351, 41, 60);
        addHoverEffect(button3);
        add(button3);

        JButton button4 = newShopItem(new ImageIcon(getClass().getResource("/images/questionMark.png")));
        button4.setBounds(617, 351, 41, 60);
        addHoverEffect(button4);
        add(button4);

        addCloseButton(this);
    }

    public static JButton newShopItem(ImageIcon icon) {
        JButton button = new JButton();
        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setIcon(icon);
        return button;
    }

    public static void addHoverEffect(JButton button) {
        ImageIcon originalIcon = (ImageIcon) button.getIcon();
        int originalX = button.getX();
        int originalY = button.getY();
        int originalWidth = originalIcon.getIconWidth();
        int originalHeight = originalIcon.getIconHeight();

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Image originalImage = originalIcon.getImage();
                Image scaledImage = originalImage.getScaledInstance(originalImage.getWidth(null) + 10, originalImage.getHeight(null) + 10, Image.SCALE_SMOOTH);
                button.setBounds(originalX - 5, originalY - 8, originalWidth + 10, originalHeight + 10);
                button.setIcon(new ImageIcon(scaledImage));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBounds(originalX, originalY, originalWidth, originalHeight);
                button.setIcon(originalIcon);
            }
        });
    }

    private void addCloseButton(JPanel page) {
        URL closeIconUrl = getClass().getResource("/images/closeShop.png");
        URL closeIconRedUrl = getClass().getResource("/images/closeRed.png");

        if (closeIconUrl == null || closeIconRedUrl == null) {
            System.err.println("Error: Close button image(s) not found for Shop!");
            return;
        }

        ImageIcon closeIcon = new ImageIcon(closeIconUrl);
        ImageIcon closeIconRed = new ImageIcon(closeIconRedUrl);

        Image scaledCloseIcon = closeIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        Image scaledCloseIconRed = closeIconRed.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);

        JButton closeButton = new JButton();
        closeButton.setIcon(new ImageIcon(scaledCloseIcon));
        closeButton.setBounds(778, 183, 25, 25);
        closeButton.setBorder(null);
        closeButton.setOpaque(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameSystem.toggleShop();
            }
        });

        closeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                closeButton.setIcon(new ImageIcon(scaledCloseIconRed));
            }

            public void mouseExited(MouseEvent evt) {
                closeButton.setIcon(new ImageIcon(scaledCloseIcon));
            }
        });

        page.add(closeButton);
    }


    /**
     * Paints the background image for the shop panel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

}
