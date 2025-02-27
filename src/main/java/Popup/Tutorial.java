package Popup;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import BoardGame.BoardGameUI;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tutorial extends JPanel {

    public Tutorial() {
        final int WIDTH = 500;
        final int HEIGHT = 500;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBounds(BoardGameUI.WINDOW_WIDTH / 2 - WIDTH / 2, BoardGameUI.WINDOW_HEIGHT / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        setBackground(new java.awt.Color(240, 240, 240));
        setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setVisible(false);

        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setFont(new java.awt.Font("Arial", Font.PLAIN, 18));

        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet boldAttr = new SimpleAttributeSet();
        StyleConstants.setBold(boldAttr, true);

        loadTutorialText(doc, boldAttr);

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
    }

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
}
