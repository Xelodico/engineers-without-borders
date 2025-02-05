package BoardGame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Journal extends JPanel {
    private BufferedImage backgroundImage;

    public Journal() {
        final int width = 700;
        final int height = 500;

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/journalBackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(null);
        setBounds((BoardGameUI.WINDOW_WIDTH / 2 - width / 2), (650 / 2 - height / 2) + 6, width, height);
        setBackground(new java.awt.Color(0, 0, 0, 0));
        setVisible(false);

        UIManager.put("ScrollBar.width", 10);

        // Create the JScrollPane to hold the page
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(24, 43, 617 - 25, 427);  // The visible area of the scroll pane
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Always show vertical scroll bar
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);  // Disable horizontal scrolling
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);  // Increase scroll speed
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setBackground(new java.awt.Color(0, 0, 0, 0));
        scrollPane.getVerticalScrollBar().setBorder(null);
        renderScrollBar(scrollPane);
        add(scrollPane);

        // Create the page content (a JPanel with a BoxLayout)
        JPanel page = new JPanel();
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));  // BoxLayout to stack components vertically
        page.setOpaque(false);

        // Objective panels and tasks
        JPanel objective1 = createObjective("Objective 1: ");
        page.add(objective1);

        JPanel objective2 = createObjective("Objective 2: ");
        page.add(objective2);

        JPanel objective3 = createObjective("Objective 3: ");
        page.add(objective3);

        JPanel objective4 = createObjective("Objective 4: ");
        page.add(objective4);

        // Add the page (which contains the text area) to the scroll pane
        scrollPane.setViewportView(page);

        // Title Label (to give some title)
        JLabel title = new JLabel("Objectives");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.LEFT);
        title.setBounds(24, 13, 617, 30);
        add(title);
    }

    private JPanel createObjective(String text) {
        JPanel objective = new JPanel();
        objective.setLayout(new BorderLayout());
        objective.setBackground(new java.awt.Color(0, 0, 0, 0));
        objective.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Padding around the objective
        JLabel objectiveLabel = new JLabel(text);
        objectiveLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        objectiveLabel.setHorizontalAlignment(SwingConstants.LEFT);
        objective.add(objectiveLabel, BorderLayout.NORTH);
        
        // Add a vertical BoxLayout to the objective to stack the tasks
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        taskPanel.setBackground(new java.awt.Color(0, 0, 0, 0));
        
        // Add the tasks with a gap between them
        taskPanel.add(createTask("Task 1: Find the key to unlock the door."));
        taskPanel.add(Box.createVerticalStrut(10)); // Vertical space between tasks
        taskPanel.add(createTask("Task 2: Find the key to unlock the door."));
        taskPanel.add(Box.createVerticalStrut(10)); // Vertical space between tasks
        taskPanel.add(createTask("Task 3: Find the key to unlock the door."));
        
        objective.add(taskPanel, BorderLayout.CENTER);
        
        return objective;
    }

    private JPanel createTask(String text) {
        JPanel task = new JPanel();
        task.setLayout(new BoxLayout(task, BoxLayout.Y_AXIS));  // BoxLayout for vertical stacking
        task.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));  // Indent subtasks (left margin)
        task.setBackground(new java.awt.Color(0, 0, 0, 0));
        JLabel taskLabel = new JLabel(text);
        taskLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        task.add(taskLabel);
        return task;
    }

    private void renderScrollBar(JScrollPane scrollPane) {
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Translucent black thumb
                g2.setColor(new Color(173, 133, 76, 150));  // Black with transparency
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }

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

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
