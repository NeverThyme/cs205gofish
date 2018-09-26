
import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand = new ArrayList<Card>();
    private ArrayList<String> fourOfAKindSets = new ArrayList<String>(); //each item is a value from "2" to "14"
    private String type; //computer or user

    //constructor
    public Player(String type){
        if(!(type.equalsIgnoreCase("computer") || type.equalsIgnoreCase("user"))){ throw new IllegalArgumentException();} //restrict domain on type
        this.type = type;
    }


    //get hand
    public ArrayList<Card> getHand(){ return hand; }

    //get type
    public String getType(){ return type; }

    //draw a new card
    public void drawCard(ArrayList<Card> deck){
        if(deck.size() > 0) { //do nothing if the deck has no cards
            hand.add(deck.get(0));
            deck.remove(0);
        }
    }

    //get a player's four-of-a-kind sets
    public ArrayList<String> getFourOfAKindSets(){
        return fourOfAKindSets;
    }

    //add a set to a payer's four-of-a-kind set
    public void addFourOfAKindSets(String value){
        if(!fourOfAKindSets.contains(value)){ //it doesn't make sense to have more than on four-of-a-kind sets for the same value
            fourOfAKindSets.add(value);
        }
    }



    public void guess(Player opponent, String guess, ArrayList<Card> deck){ //method should include opponents response to guess
        if(this == opponent){ throw new IllegalArgumentException();} //don't let user guess whats in own hand
        //if the user is guessing
        if(getType().equalsIgnoreCase("user")){
            //check if opponent (the computer) has card in hand
            //if opponent's hand doesn't have card with value guess (or if computer lies) do nothing
            //if hand does have card with value guess:
            //  add ALL cards with value to user's hand, check for 4OfAKind
            //  remove cards from computers hand. check if hand is empty. if so, draw card from deck (if its not empty)
        }

        //if the computer is guessing
        if(getType().equalsIgnoreCase("computer")){
            //ask user if they have cards with value guess
            //if user inputs yes, check their hand anyways
            //if user inputs no, do nothing.
        }
    }

}