package BoardGame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Popup extends JPanel {

    private JPanel popup;
    private JLabel popupTitle;
    private JTextArea popupDesc;
    private JButton yesButtonComponent;
    private JButton noButtonComponent;

    public Popup(String title, String desc, String yesButton, String noButton) {

        popup = this;

        final int width = 400;
        final int height = 400;

        setLayout(null);
        setBounds(62, 56, width, height);
        setBackground(new java.awt.Color(240, 240, 240));
        setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setVisible(true);

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

        yesButtonComponent = new JButton(yesButton);
        yesButtonComponent.setFont(new java.awt.Font("Segoe UI", 0, 18));
        yesButtonComponent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        yesButtonComponent.setFocusPainted(false);
        yesButtonComponent.setContentAreaFilled(false);
        yesButtonComponent.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        yesButtonComponent.setBounds((width / 2) - 110, height - 60, 100, 30);
        add(yesButtonComponent);

        noButtonComponent = new JButton(noButton);
        noButtonComponent.setFont(new java.awt.Font("Segoe UI", 0, 18));
        noButtonComponent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        noButtonComponent.setFocusPainted(false);
        noButtonComponent.setContentAreaFilled(false);
        noButtonComponent.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        noButtonComponent.setBounds((width / 2) + 10, height - 60, 100, 30);
        add(noButtonComponent);

        yesButtonComponent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                popup.setVisible(false);
                popup.getParent().remove(popup);
                popup = null;
            }
        });

        noButtonComponent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                popup.setVisible(false);
                popup.getParent().remove(popup);
                popup = null;
            }
        });
    }
}