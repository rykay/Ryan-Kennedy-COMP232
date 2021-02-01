import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Todo_List {

    public static void main(String[] args) {
        ArrayList<String> todoList = new ArrayList<String>();
        Scanner myScanner = new Scanner(System.in);
        System.out.println("---- Todo List ----");
        System.out.println("Enter 't' to add a task, 'p' to print all tasks, 'c' to clear your list, and 'q' to close the list!");
        String input = myScanner.nextLine();
        int closer = 1;
        while(closer == 1) {
            if(input.equals("t") || input.equals("T")){
                System.out.println("Add task:");
                String task = myScanner.nextLine();
                todoList.add(task);
                System.out.println("Enter 't' to add a task, 'p' to print all tasks, 'c' to clear your list, and 'q' to close the list!");
                input = myScanner.nextLine();

            }
            if(input.equals("p") || input.equals("P")){
                //print array list in an orderly fashion
                System.out.println(todoList);
                System.out.println("Enter 't' to add a task, 'p' to print all tasks, 'c' to clear your list, and 'q' to close the list!");
                input = myScanner.nextLine();
            }
            if(input.equals("c") || input.equals("C")){
                //clear the arraylist
                todoList.clear();
                System.out.println("List has been cleared");
                System.out.println("Enter 't' to add a task, 'p' to print all tasks, 'c' to clear your list, and 'q' to close the list!");
                input = myScanner.nextLine();
            }
            if(input.equals("Q") || input.equals("q")){
                System.out.println("Closing list");
                closer = 2;
            }
        }
    System.exit(1);
    }
}
