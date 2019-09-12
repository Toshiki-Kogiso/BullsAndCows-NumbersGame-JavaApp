package BullsAndCowsApp;

import java.util.Iterator;

// Class for the hard difficulty computer.
public class HardAI extends Computer {

    // Guesses a code by choosing random code from the possible codes list. After each guess, will remove codes from
    // the possible codes list if the bull and cow count of the guess and the secret code match that of the guess and
    // every code left in the list.
    // This will narrow the list down until the computer correctly guesses the secret code.
    @Override
    protected String guessCode() {

        String guess = getRandomCodeFromList();
        Iterator<String> myIterator = possibleCodes.iterator();
        while (myIterator.hasNext()) {
            String code = myIterator.next();
            if (!((checkBullCount(code, guess) == checkBullCount(guess, userSecretCode)) &&
                    (checkCowCount(code, guess) == checkCowCount(guess, userSecretCode)))) {
                myIterator.remove();
            }
        }
        return guess;
    }
}