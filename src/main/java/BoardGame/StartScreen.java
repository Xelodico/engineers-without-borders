package BoardGame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GameSystem.GameSystem;

/**
 * The StartScreen class represents the initial screen of the board game
 * application.
 * It extends JPanel and provides a user interface for players to enter their
 * names
 * and start the game.
 * 
 * @author Nathan Watkins
 */
public class StartScreen extends JPanel {

    private final int WIDTH = 904;
    private final int HEIGHT = 516;

    private JButton[] playerButtons = new JButton[4]; // Array to hold buttons
    private JTextField[] playerTextFields = new JTextField[4]; // Array to hold text fields for player names
    private int numOfPlayers = 1;

    public StartScreen() {
        setLayout(null);
        setBackground(new Color(240, 240, 240));
        setBounds(0, 0, WIDTH, HEIGHT);

        JLabel title = new JLabel("Welcome to Pavers Valley!");
        title.setBounds(0, 0, WIDTH, (int) (HEIGHT * 0.15));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.BOTTOM);
        title.setFont(new java.awt.Font("Segoe UI", 0, 26));
        add(title);

        String imagePath = "/images/addPlayer.png";
        int buttonWidth = 240;
        int buttonHeight = 55;
        int yOffset = (int) (HEIGHT * 0.2);

        for (int i = 0; i < playerButtons.length; i++) {
            playerButtons[i] = new JButton();
            playerButtons[i].setBorderPainted(false);
            playerButtons[i].setFocusPainted(false);
            playerButtons[i].setContentAreaFilled(false);
            playerButtons[i].setBounds((WIDTH - buttonWidth) / 2, yOffset + (i * (buttonHeight + 10)), buttonWidth,
                    buttonHeight);
            playerButtons[i].setHorizontalTextPosition(JButton.CENTER);

            playerTextFields[i] = new JTextField();
            playerTextFields[i].setBounds(0, 0, buttonWidth, buttonHeight);
            playerTextFields[i].setHorizontalAlignment(JTextField.CENTER);
            playerTextFields[i].setFont(new java.awt.Font("Segoe UI", 0, 16));
            playerTextFields[i].setBorder(null);
            playerTextFields[i].setOpaque(false);

            if (i != 0) {
                playerButtons[i].setIcon(new ImageIcon(getClass().getResource(imagePath)));
                playerButtons[i].setVisible(false);
                playerButtons[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                playerTextFields[i].setText("Player " + (i + 1));

                final int index = i;
                playerButtons[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        playerButtons[index].setIcon(new ImageIcon(getClass().getResource("/images/playerTurn.png")));
                        playerButtons[index].setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                        if (index < playerButtons.length - 1) {
                            playerButtons[index + 1].setVisible(true);
                        }
                        playerButtons[index].add(playerTextFields[index]);
                        numOfPlayers++;
                    }

                });
            } else {
                playerButtons[i].setIcon(new ImageIcon(getClass().getResource("/images/playerTurn.png")));
                playerTextFields[i].setText("Player 1");
                playerButtons[i].add(playerTextFields[i]);
            }

            add(playerButtons[i]);
        }

        playerButtons[1].setVisible(true);

        JButton startButton = new JButton("Start Game");
        startButton.setBounds((WIDTH - buttonWidth) / 2, (int) (HEIGHT * 0.8), buttonWidth, buttonHeight);
        startButton.setFont(new java.awt.Font("Segoe UI", 0, 16));
        startButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start game logic
                GameSystem.setTurnOrder(getPlayers());
                GameSystem.startGame();
            }
        });
        add(startButton);
    }

    
    /**
     * Gets the players from the text fields on the start screen.
     * 
     * @return An array of Player objects representing the players.
     */
    public Player[] getPlayers() {
        Player[] players = new Player[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            players[i] = new Player();
            players[i].setName(playerTextFields[i].getText());
        }
        switch (numOfPlayers) {
            case 1:
                players[0].setCoord(0);
                break;
            case 2:
                players[0].setCoord(0);
                players[1].setCoord(9);
                break;
            case 3:
                players[0].setCoord(0);
                players[1].setCoord(9);
                players[2].setCoord(90);
                break;
            case 4:
                players[0].setCoord(0);
                players[1].setCoord(9);
                players[2].setCoord(90);
                players[3].setCoord(99);
                break;

            default:
                break;
        }
        return players;
    }

}
