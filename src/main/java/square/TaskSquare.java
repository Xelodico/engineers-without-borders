package square;

import java.awt.Color;
import BoardGame.*;
import GameSystem.*;

/**
 * Represents a square on the game board that contains a specific task.
 * When a player lands on this square, they may attempt to complete the associated task,
 * provided their role matches the role required for the task.
 */
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
     * Constructs a TaskSquare with the specified task.
     *
     * @param ts The task associated with this square.
     */
    public TaskSquare(Task ts) {
        super();
        this.task = ts;
    }

    /**
     * Activates the task associated with this square for the current player.
     * If the player's role matches the required role for the task, the task is executed.
     *
     * @return {@code true} if the task was successfully activated and completed,
     *         {@code false} if the player's role does not match the task's requirements.
     */
    @Override
    public boolean activateSquareEffect() {
        super.activateSquareEffect();
        if (getCurrentPlayer().getRoles().contains(task.getRole())) {
            task.doTask(); // Perform the task's actions
            return true; // Indicate success
        } else {
            return false; // Indicate failure
        }
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
