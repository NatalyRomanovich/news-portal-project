package by.htp.jd2.dao.connectionpool;

import java.util.ResourceBundle;

public final class DBResourseManager {
	private static final DBResourseManager instance = new DBResourseManager();
	private static final String FILE_NAME = "db";

	private ResourceBundle bundle = ResourceBundle.getBundle(FILE_NAME);

	public static DBResourseManager getInstance() {
		return instance;
	}

	public String getValue(String key) {
		return bundle.getString(key);
	}
}
