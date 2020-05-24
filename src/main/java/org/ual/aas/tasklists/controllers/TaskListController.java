package org.ual.aas.tasklists.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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

public class TaskListController {

    public static void insertTaskLists(HttpServletRequest req, HttpServletResponse resp, String name) throws ServletException, IOException  {
        TaskList taskList = new TaskList(name);
        String[] splitsParam = req.getQueryString().split("=");
        String[] splits = req.getRequestURI().split("/");
        Transaction transaction = null;

        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(taskList);
            PrintWriter writer = resp.getWriter();
            writer.println("Foi criada a lista " + splitsParam[1] + ".");
            writer.close();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void updateTaskLists(HttpServletRequest req, HttpServletResponse resp, int id, String name) throws ServletException, IOException {
        Transaction transaction = null;
        PrintWriter writer2 = resp.getWriter();
        String[] splitsParam = req.getQueryString().split("=");
        String[] splits = req.getRequestURI().split("/");
        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            TaskList taskList = session.get(TaskList.class, id);
            taskList.setName(name);

            session.saveOrUpdate(taskList);
            writer2.println("Nome alterado para " + splitsParam[1]);
            writer2.close();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void getTaskLists(HttpServletRequest req, HttpServletResponse resp, int id) throws ServletException, IOException {     
        resp.setContentType("application/json");
        Transaction transaction = null;
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        
        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TaskList taskList = session.get(TaskList.class, id);
            PrintWriter writer2 = resp.getWriter();
            
            if (taskList.getTasks().isEmpty()) {
                writer2.println("No tasks available in " + taskList.getName());
            } else {
                writer2.println("List " + taskList.getName() + ":");
                for (Task task : taskList.getTasks()) {
                    //writer2.println(task.getDescription() + " " + task.getStatus() + " ID: " + task.getId());
                    jsonBuilder.add("task" + task.getId(), "Description: " + task.getDescription() + "   Status: " + task.getStatus());
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

    public static void getTaskListsL(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        int x = 1;
        Transaction transaction = null;
        String[] splits = req.getRequestURI().split("/");
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        
        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            List<TaskList> list = null;
            TypedQuery<TaskList> query = session.createQuery("from TaskList");
            list = query.getResultList();
            PrintWriter writer = resp.getWriter();

            if (list.isEmpty()) {
                writer.println("No tasklists available.");
            } else {
                for (TaskList taskList : list) {
                    System.out.println(taskList.getName());
                    jsonBuilder.add("list" + x, taskList.getName());
                    //writer.println(taskList.getName());
                    x++;
                }
                
                JsonObject empObj = jsonBuilder.build();
                StringWriter strWtr = new StringWriter();
                JsonWriter jsonWtr = Json.createWriter(strWtr);
                jsonWtr.writeObject(empObj);
                writer.println(strWtr.toString());
                jsonWtr.close();

            }
            
            writer.close();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void deleteTaskLists(HttpServletRequest req, HttpServletResponse resp, int id) throws ServletException, IOException {
        Transaction transaction = null;
        PrintWriter writer2 = resp.getWriter();
        String[] splitsParam = req.getQueryString().split("=");
        String[] splits = req.getRequestURI().split("/");
        
        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            TaskList taskList = session.get(TaskList.class, id);
            if (taskList != null) {
                session.delete(taskList);
                writer2.println("Foi apagado a TaskList " + splits[3]);
                writer2.close();
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
