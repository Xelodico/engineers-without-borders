import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import GameSystem.GameSystem;
import BoardGame.Player;
import BoardGame.Direction;

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
     * Sets up the testing environment by resetting and initialising the game state.
     * <p>
     * This method is executed before each test case to ensure that the
     * {@link GameSystem}
     * is in a known state.
     * </p>
     */
    @BeforeEach
    public void setUp() {
        // For testing purposes, ensure the game state is reset.
        GameSystem.resetForTests();
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
        player.setCoord(5); // 5 is not on the right boundary (5 % 12 != 11)
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
     * Since coordinate 0 is on the left edge (0 % 12 == 0), a move to the LEFT
     * should not change the player's position or moves travelled.
     * </p>
     */
    @Test
    public void testMovePlayerBoundaryLeft() {
        // Left boundary: coordinate 0 is on the left edge (0 % 12 == 0)
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
     * A coordinate less than 12 (e.g., 3) represents the top row. A move UP should
     * leave the player's coordinate unchanged.
     * </p>
     */
    @Test
    public void testMovePlayerBoundaryUp() {
        // Top boundary: any coordinate less than 12 (e.g., 3)
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
     * A coordinate whose modulo 12 equals 11 (e.g., 11) is on the right edge.
     * Attempting to move RIGHT should result in no change in position or moves
     * travelled.
     * </p>
     */
    @Test
    public void testMovePlayerBoundaryRight() {
        // Right boundary: coordinate mod 12 must equal 11.
        Player player = new Player("BoundaryPlayerRight", 0);
        player.setCoord(11); // 11 % 12 == 11, so on the right edge.
        player.setMovesLeft(1);
        Player[] players = { player };
        GameSystem.setTurnOrder(players);
        GameSystem.movePlayer(Direction.RIGHT);
        assertEquals(0, player.getMovesTravelled(), "Player should not move when at right boundary.");
        assertEquals(11, player.getCoord(), "Player coordinate should remain unchanged.");
    }

    /**
     * Tests that a player at the bottom boundary does not move down.
     * <p>
     * For a board with a side length of 12, bottom row indices range from 132 to
     * 143.
     * A move DOWN at such a coordinate should leave the player's position
     * unchanged.
     * </p>
     */
    @Test
    public void testMovePlayerBoundaryDown() {
        // Bottom boundary: for boardSideLength 12, bottom row indices are 132 to 143.
        Player player = new Player("BoundaryPlayerDown", 0);
        player.setCoord(140); // 140 is in the bottom row.
        player.setMovesLeft(1);
        Player[] players = { player };
        GameSystem.setTurnOrder(players);
        GameSystem.movePlayer(Direction.DOWN);
        assertEquals(0, player.getMovesTravelled(), "Player should not move when at bottom boundary.");
        assertEquals(140, player.getCoord(), "Player coordinate should remain unchanged.");
    }

    /**
     * Tests that a valid move upward results in the expected new coordinate.
     * <p>
     * For a valid UP move, the player's starting coordinate must be at least 12.
     * With a starting coordinate of 20 and a board side length of 12, the expected
     * new coordinate is 20 - 12 = 8.
     * Also, the moves travelled should increase by one.
     * </p>
     */
    @Test
    public void testMovePlayerUpValid() {
        // Test a valid move UP. Starting coordinate must be >= 12.
        Player player = new Player("ValidUp", 0);
        player.setCoord(20); // 20 is valid for moving UP (20 >= 12)
        player.setMovesLeft(1);
        Player[] players = { player };
        GameSystem.setTurnOrder(players);
        GameSystem.movePlayer(Direction.UP);
        // Expected coordinate is 20 - 12 = 8.
        assertEquals(8, player.getCoord(), "Player should move up from 20 to 8.");
        assertEquals(1, player.getMovesTravelled(), "Player should have travelled one square.");
    }

    /**
     * Tests that a valid move DOWN results in the expected new coordinate.
     * <p>
     * The player's starting coordinate is 50, which is valid for a move DOWN.
     * With one move left and a board with a side length of 12, the expected new
     * coordinate is 50 + 12 = 62.
     * The player's moves travelled should increase by one.
     * </p>
     */
    @Test
    public void testMovePlayerDownValid() {
        // Test a valid move DOWN. Starting coordinate must be less than (12-1)*12 =
        // 132.
        Player player = new Player("ValidDown", 0);
        player.setCoord(50); // 50 is valid for moving down.
        player.setMovesLeft(1);
        Player[] players = { player };
        GameSystem.setTurnOrder(players);
        GameSystem.movePlayer(Direction.DOWN);
        // Expected coordinate is 50 + 12 = 62.
        assertEquals(62, player.getCoord(), "Player should move down from 50 to 62.");
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
     * toggleShop, and playTutorial.
     * </p>
     */
    @Test
    public void testUIInteractions() {
        assertDoesNotThrow(() -> {
            GameSystem.showPopup("Test Title", "Test Description", "Yes", "No", e -> {
            }, e -> {
            });
            GameSystem.hidePopup();
            GameSystem.toggleJournal();
            GameSystem.toggleShop();
            GameSystem.playTutorial();
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
        GameSystem.resetForTests();
        GameSystem.initialise();
        assertEquals(0, GameSystem.getRoundNumber(), "After reset, round number should be 0.");
        assertEquals(0, GameSystem.getTurnNumber(), "After reset, turn number should be 0.");
    }

}
