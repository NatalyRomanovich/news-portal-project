package by.htp.jd2.controller;

import java.io.IOException;
import java.util.Enumeration;

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

	/*
	 * @Override public void init() throws ServletException { try {
	 * ConnectionPool.getInstance(); } catch (ConnectionPoolException e) { //
	 * logger.log(Level.ERROR, "Connection not established "); } }
	 */

	protected void doLogic(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession(true).setAttribute(AttributsKey.LOCAL, request.getParameter(AttributsKey.LOCAL));

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

	@Override
	public void destroy() {
		try {
			ConnectionPool.getInstance().clearConnectionQueue();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
	}
}
