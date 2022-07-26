package by.htp.jd2.bean;

import com.google.common.base.Objects;

public class NewUserInfo {
	private String username;
	private String userSurname;
	private String login;
	private char[] password;
	private String email;
	private String role;
	private Integer id;

	public NewUserInfo() {

	}

	public NewUserInfo(String username, String userSurname, String login, char[] password, String email, String role) {

		this.username = username;
		this.userSurname = userSurname;
		this.login = login;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	public char[] getPassword() {
		return password;
	}

	public void getPassword(char[] password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		NewUserInfo that = (NewUserInfo) o;
		return Objects.equal(username, that.username) && Objects.equal(userSurname, that.userSurname)
				&& Objects.equal(login, that.login) && Objects.equal(email, that.email)
				&& Objects.equal(role, that.role);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(username, userSurname, login, password, email, role);
	}

	@Override
	public String toString() {
		return "NewUserInfo{" + "username='" + username + '\'' + ", userSurname='" + userSurname + '\'' + ", login='"
				+ login + '\'' + ", password=" + password + ", email='" + email + '\'' + ", role='" + role + '\'' + '}';
	}
}
