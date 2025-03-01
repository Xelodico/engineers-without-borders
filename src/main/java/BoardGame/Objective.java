package BoardGame;

import java.awt.Color;
import java.util.ArrayList;

/**
 * The Objective class represents a goal or objective that one Player can take for the game.
 * Each objective has a title, a responsible player, a list of tasks, and a UI color.
 * 
 * @author Curtis McCartney
 * 
 */
public class Objective {
    
    private String title;
    private Player responsiblePlayer;
    private ArrayList<Task> tasks;
    private Color uiColour;

    /**
     * Constructs a new Objective with the specified title, responsible player, tasks, and UI color.
     *
     * @param title the title of the objective
     * @param responsiblePlayer the player responsible for the objective
     * @param tasks the list of tasks associated with the objective
     * @param uiColour the UI color associated with the objective
     */
    public Objective(String title, Player responsiblePlayer, ArrayList<Task> tasks, Color uiColour) {
        this.title = title;
        this.responsiblePlayer = responsiblePlayer;
        this.tasks = tasks;
        this.uiColour = uiColour;
    }

    /**
     * @param title
     */
    public Objective(String title) {
        this.title = title;
        this.responsiblePlayer = null;
        this.tasks = new ArrayList<>();
        this.uiColour = Color.BLACK;
    }

    /**
     * Checks if all tasks in the objective are completed.
     *
     * @return true if all tasks are completed, false otherwise
     */
    public boolean isCompleted() {
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if all tasks in the objective are owned by the specified player.
     *
     * @param player the player to check ownership against
     * @return true if all tasks are owned by the specified player, false otherwise
     */
    public boolean allTasksOwnedBy(Player player) {
        for(Task task : tasks) {
            if(task.getOwnedBy() != player) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the title of the objective.
     *
     * @return the title of the objective
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the objective.
     *
     * @param title the new title of the objective
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the player responsible for the objective.
     *
     * @return the player responsible for the objective
     */
    public Player getResponsiblePlayer() {
        return responsiblePlayer;
    }

    /**
     * Sets the player responsible for the objective.
     *
     * @param responsiblePlayer the new player responsible for the objective
     */
    public void setResponsiblePlayer(Player responsiblePlayer) {
        this.responsiblePlayer = responsiblePlayer;
    }

    /**
     * Gets the list of tasks associated with the objective.
     *
     * @return the list of tasks associated with the objective
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Sets the list of tasks associated with the objective.
     *
     * @param tasks the new list of tasks associated with the objective
     */
    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Gets the UI color associated with the objective.
     *
     * @return the UI color associated with the objective
     */
    public Color getUiColour() {
        return uiColour;
    }

    /**
     * Sets the UI color associated with the objective.
     *
     * @param uiColour the new UI color associated with the objective
     */
    public void setUiColour(Color uiColour) {
        this.uiColour = uiColour;
    }
}
