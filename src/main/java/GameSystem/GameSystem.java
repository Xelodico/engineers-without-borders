package GameSystem;

public class GameSystem {
    // Attributes
    private Board gameBoard;
    private BoardGameUI gameBoardUI;
    private Player[] turnOrder;
    private int roundNumber;
    private int turnNumber;

    // Methods

    public GameSystem() {
        initialise();
    }

    private void initialise(){

    }

    public void setTurnOrder(Player[] players) {

    }

    public void setRoundNumber(int number) {
        this.roundNumber = number;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public Player[] getTurnOrder(){
        return this.turnOrder;
    }

    public Player getPlayerAt(int index = turnNumber){
        return this.turnOrder[index];
    }

    public int getRoundNumber(){
        return this.roundNumber;
    }

    public int getTurnNumber(){
        return this.turnNumber;
    }

    private void endTurn(){

    }

    private void endRound(){

    }

    public void updatePlayerDisplay(){

    }

}
