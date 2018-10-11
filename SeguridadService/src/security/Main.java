package security;


import java.io.File;

import javax.xml.ws.Endpoint;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import security.utilities.Service;

public class Main {
	public static Service s = new Service();
	static Servlet servlet = new Servlet();
	
	public static void main(String[] args) throws LifecycleException {
		Integer port = 3000;
		Tomcat tomcat = new Tomcat();
		Context ctxt = null;
		String webService = "http://localhost:4000/database";
		tomcat.setPort(port);
		
		ctxt = tomcat.addContext("/", new File(".").getAbsolutePath());
		Tomcat.addServlet(ctxt, "Database", servlet);
		ctxt.addServletMappingDecoded("/DatabaseManager", "Database");
		
		Endpoint.publish(webService, s);
		
		tomcat.start();
		tomcat.getServer().await();
	}
}