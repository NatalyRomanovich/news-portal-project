package by.htp.jd2.controller;

import java.util.HashMap;
import java.util.Map;

import by.htp.jd2.controller.impl.AddNews;
import by.htp.jd2.controller.impl.ChangeLanguage;
import by.htp.jd2.controller.impl.CompletelyDeleteNewsFromDB;
import by.htp.jd2.controller.impl.DeleteNews;
import by.htp.jd2.controller.impl.DoLogIn;
import by.htp.jd2.controller.impl.DoLogOut;
import by.htp.jd2.controller.impl.DoRegistration;
import by.htp.jd2.controller.impl.EditNews;
import by.htp.jd2.controller.impl.GoToBasePage;
import by.htp.jd2.controller.impl.GoToDoAdminsAction;
import by.htp.jd2.controller.impl.GoToNewsList;
import by.htp.jd2.controller.impl.GoToRegistrationPage;
import by.htp.jd2.controller.impl.GoToViewNews;

public class CommandProvider {
	private Map<CommandName, Command> commands = new HashMap<>();

	public CommandProvider() {
		commands.put(CommandName.GO_TO_BASE_PAGE, new GoToBasePage());
		commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPage());
		commands.put(CommandName.DO_REGISTRATION, new DoRegistration());
		commands.put(CommandName.DO_LOG_IN, new DoLogIn());
		commands.put(CommandName.DO_LOG_OUT, new DoLogOut());
		commands.put(CommandName.GO_TO_NEWS_LIST, new GoToNewsList());
		commands.put(CommandName.GO_TO_VIEW_NEWS, new GoToViewNews());
		commands.put(CommandName.ADD_NEWS, new AddNews());
		commands.put(CommandName.DELETE_NEWS, new DeleteNews());
		commands.put(CommandName.COMPLETELY_DELETE_NEWS, new CompletelyDeleteNewsFromDB());
		commands.put(CommandName.EDIT_NEWS, new EditNews());
		commands.put(CommandName.GO_TO_DO_ACTION, new GoToDoAdminsAction());
		commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguage()); 
	}

	public Command getCommand(String name) {
		CommandName commandName = CommandName.valueOf(name.toUpperCase());
		Command command = commands.get(commandName);
		return command;
	}
}
