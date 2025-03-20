import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import BoardGame.*;
import square.*;

public class BoardTest {
    private Player player;
    private List<Player> players;
    private ArrayList<Objective> objectives = new ArrayList<Objective>();
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private Board board;

    @BeforeEach
    public void setUp() {
        objectives.add(new Objective("Test Objective"));
        objectives.add(new Objective("Test Objective"));
        objectives.add(new Objective("Test Objective"));
        objectives.add(new Objective("Test Objective"));
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        players = new ArrayList<Player>();

        player = new Player();
        player.setMovesLeft(1);
        players.add(player);
        players.add(player);
        board = new Board(tasks);
    }

    @Test
    void testBoardInitialization() {
        assertNotNull(board.getSquareArray(), "Board squares should be initialized");
        assertEquals(81, board.getSquareArray().size(), "Board should have 144 squares");
    }

    @Test
    void testSetSquareAt() {
        Square newSquare = new TaskSquare(new Task());
        board.setSquareAt(0, newSquare);
        assertEquals(newSquare, board.getSquareAt(0), "Square at index 0 should be updated");
    }

    @Test
    void testSetSquareAtInvalidIndex() {
        Square newSquare = new TaskSquare(new Task());
        assertThrows(IllegalArgumentException.class, () -> board.setSquareAt(-1, newSquare), "Should throw exception for invalid index");
        assertThrows(IllegalArgumentException.class, () -> board.setSquareAt(144, newSquare), "Should throw exception for invalid index");
    }

    @Test
    void testGenerateNewSquares() {
        assertEquals(2, board.getSquareArray().stream().filter(square -> square instanceof MoneySquare).count(), "Board should be initialized with 2 money squares");
        board.generateNewSquares(2, new MoneySquare());
        assertEquals(4, board.getSquareArray().stream().filter(square -> square instanceof MoneySquare).count(), "Should generate 2 more money squares totalling 4");
    }

    @Test
    void testGenerateNewSquaresInvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> board.generateNewSquares(-1, new TaskSquare(new Task())), "Should throw exception for negative amount");
        assertThrows(IllegalArgumentException.class, () -> board.generateNewSquares(145, new TaskSquare(new Task())), "Should throw exception for amount exceeding board size");
    }
    
    @Test
    void testMovePlayerInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> player.setCoord(-1), "Player should not move to an invalid position");
    }
}
