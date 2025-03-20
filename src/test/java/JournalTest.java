import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Popup.Journal;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class JournalTest {

    private Journal journal;

    @BeforeEach
    public void setUp() {
        journal = new Journal();
    }

    @Test
    public void testJournalInitialization() {
        assertNotNull(journal);
        assertEquals(700, journal.getWidth());
        assertEquals(500, journal.getHeight());
        assertFalse(journal.isVisible());
    }

    @Test
    public void testBackgroundImageLoading() {
        try {
            BufferedImage backgroundImage = ImageIO.read(getClass().getResource("/images/popups/journalBackground.png"));
            assertNotNull(backgroundImage);
        } catch (IOException e) {
            fail("Background image should be loaded without exceptions.");
        }
    }

    @Test
    public void testTitleLabel() {
        Component[] components = journal.getComponents();
        JPanel panel = (JPanel) components[0];
        JLabel titleLabel = null;
        for (Component component : panel.getComponents()) {
            if (component instanceof JLabel) {
                titleLabel = (JLabel) component;
                break;
            }
        }
        assertEquals("Objectives", titleLabel.getText());
        assertEquals(SwingConstants.LEFT, titleLabel.getHorizontalAlignment());
    }

    @Test
    public void testScrollPane() {
        Component[] components = journal.getComponents();
        JScrollPane scrollPane = null;
        for (Component component : components) {
            if (component instanceof JScrollPane) {
                scrollPane = (JScrollPane) component;
                break;
            }
        }
        assertNotNull(scrollPane);
        assertEquals(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, scrollPane.getVerticalScrollBarPolicy());
        assertEquals(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER, scrollPane.getHorizontalScrollBarPolicy());
    }
}
