package BoardGame;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Popup extends JPanel {
    private JLabel popupTitle;
    private JTextArea popupDesc;
    private JButton yesButtonComponent;
    private JButton noButtonComponent;

    public Popup(String title, String desc, String yesButton, String noButton) {
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setVisible(true); // Initially set to invisible

        popupTitle = new JLabel(title);
        popupTitle.setFont(new java.awt.Font("Segoe UI", 1, 24));
        popupTitle.setHorizontalAlignment(SwingConstants.CENTER);
        popupTitle.setText("<html>" + title + "</html>");
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
        add(popupDesc);

        yesButtonComponent = new JButton(yesButton);
        yesButtonComponent.setFont(new java.awt.Font("Segoe UI", 0, 18));
        yesButtonComponent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        yesButtonComponent.setFocusPainted(false);
        yesButtonComponent.setContentAreaFilled(false);
        yesButtonComponent.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        add(yesButtonComponent);

        noButtonComponent = new JButton(noButton);
        noButtonComponent.setFont(new java.awt.Font("Segoe UI", 0, 18));
        noButtonComponent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        noButtonComponent.setFocusPainted(false);
        noButtonComponent.setContentAreaFilled(false);
        noButtonComponent.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        add(noButtonComponent);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int parentWidth = getParent().getWidth();
                int parentHeight = getParent().getHeight();
                int popupWidth = 400;
                int popupHeight = 400;
                int x = (parentWidth - popupWidth) / 2;
                int y = (parentHeight - popupHeight) / 2;

                // Set the bounds to center the popup
                setBounds(x, y, popupWidth, popupHeight);
                setVisible(true); // Make the popup visible after centering

                popupTitle.setBounds(0, 0, popupWidth, 70);
                popupDesc.setBounds(10, 80, popupWidth - 20, popupHeight - 200);
                yesButtonComponent.setBounds(popupWidth / 2 - 60, popupHeight - 60, 100, 30);
                noButtonComponent.setBounds(popupWidth / 2 + 10, popupHeight - 60, 100, 30);
            }
        });
    }
}