package BullsAndCowsApp;

import java.util.ArrayDeque;
import java.util.Deque;

// Main game application for Bulls and Cows.
public class BullsAndCowsApp {

    private int turnNumber; // Keeps track of how many turns have passed
    private Deque<String> autoCodeQueue; // Stores queue of codes used for automatic guessing
    private Deque<String> guessHistory; // Stores a history of user and computer guesses.

    private BullsAndCowsApp() {
        this.turnNumber = 0;
        this.guessHistory = new ArrayDeque<>();
    }

    private void start() {

        // Creates new instance of Player and sets the secret code for that player to the user input.
        Player user = new Player();
        System.out.print("Please enter your secret code (4 different digits): ");
        user.secretCode = promptUserForCode();

        // Creates instance of computer player based on difficulty prompted and generates a random secret code for the computer.
        // Also gives the computer the users secret code for the HardAI to use.
        System.out.print("Please enter the computer difficulty (1 - Easy, 2 - Medium or 3 - Hard): ");
        String difficulty = promptDifficulty();
        Computer computer = createComputerPlayer(difficulty);
        computer.secretCode = computer.getRandomCodeFromList();
        computer.userSecretCode = user.secretCode;
        //System.out.println("Computer's secret code is: " + computer.secretCode); // Uncomment to see computer guess for testing purposes.

        // Prompts user for manual or automatic guessing. If automatic is chosen, the automatic code queue will be
        // generated based on filename.
        System.out.print("Do you want to enter your guesses 1 - manually or 2 - automatically?: ");
        boolean auto = BullsAndCowsReader.promptManualOrAuto();
        if (auto) {
            System.out.print("Please enter a file name: ");
            autoCodeQueue = BullsAndCowsReader.createQueueFromFileInput();
        }

        // Loop to execute up to 7 turns per game.
        boolean draw = true;
        while(turnNumber < 7) {
            turnNumber++;
            System.out.println("---");

            // Prompts user for their guess and prints result of number of bulls and cows.
            // If the guess is correct, the loop is terminated and the user wins.
            // If automatic, uses first string in queue as the users guess and prints it.
            // Once queue is finished, auto is turned off and user is asked for input.
            System.out.println("Turn number " + (turnNumber));
            System.out.print("You guess: ");
            String userGuess;
            if (auto) {
                userGuess = autoCodeQueue.pollFirst();
                if (userGuess != null) {
                    System.out.println(userGuess);
                } else {
                    auto = false;
                    userGuess = promptUserForCode();
                }
            } else {
                userGuess = promptUserForCode();
            }
            System.out.println("Result: " + getBullsAndCowsResult(userGuess, computer.secretCode));
            guessHistory.addLast(userGuess);
            if (Player.checkBullCount(userGuess, computer.secretCode) == 4) {
                System.out.println("You win! :)");
                draw = false;
                break;
            }

            System.out.println();

            // Generates computer guess based on difficulty and prints results of cows and bulls.
            // If computer wins, prints loss message and terminates loop.
            String computerGuess = computer.guessCode();
            System.out.println("Computer guess: " + computerGuess);
            System.out.println("Result: " + getBullsAndCowsResult(computerGuess, user.secretCode));
            guessHistory.addLast(computerGuess);
            if (Player.checkBullCount(computerGuess, user.secretCode) == 4) {
                System.out.println("Computer has guessed your code, you lose! :(");
                draw = false;
                break;
            }
        }

        // If loop is terminated and neither side wins, prints draw message.
        if (draw) {
            System.out.println("It's a draw.");
        }

        // Prompts user if they wish to save results. If yes, asks for file name and saves results.
        System.out.print("Do you wish to save your results to a text file? 1 - yes, 2 - no: ");
        boolean save = BullsAndCowsWriter.promptSaveOrNot();
        if (save) {
            System.out.print("Please enter a file name: ");
            BullsAndCowsWriter.saveResults(user.secretCode, computer.secretCode, guessHistory, turnNumber);
        }
    }

    // Prompts user for a code (4 digit number). Will keep asking until input is valid.
    private String promptUserForCode() {

        String code = Keyboard.readInput();
        while (!validateUserCodeInput(code)) {
            System.out.print("Error, try again: ");
            code = Keyboard.readInput();
        }
        return code;
    }

    // Prompts user for difficulty. Will keep asking until input is valid.
    private String promptDifficulty() {

        String difficulty = Keyboard.readInput();
        difficulty = difficulty.toLowerCase();
        while (!validateDifficulty(difficulty)) {
            System.out.print("Error try again: ");
            difficulty = Keyboard.readInput();
        }
        return difficulty;
    }

    // Validates user input for a code. Returns false (invalid) if the length is not 4 characters, any character is
    // not a number, or if the code contains duplicate numbers.
    private boolean validateUserCodeInput(String input) {

        if (input.length() != 4) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            if (input.charAt(i) < 48 || input.charAt(i) > 57) {
                return false;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (input.substring(i + 1).contains("" + input.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    // Validates difficulty. Only returns true (valid) if the input is 1, 2, 3, easy, medium or hard. Case insensitive.
    private boolean validateDifficulty(String input) {

        return input.equals("1")||input.equals("2")||input.equals("3")||input.equals("easy")||input.equals("medium")||input.equals("hard");
    }

    // Returns corresponding object (EasyAI, MediumAI or HardAI) based on difficulty input. Case insensitive.
    private Computer createComputerPlayer (String difficulty) {

        if (difficulty.equals("1")||difficulty.equals("easy")) {
            return new EasyAI();
        } else if (difficulty.equals("2")||difficulty.equals("medium")) {
            return new MediumAI();
        }
        else {
            return new HardAI();
        }
    }

    // Calls for number of bulls and cows and prints the result. Also changes the words "bull" and "cow" to plurals if appropriate.
    protected static String getBullsAndCowsResult(String guess, String secretCode) {

        int bullCount = Player.checkBullCount(guess, secretCode);
        int cowCount = Player.checkCowCount(guess, secretCode);
        String bullPlurality = "bulls";
        String cowPlurality = "cows";

        if (bullCount == 1){
            bullPlurality = "bull";
        }

        if (cowCount == 1){
            cowPlurality = "cow";
        }
        return bullCount + " " + bullPlurality + " and " + cowCount + " " + cowPlurality;
    }

    public static void main(String[] args) {
        BullsAndCowsApp bullsAndCowsApp = new BullsAndCowsApp();
        bullsAndCowsApp.start();
    }
}