package Popup;

import javax.swing.*;

import BoardGame.BoardGameUI;
import BoardGame.Objective;
import BoardGame.SubTask;
import BoardGame.Task;
import GameSystem.GameSystem;
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
 */
public class Journal extends JPanel {

    // The background image for the journal
    private BufferedImage backgroundImage;

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
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/journalBackground.png")));
        } catch (NullPointerException | IOException e) {
            System.err.println("Error: Journal background image not found!");
            System.exit(1);
        }

        setLayout(null);
        setBounds((BoardGameUI.WINDOW_WIDTH / 2 - WIDTH / 2), (650 / 2 - HEIGHT / 2) + 6, WIDTH, HEIGHT);
        setBackground(new java.awt.Color(0, 0, 0, 0));
        setVisible(false);

        // Title Label for the objectives section
        JLabel title = new JLabel("Objectives");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.LEFT);
        title.setBounds(24, 13, 617, 30);
        
        add(title);

        // Set scroll bar width
        UIManager.put("ScrollBar.width", 10);

        JScrollPane scrollPane = createScrollPane();
        add(scrollPane);

        // Create the page content container
        JPanel page = new JPanel();
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));
        page.setOpaque(false);

        addCloseButton(page);

        // Add objectives with their tasks
        JPanel objective1 = createObjective(0);
        page.add(objective1);

        JPanel objective2 = createObjective(1);
        page.add(objective2);

        JPanel objective3 = createObjective(2);
        page.add(objective3);

        JPanel objective4 = createObjective(3);
        page.add(objective4);

        // Add the page (which contains the objectives) to the scroll pane
        scrollPane.setViewportView(page);
    }

    /**
     * Creates a JPanel representing an objective with a given index
     *
     * @param objectiveIndex The index of the objective to be created
     * @return The created objective as a JPanel
     */
    private JPanel createObjective(int objectiveIndex) {

        Objective objectiveObj = GameSystem.getObjectives().get(objectiveIndex);
        ArrayList<Task> tasksObj = objectiveObj.getTasks();

        JPanel objective = new JPanel();
        objective.setLayout(new BorderLayout());
        objective.setBackground(new java.awt.Color(0, 0, 0, 0));
        objective.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around the objective
        JLabel objectiveLabel = new JLabel(objectiveObj.getTitle());
        objectiveLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        objectiveLabel.setHorizontalAlignment(SwingConstants.LEFT);
        objective.add(objectiveLabel, BorderLayout.NORTH);

        // Create a task panel with a vertical BoxLayout
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        taskPanel.setBackground(new java.awt.Color(0, 0, 0, 0));
        taskPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        tasksObj.forEach(task -> {
            JPanel taskRow = new JPanel();
            taskRow.setLayout(new BoxLayout(taskRow, BoxLayout.Y_AXIS));
            taskRow.setBackground(new java.awt.Color(0, 0, 0, 0));
            taskRow.setAlignmentX(Component.LEFT_ALIGNMENT);
            taskRow.add(createTask(task));
            taskPanel.add(taskRow);

            SubTask[] subTasks = task.getSteps();
            for (SubTask subTask : subTasks) {
                taskPanel.add(createSubTask(subTask));
                taskPanel.add(Box.createVerticalStrut(10));
            }

            taskPanel.add(Box.createVerticalStrut(5)); // Space between tasks
        });

        objective.add(taskPanel, BorderLayout.CENTER);

        return objective;
    }

    /**
     * Creates a JPanel representing a task with the given description.
     *
     * @param t The task to create.
     * @return A JPanel containing the task label and buttons.
     */
    private JPanel createTask(Task t) {
        JPanel task = new JPanel();
        task.setLayout(new BoxLayout(task, BoxLayout.X_AXIS));
        task.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // Indent tasks (left margin)
        task.setBackground(new java.awt.Color(0, 0, 0, 0));

        JLabel taskLabel = new JLabel(t.getTitle());
        taskLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        task.add(taskLabel);

        task.add(Box.createHorizontalGlue());

        ImageIcon transferIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/transferButton.png")));
        transferIcon.setImage(transferIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        JButton transferButton = new JButton(transferIcon);

        transferButton.setBorder(null);
        transferButton.setFocusPainted(false);
        transferButton.setContentAreaFilled(false);
        transferButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        transferButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        task.add(transferButton);

        task.add(Box.createHorizontalStrut(100));

        return task;
    }

    private JPanel createSubTask(SubTask t) {
        JPanel task = new JPanel();
        task.setLayout(new BoxLayout(task, BoxLayout.X_AXIS)); // BoxLayout for vertical stacking
        task.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0)); // Indent subtasks (left margin)
        task.setBackground(new java.awt.Color(0, 0, 0, 0));
        task.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ownedByLabel = new JLabel();

        JTextArea taskLabel = new JTextArea(t.getTitle());
        taskLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        taskLabel.setMaximumSize(new Dimension(350, 100));
        taskLabel.setLineWrap(true);
        taskLabel.setWrapStyleWord(true);
        taskLabel.setEditable(false);
        taskLabel.setOpaque(false);
        task.add(taskLabel);

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
        scrollPane.getVerticalScrollBar().setBackground(new java.awt.Color(0, 0, 0, 0));
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
            new ImageIcon(closeIconUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)
        );
        ImageIcon closeIconRed = new ImageIcon(
            new ImageIcon(closeIconRedUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)
        );
    
        JPanel closeButtonPanel = new JPanel(new BorderLayout());
        closeButtonPanel.setOpaque(false);
        closeButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
    
        JButton closeButton = new JButton();
        closeButton.setIcon(closeIcon);
        closeButton.setBorder(null);
        closeButton.setContentAreaFilled(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameSystem.toggleJournal();
            }
        });
    
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

                // Translucent thumb
                g2.setColor(new Color(173, 133, 76, 150));
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
