import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import BoardGame.*;

public class PlayerTest {

    private Player player = new Player();

    @Test
    void testSetMovesLeft() {
        player.setMovesLeft(1);
        assertEquals(1, player.getMovesLeft(), "Player should have 1 move left");
    }

    @Test
    void testSetMovesLeftInvalid() {
        assertThrows(IllegalArgumentException.class, () -> player.setMovesLeft(-1), "Player should not have negative moves left");
    }

    @Test
    void testSetCoord() {
        player.setCoord(1);
        assertEquals(1, player.getCoord(), "Player should be at position 1");
    }

    @Test
    void testSetCoordInvalid() {
        assertThrows(IllegalArgumentException.class, () -> player.setCoord(-1), "Player should not be at an invalid position");
    }

    @Test
    void testSetCoordInvalidMax() {
        assertThrows(IllegalArgumentException.class, () -> player.setCoord(101), "Player should not be at an invalid position");
    }

    @Test
    void testSetCoordInvalidMin() {
        assertThrows(IllegalArgumentException.class, () -> player.setCoord(-1), "Player should not be at an invalid position");
    }

    @Test
    void testSetResources() {
        player.setResources(1);
        assertEquals(1, player.getResources(), "Player should have 1 resource");
    }

    @Test
    void testSetResourcesNegative() {
        assertThrows(IllegalArgumentException.class, () -> player.setResources(-1), "Player should not have negative resources");
    }

    @Test
    void testSetName() {
        player.setName("Player");
        assertEquals("Player", player.getName(), "Player should have the name 'Player'");
    }
    
    @Test
    void testGetName() {
        player.setName("Player");
        assertEquals("Player", player.getName(), "Player should have the name 'Player'");
    }

    @Test
    void testSetNameInvalid() {
        assertThrows(IllegalArgumentException.class, () -> player.setName(null), "Player should have a valid name");
    }

    @Test
    void testGetMovesLeft() {
        player.setMovesLeft(1);
        assertEquals(1, player.getMovesLeft(), "Player should have 1 move left");
    }

    @Test
    void testGetCoord() {
        player.setCoord(1);
        assertEquals(1, player.getCoord(), "Player should be at position 1");
    }

    @Test
    void testGetResources() {
        player.setResources(1);
        assertEquals(1, player.getResources(), "Player should have 1 resource");
    }
}
