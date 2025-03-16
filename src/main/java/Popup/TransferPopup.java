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
import java.awt.event.ActionListener;

/**
 * The TransferPopup class represents a popup window for transferring tasks
 * between players.
 * It extends JPanel and provides methods to display and handle task transfer
 * actions.
 *
 * @author Nathan Watkins
 */
public class TransferPopup extends JPanel {

    private final JLabel popupTitle;
    private final JTextPane popupDesc;
    private final JPanel buttonContainer;
    private Task task;

    /**
     * Constructs a TransferPopup with the specified title and description.
     *
     * @param title the title of the popup
     * @param desc  the description to be displayed in the popup
     */
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

        ActionListener okSingleButton = e -> GameSystem.hidePopup();

        JButton cancelButton = createButton("Cancel");
        cancelButton.addActionListener(e -> {
            GameSystem.toggleTransfer(null);
            if (task.getOwnedBy() == null) {
                GameSystem.showPopup("Task not claimed!", task.getTitle() + " was not claimed due to poor funding.",
                        "Ok", null, okSingleButton, null);
            } else {
                GameSystem.showPopup("Task not transferred!",
                        task.getTitle() + " was not transferred to another player.", "Ok", null, okSingleButton, null);
            }

        });
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

                ActionListener okSingleButton = e -> GameSystem.hidePopup();

                ActionListener takeTask = e -> {
                    // Take task logic
                    if (GameSystem.purchaseTask(player, task.getResourceType(), task)) {
                        task.setOwnedBy(player);
                    } else {
                        GameSystem.showPopup(player.getName() + ", you do not enough resources!",
                                "You do not have enough resources to claim this task.", "OK", null, okSingleButton,
                                null);
                        return;
                    }

                    System.out.println("Task claimed!");
                    GameSystem.hideCostPopup();
                    GameSystem.refreshJournal();
                };

                ActionListener takeTaskForFree = e -> {
                    task.setOwnedBy(player);
                    System.out.println("Task claimed!");
                    GameSystem.hidePopup();
                    GameSystem.refreshJournal();
                };

                ActionListener rejectTask = e -> {
                    // Reject task logic
                    if (GameSystem.getTurnOrder().length > 1) {
                        System.out.println("Show the task to other players");
                        GameSystem.hideCostPopup();
                        GameSystem.hidePopup();
                        GameSystem.toggleTransfer(task);
                    } else {
                        System.out.println("No other players to show the task to");
                        GameSystem.hideCostPopup();
                        GameSystem.hidePopup();
                    }
                };

                if (GameSystem.getPlayerAt() == player) {
                    button.setEnabled(false);
                } else {
                    button.addActionListener(e -> {
                        if (task.getOwnedBy() == null) {
                            // If the task is not owned by anyone, give the ability to buy this task to
                            // others.
                            GameSystem.showCostPopup(player.getName() + ", do you want to get this task?",
                                    task.getTitle() + "\nDo you want to buy this task for ",
                                    task.getResourceType().toString(), task.getResourceCost(), takeTask, rejectTask);
                        } else {
                            // If the task is owned by someone, give the ability to transfer this task to
                            // others for free.
                            GameSystem.showPopup("Do you want to accept this Task?",
                                    "Do you wish to accept the Task: " + task.getTitle(), "Yes", "No", takeTaskForFree,
                                    rejectTask);
                        }

                        // GameSystem.purchaseTask(player, task.getResourceType(), task);
                        // task.setOwnedBy(player);
                        GameSystem.toggleTransfer(null);
                        GameSystem.refreshJournal();
                    });
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

    /**
     * Sets the task to be transferred.
     *
     * @param task The task to be transferred.
     */
    public void setTask(Task task) {
        this.task = task;
    }
}
