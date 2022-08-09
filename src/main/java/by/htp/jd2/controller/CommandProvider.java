package by.htp.jd2.controller;

import java.util.HashMap;
import java.util.Map;

import by.htp.jd2.controller.impl.DoLogIn;
import by.htp.jd2.controller.impl.DoLogOut;
import by.htp.jd2.controller.impl.DoRegistration;
import by.htp.jd2.controller.impl.GoToBasePage;
import by.htp.jd2.controller.impl.GoToNewsList;
import by.htp.jd2.controller.impl.GoToRegistrationPage;
import by.htp.jd2.controller.impl.GoToViewNews;

public class CommandProvider {
	private Map<CommandName, Command> commands = new HashMap<>();
	
	public CommandProvider() {
		commands.put(CommandName.GO_TO_BASE_PAGE, new GoToBasePage());
		commands.put(CommandName.GO_TO_REGISTRATION_PAGE,new GoToRegistrationPage());
		commands.put(CommandName.DO_REGISTRATION, new DoRegistration());
		commands.put(CommandName.DO_LOG_IN, new DoLogIn());
		commands.put(CommandName.DO_LOG_OUT, new DoLogOut());
		commands.put(CommandName.GO_TO_NEWS_LIST, new GoToNewsList());
		commands.put(CommandName.GO_TO_VIEW_NEWS, new GoToViewNews());
	}
	
	public Command getCommand(String name) {
		CommandName  commandName = CommandName.valueOf(name.toUpperCase());
		Command command = commands.get(commandName);
		return command;
	}
}
