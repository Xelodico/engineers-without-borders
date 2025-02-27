package BoardGame;

/**
 * Represents a sub-task within a board game.
 * Each sub-task has a title, completion status, discount status, completion score, and resource costs.
 */
public class SubTask {
    private String title;
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
        this.discounted = false;
        this.completionScore = 0;
        this.resourceCost = 0;
        this.resourceType = ResourceType.ASPHALT;
    }

    /**
     * 'Discounts' the SubTask by halving its resource cost and then setting the isDiscounted flag to true. 
     * A SubTask can only be discounted once
     * 
     * @return true if the SubTask was discounted, false if the SubTask was already discounted
     */
    private boolean discountSubTask() {
        if(!isDiscounted()) {
        	setResourceCost(this.resourceCost / 2);
        	setDiscounted(true);
        	return true;
        }
        return false;
    }

    /**
     * Gets the title
     * 
     * @return the title of the SubTask
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of title
     * 
     * @param title - The new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the discount status
     * 
     * @return true if the subtask is discounted, false if not
     */
    public boolean isDiscounted() {
        return discounted;
    }

    /**
     * Sets the discount status
     * 
     * @param discounted - The new discount status of the SubTask
     */
    public void setDiscounted(boolean discounted) {
        this.discounted = discounted;
    }

    /**
     * Gets the score for completing this SubTask
     * 
     * @return the score for completing the SubTask
     */
    public int getCompletionScore() {
        return completionScore;
    }

    /**
     * Sets the score received upon completion
     * 
     * @param completionScore - The new score received upon completion
     */
    public void setCompletionScore(int completionScore) {
        this.completionScore = completionScore;
    }

    /**
     * Gets the resource cost for completing this SubTask
     * 
     * @return the resource cost for completion
     */
    public int getResourceCost() {
        return resourceCost;
    }

    /**
     * Sets the resource cost for completing this SubTask
     * 
     * @param resourceCost - The new resource cost required for completion
     */
    public void setResourceCost(int resourceCost) {
        this.resourceCost = resourceCost;
    }

    /**
     * Gets the type of resource needed for completion
     * 
     * @return the type of resource needed for completion
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     * Sets the type of resource needed for completion
     * 
     * @param resourceType - The new type of resource needed for completion
     */
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    

}
