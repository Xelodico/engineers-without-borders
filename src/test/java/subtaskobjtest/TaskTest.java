package subtaskobjtest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import BoardGame.Objective;
import BoardGame.ResourceType;
import BoardGame.SubTask;
import BoardGame.Task;

/**
 * JUnit test cases for the Task class
 * 
 * @author Isaac Edmonds
 */
class TaskTest {

	private Task task;
	
	@BeforeEach
	void set_up() {
		Objective objective = new Objective("Objective1");
		SubTask st1 = new SubTask("Subtask1", 10, 25, ResourceType.ASPHALT);
		SubTask st2 = new SubTask("Subtask2", 10, 25, ResourceType.ASPHALT);
		SubTask st3 = new SubTask("Subtask3", 10, 25, ResourceType.ASPHALT);
		SubTask[] stArray = {st1, st2, st3};
		task = new Task("Task1", objective, stArray, 15, 40, ResourceType.ASPHALT);
	}
	
	@Test
	void testCompleteStep() {
		assertTrue(task.completeStep(), "completeStep() should return true");
		assertEquals(1, task.getCurrentStepNumber(), "currentStepNumber should equal 1");
	}
	
	@Test
	void testCompleteStepAlreadyCompleted() {
		task.setCurrentStepNumber(3);
		assertFalse(task.completeStep(), "completeStep() should return false");
		assertEquals(3, task.getCurrentStepNumber(), "currentStepNumber should have remained 3");
	}
	
	@Test
	void testIsCompleted() {
		task.setCurrentStepNumber(3);
		assertTrue(task.isCompleted(), "isCompleted() should return true");
	}
	
	@Test
	void testGetCurrentSubTask() {
		task.setCurrentStepNumber(3);
		SubTask returnedTask = task.getCurrentSubTask();
		assertTrue(returnedTask.getTitle().equals(""), "title should be empty, indicating an empty returned SubTask");
	}
	
	@Test
	void testSetCurrentSubTask() {
		SubTask newSubTask = new SubTask("Replaced SubTask", 0, 0, ResourceType.ASPHALT);
		task.setCurrentSubTask(newSubTask);
		assertEquals(newSubTask, task.getCurrentSubTask(), "Returned subtask should be the same one that replaced the old subtask");
	}
	
	@Test
	void testAlreadyCompletedSetCurrentSubTask() {
		task.setCurrentStepNumber(3);
		SubTask newSubTask = new SubTask("Replaced SubTask", 0, 0, ResourceType.ASPHALT);
		task.setCurrentSubTask(newSubTask);
		assertNotEquals(newSubTask, task.getCurrentSubTask(), "Returned subtask should not be the same one that attempted to replace the previous one");
	}
	
	@Test
	void testSetCurrentTaskNumberInvalid() {
		task.setCurrentStepNumber(-1);
		assertEquals(0, task.getCurrentStepNumber(), "currentStepNumber should not have changed");
	}
	

}
