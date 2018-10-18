package service;

import java.util.ArrayList;
import javax.jws.WebService;
import service.Database;

@WebService(endpointInterface = "service.DatabaseInterface")

public class DatabaseManager implements DatabaseInterface {
    public Database db = new Database();
    public ArrayList<String> arr = new ArrayList<String>();
    public ArrayList<String> users = new ArrayList<String>();

    public int add(String email, String pass) {
        arr.add(email);
        System.out.println("Data History: " + arr);
        System.out.println("New User: " + email + " " + pass);
        int value = user(email, pass);
        return value;
    }

    public String show() {
        return "Recent Emails: " + arr;
    }

    public int user(String email, String pass) {
        int value;
        if (db.checkUser(email, pass) == true) {
            value = 1;
        } else {
            value = 0;
        }
        return value;
    }

    public String testConnection() {
    	System.out.println("Testing Database Function");
        String user = db.test();
        return user;
    }
}