package org.ual.aas.tasklists.controllers;

import java.util.List;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.ual.aas.tasklists.HibernateUtil;
import org.ual.aas.tasklists.models.Task;

import org.ual.aas.tasklists.models.TaskList;

public class TaskListController {
	public static List<TaskList> getLists(){
		// TODO: obter as listas da base de dados
		return null;
	}
        
        public static void insertTaskLists(String name) {
        TaskList taskList = new TaskList(name);
        
        Transaction transaction = null;
       
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(taskList);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
        
        public static void updateTaskLists(int id, String name) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            //session.saveOrUpdate(taskList);

            // get entity from database
            TaskList taskList = session.get(TaskList.class, id);

            // do changes 
            taskList.setName(name);

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
        
        public static void getTaskLists(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            TaskList taskList = session.get(TaskList.class, id);

            if (taskList.getTasks().isEmpty()) {
                System.out.println("No tasks available in " + taskList.getName());
            } else {
                System.out.println("List " + taskList.getName() + ":");
                for(Task task : taskList.getTasks() ) {
                System.out.println(task.getDescription() + " " + task.getStatus());
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
        public static void getTaskListsL() {
             //java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            List<TaskList> list = null;

            TypedQuery<TaskList> query = session.createQuery("from TaskList");

            list = query.getResultList();
            if (list.isEmpty()) {
                System.out.println("No tasklists available.");
            } else {
                for(TaskList taskList : list) {
                System.out.println(taskList.getName());
                
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
        
        public static void deleteTaskLists(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // Delete a persistent object
            TaskList taskList = session.get(TaskList.class, id);
            if (taskList != null) {
                session.delete(taskList);
                System.out.println(taskList.getName() + " is deleted");
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
