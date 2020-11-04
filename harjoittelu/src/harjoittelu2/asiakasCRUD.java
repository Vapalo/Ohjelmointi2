package harjoittelu2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import harjoittelu1.Lukija;

public class asiakasCRUD {
	
	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep = null;
	private String sql;
	private String db = "Myynti.sqlite";
	private Lukija lukija = new Lukija();
	
	private Connection yhdista() {
		Connection con = null;
		String path = System.getProperty("user.dir") + "/";
		String url = "jdbc:sqlite:" + path + db;
		
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(url);
			//System.out.print("Yhteys avattu");
		} catch (Exception e) {
			System.out.println("Yhteyden avaus epäonnistui");
			e.printStackTrace();
		}
		return con;
	}

	public static void main(String[] args) {
		

	}

}
