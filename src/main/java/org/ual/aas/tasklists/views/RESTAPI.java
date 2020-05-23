package org.ual.aas.tasklists.views;

import java.io.BufferedReader;
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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.ual.aas.tasklists.HibernateUtil;
import org.ual.aas.tasklists.controllers.TaskController;
import org.ual.aas.tasklists.controllers.TaskListController;
import org.ual.aas.tasklists.models.Task;
import org.ual.aas.tasklists.models.TaskList;


@WebServlet("/lists/*")
public class RESTAPI extends HttpServlet {
    private static final long serialVersionUID = 1L;
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {       
        resp.setContentType("application/json");
        TaskListController.getTaskListsL();
        int x = 0;
        Transaction transaction = null;
        String[] splits = req.getRequestURI().split("/");
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        if(splits.length == 3) {// /lists/ -----------------------------------------------------------------
            try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
                session.getSessionFactory().openSession();
                transaction = session.beginTransaction();

                List<TaskList> list = null;

                TypedQuery<TaskList> query = session.createQuery("from TaskList");

                list = query.getResultList();
                PrintWriter writer = resp.getWriter();

                if (list.isEmpty()) {
                    System.out.println("No tasklists available.");
                } else {
                    for (TaskList taskList : list) {
                        System.out.println(taskList.getName());
                        jsonBuilder.add("list"+x, taskList.getName());
                        writer.println(taskList.getName());
                        x++;
                    }
                    JsonObject empObj = jsonBuilder.build();
                    writer.println(empObj.size());
                    StringWriter strWtr = new StringWriter();
                    JsonWriter jsonWtr = Json.createWriter(strWtr);
                    jsonWtr.writeObject(empObj);
                    writer.println(strWtr.toString());
                    jsonWtr.close();
                            
                    writer.close();
                }
                
                transaction.commit();

            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } else if (splits.length == 4) { // /lists/list_id/ -------------------------------------------------- METER JSON
            try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
                // start a transaction
                transaction = session.beginTransaction();

                TaskList taskList = session.get(TaskList.class, parseInt(splits[3]));
                PrintWriter writer2 = resp.getWriter();
                if (taskList.getTasks().isEmpty()) {
                    writer2.println("No tasks available in " + taskList.getName());
                } else {                 
                    writer2.println("List " + taskList.getName() + ":");
                    for (Task task : taskList.getTasks()) {
                        writer2.println(task.getDescription() + " " + task.getStatus()+ " ID: "+ task.getId());
                    }
                    writer2.close();
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }       
                } else if(splits.length == 5) { // /lists/list_id/task_id/---------------------------------------------------------
                    
                } else {
                    // ERRO.
                }
        
        PrintWriter writer2 = resp.getWriter();
        writer2.println("{\"method\":\"GET\",\"value\":1, \"uri\":\""+req.getRequestURI()+"\",\"splits\":}");
        writer2.close();
        
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TaskListController controller = new TaskListController();
        resp.setContentType("application/json");
        String[] splitsParam = req.getQueryString().split("=");
        String[] splits = req.getRequestURI().split("/");

        ///tasklists/lists/4/67/",
        //
        //["","tasklists","lists","4","67"]
        if (splits.length == 3) {// /lists/
            TaskListController.insertTaskLists(splitsParam[1]);
            PrintWriter writer = resp.getWriter();
            writer.println("Foi inserido a lista.");
            writer.close();
            // Ferramentas: javax.json ou gson
            // JSON -> Strings -> Objetos -> Strings -> JSON
            // TODO: Obter o nome da lista que está no json do request 
//			BufferedReader bReader = req.getReader();
//			String listName = "" + bReader;
//                        PrintWriter writer = resp.getWriter();
//                        writer.println(bReader.toString());
//                        //System.out.println(bReader.toString());
//                        writer.close();

            // criar uma lista
            //TaskListController.insertTaskLists(listName);
            // TODO: responder com o id da lista criada.
        } else if (splits.length == 4) { // /lists/list_id/
            TaskController.insertTasks(parseInt(splits[3]), splitsParam[1]);
            PrintWriter writer2 = resp.getWriter();
            writer2.println("Foi adicionado " + splitsParam[1]);
            //System.out.println(bReader.toString());
            writer2.close();

        } else if (splits.length == 5) { // /lists/list_id/task_id/
            PrintWriter writer2 = resp.getWriter();
            TaskController.updateStatusTasks(parseInt(splits[4]), splitsParam[1]);
            writer2.println("Status alterado para " + splitsParam[1]);
            writer2.close();
        } else {
            // ERRO.
        }
//		else if POST /lists/list_id/
//		
//		else if POST /lists/list_id/task_id/
//		
//		else
//			404
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String[] splits = req.getRequestURI().split("/");
        String[] splitsParam = req.getQueryString().split("=");

        if (splits.length == 4) {
            PrintWriter writer2 = resp.getWriter();
            TaskListController.updateTaskLists(parseInt(splits[3]), splitsParam[1]);
            writer2.println("Nome alterado para " + splitsParam[1]);
            writer2.close();
        } else if (splits.length == 5) {
            PrintWriter writer2 = resp.getWriter();
            TaskController.updateNameTasks(parseInt(splits[4]), splitsParam[1]);
            writer2.println("Descrição alterada para " + splitsParam[1]);
            writer2.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String[] splits = req.getRequestURI().split("/");

        if (splits.length == 4) {
            PrintWriter writer2 = resp.getWriter();
            TaskListController.deleteTaskLists(parseInt(splits[3]));
            writer2.println("Foi apagado a TaskList " + splits[3]);
            writer2.close();
        } else if (splits.length == 5) {
            PrintWriter writer2 = resp.getWriter();
            TaskController.deleteTasks(parseInt(splits[4]));
            writer2.println("Foi apagado a Task " + splits[4]);
            writer2.close();

        }
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getSessionFactory().close();

        }
    }

    // Para dados de exemplo
    private class Data {
    	private String name;
    	private Double value;
    	
		public Data(String name, Double value) {
			super();
			this.name = name;
			this.value = value;
		}
		
		public String getName() {
			return name;
		}
		
		public Double getValue() {
			return value;
		}
    }
}