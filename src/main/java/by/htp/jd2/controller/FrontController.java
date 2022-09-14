package by.htp.jd2.controller;

import java.io.IOException;
import java.util.Enumeration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.jd2.dao.connectionpool.ConnectionPool;
import by.htp.jd2.dao.connectionpool.ConnectionPoolException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	private static final String COMMAND = "command";

	private final CommandProvider provider = new CommandProvider();

	public FrontController() {
		super();
	}

	protected void doLogic(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String commandName = request.getParameter(COMMAND);
		Command command = provider.getCommand(commandName);
		command.execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doLogic(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doLogic(request, response);
	}	
}
