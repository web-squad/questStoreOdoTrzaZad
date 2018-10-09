package views;

import java.util.Scanner;

public class View {

    private Scanner scanner;

    public View() {
        scanner = new Scanner(System.in);
    }

    public String getInputString(String text) {
        print("\n" + text);
        return scanner.nextLine();
    }

    public int getInputInt(int start, int end) {
        boolean isCorrect = false;
        int input = 0;
        while(!isCorrect) {
            print(String.format("\nEnter a number from %d to %d: ", start, end));
            try{
                input = Integer.parseInt(scanner.nextLine());


            }catch(NumberFormatException e) {
                print("\nThis is not a number. Try again!");
                continue;
            }
            if(input  >= start && input <= end) {
                isCorrect = true;
            }else {
                print("\nThis is not a number from given range. Try again!");
            }
        }
        return input;
    }

    public int getInputInt() {
        boolean isCorrect = false;
        int input = 0;
        while(!isCorrect) {
            print("\nEnter a number");
            try{
                input = Integer.parseInt(scanner.nextLine());
                isCorrect = true;

            }catch(NumberFormatException e) {
                print("\nThis is not a number. Try again!");
                continue;
            }
        }
        return input;
    }

    public void print(String text) {
        System.out.print(text.replace("'", ""));
    }

    public void printMenu(String... items) {

        print("\nWhat would you like to do: \n");

        for (int i=1; i<items.length; i++) {
            print(String.format("    (%d) %s\n", i, items[i]));
        }

        print(String.format("    (0) %s\n", items[0]));
    }

    public void waitForConfirm(){
        getInputString("Press enter to continue");
    }
}