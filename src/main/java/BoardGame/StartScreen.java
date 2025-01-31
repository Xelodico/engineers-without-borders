package BoardGame;

import java.awt.Color;

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

    private final int buttonWidth = 240;
    private final int buttonHeight = 55;

    private final ImageIcon playerBackgroundIcon = new ImageIcon(getClass().getResource("/images/playerTurn.png"));
    private final ImageIcon addPlayerIcon = new ImageIcon(getClass().getResource("/images/addPlayer.png"));

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


        for (int i = 0; i < playerButtons.length; i++) {
            playerButtons[i] = createPlayerButton(i);

            playerTextFields[i] = new JTextField();
            playerTextFields[i].setBounds(0, 0, buttonWidth, buttonHeight);
            playerTextFields[i].setHorizontalAlignment(JTextField.CENTER);
            playerTextFields[i].setFont(new java.awt.Font("Segoe UI", 0, 16));
            playerTextFields[i].setBorder(null);
            playerTextFields[i].setOpaque(false);

            if (i != 0) {
                playerButtons[i].setIcon(addPlayerIcon);
                playerButtons[i].setVisible(false);
                playerButtons[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                playerTextFields[i].setText("Player " + (i + 1));

                final int index = i;
                playerButtons[i].addActionListener(_ -> {
                    playerButtons[index].setIcon(playerBackgroundIcon);
                    playerButtons[index].setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                    if (index < playerButtons.length - 1) {
                        playerButtons[index + 1].setVisible(true);
                    }
                    playerButtons[index].add(playerTextFields[index]);
                    numOfPlayers++;
                });

            } else {
                playerButtons[i].setIcon(playerBackgroundIcon);
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
        startButton.addActionListener(_ -> {
            GameSystem.setTurnOrder(getPlayers());
            GameSystem.startGame();
        });

        add(startButton);
    }

    private JButton createPlayerButton(int index) {
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBounds((WIDTH - 240) / 2, (int) (HEIGHT * 0.2) + (index * (55 + 10)), 240, 55);
        button.setHorizontalTextPosition(JButton.CENTER);
        return button;
    }

    /**
     * Gets the players from the text fields on the start screen and initializes
     * them.
     * 
     * @return An array of Player objects representing the players.
     */
    public Player[] getPlayers() {
        Player[] players = new Player[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            players[i] = new Player();
            players[i].setName(playerTextFields[i].getText());
        }

        int[] startingCoords = { 0, 9, 90, 99 };
        for (int i = 0; i < numOfPlayers; i++) {
            players[i].setCoord(startingCoords[i]);
        }
        return players;
    }

}
