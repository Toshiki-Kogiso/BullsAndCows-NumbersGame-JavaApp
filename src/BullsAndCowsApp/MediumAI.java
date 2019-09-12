package BullsAndCowsApp;

import java.util.SortedSet;
import java.util.TreeSet;

// Class for the medium difficulty computer.
public class MediumAI extends Computer {

    // Stores attempted codes in a failed codes list.
    private SortedSet<String> failedCodes = new TreeSet<>();

    // Guesses a code by choosing a random code from all possible codes in the list. However, will never guess
    // the same code twice by comparing the chosen code with all codes in the failed code list.
    @Override
    protected String guessCode() {

        String guess = getRandomCodeFromList();
        while (failedCodes.contains(guess)) {
            guess = getRandomCodeFromList();
        }
        failedCodes.add(guess);
        return guess;
    }
}