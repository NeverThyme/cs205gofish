import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String args[]){

        //create deck
        ArrayList<Card> deck = new ArrayList<Card>();
        for(int i=2;i<15;i++){
            String cardValue = Integer.toString(i);
            Card heart = new Card(cardValue,'♡');
            Card diamond = new Card(cardValue,'♢');
            Card club = new Card(cardValue,'♣');
            Card spade = new Card(cardValue,'♠');
            deck.add(heart);
            deck.add(diamond);
            deck.add(club);
            deck.add(spade);
        }


        //create two players

        Player user = new Player("user");
        Player computer = new Player("computer");

        //add 7 cards to each player's hand
        for(int i=0;i<7;i++){
            user.drawCard(deck);
            computer.drawCard(deck);
        }

        //start the game
        boolean game = true;

        while(game){
            //show user their hand
            System.out.println("Your Hand:");
            displayCards(user.getHand());

            //ask user to quite or guess
            boolean goodInput = false;
            char input_Quit = 'c';
            while(!goodInput){
                Scanner reader = new Scanner(System.in);
                System.out.println("Please choose:");
                System.out.println("q to quit");
                System.out.println("g to guess");
                input_Quit = reader.next().charAt(0);
                if(input_Quit=='q' || input_Quit == 'g'){
                    goodInput = true;
                }
            }

            //quit the game if the user typed 'q'
            if(input_Quit == 'q'){
                game = false;
                break;
            }

            //allow user to input a guess if they choose guess
            goodInput = false;
            String input_Guess = "";
            while(!goodInput) {
                Scanner reader = new Scanner(System.in);
                System.out.println("What is your guess?");
                System.out.println("enter a value 1-14 or:");
                System.out.println("j for jack");
                System.out.println("q for queen");
                System.out.println("k for king");
                System.out.println("a for ace");
                input_Guess = reader.next().substring(0, 1);
                //convert guess to number form
                if(input_Guess.toLowerCase().equalsIgnoreCase("j")){
                    input_Guess = "11";
                }else if(input_Guess.toLowerCase().equalsIgnoreCase("q")){
                    input_Guess = "12";
                }else if(input_Guess.toLowerCase().equalsIgnoreCase("k")){
                    input_Guess = "13";
                }else if(input_Guess.toLowerCase().equalsIgnoreCase("a")){
                    input_Guess = "14";
                }

                //check if input has correct domain
                if(isInteger(input_Guess) && Integer.parseInt(input_Guess) > 0 && Integer.parseInt(input_Guess) < 15){
                    goodInput = true;
                }
            }



            //player guess
            user.guess(computer,input_Guess, deck);

            //we should make computer guess random
            computer.guess(user,"1", deck);

            //maybe we should make player.guess return a string of information to use for the I/O?
            //so it would look like String turnData1 = user.guess(computer,input_Guess,deck);
            //                      String turnData2 = computer.guess(user,<what ever the random int is>, deck);
            //                      write turnData1 and turnData2 to I/O
            //                      we could also add a turn line number to the beginning of the string depending on how many times the game loop has run
            //                      the turn data string look something like:
            //                      "Round 7: user-hand: A A 3 J  computer-hand: 7 A 2 Q K 6  user guess: A  user-hand-after-guess: A A A 3 J ..........."


            //end game when user hand is empty
            //note that part of the computer.guess() could take a card from the player, but the player will also draw a new card in the same method
            if(user.getHand().size() <= 0){
                game = false;
                //display winner and print out each player's 4OfAKind arrays
            }

        }

    }


    public static void shuffle(ArrayList<Card> deck){
        //however you want to randomly rearrange the indexes
        //one way (maybe not the best) is to make a new array, loop through old array and try to add to a random index
        //if a card is already there, find the closes spot to the right and loop to the front when you get to the old array.size()
    }

    public static void displayCards(ArrayList<Card> cards){
        for(Card card: cards){
            if(card.getValue().equalsIgnoreCase("11")){
                System.out.print("J"+card.getSuit()+" ");
            }else if(card.getValue().equalsIgnoreCase("12")){
                System.out.print("Q"+card.getSuit()+" ");
            }else if(card.getValue().equalsIgnoreCase("13")){
                System.out.print("K"+card.getSuit()+" ");
            }else if(card.getValue().equalsIgnoreCase("14")){
                System.out.print("A"+card.getSuit()+" ");
            }else{
                System.out.print(card.getValue()+card.getSuit()+" ");
            }

        }
        System.out.println("");
    }

    public static boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }

}