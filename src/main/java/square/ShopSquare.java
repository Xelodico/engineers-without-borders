package square;

import java.awt.Color;

/**
 * Represents a shop square on the game board where players can purchase items.
 *
 * @author Kal Worthington
 * @author Antons Bogdanovs
 */
public class ShopSquare extends Square {

    /**
     * The type of this square, indicating it is a ShopSquare.
     */
    private final SquareType sType = SquareType.SHOPSQUARE;

    /**
     * The color representation of this square.
     */
    private final Color squareColor = Color.GREEN;

    /**
     * Returns the type of this square.
     * 
     * @return The square type, which is {@code SquareType.SHOPSQUARE}.
     */
    public SquareType getSquareType() {
        return this.sType;
    }

    /**
     * Returns the color associated with this square.
     * 
     * @return The color of this square, which is {@code Color.GREEN}.
     */
    public Color getColor() {
        return this.squareColor;
    }
}
