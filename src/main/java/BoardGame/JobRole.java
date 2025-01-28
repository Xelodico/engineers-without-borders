package BoardGame;

import java.awt.Color;
import java.util.ArrayList;

/**
 * @author Isaac Edmonds
 * 
 * An object representing a responsibility or job that a player can have. An assigned JobRole has corresponding subtasks.
 * Be careful when handling the responsiblePlayer values as they default to null whenever the roll has not been assigned
 * to a player yet. 
 */
public class JobRole {

	// ATTRIBUTES //
	private String title;
	private Player responsiblePlayer;
	private ArrayList<Task> subtasks;
	private Color uiColor;
	private int availableStage;
	
	// METHODS //
	/**
	 * @param title - the title of the role to be created
	 * @param responsiblePlayer - the player this JobRole will be assigned to
	 * @param subtasks - an ArrayList of Task that contains the subtasks of this JobRole
	 * @param uiColor - The colour desired for this JobRole's and its subtasks' squares on the board UI
	 * @param availableStage - The stage of the game this role is available on
	 * 
	 * A parameter constructor that assigns values for every attribute.
	 */
	public JobRole(String title, Player responsiblePlayer, ArrayList<Task> subtasks, Color uiColor, int availableStage) {
		this.title = title;
		this.responsiblePlayer = responsiblePlayer;
		this.subtasks = subtasks;
		this.uiColor = uiColor;
		this.availableStage = availableStage;
	}
	
	/**
	 * @param title - the title of the role to be created
	 * @param subtasks - an ArrayList of Task that contains the subtasks of this JobRole
	 * @param uiColor - The colour desired for this JobRole's and its subtasks' squares on the board UI
	 * @param availableStage - The stage of the game this role is available on
	 * 
	 * A constructor that assigns values for every attribute excluding responsiblePlayer.
	 * This attribute is instead defaulted to null.
	 */
	public JobRole(String title, ArrayList<Task> subtasks, Color uiColor, int availableStage) {
		this.title = title;
		this.responsiblePlayer = null;
		this.subtasks = subtasks;
		this.uiColor = uiColor;
		this.availableStage = availableStage;
	}
	
	/**
	 * @param title - the title of the role to be created
	 * @param responsiblePlayer - the player this JobRole will be assigned to
	 * @param uiColor - The colour desired for this JobRole's and its subtasks' squares on the board UI
	 * @param availableStage - The stage of the game this role is available on
	 * 
	 * A constructor that assigns values for every attribute excluding subtasks.
	 * This attribute is instead defaulted to an empty ArrayList.
	 */
	public JobRole(String title, Player responsiblePlayer, Color uiColor, int availableStage) {
		this.title = title;
		this.responsiblePlayer = responsiblePlayer;
		this.subtasks = new ArrayList<Task>();
		this.uiColor = uiColor;
		this.availableStage = availableStage;
	}
	
	/**
	 * A default constructor that takes in no parameters and simply creates an 'empty' JobRole object.
	 */
	public JobRole() {
		this.title = "";
		this.responsiblePlayer = null;
		this.subtasks = new ArrayList<Task>();
		this.uiColor = Color.WHITE;
		this.availableStage = 0;
	}
	
	/**
	 * @return True if responsiblePlayer is not null, False if it is
	 * 
	 * A function that checks if responsiblePlayer isn't null.
	 */
	public boolean isAssigned() {
		if (responsiblePlayer != null) {
			return true;
		} else {
			return false;
		}
	}
	
	
	// GETTERS //

	/**
	 * @return the title of the JobRole
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the player responsible for this JobRole. Can be null if no player assigned
	 */
	public Player getResponsiblePlayer() {
		return responsiblePlayer;
	}

	/**
	 * @return the colour representing this JobRole and its subtasks' squares on the board UI
	 */
	public Color getUiColor() {
		return uiColor;
	}

	/**
	 * @return the stage of the game this JobRole is available on
	 */
	public int getAvailableStage() {
		return availableStage;
	}
	
	// SETTERS //

	/**
	 * @param title - the new title of the JobRole
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param responsiblePlayer - the player to be responsible for this JobRole
	 */
	public void setResponsiblePlayer(Player responsiblePlayer) {
		this.responsiblePlayer = responsiblePlayer;
	}
	/**
	 * @param subtask - the subtask to add to this JobRole
	 */
	public void addSubtask(Task subtask) {
		subtasks.add(subtask);
	}
	/**
	 * @param subtask - the subtask to remove from this JobRole
	 */
	public void removeSubtask(Task subtask) {
		subtasks.remove(subtask);
	}

	/**
	 * @param uiColor - the new colour to represent this JobRole and its subtasks
	 */
	public void setUiColor(Color uiColor) {
		this.uiColor = uiColor;
	}

	/**
	 * @param availableStage - the new stage this JobRole is available on
	 */
	public void setAvailableStage(int availableStage) {
		this.availableStage = availableStage;
	}
	
	
}
