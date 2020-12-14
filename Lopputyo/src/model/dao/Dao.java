package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Vene;

public class Dao {

	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement prep = null;
	private String sql;
	private String db = "Venekanta.sqlite";

	private Connection yhdista() {
		Connection con = null;
		String path = "C:/Users/donit/Documents" + "/";
		String url = "jdbc:sqlite:" + path + db;

		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(url);

		} catch (Exception e) {
			System.out.println("Yhdist‰ess‰ jotain meni pieleen");
			e.printStackTrace();
		}

		return con;
	}

	public ArrayList<Vene> listaaKaikki() {
		ArrayList<Vene> veneet = new ArrayList<Vene>();
		sql = "SELECT * FROM veneet";

		try {
			con = yhdista();
			if (con != null) {
				prep = con.prepareStatement(sql);
				rs = prep.executeQuery();
				if (rs != null) {
					while (rs.next()) {
						Vene vene = new Vene();
						vene.setTunnus(rs.getInt(1));
						vene.setNimi(rs.getString(2));
						vene.setMerkkimalli(rs.getString(3));
						vene.setPituus(rs.getDouble(4));
						vene.setLeveys(rs.getDouble(5));
						vene.setHinta(rs.getInt(6));

						veneet.add(vene);

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return veneet;
	}

	public ArrayList<Vene> listaaKaikki(String hakusana) {
		ArrayList<Vene> veneet = new ArrayList<Vene>();
		sql = "SELECT * FROM veneet WHERE nimi LIKE ? OR merkkimalli LIKE ? OR pituus LIKE ? OR leveys LIKE ? OR hinta like ?";
		// SQL lauseke hakemista varten

		try {
			con = yhdista();
			if (con != null) {
				prep = con.prepareStatement(sql);
				prep.setString(1, "%" + hakusana + "%");
				prep.setString(2, "%" + hakusana + "%");
				prep.setString(3, "%" + hakusana + "%");
				prep.setString(4, "%" + hakusana + "%");
				prep.setString(5, "%" + hakusana + "%");
				rs = prep.executeQuery();

				if (rs != null) { // Jos haku tuotti tulosta
					while (rs.next()) {
						Vene vene = new Vene();
						vene.setTunnus(rs.getInt(1));
						vene.setNimi(rs.getString(2));
						vene.setMerkkimalli(rs.getString(3));
						vene.setPituus(rs.getDouble(4));
						vene.setLeveys(rs.getDouble(5));
						vene.setHinta(rs.getInt(6));
						// Lis‰t‰‰n veneelle b‰kk‰rist‰ haetut tiedot
						veneet.add(vene); // Lis‰t‰‰n vene arraylistaan
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return veneet;
	}

	public boolean poistaVene(int tunnus) {
		boolean paluuArvo = true;
		sql = "DELETE FROM veneet WHERE tunnus=?";

		try {
			con = yhdista();
			if (con != null) { // Jos yhteys onnistui
				prep = con.prepareStatement(sql);
				prep.setInt(1, tunnus); // Annetaan tunnus arvoksi kysymysmerkille
				prep.executeUpdate();
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			paluuArvo = false;
		}
		return paluuArvo;
	}

	public boolean lisaaVene(Vene vene) {
		boolean paluuArvo = true;
		sql = "INSERT INTO veneet(nimi, merkkimalli, pituus, leveys, hinta) VALUES(?,?,?,?,?)";
		// SQL lause arvojen lis‰‰miselle. Pit‰‰ erikseen kertoa
		// Mitk‰ arvot lis‰t‰‰n koska kaikkia arvoja ei lis‰t‰. autoincrementtaavaa
		// id:t‰ ei voida lis‰t‰

		try {
			con = yhdista();
			if (con != null) {
				prep = con.prepareStatement(sql);
				prep.setString(1, vene.getNimi());
				prep.setString(2, vene.getMerkkimalli());
				prep.setDouble(3, vene.getPituus());
				prep.setDouble(4, vene.getLeveys());
				prep.setInt(5, vene.getHinta());
				prep.executeUpdate();
				con.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			paluuArvo = false;
		}
		return paluuArvo;
	}
	
	public Vene etsiVene(int id) {
		Vene vene = null;
		sql = "SELECT * FROM veneet WHERE tunnus=?";
		
		try {
			con = yhdista();
			if (con != null) {
				prep = con.prepareStatement(sql);
				prep.setInt(1, id);
				rs = prep.executeQuery();
				
				if (rs.isBeforeFirst()) { //Jos kysely tuotti tulosta
				
					rs.next();
					vene = new Vene();
					vene.setNimi(rs.getString(2));
					vene.setMerkkimalli(rs.getString(3));
					vene.setPituus(rs.getDouble(4));
					vene.setLeveys(rs.getDouble(5));
					vene.setHinta(rs.getInt(6));
				}
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vene;
	}
	
	public boolean muutaVene(Vene vene, int id) {
		boolean paluuArvo = true;
		sql = "UPDATE veneet SET nimi=?, merkkimalli=?, pituus=?, leveys=?, hinta=? WHERE tunnus=?";
		
		try {
			con = yhdista();
			if (con != null) {
				prep = con.prepareStatement(sql);
				prep.setString(1, vene.getNimi());
				prep.setString(2, vene.getMerkkimalli());
				prep.setDouble(3, vene.getPituus());
				prep.setDouble(4, vene.getLeveys());
				prep.setInt(5, vene.getHinta());
				prep.setInt(6, id);
				prep.executeUpdate();
				con.close();
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			paluuArvo = false;
		}
		return paluuArvo;
	}

}
