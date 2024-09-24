import java.util.*;

/**
 * The Game class represents a game of Blackjack with functionalities
 * for a player and dealer. It includes methods to handle the player's
 * and dealer's turns, determine the winner and manage betting money.
 * 
 * @author Tristan Cravens 
 */
public class Game {
	//instance variables
	private Deck deck;
	private List<Card> playerHand;
	private List<Card> dealerHand;
	private boolean playerBusted;
	private boolean dealerBusted;
	private double playerMoney;
	
	/**
	 * Constructs a new Game with an initial amount of money for the player.
	 * 
	 * @param initialMoney the initial amount of money the player starts with.
	 */
	public Game(double initialMoney) {
		this.deck = new Deck();
		this.playerHand = new ArrayList<>();
		this.dealerHand = new ArrayList<>();
		this.playerBusted = false;
		this.dealerBusted = false;
		this.playerMoney = initialMoney;
		
		//shuffle deck
		this.deck.shuffle();
		
		//deal initial two cards to player and dealer
		playerHand.add(deck.drawCard());
		playerHand.add(deck.drawCard());
		
		dealerHand.add(deck.drawCard());
		dealerHand.add(deck.drawCard());
	}
	
	/**
	 * Starts a new round by clearing hands, resetting the busted values, 
	 * and dealing two new cards to both player and dealer.
	 */
	public void newRound() {
		playerHand.clear();
		dealerHand.clear();
		playerBusted = false;
		dealerBusted = false;
		
		playerHand.add(deck.drawCard());
		playerHand.add(deck.drawCard());
		
		dealerHand.add(deck.drawCard());
		dealerHand.add(deck.drawCard());
		
		if(calculateHandValue(playerHand) == 21) {
			System.out.println("Player gets Blackjack!");
		}
	}
	
	/**
	 * Handles the player's turn, allowing the player to Hit or Stand. 
	 * If the player busts (goes over 21), the turn ends.
	 * 
	 * @param scan the Scanner object to read user input.
	 */
	public void playerTurn(Scanner scan) {
		if (calculateHandValue(playerHand) == 21) {
			System.out.println("You have Blackjack!");
			return;
		}
		while(true) {
			displayHand(playerHand, "Player");
			
			System.out.print("Do you want to Hit or Stand? Type H for hit, S for Stand. ");
			String choice = scan.nextLine().toUpperCase();
			
			if(choice.equals("H")) {
				playerHand.add(deck.drawCard());
				if(calculateHandValue(playerHand) > 21) {
					System.out.println("Player busts! You had a total value of " + calculateHandValue(playerHand) + "!");
					playerBusted = true;
					break;
				}
			}
			else if(choice.equals("S")) {
				break;
			}
			else {
				System.out.println("Invalid choice; please enter H or S. ");
			}
		}
	}
	
	/**
	 * Handles the dealer's turn. Dealer keeps drawing until the total hand value
	 * is at least 17. If the dealer busts (goes over 21), the dealer turn ends.
	 */
	public void dealerTurn() {
		if(!playerBusted) {
			while(calculateHandValue(dealerHand) < 17) {
				dealerHand.add(deck.drawCard());
			}
			
			displayHand(dealerHand, "Dealer");
			
			if(calculateHandValue(dealerHand) > 21) {
				System.out.println("Dealer busts!");
				dealerBusted = true;
			}
		}
	}
	
