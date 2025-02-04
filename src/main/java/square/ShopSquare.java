package square;

import java.awt.Color;

public class ShopSquare extends Square {
    private final SquareType sType = SquareType.SHOPSQUARE;
    private final Color squareColor = Color.GREEN;

    public SquareType getSquareType() {
        return this.sType;
    }

    public Color getColor() {
        return this.squareColor;
    }
} 