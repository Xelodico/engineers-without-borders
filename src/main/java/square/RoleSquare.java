package square;
import java.awt.Color;

import BoardGame.*;
import GameSystem.*;

/**
 * Represents a square on the game board that assigns a job role to a player.
 * The job role is pre-defined and assigned to a specific player when the square is activated.
 */
public class RoleSquare extends Square {

    /**
     * The job role assigned to the player when this square is activated.
     */
    private JobRole playerRole;

    /**
     * The type of this square, indicating it is a RoleSquare.
     */
    private final SquareType sType = SquareType.ROLESQUARE;

    /**
     * The color representation of this square.
     */
    private final Color squareColor = Color.ORANGE;

    /**
     * Constructs a RoleSquare with the specified job role.
     *
     * @param role The job role assigned to this square.
     */
    public RoleSquare(JobRole role) {
        super();
        this.playerRole = role;
    }

    /**
     * Activates the effect of the RoleSquare, assigning the job role to the current player.
     *
     * @return {@code true} if the role was successfully assigned to the player,
     *         {@code false} otherwise.
     */
    @Override
    public boolean activateSquareEffect() {
        super.activateSquareEffect();
        //getPrimaryOccupier().addRole(this.playerRole);
        return true;
    }

    /**
     * Returns the type of this square.
     *
     * @return The square type, which is {@code SquareType.ROLESQUARE}.
     */
    public SquareType getSquareType() {
        return this.sType;
    }

    /**
     * Returns the color associated with this square.
     *
     * @return The color of this square, which is {@code Color.ORANGE}.
     */
    public Color getColor() {
        return this.squareColor;
    }
}
