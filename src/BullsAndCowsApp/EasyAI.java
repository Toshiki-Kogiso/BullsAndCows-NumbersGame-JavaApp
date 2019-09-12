package BullsAndCowsApp;

// Class for the easy computer difficulty.
public class EasyAI extends Computer {

    // Guesses a code by choosing a random code from all possible codes in the list.
    @Override
    protected String guessCode() {

        return getRandomCodeFromList();
    }
}