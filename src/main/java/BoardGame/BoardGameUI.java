package BoardGame;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;

import javax.swing.*;

import GameSystem.GameSystem;
import Popup.EndGame;
import Popup.Journal;
import Popup.Popup;
import Popup.Shop;
import Popup.Tutorial;
import Popup.TransferPopup;

/**
 * The BoardGameUI class represents the user interface for the board game.
 * It extends JFrame and provides the main window for the game, including
 * the game board and side panel with player resources and controls.
 * The class includes methods for initializing the UI components, moving players
 * on the board, and handling user interactions such as button clicks.
 *
 * @author Nathan Watkins
 * @author Curtis McCartney
 */
public class BoardGameUI extends JFrame {

    Player[] players;

    public static final int WINDOW_WIDTH = 1075;
    public static final int WINDOW_HEIGHT = 735;
    private static final int BOARD_WIDTH = 650, BOARD_HEIGHT = 650;
    private static final int SIDE_PANEL_WIDTH = 384;

    public StartScreen startScreen;

    public TransferPopup transferPopup;

    public EndGame endGame;

    /**
     * Creates a new BoardGameUI instance with the specified list of players.
     */
    public BoardGameUI(Board gameBoard) {
        this.players = GameSystem.getTurnOrder();
        this.gameBoard = gameBoard;

        startScreen = new StartScreen();
        startScreen.setVisible(true);

        popup = new Popup("", "", "", "", null, null);
        journal = new Journal();
        shop = new Shop();
        endGame = new EndGame();
        transferPopup = new TransferPopup("Transfer Task", "Description");
        tutorial = new Tutorial();

        dimBackground = new JPanel();
        dimBackground.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        dimBackground.setBackground(new java.awt.Color(0, 0, 0, 150));
        dimBackground.setVisible(false);
        add(dimBackground);

        JLayeredPane layeredPane = getLayeredPane();
        layeredPane.add(startScreen, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(popup, JLayeredPane.POPUP_LAYER);
        layeredPane.add(tutorial, JLayeredPane.POPUP_LAYER);
        layeredPane.add(transferPopup, JLayeredPane.POPUP_LAYER);
        layeredPane.add(journal, JLayeredPane.POPUP_LAYER);
        layeredPane.add(shop, JLayeredPane.POPUP_LAYER);
        layeredPane.add(endGame, JLayeredPane.POPUP_LAYER);

        initComponents();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public void showPopup(String title, String desc, String yesButton, String noButton, ActionListener yesAction,
            ActionListener noAction) {
        popup.setTitle(title);
        popup.setDescription(desc);
        popup.setYesButtonText(yesButton);
        popup.setNoButtonText(noButton);
        popup.setYesButtonAction(yesAction);
        popup.setNoButtonAction(noAction);
        popup.setVisible(true);
        dimBackground.setVisible(true);
        toggleEnableButtons();
    }

    public void hidePopup() {
        popup.setVisible(false);
        dimBackground.setVisible(false);
        toggleEnableButtons();
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

    public void setResourceValues() {
        for (int i = 0; i < players.length; i++) {
            Player currentPlayer = players[i];
            
            // Ensure resource values are valid (non-negative)
            int volunteers = currentPlayer.getResource(ResourceType.VOLUNTEERS);
            int knowledge = currentPlayer.getResource(ResourceType.KNOWLEDGE);
            int asphalt = currentPlayer.getResource(ResourceType.ASPHALT);
            int influence = currentPlayer.getResource(ResourceType.INFLUENCE);
            int money = currentPlayer.getMoney();
            int score = currentPlayer.getScore();
    
            // Set text for player resource labels
            playerSatisfactions[i].setText(volunteers >= 0 ? Integer.toString(volunteers) : "0");
            playerKnowledges[i].setText(knowledge >= 0 ? Integer.toString(knowledge) : "0");
            playerAsphalts[i].setText(asphalt >= 0 ? Integer.toString(asphalt) : "0");
            playerInfluences[i].setText(influence >= 0 ? Integer.toString(influence) : "0");
            playerMoneys[i].setText(money >= 0 ? Integer.toString(money) : "0");
            playerScores[i].setText(score >= 0 ? Integer.toString(score) : "0");
        }

    }

    /**
     * Starts the game by hiding the start screen and showing the game board.
     */
    public void startGame() {
        startScreen.setVisible(false);
        remove(startScreen);
    }

    private void setupArrowButtons() {
        JPanel arrowsContainer = new JPanel();
        arrowDown = new JButton();
        arrowUp = new JButton();
        arrowLeft = new JButton();
        arrowRight = new JButton();

        arrowsContainer.setLayout(null);

        JButton[] arrowButtons = { arrowDown, arrowUp, arrowLeft, arrowRight };
        String[] arrowIcons = { "/images/arrows/ArrowDown.png", "/images/arrows/ArrowUp.png",
                "/images/arrows/ArrowLeft.png", "/images/arrows/ArrowRight.png" };
        for (int i = 0; i < arrowButtons.length; i++) {
            arrowButtons[i].setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(arrowIcons[i]))));
            arrowButtons[i].setBorder(null);
            arrowButtons[i].setContentAreaFilled(false);
            arrowButtons[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            arrowButtons[i].setFocusable(false);
            arrowButtons[i].setRolloverEnabled(false);

            if (i == 0) { // Arrow Down
                arrowButtons[i].setBounds(45, 46, 46, 46);
            } else if (i == 1) { // Arrow Up
                arrowButtons[i].setBounds(45, 1, 46, 46);
            } else if (i == 2) { // Arrow Left
                arrowButtons[i].setBounds(0, 46, 46, 46);
            } else { // Arrow Right
                arrowButtons[i].setBounds(90, 46, 46, 46);
            }

            arrowsContainer.add(arrowButtons[i]);
        }

        setupArrowButtonAction(arrowRight, Direction.RIGHT);
        setupArrowButtonAction(arrowLeft, Direction.LEFT);
        setupArrowButtonAction(arrowUp, Direction.UP);
        setupArrowButtonAction(arrowDown, Direction.DOWN);

        sidePanelContainer.add(arrowsContainer);
        arrowsContainer.setBounds(10, WINDOW_HEIGHT - 52 - 92, 138, 92);
    }

    private void setupArrowButtonAction(JButton button, Direction direction) {
        button.addActionListener(e -> {
            GameSystem.movePlayer(direction);
            if (GameSystem.getPlayerAt().getMovesLeft() == 0) {
                movesLeftLabel.setVisible(false);
                endTurnButton.setVisible(true);
            } else {
                movesLeftLabel.setText("Moves Left: " + GameSystem.getPlayerAt().getMovesLeft());
            }
        });
    }

    private void setupLabelsAndButtons() {
        roundNumberGraphic = new JLabel();
        playerTurnGraphic = new JLabel();
        helpButton = new JButton();
        journalButton = new JButton();
        shopButton = new JButton();
        closeButton = new JButton();
        rollDiceButton = new JButton();
        endTurnButton = new JButton();
        movesLeftLabel = new JLabel();

        roundNumberGraphic.setFont(new java.awt.Font("Segue UI", Font.PLAIN, 26));
        roundNumberGraphic.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/Round.png"))));
        roundNumberGraphic.setText("Round " + GameSystem.getRoundNumber());
        roundNumberGraphic.setHorizontalTextPosition(SwingConstants.CENTER);
        sidePanelContainer.add(roundNumberGraphic);
        roundNumberGraphic.setBounds(251, 0, 122, 57);

        playerTurnGraphic.setFont(new java.awt.Font("Segue UI", Font.PLAIN, 26));
        playerTurnGraphic.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/PlayerTurn.png"))));
        playerTurnGraphic.setText("     " + GameSystem.getPlayerAt().getName() + "'s Turn");
        playerTurnGraphic.setToolTipText("");
        playerTurnGraphic.setHorizontalTextPosition(SwingConstants.CENTER);
        sidePanelContainer.add(playerTurnGraphic);
        playerTurnGraphic.setBounds(0, 0, 241, 57);

        popupButtonContainer = new JPanel();
        popupButtonContainer.setLayout(new BoxLayout(popupButtonContainer, BoxLayout.X_AXIS));

        ImageIcon shopIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/shopButton.png")));
        createPopupButton(shopButton, shopIcon);
        shopButton.addActionListener(e -> toggleShop());

        ImageIcon journalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/journalButton.png")));
        createPopupButton(journalButton, journalIcon);
        journalButton.addActionListener(evt -> toggleJournal());

        ImageIcon helpIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/help.png")));
        createPopupButton(helpButton, helpIcon);
        helpButton.addActionListener(evt -> toggleTutorial());

        ImageIcon closeIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/quitGameButton.png")));
        createPopupButton(closeButton, closeIcon);
        closeButton.addActionListener(e -> toggleEndGame(EndGame.Ending.BAD));

        setPopupButtonsPosition();
        sidePanelContainer.add(popupButtonContainer);

        rollDiceButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/RollDiceButton.png"))));
        rollDiceButton.setBorder(null);
        rollDiceButton.setContentAreaFilled(false);
        rollDiceButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rollDiceButton.setFocusable(false);
        rollDiceButton.setBounds(170, WINDOW_HEIGHT - 52 - 47, 192, 47);
        rollDiceButton.setRolloverEnabled(false);
        rollDiceButton.addActionListener(e -> {
            GameSystem.getPlayerAt().rollDie();
            rollDiceButton.setVisible(false);
            movesLeftLabel.setText("Moves Left: " + GameSystem.getPlayerAt().getMovesLeft());
            movesLeftLabel.setVisible(true);
        });
        sidePanelContainer.add(rollDiceButton);

        movesLeftLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/buttonBackground.png"))));
        movesLeftLabel.setBounds(170, WINDOW_HEIGHT - 52 - 47, 192, 47);
        movesLeftLabel.setVisible(false);
        movesLeftLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        movesLeftLabel.setFont(new java.awt.Font("Segue UI", Font.PLAIN, 26));
        sidePanelContainer.add(movesLeftLabel);

        endTurnButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/buttonBackground.png"))));
        endTurnButton.setBounds(170, WINDOW_HEIGHT - 52 - 47, 192, 47);
        endTurnButton.setVisible(false);
        endTurnButton.setText("End Turn");
        endTurnButton.setFont(new java.awt.Font("Segue UI", Font.PLAIN, 26));
        endTurnButton.setHorizontalTextPosition(SwingConstants.CENTER);
        endTurnButton.setContentAreaFilled(false);
        endTurnButton.setBorder(null);
        endTurnButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        endTurnButton.setRolloverEnabled(false);
        sidePanelContainer.add(endTurnButton);

        endTurnButton.addActionListener(e -> {
            GameSystem.nextTurn();
            playerTurnGraphic.setText("     " + GameSystem.getPlayerAt().getName() + "'s Turn");
            roundNumberGraphic.setText("Round " + GameSystem.getRoundNumber());
            movesLeftLabel.setVisible(false);
            endTurnButton.setVisible(false);
            rollDiceButton.setVisible(true);
        });
    }

    private void setPopupButtonsPosition() {
        int numberOfButtons = Arrays.stream(popupButtonContainer.getComponents()).filter(component -> component instanceof JButton && component.isVisible()).toArray().length;
        popupButtonContainer.setSize(37*numberOfButtons + 15*(numberOfButtons-1), 37);
        popupButtonContainer.setLocation(SIDE_PANEL_WIDTH - popupButtonContainer.getWidth() - 22, WINDOW_HEIGHT - 105 - 37);
    }

    private void createPopupButton(JButton button, ImageIcon icon) {
        button.setIcon(new ImageIcon(icon.getImage().getScaledInstance(37, 37, Image.SCALE_SMOOTH)));
        button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        popupButtonContainer.add(button);
    }

    private void setupPlayerPanels() {
        JPanel[] playerResources = { Player1Resources, Player2Resources, Player3Resources, Player4Resources };
        JLabel[] playerResourceTitles = { player1ResourceTitle, player2ResourceTitle, player3ResourceTitle,
                player4ResourceTitle };

        playerAsphalts = new JLabel[players.length];
        playerKnowledges = new JLabel[players.length];
        playerSatisfactions = new JLabel[players.length];
        playerInfluences = new JLabel[players.length];
        playerMoneys = new JLabel[players.length];
        playerScores = new JLabel[players.length];
        
        ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/Resources 1.png")));
        backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(373, 127, Image.SCALE_SMOOTH));

        for (int i = 0; i < players.length; i++) {
            Player currentPlayer = players[i];

            playerResources[i] = new JPanel();
            playerResources[i].setLayout(null);

            playerResourceTitles[i] = new JLabel(currentPlayer.getName());
            playerResourceTitles[i].setFont(new java.awt.Font("Segue UI", Font.BOLD, 18));
            playerResourceTitles[i].setBounds(10, -3, 350, 30);
            playerResources[i].add(playerResourceTitles[i]);
            
            playerAsphalts[i] = new JLabel(Integer.toString(currentPlayer.getResource(ResourceType.ASPHALT)));
            playerAsphalts[i].setBounds(60, 23, 60, 40);
            playerAsphalts[i].setFont(new java.awt.Font("Segue UI", Font.PLAIN, 30));
            playerResources[i].add(playerAsphalts[i]);

            playerKnowledges[i] = new JLabel(Integer.toString(currentPlayer.getResource(ResourceType.KNOWLEDGE)));
            playerKnowledges[i].setBounds(185, 23, 60, 40);
            playerKnowledges[i].setFont(new java.awt.Font("Segue UI", Font.PLAIN, 30));
            playerResources[i].add(playerKnowledges[i]);

            playerSatisfactions[i] = new JLabel(Integer.toString(currentPlayer.getResource(ResourceType.VOLUNTEERS)));
            playerSatisfactions[i].setBounds(310, 23, 60, 40);
            playerSatisfactions[i].setFont(new java.awt.Font("Segue UI", Font.PLAIN, 30));
            playerResources[i].add(playerSatisfactions[i]);

            playerInfluences[i] = new JLabel(Integer.toString(currentPlayer.getResource(ResourceType.INFLUENCE)));
            playerInfluences[i].setBounds(60, 75, 60, 40);
            playerInfluences[i].setFont(new java.awt.Font("Segue UI", Font.PLAIN, 30));
            playerResources[i].add(playerInfluences[i]);

            playerMoneys[i] = new JLabel(Integer.toString(currentPlayer.getMoney()));
            playerMoneys[i].setBounds(185, 75, 60, 40);
            playerMoneys[i].setFont(new java.awt.Font("Segue UI", Font.PLAIN, 30));
            playerResources[i].add(playerMoneys[i]);

            playerScores[i] = new JLabel(Integer.toString(currentPlayer.getScore()));
            playerScores[i].setBounds(310, 75, 60, 40);
            playerScores[i].setFont(new java.awt.Font("Segue UI", Font.PLAIN, 30));
            playerResources[i].add(playerScores[i]);

            JLabel background = new JLabel();
            background.setIcon(backgroundIcon);
            background.setBounds(0, 0, 375, 127);
            playerResources[i].add(background);
            playerResources[i].setBounds(0, 62 + (i * 132), 373, 127);
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

         GroupLayout layout = new GroupLayout(getContentPane());
         getContentPane().setLayout(layout);
         layout.setHorizontalGroup(
                 layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                         .addGroup(layout.createSequentialGroup()
                                 .addContainerGap()
                                 .addComponent(gameBoard, GroupLayout.PREFERRED_SIZE, BOARD_WIDTH,
                                         GroupLayout.PREFERRED_SIZE)
                                 .addGap(10, 10, 10)
                                 .addComponent(sidePanelContainer, GroupLayout.DEFAULT_SIZE, SIDE_PANEL_WIDTH,
                                         Short.MAX_VALUE)
                                 .addGap(0, 0, 0)));
         layout.setVerticalGroup(
                 layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                         .addGroup(layout.createSequentialGroup()
                                 .addGap(6, 6, 6)
                                 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                         .addComponent(gameBoard, GroupLayout.PREFERRED_SIZE, BOARD_HEIGHT,
                                                 GroupLayout.PREFERRED_SIZE)
                                         .addComponent(sidePanelContainer, GroupLayout.PREFERRED_SIZE, WINDOW_HEIGHT,
                                                 GroupLayout.PREFERRED_SIZE))
                                 .addGap(10, 10, 10)));

        setupArrowButtons();
        setupLabelsAndButtons();
        setupPlayerPanels();

        setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
    }

