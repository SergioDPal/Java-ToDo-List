package todolist;

import java.text.SimpleDateFormat;
import java.util.Date;
class Task {
    public static final String DATE_PRINT_FORMAT = "MM/dd/yyyy";
    public static final String DATED_TASK_FORMAT = """
            - Title: %s.
                - Due date: %s.
            """;
    public static final String TASK_FORMAT = """
            - Title: %s.
            """;
    private String title;
    private Date dueDate = null;

    public Task(String title){
        this.title = title;
    }

    public Task(String title, Date dueDate){
        this(title);
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        if (this.dueDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_PRINT_FORMAT);
            String date = formatter.format(dueDate);
            return String.format(DATED_TASK_FORMAT, title, date);
        } else {
            return String.format(TASK_FORMAT, title);
        }
    }
}
