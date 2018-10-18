package service;

import javax.jws.WebService;

@WebService
public interface DatabaseInterface {
		int add(String email, String pass);
		int user(String email, String pass);
		String show();
		String testConnection();
}