    private void toggleEnableButtons() {
        arrowDown.setEnabled(!arrowDown.isEnabled());
        arrowUp.setEnabled(!arrowUp.isEnabled());
        arrowLeft.setEnabled(!arrowLeft.isEnabled());
        arrowRight.setEnabled(!arrowRight.isEnabled());
        rollDiceButton.setEnabled(!rollDiceButton.isEnabled());
        movesLeftLabel.setEnabled(!movesLeftLabel.isEnabled());
        endTurnButton.setEnabled(!endTurnButton.isEnabled());
        helpButton.setEnabled(!helpButton.isEnabled());
        journalButton.setEnabled(!journalButton.isEnabled());
        shopButton.setEnabled(!shopButton.isEnabled());
        closeButton.setEnabled(!closeButton.isEnabled());
    }

    public void toggleJournal() {
        if (!journal.isVisible()) {
            journal.refresh();
        }
        journal.setVisible(!journal.isVisible());
        dimBackground.setVisible(!dimBackground.isVisible());
        toggleEnableButtons();
    }

    public void toggleShop() {
        shop.setVisible(!shop.isVisible());
        dimBackground.setVisible(!dimBackground.isVisible());
        toggleEnableButtons();
    }

    public void setShopButtonVisible(boolean state) {
        shopButton.setVisible(state);
        setPopupButtonsPosition();
    }

