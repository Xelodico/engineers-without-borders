package Popup;

import javax.swing.*;
import javax.swing.text.*;

import BoardGame.*;
import GameSystem.GameSystem;
import Popup.EndGame.Ending;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import javax.imageio.ImageIO;

/**
 * Represents a journal component in the BoardGame UI.
 * This panel displays a background image with objectives and their
 * corresponding tasks in a scrollable area.
 * Each objective contains multiple tasks, with vertical spacing between them.
 * 
 * @author Nathan Watkins
 * @author Curtis McCartney (Supporting)
 */
public class Journal extends JPanel {

    // The background image for the journal
    private BufferedImage backgroundImage;
    private final JPanel page;
    private final JScrollPane scrollPane;

    public JButton closeButton;
    private JLabel solutionImplementation;

    /**
     * Constructs a new Journal panel with the following layout:
     * - A title at the top
     * - A scrollable area containing objectives, each with a list of tasks
     */
    public Journal() {
        final int WIDTH = 700;
        final int HEIGHT = 500;

        try {
            // Load background image
            backgroundImage = ImageIO
                    .read(Objects.requireNonNull(getClass().getResource("/images/popups/journalBackground.png")));
        } catch (NullPointerException | IOException e) {
            System.err.println("Error: Journal background image not found!");
            System.exit(1);
        }

        setLayout(null);
        setBounds((BoardGameUI.WINDOW_WIDTH / 2 - WIDTH / 2), (650 / 2 - HEIGHT / 2) + 6, WIDTH, HEIGHT);
        setBackground(new Color(0, 0, 0, 0));
        setVisible(false);

        JPanel titleBar = new JPanel();
        titleBar.setBounds(25, 0, WIDTH - 100, 50);
        titleBar.setBackground(new Color(0, 0, 0, 0));
        titleBar.setLayout(new BorderLayout());
        titleBar.setOpaque(false);

        // Title Label for the objectives section
        JLabel title = new JLabel("Objectives");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.LEFT);
        title.setBounds(24, 13, 617, 30);

        solutionImplementation = new JLabel();
        // solutionImplementation.setText("Solution Implementation: FIX ME");
        solutionImplementation
                .setText("Solution Implementation: " + (int) (GameSystem.getImplementationPercent() * 100) + "%");
        solutionImplementation.setFont(new Font("Arial", Font.PLAIN, 16));
        solutionImplementation.setHorizontalAlignment(SwingConstants.RIGHT);
        solutionImplementation.setBounds(24, 13, 617, 30);

        titleBar.add(title, BorderLayout.WEST);
        titleBar.add(solutionImplementation, BorderLayout.EAST);
        add(titleBar);

        // Set scroll bar width
        UIManager.put("ScrollBar.width", 10);

        scrollPane = createScrollPane();
        add(scrollPane);

