import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import BoardGame.Board;
import BoardGame.Direction;
import BoardGame.Objective;
import BoardGame.Player;
import BoardGame.ResourceType;
import BoardGame.SubTask;
import BoardGame.Task;
import GameSystem.GameSystem;

/**
 * Test suite for the {@link GameSystem} class.
 * <p>
 * This class verifies that the game system initialises correctly and that
 * its state can be manipulated as expected (e.g., setting turn order, round
 * number,
 * turn number, and retrieving players).
 * 
 * @author Peter Robinson
 *         </p>
 */
public class GameSystemTest {

    /**
     * Performs a one-time setup for the game system before all test cases run.
     * <p>
     * This method ensures that the {@link GameSystem} is properly initialised
     * before executing any tests. Since some tests depend on an initialised game
     * state, this setup prevents redundant initialisation calls and maintains
     * consistency across all test cases.
     * </p>
     */
    @BeforeAll
    public static void setupAll() {
        GameSystem.initialise();
    }

    /**
     * Resets and reinitialises the game system before each test case.
     * <p>
     * This method ensures that each test starts with a clean and predictable
     * game state by first resetting any existing game data and then
     * reinitialising the {@link GameSystem}.
     * </p>
     */
    @BeforeEach
    public void setUp() {
        // Reset any existing game state to avoid interference between tests.
        GameSystem.reset();

        // Reinitialise the game system to ensure a fresh start for each test.
        GameSystem.initialise();
    }

    /**
     * Tests the initialisation of the game system.
     * <p>
     * Verifies that after initialisation, the turn order is not null, the round
     * number and turn number
     * are set to 0, and that the objectives and tasks lists are populated.
     * </p>
     */
    @Test
    public void testInitialisation() {
        assertNotNull(GameSystem.getTurnOrder(), "Turn order should not be null after initialisation.");
        assertEquals(0, GameSystem.getRoundNumber(), "Round number should be 0 after initialisation.");
        assertEquals(0, GameSystem.getTurnNumber(), "Turn number should be 0 after initialisation.");
        assertFalse(GameSystem.getObjectives().isEmpty(), "Objectives list should be populated after initialisation.");
        assertFalse(GameSystem.getTasks().isEmpty(), "Tasks list should be populated after initialisation.");
    }

    /**
     * Tests setting and retrieving the turn order.
     * <p>
     * Verifies that the turn order can be correctly set using an array of
     * {@link Player} objects
     * and that the same order is returned by the game system.
     * </p>
     */
    @Test
    public void testSetTurnOrder() {
        Player[] players = { new Player("Alice", 0), new Player("Bob", 0) };
        GameSystem.setTurnOrder(players);
        assertArrayEquals(players, GameSystem.getTurnOrder(), "Turn order should match the array provided.");
    }

    /**
     * Tests setting the round number.
     * <p>
     * Verifies that the round number can be updated to a specific value.
     * </p>
     */
    @Test
    public void testSetRoundNumber() {
        GameSystem.setRoundNumber(5);
        assertEquals(5, GameSystem.getRoundNumber(), "Round number should be set to 5.");
    }

    /**
     * Tests setting the turn number.
     * <p>
     * Verifies that the turn number can be updated to a specific value.
     * </p>
     */
    @Test
    public void testSetTurnNumber() {
        GameSystem.setTurnNumber(2);
        assertEquals(2, GameSystem.getTurnNumber(), "Turn number should be set to 2.");
    }

    /**
     * Tests retrieving players based on turn information.
     * <p>
     * Verifies that when the turn order is set and the turn number is specified,
     * the game system returns the correct current player and allows access to a
     * player by index.
     * </p>
     */
    @Test
    public void testGetPlayerAt() {
        Player[] players = { new Player("Alice", 0), new Player("Bob", 0) };
        GameSystem.setTurnOrder(players);
        GameSystem.setTurnNumber(1);
        assertEquals("Bob", GameSystem.getPlayerAt().getName(), "Current player should be Bob.");
        assertEquals("Alice", GameSystem.getPlayerAt(0).getName(), "Player at index 0 should be Alice.");
    }

