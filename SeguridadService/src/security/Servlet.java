package security;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.json.JSONObject;

import security.utilities.ServiceInterface;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Servlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		URL url = new URL("http://localhost:4000/database?wsdl");
		QName qname = new QName("http://utilities.security/", "ServiceService");
		Service service = Service.create(url, qname);
		ServiceInterface databaseManager = service.getPort(ServiceInterface.class);

		PrintWriter out = response.getWriter();
		String users = databaseManager.testConnection();
		out.print(users.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		
		URL url = new URL("http://localhost:4000/database?wsdl");
		QName qname = new QName("http://utilities.security/", "ServiceService");
		Service service = Service.create(url, qname);
		ServiceInterface databaseManager = service.getPort(ServiceInterface.class);


		String email = reqBody.getString("email");
		String pass = reqBody.getString("password");
		switch (databaseManager.add(email, pass)) {
		case 0:
			json.put("error", "Contrasena erronea o usuario no existe.");
			json.put("DatabaManager", databaseManager.show());
			break;
		case 1:
			json.put("message", "Autenticacion completada.");
			json.put("DatabaManager", databaseManager.show());
			break;
		}
		out.print(json.toString());
	}
}