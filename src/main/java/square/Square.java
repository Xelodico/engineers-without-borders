package square;

import java.awt.Color;

/**
 * Represents a square on a game board that can be occupied by a player.
 *
 * @author Kal Worthington
 * @author Antons Bogdanovs
 */
public class Square {

    private final SquareType sType = SquareType.SQUARE;
    private final Color squareColor = Color.WHITE;

    /**
     * Constructs a new Square instance.
     */
    public Square() {

    }

    /**
     * Activates the effect associated with the square.
     * This method should be overridden in subclasses to provide specific effects.
     */
    public boolean activateSquareEffect() {
        // No default behavior. Override for specific effects.
        return true;
    }

    public SquareType getSquareType() {
        return this.sType;
    }

    public Color getColor() {
        return this.squareColor;
    }
}
