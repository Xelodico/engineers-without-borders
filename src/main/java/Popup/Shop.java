package Popup;

import javax.imageio.ImageIO;
import javax.swing.*;

import BoardGame.BoardGameUI;
import BoardGame.ResourceType;
import GameSystem.GameSystem;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Represents a shop component in the BoardGame UI.
 * This panel displays a background image with a list of items that the player
 * can purchase.
 * Each item has a corresponding cost and description.
 * The shop panel is not visible by default.
 * 
 * @author Nathan Watkins
 */
public class Shop extends JPanel {

    private BufferedImage backgroundImage;
    private String resourceCost = Integer.toString(GameSystem.getResourcePrice()) + " Rand";
    private JButton closeButton;

    /**
     * Default constructor for the Shop object.
     * Initializes the Shop object with a background image and sets the layout to
     * null.
     * The Shop object is not visible by default.
     */
    public Shop() {
        try {
            // Load background image
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/popups/shop.png")));
        } catch (NullPointerException | IOException e) {
            System.err.println("Error: Shop background image not found!");
            System.exit(1);
        }

        setLayout(null);
        setBounds(0, 0, BoardGameUI.WINDOW_WIDTH, BoardGameUI.WINDOW_HEIGHT);
        setBackground(new java.awt.Color(0, 0, 0, 0));
        setVisible(false);
        setOpaque(false);

        addShopItemsAndPrices();
        addCloseButton(this);
    }

    /**
     * Adds the shop items and their corresponding prices to the shop panel.
     * Each item is represented by a JButton with an image icon.
     * Each price is represented by a JLabel with a string value.
     */
    public void addShopItemsAndPrices() {
        ActionListener acknowledgeActionListener = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GameSystem.hidePopup();
            }
        };

        JButton item1 = newShopItem(
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/resources/coldAsphalt.png"))));
        item1.setBounds(412, 188, 70, 70);
        item1.addActionListener(e -> {
            GameSystem.showCostPopup("Purchase Resource", "Do you want to buy Cold Asphalt for ", "Rand",
                    GameSystem.getResourcePrice(),
                    e1 -> {
                        if (GameSystem.purchaseResource(ResourceType.ASPHALT)) {
                            GameSystem
                                    .showPopup("Success!",
                                            "You successfully bought " + GameSystem.getResourceAwardedAmount()
                                                    + " Cold Asphalt Mix!",
                                            "OK", null, acknowledgeActionListener, null);
                        } else {
                            GameSystem.showPopup("You don't have enough money!", null, "OK", null,
                                    acknowledgeActionListener, null);
                        }
                        GameSystem.hideCostPopup();
                    },
                    e1 -> {
                        GameSystem.hideCostPopup();
                    });
        });
        addHoverEffect(item1);
        add(item1);

        JLabel price1 = newPrice(resourceCost);
        price1.setBounds(396, 303, 100, 39);
        add(price1);

        JButton item2 = newShopItem(
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/resources/influence.png"))));
        item2.setBounds(600, 188, 70, 70);
        item2.addActionListener(e -> {
            GameSystem.showCostPopup("Purchase Resource", "Do you want to buy Influence for ", "Rand",
                    GameSystem.getResourcePrice(),
                    e1 -> {
                        if (GameSystem.purchaseResource(ResourceType.INFLUENCE)) {
                            GameSystem.showPopup("Success!",
                                    "You successfully bought " + GameSystem.getResourceAwardedAmount() + " Influence!",
                                    "OK", null, acknowledgeActionListener, null);
                        } else {
                            GameSystem.showPopup("You don't have enough money!", null, "OK", null,
                                    acknowledgeActionListener, null);
                        }
                        GameSystem.hideCostPopup();
                    },
                    e1 -> {
                        GameSystem.hideCostPopup();
                    });
        });
        addHoverEffect(item2);
        add(item2);

        JLabel price2 = newPrice(resourceCost);
        price2.setBounds(585, 303, 100, 39);
        add(price2);

        JButton item3 = newShopItem(
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/resources/knowledge.png"))));
        item3.setBounds(412, 360, 70, 70);
        item3.addActionListener(e -> {
            GameSystem.showCostPopup("Purchase Resource", "Do you want to buy Knowledge for ", "Rand",
                    GameSystem.getResourcePrice(),
                    e1 -> {
                        if (GameSystem.purchaseResource(ResourceType.KNOWLEDGE)) {
                            GameSystem.showPopup("Success!",
                                    "You successfully bought " + GameSystem.getResourceAwardedAmount() + " Knowledge!",
                                    "OK", null, acknowledgeActionListener, null);
                        } else {
                            GameSystem.showPopup("You don't have enough money!", null, "OK", null,
                                    acknowledgeActionListener, null);
                        }
                        GameSystem.hideCostPopup();
                    },
                    e1 -> {
                        GameSystem.hideCostPopup();
                    });
        });
        addHoverEffect(item3);
        add(item3);

        JLabel price3 = newPrice(resourceCost);
        price3.setBounds(396, 473, 100, 39);
        add(price3);

        JButton item4 = newShopItem(
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/resources/volunteers.png"))));
        item4.setBounds(600, 360, 70, 70);
        item4.addActionListener(e -> {
            GameSystem.showCostPopup("Purchase Resource", "Do you want to buy Volunteers for ", "Rand",
                    GameSystem.getResourcePrice(),
                    e1 -> {
                        if (GameSystem.purchaseResource(ResourceType.VOLUNTEERS)) {
                            GameSystem.showPopup("Success!",
                                    "You successfully bought " + GameSystem.getResourceAwardedAmount() + " Volunteers!",
                                    "OK", null, acknowledgeActionListener, null);
                        } else {
                            GameSystem.showPopup("You don't have enough money!", null, "OK", null,
                                    acknowledgeActionListener, null);
                        }
                        GameSystem.hideCostPopup();
                    },
                    e1 -> {
                        GameSystem.hideCostPopup();
                    });
        });
        addHoverEffect(item4);
        add(item4);

        JLabel price4 = newPrice(resourceCost);
        price4.setBounds(585, 473, 100, 39);
        add(price4);

    }

    /**
     * @return A new shop item button with the specified image icon.
     */
    public static JButton newShopItem(ImageIcon icon) {
        icon = new ImageIcon(icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH));
        JButton button = new JButton();
        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setIcon(icon);
        return button;
    }

    /**
     * @return A new price label with the specified price string.
     */
    public static JLabel newPrice(String price) {
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return priceLabel;
    }

    /**
     * Adds a hover effect to the specified button.
     * When the mouse enters the button, the button's icon is scaled up.
     * When the mouse exits the button, the button's icon is scaled back down.
     */
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
                Image scaledImage = originalImage.getScaledInstance(originalImage.getWidth(null) + 10,
                        originalImage.getHeight(null) + 10, Image.SCALE_SMOOTH);
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

    /**
     * Adds a close button to the specified page.
     * The close button is represented by an image icon.
     * When the close button is clicked, the shop panel is hidden.
     */
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

        closeButton = new JButton();
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
