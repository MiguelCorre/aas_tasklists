package org.ual.aas.tasklists.views;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;

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


@WebServlet("/lists/*")
public class RESTAPI extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] splits = req.getRequestURI().split("/");
        Transaction transaction = null;
        if (splits.length == 3) {// /lists/ -----------------------------------------------------------------
            TaskListController.getTaskListsL(req, resp);
            
        } else if (splits.length == 4) { // /lists/list_id/ --------------------------------------------------
            TaskListController.getTaskLists(req, resp, parseInt(splits[3]));
            
        } else if (splits.length == 5) { // /lists/list_id/task_id/---------------------------------------------------------
            TaskController.getTasks(req, resp, parseInt(splits[4]));
            
        } else {
            PrintWriter writer2 = resp.getWriter();
            writer2 = resp.getWriter();
            writer2.println("404 - Not Found");
            writer2.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String[] splitsParam = req.getQueryString().split("=");
        String[] splits = req.getRequestURI().split("/");
        
        if (splits.length == 3) {// /lists/
            TaskListController.insertTaskLists(req, resp, splitsParam[1]);

        } else if (splits.length == 4) { // /lists/list_id/
            TaskController.insertTasks(req, resp, parseInt(splits[3]), splitsParam[1]);

        } else if (splits.length == 5) { // /lists/list_id/task_id/
            TaskController.updateStatusTasks(req, resp, parseInt(splits[4]), splitsParam[1]);

        } else {
            PrintWriter writer2 = resp.getWriter();
            writer2 = resp.getWriter();
            writer2.println("404 - Not Found");
            writer2.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String[] splits = req.getRequestURI().split("/");
        String[] splitsParam = req.getQueryString().split("=");

        if (splits.length == 4) {          
            TaskListController.updateTaskLists(req, resp, parseInt(splits[3]), splitsParam[1]);
            
        } else if (splits.length == 5) {        
            TaskController.updateNameTasks(req, resp, parseInt(splits[4]), splitsParam[1]);
            
        } else {
            PrintWriter writer2 = resp.getWriter();
            writer2 = resp.getWriter();
            writer2.println("404 - Not Found");
            writer2.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String[] splits = req.getRequestURI().split("/");

        if (splits.length == 4) {          
            TaskListController.deleteTaskLists(req, resp, parseInt(splits[3]));
            
        } else if (splits.length == 5) {
            TaskController.deleteTasks(req, resp, parseInt(splits[4]));
            
        } else {
            PrintWriter writer2 = resp.getWriter();
            writer2 = resp.getWriter();
            writer2.println("404 - Not Found");
            writer2.close();
        }
    }

    @Override // PLACEHOLDER SO PARA FECHAR A BASE DE DADOS.
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getSessionFactory().close();

        }
    }
}
