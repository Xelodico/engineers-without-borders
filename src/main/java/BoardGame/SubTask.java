package BoardGame;

/**
 * Represents a sub-task within a board game.
 * Each sub-task has a title, completion status, discount status, completion score, and resource costs.
 */
public class SubTask {
    private String title;
    private boolean completed;
    private boolean discounted;
    private int completionScore;
    private int resourceCost;
    private ResourceType resourceType;
    
    /**
     * Constructs a new SubTask with the specified title, completion score, and resource costs.
     *
     * @param title the title of the subtask
     * @param completionScore the score awarded upon completion of the subtask
     * @param resourceCost1 the cost of the first resource required to complete the subtask
     * @param resourceCost2 the cost of the second resource required to complete the subtask
     * @param resourceCost3 the cost of the third resource required to complete the subtask
     * @param resourceCost4 the cost of the fourth resource required to complete the subtask
     */
    public SubTask(String title, int completionScore, int resourceCost, ResourceType resourceType) {
        this.title = title;
        this.completed = false;
        this.discounted = false;
        this.completionScore = completionScore;
        this.resourceCost = resourceCost;
        this.resourceType = resourceType;
    }

    /**
     * Constructs a new SubTask with default values.
     */
    public SubTask() {
        this.title = "";
        this.completed = false;
        this.discounted = false;
        this.completionScore = 0;
        this.resourceCost = 0;
        this.resourceType = ResourceType.ASPHALT;
    }

    private boolean discountSubTask() {
        // TODO: Implement discountSubTask
        return false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isDiscounted() {
        return discounted;
    }

    public void setDiscounted(boolean discounted) {
        this.discounted = discounted;
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
