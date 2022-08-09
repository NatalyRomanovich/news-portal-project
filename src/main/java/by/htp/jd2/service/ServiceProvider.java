package by.htp.jd2.service;

import by.htp.jd2.service.impl.NewsServiceImpl;
import by.htp.jd2.service.impl.UserServiceImpl;

public final class ServiceProvider {
	private static final ServiceProvider instance = new ServiceProvider();
	
	private ServiceProvider() {}
	
	private final UserService userService = new UserServiceImpl();
	private final NewsService newsService = new NewsServiceImpl();
	
	public NewsService getNewsService() {
		return newsService;
	}
	
	public UserService getUserService() {
		return userService;
	}
	
	public static ServiceProvider getInstance() {
		return instance;
	}
}
