package square;

import java.util.Random;

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
     * Constructs a RoleSquare object and randomly selects a job role for the player.
     * The role is selected from a range of 4 possible roles.
     * If the game is in phase 2 or 3, an additional offset (4) could be added to the random selection.
     */
    RoleSquare() {
        Random random = new Random();
        // Randomly select a job role from a range of 4 roles
        int roleReturned = random.nextInt(4);
        playerRole.setJobRole(roleReturned);
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
        if (playerRoleAssignment > players.length) {
            //invalid option
        } else {
            players[playerRoleAssignment].setJobRole(this.playerRole);
        }
        break;
    }
}