    /**
     * Tests that attempting to access a player at an invalid index throws an
     * ArrayIndexOutOfBoundsException.
     */
    @Test
    public void testGetPlayerAtInvalidIndex() {
        Player[] players = { new Player("Alice", 0), new Player("Bob", 0) };
        GameSystem.setTurnOrder(players);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            GameSystem.getPlayerAt(5);
        }, "Accessing an invalid index should throw an exception.");
    }

    /**
     * Tests that a valid move to the RIGHT results in the player travelling one
     * square.
     * <p>
     * In this test, the player's starting coordinate is set to 5, which is not on
     * the right boundary.
     * With one move left, moving RIGHT should increment the player's moves
     * travelled by one.
     * </p>
     */
    @Test
    public void testMovePlayer() {
        // Test a valid move to the RIGHT from coordinate 5.
        Player player = new Player("TestPlayer", 0);
        player.setCoord(5); // 5 is not on the right boundary
        Player[] players = { player };
        GameSystem.setTurnOrder(players);
        player.setMovesLeft(1);
        GameSystem.movePlayer(Direction.RIGHT);
        assertEquals(1, player.getMovesTravelled(), "Player should have travelled one square.");
    }

    /**
     * Tests that the player does not move when there are no moves left.
     * <p>
     * In this test, the player's moves left is set to 0. Attempting to move RIGHT
     * should result in
     * no change in the player's travelled moves.
     * </p>
     */
    @Test
    public void testMovePlayerNoMoves() {
        Player player = new Player("NoMovePlayer", 0);
        Player[] players = { player };
        GameSystem.setTurnOrder(players);
        player.setMovesLeft(0);
        GameSystem.movePlayer(Direction.RIGHT);
        assertEquals(0, player.getMovesTravelled(), "Player should not move if moves left is 0.");
    }

    /**
     * Tests that passing a null direction to the movePlayer method throws an
     * IllegalArgumentException.
     * <p>
     * The exception message should indicate that the direction cannot be null.
     * </p>
     */
    @Test
    public void testMovePlayerNullDirection() {
        Player player = new Player("TestPlayer", 0);
        player.setCoord(5);
        player.setMovesLeft(1);
        Player[] players = { player };
        GameSystem.setTurnOrder(players);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            GameSystem.movePlayer(null);
        });
        assertEquals("Direction cannot be null", exception.getMessage(), "Should throw exception for null direction.");
    }

    /**
     * Tests that a player at the left boundary (coordinate 0) does not move left.
     * <p>
     * Since coordinate 0 is on the left edge (0 % 9 == 0), a move to the LEFT
     * should not change the player's position or moves travelled.
     * </p>
     */
    @Test
    public void testMovePlayerBoundaryLeft() {
        // Left boundary: coordinate 0 is on the left edge (0 % 9 == 0)
        Player player = new Player("BoundaryPlayer", 0);
        player.setCoord(0);
        player.setMovesLeft(1);
        Player[] players = { player };
        GameSystem.setTurnOrder(players);
        GameSystem.movePlayer(Direction.LEFT);
        assertEquals(0, player.getMovesTravelled(), "Player should not move when at left boundary.");
        assertEquals(0, player.getCoord(), "Player coordinate should remain unchanged.");
    }

    /**
     * Tests that a player at the top boundary does not move upward.
     * <p>
     * A coordinate less than 9 (e.g., 3) represents the top row. A move UP should
     * leave the player's coordinate unchanged.
     * </p>
     */
    @Test
    public void testMovePlayerBoundaryUp() {
        // Top boundary: any coordinate less than 9 (e.g., 3)
        Player player = new Player("BoundaryPlayerUp", 0);
        player.setCoord(3);
        player.setMovesLeft(1);
        Player[] players = { player };
        GameSystem.setTurnOrder(players);
        GameSystem.movePlayer(Direction.UP);
        assertEquals(0, player.getMovesTravelled(), "Player should not move when at top boundary.");
        assertEquals(3, player.getCoord(), "Player coordinate should remain unchanged.");
    }

    /**
     * Tests that a player at the right boundary does not move right.
     * <p>
     * A coordinate whose modulo 9 equals 8 (e.g., 8) is on the right edge.
     * Attempting to move RIGHT should result in no change in position or moves
     * travelled.
     * </p>
     */
    @Test
    public void testMovePlayerBoundaryRight() {
        // Right boundary: coordinate mod 9 must equal 8.
        Player player = new Player("BoundaryPlayerRight", 0);
        player.setCoord(8); // 8 % 9 == 8, so on the right edge.
        player.setMovesLeft(1);
        Player[] players = { player };
        GameSystem.setTurnOrder(players);
        GameSystem.movePlayer(Direction.RIGHT);
        assertEquals(0, player.getMovesTravelled(), "Player should not move when at right boundary.");
        assertEquals(8, player.getCoord(), "Player coordinate should remain unchanged.");
    }

    /**
     * Tests that a player at the bottom boundary does not move down.
     * <p>
     * For a board with a side length of 9, bottom row indices range from (9-1)*9 =
     * 72 to 80.
     * A move DOWN at such a coordinate should leave the player's position
     * unchanged.
     * </p>
     */
    @Test
    public void testMovePlayerBoundaryDown() {
        // Bottom boundary: for boardSideLength 9, bottom row indices are 72 to 80.
        Player player = new Player("BoundaryPlayerDown", 0);
        player.setCoord(75); // 75 is in the bottom row.
        player.setMovesLeft(1);
        Player[] players = { player };
        GameSystem.setTurnOrder(players);
        GameSystem.movePlayer(Direction.DOWN);
        assertEquals(0, player.getMovesTravelled(), "Player should not move when at bottom boundary.");
        assertEquals(75, player.getCoord(), "Player coordinate should remain unchanged.");
    }

    /**
     * Tests that a valid move upward results in the expected new coordinate.
     * <p>
     * For a valid UP move, the player's starting coordinate must be at least 9.
     * With a starting coordinate of 20 and a board side length of 9, the expected
     * new coordinate is 20 - 9 = 11.
     * Also, the moves travelled should increase by one.
     * </p>
     */
    @Test
    public void testMovePlayerUpValid() {
        // Test a valid move UP. Starting coordinate must be >= 9.
        Player player = new Player("ValidUp", 0);
        player.setCoord(20); // 20 is valid for moving UP (20 >= 9)
        player.setMovesLeft(1);
        Player[] players = { player };
        GameSystem.setTurnOrder(players);
        GameSystem.movePlayer(Direction.UP);
        // Expected coordinate is 20 - 9 = 11.
        assertEquals(11, player.getCoord(), "Player should move up from 20 to 11.");
        assertEquals(1, player.getMovesTravelled(), "Player should have travelled one square.");
    }

    /**
     * Tests that a valid move DOWN results in the expected new coordinate.
     * <p>
     * The player's starting coordinate is 50, which is valid for a move DOWN.
     * With one move left and a board with a side length of 9, the expected new
     * coordinate is 50 + 9 = 59.
     * The player's moves travelled should increase by one.
     * </p>
     */
    @Test
    public void testMovePlayerDownValid() {
        // Test a valid move DOWN. Starting coordinate must be less than (9-1)*9 = 72.
        Player player = new Player("ValidDown", 0);
        player.setCoord(50); // 50 is valid for moving down.
        player.setMovesLeft(1);
        Player[] players = { player };
        GameSystem.setTurnOrder(players);
        GameSystem.movePlayer(Direction.DOWN);
        // Expected coordinate is 50 + 9 = 59.
        assertEquals(59, player.getCoord(), "Player should move down from 50 to 59.");
        assertEquals(1, player.getMovesTravelled(), "Player should have travelled one square.");
    }

    /**
     * Tests the nextTurn method for proper update of turn and round numbers.
     * <p>
     * Initially, both the turn number and round number should be 0.
     * After one call to nextTurn, the turn number should increment to 1 while the
     * round number remains 0.
     * After a second call, the turn number should reset to 0 and the round number
     * should increment to 1.
     * </p>
     */
    @Test
    public void testNextTurn() {
        Player[] players = { new Player("Alice", 0), new Player("Bob", 0) };
        GameSystem.setTurnOrder(players);
        assertEquals(0, GameSystem.getTurnNumber(), "Initial turn number should be 0.");
        assertEquals(0, GameSystem.getRoundNumber(), "Initial round number should be 0.");

        // First call: turn advances to 1; round remains 0.
        GameSystem.nextTurn();
        assertEquals(1, GameSystem.getTurnNumber(), "After one turn, turn number should be 1.");
        assertEquals(0, GameSystem.getRoundNumber(), "After one turn, round number should remain 0.");

        // Second call: turn resets to 0; round increments to 1.
        GameSystem.nextTurn();
        assertEquals(0, GameSystem.getTurnNumber(), "After cycling, turn number should reset to 0.");
        assertEquals(1, GameSystem.getRoundNumber(), "After cycling, round number should increment to 1.");
    }

    /**
     * Tests the nextTurn method in a multi-player scenario.
     * <p>
     * With three players ("Alice", "Bob", "Charlie"), the test verifies that
     * nextTurn correctly cycles through the players.
     * After cycling through all players, the turn resets and the round number
     * increments.
     * </p>
     */
    @Test
    public void testNextTurnMultiplePlayers() {
        Player[] players = {
                new Player("Alice", 0),
                new Player("Bob", 0),
                new Player("Charlie", 0)
        };
        GameSystem.setTurnOrder(players);
        assertEquals("Alice", GameSystem.getPlayerAt().getName(), "Initial player should be Alice.");
        GameSystem.nextTurn(); // turn becomes 1.
        assertEquals("Bob", GameSystem.getPlayerAt().getName(), "Second player should be Bob.");
        GameSystem.nextTurn(); // turn becomes 2.
        assertEquals("Charlie", GameSystem.getPlayerAt().getName(), "Third player should be Charlie.");
        GameSystem.nextTurn(); // cycles back to 0, round increments.
        assertEquals("Alice", GameSystem.getPlayerAt().getName(), "After cycling, player should be Alice.");
        assertEquals(1, GameSystem.getRoundNumber(), "Round number should increment after full cycle.");
    }

    /**
     * Tests that the startGame method properly increments the round number.
     * <p>
     * The test captures the initial round number, calls startGame, and then
     * verifies that the round number has increased by one.
     * </p>
     */
    @Test
    public void testStartGame() {
        int initialRound = GameSystem.getRoundNumber();
        GameSystem.startGame();
        assertEquals(initialRound + 1, GameSystem.getRoundNumber(), "startGame() should increment the round number.");
    }

    /**
     * Tests the UI interaction methods to ensure they execute without throwing any
     * exceptions.
     * <p>
     * This includes testing methods such as showPopup, hidePopup, toggleJournal,
     * toggleShop, toggleTutorial and toggleTransfer.
     * </p>
     */
    @Test
    public void testUIInteractions() {
        assertDoesNotThrow(() -> {
            Task task = new Task();
            GameSystem.showPopup("Test Title", "Test Description", "Yes", "No", e -> {
            }, e -> {
            });
            GameSystem.hidePopup();
            GameSystem.toggleJournal();
            GameSystem.toggleShop();
            GameSystem.toggleTutorial();
            GameSystem.toggleTransfer(task);
        }, "UI interaction methods should execute without throwing exceptions.");
    }

    /**
     * Tests that the resetForTests method properly resets the game state.
     * <p>
     * The test sets the round and turn numbers to non-zero values, then resets the
     * game for tests and reinitialises the game.
     * It verifies that both the round number and turn number are reset to 0.
     * </p>
     */
    @Test
    public void testResetForTests() {
        GameSystem.setRoundNumber(10);
        GameSystem.setTurnNumber(3);
        GameSystem.reset();
        GameSystem.initialise();
        assertEquals(0, GameSystem.getRoundNumber(), "After reset, round number should be 0.");
        assertEquals(0, GameSystem.getTurnNumber(), "After reset, turn number should be 0.");
    }

    /**
     * Checks that the board is not null and its side length is 12.
     */
    @Test
    public void testGetBoard() {
        Board board = GameSystem.getBoard();
        assertNotNull(board, "getBoard() should not return null.");
        assertEquals(9, board.boardSideLength, "Board side length should be 9.");
    }

    /**
     * Tests the progress of a task by a player.
     * <p>
     * A dummy task is created with a single subtask that requires a specific
     * resource (asphalt)
     * and awards a completion score when finished. A player is initialized with
     * enough asphalt and a zero score.
     * After calling {@code GameSystem.progressTask(task)}, the test verifies that:
     * <ul>
     * <li>The player's asphalt resource decreases by the subtask's cost (30).</li>
     * <li>The player's score increases by the subtask's score (20) plus the task's
     * completion score (50),
     * since the task becomes complete.</li>
     * <li>The task is marked as completed.</li>
     * </ul>
     * </p>
     */
    @Test
    public void testProgressTask() {
        // Set up a dummy Task with one SubTask.
        Task task = new Task();
        task.setResourceType(ResourceType.ASPHALT);
        task.setCompletionScore(50);

        // Create a subtask with a resource cost and a completion score.
        SubTask subtask = new SubTask();
        subtask.setResourceCost(30);
        subtask.setCompletionScore(20);
        task.addStep(subtask);

        // Set up a player with sufficient asphalt resource.
        Player player = new Player("TaskTester", 0);
        player.setResource(100, ResourceType.ASPHALT); // Ensure resource check passes.
        player.setScore(0);
        Player[] players = { player };
        GameSystem.setTurnOrder(players);

        int initialResource = player.getResource(ResourceType.ASPHALT);
        int initialScore = player.getScore();

        // Execute the task progression.
        GameSystem.progressTask(task);

        // Verify that the player's asphalt resource decreases by the subtask's cost.
        assertEquals(initialResource - 30, player.getResource(ResourceType.ASPHALT),
                "Player's resource should decrease by the subtask's resource cost.");
        // Verify that the player's score increases by the subtask and task completion
        // scores.
        assertEquals(initialScore + 20 + 50, player.getScore(),
                "Player's score should increase by the subtask and task completion scores.");
        // Verify that the task is marked as complete.
        assertTrue(task.isCompleted(), "Task should be marked as completed after progress.");
    }

    /**
     * Tests that purchasing a resource is successful when the player has sufficient
     * quantity.
     * <p>
     * The player is initialized with at least 150 units of VOLUNTEERS and 500
     * money.
     * When {@code GameSystem.purchaseResource(ResourceType.VOLUNTEERS)} is called,
     * the purchase should succeed:
     * <ul>
     * <li>The player's money is reduced by 100 (the cost of the purchase).</li>
     * <li>The player's VOLUNTEERS resource increases by 25.</li>
     * </ul>
     * </p>
     */
    @Test
    public void testPurchaseResourceSuccess() {
        Player player = new Player("Purchaser", 0);
        // Ensure the player has sufficient VOLUNTEERS resource.
        player.setResource(150, ResourceType.VOLUNTEERS);
        // Provide the player with some money.
        player.changeMoney(500);
        int initialMoney = player.getMoney();
        int initialResource = player.getResource(ResourceType.VOLUNTEERS);
        Player[] players = { player };
        GameSystem.setTurnOrder(players);

        boolean success = GameSystem.purchaseResource(ResourceType.VOLUNTEERS);
        assertTrue(success, "Purchase should succeed with sufficient resource.");
        assertEquals(initialMoney - 100, player.getMoney(),
                "Player's money should be reduced by RESOURCE_PRICE (100).");
        assertEquals(initialResource + 25, player.getResource(ResourceType.VOLUNTEERS),
                "Player's resource should increase by 25.");
    }

    /**
     * Tests that purchasing a resource fails when the player has insufficient
     * resource quantity.
     * <p>
     * The player's resource for KNOWLEDGE is set below the required threshold (less
     * than 100).
     * When {@code GameSystem.purchaseResource(ResourceType.KNOWLEDGE)} is called,
     * the purchase should fail,
     * and neither the player's money nor the KNOWLEDGE resource should change.
     * </p>
     */
    @Test
    public void testPurchaseResourceFailure() {
        Player player = new Player("Purchaser", 0);
        // Set insufficient KNOWLEDGE resource (below RESOURCE_PRICE, 100).
        player.setResource(50, ResourceType.KNOWLEDGE);
        player.changeMoney(500);
        int initialMoney = player.getMoney();
        int initialResource = player.getResource(ResourceType.KNOWLEDGE);
        Player[] players = { player };
        GameSystem.setTurnOrder(players);

        boolean success = GameSystem.purchaseResource(ResourceType.KNOWLEDGE);
        assertFalse(success, "Purchase should fail with insufficient resource.");
        // Verify that neither money nor resource quantity has changed.
        assertEquals(initialMoney, player.getMoney(), "Player's money should remain unchanged.");
        assertEquals(initialResource, player.getResource(ResourceType.KNOWLEDGE),
                "Player's resource should remain unchanged.");
    }

    /**
     * Tests the calculation of the implementation progress percentage.
     * <p>
     * The method {@code GameSystem.getImplementationPercent()} should return a
     * value between 0.0 and 1.0,
     * representing the ratio of the current total awarded score to the maximum
     * possible score.
     * This test simulates:
     * <ul>
     * <li>No progress (score 0, percent 0.0).</li>
     * <li>Full progress (score equals maxScore, percent 1.0).</li>
     * <li>Half progress (score is half of maxScore, percent 0.5).</li>
     * </ul>
     * </p>
     */
    @Test
    public void testGetImplementationPercent() {
        // Simulate no progress.
        GameSystem.setCurrentTotalAwardedScore(0);
        double percent0 = GameSystem.getImplementationPercent();
        assertEquals(0.0, percent0, "Implementation percent should be 0.0 when no progress is made.");

        // Simulate full progress.
        GameSystem.setMaxScore(1000);
        GameSystem.setCurrentTotalAwardedScore(1000);
        double percentFull = GameSystem.getImplementationPercent();
        assertEquals(1.0, percentFull, "Implementation percent should be 1.0 when progress equals maxScore.");

        // Simulate half progress.
        GameSystem.setCurrentTotalAwardedScore(500);
        double percentHalf = GameSystem.getImplementationPercent();
        assertEquals(0.5, percentHalf, "Implementation percent should be 0.5 when progress is half of maxScore.");
    }

    /**
     * Tests that {@code GameSystem.checkWinCondition()} correctly returns true when
     * all objectives are marked as complete.
     * 
     * This test retrieves the list of objectives and simulates their completion
     * by setting all tasks to their final step. It then verifies that the win
     * condition method properly recognizes this
     * state.
     */
    @Test
    public void testCheckWinConditionAllComplete() throws Exception {
        // Retrieve the list of objectives from the game system
        ArrayList<Objective> completeList = GameSystem.getObjectives();

        // Iterate through each objective and mark all tasks as complete
        for (Objective o : completeList) {
            for (Task t : o.getTasks()) {
                t.setCurrentStepNumber(t.getSteps().length); // Set task to final step
            }
        }

        // Verify that the win condition returns true when all objectives are complete
        boolean result = GameSystem.checkWinCondition();
        assertTrue(result, "checkWinCondition should return true when all objectives are complete.");
    }

    /**
     * Tests that {@code GameSystem.checkWinCondition()} correctly returns false
     * when at least one objective is incomplete.
     * 
     * This test retrieves a list of objectives and ensures that only half of them
     * are marked as complete, while the other half remains incomplete.
     * It then verifies that the win condition method correctly fails in this case.
     */
    @Test
    public void testCheckWinConditionIncomplete() throws Exception {
        // Retrieve the list of objectives from the game system
        ArrayList<Objective> mixedList = GameSystem.getObjectives();

        // Mark only half of the objectives as complete
        for (int i = 0; i < mixedList.size() / 2; i++) {
            Objective o = mixedList.get(i);
            for (Task t : o.getTasks()) {
                t.setCurrentStepNumber(t.getSteps().length); // Set task to final step
            }
        }

        // Verify that the win condition returns false when at least one objective is
        // incomplete
        boolean result = GameSystem.checkWinCondition();
        assertFalse(result, "checkWinCondition should return false when at least one objective is incomplete.");
    }

}
