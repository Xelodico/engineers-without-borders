package BoardGame;

public enum ResourceType {
	ASPHALT,
	VOLUNTEERS,
	INFLUENCE,
	KNOWLEDGE;

	public String toString() {
		if (this == ASPHALT) {
			return "Cold Asphalt";
		} else if (this == VOLUNTEERS) {
			return "Volunteers";
		} else if (this == INFLUENCE) {
			return "Influence";
		} else if (this == KNOWLEDGE) {
			return "Knowledge";
		} else return "UNKNOWN RESOURCE";
	}
}
