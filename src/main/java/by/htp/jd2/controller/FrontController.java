package by.htp.jd2.controller;

import java.io.IOException;
import java.sql.Connection;

import by.htp.jd2.dao.connectionpool.ConnectionPool;
import by.htp.jd2.dao.connectionpool.ConnectionPoolException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final CommandProvider provider = new CommandProvider();
    public ConnectionPool connectionPool;     
    
    public FrontController() {
        super();
    }

    @Override
    public void init () throws ServletException {
		super.init();
		try {							
				connectionPool = ConnectionPool.getInstance();						
		} catch (ConnectionPoolException e) {
			// logger.log(Level.ERROR, "Connection not established ");
		}   
	} 
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String commandName = request.getParameter("command");

		Command command = provider.getCommand(commandName);
		command.execute(request, response);
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
		
	@Override
    public void destroy() {
		connectionPool.clearConnectionQueue();
        super.destroy();
    }
}
