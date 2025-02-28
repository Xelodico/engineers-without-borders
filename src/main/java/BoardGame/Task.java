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
	private SubTask[] steps;
	private int currentStep;
	private int completionScore;
	private int resourceCost1;
	private int resourceCost2;
	private int resourceCost3;
	private int resourceCost4;
	
	// METHODS //
	public Task(String title, Objective belongsTo, SubTask[] steps, int completionScore, int resourceCost1, int resourceCost2, int resourceCost3, int resourceCost4) {
		this.title = title;
		this.belongsTo = belongsTo;
		this.ownedBy = null;
		this.steps = steps;
		this.currentStep = 0;
		this.completionScore = completionScore;
		this.resourceCost1 = resourceCost1;
		this.resourceCost2 = resourceCost2;
		this.resourceCost3 = resourceCost3;
		this.resourceCost4 = resourceCost4;
	}

	public Task() {
		this.title = "";
		this.belongsTo = null;
		this.ownedBy = null;
		this.steps = new SubTask[0];
		this.currentStep = 0;
		this.completionScore = 0;
		this.resourceCost1 = 0;
		this.resourceCost2 = 0;
		this.resourceCost3 = 0;
		this.resourceCost4 = 0;
	}

	public SubTask currentSubTask() {
		return steps[currentStep];
	}

	public boolean completeStep() {
		if (currentStep < steps.length) {
			currentStep++;
			return true;
		}
		return false;
	}

	public boolean isCompleted() {
		return currentStep == steps.length;
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

	public SubTask[] getSteps() {
		return steps;
	}

	public void setSteps(SubTask[] steps) {
		this.steps = steps;
	}

	public void addStep(SubTask step) {
		SubTask[] newSteps = new SubTask[steps.length + 1];
		for(int i = 0; i < steps.length; i++) {
			newSteps[i] = steps[i];
		}
		newSteps[steps.length] = step;
		steps = newSteps;
	}

	public int getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

	public int getCompletionScore() {
		return completionScore;
	}

	public void setCompletionScore(int completionScore) {
		this.completionScore = completionScore;
	}

	public int getResourceCost1() {
		return resourceCost1;
	}

	public void setResourceCost1(int resourceCost1) {
		this.resourceCost1 = resourceCost1;
	}

	public int getResourceCost2() {
		return resourceCost2;
	}

	public void setResourceCost2(int resourceCost2) {
		this.resourceCost2 = resourceCost2;
	}

	public int getResourceCost3() {
		return resourceCost3;
	}

	public void setResourceCost3(int resourceCost3) {
		this.resourceCost3 = resourceCost3;
	}

	public int getResourceCost4() {
		return resourceCost4;
	}

	public void setResourceCost4(int resourceCost4) {
		this.resourceCost4 = resourceCost4;
	}
}
