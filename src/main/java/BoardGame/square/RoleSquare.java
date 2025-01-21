package square;

public class RoleSquare extends Square{
	private JobRole playerRole;
	RoleSquare(JobRole role) {
		this.playerRole = role;
	}
	
	@Override
	public void activateSquareEffect(Player p) {
		boolean playerChoice = true;
		//pop-up code
		//print "do you want this role?"
		
		if (playerChoice) {
			p.setJobRole(this.playerRole);
		}
	}
	
}
