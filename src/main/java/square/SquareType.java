package square;

/**
 * Enum representing the different types of squares on the game board.
 *
 * @author Kal Worthington
 * @author Antons Bogdanovs
 */
public enum SquareType {

    /**
     * A regular square with no special effect.
     */
    SQUARE,

    /**
     * A square that contains a specific task for players to complete.
     */
    TASKSQUARE,

    /**
     * A shop square where players can purchase items.
     */
    SHOPSQUARE,

    /**
     * A money square that provides or deducts money from the player.
     */
    MONEYSQUARE;
}