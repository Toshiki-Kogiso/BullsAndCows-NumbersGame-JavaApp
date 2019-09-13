package BullsAndCowsApp;

// Class for the medium difficulty computer.
public class MediumAI extends Computer {

    // Guesses a code by choosing a random code from all possible codes in the list. However, will never guess
    // the same code twice by removing the guess from the possible codes list.
    @Override
    protected String guessCode() {

        String guess = getRandomCodeFromList();
        possibleCodes.remove(guess);
        return guess;
    }
}