package Popup;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import BoardGame.BoardGameUI;
import GameSystem.GameSystem;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 * The Tutorial class represents a tutorial panel in a board game application.
 * It displays a tutorial text loaded from a file and provides a close button
 * to hide the tutorial.
 *
 * @author Nathan Watkins
 */
public class Tutorial extends JPanel {

    /**
     * Constructs a Tutorial panel with a specified width and height.
     * The tutorial text is loaded from a file, and the panel is set up with
     * a close button and title.
     */
    public Tutorial() {
        final int WIDTH = 700;
        final int HEIGHT = 500;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBounds(BoardGameUI.WINDOW_WIDTH / 2 - WIDTH / 2, BoardGameUI.WINDOW_HEIGHT / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        setBackground(new java.awt.Color(240, 240, 240));
        setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setVisible(false);

        addCloseButtonAndTitle();

        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setFont(new java.awt.Font("Arial", Font.PLAIN, 18));
        textPane.setCaretColor(new java.awt.Color(0, 0, 0, 0));
        textPane.setHighlighter(null);
        textPane.setBackground(new java.awt.Color(240, 240, 240));

        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet boldAttr = new SimpleAttributeSet();
        StyleConstants.setBold(boldAttr, true);

        loadTutorialText(doc, boldAttr);

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        renderScrollBar(scrollPane);
        add(scrollPane);
    }

    /**
     * Loads the tutorial text from a file and inserts it into the document.
     *
     * @param doc      The StyledDocument to insert the text into.
     * @param boldAttr The SimpleAttributeSet for bold text.
     */
    private void loadTutorialText(StyledDocument doc, SimpleAttributeSet boldAttr) {
        String filePath = "src/main/resources/data/tutorial.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.startsWith("#")) {
                    doc.insertString(doc.getLength(), line.substring(1) + "\n", boldAttr);
                } else {
                    doc.insertString(doc.getLength(), line + "\n", null);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath + " - " + e.getMessage());
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a close button and title to the tutorial panel.
     */
    private void addCloseButtonAndTitle() {
        URL closeIconUrl = getClass().getResource("/images/closeTutorial.png");
        URL closeIconRedUrl = getClass().getResource("/images/closeRed.png");

        if (closeIconUrl == null || closeIconRedUrl == null) {
            System.err.println("Error: Close button image(s) not found for Journal!");
            return;
        }

        ImageIcon closeIcon = new ImageIcon(
                new ImageIcon(closeIconUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)
        );
        ImageIcon closeIconRed = new ImageIcon(
                new ImageIcon(closeIconRedUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)
        );

        JButton closeButton = new JButton(closeIcon);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setRolloverIcon(closeIconRed);
        closeButton.addActionListener(e -> GameSystem.toggleTutorial());

        closeButton.setPreferredSize(new Dimension(30, 30));
        closeButton.setMaximumSize(new Dimension(30, 30));
        closeButton.setMinimumSize(new Dimension(30, 30));

        JPanel closePanel = new JPanel();
        closePanel.setLayout(new BoxLayout(closePanel, BoxLayout.X_AXIS));
        closePanel.setOpaque(false);
        closePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Tutorial");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        closePanel.add(titleLabel);
        closePanel.add(Box.createHorizontalGlue());
        closePanel.add(closeButton);

        add(closePanel);
    }

    /**
     * Renders a custom scroll bar for the given JScrollPane.
     *
     * @param scrollPane The JScrollPane to customize.
     */
    private void renderScrollBar(JScrollPane scrollPane) {
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();

        UIManager.put("ScrollBar.width", 8);

        verticalScrollBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(180, 180, 180));
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }

            // Remove track
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            }

            // Remove scrollbar buttons
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            /**
             * Creates a zero-size button to remove the scrollbar buttons.
             *
             * @return A JButton with zero size.
             */
            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });
    }

}