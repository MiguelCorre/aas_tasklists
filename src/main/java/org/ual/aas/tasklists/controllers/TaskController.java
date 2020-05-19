package org.ual.aas.tasklists.controllers;

import java.util.List;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.ual.aas.tasklists.HibernateUtil;
import org.ual.aas.tasklists.models.Task;
import org.ual.aas.tasklists.models.TaskList;


public class TaskController {
    
    public static void getTasks(int idT) {  // TENHO QUE TESTAR
        Transaction transaction = null;
        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            List<Task> list = null;

            TypedQuery<Task> query = session.createQuery("from Task");

            list = query.getResultList();
            if (list.isEmpty()) {
                System.out.println("No tasklists available.");
            } else {
                for (Task task : list) {
                    if (task.getId() == idT) {
                        System.out.println(task.getDescription());
                    }
                }
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public static void insertTasks(int idL, String name) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            
            TaskList taskList = session.get(TaskList.class, idL);
            taskList.getTasks().add(new Task(name, "doing"));
            session.saveOrUpdate(taskList);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public static void updateNameTasks(int idT, String description) { //TENHO QUE TESTAR
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Task> list = null;

            TypedQuery<Task> query = session.createQuery("from Task");

            list = query.getResultList();
            if (list.isEmpty()) {
                System.out.println("No tasklists available.");
            } else {
                for (Task task : list) {
                    if (task.getId() == idT) {
                        task.setDescription(description);
                        session.saveOrUpdate(task);
                    }
                }
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public static void updateStatusTasks(int idT, String status) { //TENHO QUE TESTAR
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Task> list = null;

            TypedQuery<Task> query = session.createQuery("from Task");

            list = query.getResultList();
            if (list.isEmpty()) {
                System.out.println("No tasklists available.");
            } else {
                for (Task task : list) {
                    if (task.getId() == idT) {
                        task.setStatus(status);
                        session.saveOrUpdate(task);
                    }
                }
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    
    
    public static void deleteTasks(int idT) { //TENHO QUE TESTAR
        Transaction transaction = null;
        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Task> list = null;
            List<TaskList> list2 = null;

            TypedQuery<Task> query = session.createQuery("from Task");
            TypedQuery<TaskList> query2 = session.createQuery("from TaskList");

            list = query.getResultList();
            list2 = query2.getResultList();
            if (list.isEmpty()) {
                System.out.println("No tasklists available.");
            } else {
                for (TaskList taskList : list2) {
                    for (Task task : list) {
                        if (task.getId() == idT) {
                            taskList.getTasks().remove(task);
                            session.remove(task);
                            System.out.println("Removido.");
                        }
                    }
                }

            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
