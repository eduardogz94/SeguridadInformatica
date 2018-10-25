package org;

import javax.xml.ws.Endpoint;

import org.apache.catalina.LifecycleException;

import service.DatabaseManager;

public class Main {
	public static DatabaseManager s = new DatabaseManager();
	
	public static void main(String[] args) throws LifecycleException {
		String webService = "http://172.20.10.2:8080/Database_Service/services/DatabaseManager";
		Endpoint.publish(webService, s);
		System.out.println(webService);
		System.out.println("Database Manager Waiting For Requests");
	}
}