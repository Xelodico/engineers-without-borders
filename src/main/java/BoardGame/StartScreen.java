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

    private static final int WIDTH = BoardGameUI.WINDOW_WIDTH;
    private static final int HEIGHT = BoardGameUI.WINDOW_HEIGHT;

    private static final int BUTTON_WIDTH = 240;
    private static final int BUTTON_HEIGHT = 55;
    private static final int BUTTON_SPACING = 10;
    private static final int MAX_PLAYERS = 4;

    private static final int[] STARTING_COORDS = { 65, 66, 77, 78 };

    private final ImageIcon playerBackgroundIcon = new ImageIcon(getClass().getResource("/images/playerTurn.png"));
    private final ImageIcon addPlayerIcon = new ImageIcon(getClass().getResource("/images/addPlayer.png"));

    private JButton[] playerButtons = new JButton[MAX_PLAYERS]; // Array to hold buttons
    private JTextField[] playerTextFields = new JTextField[MAX_PLAYERS]; // Array to hold text fields for player names
    private int numOfPlayers = 1;

    public StartScreen() {
        setLayout(null);
        setBackground(new Color(240, 240, 240));
        setBounds(0, 0, WIDTH, HEIGHT);

        addTitle();
        initializePlayerInputs();
        addStartButton();
    }

    private void addTitle() {
        JLabel title = new JLabel("Welcome to Pavers Valley!");
        title.setBounds(0, 0, WIDTH, (int) (HEIGHT * 0.15));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.BOTTOM);
        title.setFont(new java.awt.Font("Segoe UI", 0, 26));
        add(title);
    }

    private void initializePlayerInputs() {
        for (int i = 0; i < playerButtons.length; i++) {
            playerButtons[i] = createPlayerButton(i);
            playerTextFields[i] = createPlayerTextField();

            if (i != 0) {
                addNewPlayerButton(i);
            } else {
                playerButtons[i].setIcon(playerBackgroundIcon);
                playerTextFields[i].setText("Player 1");
                playerButtons[i].add(playerTextFields[i]);
            }

            add(playerButtons[i]);
        }

        playerButtons[1].setVisible(true);
    }

    private JButton createPlayerButton(int index) {
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBounds((WIDTH - 240) / 2, (int) (HEIGHT * 0.2) + (index * (BUTTON_HEIGHT + BUTTON_SPACING)),
                BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setHorizontalTextPosition(JButton.CENTER);
        return button;
    }

    private void addNewPlayerButton(int i) {
        playerButtons[i].setIcon(addPlayerIcon);
        playerButtons[i].setVisible(false);
        playerButtons[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        playerTextFields[i].setText("Player " + (i + 1));

        final int index = i;
        playerButtons[i].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerButtons[index].setIcon(playerBackgroundIcon);
                playerButtons[index].setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                if (index < MAX_PLAYERS - 1) {
                    playerButtons[index + 1].setVisible(true);
                }
                playerButtons[index].add(playerTextFields[index]);
                numOfPlayers++;
            }
        });
    }

    private JTextField createPlayerTextField() {
        JTextField textField = new JTextField();
        textField.setBounds(0, 0, 240, 55);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new java.awt.Font("Segoe UI", 0, 16));
        textField.setBorder(null);
        textField.setOpaque(false);
        return textField;
    }

    private void addStartButton() {
        JButton startButton = new JButton("Start Game");
        startButton.setBounds((WIDTH - BUTTON_WIDTH) / 2, (int) (HEIGHT * 0.8), BUTTON_WIDTH, BUTTON_HEIGHT);
        startButton.setFont(new java.awt.Font("Segoe UI", 0, 16));
        startButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameSystem.setTurnOrder(getPlayers());
                GameSystem.startGame();
            }
        });

        add(startButton);
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

        for (int i = 0; i < numOfPlayers; i++) {
            players[i].setCoord(STARTING_COORDS[i]);
        }
        return players;
    }

}
