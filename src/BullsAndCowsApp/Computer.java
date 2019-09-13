package BullsAndCowsApp;

import java.util.ArrayList;
import java.util.Iterator;

// An class extending player for the computer.
public abstract class Computer extends Player {

    // Stores users secret code and all the possible 4 digit number combinations possible for the game.
    protected String userSecretCode;
    protected ArrayList<String> possibleCodes;

    public Computer() {
        possibleCodes = generateEveryPossibleCode();
    }

    // Returns and ArrayList of every possible code. Loops through numbers from 0 to 9999, formats it to a four digit
    // representation and adds it to the list if no digit duplicates are found.
    private ArrayList<String> generateEveryPossibleCode() {

        ArrayList<String> possibleCodes = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            String number = String.format("%04d", i);
            char[] digits = number.toCharArray();
            boolean containsDuplicate = false;
            for (int j = 0; j < 3; j++) {
                if (number.substring(j + 1).contains("" + digits[j])){
                    containsDuplicate = true;
                    break;
                }
            }
            if (!containsDuplicate) {
                possibleCodes.add(number);
            }
        }
        return possibleCodes;
    }

    // Gets a random code from the possible codes list.
    protected String getRandomCodeFromList() {
        return possibleCodes.get((int)(Math.random() * possibleCodes.size()));
    }

    // Abstract method for guessing the code.
    protected abstract String guessCode();
}