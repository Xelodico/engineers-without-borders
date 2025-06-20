package BoardGame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.util.Objects;

import javax.swing.*;

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
    private static final int BUTTON_SPACING = 20;
    private static final int MAX_PLAYERS = 4;

    private static final int[] STARTING_COORDS = GameSystem.getSpawnLocations();

    private final ImageIcon playerBackground = new ImageIcon(
            Objects.requireNonNull(getClass().getResource("/images/PlayerTurn.png")));
    private final ImageIcon playerBackgroundIcon = new ImageIcon(
            playerBackground.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_SMOOTH));
    private final ImageIcon addPlayer = new ImageIcon(
            Objects.requireNonNull(getClass().getResource("/images/addPlayer.png")));
    private final ImageIcon addPlayerIcon = new ImageIcon(
            addPlayer.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_SMOOTH));

    private final JButton[] playerButtons = new JButton[MAX_PLAYERS]; // Array to hold buttons
    private final JTextField[] playerTextFields = new JTextField[MAX_PLAYERS]; // Array to hold text fields for player
                                                                               // names
    private int numOfPlayers = 1;

    /**
     * Constructs a StartScreen object and initializes the UI components.
     */
    public StartScreen() {
        setLayout(null);
        setBackground(new Color(240, 240, 240));
        setBounds(0, 0, WIDTH, HEIGHT);

        addTitle();
        initializePlayerInputs();
        addStartButton();
    }

    /**
     * Adds the title label to the start screen.
     */
    private void addTitle() {
        JLabel title = new JLabel("Welcome to Pavers Valley!");
        title.setBounds(0, 0, WIDTH, (int) (HEIGHT * 0.15));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.BOTTOM);
        title.setFont(new Font("Segue UI", Font.PLAIN, 26));
        add(title);
    }

    /**
     * Initializes the player input fields and buttons.
     */
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

    /**
     * Creates a player button with the specified index.
     *
     * @param index the index of the player button
     * @return the created JButton
     */
    private JButton createPlayerButton(int index) {
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBounds((WIDTH - BUTTON_WIDTH) / 2, (int) (HEIGHT * 0.2) + (index * (BUTTON_HEIGHT + BUTTON_SPACING)),
                BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setHorizontalTextPosition(JButton.CENTER);

        return button;
    }

    /**
     * Adds a new player button to the start screen.
     *
     * @param i the index of the player button
     */
    private void addNewPlayerButton(int i) {
        playerButtons[i].setIcon(addPlayerIcon);
        playerButtons[i].setVisible(false);
        playerButtons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));

        playerTextFields[i].setText("Player " + (i + 1));

        final int index = i;
        playerButtons[i].addActionListener(e -> {
            playerButtons[index].setIcon(playerBackgroundIcon);
            playerButtons[index].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            playerTextFields[i].setVisible(true);
            if (index < MAX_PLAYERS - 1) {
                playerButtons[index + 1].setVisible(true);
            }
            playerButtons[index].add(playerTextFields[index]);
            numOfPlayers++;
        });
    }

    /**
     * Creates a player text field.
     *
     * @return the created JTextField
     */
    private JTextField createPlayerTextField() {
        JTextField textField = new JTextField();
        textField.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Segue UI", Font.PLAIN, 16));
        textField.setBorder(null);
        textField.setOpaque(false);
        return textField;
    }

    /**
     * Adds the start button to the start screen.
     */
    private void addStartButton() {
        JButton startButton = new JButton("Start Game");
        startButton.setBounds((WIDTH - BUTTON_WIDTH) / 2, (int) (HEIGHT * 0.8), BUTTON_WIDTH, BUTTON_HEIGHT);
        startButton.setFont(new Font("Segue UI", Font.PLAIN, 16));
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.addActionListener(e -> {
            GameSystem.setTurnOrder(getPlayers());
            GameSystem.startGame();
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
            players[i].setCoord(STARTING_COORDS[i % STARTING_COORDS.length]);
        }

        return players;
    }

}