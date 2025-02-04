package square;
import java.awt.Color;

import BoardGame.*;
import GameSystem.*;

/**
 * The TaskSquare class represents a square on the game board that contains a specific task.
 * When a player lands on this square, they have the opportunity to activate and complete the task,
 * provided their role matches the role required for the task.
 */
public class TaskSquare extends Square {

    /**
     * The task associated with this square, which players can attempt to complete.
     */
    private Task task;
    private final SquareType sType = SquareType.TASKSQUARE;
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
     * When a player lands on a TaskSquare, if it is not claimed by another player, they have the opportunity to claim it.
     * If the player doesn't wish to claim this task, they can choose to pass it to another player or leave it unclaimed.
     * However, if the task is already claimed by another player, the current player can choose to help complete the task.
     * 
     * @return {@code true} if the task was successfully activated, {@code false} otherwise.
     */
    @Override
    public boolean activateSquareEffect() {
        /*
        super.activateSquareEffect(); 
        if (task.getOwner() == null) {
             popup("Do you want to get this task?", "Yes", "No");
                if (yes) {
                    task.setOwner(getPrimaryOccupier());
                } else {
                    popup("Who wants this task?", "Player 1", "Player 2", "Player 3", "Player 4", "Nobody");
                    if(player1) {
                        task.setOwner(player1);
                    } else if(player2) {
                        task.setOwner(player2);
                    } else if(player3) {
                        task.setOwner(player3);
                    } else if(player4) {
                        task.setOwner(player4);
                    } else {
                        task.setOwner(null);
                    }
                }
        } else if (task.getOwner() != getPrimaryOccupier()) {
            popup("Do you want to help complete this task?", "Yes", "No");
            if (yes) {
                task.addHelper(getPrimaryOccupier());
            }
        } else {
            popup("You already own this task!", "OK"); 
        }
        */
        return true;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public SquareType getSquareType() {
        return this.sType;
    }

    public Color getColor() {
        return this.squareColor;
    }
}
