package todolist;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

class SubMenu extends Menu{

    public static final String ADD_DATE_PROMPT = """
            Do you want to add a due date?
            1 - Yes
            2 - No
            """;
    public static final String TASK_CREATED_S = """
            Task created:
            %s
            """;
    public static final String DELETE_TASK_PROMPT = "Please enter the number of the task you want to delete.";
    public static final String TASK_DELETED = "Task deleted.";
    public static final String INVALID_TITLE = "Please enter a valid title";
    public static final String DATE_PROMPT = "Please enter the date in format dd/mm/yyyy:";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String INVALID_DATE = "Please enter a valid date";

    protected static void newTask(TaskList taskList) {
        String title = getString("Please type the title for the new task");
        System.out.print(ADD_DATE_PROMPT);

        boolean shouldAddDate = getIntInRange(1, 2) == 1;

        Date dueDate = null;
        if (shouldAddDate) {
            dueDate = getDate();
        }

        Task task = new Task(title, dueDate);
        taskList.add(task);
        System.out.printf(TASK_CREATED_S, task);
    }

    protected static void deleteTask(TaskList taskList) {
        printTaskList(taskList);
        if (!taskList.isEmpty()) {
            System.out.println(DELETE_TASK_PROMPT);
            int taskNumber = getIntInRange(1, taskList.size());

            taskList.deleteTask(taskNumber);

            System.out.println(TASK_DELETED);
        }
    }

    private static String getString(String message) {
        System.out.println(message);
        while (true) {
            try{
                Scanner scanner = new Scanner(System.in);
                String string = scanner.nextLine();
                if (!string.isBlank()) {
                    return string;
                }
            } catch (InputMismatchException err) {
                System.out.println(INVALID_TITLE);
            }
        }
    }

    private static Date getDate() {

        Scanner scanner = new Scanner(System.in);
        System.out.println(DATE_PROMPT);

        while (true) {
            try {
                String dateString = scanner.nextLine();
                validateDateFormat(dateString);

                Date newDueDate = new SimpleDateFormat(DATE_FORMAT).parse(dateString);
                Date today = Calendar.getInstance().getTime();

                if (newDueDate.before(today)) {
                    System.out.println(INVALID_DATE);
                } else {
                    return newDueDate;
                }
            } catch (ArrayIndexOutOfBoundsException | ParseException err) {
                System.out.println(INVALID_DATE);
            }
        }
    }
    private static void validateDateFormat(String date) throws ParseException {
        DateFormat newDueDate = new SimpleDateFormat(DATE_FORMAT);
        newDueDate.setLenient(false);
        newDueDate.parse(date);
    }
}
