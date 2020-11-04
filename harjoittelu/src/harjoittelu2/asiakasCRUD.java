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
			// System.out.print("Yhteys avattu");
		} catch (Exception e) {
			System.out.println("Yhteyden avaus epäonnistui");
			e.printStackTrace();
		}
		return con;
	}

	private void naytaValikko() {
		System.out.println("\n1. Näytä kaikki asiakkaat");
		System.out.println("2. Lisää asiakas");
		System.out.println("3. Muuta asiakasta");
		System.out.println("4. Poista asiakas");
		System.out.println("0. Lopeta");

		switch (lukija.lueKokonaisluku("Valintasi: ")) {
		case 1:
			listaaAsiakas();
			break;
		case 2:
			lisaaAsiakas();
			break;
		case 3:
			muutaAsiakas();
			break;
		case 4:
			poistaAsiakas();
			break;
		case 0:
			System.exit(0);
			break;
		default:
			System.out.println("Virheellinen valinta");
			break;
		}
		naytaValikko();
	}

	private void listaaAsiakas() {
		
	}

	public static void main(String[] args) {
		asiakasCRUD ohj = new asiakasCRUD();
		ohj.naytaValikko();

	}

}