package org.ual.aas.tasklists.views;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import static org.ual.aas.tasklists.App.printTaskList;
import org.ual.aas.tasklists.models.Task;
import org.ual.aas.tasklists.models.TaskList;


public class CLI {
    
    public void menuView() {
        
        
        System.out.println("Command Options: ");
        System.out.println("1: Criaçao da base de dados");
        System.out.println("2: Num Questions Asked");
        System.out.println("3: All Numbers Are the Same");
        System.out.println("4: Sum Between Two Integers");
        System.out.println("5: Repeat the String");
        System.out.println("6: It is Palindrome");
        System.out.println("7: Display");
        System.out.println("8: Quit");
        System.out.print("Your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println("TEste");
                TaskList taskList = new TaskList("Sample Task List");
                taskList.getTasks().add(new Task("1st task", "doing"));
                taskList.getTasks().add(new Task("2nd task", "doing"));
                taskList.getTasks().add(new Task("3rd task", "doing"));
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
                sessionFactory.close();
                break;
            case 2:
                session = sessionFactory.openSession();
                session.beginTransaction();

                List resultSet = session.createQuery("from TaskList").list();
                for (TaskList tl : (List<TaskList>) resultSet) {
                    printTaskList(tl);
                }

                session.getTransaction().commit();
                session.close();
                break;
            case 3:
                // Perform "decrypt number" case.
                break;
            case 4:
                
                break;
            default:
                // The user input an unexpected choice.
        }
    }
    
}
