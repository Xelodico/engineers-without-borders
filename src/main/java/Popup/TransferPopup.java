package Popup;

import BoardGame.BoardGameUI;
import BoardGame.Player;
import BoardGame.Task;
import GameSystem.GameSystem;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

/**
 * @author Nathan Watkins
 */
public class TransferPopup extends JPanel {

    private final JLabel popupTitle;
    private final JTextPane popupDesc;
    private final JPanel buttonContainer;
    private Task task;

    public TransferPopup(String title, String desc) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        int HEIGHT = 500;
        int WIDTH = 500;
        setBounds(BoardGameUI.WINDOW_WIDTH / 2 - WIDTH / 2, BoardGameUI.WINDOW_HEIGHT / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        setBackground(new java.awt.Color(240, 240, 240));
        setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setVisible(false);

        add(Box.createVerticalStrut(20));

        popupTitle = new JLabel(title);
        popupTitle.setFont(new java.awt.Font("Segue UI", Font.BOLD, 24));
        popupTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(popupTitle);

        add(Box.createVerticalStrut(20));

        popupDesc = new JTextPane();
        popupDesc.setText(desc);
        popupDesc.setFont(new java.awt.Font("Segue UI", Font.PLAIN, 18));
        popupDesc.setOpaque(false);
        popupDesc.setEditable(false);
        popupDesc.setHighlighter(null);
        popupDesc.setCaretColor(new Color(0, 0, 0, 0));
        StyledDocument doc = popupDesc.getStyledDocument();
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setAlignment(attr, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), attr, false);
        add(popupDesc);

        add(Box.createVerticalStrut(20));

        buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(buttonContainer);
        add(Box.createVerticalStrut(10));

        JButton cancelButton = createButton("Cancel");
        cancelButton.addActionListener(e -> GameSystem.toggleTransfer(null));
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(cancelButton);
        add(Box.createVerticalStrut(10));
    }

    /**
     * Creates a styled JButton with the specified text.
     * The button has a custom font, a hand cursor when hovered over,
     * a border, and additional visual properties.
     *
     * @param text The text to be displayed on the button.
     * @return A JButton object with defined styles and the specified text.
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new java.awt.Font("Segue UI", Font.PLAIN, 18));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }

    /**
     * Renders buttons for each player in the provided array.
     * Each button is associated with a player and, when clicked,
     * sets the task's owner to that player.
     *
     * @param players An array of Player objects representing the players.
     */
    public void renderButtons(Player[] players) {
        buttonContainer.removeAll();
        for (Player player : players) {
            if (player != null) {
                JButton button = createButton(player.getName());
                if (task.getOwnedBy() == player) {
                    button.setEnabled(false);
                }
                buttonContainer.add(button);
                if (players.length > 1 && player != players[players.length - 1]) {
                    buttonContainer.add(Box.createHorizontalStrut(10));
                }
            }
        }
        setDescription(task.getTitle() + "\n\nWho do you want to transfer this task to?");
    }

    /**
     * Sets the title of the popup by updating the text of the popupTitle field.
     *
     * @param text The text to be set as the title of the popup.
     */
    public void setTitle(String text) {
        popupTitle.setText(text);
    }

    /**
     * Updates the description text displayed in the popup.
     *
     * @param text The text to be set as the description of the popup.
     */
    public void setDescription(String text) {
        popupDesc.setText(text);
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
