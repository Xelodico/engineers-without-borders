package BoardGame;

import java.util.ArrayList;
import java.util.List;

public class GameSystem {

    private Player player1 = new Player("Nathan", 0);
    private Player player2 = new Player("Curtis", 9);

    private static List<Player> players;

    private static BoardGameUI game;

    public GameSystem() {
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
    }

    public static void main(String[] args) {
        new GameSystem();
        game = new BoardGameUI(players);
        game.setVisible(true);
    }
}