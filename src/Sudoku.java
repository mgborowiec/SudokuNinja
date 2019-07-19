
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Objects;

public class Sudoku {

    /**
     * Print a game menu message to the console.
     */
    private static void printMenu() {
        System.out.print("\n" +
        		"1. Set field\n" +
        		"2. Clear field\n" +
                "3. Print game\n" +
                "4. Solve\n" +
                "5. Exit\n\n" +
                "Select an action [1-5]: ");
    }   

    /**
     * Read a single integer value from the console and return it.
     * This function blocks the program's execution until a user
     * entered a value into the command line and confirmed by pressing
     * the Enter key.
     * @return The user's input as integer or -1 if the user's input was invalid.
     */
    private static int parseInput() {
        Scanner in = new Scanner(System.in);
        try {
            return in.nextInt();
        } catch (InputMismatchException missE) {
            in.next(); // discard invalid input
            return -1;
        }
    }   

    /**
     * Display a dialog requesting a single integer which is returned
     * upon completion.
     *
     * The dialog is repeated in an endless loop if the given input 
     * is not an integer or not within min and max bounds.
     *
     * @param msg: a name for the requested data.
     * @param min: minimum accepted integer.
     * @param max: maximum accepted integer.
     * @return The user's input as integer.
     */
    private static int requestInt(String msg, int min, int max) {
        Objects.requireNonNull(msg);

        while(true) {
            System.out.print("Please provide " + msg + ": ");
            int input = parseInput();
            if (input >= min && input <= max) return input;
            else {
                System.out.println("Invalid input. Must be between " + min + " and " + max);
            }
        }
    }


    public static void main(String[] args) {
        
        GameGrid game = new GameGrid(args[0]);
        
        boolean check = true;
        
        while(check) {
        	
        printMenu();
        int selection = parseInput();
       
        switch(selection) {
            case 1:
        	    int xAdd = requestInt("x coordinate", 0, 8);
        	    int yAdd = requestInt("y coordinate", 0, 8);
        	    int newField = requestInt("Number for the field", 1, 9);
        	
        	    game.setField(xAdd, yAdd, newField);
        	    System.out.println(game.toString());
        	    break;
            case 2:
            	int xClear = requestInt("x coordinate", 0, 8);
                int yClear = requestInt("y coordinate", 0, 8);
                game.clearField(xClear, yClear);
                System.out.println(game.toString());
                break;
            case 3:
                System.out.println(game.toString());
                break;
            case 4:
                GameGrid copy = new GameGrid(game);
                if(Solver.solveRecursive(copy)) {
                    System.out.println(copy.toString());
                }
                else System.out.println("No solutions found");
            case 5:
                break;
        	default:
                System.out.println("Invalid input");
                break;
          }

          if (selection == 5) check = false;
        }
  
    }
    
}