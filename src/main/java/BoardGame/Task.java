package BoardGame;

public class Task {
	
	// ATTRIBUTES //
	private String title;
	private JobRole belongsTo;
	private String description;
	private boolean completed;
	private int availableStage;
	
	// METHODS //
	public Task(String title, JobRole belongsTo, String description, int availableStage) {
		this.title = title;
		this.belongsTo = belongsTo;
		this.description = description;
		this.completed = false;
		this.availableStage = availableStage;
	}
	
	public Task() {
		this.title = "";
		this.belongsTo = null;
		this.description = "";
		this.completed = false;
		this.availableStage = 0;
	}

	// GETTERS //
	public String getTitle() {
		return title;
	}

	public JobRole getBelongsTo() {
		return belongsTo;
	}

	public String getDescription() {
		return description;
	}

	public boolean isCompleted() {
		return completed;
	}

	public int getAvailableStage() {
		return availableStage;
	}

	
	// SETTERS //
	public void setTitle(String title) {
		this.title = title;
	}

	public void setBelongsTo(JobRole belongsTo) {
		this.belongsTo = belongsTo;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public void setAvailableStage(int availableStage) {
		this.availableStage = availableStage;
	}
	
	
}
