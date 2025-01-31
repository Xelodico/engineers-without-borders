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
        board = new Board();

        boardGameUI = new BoardGameUI(board);
        boardGameUI.setVisible(false); 
    }

    @Test
    void testMovePlayerInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> player.setCoord(-1), "Player should not move from an invalid position");
    }
}
