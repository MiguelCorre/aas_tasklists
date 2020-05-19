package org.ual.aas.tasklists;
import java.util.logging.Level;
import org.ual.aas.tasklists.models.Task;
import org.ual.aas.tasklists.models.TaskList;
import org.ual.aas.tasklists.views.CLI;

public class App {

    public static void printTaskList(TaskList taskList) {
        System.out.println(taskList.getName());
        for (Task task : taskList.getTasks()) {
            System.out.println(task.getDescription());
        }
    }

    public static void main(String[] args) {
       
       CLI cli = new CLI();
       cli.menuView();

    }
}
