package square;

/**
 * Represents a square on a game board that can be occupied by a player.
 */
public class Square {

    private Player primaryOccupier;

    /**
     * Constructs a new Square instance with no primary occupier.
     */
    public Square() {
        this.primaryOccupier = null;
    }

    /**
     * Activates the effect associated with the square.
     * This method should be overridden in subclasses to provide specific effects.
     */
    public void activateSquareEffect() {
        // No default behavior. Override for specific effects.
    }

    /**
     * Checks if the square is already occupied by a player.
     *
     * @return {@code true} if the square is occupied, {@code false} otherwise.
     */
    public boolean alreadyOccupiedEffect() {
        return primaryOccupier != null;
    }

    /**
     * Gets the player currently occupying the square.
     *
     * @return The player occupying the square, or {@code null} if the square is unoccupied.
     */
    public Player getPrimaryOccupier() {
        return primaryOccupier;
    }

    /**
     * Sets the player occupying the square.
     *
     * @param occupier The player to occupy the square.
     */
    public void setPrimaryOccupier(Player occupier) {
        this.primaryOccupier = occupier;
    }
}
