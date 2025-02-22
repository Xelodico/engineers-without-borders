package Popup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author Nathan Watkins
 */
public class TransferPopup extends JPanel {

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
        setBounds((650/2 - WIDTH /2) + 12, (650/2 - HEIGHT /2) + 6, WIDTH, HEIGHT);
        setBackground(new java.awt.Color(240, 240, 240));
        setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setVisible(false);

        JLabel popupTitle = new JLabel(title);
        popupTitle.setFont(new java.awt.Font("Segue UI", Font.BOLD, 24));
        popupTitle.setHorizontalAlignment(SwingConstants.CENTER);
        popupTitle.setText("<html>" + title + "</html>");
        popupTitle.setBounds(0, 0, WIDTH, 70);
        add(popupTitle);

        JTextArea popupDesc = new JTextArea(desc);
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

        JButton button1 = createButton(button1Text);
        button1.setBounds((WIDTH / 2) - 215, HEIGHT - 100, 100, 30);
        add(button1);

        JButton button2 = createButton(button2Text);
        button2.setBounds((WIDTH / 2) - 105, HEIGHT - 100, 100, 30);
        add(button2);

        JButton button3 = createButton(button3Text);
        button3.setBounds((WIDTH / 2) + 5, HEIGHT - 100, 100, 30);
        add(button3);

        JButton button4 = createButton(button4Text);
        button4.setBounds((WIDTH / 2) + 115, HEIGHT - 100, 100, 30);
        add(button4);

        JButton cancelButton = createButton("Cancel");
        cancelButton.setBounds((WIDTH / 2) - 50, HEIGHT - 51, 100, 30);
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

}
