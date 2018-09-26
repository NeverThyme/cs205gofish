public class Card {
    private final String value;
    private final char suit;

    //constructor
    public Card(String value, char suit){
        this.value = value;
        this.suit = suit;
    }

    //get value
    public String getValue(){
        return value;
    }

    //get suit
    public char getSuit(){
        return suit;
    }
}
