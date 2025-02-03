package square;
import java.awt.Color;

import BoardGame.*;
import GameSystem.*;

/**
 * The RoleSquare class extends the Square class and represents a square on the game board that assigns
 * a job role to a player. The job role is randomly selected, and the selected role is assigned to a specific player
 * when the square is activated.
 */
public class RoleSquare extends Square {
    
    /**
     * The job role that is assigned to the player when this square is activated.
     */
    private JobRole playerRole;
    private final SquareType sType = SquareType.ROLESQUARE;
    private final Color squareColor = Color.ORANGE;

    /**
    * Constructs a RoleSquare with the specified job role.
    *
    * @param role the job role assigned to this square
    */
    public RoleSquare(JobRole role) {
        super();
        this.playerRole = role;
    }

    /**
     * Activates the effect of the RoleSquare, assigning the randomly selected job role
     * to one of the players. The specific player is determined by the input parameter.
     *
     * @param players array of players in game
     * @param playerRoleAssignment the player to get the role of the square
     */
    public boolean activateSquareEffect() {
        // Determine which player to assign the role to
        //TODO use UI to get player's choice
        //Player[] players = GameSystem.getTurnOrder();
        super.activateSquareEffect();
        getPrimaryOccupier().addRole(this.playerRole);
        return true;
    }

    public SquareType getSquareType() {
        return this.sType;
    }

    public Color getColor() {
        return this.squareColor;
    }
}

