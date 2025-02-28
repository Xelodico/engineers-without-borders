package Popup;

import BoardGame.BoardGameUI;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.*;

/**
 * Popup is a custom JPanel that displays a popup window with a title, description,
 * and two buttons (Yes and No). The popup can be customized with specific text for
 * the title, description, and buttons.
 * <p>
 * This class extends JPanel and uses various Swing components to create the popup
 * window. It includes a JLabel for the title, a JTextArea for the description, and
 * two JButtons for the Yes and No actions. The layout and appearance of the popup
 * are set within the constructor.
 * <p>
 * When either button is clicked, the popup is removed from its parent container
 * and set to null.
 * 
 * @author Nathan Watkins
 * @author Curtis McCartney
 */
public class Popup extends JPanel {

    private final JLabel popupTitle;
    private final JTextPane popupDesc;
    private final JButton yesButtonComponent;
    private final JButton noButtonComponent;

    @SuppressWarnings("unused")
    private boolean yesButtonVisible = true;
    @SuppressWarnings("unused")
    private boolean noButtonVisible = true;

    private final int WIDTH = 500;
    private final int HEIGHT = 500;

    /**
     *
     * @param title The title text to be displayed at the top of the popup.
     * @param desc The description text to be displayed in the body of the popup.
     * @param yesButtonText The text to be displayed on the Yes button.
     * @param noButtonText The text to be displayed on the No button.
     * @param yesAction The action to perform on when the Yes button is clicked.
     * @param noAction The action to perform when the No button is clicked.
     */
    public Popup(String title, String desc, String yesButtonText, String noButtonText, ActionListener yesAction, ActionListener noAction) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBounds(BoardGameUI.WINDOW_WIDTH / 2 - WIDTH / 2, BoardGameUI.WINDOW_HEIGHT / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        setBackground(new java.awt.Color(240, 240, 240));
        setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setVisible(false);

        add(Box.createVerticalStrut(20));

        popupTitle = new JLabel(title);
        popupTitle.setFont(new java.awt.Font("Segue UI", Font.BOLD, 24));
        popupTitle.setHorizontalAlignment(SwingConstants.CENTER);
        popupTitle.setText("<html>" + title + "</html>");
        popupTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(popupTitle);

        add(Box.createVerticalStrut(20));

        popupDesc = new JTextPane();
        popupDesc.setText(desc);
        popupDesc.setFont(new java.awt.Font("Segue UI", Font.PLAIN, 18));
        popupDesc.setEditable(false);
        popupDesc.setBackground(new java.awt.Color(240, 240, 240));
        popupDesc.setBorder(null);
        popupDesc.setEnabled(false);
        popupDesc.setDisabledTextColor(Color.BLACK);

        StyledDocument doc = popupDesc.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        add(popupDesc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setOpaque(false);

        yesButtonComponent = createButton(yesButtonText, yesAction);
        buttonPanel.add(yesButtonComponent);
        
        buttonPanel.add(Box.createHorizontalStrut(20));
        
        noButtonComponent = createButton(noButtonText, noAction);
        buttonPanel.add(noButtonComponent);

        add(buttonPanel);
        add(Box.createVerticalStrut(20));
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new java.awt.Font("Segue UI", Font.PLAIN, 18));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(action);

        button.setPreferredSize(new Dimension(100, 30));
        button.setMinimumSize(new Dimension(100, 30));
        button.setMaximumSize(new Dimension(100, 30));

        return button;
    }

    public void setTitle(String title) {
        popupTitle.setText(title);
    }

    public void setDescription(String desc) {
        popupDesc.setText(desc);
    }

    public void setYesButtonText(String text) {
        yesButtonComponent.setText(text);
        if (text == null || text.isEmpty()) {
            yesButtonComponent.setVisible(false);
            yesButtonVisible = false;
        } else {
            yesButtonComponent.setVisible(true);
            yesButtonVisible = true;
        }
    }

    public void setNoButtonText(String text) {
        noButtonComponent.setText(text);
        if (text == null || text.isEmpty()) {
            noButtonComponent.setVisible(false);
            noButtonVisible = false;
        } else {
            noButtonComponent.setVisible(true);
            noButtonVisible = true;
        }
    }

    public void setYesButtonAction(ActionListener action) {
        ActionListener[] actions = yesButtonComponent.getActionListeners();
        for (ActionListener a : actions) {
            yesButtonComponent.removeActionListener(a);
        }
        yesButtonComponent.addActionListener(action);
    }

    public void setNoButtonAction(ActionListener action) {
        ActionListener[] actions = noButtonComponent.getActionListeners();
        for (ActionListener a : actions) {
            noButtonComponent.removeActionListener(a);
        }
        noButtonComponent.addActionListener(action);
    }

}