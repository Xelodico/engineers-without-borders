package BoardGame;

/**
 * The ResourceType enum represents different types of resources in the board
 * game.
 *
 * @author Isaac Edmonds
 * @author Nathan Watkins (Supporting)
 */
public enum ResourceType {
	ASPHALT,
	VOLUNTEERS,
	INFLUENCE,
	KNOWLEDGE;

	/**
	 * Returns a string representation of the resource type.
	 *
	 * @return a string representing the resource type
	 */
	public String toString() {
		if (this == ASPHALT) {
			return "Cold Asphalt";
		} else if (this == VOLUNTEERS) {
			return "Volunteers";
		} else if (this == INFLUENCE) {
			return "Influence";
		} else if (this == KNOWLEDGE) {
			return "Knowledge";
		} else {
			return "UNKNOWN RESOURCE";
		}
	}
}