import BoardGame.BoardGameUI;
import Popup.Shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {

    private Shop shop;

    @BeforeEach
    public void setUp() {
        shop = new Shop();
    }

    @Test
    public void testShopInitialization() {
        assertNotNull(shop);
        assertFalse(shop.isVisible());
        assertEquals(BoardGameUI.WINDOW_WIDTH, shop.getWidth());
        assertEquals(BoardGameUI.WINDOW_HEIGHT, shop.getHeight());
        assertEquals(0, shop.getX());
        assertEquals(0, shop.getY());
        assertEquals(null, shop.getLayout());
    }

    @Test
    public void testShopItemsExist() {
        Component[] components = shop.getComponents();
        assertEquals(9, components.length); // 4 Items, 4 Prices, 1 Close Button

        boolean hasItems = false;
        boolean hasLabels = false;
        boolean hasCloseButton = false;

        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getIcon() != null) {
                    hasItems = true;
                }
                if (button.getBounds().x == 778 && button.getBounds().y == 183) {
                    hasCloseButton = true;
                }
            } else if (component instanceof JLabel) {
                hasLabels = true;
            }
        }

        assertTrue(hasItems, "Shop should contain item buttons with icons.");
        assertTrue(hasLabels, "Shop should contain price labels.");
        assertTrue(hasCloseButton, "Shop should have a close button.");
    }

}
