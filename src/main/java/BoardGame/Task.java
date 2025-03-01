package BoardGame;

/**
 * @author Isaac Edmonds
 * @author Curtis McCartney (Supporting)
 * 
 * A Task object represents a subtask for its corresponding role that the player responsible for that role must complete
 * to progress the game. 
 */
public class Task {
	
	// ATTRIBUTES //
	private String title;
	private Objective belongsTo;
	private Player ownedBy;
	private SubTask[] steps;
	private int currentStepNumber;
	private int completionScore;
	private int resourceCost;
	private ResourceType resourceType;

	
	// METHODS //
	public Task(String title, Objective belongsTo, SubTask[] steps, int completionScore, int resourceCost, ResourceType resourceType) {
		this.title = title;
		this.belongsTo = belongsTo;
		this.ownedBy = null;
		this.steps = steps;
		this.currentStepNumber = 0;
		this.completionScore = completionScore;
		this.resourceCost = resourceCost;
		this.resourceType = resourceType;
	}

	public Task() {
		this.title = "";
		this.belongsTo = null;
		this.ownedBy = null;
		this.steps = new SubTask[0];
		this.currentStepNumber = 0;
		this.completionScore = 0;
		this.resourceCost = 0;
		this.resourceType = ResourceType.ASPHALT;
	}

	/**
	 * Progresses the task onto the next step, incrementing its currentStepNumber by 1 (if all subtasks 
	 * aren't already completed
	 * 
	 * @return true if currentStepNumber was incremented, false if it was already completed
	 */
	public boolean completeStep() {
		if (currentStepNumber < steps.length) {
			currentStepNumber++;
			return true;
		}
		return false;
	}

	/**
	 * Tells whether or not the task is completed
	 * 
	 * @return true if the task is completed, false if not
	 */
	public boolean isCompleted() {
		return currentStepNumber >= steps.length;
	}

	// GETTERS & SETTERS //
	/**
	 * Gets the title of the task
	 * 
	 * @return the title of the task
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the task
	 * 
	 * @param title - The new title of the task
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the objective object this task belongs to
	 * 
	 * @return the objective object this task belongs to
	 */
	public Objective getBelongsTo() {
		return belongsTo;
	}

	/**
	 * Sets this task as belonging to a new objective object
	 * 
	 * @param belongsTo - The new objective for this task to belong to
	 */
	public void setBelongsTo(Objective belongsTo) {
		this.belongsTo = belongsTo;
	}

	/**
	 * Gets the player who owns this task
	 * 
	 * @return the player who owns this task
	 */
	public Player getOwnedBy() {
		return ownedBy;
	}

	/**
	 * Sets the player who owns this task
	 * 
	 * @param ownedBy - The new player who will own this task
	 */
	public void setOwnedBy(Player ownedBy) {
		this.ownedBy = ownedBy;
	}

	/**
	 * Gets the array of SubTask steps that compose this Task
	 * 
	 * @return the array of SubTask steps of this Task
	 */
	public SubTask[] getSteps() {
		return steps;
	}

	/**
	 * Sets the array of SubTask steps of this Task
	 * 
	 * @param steps - The new array of SubTasks to give this Task
	 */
	public void setSteps(SubTask[] steps) {
		this.steps = steps;
	}

	/**
	 * Gets the current SubTask object that is being pointed to by currentStepNumber
	 * 
	 * @return The current SubTask referenced by currentStepNumber, or an 'empty' SubTask if the Task is completed
	 */
	public SubTask getCurrentSubTask() {
		if(this.currentStepNumber < steps.length) {
			return steps[currentStepNumber];
		} else {
			return new SubTask();
		}
	}
	
	/**
	 * Replaces the SubTask referenced by currentStepNumber with the inputed SubTask
	 * Only replaces if the Task is not completed
	 * 
	 * @param newStep - The new SubTask to replace the current one with
	 */
	public void setCurrentSubTask(SubTask newStep) {
		if(this.currentStepNumber < steps.length) {
			this.steps[currentStepNumber] = newStep;
		}
	}
	
	/**
	 * Gets the index of the current step this task is on
	 * 
	 * @return the index of the current step this task is on
	 */
	public int getCurrentStepNumber() {
		return currentStepNumber;
	}

	/**
	 * Sets a new index of the current step this task is on.
	 * Cannot be less than 0
	 * 
	 * @param currentStep - The index of the new task to mark as the current task
	 */
	public void setCurrentStepNumber(int currentStep) {
		if(currentStep >= 0) {
			this.currentStepNumber = currentStep;
		}
	}

	/**
	 * Gets the score to be awarded on completion of the task
	 * 
	 * @return the score to be gained upon completion
	 */
	public int getCompletionScore() {
		return completionScore;
	}

	/**
	 * Sets a new value to the score to be awarded on completion of the task
	 * 
	 * @param completionScore - The new score to be given when this task is completed
	 */
	public void setCompletionScore(int completionScore) {
		this.completionScore = completionScore;
	}

	/**
	 * Gets the resource cost of taking responsibility of this task
	 * 
	 * @return the resource cost of taking responsibility of this task
	 */
	public int getResourceCost() {
		return resourceCost;
	}

	/**
	 * Sets a new value for the resource cost of taking responsibility for this task
	 * 
	 * @param resourceCost - The new resource cost of taking responsibility of the task
	 */
	public void setResourceCost(int resourceCost) {
		this.resourceCost = resourceCost;
	}
	
	/**
	 * Gets the type of resource used to interact with this task (e.g. ASPHALT, VOLUNTEERS, etc)
	 * 
	 * @return the type of resource that is used to interact with this task
	 */
	public ResourceType getResourceType() {
		return resourceType;
	}

	/**
	 * Sets a new type of resource to be used to interact with this task (e.g. ASPHALT, VOLUNTEERS, etc)
	 * 
	 * @param resourceType - The new type of resource to be used to interact with this task
	 */
	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}
	
}
