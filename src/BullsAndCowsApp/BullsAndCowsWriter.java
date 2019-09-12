package BullsAndCowsApp;

import java.io.*;
import java.util.Deque;

// Class to handle writer methods
public class BullsAndCowsWriter {

    // Prompts user if they wish to save and returns true if yes.
    protected static boolean promptSaveOrNot() {

        String input = Keyboard.readInput();
        input = input.toLowerCase();
        while (!validateYesOrNo(input)) {
            System.out.print("Error, try again: ");
            input = Keyboard.readInput();
        }
        return input.equals("yes") || input.equals("1") || input.equals("y");
    }

    // Validates yes or no input.
    private static boolean validateYesOrNo(String input) {

        return input.equals("yes") || input.equals("no") || input.equals("1") || input.equals("2") || input.equals("y") || input.equals("n");
    }

    // Saves the results of the game in a text file.
    protected static void saveResults(String userCode, String computerCode, Deque<String> guessHistory, int turnNumber) {

        String fileName = Keyboard.readInput();
        try (PrintWriter myWriter = new PrintWriter(new FileWriter(new File(fileName)))) {
            myWriter.println("Bulls and Cows game result.");
            myWriter.println("Your code: " + userCode);
            myWriter.println("Computer's code: " + computerCode);
            boolean draw = true;
            for (int i = 0; i < turnNumber; i++) {
                myWriter.println("---");
                myWriter.println("Turn " + (i + 1));
                String userGuess = guessHistory.pollFirst();
                String computerGuess = guessHistory.pollFirst();

                myWriter.println("You guessed " + userGuess + ", scoring " + BullsAndCowsApp.getBullsAndCowsResult(userGuess, computerCode));
                if (Player.checkBullCount(userGuess, computerCode) == 4) {
                    myWriter.println("You win! :)");
                    draw = false;
                    break;
                }

                myWriter.println("Computer guessed " + computerGuess + ", scoring " + BullsAndCowsApp.getBullsAndCowsResult(computerGuess, userCode));
                if (Player.checkBullCount(computerGuess, userCode) == 4) {
                    myWriter.println("Computer has guessed your code, you lose! :(");
                    draw = false;
                    break;
                }
            }
            if (draw) {
                myWriter.println("It's a draw.");
            }

        } catch (IOException e) {
            System.out.println("IO Error");
        }
    }
}