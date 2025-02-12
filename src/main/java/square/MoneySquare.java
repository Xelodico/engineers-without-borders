Package Square;

import java.util.random.*;
import GameSystem.*;

public class MoneySquare extends Square {
    private int money; // Stores the money value for this square.

    /**
     * Default constructor that assigns a random amount of money between 800 and 1200.
     */
    public MoneySquare() {
        super(); // Calls the parent class constructor.
        Random rand = new Random();
        this.money = rand.nextInt(400) + 800; // Generates a random value between 800 and 1200.
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

    /**
     * Activates the effect of landing on a MoneySquare.
     * Calls the parent class method and returns true.
     *
     * @return {@code true} indicating the effect has been activated.
     */
    @Override
    public boolean activateSquareEffect() {
        super.activateSquareEffect(); // Calls the superclass method.
        getPlayerAt().changeMoney(this.money);
        return true;
    }
}