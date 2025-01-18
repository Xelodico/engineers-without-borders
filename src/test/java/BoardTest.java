import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import BoardGame.Board;
import BoardGame.Direction;

public class BoardTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

    @Test
    void testMovePlayerUp() {
        int playerIndex = 2;
        board.movePlayer(playerIndex, Direction.UP);
        assertEquals(80, board.getPlayerPositions().get(playerIndex), "Player should move up from bottom row");
    }

    @Test 
    void testMovePlayerDown() {
        int playerIndex = 0;
        board.movePlayer(playerIndex, Direction.DOWN);
        assertEquals(10, board.getPlayerPositions().get(playerIndex), "Player should move down from top row");
    }

    @Test 
    void testMovePlayerLeft() {
        int playerIndex = 1;
        board.movePlayer(playerIndex, Direction.LEFT);
        assertEquals(8, board.getPlayerPositions().get(playerIndex), "Player should move left from rightmost column");
    }

    @Test 
    void testMovePlayerRight() {
        int playerIndex = 0;
        board.movePlayer(playerIndex, Direction.RIGHT);
        assertEquals(1, board.getPlayerPositions().get(playerIndex), "Player should move right from leftmost column");
    }

    @Test 
    void testMovePlayerInvalidDirection() {
        int playerIndex = 0;
        assertThrows(IllegalArgumentException.class, () -> board.movePlayer(playerIndex, null), "Player should not move in an invalid direction");
    }

    @Test
    void testMovePlayerUpBoundary() {
        int playerIndex = 0;
        board.movePlayer(playerIndex, Direction.UP);
        assertEquals(0, board.getPlayerPositions().get(playerIndex), "Player should not move up from top row");
    }

    @Test 
    void testMovePlayerDownBoundary() {
        int playerIndex = 2;
        board.movePlayer(playerIndex, Direction.DOWN);
        assertEquals(90, board.getPlayerPositions().get(playerIndex), "Player should not move down from bottom row");
    }

    @Test 
    void testMovePlayerLeftBoundary() {
        int playerIndex = 0;
        board.movePlayer(playerIndex, Direction.LEFT);
        assertEquals(0, board.getPlayerPositions().get(playerIndex), "Player should not move left from leftmost column");
    }

    @Test 
    void testMovePlayerRightBoundary() {
        int playerIndex = 1;
        board.movePlayer(playerIndex, Direction.RIGHT);
        assertEquals(9, board.getPlayerPositions().get(playerIndex), "Player should not move right from rightmostmost column");
    }

    @Test
    void testBoardInitialization() {
        assertEquals(100, board.getsquareArray().size(), "Board should have 100 squares");
        assertEquals(Arrays.asList(0, 9, 90, 99), board.getPlayerPositions(),
            "Players should start at spawn locations (0, 9, 90, 99)");
        assertEquals(4, Collections.frequency(board.getsquareArray(), "Spawn"), "Should have 4 spawn squares");
    }

    @Test
    void testGetPlayerPositions() {
        board.movePlayer(0, Direction.RIGHT);
        List<Integer> positions = board.getPlayerPositions();
        assertEquals(1, positions.get(0), "Player 0 should have moved to position 1");
    }

    @Test
    void testGetsquareArray() {
        List<String> squareArray = board.getsquareArray();
        assertEquals(100, squareArray.size(), "Board should have 100 squares");
    }

    @Test
    void testGetSquareAt() {
        assertEquals("Spawn", board.getSquareAt(0), "Square at position 0 should be Spawn");
    }

    @Test
    void testGetSquareAtInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> board.getSquareAt(-1), "Should not get square at invalid position");
    }

    @Test
    void testSetSquareAt() {
        board.setSquareAt(0, "Pothole");
        assertEquals("Pothole", board.getSquareAt(0), "Square at position 0 should be Pothole");
    }

    @Test
    void testSetSquareAtInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> board.setSquareAt(-1, "Pothole"), "Should not set square at invalid position");
    }

}