        // Create the page content container
        page = new JPanel();
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));
        page.setOpaque(false);
        page.setBackground(new Color(214, 183, 109));

        addCloseButton(page);

        // Add the page (which contains the objectives) to the scroll pane
        scrollPane.setViewportView(page);
    }

    /**
     * Refreshes the current layout and content of the journal panel.
     * This method clears any existing components on the page, re-adds the close
     * button,
     * and dynamically populates the page with updated objectives and their
     * associated tasks.
     * Additionally, it resets the vertical scroll position of the scroll pane to
     * the top.
     */
    public void refresh() {

        int originalScrollValue = scrollPane.getVerticalScrollBar().getValue();
        solutionImplementation
                .setText("Solution Implementation: " + (int) (GameSystem.getImplementationPercent() * 100) + "%");

        page.removeAll(); // Remove all existing components
        addCloseButton(page); // Re-add the close button

        // Add objectives
        ArrayList<Objective> objectives = GameSystem.getObjectives();
        objectives.forEach(objective -> {
            final boolean[] showObjective = { false };

            objective.getTasks().forEach(task -> {
                if (task.getOwnedBy() != null) {
                    showObjective[0] = true;
                }
            });

            if (showObjective[0]) {
                page.add(createObjective(objectives.indexOf(objective)));
            }
        });

        // Check if only one objective has a task owned by someone
        long count = objectives.stream()
                .filter(objective -> objective.getTasks().stream().anyMatch(task -> task.getOwnedBy() != null))
                .count();

        if (count == 0) {
            JPanel noObjectives = new JPanel();
            noObjectives.setLayout(new BorderLayout());
            noObjectives.setBackground(new Color(0, 0, 0, 0));
            noObjectives.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0)); // Padding around the objective
            JLabel noObjectivesLabel = new JLabel("No objectives discovered yet!");
            noObjectivesLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            noObjectivesLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noObjectives.add(noObjectivesLabel, BorderLayout.NORTH);
            page.add(noObjectives);
        } else if (count == 1) {
            page.add(Box.createVerticalStrut(150));
        }

        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(originalScrollValue));

        revalidate();
        repaint();

        if (isVisible()) {
            GameSystem.toggleJournal();
            GameSystem.toggleJournal();
        }
    }

    /**
     * Creates a JPanel representation of an objective, which includes the
     * objective's title
     * and a list of associated tasks and sub-tasks. Tasks and sub-tasks are
     * displayed in a
     * vertically stacked layout within the panel. Each component of the panel is
     * styled and
     * aligned to blend seamlessly into the overall UI.
     *
     * @param objectiveIndex The index of the objective in the list of objectives
     *                       maintained
     *                       by the game system.
     * @return A JPanel representing the objective, including its title, tasks, and
     *         sub-tasks.
     */
    private JPanel createObjective(int objectiveIndex) {
        Objective objectiveObj = GameSystem.getObjectives().get(objectiveIndex);
        ArrayList<Task> tasksObj = objectiveObj.getTasks();

        JPanel objective = new JPanel();
        objective.setLayout(new BorderLayout());
        objective.setBackground(new Color(0, 0, 0, 0));
        objective.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around the objective
        JLabel objectiveLabel = new JLabel(objectiveObj.getTitle());
        objectiveLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        objectiveLabel.setHorizontalAlignment(SwingConstants.LEFT);
        objective.add(objectiveLabel, BorderLayout.NORTH);

        // Create a task panel with a vertical BoxLayout
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        taskPanel.setBackground(new Color(214, 183, 109));
        taskPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        boolean[] allTasksCompleted = { true };

        tasksObj.forEach(task -> {

            if (!task.isCompleted()) {
                allTasksCompleted[0] = false;
            }

            if (task.getOwnedBy() != null) {
                JPanel taskRow = new JPanel();
                taskRow.setLayout(new BoxLayout(taskRow, BoxLayout.Y_AXIS));
                taskRow.setBackground(new Color(0, 0, 0, 0));
                taskRow.setAlignmentX(Component.LEFT_ALIGNMENT);

                if (task.getOwnedBy() != null) {
                    JLabel ownedByLabel = new JLabel("Owned by " + task.getOwnedBy().getName());
                    ownedByLabel.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 16));
                    ownedByLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
                    taskPanel.add(ownedByLabel);
                }

                taskRow.add(createTask(task));
                taskPanel.add(taskRow);

                SubTask[] subTasks = task.getSteps();
                for (int i = 0; i < subTasks.length; i++) {
                    taskPanel.add(createSubTask(subTasks[i], i < task.getCurrentStepNumber()));
                    taskPanel.add(Box.createVerticalStrut(10));
                }

                taskPanel.add(Box.createVerticalStrut(5)); // Space between tasks
            }
        });

        if (allTasksCompleted[0]) {
            objectiveLabel.setText("<html><s>" + objectiveObj.getTitle() + "</s></html>");
        }

        objective.add(taskPanel, BorderLayout.CENTER);

        return objective;
    }

    /**
     * Creates a JPanel representation of a task. This panel contains the task's
     * title
     * and buttons for transferring and completing the task. The appearance and
     * layout
     * of the panel are styled for seamless integration into the UI.
     *
     * @param t The Task object containing the details of the task to be created.
     * @return A JPanel representing the task, complete with its title and action
     *         buttons.
     */
    private JPanel createTask(Task t) {
        JPanel task = new JPanel();
        task.setLayout(new BoxLayout(task, BoxLayout.X_AXIS));
        task.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // Indent tasks (left margin)
        task.setBackground(new Color(0, 0, 0, 0));

        JLabel taskLabel = new JLabel(t.getTitle());
        taskLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        if (t.isCompleted()) {
            taskLabel.setText("<html><s>" + t.getTitle() + "</s></html>");
        }

        task.add(taskLabel);

        task.add(Box.createHorizontalGlue());

        if (GameSystem.getTurnOrder().length > 1 && t.getOwnedBy() == GameSystem.getPlayerAt()) {
            ImageIcon transferIcon = new ImageIcon(
                    Objects.requireNonNull(getClass().getResource("/images/transferButton.png")));
            transferIcon.setImage(transferIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
            JButton transferButton = new JButton(transferIcon);

            transferButton.setBorder(null);
            transferButton.setFocusPainted(false);
            transferButton.setContentAreaFilled(false);
            transferButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            transferButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
            task.add(transferButton);
            task.add(Box.createHorizontalStrut(10));

            transferButton.addActionListener(e -> GameSystem.toggleTransfer(t));
        }

        boolean[] allTasksOwnedByPlayer = { true };
        t.getBelongsTo().getTasks().forEach(ta -> {
            if (ta.getOwnedBy() != GameSystem.getPlayerAt()) {
                allTasksOwnedByPlayer[0] = false;
            }
        });

        if (allTasksOwnedByPlayer[0] && !t.isCompleted()) {
            ImageIcon completeIcon = new ImageIcon(
                    Objects.requireNonNull(getClass().getResource("/images/completeTaskButton.png")));
            completeIcon.setImage(completeIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
            JButton completeButton = new JButton(completeIcon);

            completeButton.setBorder(null);
            completeButton.setFocusPainted(false);
            completeButton.setContentAreaFilled(false);
            completeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            completeButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
            task.add(completeButton);

            ActionListener okSingleButton = e -> {
                GameSystem.hidePopup();

                if (GameSystem.checkWinCondition()) {
                    GameSystem.toggleJournal();
                    GameSystem.toggleEndGame(Ending.GOOD);
                }
            };

            ActionListener progressTaskActionListener = e -> {
                GameSystem.hideCostPopup();
                if (GameSystem.progressTask(t)) {
                    GameSystem.refreshJournal();
                    GameSystem.showPopup("Task progressed successfuly!", t.getTitle() + " was progressed!", "Ok", null,
                            okSingleButton, null);
                } else {
                    GameSystem.showPopup("Task not progressed!",
                            t.getTitle() + " was not progressed due to lack of resources!", "Ok", null, okSingleButton,
                            null);
                }
            };

            ActionListener rejectTaskActionListener = e -> GameSystem.hideCostPopup();

            completeButton.addActionListener(e -> {
                GameSystem.showCostPopup("Do you wish to progress this Task?", "It will cost you ",
                        t.getResourceType(), t.getCurrentSubTask().getResourceCost(),
                        progressTaskActionListener, rejectTaskActionListener);
                refresh();
            });
        }

        task.add(Box.createHorizontalStrut(50));

        return task;
    }

    /**
     * Creates a JPanel representing a sub-task with the given details.
     * The panel contains a formatted label displaying the sub-task's title,
     * and it is styled with specific layout, alignment, and background properties.
     *
     * @param t The SubTask object containing the details of the sub-task.
     * @return A JPanel representing the sub-task.
     */
    private JPanel createSubTask(SubTask t, boolean isComplete) {
        JPanel task = new JPanel();
        task.setLayout(new BoxLayout(task, BoxLayout.X_AXIS)); // BoxLayout for vertical stacking
        task.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0)); // Indent subtasks (left margin)
        task.setBackground(new Color(0, 0, 0, 0));
        task.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextPane taskLabel = new JTextPane();
        taskLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        taskLabel.setMaximumSize(new Dimension(350, 100));
        taskLabel.setEditable(false);
        taskLabel.setBackground(new Color(214, 183, 109));
        taskLabel.setHighlighter(null);
        taskLabel.setCaretColor(new Color(0, 0, 0, 0));
        task.add(taskLabel);

        if (isComplete) {
            StyledDocument doc = taskLabel.getStyledDocument();
            SimpleAttributeSet attributes = new SimpleAttributeSet();
            StyleConstants.setStrikeThrough(attributes, true);
            try {
                doc.insertString(doc.getLength(), t.getTitle(), attributes);
            } catch (BadLocationException e) {
                System.err.println("Error inserting text into JTextPane: " + e.getMessage());
            }
        } else {
            taskLabel.setText(t.getTitle());
        }

        return task;
    }

    /**
     * Creates a JScrollPane with a custom vertical scroll bar.
     *
     * @return A JScrollPane.
     */
    private JScrollPane createScrollPane() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(24, 43, 617 - 25, 427);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setBackground(new Color(0, 0, 0, 0));
        scrollPane.getVerticalScrollBar().setBorder(null);
        renderScrollBar(scrollPane);

        return scrollPane;
    }

    /**
     * Adds a close button to the given page.
     *
     * @param page The page to add the close button to.
     */
    private void addCloseButton(JPanel page) {
        URL closeIconUrl = getClass().getResource("/images/closeJournal.png");
        URL closeIconRedUrl = getClass().getResource("/images/closeRed.png");

        if (closeIconUrl == null || closeIconRedUrl == null) {
            System.err.println("Error: Close button image(s) not found for Journal!");
            return;
        }

        ImageIcon closeIcon = new ImageIcon(
                new ImageIcon(closeIconUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon closeIconRed = new ImageIcon(
                new ImageIcon(closeIconRedUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

        JPanel closeButtonPanel = new JPanel(new BorderLayout());
        closeButtonPanel.setOpaque(false);
        closeButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        closeButtonPanel.setMaximumSize(new Dimension(700, 30));

        closeButton = new JButton();
        closeButton.setIcon(closeIcon);
        closeButton.setBorder(null);
        closeButton.setContentAreaFilled(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        closeButton.addActionListener(e -> GameSystem.toggleJournal());

        closeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                closeButton.setIcon(closeIconRed);
            }

            public void mouseExited(MouseEvent evt) {
                closeButton.setIcon(closeIcon);
            }
        });

        closeButtonPanel.add(closeButton, BorderLayout.EAST);
        page.add(closeButtonPanel);
    }

    /**
     * Renders a custom vertical scroll bar with translucent black thumb and no
     * buttons.
     *
     * @param scrollPane The JScrollPane to apply custom scroll bar to.
     */
    private void renderScrollBar(JScrollPane scrollPane) {
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(173, 133, 76));
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }

            // Remove track
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            }

            // Remove scrollbar buttons
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            /**
             * Creates a zero-size button to remove the scrollbar buttons.
             *
             * @return A JButton with zero size.
             */
            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });
    }

    /**
     * Paints the component by drawing the background image.
     *
     * @param g The graphics context used to paint the component.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
