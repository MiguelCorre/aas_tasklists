package org.ual.aas.tasklists.views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.List;
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
        Transaction transaction = null;
        String[] splits = req.getRequestURI().split("/");
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
                        writer.println(taskList.getName());

                    }
                    writer.close();
                }
                transaction.commit();

            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } else if (splits.length == 4) { // /lists/list_id/ --------------------------------------------------
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
                        writer2.println(task.getDescription() + " " + task.getStatus());
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
		
		String[] splits = req.getRequestURI().split("/");
		
                
		///tasklists/lists/4/67/",
		//
		//["","tasklists","lists","4","67"]
		
		if(splits.length == 3) {// /lists/
                    TaskListController.insertTaskLists("Testerino");
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
		} else if(splits.length == 4) { // /lists/list_id/
                    TaskController.insertTasks(parseInt(splits[3]), "Tarefa");
                    PrintWriter writer2 = resp.getWriter();
                        writer2.println("Foi adicionado " + splits[3]);
                        //System.out.println(bReader.toString());
                        writer2.close();
                    
                } else if(splits.length == 5) { // /lists/list_id/task_id/
                    
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
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getSessionFactory().close();
                
            }
        }
        
        @Override
        protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("application/json");
            String[] splits = req.getRequestURI().split("/");
            PrintWriter writer2 = resp.getWriter();
            writer2.println(splits[3]);
            writer2.close();
            
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