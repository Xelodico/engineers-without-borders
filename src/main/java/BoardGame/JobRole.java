package BoardGame;

import java.awt.Color;
import java.util.ArrayList;

public class JobRole {

	// ATTRIBUTES //
	private String title;
	private Player responsiblePlayer;
	private ArrayList<Task> subtasks;
	private Color uiColor;
	private int availableStage;
	
	// METHODS //
	public JobRole(String title, Player responsiblePlayer, ArrayList<Task> subtasks, Color uiColor, int availableStage) {
		this.title = title;
		this.responsiblePlayer = responsiblePlayer;
		this.subtasks = subtasks;
		this.uiColor = uiColor;
		this.availableStage = availableStage;
	}
	
	public JobRole(String title, ArrayList<Task> subtasks, Color uiColor, int availableStage) {
		this.title = title;
		this.responsiblePlayer = null;
		this.subtasks = subtasks;
		this.uiColor = uiColor;
		this.availableStage = availableStage;
	}
	
	public JobRole(String title, Player responsiblePlayer, Color uiColor, int availableStage) {
		this.title = title;
		this.responsiblePlayer = responsiblePlayer;
		this.subtasks = new ArrayList<Task>();
		this.uiColor = uiColor;
		this.availableStage = availableStage;
	}
	
	public JobRole() {
		this.title = "";
		this.responsiblePlayer = null;
		this.subtasks = new ArrayList<Task>();
		this.uiColor = Color.WHITE;
		this.availableStage = 0;
	}
	
	public boolean isAssigned() {
		if (responsiblePlayer != null) {
			return true;
		} else {
			return false;
		}
	}
	
	
	// GETTERS //

	public String getTitle() {
		return title;
	}

	public Player getResponsiblePlayer() {
		return responsiblePlayer;
	}

	public Color getUiColor() {
		return uiColor;
	}

	public int getAvailableStage() {
		return availableStage;
	}
	
	// SETTERS //

	public void setTitle(String title) {
		this.title = title;
	}

	public void setResponsiblePlayer(Player responsiblePlayer) {
		this.responsiblePlayer = responsiblePlayer;
	}

	public void setUiColor(Color uiColor) {
		this.uiColor = uiColor;
	}

	public void setAvailableStage(int availableStage) {
		this.availableStage = availableStage;
	}
	
	
}
