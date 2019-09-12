package BullsAndCowsApp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

// Class to handle file reader methods.
public class BullsAndCowsReader {

    // Prompts user for manual or automatic guessing. returns true if automatic is chosen.
    protected static boolean promptManualOrAuto() {

        String input = Keyboard.readInput();
        input = input.toLowerCase();
        while (!validateAutoOrManual(input)) {
            System.out.print("Error, try again: ");
            input = Keyboard.readInput();
        }
        return input.equals("2") || input.equals("auto") || input.equals("automatic") || input.equals("automatically");
    }

    // Validates the input for manual or automatic.
    private static boolean validateAutoOrManual(String input) {

        return input.equals("1") || input.equals("2") || input.equals("auto") || input.equals("automatic") ||
                input.equals("automatically") || input.equals("manual") || input.equals("manually");
    }

    // Creates a queue from the file given. If the file doesnt exist, the user is asked again for the file name.
    protected static Deque<String> createQueueFromFileInput() {

        String fileName = Keyboard.readInput();
        Deque<String> codeQueue = new ArrayDeque<>();
        try (Scanner myScanner = new Scanner(new File(fileName))) {
            while (myScanner.hasNext()) {
                codeQueue.addLast(myScanner.next());
            }
        } catch (IOException e) {
            System.out.print("File doesn't exist, try again: ");
            return createQueueFromFileInput();
        }
        return codeQueue;
    }
}