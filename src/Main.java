import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String args[]) {

        //create deck
        ArrayList<Card> deck = new ArrayList<>();
        for (int i = 2; i < 15; i++) {
            String cardValue = Integer.toString(i);
            Card heart = new Card(cardValue, '♡');
            Card diamond = new Card(cardValue, '♢');
            Card club = new Card(cardValue, '♣');
            Card spade = new Card(cardValue, '♠');
            deck.add(heart);
            deck.add(diamond);
            deck.add(club);
            deck.add(spade);
        }

        //create two players
        Player user = new Player("user");
        Player computer = new Player("computer");

        //add 7 cards to each player's hand
        for (int i = 0; i < 7; i++) {
            user.drawCard(deck);
            computer.drawCard(deck);
        }

        //start the game
        boolean game = true;

        boolean goodInputD = false;
        String inputDif = "0";
        int difficutly = 0;
        while (!goodInputD) {
            Scanner reader = new Scanner(System.in);
            System.out.println("Opponent lying % (integer from 1-100): ");
            inputDif = reader.next();
            if (isInteger(inputDif)) {
                difficutly = Integer.parseInt(inputDif);
                if(difficutly >= 0 && difficutly <= 100){
                    goodInputD = true;
                }
            }
        }


        while (game) {
            //show user their hand
            System.out.print("Your Hand: ");
            user.displayCards(user.getHand());

            //ask user to quite or guess
            boolean goodInput = false;
            //initializing to 'c', as compiler doesn't like initializing to ''
            char inputQuit = 'c';
            while (!goodInput) {
                Scanner reader = new Scanner(System.in);
                System.out.println("Please choose:");
                System.out.println("q to quit");
                System.out.println("g to guess");
                inputQuit = reader.next().charAt(0);
                if (inputQuit == 'q' || inputQuit == 'g') {
                    goodInput = true;
                }
            }

            //quit the game if the user typed 'q'
            if (inputQuit == 'q') {
                break;
            }

            //allow user to input a guess if they choose guess
            goodInput = false;
            String inputGuess = "";
            while (!goodInput) {
                Scanner reader = new Scanner(System.in);
                System.out.println("What is your guess?\n");
                System.out.println("Enter a value 2-14 or:");
                System.out.println("j for Jack");
                System.out.println("q for Queen");
                System.out.println("k for King");
                System.out.println("a for Ace");
                inputGuess = reader.next();
                //convert guess to number form
                switch (inputGuess.toLowerCase()) {
                    case "j":
                        inputGuess = "11";
                        break;
                    case "q":
                        inputGuess = "12";
                        break;
                    case "k":
                        inputGuess = "13";
                        break;
                    case "a":
                        inputGuess = "14";
                        break;
                    default:
                        break;
                }

                //check if input has correct domain
                if (isInteger(inputGuess) && Integer.parseInt(inputGuess) > 0 && Integer.parseInt(inputGuess) < 15) {
                    goodInput = true;
                }
            }

            //player guess
            user.guess(computer, inputGuess, deck, difficutly);

            if(!computer.getHand().isEmpty()){
                Random rand = new Random();
                int compGuess = rand.nextInt(computer.getHand().size());
                computer.guess(user, computer.getHand().get(compGuess).getValue(), deck,difficutly);
            }


            //end game when user hand is empty
            //note that part of the computer.guess() could take a card from the player, but the player will also draw a new card in the same method
            if (user.getHand().size() <= 0 && deck.isEmpty()) {
                if (user.getFourOfAKindSets().size() == computer.getFourOfAKindSets().size()) {
                    System.out.println("Tie Game! Everyone loses!");
                } else if (user.getFourOfAKindSets().size() >= computer.getFourOfAKindSets().size()) {
                    System.out.println("User wins! Congratulations!");
                } else {
                    System.out.println("Computer wins! Try harder next time!");
                }
                System.out.print("Your sets:");
                user.printSets(user.getFourOfAKindSets());
                System.out.print("\nComputer's sets:");
                computer.printSets(computer.getFourOfAKindSets());

                game = false;
                //display winner and print out each player's 4OfAKind arrays
            }
        }
    }

    public static void shuffle(ArrayList<Card> deck) {
        //however you want to randomly rearrange the indexes
        //one way (maybe not the best) is to make a new array, loop through old array and try to add to a random index
        //if a card is already there, find the closes spot to the right and loop to the front when you get to the old array.size()
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}