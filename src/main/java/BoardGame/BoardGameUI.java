package BoardGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import GameSystem.GameSystem;
import square.SquareType;

/**
 * The BoardGameUI class represents the user interface for the board game.
 * It extends JFrame and provides the main window for the game, including
 * the game board and side panel with player resources and controls.
 * 
 * The class includes methods for initializing the UI components, moving players
 * on the board, and handling user interactions such as button clicks.
 * 
 * @param players A list of Player objects representing the players in the game.
 * 
 * @author Nathan Watkins
 * @author Curtis McCartney
 */
public class BoardGameUI extends JFrame {

    Player[] players;
    String player1Name;
    String player2Name;
    String player3Name;
    String player4Name;

    protected static final int WINDOW_WIDTH = 1075;
    protected static final int WINDOW_HEIGHT = 705;
    private static final int BOARD_WIDTH = 650;
    private static final int BOARD_HEIGHT = BOARD_WIDTH;

    public StartScreen startScreen;

    /**
     * Creates a new BoardGameUI instance with the specified list of players.
     * 
     * @param players A list of Player objects representing the players in the game.
     */
    public BoardGameUI(Board gameBoard) {
        this.players = GameSystem.getTurnOrder();
        this.gameBoard = gameBoard;

        
        startScreen = new StartScreen();
        add(startScreen);
        startScreen.setVisible(true);
        
        popup = new Popup("", "", "", "", null, null);
        add(popup);
        
        initComponents();
        setVisible(true);
        setResizable(false);
    }

    public void showPopup(String title, String desc, String yesButton, String noButton) {
        popup.setTitle(title);
        popup.setDescription(desc);
        popup.setYesButtonText(yesButton);
        popup.setNoButtonText(noButton);
        popup.setVisible(true);
    }

    /**
     * Refreshes the game window to update the player resources and board state.
     */
    public void refresh() {
        this.players = GameSystem.getTurnOrder();
        gameBoard.refresh();
        remove(sidePanelContainer);
        initComponents();
        revalidate();
        repaint();
        sidePanelContainer.setVisible(true);
    }

    /**
     * Starts the game by hiding the start screen and showing the game board.
     */
    public void startGame() {
        startScreen.setVisible(false);
        remove(startScreen);
    }
    
