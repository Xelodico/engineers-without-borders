package Popup;

import BoardGame.BoardGameUI;
import BoardGame.Player;
import GameSystem.GameSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author Nathan Watkins
 */
public class TransferPopup extends JPanel {

    private final JLabel popupTitle;
    private final JTextArea popupDesc;
    private final JButton button1;
    private final JButton button2;
    private final JButton button3;
    private final JButton button4;
    private final JButton cancelButton;

    /**
     * Constructs a TransferPopup panel with a specified title, description,
     * and four buttons with their corresponding action listeners.
     *
     * @param title         The title text for the popup.
     * @param desc          The description text displayed in the popup.
     * @param button1Text   The text for the first button.
     * @param button2Text   The text for the second button.
     * @param button3Text   The text for the third button.
     * @param button4Text   The text for the fourth button.
     * @param button1Action The action listener for the first button.
     * @param button2Action The action listener for the second button.
     * @param button3Action The action listener for the third button.
     * @param button4Action The action listener for the fourth button.
     */
    public TransferPopup(String title, String desc, String button1Text, String button2Text, String button3Text, String button4Text, ActionListener button1Action, ActionListener button2Action, ActionListener button3Action, ActionListener button4Action) {
        setLayout(null);
        int HEIGHT = 500;
        int WIDTH = 500;
        setBounds(BoardGameUI.WINDOW_WIDTH / 2 - WIDTH/2, BoardGameUI.WINDOW_HEIGHT/2 - HEIGHT/2, WIDTH, HEIGHT);
        setBackground(new java.awt.Color(240, 240, 240));
        setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setVisible(false);

        popupTitle = new JLabel(title);
        popupTitle.setFont(new java.awt.Font("Segue UI", Font.BOLD, 24));
        popupTitle.setHorizontalAlignment(SwingConstants.CENTER);
        popupTitle.setText("<html>" + title + "</html>");
        popupTitle.setBounds(0, 0, WIDTH, 70);
        add(popupTitle);

        popupDesc = new JTextArea(desc);
        popupDesc.setFont(new java.awt.Font("Segue UI", Font.PLAIN, 18));
        popupDesc.setLineWrap(true);
        popupDesc.setWrapStyleWord(true);
        popupDesc.setEditable(false);
        popupDesc.setBackground(new java.awt.Color(240, 240, 240));
        popupDesc.setBorder(null);
        popupDesc.setEnabled(false);
        popupDesc.setDisabledTextColor(Color.BLACK);
        popupDesc.setBounds(10, 80, WIDTH - 20, HEIGHT - 200);
        add(popupDesc);

        button1 = createButton(button1Text);
        button1.setBounds((WIDTH / 2) - 215, HEIGHT - 100, 100, 30);
        add(button1);

        button2 = createButton(button2Text);
        button2.setBounds((WIDTH / 2) - 105, HEIGHT - 100, 100, 30);
        add(button2);

        button3 = createButton(button3Text);
        button3.setBounds((WIDTH / 2) + 5, HEIGHT - 100, 100, 30);
        add(button3);

        button4 = createButton(button4Text);
        button4.setBounds((WIDTH / 2) + 115, HEIGHT - 100, 100, 30);
        add(button4);

        cancelButton = createButton("Cancel");
        cancelButton.setBounds((WIDTH / 2) - 50, HEIGHT - 51, 100, 30);
        cancelButton.addActionListener(e -> GameSystem.toggleTransfer());
        add(cancelButton);
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
        button.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        return button;
    }

    public void renderPlayerNames(Player[] players) {
        JButton[] buttons = {button1, button2, button3, button4};
        for (JButton b : buttons) {
            b.setVisible(false);
        }

        for (int i = 0; i < players.length; i++) {
            buttons[i].setVisible(true);
            buttons[i].setText(players[i].getName());

            int offset = (getWidth() / 2) - (buttons[i].getWidth() * players.length / 2) + i * (buttons[i].getWidth() + 5);
            if (players.length > 1) {
                offset -= 5;
            }
            buttons[i].setLocation(offset, getHeight() - 60 - buttons[i].getHeight());
        }
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
     * Updates the text of the first button in the popup.
     *
     * @param text The text to be displayed on the first button.
     */
    public void setButton1Text(String text) {
        button1.setText(text);
    }

    /**
     * Updates the text of the second button in the popup.
     *
     * @param text The text to be displayed on the second button.
     */
    public void setButton2Text(String text) {
        button2.setText(text);
    }

    /**
     * Updates the text of the third button in the popup.
     *
     * @param text The text to be displayed on the third button.
     */
    public void setButton3Text(String text) {
        button3.setText(text);
    }

    /**
     * Updates the text of the fourth button in the popup.
     *
     * @param text The text to be displayed on the fourth button.
     */
    public void setButton4Text(String text) {
        button4.setText(text);
    }

    /**
     * Sets the action listener for the first button in the popup.
     * Any previously assigned action listeners are removed before
     * adding the new action listener.
     *
     * @param action The ActionListener to be assigned to button1.
     */
    public void setButton1Action(ActionListener action) {
        ActionListener[] actions = button1.getActionListeners();
        for (ActionListener a : actions) {
            button1.removeActionListener(a);
        }
        button1.addActionListener(action);
    }

    /**
     * Sets the action listener for the second button in the popup.
     * Any previously assigned action listeners are removed before
     * adding the new action listener.
     *
     * @param action The ActionListener to be assigned to button2.
     */
    public void setButton2Action(ActionListener action) {
        ActionListener[] actions = button2.getActionListeners();
        for (ActionListener a : actions) {
            button2.removeActionListener(a);
        }
        button2.addActionListener(action);
    }

    /**
     * Sets the action listener for the third button in the popup.
     * Any previously assigned action listeners are removed before
     * adding the new action listener.
     *
     * @param action The ActionListener to be assigned to button3.
     */
    public void setButton3Action(ActionListener action) {
        ActionListener[] actions = button3.getActionListeners();
        for (ActionListener a : actions) {
            button3.removeActionListener(a);
        }
        button3.addActionListener(action);
    }

    /**
     * Sets the action listener for the fourth button in the popup.
     * Any previously assigned action listeners are removed before
     * adding the new action listener.
     *
     * @param action The ActionListener to be assigned to button4.
     */
    public void setButton4Action(ActionListener action) {
        ActionListener[] actions = button4.getActionListeners();
        for (ActionListener a : actions) {
            button4.removeActionListener(a);
        }
        button4.addActionListener(action);
    }

    /**
     * Sets the action listener for the cancel button. Any previously assigned
     * action listeners are removed before adding the new action listener.
     *
     * @param action The ActionListener to be assigned to the cancel button.
     */
    public void setCancelButtonAction(ActionListener action) {
        ActionListener[] actions = cancelButton.getActionListeners();
        for (ActionListener a : actions) {
            cancelButton.removeActionListener(a);
        }
        cancelButton.addActionListener(action);
    }
}
