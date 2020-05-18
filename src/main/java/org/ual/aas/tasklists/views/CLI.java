package org.ual.aas.tasklists.views;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import static org.ual.aas.tasklists.App.printTaskList;
import org.ual.aas.tasklists.controllers.HibernateUtil;
import org.ual.aas.tasklists.models.Task;
import org.ual.aas.tasklists.models.TaskList;


public class CLI {
    
    public void menuView() {
        
        while(true) {
            System.out.println("Command Options: ");
            System.out.println("1: Criaçao da base de dados");
            System.out.println("2: Ver base de dados");
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
                    createDatabase(); 
                    break;
                case 2:
                    readEntity(1); 
                    break;
                case 3:
                    saveOrUpdateTasks(1);
                    break;
                case 4:
                    deleteTasks(1);
                    break;
                case 5:
                    insertTasks();
                    break;
                case 6:
                    System.out.println("Command Options: ");
                    System.out.println("1: Criaçao da base de dados");
                    System.out.println("2: Ver base de dados");
                    System.out.println("8: Quit");
                    System.out.print("Your choice: ");

                    int choice2 = scanner.nextInt();
                    switch (choice2) {
                        case 1:
                            System.out.println("Workded");
                            break;
                        case 2:
                            System.out.println("Worekd");
                            break;
                    }
                    break;
                case 7:
                    System.exit(0);
                default:
                    // The user input an unexpected choice.
            }
            }
        
    }
    
    public void createDatabase() {
        TaskList taskList = new TaskList("Sample Task List");
        taskList.getTasks().add(new Task("1st task", "doing"));
        taskList.getTasks().add(new Task("2nd task", "doing"));
        taskList.getTasks().add(new Task("3rd task", "doing"));
        
        Transaction transaction = null;
        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
//            // save the student objects
            session.saveOrUpdate(taskList);
//            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
            List resultSet = session.createQuery("from TaskList").list();
            for (TaskList tl : (List<TaskList>) resultSet) {
                printTaskList(tl);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        //System.out.println("TEste");
//        TaskList taskList = new TaskList("Sample Task List");
//        taskList.getTasks().add(new Task("1st task", "doing"));
//        taskList.getTasks().add(new Task("2nd task", "doing"));
//        taskList.getTasks().add(new Task("3rd task", "doing"));
//        SessionFactory sessionFactory = null;
//
//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure("hibernate.cfg.xml")
//                .build();
//        try {
//            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
//
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        session.save(taskList);
//        session.getTransaction().commit();
//        session.close();
//        sessionFactory.close();
    }
    
    public void readEntity(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // get Student entity using get() method
            TaskList taskList = session.get(TaskList.class, id);
            System.out.println(taskList.getName());
            //System.out.println(taskList.getEmail());

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
//        session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        List resultSet = session.createQuery("from TaskList").list();
//        for (TaskList tl : (List<TaskList>) resultSet) {
//            printTaskList(tl);
//        }
//
//        session.getTransaction().commit();
//        session.close();
    
    public static void saveOrUpdateTasks(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            //session.saveOrUpdate(taskList);

            // get entity from database
            TaskList taskList = session.get(TaskList.class, id);

            // do changes 
            taskList.setName("Ram");

            // update the student object
            session.saveOrUpdate(taskList);
            

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public void deleteTasks(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // Delete a persistent object
            TaskList taskList = session.get(TaskList.class, id);
            if (taskList != null) {
                session.delete(taskList);
                System.out.println("student 1 is deleted");
            }

            // Delete a transient object
//            Student student2 = new Student();
//            student2.setId(2);
//            session.delete(student2);
//            System.out.println("Student 2 is deleted");

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public void insertTasks() {
        //TaskList taskList = new TaskList("TaskListTest");
        
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            
            TaskList taskList = session.get(TaskList.class, 2);
            taskList.getTasks().add(new Task("TestTask", "Test"));
            // save the student objects
            session.saveOrUpdate(taskList);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    }
    
    
    