    private void setupArrowButtons() {
        arrowsContainer = new JPanel();
        arrowDown = new JButton();
        arrowUp = new JButton();
        arrowLeft = new JButton();
        arrowRight = new JButton();

        arrowsContainer.setLayout(null);

        JButton[] arrowButtons = { arrowDown, arrowUp, arrowLeft, arrowRight };
        String[] arrowIcons = { "/images/arrows/ArrowDown.png", "/images/arrows/ArrowUp.png",
                "/images/arrows/ArrowLeft.png", "/images/arrows/ArrowRight.png" };
        for (int i = 0; i < arrowButtons.length; i++) {
            arrowButtons[i].setIcon(new ImageIcon(getClass().getResource(arrowIcons[i])));
            arrowButtons[i].setBorder(null);
            arrowButtons[i].setContentAreaFilled(false);
            arrowButtons[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            arrowButtons[i].setFocusPainted(false);

            if (i == 0) { // Arrow Down
                arrowButtons[i].setBounds(45, 46, 46, 46);
            } else if (i == 1) { // Arrow Up
                arrowButtons[i].setBounds(45, 1, 46, 46);
            } else if (i == 2) { // Arrow Left
                arrowButtons[i].setBounds(0, 46, 46, 46);
            } else if (i == 3) { // Arrow Right
                arrowButtons[i].setBounds(90, 46, 46, 46);
            }

            arrowsContainer.add(arrowButtons[i]);
        }

        setupArrowButtonAction(arrowRight, Direction.RIGHT);
        setupArrowButtonAction(arrowLeft, Direction.LEFT);
        setupArrowButtonAction(arrowUp, Direction.UP);
        setupArrowButtonAction(arrowDown, Direction.DOWN);

        sidePanelContainer.add(arrowsContainer);
        arrowsContainer.setBounds(10, BOARD_HEIGHT - 92, 138, 92);
    }

    private void setupArrowButtonAction(JButton button, Direction direction) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameSystem.movePlayer(direction);
                if (GameSystem.getPlayerAt().getMovesLeft() == 0) {
                    movesLeftLabel.setVisible(false);
                    endTurnButton.setVisible(true);
                } else {
                    movesLeftLabel.setText("Moves Left: " + GameSystem.getPlayerAt().getMovesLeft());
                }
            }
        });
    }

    private void setupLabelsAndButtons() {
        roundNumberGraphic = new JLabel();
        playerTurnGraphic = new JLabel();
        helpButton = new JButton();
        rollDiceButton = new JButton();
        endTurnButton = new JButton();
        movesLeftLabel = new JLabel();

        roundNumberGraphic.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        roundNumberGraphic.setIcon(new ImageIcon(getClass().getResource("/images/Round.png"))); // NOI18N
        roundNumberGraphic.setText("Round " + GameSystem.getRoundNumber());
        roundNumberGraphic.setHorizontalTextPosition(SwingConstants.CENTER);
        sidePanelContainer.add(roundNumberGraphic);
        roundNumberGraphic.setBounds(251, 0, 122, 57);

        playerTurnGraphic.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        playerTurnGraphic.setIcon(new ImageIcon(getClass().getResource("/images/PlayerTurn.png"))); // NOI18N
        playerTurnGraphic.setText("     " + GameSystem.getPlayerAt().getName() + "'s Turn");
        playerTurnGraphic.setToolTipText("");
        playerTurnGraphic.setHorizontalTextPosition(SwingConstants.CENTER);
        sidePanelContainer.add(playerTurnGraphic);
        playerTurnGraphic.setBounds(0, 0, 241, 57);

        helpButton.setIcon(new ImageIcon(getClass().getResource("/images/Help.png"))); // NOI18N
        helpButton.setBorder(null);
        helpButton.setContentAreaFilled(false);
        helpButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sidePanelContainer.add(helpButton);
        helpButton.setBounds(330, BOARD_HEIGHT - 55 - 37, 37, 37);

        rollDiceButton.setIcon(new ImageIcon(getClass().getResource("/images/RollDiceButton.png"))); // NOI18N
        rollDiceButton.setBorder(null);
        rollDiceButton.setContentAreaFilled(false);
        rollDiceButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rollDiceButton.setFocusPainted(false);
        sidePanelContainer.add(rollDiceButton);
        rollDiceButton.setBounds(170, BOARD_HEIGHT - 47, 192, 47);

        rollDiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameSystem.getPlayerAt().rollDie();
                rollDiceButton.setVisible(false);
                movesLeftLabel.setText("Moves Left: " + GameSystem.getPlayerAt().getMovesLeft());
                movesLeftLabel.setVisible(true);
            }
        });

        movesLeftLabel.setIcon(new ImageIcon(getClass().getResource("/images/buttonBackground.png")));
        movesLeftLabel.setBounds(170, BOARD_HEIGHT - 47, 192, 47);
        movesLeftLabel.setVisible(false);
        movesLeftLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        movesLeftLabel.setFont(new java.awt.Font("Segoe UI", 0, 26));
        sidePanelContainer.add(movesLeftLabel);

        endTurnButton.setIcon(new ImageIcon(getClass().getResource("/images/buttonBackground.png")));
        endTurnButton.setBounds(170, BOARD_HEIGHT - 47, 192, 47);
        endTurnButton.setVisible(false);
        endTurnButton.setText("End Turn");
        endTurnButton.setFont(new java.awt.Font("Segoe UI", 0, 26));
        endTurnButton.setHorizontalTextPosition(SwingConstants.CENTER);
        endTurnButton.setContentAreaFilled(false);
        endTurnButton.setBorder(null);
        endTurnButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sidePanelContainer.add(endTurnButton);

        endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameSystem.nextTurn();
                playerTurnGraphic.setText("     " + GameSystem.getPlayerAt().getName() + "'s Turn");
                roundNumberGraphic.setText("Round " + GameSystem.getRoundNumber());
                movesLeftLabel.setVisible(false);
                endTurnButton.setVisible(false);
                rollDiceButton.setVisible(true);
            }
        });
    }

    private void setupPlayerPanels() {
        Player1Resources = new JPanel();
        player1ResourceTitle = new JLabel();
        player1Satisfaction = new JLabel();
        player1Knowledge = new JLabel();
        player1Asphalt = new JLabel();
        Player2Resources = new JPanel();
        player2ResourceTitle = new JLabel();
        player2Satisfaction = new JLabel();
        player2Knowledge = new JLabel();
        player2Asphalt = new JLabel();
        Player3Resources = new JPanel();
        player3ResourceTitle = new JLabel();
        player3Satisfaction = new JLabel();
        player3Knowledge = new JLabel();
        player3Asphalt = new JLabel();
        Player4Resources = new JPanel();
        player4ResourceTitle = new JLabel();
        player4Satisfaction = new JLabel();
        player4Knowledge = new JLabel();
        player4Asphalt = new JLabel();

        JPanel[] playerResources = { Player1Resources, Player2Resources, Player3Resources, Player4Resources };
        JLabel[] playerResourceTitles = { player1ResourceTitle, player2ResourceTitle, player3ResourceTitle,
                player4ResourceTitle };
        JLabel[] playerSatisfactions = { player1Satisfaction, player2Satisfaction, player3Satisfaction,
                player4Satisfaction };
        JLabel[] playerKnowledges = { player1Knowledge, player2Knowledge, player3Knowledge, player4Knowledge };
        JLabel[] playerAsphalts = { player1Asphalt, player2Asphalt, player3Asphalt, player4Asphalt };

        for (int i = 0; i < players.length; i++) {
            Player currentPlayer = players[i];

            playerResources[i].setLayout(null);

            playerResourceTitles[i].setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
            playerResourceTitles[i].setText(currentPlayer.getName()); // Get name from the Player object
            playerResourceTitles[i].setBounds(10, -3, 350, 30);
            playerResources[i].add(playerResourceTitles[i]);

            playerSatisfactions[i].setBounds(310, 23, 60, 40);
            playerSatisfactions[i].setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
            playerSatisfactions[i].setText("999");
            playerResources[i].add(playerSatisfactions[i]);

            playerKnowledges[i].setBounds(185, 23, 60, 40);
            playerKnowledges[i].setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
            playerKnowledges[i].setText("999");
            playerResources[i].add(playerKnowledges[i]);

            playerAsphalts[i].setBounds(60, 23, 60, 40);
            playerAsphalts[i].setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
            playerAsphalts[i].setText("999");
            playerResources[i].add(playerAsphalts[i]);

            JLabel background = new JLabel();
            background.setIcon(new ImageIcon(getClass().getResource("/images/Resources.png")));
            background.setBounds(0, 0, 373, 74);
            playerResources[i].add(background);
            playerResources[i].setBounds(0, 62 + (i * 79), 373, 74);
            sidePanelContainer.add(playerResources[i]);
        }
    }

    /**
     * Initializes the UI components for the game window, including the game board,
     * side panel with player resources, and control buttons for moving players and
     * ending turns.
     */
    private void initComponents() {
        sidePanelContainer = new JPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        sidePanelContainer.setLayout(null);
        sidePanelContainer.setVisible(false);

        setupArrowButtons();
        setupLabelsAndButtons();
        setupPlayerPanels();

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(gameBoard, GroupLayout.PREFERRED_SIZE, BOARD_WIDTH,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(sidePanelContainer, GroupLayout.DEFAULT_SIZE, 384,
                                        Short.MAX_VALUE)
                                .addGap(0, 0, 0)));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(gameBoard, GroupLayout.PREFERRED_SIZE, BOARD_HEIGHT,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(sidePanelContainer, GroupLayout.PREFERRED_SIZE, BOARD_HEIGHT,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)));

        setSize(new java.awt.Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLocationRelativeTo(null);
    }

    private JLabel movesLeftLabel;
    private JButton endTurnButton;
    private JPanel Player1Resources;
    private JPanel Player2Resources;
    private JPanel Player3Resources;
    private JPanel Player4Resources;
    private JButton arrowDown;
    private JButton arrowLeft;
    private JButton arrowRight;
    private JButton arrowUp;
    private JPanel arrowsContainer;
    public Board gameBoard;
    private JButton helpButton;
    private JLabel player1Asphalt;
    private JLabel player1Knowledge;
    private JLabel player1ResourceTitle;
    private JLabel player1Satisfaction;
    private JLabel player2Asphalt;
    private JLabel player2Knowledge;
    private JLabel player2ResourceTitle;
    private JLabel player2Satisfaction;
    private JLabel player3Asphalt;
    private JLabel player3Knowledge;
    private JLabel player3ResourceTitle;
    private JLabel player3Satisfaction;
    private JLabel player4Asphalt;
    private JLabel player4Knowledge;
    private JLabel player4ResourceTitle;
    private JLabel player4Satisfaction;
    private JLabel playerTurnGraphic;
    private JButton rollDiceButton;
    private JLabel roundNumberGraphic;
    private JPanel sidePanelContainer;
    private Popup popup;
}
