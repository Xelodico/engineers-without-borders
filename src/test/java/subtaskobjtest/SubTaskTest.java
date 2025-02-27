package subtaskobjtest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import BoardGame.ResourceType;
import BoardGame.SubTask;

class SubTaskTest {

	private SubTask subtask;
	
	@BeforeEach
	void setUp() {
		subtask = new SubTask("Research Funding Opportunities", 10, 25, ResourceType.INFLUENCE);
	}
	
	@Test
	void testDiscount() {
		System.out.println("Test");
		subtask.discountSubTask();
		assertEquals(5, subtask.getCompletionScore(), "completionScore should equal 5");
		assertEquals(12, subtask.getResourceCost(), "resourceCost should equal 12");
	}

	
}
