package BoardGame;

import java.util.ArrayList;
import java.util.List;

public class GameSystem {

    private Player player1 = new Player("Nathan", 0);
    private Player player2 = new Player("Curtis", 9);
    private Player player3 = new Player("Isaac", 90);

    private static List<Player> players;
    
    public GameSystem() {
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
    }

    public static void main(String[] args) {
        new GameSystem();
        new BoardGameUI(players);
    }
}