package todolist;

import java.util.InputMismatchException;
import java.util.Scanner;

class Menu {

    private static final String MENU_PROMPT = """
            What do you want to do? Please type the corresponding number:
            1 - New task.
            2 - See current tasks.
            3 - Delete a task.
            4 - Exit.
            """;
    private static final String INVALID_NUMBER_OUT_OF_RANGE = "Please enter a valid number between %d and %d.\n";
    private static final String TASK_LIST_IS_EMPTY = "Your to do list is currently empty.";
    private static final String CURRENT_TASKS_TITLE = "These are your current tasks:";

    private enum MenuOptions {

        NEW_TASK,
        TASK_LIST,
        DELETE_TASK,
        EXIT

    }

    protected static void run(TaskList taskList) {
        MenuOptions option = null;
        while (option != MenuOptions.EXIT) {
            System.out.print(MENU_PROMPT);

            option = MenuOptions.values()[getIntInRange(1, 4) - 1];

            switch (option) {
                case NEW_TASK -> SubMenu.newTask(taskList);
                case TASK_LIST -> printTaskList(taskList);
                case DELETE_TASK -> SubMenu.deleteTask(taskList);
            }
        }
    }
    protected static int getIntInRange(int low, int high){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= low && input <= high) {
                    return input;
                }
            } catch (InputMismatchException err) {
                scanner.nextLine();
            }
            System.out.printf(INVALID_NUMBER_OUT_OF_RANGE, low, high);
        }
    }

    protected static void printTaskList(TaskList taskList){
        if (taskList.isEmpty()){
            System.out.println(TASK_LIST_IS_EMPTY);
        } else {
            System.out.println(CURRENT_TASKS_TITLE);
            System.out.print(taskList);
        }
    }

}
