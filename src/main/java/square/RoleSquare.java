package square;

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
    @Override
    public void activateSquareEffect(Player[] players, int playerRoleAssignment) {
        // Determine which player to assign the role to
        //TODO use UI to get player's choice
        players[playerRoleAssignment].setJobRole(this.playerRole);
    }
}

