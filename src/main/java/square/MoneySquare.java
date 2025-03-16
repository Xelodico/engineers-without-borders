package square;

import java.util.Random;
import GameSystem.*;
import java.awt.Color;
import java.awt.event.ActionListener;

/**
 * The MoneySquare class represents a square on the game board that provides a
 * monetary benefit to the player.
 * The square gives a random amount of money to the player when they land on it.
 *
 * @author Kal Worthington
 * @author Antons Bogdanovs
 */
public class MoneySquare extends Square {
    private int money; // Stores the money value for this square.

    private final Color squareColor = Color.YELLOW;

    private final SquareType sType = SquareType.MONEYSQUARE;

    /**
     * Default constructor that assigns a random amount of money between 800 and
     * 1200.
     */
    public MoneySquare() {
        super(); // Calls the parent class constructor.

        // Creating a random number between 0 and 4.
        int index = new Random().nextInt(5);
        switch (index) {
            case 0:
                this.money = 15;
                break;
            case 1:
                this.money = 20;
                break;
            case 2:
                this.money = 20;
                break;
            case 3:
                this.money = 25;
                break;
            case 4:
                this.money = 30;
                break;
        }
    }

    /**
     * Constructor that initializes the MoneySquare with a specific amount of money.
     *
     * @param money The predefined money value for this square.
     */
    public MoneySquare(int money) {
        super(); // Calls the parent class constructor.
        this.money = money;
    }

    ActionListener okSingleButton = e -> GameSystem.hidePopup();

    /**
     * Activates the effect of landing on a MoneySquare.
     * Calls the parent class method and returns true.
     *
     * @return {@code true} indicating the effect has been activated.
     */
    @Override
    public boolean activateSquareEffect() {
        super.activateSquareEffect(); // Calls the superclass method.
        GameSystem.getPlayerAt().changeMoney(this.money);
        GameSystem.showPopup("Extra Funds!", "You have been given " + money + " Rand!", "Ok", null, okSingleButton,
                null);
        GameSystem.replaceMoneySquare();
        return true;
    }

    /**
     * Returns the color associated with this square.
     * 
     * @return The color of this square, which is {@code Color.YELLOW}.
     */
    public Color getColor() {
        return this.squareColor;
    }

    /**
     * Returns the type of this square.
     * 
     * @return The type of this square, which is {@code SquareType.MONEYSQUARE}.
     */
    public SquareType getSquareType() {
        return this.sType;
    }
}