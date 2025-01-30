package square;

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
    /**
     * Constructs a TaskSquare object with the specified task.
     * This task will be available for players who land on this square.
     *
     * @param ts The task associated with this square.
     */
    public TaskSquare(Task ts) {
        super();
        this.task = ts;
    }

    /**
     * Activates the task associated with this square for a specific player.
     * If the player's role matches the required role for the task, the task is executed.
     * Otherwise, a message is displayed indicating that the player's role is not suitable for the task.
     *
     * @param p The player who landed on the TaskSquare.
     * @return true if the task was successfully activated and completed,
     *         false if the player's role does not match the task's requirements.
     */
    @Override
    public boolean activateSquareEffect() {
        if (primaryOccupier.getRole() == task.getRole()) {
            // Execute the task
            task.doTask();  // Perform the task's actions
            return true;  // Indicate success
        } else {
            // Notify the player that their role is not suitable for this task
            return false;  // Indicate failure
        }
    }

    public SquareType getSquareType() {
        return this.sType;
    }
}