    public void toggleTutorial() {
        tutorial.setVisible(!tutorial.isVisible());
        dimBackground.setVisible(!dimBackground.isVisible());
        toggleEnableButtons();
    }

    public void toggleTransfer(Task task) {
        transferPopup.setVisible(!transferPopup.isVisible());
        dimBackground.setVisible(!dimBackground.isVisible());
        toggleEnableButtons();
        if (task != null) {
            transferPopup.setTask(task);
        }
        transferPopup.renderButtons(this.players);
    }

    public void refreshJournal() {
        journal.refresh();
    }

    public void toggleEndGame(EndGame.Ending endingType) {
        endGame.setVisible(!endGame.isVisible());
        dimBackground.setVisible(!dimBackground.isVisible());
        toggleEnableButtons();
        if (endingType == null) {
            System.err.println("Ending type is null");
        } else if (endingType == EndGame.Ending.GOOD) {
            endGame.showGoodEnding();
        } else if (endingType == EndGame.Ending.BAD) {
            endGame.showBadEnding();
        }
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
    public Board gameBoard;
    private JButton helpButton;

    private JLabel[] playerAsphalts;
    private JLabel[] playerKnowledges;
    private JLabel[] playerSatisfactions;
    private JLabel[] playerInfluences;
    private JLabel[] playerMoneys;
    private JLabel[] playerScores;

    private JLabel player1ResourceTitle;
    private JLabel player2ResourceTitle;
    private JLabel player3ResourceTitle;
    private JLabel player4ResourceTitle;

    private JLabel playerTurnGraphic;
    private JButton rollDiceButton;
    private JLabel roundNumberGraphic;
    private JPanel sidePanelContainer;
    private final Popup popup;
    private JPanel popupButtonContainer;
    private JButton journalButton;
    private final Journal journal;
    private final JPanel dimBackground;
    private final Shop shop;
    private JButton shopButton;
    private final Tutorial tutorial;
    private JButton closeButton;
}
