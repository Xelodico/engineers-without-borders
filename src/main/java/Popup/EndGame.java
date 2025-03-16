package Popup;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import BoardGame.BoardGameUI;
import BoardGame.Player;
import GameSystem.GameSystem;

/**
 * The EndGame class represents a JPanel that displays the end game screen
 * with a scrolling text area showing the game's epilogue.
 *
 * @author Nathan Watkins
 * @author Peter Robinson (supporting)
 */
public class EndGame extends JPanel {

    private JTextPane textArea;
    private JScrollPane scrollPane;
    private JPanel statsContainer;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private GridBagConstraints gridBagConstraints;
    private Timer timer;
    private boolean userScrolling = false;
    private BufferedImage backgroundImage;

    /**
     * Constructs an EndGame panel with default settings.
     */
    public EndGame() {
        int WIDTH = 800;
        int HEIGHT = 500;
        setBounds(BoardGameUI.WINDOW_WIDTH / 2 - WIDTH / 2, BoardGameUI.WINDOW_HEIGHT / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        setVisible(false);
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        createEpilogueContainer();
        createStatsContainer();

        add(cardPanel, BorderLayout.CENTER);

        cardLayout.show(cardPanel, "epilogue");
    }

    /**
     * Creates the epilogue container for displaying the game's ending.
     */
    private void createEpilogueContainer() {
        JPanel epilogueContainer = new JPanel();
        epilogueContainer.setLayout(new BoxLayout(epilogueContainer, BoxLayout.Y_AXIS));
        add(epilogueContainer);

        textArea = new JTextPane();
        textArea.setEditable(false);
        textArea.setText("Game in progress...");
        textArea.setFont(new Font("Segue UI", Font.PLAIN, 18));
        textArea.setBackground(new Color(240, 240, 240));
        textArea.setCaretColor(new Color(0, 0, 0, 0));
        textArea.setHighlighter(null);
        StyledDocument doc = textArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(new Color(240, 240, 240));
        scrollPane.setBorder(null);
        epilogueContainer.add(scrollPane, BorderLayout.CENTER);

        JButton continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Segue UI", Font.PLAIN, 18));
        continueButton.setPreferredSize(new Dimension(200, 50));
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.addActionListener(e -> showStats());

        epilogueContainer.add(continueButton);
        epilogueContainer.add(Box.createVerticalStrut(20));

        cardPanel.add(epilogueContainer, "epilogue");
    }

    /**
     * Creates the statistics container for displaying player stats.
     */
    private void createStatsContainer() {

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(0, 0, 0));
        container.setOpaque(false);
        add(container);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();

        statsContainer = new JPanel();
        statsContainer.setLayout(gridBagLayout);
        statsContainer.setBackground(new Color(240, 240, 240, 0));
        setOpaque(false);
        statsContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.setOpaque(false);

        JButton continueButton = new JButton("Play Again");
        continueButton.setFont(new Font("Segue UI", Font.PLAIN, 18));
        continueButton.setPreferredSize(new Dimension(200, 50));
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.addActionListener(e -> {
            GameSystem.reset();
            GameSystem.initialise();
        });
        buttonContainer.add(continueButton);

        buttonContainer.add(Box.createHorizontalStrut(20));

        JButton exitButton = new JButton("Quit Game");
        exitButton.setFont(new Font("Segue UI", Font.PLAIN, 18));
        exitButton.setPreferredSize(new Dimension(200, 50));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));
        buttonContainer.add(exitButton);

        container.add(statsContainer);
        container.add(buttonContainer);

        cardPanel.add(container, "stats");
    }

    /**
     * Starts the automatic scrolling of the text area.
     */
    private void startScrolling() {
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();

        verticalBar.addAdjustmentListener(e -> {
            if (timer.isRunning() && !userScrolling) {
                timer.stop();
            }
        });

        int scrollSpeed = 40; // lower is faster
        timer = new Timer(scrollSpeed, e -> {
            userScrolling = true;
            int current = verticalBar.getValue();
            int max = verticalBar.getMaximum() - verticalBar.getVisibleAmount();

            if (current < max) {
                verticalBar.setValue(current + 1); // Move scrollbar down
            } else {
                timer.stop(); // Stop scrolling when reaching the bottom
            }
            userScrolling = false;
        });
        timer.start();
    }

    /**
     * Reads the epilogue text from a file based on the ending type.
     *
     * @param ending The type of ending (GOOD or BAD).
     * @return The epilogue text as a String.
     */
    private String getEpilogueText(Ending ending) {

        String filePath;
        if (ending == Ending.GOOD) {
            filePath = "src/main/resources/data/goodEndingEvents.txt";
        } else {
            filePath = "src/main/resources/data/badEndingEvents.txt";
        }

        StringBuilder string = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            Object[] lines = reader.lines().toArray();
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].equals("")) {
                    break;
                }
                string.append(2025 + i).append("\n");
                string.append(lines[i].toString());
                string.append("\n\n");
            }
            string.append(lines[lines.length - 1].toString());

            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath + " - " + e.getMessage());
            return "Error reading file: " + filePath;
        }

        return string.toString();
    }

    /**
     * Displays the good ending epilogue and starts scrolling after a delay.
     */
    public void showGoodEnding() {
        cardLayout.show(cardPanel, "epilogue");
        textArea.setText(getEpilogueText(Ending.GOOD));
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
        setVisible(true);

        new Timer(4000, e -> {
            startScrolling();
            ((Timer) e.getSource()).stop();
        }).start();
    }

    /**
     * Displays the bad ending epilogue and starts scrolling after a delay.
     */
    public void showBadEnding() {
        cardLayout.show(cardPanel, "epilogue");
        textArea.setText(getEpilogueText(Ending.BAD));
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
        setVisible(true);

        new Timer(4000, e -> {
            startScrolling();
            ((Timer) e.getSource()).stop();
        }).start();
    }

    /**
     * Creates a player card with the player's information.
     *
     * @param player The player whose information is to be displayed.
     * @return A JPanel containing the player's information.
     */
    private JPanel createPlayerCard(Player player) {
        JPanel playerCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        playerCard.setLayout(new BoxLayout(playerCard, BoxLayout.Y_AXIS));
        playerCard.setOpaque(false);
        playerCard.setPreferredSize(new Dimension(235, 480));

        playerCard.add(Box.createVerticalStrut(10));

        JLabel playerName = new JLabel(player.getName());
        playerName.setFont(new Font("Segue UI", Font.BOLD, 18));
        playerName.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerCard.add(playerName);
        playerCard.add(Box.createVerticalStrut(21));

        JLabel playerTitle = new JLabel(player.getAchievement());
        playerTitle.setFont(new Font("Segue UI", Font.PLAIN, 16));
        playerTitle.setForeground(Color.WHITE);
        playerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerCard.add(playerTitle);
        playerCard.add(Box.createVerticalStrut(20));

        JPanel totalScore = createStat("Score: ", String.valueOf(player.getScore()));
        playerCard.add(totalScore);

        JPanel movesTaken = createStat("Moves Taken: ", String.valueOf(player.getMovesTravelled()));
        playerCard.add(movesTaken);

        JPanel currentMoney = createStat("Current Money: ", String.valueOf(player.getMoney()));
        playerCard.add(currentMoney);

        JPanel moneySpent = createStat("Money Spent: ", String.valueOf(player.getMoneySpent()));
        playerCard.add(moneySpent);

        JPanel timesHelped = createStat("Times Helped: ", String.valueOf(player.getTimesHelped()));
        playerCard.add(timesHelped);

        playerCard.add(Box.createVerticalStrut(200));

        gridBagConstraints.gridx = statsContainer.getComponentCount();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.fill = GridBagConstraints.NONE;

        gridBagConstraints.insets = new Insets(0, 10, 0, 10);

        return playerCard;
    }

    /**
     * Creates a stat panel with a name and value.
     *
     * @param name  The name of the stat.
     * @param value The value of the stat.
     * @return A JPanel containing the stat information.
     */
    private JPanel createStat(String name, String value) {
        JPanel stat = new JPanel();
        stat.setLayout(new BoxLayout(stat, BoxLayout.X_AXIS));
        stat.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        stat.setAlignmentX(Component.CENTER_ALIGNMENT);
        stat.setOpaque(false);

        JLabel statName = new JLabel(name);
        statName.setFont(new Font("Segue UI", Font.PLAIN, 16));
        stat.add(statName);
        stat.add(Box.createHorizontalGlue());
        JLabel statValue = new JLabel(value);
        statValue.setFont(new Font("Segue UI", Font.PLAIN, 16));
        stat.add(statValue);

        return stat;
    }

    /**
     * Displays the statistics of all players at the end of the game.
     */
    public void showStats() {

        try {
            backgroundImage = ImageIO
                    .read(Objects.requireNonNull(getClass().getResource("/images/popups/statsPanel.png")));
        } catch (NullPointerException | IOException e) {
            System.err.println("Error: Stats Panel background image not found!");
            System.exit(1);
        }

        Player[] players = GameSystem.getTurnOrder();
        determineAchievements();

        for (Player player : players) {
            JPanel playerCard = createPlayerCard(player);
            statsContainer.add(playerCard, gridBagConstraints);
        }

        setBounds(0, 0, BoardGameUI.WINDOW_WIDTH, (int) (BoardGameUI.WINDOW_HEIGHT * 0.95));
        cardLayout.show(cardPanel, "stats");
    }

    /**
     * Determines and assigns unique achievements to each player based on their
     * performance.
     * <p>
     * Achievements are calculated using various performance metrics:
     * <ul>
     * <li>"Highest Scorer" for the player with the highest score.</li>
     * <li>"Cheapskate" for the player who spent the least money.</li>
     * <li>"Team Player" for the player who helped others the most.</li>
     * <li>"Traveller" for the player who moved the most.</li>
     * <li>"Big Spender" for the player who spent the most money.</li>
     * <li>"Money Maker" for the player with the highest current money.</li>
     * <li>"Efficiency Expert" for the player with the best score-to-moves
     * ratio.</li>
     * <li>"Risk Taker" for the player with the highest ratio of money spent to
     * current money.</li>
     * </ul>
     * In case of ties, a random tiebreaker is used.
     * <p>
     * Finally, any player who does not receive an achievement based on these
     * criteria
     * is given the default "Participation Award".
     */
    private void determineAchievements() {
        // Get the current turn order of players
        Player[] players = GameSystem.getTurnOrder();

        // Clear previous achievements for all players to ensure uniqueness
        for (Player player : players) {
            player.setAchievement(null);
        }

        // Create a Random object for tie-breaking decisions
        Random rand = new Random();

        // Initialize indices for each achievement category to the first player (index
        // 0)
        int highestScorerIndex = 0;
        int lowestSpenderIndex = 0;
        int teamPlayerIndex = 0;
        int travellerIndex = 0;
        int highestSpenderIndex = 0;
        int moneyMakerIndex = 0;
        int efficiencyIndex = 0;
        int riskTakerIndex = 0;

        // Loop through players starting from index 1 to compare performance metrics
        for (int i = 1; i < players.length; i++) {
            // Determine the player with the highest score
            if (players[i].getScore() > players[highestScorerIndex].getScore()) {
                highestScorerIndex = i;
            } else if (players[i].getScore() == players[highestScorerIndex].getScore() && rand.nextBoolean()) {
                highestScorerIndex = i;
            }

            // Determine the player with the lowest money spent
            if (players[i].getMoneySpent() < players[lowestSpenderIndex].getMoneySpent()) {
                lowestSpenderIndex = i;
            } else if (players[i].getMoneySpent() == players[lowestSpenderIndex].getMoneySpent()
                    && rand.nextBoolean()) {
                lowestSpenderIndex = i;
            }

            // Determine the player who helped others the most
            if (players[i].getTimesHelped() > players[teamPlayerIndex].getTimesHelped()) {
                teamPlayerIndex = i;
            } else if (players[i].getTimesHelped() == players[teamPlayerIndex].getTimesHelped() && rand.nextBoolean()) {
                teamPlayerIndex = i;
            }

            // Determine the player who travelled the most moves
            if (players[i].getMovesTravelled() > players[travellerIndex].getMovesTravelled()) {
                travellerIndex = i;
            } else if (players[i].getMovesTravelled() == players[travellerIndex].getMovesTravelled()
                    && rand.nextBoolean()) {
                travellerIndex = i;
            }

            // Determine the player with the highest money spent
            if (players[i].getMoneySpent() > players[highestSpenderIndex].getMoneySpent()) {
                highestSpenderIndex = i;
            } else if (players[i].getMoneySpent() == players[highestSpenderIndex].getMoneySpent()
                    && rand.nextBoolean()) {
                highestSpenderIndex = i;
            }

            // Determine the player with the highest current money
            if (players[i].getMoney() > players[moneyMakerIndex].getMoney()) {
                moneyMakerIndex = i;
            } else if (players[i].getMoney() == players[moneyMakerIndex].getMoney() && rand.nextBoolean()) {
                moneyMakerIndex = i;
            }

            // Calculate efficiency as score divided by moves travelled, handling division
            // by zero
            double efficiency = players[i].getMovesTravelled() != 0
                    ? players[i].getScore() / (double) players[i].getMovesTravelled()
                    : 0;
            double maxEfficiency = players[efficiencyIndex].getMovesTravelled() != 0
                    ? players[efficiencyIndex].getScore() / (double) players[efficiencyIndex].getMovesTravelled()
                    : 0;
            // Determine the player with the highest efficiency
            if (efficiency > maxEfficiency) {
                efficiencyIndex = i;
            } else if (efficiency == maxEfficiency && rand.nextBoolean()) {
                efficiencyIndex = i;
            }

            // Calculate risk factor as money spent divided by current money, handling
            // division by zero
            double riskTaker = players[i].getMoney() != 0
                    ? players[i].getMoneySpent() / (double) players[i].getMoney()
                    : 0;
            double maxRiskTaker = players[riskTakerIndex].getMoney() != 0
                    ? players[riskTakerIndex].getMoneySpent() / (double) players[riskTakerIndex].getMoney()
                    : 0;
            // Determine the player who is the biggest risk taker
            if (riskTaker > maxRiskTaker) {
                riskTakerIndex = i;
            } else if (riskTaker == maxRiskTaker && rand.nextBoolean()) {
                riskTakerIndex = i;
            }
        }

        // Award achievements only if the player does not already have one assigned
        if (players[highestScorerIndex].getAchievement() == null)
            players[highestScorerIndex].setAchievement("Highest Scorer");
        if (players[lowestSpenderIndex].getAchievement() == null)
            players[lowestSpenderIndex].setAchievement("Cheapskate");
        if (players[teamPlayerIndex].getAchievement() == null)
            players[teamPlayerIndex].setAchievement("Team Player");
        if (players[travellerIndex].getAchievement() == null)
            players[travellerIndex].setAchievement("Traveller");
        if (players[highestSpenderIndex].getAchievement() == null)
            players[highestSpenderIndex].setAchievement("Big Spender");
        if (players[moneyMakerIndex].getAchievement() == null)
            players[moneyMakerIndex].setAchievement("Money Maker");
        if (players[efficiencyIndex].getAchievement() == null)
            players[efficiencyIndex].setAchievement("Efficiency Expert");
        if (players[riskTakerIndex].getAchievement() == null)
            players[riskTakerIndex].setAchievement("Risk Taker");

        // If any player still hasn't received an achievement, assign the default
        // "Participation Award"
        String defaultAchievement = "Participation Award";
        for (Player player : players) {
            if (player.getAchievement() == null) {
                player.setAchievement(defaultAchievement);
            }
        }
    }

    /**
     * Enum representing the possible endings of the game.
     */
    public static enum Ending {
        GOOD, BAD
    }

}