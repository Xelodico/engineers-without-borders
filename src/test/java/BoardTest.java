import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import BoardGame.*;

public class BoardTest {
    private BoardGameUI boardGameUI;
    private Player player;
    private List<Player> players;
    private Board board;

    @BeforeEach
    public void setUp() {
        players = new ArrayList<Player>();

        player = new Player();
        player.setMovesLeft(1);
        players.add(player);
        players.add(player);
        board = new Board(players);

        boardGameUI = new BoardGameUI(board, players);
        boardGameUI.setVisible(false); 
    }

    @Test
    void testMovePlayerUp() {
        player.setCoord(10);
        boardGameUI.movePlayer(player, Direction.UP);
        assertEquals(0, player.getCoord(), "Player should move up from bottom row");
    }

    @Test 
    void testMovePlayerDown() {
        player.setCoord(0);
        boardGameUI.movePlayer(player, Direction.DOWN);
        assertEquals(10, player.getCoord(), "Player should move down from top row");
    }

    @Test
    void testMovePlayerLeft() {
        player.setCoord(1);
        boardGameUI.movePlayer(player, Direction.LEFT);
        assertEquals(0, player.getCoord(), "Player should move left from rightmost column");
    }

    @Test
    void testMovePlayerRight() {
        player.setCoord(0);
        boardGameUI.movePlayer(player, Direction.RIGHT);
        assertEquals(1, player.getCoord(), "Player should move right from leftmost column");
    }

    @Test
    void testMovePlayerInvalidDirection() {
        assertThrows(IllegalArgumentException.class, () -> boardGameUI.movePlayer(player, null), "Player should not move in an invalid direction");
    }

    @Test
    void testMovePlayerInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> player.setCoord(-1), "Player should not move from an invalid position");
    }
    
    @Test
    void testMovePlayerUpBoundary() {
        player.setCoord(0);
        boardGameUI.movePlayer(player, Direction.UP);
        assertEquals(0, player.getCoord(), "Player should not move up from top row");
    }

    @Test
    void testMovePlayerDownBoundary() {
        player.setCoord(90);
        boardGameUI.movePlayer(player, Direction.DOWN);
        assertEquals(90, player.getCoord(), "Player should not move down from bottom row");
    }

    @Test
    void testMovePlayerLeftBoundary() {
        player.setCoord(0);
        boardGameUI.movePlayer(player, Direction.LEFT);
        assertEquals(0, player.getCoord(), "Player should not move left from leftmost column");
    }

    @Test
    void testMovePlayerRightBoundary() {
        player.setCoord(9);
        boardGameUI.movePlayer(player, Direction.RIGHT);
        assertEquals(9, player.getCoord(), "Player should not move right from rightmost column");
    }

}
