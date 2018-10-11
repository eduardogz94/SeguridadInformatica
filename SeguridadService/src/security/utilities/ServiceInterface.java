package security.utilities;

import javax.jws.WebService;

@WebService
public interface ServiceInterface {
		int add(String email, String pass);
		int user(String email, String pass);
		String show();
		String testConnection();
}
