
import java.util.ArrayList;
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
            //System.out.println("Removing " + hand.get(i).getSuit());
            hand.remove(i);
        } else {
            System.out.println("YOU SHOULD NEVER SEE THIS");
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
    public void drawCard(ArrayList<Card> deck) {
        if (deck.size() > 0) { //do nothing if the deck has no cards
            addToHand(deck.get(0));
            deck.remove(0);
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
    public boolean checkFourOfAKind(String value, ArrayList<Card> deck) {
        int ctr = 0;
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getValue().equals(value)) {
                ctr++;
            }
        }
        if (ctr == 4) {
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
            if (getHand().isEmpty()) {
                drawCard(deck);
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

    public void guess(Player opponent, String guess, ArrayList<Card> deck) { //method should include opponents response to guess
        ArrayList<Card> toRemove = new ArrayList<>();

        if (this == opponent) {
            throw new IllegalArgumentException();
        } //don't let user guess whats in own hand
        //if the user is guessing
        if (getType().equalsIgnoreCase("user")) {
            if (opponent.getCards(opponent.getHand(), guess).isEmpty()) {
                //opponent's hand does not have the card we guessed
                System.out.println("Go fish!");
                drawCard(deck);
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
                for (int i = 0; i < opponent.getHand().size(); i++) {
                    for (int j = 0; j < toRemove.size(); j++) {
                        if (opponent.getHand().get(i).getValue().equals(toRemove.get(j).getValue())) {
                            opponent.removeFromHand(i);
                        }
                    }
                }
                checkFourOfAKind(guess, deck);
            }
            //add a card to opponent's hand if their hand is empty
            if (opponent.getHand().isEmpty() && !deck.isEmpty()) {
                opponent.getHand().add(deck.get(0));
            }
        }

        //if the computer is guessing
        if (getType().equals("computer")) {
            boolean goodInput = false;
            char inputHaveCard = 'c';
            while (!goodInput) {
                Scanner reader = new Scanner(System.in);
                System.out.println("Your Hand: ");
                displayCards(opponent.getHand());
                System.out.println("Do you have any " + guess + "s?");
                System.out.println("y - yes");
                System.out.println("n - no");
                inputHaveCard = reader.next().charAt(0);

                //check if input has correct domain
                if (inputHaveCard == 'y' || inputHaveCard == 'n') {
                    goodInput = true;
                }
            }
            if (inputHaveCard == 'n') {
                drawCard(deck);
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
                for (int i = 0; i < opponent.getHand().size(); i++) {
                    for (int j = 0; j < toRemove.size(); j++) {
                        if (opponent.getHand().get(i).getValue().equals(toRemove.get(j).getValue())) {
                            opponent.removeFromHand(i);
                        }
                    }
                }
                checkFourOfAKind(guess, deck);
                if (!playerReallyHasCard) {
                    System.out.println("ok then, where is it?");
                    System.out.println("LIAR!\n");
                    drawCard(deck);
                }
            }
        }
    }
}
