package BoardGame;

/**
 * Represents a sub-task within a board game.
 * Each sub-task has a title, completion status, discount status, completion score, and resource costs.
 * @author Curtis McCartney
 * @author Isaac Edmonds (Supporting)
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
     * @param resourceCost the cost of the resource to be used in the completion of the subtask
     * @param resourceType the type of resource to be used in the completion of the subtask
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
        this.completionScore = 5;
        this.resourceCost = 20;
        this.resourceType = null;
    }

    /**
     * 'Discounts' the SubTask by halving its resource cost and then setting the isDiscounted flag to true. 
     * A SubTask can only be discounted once.
     * In the event of an odd value for the completion score before discount, the number will be rounded up
     * 
     * @return true if the SubTask was discounted, false if the SubTask was already discounted
     */
    public boolean discountSubTask() {
        if(!isDiscounted()) {
        	setResourceCost(this.resourceCost / 2 + (this.completionScore % 2));
        	setCompletionScore(this.completionScore / 2 + (this.completionScore % 2));
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
