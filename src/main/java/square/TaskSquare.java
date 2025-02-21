package square;

import java.awt.Color;
import java.awt.event.ActionListener;
import BoardGame.*;
import GameSystem.*;

public class TaskSquare extends Square {

    /**
     * The task associated with this square, which players can attempt to complete.
     */
    private Task task;

    /**
     * The type of this square, indicating it is a TaskSquare.
     */
    private final SquareType sType = SquareType.TASKSQUARE;

    /**
     * The color representation of this square.
     */
    private final Color squareColor = Color.RED;

    /**
     * Constructs a TaskSquare object with the specified task.
     * This task will be available for players who land on this square.
     * Initializes the squareColor to Color.RED and sType to SquareType.TASKSQUARE.
     *
     * @param ts The task associated with this square.
     */
    public TaskSquare(Task ts) {
        super();
        this.task = ts;
    }

    /**
     * When a player lands on a TaskSquare, if it is not claimed by another player,
     * they have the opportunity to claim it.
     * If the player doesn't wish to claim this task, they can choose to pass it to
     * another player or leave it unclaimed.
     * However, if the task is already claimed by another player, the current player
     * can choose to help complete the task.
     * 
     * @return {@code true} if the task was successfully activated, {@code false}
     *         otherwise.
     */
    @Override
    public boolean activateSquareEffect() {

        ActionListener takeTask = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Take task logic
                task.setOwnedBy(GameSystem.getPlayerAt());
                System.out.println("Task claimed!");
                GameSystem.hidePopup();
            }
        };

        ActionListener rejectTask = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Reject task logic
                System.out.println("Show the task to other players");
                GameSystem.hidePopup();
            }
        };

        ActionListener beginHelping = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Discounting task logic
                System.out.println("Discounting the task!");
                GameSystem.hidePopup();
            }
        };

        ActionListener ignoreHelping = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GameSystem.hidePopup();
            }
        };

        ActionListener okSingleButton = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GameSystem.hidePopup();
            }
        };

        super.activateSquareEffect();
        if (task.getOwnedBy() == null) {
            GameSystem.showPopup("Do you want to get this task?", "", "Yes", "No", takeTask, rejectTask);
        } else if (task.getOwnedBy() != GameSystem.getPlayerAt()) {
            GameSystem.showPopup("Do you want to help complete this task?", task.getDescription(), "Yes", "No",
                    beginHelping, ignoreHelping);
        } else {
            GameSystem.showPopup("You already own this task!", null, "Ok", null, okSingleButton, null);
        }
        return true;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * Returns the type of this square.
     *
     * @return The square type, which is {@code SquareType.TASKSQUARE}.
     */
    public SquareType getSquareType() {
        return this.sType;
    }

    /**
     * Returns the color associated with this square.
     *
     * @return The color of this square, which is {@code Color.RED}.
     */
    public Color getColor() {
        return this.squareColor;
    }
}
