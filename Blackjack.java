import java.util.*;
public class Blackjack
{
    public static void main(String[] args) 
    {
        Scanner sc=new Scanner(System.in);
        Random random=new Random();
        List<String> deck=initializeDeck();
        while(true)
        {
            System.out.println("WELCOME TO BLACKJACK");
            List<String> playerHand=new ArrayList<String>();
            List<String> dealerHand=new ArrayList<String>();
            shuffleDeck(deck, random);
            dealInitial(playerHand, dealerHand, deck);
            displayHands(playerHand, dealerHand);
            playerTurn(playerHand, dealerHand, deck, sc);
            dealerTurn(dealerHand, deck, dealerHand);
            determineWinner(playerHand, dealerHand);
            System.out.println("Do you want to play again? (Y/N)");
            String play=sc.nextLine();
            System.out.println("--------------------------------------------------------------------------------------");
            if(deck.size()<=5)
                deck=initializeDeck();
            if(!play.equalsIgnoreCase("Y"))
                break;
        }
    }
    public static void shuffleDeck(List<String> deck, Random random)
    {
        String temp;
        int rand;
        for(int i=0;i<deck.size()-1;i++)
        {
            rand=random.nextInt(deck.size()-1);
            temp=deck.get(rand);
            deck.set(rand,deck.get(i));
            deck.set(i,temp);
        }
    }
    public static List<String> initializeDeck()
    {
        List<String> deck=new ArrayList<String>();
        String[] suit={"\u2660","\u2661","\u2662","\u2663"};
        String[] value={"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        for(int i=1;i<=4;i++)
        {
            for(String s:suit)
            {
                for(String val:value)
                {
                    deck.add(val+s);
                }
            }
        }
        return deck;
    }
    public static void dealInitial(List<String> playerHand, List<String> dealerHand, List<String> deck)
    {
        playerHand.add(deck.remove(deck.size()-1));
        dealerHand.add(deck.remove(deck.size()-1));
        playerHand.add(deck.remove(deck.size()-1));
        dealerHand.add(deck.remove(deck.size()-1));
    }
    public static void displayHands(List<String> playerHand, List<String> dealerHand)
    {
        System.out.println("Your Hand: "+playerHand+" ("+calculateHand(playerHand)+")");
        System.out.println("Dealer's Hand: "+dealerHand.get(0)+"  \u2370");
    }
    public static int calculateHand(List<String> hand)
    {
        int value=0;
        int aces=0;
        for(String card:hand)
        {
            String rank=card.substring(0,card.length()-1);
            if(rank.equals("A"))
            {
                aces++;
                value+=11;
            }
            else if(rank.equals("K")||rank.equals("Q")||rank.equals("J"))
                value+=10;
            else
                value+=Integer.parseInt(rank);
        }
        while(aces>0 && value>21)
        {
            value-=10;
            aces--;
        }
        return value;
    }
    public static void playerTurn(List<String> playerHand, List<String> dealerHand, List<String> deck, Scanner sc)
    {
        while(true)
        {
            int value=calculateHand(playerHand);
            if(value>21)
                break;
            if(value==21)
                break;
            System.out.println("Do you want to hit or stand? (H/S)");
            String choice=sc.nextLine();
            if(choice.equalsIgnoreCase("h"))
            {
                hit(deck, playerHand, dealerHand);
            }
            else if(playerHand.size()>=5)
                break;
            else if(choice.equalsIgnoreCase("s"))
            {
                stand(deck, playerHand, dealerHand);
                break;
            }
            else
                System.out.println("Invalid choice");
        }
    }
    public static void hit(List<String> deck, List<String> playerHand, List<String> dealerHand)
    {
        playerHand.add(deck.remove(deck.size()-1));
        System.out.println("Your Hand: "+playerHand+" ("+calculateHand(playerHand)+")");
    }
    public static void stand(List<String> deck, List<String> playerHand, List<String> dealerHand)
    {
        System.out.println("Your Hand: "+playerHand+" ("+calculateHand(playerHand)+")");
    }
    public static void dealerTurn(List<String> hand, List<String> deck, List<String> dealerHand)
    {
        while(calculateHand(hand)<17)
        {
            hand.add(deck.remove(deck.size()-1));
        }
        System.out.println("Dealer Hand: "+dealerHand+" ("+calculateHand(dealerHand)+")");
    }
    public static void determineWinner(List<String> playerHand, List<String> dealerHand)
    {
        int player, dealer;
        player=calculateHand(playerHand);
        dealer=calculateHand(dealerHand);
        if(player>21)
        {
            System.out.println("YOU WENT OVER 21");
            System.out.println("BUST");
        }
        else if(player==21)
            System.out.println("BLACKJACK");
        else if(player>dealer)
        {
            System.out.println("YOU WIN");
        }
        else if(playerHand.size()>=5)
        {
            System.out.println("YOU WIN");
        }
        else if(dealer>21)
        {
            System.out.println("DEALER BUST");
            System.out.println("YOU WIN");
        }
        else if(dealer>player)
        {
            System.out.println("DEALER WINS");
        }
        else
            System.out.println("IT'S A TIE");
    }
}