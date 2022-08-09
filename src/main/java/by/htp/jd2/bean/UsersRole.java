package by.htp.jd2.bean;

public enum UsersRole {
	USER ("user"),
	ADMIN ("admin"),
	GUEST ("guest"),
	UNKNOWN ("unkown");
	
	private String title;
	
	UsersRole(String title) {
	       this.title = title;
	   }

	   public String getTitle() {
	       return title;
	   }

	   @Override
	   public String toString() {
	       return "ClientsRole{" +
	               "title='" + title + '\'' +
	               '}';
	   }
}

