package Popup;

import BoardGame.BoardGameUI;
import BoardGame.Player;
import GameSystem.GameSystem;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * The EndGame class represents a JPanel that displays the end game screen
 * with a scrolling text area showing the game's epilogue.
 *
 * @author Nathan Watkins
 */
public class EndGame extends JPanel {

    private final JTextPane textArea;
    private final JScrollPane scrollPane;
    private final JPanel statsContainer;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final GridBagConstraints gridBagConstraints;
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

        textArea = new JTextPane();
        textArea.setEditable(false);
        textArea.setText("Game in progress...");
        textArea.setFont(new Font("Segue UI", Font.PLAIN, 18));
        textArea.setBackground(new Color(240, 240, 240));

        StyledDocument doc = textArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(new Color(240, 240, 240));
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();

        statsContainer = new JPanel();
        statsContainer.setLayout(gridBagLayout);
        statsContainer.setBackground(new Color(240, 240, 240, 0));
        setOpaque(false);
        statsContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        add(statsContainer, BorderLayout.CENTER);

        cardPanel.add(statsContainer, "stats");
        cardPanel.add(scrollPane, "epilogue");
        add(cardPanel, BorderLayout.CENTER);
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

        JLabel playerTitle = new JLabel("[Title]");
        playerTitle.setFont(new Font("Segue UI", Font.PLAIN, 16));
        playerTitle.setForeground(Color.WHITE);
        playerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerCard.add(playerTitle);

        playerCard.add(Box.createVerticalStrut(20));

        JPanel totalScore = createStat("Score: ", String.valueOf(player.getScore()));
        playerCard.add(totalScore);

        JPanel movesTaken = createStat("Moves Taken: ", String.valueOf(player.getMovesLeft()));
        playerCard.add(movesTaken);

        JPanel moneySpent = createStat("Money Spent: ", String.valueOf(player.getMoney()));
        playerCard.add(moneySpent);

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
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/statsPanel.png")));
        } catch (NullPointerException | IOException e) {
            System.err.println("Error: Stats Panel background image not found!");
            System.exit(1);
        }

        Player[] players = GameSystem.getTurnOrder();

        for (Player player : players) {
            JPanel playerCard = createPlayerCard(player);
            statsContainer.add(playerCard, gridBagConstraints);
        }

        setBounds(0, 0, BoardGameUI.WINDOW_WIDTH, BoardGameUI.WINDOW_HEIGHT);
        cardLayout.show(cardPanel, "stats");
    }

    /**
     * Enum representing the possible endings of the game.
     */
    private enum Ending {
        GOOD, BAD
    }

}