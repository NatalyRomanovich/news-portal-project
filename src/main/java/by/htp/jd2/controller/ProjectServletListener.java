package by.htp.jd2.controller;

import java.sql.Connection;

import by.htp.jd2.dao.connectionpool.ConnectionPool;
import by.htp.jd2.dao.connectionpool.ConnectionPoolException;
import by.htp.jd2.dao.connectionpool.DBParameter;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ProjectServletListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext ctx = servletContextEvent.getServletContext();
		ConnectionPool connectionPool = null;
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			// logger.log(Level.ERROR, "Connection not established ");
		}
		ctx.setAttribute("conPool", connectionPool);
		System.out.println("Database connection initialized for Application.");
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext ctx = servletContextEvent.getServletContext();

		ConnectionPool connectionPool = (ConnectionPool) ctx.getAttribute("conPool");
		try {
			ConnectionPool.getInstance().clearConnectionQueue();
		} catch (ConnectionPoolException e) {

			e.printStackTrace();
		}
	}
}