	/**
	 * Determines the winner of the current round based on the hands of the player and dealer.
	 * Updates the player's money based on the outcome of the round.
	 * 
	 * @param bet the amount of money bet by the player for the current round.
	 * @return true (if the player wins), false (otherwise (lose/tie)).
	 */
	public boolean determineWinner(double bet) {
		if(playerBusted) {
			System.out.println("Dealer wins!");
			playerMoney -= bet;
			return false;
		}
		else if(dealerBusted) {
			System.out.println("Player wins!");
			playerMoney += bet;
			return true;
		}
		else {
			int playerTotal = calculateHandValue(playerHand);
			int dealerTotal = calculateHandValue(dealerHand);
			
			if(playerTotal > dealerTotal) {
				System.out.println("Player wins!\n");
				playerMoney += bet;
				return true;
			}
			else if(playerTotal < dealerTotal) {
				System.out.println("Dealer wins!\n");
				playerMoney -= bet;
				return false;
			}
			else {
				System.out.println("It's a tie!\n");
				return false;
			}
		}
	}
	
	/**
	 * Getter method for the current amount of money the player has.
	 * 
	 * @return the player's current money.
	 */
	public double getPlayerMoney() {
		return playerMoney;
	}
	
	/**
	 * Calculates the total value of a given hand. Aces are counted as 11, unless doing so would
	 * cause the hand to exceed 21, in which case they are counted as 1.
	 * 
	 * @param hand the list of cards representing a hand
	 * @return the total value of the hand
	 */
	public static int calculateHandValue(List<Card> hand) {
		int value = 0;
		int aceCount = 0;
		
		for(Card card : hand) {
			value += card.getCardValue();
			if(card.value.equals("A")) {
				aceCount++;
			}
		}
		
		while (value > 21 && aceCount > 0) {
			value -=10;
			aceCount--;
		}
		
		return value;
	}
	
	/**
	 * Displays the current cards in the specified hand along with the total value of the hand.
	 * 
	 * @param hand the list of cards representing the hand.
	 * @param owner the owner of the hand.
	 */
	public static void displayHand(List<Card> hand, String owner) {
		System.out.println(owner + "'s hand:");
		for (Card card : hand) {
			System.out.println(card);
		}
		System.out.println("Total value: " + calculateHandValue(hand));
		System.out.println();
	}

	/**
	 * The Card class represents an individual card with a suit and value.
	 */
	public static class Card{
		//instance variables
		String suit;
		String value;
		
		/**
		 * Constructs a card with the specified suit and value.
		 * 
		 * @param suit the suit of the card (Hearts, Diamonds, Clubs, Spades).
		 * @param value the value of the card (2-10, J, Q, K, A).
		 */
		public Card(String suit, String value) {
			this.suit = suit;
			this.value = value;
		}
		
		/**
		 * Returns the point value of the card. Face cards (K, Q, J) are worth 10, 
		 * aces are worth 11 (unless it causes a bust, in which case it's worth 1),
		 * and other cards according to their numeric value.
		 * 
		 * @return point value of the card.
		 */
		public int getCardValue() {
			if(value.equals("A")) {
				return 11;
			}
			else if(value.equals("K") || value.equals("Q") || value.equals("J")) {
				return 10;
			}
			else {
				return Integer.parseInt(value);
			}
		}
		
		/**
		 * Returns the string representation of the card, including its value and suit.
		 * 
		 * @return a string describing the card.
		 */
		public String toString() {
			return value + " of " + suit;
		}
	}
	
	/**
	 * The Deck class represents a deck of 52 cards.
	 * It includes functionality for shuffling and drawing cards.
	 */
	public static class Deck{
		//instance variables
		private List<Card> deck;
		private Random rand;
		
		/**
		 * Constructs a new deck of 52 cards, one for each combination of suit and value.
		 */
		public Deck() {
			deck = new ArrayList<>();
			String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
			String [] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
			for(String suit : suits) {
				for(String value : values) {
					deck.add(new Card(suit, value));
				}
			}
			rand = new Random();
		}
		
		/**
		 * Shuffles the deck using a random number generator.
		 */
		public void shuffle() {
			Collections.shuffle(deck, rand);
		}
		
		/**
		 * Draws a card from the deck and removes it from the list of available cards.
		 * 
		 * @return the drawn card.
		 */
		public Card drawCard() {
			return deck.remove(deck.size() - 1);
		}
	}
}
