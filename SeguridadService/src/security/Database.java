package security;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import security.utilities.PropertiesReader;
import security.utilities.SingleDB;

public class Database {
	public ResultSet rs;
	private PreparedStatement pstmt;
	private Connection con;
	private PropertiesReader prop = PropertiesReader.getInstance();

	public Database() {
		try {
			SingleDB db = SingleDB.getInstance();
			Class.forName(db.getDriver());
			this.con = DriverManager.getConnection(db.getUrl(), db.getUsername(),
					db.getPassword());
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public boolean checkUser(String email, String password) {
		boolean state = false;
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_checkuser"));
			this.pstmt.setString(1, email);
			this.pstmt.setString(2, password);
			this.rs = pstmt.executeQuery();
			System.out.println(this.pstmt);
			state = this.rs.next();
		} catch (SQLException e) {
			System.out.println(e);
			e.getStackTrace();
		}
		return state;
	}

	public String test() {
		System.out.println("Testing!!!!");
		String password = "";
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_user"));
			this.pstmt.setString(1, "a");
			this.rs = pstmt.executeQuery();
			while (this.rs.next())
				password = this.rs.getString("password");
		} catch (SQLException e) {
			e.getStackTrace();
		}
		return password;
	}

	public void closeCon() {
		try {
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}