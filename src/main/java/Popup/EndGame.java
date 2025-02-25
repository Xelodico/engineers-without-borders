package Popup;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import BoardGame.BoardGameUI;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class EndGame extends JPanel {

    private final int WIDTH = 500;
    private final int HEIGHT = 500;

    private JTextPane textArea;
    private JScrollPane scrollPane;
    private Timer timer;
    private final int scrollSpeed = 45; // lower is faster
    private boolean userScrolling = false;

    public EndGame() {
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

    private void startScrolling() {
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();

        verticalBar.addAdjustmentListener(e -> {
            if (timer.isRunning() && !userScrolling) {
                timer.stop();
            } 
        });
        
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

    private static String getEpilogueText(Ending ending) {

        String filePath = "";
        if (ending == Ending.GOOD) {
            filePath = "src/main/resources/data/goodEndingEvents.txt";
        } else {
            filePath = "src/main/resources/data/badEndingEvents.txt";
        }

        String string = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            Object[] lines = reader.lines().toArray();
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].equals("")) {
                    break;
                }
                string += 2025 + i + "\n";
                string += lines[i].toString();
                string += "\n\n";
            }
            string += lines[lines.length - 1].toString();
            
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string;
    }

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

    public void showBadEnding() {
        textArea.setText(getEpilogueText(Ending.BAD));
        SwingUtilities.invokeLater(() -> {
            scrollPane.getVerticalScrollBar().setValue(0);
        });
        setVisible(true);

        new Timer(4000, e -> {
            startScrolling();
            ((Timer) e.getSource()).stop();
        }).start();
    }

    private enum Ending {
        GOOD, BAD
    }

}
