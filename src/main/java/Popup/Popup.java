package Popup;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

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
    private final JTextArea popupDesc;
    private final JButton yesButtonComponent;
    private final JButton noButtonComponent;

    private boolean yesButtonVisible = true;
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
        setLayout(null);
        setBounds((650/2 - WIDTH/2) + 12, (650/2 - HEIGHT/2) + 6, WIDTH, HEIGHT);
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

        yesButtonComponent = new JButton(yesButtonText);
        yesButtonComponent.setFont(new java.awt.Font("Segue UI", Font.PLAIN, 18));
        yesButtonComponent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        yesButtonComponent.setFocusPainted(false);
        yesButtonComponent.setContentAreaFilled(false);
        yesButtonComponent.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        yesButtonComponent.setBounds((WIDTH / 2) - 110, HEIGHT - 60, 100, 30);
        add(yesButtonComponent);

        noButtonComponent = new JButton(noButtonText);
        noButtonComponent.setFont(new java.awt.Font("Segue UI", Font.PLAIN, 18));
        noButtonComponent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        noButtonComponent.setFocusPainted(false);
        noButtonComponent.setContentAreaFilled(false);
        noButtonComponent.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        noButtonComponent.setBounds((WIDTH / 2) + 10, HEIGHT - 60, 100, 30);
        add(noButtonComponent);

        yesButtonComponent.addActionListener(yesAction);
        noButtonComponent.addActionListener(noAction);
    }

    public void setTitle(String title) {
        popupTitle.setText(title);
    }

    public void setDescription(String desc) {
        popupDesc.setText(desc);
    }

    public void positionButtons() {
        if (yesButtonVisible && noButtonVisible) {
            yesButtonComponent.setBounds((WIDTH / 2) - 110, HEIGHT - 60, 100, 30);
            noButtonComponent.setBounds((WIDTH / 2) + 10, HEIGHT - 60, 100, 30);
        } else if (yesButtonVisible) {
            yesButtonComponent.setBounds((WIDTH / 2) - 50, HEIGHT - 60, 100, 30);
        } else if (noButtonVisible) {
            noButtonComponent.setBounds((WIDTH / 2) - 50, HEIGHT - 60, 100, 30);
        }
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
        positionButtons();
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
        positionButtons();
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