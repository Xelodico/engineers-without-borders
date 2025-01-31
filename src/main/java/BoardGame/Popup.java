package BoardGame;

import java.awt.Color;
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
 * 
 * This class extends JPanel and uses various Swing components to create the popup
 * window. It includes a JLabel for the title, a JTextArea for the description, and
 * two JButtons for the Yes and No actions. The layout and appearance of the popup
 * are set within the constructor.
 * 
 * When either button is clicked, the popup is removed from its parent container
 * and set to null.
 * 
 * @author Nathan Watkins
 * @author Curtis McCartney
 * 
 * @param title The title text to be displayed at the top of the popup.
 * @param desc The description text to be displayed in the body of the popup.
 * @param yesButton The text to be displayed on the Yes button.
 * @param noButton The text to be displayed on the No button.
 */
public class Popup extends JPanel {

    private JLabel popupTitle;
    private JTextArea popupDesc;
    private JButton yesButtonComponent;
    private JButton noButtonComponent;

    public Popup(String title, String desc, String yesButtonText, String noButtonText, ActionListener yesAction, ActionListener noAction) {
        final int width = 400;
        final int height = 400;

        setLayout(null);
        setBounds(62, 56, width, height);
        setBackground(new java.awt.Color(240, 240, 240));
        setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setVisible(false);

        popupTitle = new JLabel(title);
        popupTitle.setFont(new java.awt.Font("Segoe UI", 1, 24));
        popupTitle.setHorizontalAlignment(SwingConstants.CENTER);
        popupTitle.setText("<html>" + title + "</html>");
        popupTitle.setBounds(0, 0, width, 70);
        add(popupTitle);

        popupDesc = new JTextArea(desc);
        popupDesc.setFont(new java.awt.Font("Segoe UI", 0, 18));
        popupDesc.setLineWrap(true);
        popupDesc.setWrapStyleWord(true);
        popupDesc.setEditable(false);
        popupDesc.setBackground(new java.awt.Color(240, 240, 240));
        popupDesc.setBorder(null);
        popupDesc.setEnabled(false);
        popupDesc.setDisabledTextColor(Color.BLACK);
        popupDesc.setBounds(10, 80, width - 20, height - 200);
        add(popupDesc);

        yesButtonComponent = new JButton(yesButtonText);
        yesButtonComponent.setFont(new java.awt.Font("Segoe UI", 0, 18));
        yesButtonComponent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        yesButtonComponent.setFocusPainted(false);
        yesButtonComponent.setContentAreaFilled(false);
        yesButtonComponent.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        yesButtonComponent.setBounds((width / 2) - 110, height - 60, 100, 30);
        add(yesButtonComponent);

        noButtonComponent = new JButton(noButtonText);
        noButtonComponent.setFont(new java.awt.Font("Segoe UI", 0, 18));
        noButtonComponent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        noButtonComponent.setFocusPainted(false);
        noButtonComponent.setContentAreaFilled(false);
        noButtonComponent.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        noButtonComponent.setBounds((width / 2) + 10, height - 60, 100, 30);
        add(noButtonComponent);

        yesButtonComponent.addActionListener(yesAction);
        noButtonComponent.addActionListener(noAction);
    }

    public void setTitle(String title) {
        popupTitle.setText("<html>" + title + "</html>");
    }

    public void setDescription(String desc) {
        popupDesc.setText(desc);
    }

    public void setYesButtonText(String text) {
        yesButtonComponent.setText(text);
    }

    public void setNoButtonText(String text) {
        noButtonComponent.setText(text);
    }

}