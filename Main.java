import java.util.Scanner;

/**
 * The Main class is the entry point for the Blackjack game. 
 * Handles the flow of the game, including betting, rounds and user inputs
 *
 * @author Tristan Cravens 
 */
public class Main {

	/**
	 * Starts the Blackjack game, takes user input for name and initial money, runs the game loop.
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		//Welcomes the player, gets their name
		System.out.print("Welcome to Blackjack. Please, enter your name: ");
		String playerName = scan.nextLine();
		
		//Gets the amount of money the user wants to start out with
		double playerMoney = getMoneyFromInput(scan, "Wecome, " + playerName + ". Enter the amount of money you want to start with: ");
				
		//Create a new game instance with the starting money
		Game game = new Game(playerMoney);
		
		//Game loop
		while(true) {
			//Check if player has run out of money
			if(game.getPlayerMoney() <= 0) {
				System.out.println("You're out of money!");
				
				//Ask if player would like to buy back in
				System.out.print("Do you want to buy back in? Enter yes or no: ");
				if(scan.nextLine().equalsIgnoreCase("yes")) {
					double buyIn = getMoneyFromInput(scan, "Enter the amount of money to buy back in: ");
					//Start a new game with the buy in
					game = new Game(buyIn);
				}
				else {
					//Exit the game if player does not want to buy back in
					break;
				}
			}
			
			//Display the players current money 
			System.out.println("\n" + playerName + ", you have $" + String.format("%.2f", game.getPlayerMoney()));
			
			//Get the player's bet for the current round
			double bet;
			while (true) {
				bet = getMoneyFromInput(scan, "Enter your bet for this hand: ");
				
				//Ensure the player has the money to cover their bet
				if(bet > game.getPlayerMoney()) {
					System.out.println("You don't have enough money to bet that amount. Try again.");
				}
				else {
					break;
				}
			}
			
			//Start a new round of the game
			game.newRound();
			//Player's turn
			game.playerTurn(scan);
			//Dealer's turn
			game.dealerTurn();
			//Determine the winner, update the player's money
			game.determineWinner(bet);
			
			//Display the player's updated money
			System.out.println(playerName + ", you now have $" + String.format("%.2f", game.getPlayerMoney()));
			
			//Ask if the player wants to quit the game
			System.out.print("Do you want to quit? Please enter yes or no: ");
			String quitInput = scan.nextLine().trim().toLowerCase();
			while(!quitInput.equals("yes") && !quitInput.equals("no")) {
				System.out.println("Invalid input. Please enter yes or no: ");
				quitInput = scan.nextLine().trim().toLowerCase();
			}
			if(quitInput.equals("yes")) {
				break;
			}
			
		}
		
		//Thank the player for playing
		System.out.println("\nThank you for playing, " + playerName + "!");
		//Close the scanner
		scan.close();
	}
	
	/**
	 * Prompts the user for a valid monetary input and returns the amount. 
	 * Ensures the input is a positive number, strips the leading "$" if that is included
	 * in the user input. 
	 * 
	 * @param scan Scanner object to read user input
	 * @param prompt the message asking for the amount of money
	 * @return valid positive amount of money entered by the user
	 */
	public static double getMoneyFromInput(Scanner scan, String prompt) {
		double money = 0.0;
		while (true) {
			System.out.print(prompt);
			String input = scan.nextLine().trim();
			try {
				//Strip the leading "S" if present
				if(input.startsWith("$")) {
					input = input.substring(1).trim();
				}
				
				//Parse the input as a double
				money = Double.parseDouble(input);
				
				//Ensure the input is ppsitive
				if(money <= 0) {
					throw new NumberFormatException();
				}
				//Exit loop if a valid input is provided
				break;
			}
			catch(NumberFormatException e) {
				//Display an errpr message for any invalid inputs
				System.out.println("Invalid input. Please enter a valid positive amount (ex. $50 or 50.50).");
			}
		}
		return money;
	}
}
