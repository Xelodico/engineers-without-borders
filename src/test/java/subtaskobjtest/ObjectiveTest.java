package subtaskobjtest;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import BoardGame.Objective;
import BoardGame.Player;
import BoardGame.ResourceType;
import BoardGame.SubTask;
import BoardGame.Task;

/**
 * JUnit test cases for the Objective Class
 * 
 * @author Isaac Edmonds
 */
class ObjectiveTest {
	
	private Task t1;
	private Task t2;
	private Task t3;
	private Objective objective;
	
	private Player player;

	@BeforeEach
	void set_up() {
		objective = new Objective("Objective1", null, null, Color.RED);
		t1 = new Task("Task1", objective, new SubTask[3], 25, 15, ResourceType.ASPHALT);
		t2 = new Task("Task2", objective, new SubTask[3], 25, 15, ResourceType.ASPHALT);
		t3 = new Task("Task3", objective, new SubTask[3], 25, 15, ResourceType.ASPHALT);
		ArrayList<Task> tList = new ArrayList<Task>();
		tList.add(t1);
		tList.add(t2);
		tList.add(t3);
		objective.setTasks(tList);
		player = new Player();
	}
	
	@Test
	void testIsCompletedSuccess() {
		t1.setCurrentStepNumber(3);
		t2.setCurrentStepNumber(3);
		t3.setCurrentStepNumber(3);
		assertTrue(objective.isCompleted(), "isCompleted() should return true");
	}
	
	@Test
	void testIsCompletedFailure() {
		t1.setCurrentStepNumber(3);
		t2.setCurrentStepNumber(0);
		t3.setCurrentStepNumber(3);
		assertFalse(objective.isCompleted(), "isCompleted() should return false");
	}

	@Test
	void testAllTaskOwnedByTrue() {
		t1.setOwnedBy(player);
		t2.setOwnedBy(player);
		t3.setOwnedBy(player);
		assertTrue(objective.allTasksOwnedBy(player), "allTasksOwnedBy() should return true");
	}
	
	@Test
	void testAllTaskOwnedByFalse() {
		t1.setOwnedBy(player);
		t2.setOwnedBy(null);
		t3.setOwnedBy(player);
		assertFalse(objective.allTasksOwnedBy(player), "allTasksOwnedBy() should return false");
	}
}
