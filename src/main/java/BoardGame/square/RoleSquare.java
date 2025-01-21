package square;

import java.util.Random;

public class RoleSquare extends Square{
	private JobRole playerRole;

	RoleSquare() {
		Random random = new Random();
		// if in phase 2, add 4 to random number(assuming jobroles are numbered)
		int roleReturned = random.nextInt(4);
		playerRole.setJobRole(roleReturned);
	}

	@Override
	public void activateSquareEffect(Player p1, Player p2, Player p3, Player p4) {
		// numbers 0-3 correspond with different job roles
		// ask what player this role will be assigned to
		int playerRoleAssignment;
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
			//error; try again
		}
	}
}

}

