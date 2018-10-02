
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Player {

    private ArrayList<Card> hand = new ArrayList<Card>();
    private ArrayList<String> fourOfAKindSets = new ArrayList<String>(); //each item is a value from "2" to "14"
    private String type; //computer or user

    //constructor
    public Player(String type) {
        if (!(type.equals("computer") || type.equals("user"))) {
            throw new IllegalArgumentException();
        } //restrict domain on type
        this.type = type;
    }

    //get hand
    public ArrayList<Card> getHand() {
        return hand;
    }

    //get type
    public String getType() {
        return type;
    }

    public void addToHand(Card c) {
        hand.add(c);
    }

    public void removeFromHand(int i) {
        if (hand.size() >= i) {
            hand.remove(i);
        }
    }

    public ArrayList<Card> getCards(ArrayList<Card> hand, String query) {
        ArrayList<Card> foundCards = new ArrayList<>();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getValue().equals(query)) {
                foundCards.add(hand.get(i));
            }
        }
        return foundCards;
    }

    //draw a new card
    public void drawCard(ArrayList<Card> deck, ArrayList<String> smartComputerMemory) {
        if (deck.size() > 0) { //do nothing if the deck has no cards
            //check for 4OfAKind
            String valueToCheck = deck.get(0).getValue();
            addToHand(deck.get(0));
            deck.remove(0);
            checkFourOfAKind(valueToCheck,deck,smartComputerMemory);
        }
    }

    //get a player's four-of-a-kind sets
    public ArrayList<String> getFourOfAKindSets() {
        return fourOfAKindSets;
    }

    //add a set to a payer's four-of-a-kind set
    public void addFourOfAKindSets(String value) {
        if (!fourOfAKindSets.contains(value)) { //it doesn't make sense to have more than on four-of-a-kind sets for the same value
            fourOfAKindSets.add(value);
        }
    }

    // check if hand has 4 of a kind
    public boolean checkFourOfAKind(String value, ArrayList<Card> deck, ArrayList<String> smartComputerMemory) {
        int ctr = 0;
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getValue().equals(value)) {
                ctr++;
            }
        }
        if (ctr >= 4) {
            ArrayList<Card> toRemove = new ArrayList<>();
            for (int i = 0; i < getHand().size(); i++) {
                if (getHand().get(i).getValue().equals(value)) {
                    toRemove.add(getHand().get(i));
                }
            }
            for (int i = 0; i < toRemove.size(); i++) {
                for (int j = 0; j < getHand().size(); j++) {
                    if (getHand().get(j).getValue().equals(toRemove.get(i).getValue())) {
                        removeFromHand(j);
                    }
                }
            }
            addFourOfAKindSets(value);
            if (getType().equals("user")) {
                System.out.println("You got a set of " + valueToDisplay(value) + "s!");
                while(smartComputerMemory.contains(value)){
                    smartComputerMemory.remove(value);
                }
            }
            if (getHand().isEmpty()) {

                if (getType().equals("user") && !deck.isEmpty()) {
                    System.out.println("Your hand was empty, you drew a card.");
                }
                drawCard(deck, smartComputerMemory);
            }
            return true;
        }
        return false;
    }

    public static void displayCards(ArrayList<Card> cards) {
        for (Card card : cards) {
            switch (card.getValue()) {
                case "11":
                    System.out.print("J" + card.getSuit() + " ");
                    break;
                case "12":
                    System.out.print("Q" + card.getSuit() + " ");
                    break;
                case "13":
                    System.out.print("K" + card.getSuit() + " ");
                    break;
                case "14":
                    System.out.print("A" + card.getSuit() + " ");
                    break;
                default:
                    System.out.print(card.getValue() + card.getSuit() + " ");
                    break;
            }
        }
        System.out.println("");
    }
    
    public static void printSets(ArrayList<String> vals) {
        for (String v : vals ) {
            switch (v) {
                case "11":
                    System.out.print("J♡ J♢ J♣ J♠ ");
                    break;
                case "12":
                    System.out.print("Q♡ Q♢ Q♣ Q♠ ");
                    break;
                case "13":
                    System.out.print("K♡ K♢ K♣ K♠ ");
                    break;
                case "14":
                    System.out.print("A♡ A♢ A♣ A♠ ");
                    break;
                default:
                    System.out.print(v + "♡ " + v + "♢ " + v + "♣ " + v + "♠ ");
                    break;
            }
        }
    }

    public void guess(Player opponent, String guess, ArrayList<Card> deck, int dif, ArrayList<String> smartComputerMemory) {
        ArrayList<Card> toRemove = new ArrayList<>();

        //if the user is guessing
        if (getType().equalsIgnoreCase("user")) {
            Random rand = new Random();
            int randNum = rand.nextInt(100 + 1);
            if(randNum<dif){
                System.out.println("Go fish! ;)");
                drawCard(deck, smartComputerMemory);
            }else{
                if (opponent.getCards(opponent.getHand(), guess).isEmpty()) {
                    //opponent's hand does not have the card we guessed
                    System.out.println("Go fish!");
                    drawCard(deck, smartComputerMemory);
                } else {
                    //opponent's hand does contain the queried card
                    for (int i = 0; i < opponent.getHand().size(); i++) {
                        //find all cards in opponent's hand that match the player's
                        if (opponent.getHand().get(i).getValue().equals(guess)) {
                            // add card to player's hand and remove card from opponent's hand
                            addToHand(opponent.getHand().get(i));
                            toRemove.add(opponent.getHand().get(i));
                        }
                    }
                    for (int j = 0; j < toRemove.size(); j++) {
                        for (int i = 0; i < opponent.getHand().size(); i++) {
                            if (opponent.getHand().get(i).getValue().equals(toRemove.get(j).getValue())) {
                                opponent.removeFromHand(i);
                            }
                        }
                    }
                    checkFourOfAKind(guess, deck,smartComputerMemory);
                }
                //add a card to opponent's hand if their hand is empty
                if (opponent.getHand().isEmpty() && !deck.isEmpty()) {
                    opponent.drawCard(deck, smartComputerMemory);
                }
            }

        }

        //if the computer is guessing
        if (getType().equals("computer")) {
            boolean goodInput = false;
            char inputHaveCard = 'c';
            while (!goodInput) {
                Scanner reader = new Scanner(System.in);
                System.out.print("Your Hand: ");
                displayCards(opponent.getHand());
                System.out.println("Do you have any " + valueToDisplay(guess) + "s?");
                System.out.println("y - yes");
                System.out.println("n - no");
                inputHaveCard = reader.next().charAt(0);

                //check if input has correct domain
                if (inputHaveCard == 'y' || inputHaveCard == 'n') {
                    goodInput = true;
                }
            }
            if (inputHaveCard == 'n') {
                drawCard(deck, smartComputerMemory);
            } else if (inputHaveCard == 'y') {
                boolean playerReallyHasCard = false;
                for (int i = 0; i < opponent.getHand().size(); i++) {
                    //find all cards in opponent's hand that match the player's
                    if (opponent.getHand().get(i).getValue().equals(guess)) {
                        // add card to player's hand and remove card from opponent's hand
                        addToHand(opponent.getHand().get(i));
                        toRemove.add(opponent.getHand().get(i));
                        playerReallyHasCard = true;
                    }
                }
                for (int j = 0; j < toRemove.size(); j++) {
                    for (int i = 0; i < opponent.getHand().size(); i++) {
                        if (opponent.getHand().get(i).getValue().equals(toRemove.get(j).getValue())) {
                            opponent.removeFromHand(i);
                            if(opponent.getHand().isEmpty()){
                                if(!deck.isEmpty()){
                                    System.out.println("Your hand was empty, you drew a card.");
                                }
                                opponent.drawCard(deck, smartComputerMemory);
                            }
                        }
                    }
                }
                checkFourOfAKind(guess, deck,smartComputerMemory);
                if (!playerReallyHasCard) {
                    System.out.println("ok then, where is it?");
                    System.out.println("...LIAR!\n");
                    drawCard(deck, smartComputerMemory);
                }
            }
        }
    }

    private String valueToDisplay(String value){
        String display = value;
        switch (value.toLowerCase()) {
            case "11":
                display = "J";
                break;
            case "12":
                display = "Q";
                break;
            case "13":
                display = "K";
                break;
            case "14":
                display = "A";
                break;
            default:
                break;
        }
        return display;
    }

    public String handToString(){
        String output = "";
        for(Card c:hand){
            output= output+valueToDisplay(c.getValue())+c.getSuit()+" ";
        }
        return output;
    }

    public String FourOfAKindSetsToString(){
        String output = "";
        for(String v:getFourOfAKindSets()){
            output = output + valueToDisplay(v) + "♡ " + valueToDisplay(v) + "♢ " + valueToDisplay(v) + "♣ " + valueToDisplay(v) + "♠ ";
        }
        return output;
    }


}