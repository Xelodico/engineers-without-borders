package square;

import java.awt.Color;
import java.awt.event.ActionListener;
import BoardGame.*;
import GameSystem.*;

/**
 * The TaskSquare class represents a square on the game board that contains a
 * task.
 * Players can attempt to complete the task when they land on this square.
 *
 * @author Kal Worthington
 * @author Antons Bogdanovs
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
    private Color squareColor = Color.BLACK;

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
        if (ts.getBelongsTo() != null) {
            this.squareColor = ts.getBelongsTo().getUiColour();
        }
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

        ActionListener okSingleButton = e -> GameSystem.hidePopup();

        ActionListener rejectTask = e -> {
            // Reject task logic
            if (GameSystem.getTurnOrder().length > 1) {
                System.out.println("Show the task to other players");
                GameSystem.hideCostPopup();
                GameSystem.toggleTransfer(task);
            } else {
                System.out.println("No other players to show the task to");
                GameSystem.hideCostPopup();
                GameSystem.showPopup("Task not claimed!", task.getTitle() + " was not claimed due to poor funding.",
                        "Ok", null, okSingleButton, null);
            }
        };

        ActionListener beginHelping = e -> {
            // Discounting task logic
            if (GameSystem.discountSubTask(task)) {
                GameSystem.showPopup("Task discounted!", "The current Subtask \""
                        + task.getCurrentSubTask().getTitle() + "\" has been discounted!\nGood luck!", "OK", null,
                        okSingleButton, null);
            } else {
                GameSystem.showPopup("Not enough resources!",
                        "You do not have enough resources to help with this task.", "OK", null, okSingleButton,
                        null);
            }
            GameSystem.hideCostPopup();
        };

        ActionListener ignoreHelping = e -> GameSystem.hideCostPopup();

        ActionListener takeTask = e -> {
            // Take task logic
            if (GameSystem.purchaseTask(GameSystem.getPlayerAt(), task.getResourceType(), task)) {
                task.setOwnedBy(GameSystem.getPlayerAt());
            } else {
                GameSystem.showPopup("Not enough resources!",
                        "You do not have enough resources to claim this task.", "OK", null, okSingleButton, null);
                return;
            }

            System.out.println("Task claimed!");
            GameSystem.hideCostPopup();
        };

        super.activateSquareEffect();
        if (task.getOwnedBy() == null) {
            GameSystem.showCostPopup("Do you want to get this task?",
                    task.getTitle() + "\nDo you want to buy this task for ", task.getResourceType().toString(),
                    task.getResourceCost(), takeTask, rejectTask);
        } else if (task.getOwnedBy() != GameSystem.getPlayerAt()) {
            if (task.getCurrentSubTask().isDiscounted()) {
                GameSystem.showPopup("Task already discounted!",
                        "The current Subtask \"" + task.getCurrentSubTask().getTitle()
                                + "\" has already been discounted!\nCome back again later!",
                        "OK", null, okSingleButton, null);
            } else {
                GameSystem.showCostPopup("Do you want to help with this task?",
                        "The current Subtask is \"" + task.getCurrentSubTask().getTitle()
                                + "\".\nWould you like to help out by spending ",
                        task.getCurrentSubTask().getResourceType().toString(),
                        task.getCurrentSubTask().getResourceCost() / 2, beginHelping, ignoreHelping);
            }
        } else {
            GameSystem.showPopup("You already own this task!", task.getTitle(), "OK", null, okSingleButton, null);
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
