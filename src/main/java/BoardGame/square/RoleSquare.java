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
     * @param p1 The first player in the game.
     * @param p2 The second player in the game.
     * @param p3 The third player in the game.
     * @param p4 The fourth player in the game.
     */
    @Override
    public void activateSquareEffect(Player p1, Player p2, Player p3, Player p4) {
        // Determine which player to assign the role to
        int playerRoleAssignment;  // This variable should have been initialized based on game logic
        switch (playerRoleAssignment) {
        case 1:
            p1.setJobRole(this.playerRole);
            break;
        case 2:
            p2.setJobRole(this.playerRole);
            break;
        case 3:
            p3.setJobRole(this.playerRole);
            break;
        case 4:
            p4.setJobRole(this.playerRole);
            break;
        default:
            // Error: invalid player role assignment, try again
            break;
        }
    }
}

