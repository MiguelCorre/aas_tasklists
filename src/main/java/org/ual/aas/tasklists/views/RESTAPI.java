package org.ual.aas.tasklists.views;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.ual.aas.tasklists.controllers.TaskListController;
import org.ual.aas.tasklists.models.TaskList;


@WebServlet("/lists/*")
public class RESTAPI extends HttpServlet {
    private static final long serialVersionUID = 1L;
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        resp.setContentType("application/json");
        //TaskListController.getTaskListsL();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            List<TaskList> list = null;

            TypedQuery<TaskList> query = session.createQuery("from TaskList");

            list = query.getResultList();
            PrintWriter writer = resp.getWriter();
            
            if (list.isEmpty()) {
                System.out.println("No tasklists available.");
            } else {
                for(TaskList taskList : list) {
                System.out.println(taskList.getName());
                writer.println(taskList.getName());
                
                }
            writer.println("{\"method\":\"GET\",\"value\":1, \"uri\":\""+req.getRequestURI()+"\"}");
            writer.close();
            }
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        
    }
    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("application/json");
		TaskListController.insertTaskLists("pOPO");
                PrintWriter writer = resp.getWriter();
                writer.println("FUNCIONOU");
            writer.close();
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