package todolist;

import java.util.ArrayList;

class TaskList {
    public static final String TASK_ENTRY_FORMAT = """
            Task %d:
                %s
            """;
    private final ArrayList<Task> taskList = new ArrayList<>();

    protected void add(Task task) {
        this.taskList.add(task);
    }

    protected void deleteTask(int taskNumber) {
        this.taskList.remove(taskNumber - 1);
    }

    protected int size() {
        return this.taskList.size();
    }

    protected boolean isEmpty() {
        return this.taskList.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder stringList = new StringBuilder();
        int counter = 1;
        for (Task task : taskList) {
            stringList.append(String.format(TASK_ENTRY_FORMAT, counter, task.toString()));
            counter += 1;
        }
        return stringList.toString();
    }
}
