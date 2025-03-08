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
        JLabel titleLabel = null;
        for (Component component : components) {
            if (component instanceof JLabel) {
                titleLabel = (JLabel) component;
                break;
            }
        }
        assertNotNull(titleLabel);
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

    @Test
    public void testObjectivesAndTasks() {
        Component[] components = journal.getComponents();
        JScrollPane scrollPane = null;
        for (Component component : components) {
            if (component instanceof JScrollPane) {
                scrollPane = (JScrollPane) component;
                break;
            }
        }
        assertNotNull(scrollPane);

        JViewport viewport = scrollPane.getViewport();
        JPanel page = (JPanel) viewport.getView();
        assertNotNull(page);
        assertEquals(4, page.getComponentCount());

        for (Component component : page.getComponents()) {
            assertTrue(component instanceof JPanel);
            JPanel objectivePanel = (JPanel) component;
            assertEquals(2, objectivePanel.getComponentCount());

            JLabel objectiveLabel = (JLabel) objectivePanel.getComponent(0);
            assertTrue(objectiveLabel.getText().startsWith("Objective"));

            JPanel taskPanel = (JPanel) objectivePanel.getComponent(1);
            assertEquals(5, taskPanel.getComponentCount()); // 3 tasks + 2 vertical struts
        }
    }
}
