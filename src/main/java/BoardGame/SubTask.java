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
    private int resourceCost1;
    private int resourceCost2;
    private int resourceCost3;
    private int resourceCost4;

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
    public SubTask(String title, int completionScore, int resourceCost1, int resourceCost2, int resourceCost3, int resourceCost4) {
        this.title = title;
        this.completed = false;
        this.discounted = false;
        this.completionScore = completionScore;
        this.resourceCost1 = resourceCost1;
        this.resourceCost2 = resourceCost2;
        this.resourceCost3 = resourceCost3;
        this.resourceCost4 = resourceCost4;
    }

    /**
     * Constructs a new SubTask with default values.
     */
    public SubTask() {
        this.title = "";
        this.completed = false;
        this.discounted = false;
        this.completionScore = 0;
        this.resourceCost1 = 0;
        this.resourceCost2 = 0;
        this.resourceCost3 = 0;
        this.resourceCost4 = 0;
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
