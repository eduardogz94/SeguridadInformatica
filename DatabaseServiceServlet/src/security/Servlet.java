package security;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

import org.json.JSONObject;

import service.DatabaseInterface;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	protected static URL wsdlURL;
	protected static QName serviceName;
	protected static QName portName;
	protected static Endpoint endpoint;
	protected static String address;
	protected static Service database;
	protected static DatabaseInterface dbInterface;

	public static void setUp() throws MalformedURLException {
	    address = "http://localhost:8080/Database_Service/services/DatabaseManager";
	    wsdlURL = new URL(address + "?wsdl");
	    serviceName = new QName("http://service/", "DatabaseManagerService");
	    database = Service.create(wsdlURL, serviceName);
	    dbInterface = database.getPort(DatabaseInterface.class);
	}

	public Servlet()  {
		try {
			setUp();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		PrintWriter out = response.getWriter();		
		String users = dbInterface.testConnection();
		
		JSONObject res = new JSONObject();
		res.put("last service", users);
		
		out.print(res.toString());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));

		String email = reqBody.getString("email");
		String pass = reqBody.getString("password");
		switch (dbInterface.add(email, pass)) {
		case 0:
			json.put("error", "Contrasena erronea o usuario no existe.");
			json.put("DatabaManager", dbInterface.show());
			break;
		case 1:
			json.put("message", "Autenticacion completada.");
			json.put("DatabaManager", dbInterface.show());
			break;
		}
		out.print(json.toString());
	}
}