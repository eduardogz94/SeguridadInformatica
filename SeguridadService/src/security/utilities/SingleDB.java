package security.utilities;

public class SingleDB {
		private PropertiesReader prop = PropertiesReader.getInstance();
		private String username = prop.getValue("dbUser");
		private String password = prop.getValue("dbPassword");
		private String url = prop.getValue("dbUrl");
		private String driver = prop.getValue("dbDriver");

			
		private static SingleDB instance = new SingleDB();
		
		public static SingleDB getInstance(){
			return instance;
		}
		
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getDriver() {
			return driver;
		}

		public void setDriver(String driver) {
			this.driver = driver;
		}
	}
