package security;


import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;


public class Main {
	
	static Servlet servlet = new Servlet();
	
	public static void main(String[] args) throws LifecycleException {
		Integer port = 3000;
		Tomcat tomcat = new Tomcat();
		Context ctxt = null;
		tomcat.setPort(port);
		ctxt = tomcat.addContext("/", new File(".").getAbsolutePath());
		Tomcat.addServlet(ctxt, "Database", servlet);
		ctxt.addServletMappingDecoded("/DatabaseManager", "Database");
		tomcat.start();
		tomcat.getServer().await();
	}
}