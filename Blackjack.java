import java.util.*;

import javafx.scene.control.Label;

public class Blackjack extends BlackjackGUI
{
    static final int maxHand=21;
    public static void main(String[] args) 
    {
        BlackjackGUI.main(null);
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        List<String> deck = initializeDeck();
        System.out.println("WELCOME TO BLACKJACK");
        int balance = getInitialBalance(sc);
        
        while (true) 
        {
            Label bal=new Label("BALANCE"+balance);
            int wager = getWager(sc, balance);
            balance -= wager;

            List<String> playerHand = new ArrayList<>();
            List<String> dealerHand = new ArrayList<>();
            shuffleDeck(deck, random);
            dealInitial(playerHand, dealerHand, deck);
            displayHands(playerHand, dealerHand);

            String surrenderChoice = playerTurn(playerHand, dealerHand, deck, sc);
            dealerTurn(dealerHand, deck);

            balance += determineWinner(playerHand, dealerHand, wager, surrenderChoice);
            System.out.println("BALANCE LEFT: $" + balance);
            
            System.out.println("---------------------------------------------------------");
            
            if (deck.size() <= 5) {
                deck = initializeDeck();
            }

            if (!playAgain(sc)) {
                break;
            }
        }
    }

    public static void shuffleDeck(List<String> deck, Random random) {
        Collections.shuffle(deck, random);
    }

    public static List<String> initializeDeck() {
        List<String> deck = new ArrayList<>();
        String[] suits = {"\u2660", "\u2661", "\u2662", "\u2663"};
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String suit : suits) {
            for (String value : values) {
                deck.add(value + suit);
            }
        }
        return deck;
    }

    public static void dealInitial(List<String> playerHand, List<String> dealerHand, List<String> deck) {
        for (int i = 0; i < 2; i++) {
            playerHand.add(deck.remove(deck.size() - 1));
            dealerHand.add(deck.remove(deck.size() - 1));
        }
    }

    public static void displayHands(List<String> playerHand, List<String> dealerHand) {
        System.out.println("Your Hand: " + playerHand + " (" + calculateHand(playerHand) + ")");
        System.out.println("Dealer's Hand: " + dealerHand.get(0) + "  \u2370");
    }

    public static int calculateHand(List<String> hand) {
        int value = 0;
        int aces = 0;

        for (String card : hand) {
            String rank = card.substring(0, card.length() - 1);
            if (rank.equals("A")) {
                aces++;
                value += 11;
            } else if (rank.equals("K") || rank.equals("Q") || rank.equals("J")) {
                value += 10;
            } else {
                value += Integer.parseInt(rank);
            }
        }

        while (aces > 0 && value > 21) {
            value -= 10;
            aces--;
        }
        return value;
    }

    public static String playerTurn(List<String> playerHand, List<String> dealerHand, List<String> deck, Scanner sc) {
        int turns = 1;
        String choice = "";
        while (true) {
            int value = calculateHand(playerHand);

            if (value >= 21) {
                break;
            }

            if (turns == 1) {
                System.out.println("Do you want to hit, stand, or surrender? (h/s/r)");
            } else {
                System.out.println("Do you want to hit or stand? (h/s)");
            }

            choice = sc.next();
            
            if (choice.equalsIgnoreCase("h")) {
                hit(deck, playerHand);
            } else if (choice.equalsIgnoreCase("s")) {
                stand(playerHand);
                break;
            } else if (choice.equalsIgnoreCase("r") && turns == 1) {
                break;
            } else {
                System.out.println("Invalid choice");
            }
            
            turns++;
        }
        return choice;
    }

    public static void hit(List<String> deck, List<String> playerHand) {
        playerHand.add(deck.remove(deck.size() - 1));
        System.out.println("Your Hand: " + playerHand + " (" + calculateHand(playerHand) + ")");
    }

    public static void stand(List<String> playerHand) {
        System.out.println("Your Hand: " + playerHand + " (" + calculateHand(playerHand) + ")");
    }

    public static void dealerTurn(List<String> dealerHand, List<String> deck) {
        while (calculateHand(dealerHand) < 17) {
            dealerHand.add(deck.remove(deck.size() - 1));
        }
        System.out.println("Dealer Hand: " + dealerHand + " (" + calculateHand(dealerHand) + ")");
    }

    public static int determineWinner(List<String> playerHand, List<String> dealerHand, int bet, String choice) {
        if (choice.equalsIgnoreCase("r")) {
            System.out.println("YOU SURRENDERED");
            return (int) (bet * 0.5);
        }

        int playerValue = calculateHand(playerHand);
        int dealerValue = calculateHand(dealerHand);

        if (playerValue > maxHand) {
            System.out.println("YOU WENT OVER 21");
            System.out.println("BUST");
            return 0;
        } else if (playerValue == maxHand && dealerValue != maxHand) {
            System.out.println("BLACKJACK");
            return (int) (bet * 2.5);
        } else if (playerValue > dealerValue || dealerValue > maxHand) {
            System.out.println("YOU WIN");
            return bet * 2;
        } else if (dealerValue > playerValue) {
            System.out.println("DEALER WINS");
            return 0;
        } else {
            System.out.println("IT'S A TIE");
            return bet;
        }
    }

    public static int getInitialBalance(Scanner sc) {
        System.out.print("Enter initial buy-in amount: $");
        return sc.nextInt();
    }

    public static int getWager(Scanner sc, int balance) {
        while (true) {
            System.out.print("Enter your wager for this round: $");
            int wager = sc.nextInt();
            if (wager <= balance) {
                return wager;
            } else {
                System.out.println("INSUFFICIENT BALANCE");
            }
        }
    }

    public static boolean playAgain(Scanner sc) {
        System.out.print("Do you want to play again? (Y/N): ");
        String play = sc.next();
        return play.equalsIgnoreCase("Y");
    }
}
