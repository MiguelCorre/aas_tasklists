package org.ual.aas.tasklists.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import static java.lang.Integer.parseInt;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.ual.aas.tasklists.HibernateUtil;
import org.ual.aas.tasklists.models.Task;
import org.ual.aas.tasklists.models.TaskList;


public class TaskController {
    
    public static void getTasks(HttpServletRequest req, HttpServletResponse resp, int idT) throws ServletException, IOException {
        resp.setContentType("application/json");
        Transaction transaction = null;
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        
        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Task> list = null;

            TypedQuery<Task> query = session.createQuery("from Task");
            PrintWriter writer2 = resp.getWriter();
            list = query.getResultList();
            if (list.isEmpty()) {
                writer2.println("No tasklists available.");
            } else {
                for (Task task : list) {
                    if (task.getId() == idT) {
                        //writer2.println(task.getDescription() + " " + task.getStatus());
                        jsonBuilder.add("task" + task.getId(), "Description: " + task.getDescription() + "   Status: " + task.getStatus());
                    }
                }
                JsonObject empObj = jsonBuilder.build();
                StringWriter strWtr = new StringWriter();
                JsonWriter jsonWtr = Json.createWriter(strWtr);
                jsonWtr.writeObject(empObj);
                writer2.println(strWtr.toString());
                jsonWtr.close();
            }
            writer2.close();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public static void insertTasks(HttpServletRequest req, HttpServletResponse resp, int idL, String name) throws ServletException, IOException {
        Transaction transaction = null;
        String[] splitsParam = req.getQueryString().split("=");
        String[] splits = req.getRequestURI().split("/");
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            
            TaskList taskList = session.get(TaskList.class, idL);
            taskList.getTasks().add(new Task(name, "doing"));
            session.saveOrUpdate(taskList);
            PrintWriter writer2 = resp.getWriter();
            writer2.println("Foi adicionado a tarefa " + splitsParam[1] + " à Lista " + idL);
            writer2.close();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public static void updateNameTasks(HttpServletRequest req, HttpServletResponse resp, int idT, String description) throws ServletException, IOException { 
        Transaction transaction = null;
        String[] splitsParam = req.getQueryString().split("=");
        String[] splits = req.getRequestURI().split("/");
        PrintWriter writer2 = resp.getWriter();
        
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
                        writer2.println("Descrição alterada para " + splitsParam[1]);
                        writer2.close();
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
    
    public static void updateStatusTasks(HttpServletRequest req, HttpServletResponse resp, int idT, String status) throws ServletException, IOException { 
        Transaction transaction = null;
        PrintWriter writer2 = resp.getWriter();
        String[] splitsParam = req.getQueryString().split("=");
        String[] splits = req.getRequestURI().split("/");
        
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
                        writer2.println("Status alterado para " + splitsParam[1]);
                        writer2.close();
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
    
    
    
    public static void deleteTasks(HttpServletRequest req, HttpServletResponse resp, int idT) throws ServletException, IOException { 
        Transaction transaction = null;
        PrintWriter writer2 = resp.getWriter();
        String[] splitsParam = req.getQueryString().split("=");
        String[] splits = req.getRequestURI().split("/");
        
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
                            writer2.println("Foi apagado a Task " + splits[4]);
                            writer2.close();
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
