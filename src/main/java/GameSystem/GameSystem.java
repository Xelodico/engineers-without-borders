package GameSystem;

import BoardGame.*;

import java.util.Scanner;


public class GameSystem {
    // Attributes
	private Scanner input;
	
    private Board gameBoard;
    private BoardGameUI gameBoardUI;
    private Player[] turnOrder;
    private int roundNumber;
    private int turnNumber;

    // Methods

    public GameSystem() {
    	input = new Scanner(System.in);
    	
    	gameBoard = new Board(this.turnOrder);
    	gameBoardUI = new BoardGameUI(this.gameBoard, this.turnOrder);
    	gameBoardUI.setVisible(true);
    	
        startGame();
    }

    private void startGame() {
    	enterPlayers();
    	
    	System.out.println("Press Enter to start new game");
    	input.nextLine();
    	
    	initialise();
    }
    
    private void enterPlayers() {
    	System.out.print("Please enter the number of players: ");
    	int playerNum = input.nextInt();
    	input.nextLine();
    	this.turnOrder = new Player[playerNum]; 
    	
    	for(int i = 0; i < this.turnOrder.length; i++) {
    		System.out.print("Please enter the name of Player " + (i+1) + ": ");
    		String name = input.nextLine();
    		this.turnOrder[i].setName(name);
    	}
    }
    
    private void initialise(){
    	
    }

    public void setTurnOrder(Player[] players) {
    	this.turnOrder = players;
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

    public Player getPlayerAt(int index){
        return this.turnOrder[index];
    }
    
    public Player getPlayerAt() {
    	return this.turnOrder[turnNumber];
    }

    public int getRoundNumber(){
        return this.roundNumber;
    }

    public int getTurnNumber(){
        return this.turnNumber;
    }

    public void movePlayer() {
    	
    }
    
    private void nextTurn(){
    	// Change turn & round number
    	if(turnNumber >= turnOrder.length) {
    		turnNumber = 0;
    		roundNumber++;
    	} else {
    		turnNumber++;
    	}
    }

    private void nextRound(){

    }

    public void updatePlayerDisplay(){

    }

}
