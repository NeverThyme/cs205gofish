
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String args[]) {
        //open a new output log file
        FIO fio = new FIO();
        String fName = "src/gameLogs.txt";

        fio.fWrite(fName, "\n=== NEW GAME ===\n");
        //initialize rounds for use in output logs
        int round = 0;

        //needed for smart computer
        ArrayList<String> smartComputerMemory = new ArrayList<String>();

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
        //ensure random start order
        shuffle(deck);

        //create two players
        Player user = new Player("user");
        Player computer = new Player("computer");

        //add 7 cards to each player's hand
        for (int i = 0; i < 7; i++) {
            user.drawCard(deck, smartComputerMemory);
            computer.drawCard(deck, smartComputerMemory);
        }

        //start the game
        boolean game = true;

        //set the lying chance
        boolean goodInputL = false;
        String inputLyingChace = "0";
        int lyingChance = 0;
        while (!goodInputL) {
            Scanner reader = new Scanner(System.in);
            System.out.println("Opponent lying % (integer from 0-100): ");
            inputLyingChace = reader.next();
            if (isInteger(inputLyingChace)) {
                lyingChance = Integer.parseInt(inputLyingChace);
                if (lyingChance >= 0 && lyingChance <= 100) {
                    goodInputL = true;
                }
            }
        }

        //ask user to set smart computer or not
        boolean goodInputSmart = false;
        //initializing to 'c', as compiler doesn't like initializing to ''
        char inputSmart = 'c';
        while (!goodInputSmart) {
            Scanner reader = new Scanner(System.in);
            System.out.println("Will your opponent remember all your guesses?");
            System.out.println("y - yes");
            System.out.println("n - no");
            inputSmart = reader.next().charAt(0);
            if (inputSmart == 'y' || inputSmart == 'n') {
                goodInputSmart = true;
            }
        }

        //set computer memory based on input
        boolean isComputerMemoryOn = false;
        if (inputSmart == 'y') {
            isComputerMemoryOn = true;
        } else {
            isComputerMemoryOn = false;
        }

        //start game loop
        while (game) {

            round++;
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
            //add user's guess to computer's memory
            smartComputerMemory.add(inputGuess);

            //player guess
            String output = Integer.toString(round);
            output = "round " + output + ":";
            fio.fWrite(fName, output);

            output = "user's hand: " + user.handToString();
            fio.fWrite(fName, output);
            output = "computer's hand: " + computer.handToString();
            fio.fWrite(fName, output);

            output = "user's guess: " + valueToDisplay(inputGuess);
            fio.fWrite(fName, output);

            user.guess(computer, inputGuess, deck, lyingChance, smartComputerMemory);

            output = "user's hand: " + user.handToString();
            fio.fWrite(fName, output);
            output = "computer's hand: " + computer.handToString();
            fio.fWrite(fName, output);

            //if the computer has cards
            if (!computer.getHand().isEmpty()) {

                boolean compterHasCardInMemory = false;

                //get an array, validGuess, based on what the computer has in its hand and memory
                ArrayList<String> validGuess = new ArrayList<String>();
                for (Card c : computer.getHand()) {
                    for (String v : smartComputerMemory) {
                        if (c.getValue().equals(v) && !validGuess.contains(v)) {
                            validGuess.add(v);
                            compterHasCardInMemory = true;
                        }
                    }
                }

                //if computer has cards in ValidGuess and the user set computer memory on then pick a random guess from valid guesses
                //if not, guess randomly based on computer's hand
                if (compterHasCardInMemory && isComputerMemoryOn) {
                    Random rand = new Random();
                    int compGuess = rand.nextInt(validGuess.size());
                    output = "computer's guess: " + valueToDisplay(validGuess.get(compGuess));
                    computer.guess(user, validGuess.get(compGuess), deck, lyingChance, smartComputerMemory);
                    fio.fWrite(fName, output);
                } else {
                    Random rand2 = new Random();
                    int compGuess2 = rand2.nextInt(computer.getHand().size());
                    output = "computer's guess: " + valueToDisplay(computer.getHand().get(compGuess2).getValue());
                    computer.guess(user, computer.getHand().get(compGuess2).getValue(), deck, lyingChance, smartComputerMemory);
                    fio.fWrite(fName, output);
                }

            }

            //output player hands to log
            output = "user's hand: " + user.handToString();
            fio.fWrite(fName, output);
            output = "computer's hand: " + computer.handToString();
            fio.fWrite(fName, output);

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

                output = "user's sets: " + user.FourOfAKindSetsToString();
                fio.fWrite(fName, output);
                output = "computers's sets: " + computer.FourOfAKindSetsToString();
                fio.fWrite(fName, output);

                game = false;
            }
        }
    }

    //method that takes an array of cards and randomly reorganizes the indexes
    public static void shuffle(ArrayList<Card> deck) {
        ArrayList<Card> newArray = new ArrayList<Card>();
        for (int i = 0; i < deck.size(); i++) {
            Card newCard = new Card("0", '0');
            newArray.add(newCard);
        }
        for (int i = 0; i < deck.size(); i++) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(deck.size());
            //try to place card in random index
            boolean cardPlaced = false;
            while (!cardPlaced) {
                if (newArray.get(randomIndex).getValue() == "0") {
                    newArray.set(randomIndex, deck.get(i));
                    cardPlaced = true;
                }
                randomIndex++;
                if (randomIndex >= 52) {
                    randomIndex = 0;
                }
            }
        }

        for (int i = 0; i < deck.size(); i++) {
            deck.set(i, newArray.get(i));
        }

    }

    //checks to see if a given string is an integer
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //converts integer to face card value
    private static String valueToDisplay(String value) {
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

}
