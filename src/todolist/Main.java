package todolist;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome to my to do list!");

        TaskList taskList = new TaskList();
        Menu.run(taskList);

        System.out.println("Bye bye!");
    }
}