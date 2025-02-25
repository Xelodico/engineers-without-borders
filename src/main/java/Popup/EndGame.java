package Popup;

import BoardGame.BoardGameUI;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The EndGame class represents a JPanel that displays the end game screen
 * with a scrolling text area showing the game's epilogue.
 *
 * @author Nathan Watkins
 */
public class EndGame extends JPanel {

    private final JTextPane textArea;
    private final JScrollPane scrollPane;
    private Timer timer;
    private boolean userScrolling = false;

    /**
     * Constructs an EndGame panel with default settings.
     */
    public EndGame() {
        int WIDTH = 500;
        int HEIGHT = 500;
        setBounds(BoardGameUI.WINDOW_WIDTH / 2 - WIDTH / 2, BoardGameUI.WINDOW_HEIGHT / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        setVisible(true);
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        textArea = new JTextPane();
        textArea.setEditable(false);
        textArea.setText("Game in progress...");
        textArea.setFont(new Font("Segue UI", Font.PLAIN, 18));
        textArea.setBackground(new Color(240, 240, 240));

        StyledDocument doc = textArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(new Color(240, 240, 240));
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Starts the automatic scrolling of the text area.
     */
    private void startScrolling() {
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();

        verticalBar.addAdjustmentListener(e -> {
            if (timer.isRunning() && !userScrolling) {
                timer.stop();
            }
        });

        int scrollSpeed = 40; // lower is faster
        timer = new Timer(scrollSpeed, e -> {
            userScrolling = true;
            int current = verticalBar.getValue();
            int max = verticalBar.getMaximum() - verticalBar.getVisibleAmount();

            if (current < max) {
                verticalBar.setValue(current + 1); // Move scrollbar down
            } else {
                timer.stop(); // Stop scrolling when reaching the bottom
            }
            userScrolling = false;
        });
        timer.start();
    }

    /**
     * Reads the epilogue text from a file based on the ending type.
     *
     * @param ending The type of ending (GOOD or BAD).
     * @return The epilogue text as a String.
     */
    private String getEpilogueText(Ending ending) {

        String filePath;
        if (ending == Ending.GOOD) {
            filePath = "src/main/resources/data/goodEndingEvents.txt";
        } else {
            filePath = "src/main/resources/data/badEndingEvents.txt";
        }

        StringBuilder string = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            Object[] lines = reader.lines().toArray();
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].equals("")) {
                    break;
                }
                string.append(2025 + i).append("\n");
                string.append(lines[i].toString());
                string.append("\n\n");
            }
            string.append(lines[lines.length - 1].toString());

            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath + " - " + e.getMessage());
            return "Error reading file: " + filePath;
        }

        return string.toString();
    }

    /**
     * Displays the good ending epilogue and starts scrolling after a delay.
     */
    public void showGoodEnding() {
        textArea.setText(getEpilogueText(Ending.GOOD));
        SwingUtilities.invokeLater(() -> {
            scrollPane.getVerticalScrollBar().setValue(0);
        });
        setVisible(true);

        new Timer(4000, e -> {
            startScrolling();
            ((Timer) e.getSource()).stop();
        }).start();
    }

    /**
     * Displays the bad ending epilogue and starts scrolling after a delay.
     */
    public void showBadEnding() {
        textArea.setText(getEpilogueText(Ending.BAD));
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
        setVisible(true);

        new Timer(4000, e -> {
            startScrolling();
            ((Timer) e.getSource()).stop();
        }).start();
    }

    /**
     * Enum representing the possible endings of the game.
     */
    private enum Ending {
        GOOD, BAD
    }

}