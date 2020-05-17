package org.ual.aas.tasklists;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
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
        TaskList taskList = new TaskList("Sample Task List");
        taskList.getTasks().add(new Task("1st task", "doing"));
        taskList.getTasks().add(new Task("2nd task", "doing"));
        taskList.getTasks().add(new Task("3rd task", "doing"));

        printTaskList(taskList);

        SessionFactory sessionFactory = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(taskList);
        session.getTransaction().commit();
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();

        List resultSet = session.createQuery("from TaskList").list();
        for (TaskList tl : (List<TaskList>) resultSet) {
            printTaskList(tl);
        }

        session.getTransaction().commit();
        session.close();

        sessionFactory.close();

    }
}
