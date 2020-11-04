package harjoittelu2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import harjoittelu1.Lukija;
import jdk.nashorn.internal.runtime.ECMAErrors;

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
		sql = "SELECT * FROM asiakkaat";
		try {
			con = yhdista();
			if (con != null) {// Jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);
				rs = stmtPrep.executeQuery();

				if (rs != null) { // Jos kysely tuotti tulosta
					System.out.println();

					while (rs.next()) {
						System.out.print(rs.getString(1) + "\t\t");
						System.out.print(rs.getString(2) + "\t\t");
						System.out.print(rs.getString(3) + "\t\t");
						System.out.print(rs.getString(4) + "\t\t");
						System.out.println();

					}
					System.out.println();
				}
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void lisaaAsiakas() {
		String etunimi = lukija.lueTeksti("Anna lisättävän asiakkaan etunimi: ");
		String sukunimi = lukija.lueTeksti("Anna lisättävän asiakkaan sukunimi: ");
		String puhelin = lukija.lueTeksti("Anna lisättävän asiakkaan puhelinnumero: ");
		String sposti = lukija.lueTeksti("Anna lisättävän asiakkaan sähköposti: ");

		if (etunimi.length() > 1 && sukunimi.length() > 1 && puhelin.length() > 1 && sposti.length() > 1) {
			sql = "INSERT INTO asiakkaat (etunimi, sukunimi, puhelin, sposti) VALUES (?,?,?,?)";

			try {
				con = yhdista();
				stmtPrep = con.prepareStatement(sql);
				stmtPrep.setString(1, etunimi);
				stmtPrep.setString(2, sukunimi);
				stmtPrep.setString(3, puhelin);
				stmtPrep.setString(4, sposti);
				stmtPrep.executeUpdate();

				con.close();

				System.out.println("Asiakkaan lisääminen onnistui");
				listaaAsiakas();
			} catch (SQLException e) {
				System.out.println("Asiakkaan lisääminen epäonnistui");
				e.printStackTrace();
			}
		}
	}

	public void muutaAsiakas() {
		listaaAsiakas();

		String etunimi = lukija.lueTeksti("Anna muutettavan asiakkaan etunimi: ");
		String sukunimi = lukija.lueTeksti("Anna muutettavan asiakkaan sukunimi: ");

		sql = "SELECT * FROM asiakkaat WHERE etunimi = ? && sukunimi = ?";

		try {
			con = yhdista();
			if (con != null) {
				stmtPrep = con.prepareStatement(sql);
				stmtPrep.setString(1, etunimi);
				stmtPrep.setString(2, sukunimi);
				rs = stmtPrep.executeQuery();

				if (rs.isBeforeFirst()) { // Jos kyselyllä löytyi tietoa
					String uusienimi = lukija.lueTeksti("Anna uusi etunimi (enter ohittaa): ");
					String uusisnimi = lukija.lueTeksti("Anna uusi sukunimi (Enter ohittaa): ");
					String puhelin = lukija.lueTeksti("Anna uusi puhelinnumero (Enter ohittaa): ");
					String sposti = lukija.lueTeksti("Anna uusi sähköposti (Enter ohittaa): ");

					if (uusienimi.equals("")) {
						uusienimi = etunimi;
					}
					if (uusisnimi.equals("")) {
						uusisnimi = sukunimi;
					}
					if (puhelin.equals("")) {
						puhelin = rs.getString("puhelin");
					}
					if (sposti.contentEquals("")) {
						sposti = rs.getString("sposti");
					}

					sql = "UPDATE asiakkaat SET etunimi = ?, sukunimi = ?, puhelin = ?, sposti = ? WHERE etunimi = ? && sukunimi = ?";

					stmtPrep = con.prepareStatement(sql);
					stmtPrep.setString(1, uusienimi);
					stmtPrep.setString(2, uusisnimi);
					stmtPrep.setString(3, puhelin);
					stmtPrep.setString(4, sposti);
					stmtPrep.setString(5, etunimi);
					stmtPrep.setString(6, sukunimi);
					stmtPrep.executeUpdate();
				} else {
					System.out.println("Antamaasi henkilöä ei ole tietokannassa\n");
				}
				con.close();
				listaaAsiakas();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		asiakasCRUD ohj = new asiakasCRUD();
		ohj.naytaValikko();

	}

}
