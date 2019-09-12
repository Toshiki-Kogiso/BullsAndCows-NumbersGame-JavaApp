package BullsAndCowsApp;

// Class for players of the game.
public class Player {

    // Stores players secret code as a string.
    protected String secretCode;

    // Checks number of bulls by comparing the corresponding positions of the characters in the guess and the secret code.
    protected static int checkBullCount(String guess, String code){

        int bullCount = 0;
        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == code.charAt(i)) {
                bullCount++;
            }
        }
        return bullCount;
    }

    // Checks number of cows by checking if the secret code contains any of the characters in the guess.
    protected static int checkCowCount(String guess, String code){

        int cowCount = 0;
        for (int i = 0; i < 4; i++) {
            if (code.contains("" + guess.charAt(i))) {
                cowCount++;
            }
        }
        return cowCount - checkBullCount(guess, code);
    }
}