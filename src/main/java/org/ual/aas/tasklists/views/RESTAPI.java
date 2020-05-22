package org.ual.aas.tasklists.views;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/lists/*")
public class RESTAPI extends HttpServlet {
    private static final long serialVersionUID = 1L;
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.println("{\"method\":\"GET\",\"value\":1, \"uri\":\""+req.getRequestURI()+"\"}");
        writer.close();
    }
    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.println("{\"method\":\"POST\",\"value\":1, \"uri\":\""+req.getRequestURI()+"\"}");
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