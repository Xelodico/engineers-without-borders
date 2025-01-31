package BoardGame;

/**
 * @author Isaac Edmonds
 * 
 * A Task object represents a subtask for its corresponding role that the player responsible for that role must complete
 * to progress the game. 
 */
public class Task {
	
	// ATTRIBUTES //
	private String title;
	private JobRole belongsTo;
	private String description;
	private boolean completed;
	private int availableStage;
	
	// METHODS //
	/**
	 * @param title - The title of this Task
	 * @param belongsTo - The JobRole this Task is a subtask of
	 * @param description - The description to be shown to the player when this task is shown on screen
	 * @param availableStage - The stage this task is available on
	 * 
	 * A constructor that takes in values for every attribute excluding 'completed'. This value is defaulted to False.
	 */
	public Task(String title, JobRole belongsTo, String description, int availableStage) {
		this.title = title;
		this.belongsTo = belongsTo;
		this.description = description;
		this.completed = false;
		this.availableStage = availableStage;
	}
	
	/**
	 * A default constructor that takes in no parameters and essentially creates an 'empty' Task object.
	 */
	public Task() {
		this.title = "";
		this.belongsTo = null;
		this.description = "";
		this.completed = false;
		this.availableStage = 0;
	}

	// GETTERS //
	/**
	 * @return the title of this Task
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the JobRole this task belongs to
	 */
	public JobRole getRole() {
		return belongsTo;
	}

	/**
	 * @return the description of this task to show the player
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return True if the task has been completed (completed == True), False if not
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * @return the stage of the game this task is available on
	 */
	public int getAvailableStage() {
		return availableStage;
	}

	
	// SETTERS //
	/**
	 * @param title - The new title of this task
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param belongsTo - The new JobRole this task is a subtask of
	 */
	public void setRole(JobRole belongsTo) {
		this.belongsTo = belongsTo;
	}

	/**
	 * @param description - The new description of this task
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param completed - The new state of completion for this task
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	/**
	 * @param availableStage - The new stage this task is available on
	 */
	public void setAvailableStage(int availableStage) {
		this.availableStage = availableStage;
	}
	
	
}
