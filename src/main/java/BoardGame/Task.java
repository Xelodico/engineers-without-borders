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
	private Objective belongsTo;
	private Player ownedBy;
	private String description;
	private SubTask[] steps;
	private int currentStepNumber;
	private int completionScore;
	private int resourceCost;
	private ResourceType resourceType;

	
	// METHODS //
	public Task(String title, Objective belongsTo, String description, SubTask[] steps, int completionScore, int resourceCost, ResourceType resourceType) {
		this.title = title;
		this.belongsTo = belongsTo;
		this.ownedBy = null;
		this.description = description;
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
		this.description = "";
		this.steps = new SubTask[0];
		this.currentStepNumber = 0;
		this.completionScore = 0;
		this.resourceCost = 0;
		this.resourceType = ResourceType.ASPHALT;
	}

	public SubTask currentSubTask() {
		return steps[currentStepNumber];
	}

	public boolean completeStep() {
		if (currentStepNumber < steps.length) {
			currentSubTask().setCompleted(true);
			currentStepNumber++;
			return true;
		}
		return false;
	}

	public boolean isCompleted() {
		return currentStepNumber == steps.length;
	}

	// GETTERS & SETTERS //
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Objective getBelongsTo() {
		return belongsTo;
	}

	public void setBelongsTo(Objective belongsTo) {
		this.belongsTo = belongsTo;
	}

	public Player getOwnedBy() {
		return ownedBy;
	}

	public void setOwnedBy(Player ownedBy) {
		this.ownedBy = ownedBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SubTask[] getSteps() {
		return steps;
	}

	public void setSteps(SubTask[] steps) {
		this.steps = steps;
	}

	public SubTask getCurrentStep() {
		return steps[currentStepNumber];
	}
	
	public void setCurrentStep(SubTask newStep) {
		this.steps[currentStepNumber] = newStep;
	}
	
	public int getCurrentStepNumber() {
		return currentStepNumber;
	}

	public void setCurrentStepNumber(int currentStep) {
		this.currentStepNumber = currentStep;
	}

	public int getCompletionScore() {
		return completionScore;
	}

	public void setCompletionScore(int completionScore) {
		this.completionScore = completionScore;
	}

	public int getResourceCost() {
		return resourceCost;
	}

	public void setResourceCost(int resourceCost) {
		this.resourceCost = resourceCost;
	}
	
	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}
}
