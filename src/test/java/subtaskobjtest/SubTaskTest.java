package subtaskobjtest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import BoardGame.ResourceType;
import BoardGame.SubTask;

/**
 * JUnit test cases for the SubTask class
 * 
 * @author Isaac Edmonds
 */
class SubTaskTest {

	private SubTask subtask;
	
	@BeforeEach
	void setUp() {
		subtask = new SubTask("Research Funding Opportunities", 10, 25, ResourceType.INFLUENCE);
	}
	
	@Test
	void testDiscount() {
		subtask.discountSubTask();
		assertEquals(5, subtask.getCompletionScore(), "completionScore should equal 5");
		assertEquals(12, subtask.getResourceCost(), "resourceCost should equal 12");
	}

	@Test
	void testDiscountAlreadyApplied() {
		subtask.setDiscounted(true);
		subtask.discountSubTask();
		assertTrue(subtask.isDiscounted(), "isDiscounted should be true");
		assertEquals(10, subtask.getCompletionScore(), "completionScore should equal 10");
		assertEquals(25, subtask.getResourceCost(), "resourceCost should equal 25");
	}
}
