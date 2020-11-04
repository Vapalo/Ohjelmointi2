package ohjelma;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutoOhjelma {

	private Connection con=null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep=null; 
	private String sql;
	private String db ="Autot.sqlite";
	private Lukija lukija = new Lukija();
	
	private Connection yhdista(){		
    	Connection con = null;    	
    	String path = System.getProperty("user.dir")+"/";    	
    	String url = "jdbc:sqlite:"+path+db;    	
    	try {	       
    		Class.forName("org.sqlite.JDBC");
	        con = DriverManager.getConnection(url);	
	        //System.out.println("Yhteys avattu.");
	     }catch (Exception e){	
	    	 System.out.println("Yhteyden avaus epäonnistui.");
	        e.printStackTrace();	         
	     }
	     return con;
	}
	/*
	private void testaaYhteys() {
		con = yhdista();
		try {
			con.close();
			System.out.println("Yhteys suljettu.");
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	*/
	
	private void naytaValikko() {
		System.out.println("1. Näytä kaikki autot");
		System.out.println("2. Lisää auto");
		System.out.println("3. Muuta autoa");
		System.out.println("4. Poista auto");
		System.out.println("0. Lopeta");
		switch (lukija.lueKokonaisluku("Valintasi: ")) {
		case 1:
			listaaAutot();
			break;
		case 2:
			lisaaAuto();
			break;
		case 3:
			muutaAuto();
			break;
		case 4:
			poistaAuto();
			break;
		case 0:
			System.exit(0);
			break;
		default:
			System.out.println("Väärä valinta!");
			break;
		}
		naytaValikko();		
	}
	
	private void listaaAutot(){
		sql = "SELECT * FROM autot";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui					
					System.out.println();
					while(rs.next()){
						System.out.print(rs.getString(1) +"\t\t");
						System.out.print(rs.getString(2)+"\t\t");
						System.out.print(rs.getString(3)+"\t\t");	
						System.out.print(rs.getInt(4)+"\t\t");	
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
	
	private void lisaaAuto() {
		String rekno = lukija.lueTeksti("Anna lisättävän auton rekisterinumero: ");
		String merkki = lukija.lueTeksti("Anna lisättävän auton merkki: ");
		String malli = lukija.lueTeksti("Anna lisättävän auton malli: ");
		int vuosi = lukija.lueKokonaisluku("Anna lisättävän auton valmistusvuosi: ");
		if(rekno.length()>1 && merkki.length()>1 && malli.length()>1){
			sql="INSERT INTO autot VALUES(?,?,?,?)";						  
			try {
				con = yhdista();
				stmtPrep=con.prepareStatement(sql); 
				stmtPrep.setString(1, rekno.toUpperCase());
				stmtPrep.setString(2, merkki);
				stmtPrep.setString(3, malli);
				stmtPrep.setInt(4, vuosi);
				stmtPrep.executeUpdate();
		        con.close();
		        System.out.println("Auton lisääminen onnistui.");
		        listaaAutot();
			} catch (SQLException e) {	
				System.out.println("Auton lisääminen epäonnistui.");
				e.printStackTrace();
			}
		}		
	}
	
	private void muutaAuto() {
		listaaAutot();
		String rekno = lukija.lueTeksti("Anna muutettavan auton rekisterinumero: ").toUpperCase();
		sql = "SELECT * FROM autot WHERE rekno=?";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setString(1, rekno);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa, eli id on käytössä
        			String uusirekno = lukija.lueTeksti("Anna uusi rekisterinumero (enter ohittaa): ");
        			String merkki = lukija.lueTeksti("Anna uusi merkki (enter ohittaa): ");
        			String malli = lukija.lueTeksti("Anna uusi malli (enter ohittaa): ");
        			String vuosiStr = lukija.lueTeksti("Anna uusi valmistusvuosi (enter ohittaa): ");
        			int vuosi=0;
					if(uusirekno.equals("")) {
						uusirekno=rekno;
					}
					if(merkki.equals("")) {
						merkki=rs.getString("merkki");
					}
					if(malli.equals("")) {
						malli=rs.getString("malli");
					}
					if(vuosiStr.equals("")) {
						vuosi=rs.getInt("vuosi");
					}else {
						try {
							vuosi = Integer.parseInt(vuosiStr);								
						} catch (Exception e) {
							System.out.println("Antamasi arvo ei ole kokonaisluku");
							vuosi = lukija.lueKokonaisluku("Anna uusi valmistusvuosi (enter ohittaa): ");
						}										
					}
					sql="UPDATE autot SET rekno=?, merkki=?, malli=?, vuosi=? WHERE rekno=?";	        				
					stmtPrep=con.prepareStatement(sql);
					stmtPrep.setString(1, uusirekno.toUpperCase());
					stmtPrep.setString(2, merkki);
					stmtPrep.setString(3, malli);
					stmtPrep.setInt(4, vuosi);
					stmtPrep.setString(5, rekno);
					stmtPrep.executeUpdate();					
				}else{
					System.out.println("Antamasi rekisterinumero ei ole käytössä!\n");
				}
        		con.close();
    			listaaAutot();
			}				
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void poistaAuto() {
		listaaAutot();
		String rekno = lukija.lueTeksti("Anna poistettavan auton rekisterinumero: ").toUpperCase();
		sql = "SELECT * FROM autot WHERE rekno=?";      
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setString(1, rekno);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa, eli rekisterinumero on käytössä
        			rs.next(); //siirrytään 1. tietueriville
        			if(lukija.lueTeksti("Haluatko varmasti poistaa auton "
        					+rekno+" "+rs.getString("merkki")+" "+rs.getString("malli")
        					+ "(k/e): ").equalsIgnoreCase("k")){        				
        				sql="DELETE FROM autot WHERE rekno=?";	        				
    					stmtPrep=con.prepareStatement(sql);     					
    					stmtPrep.setString(1, rekno);
    					System.out.println(rekno);    					
    					stmtPrep.executeUpdate(); 
        			}	       			
				}else{
					System.out.println("Antamasi rekisterinumero ei ole käytössä!\n");
				}				
			}	
			con.close();
			listaaAutot();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}
		
	public static void main(String[] args) {
		AutoOhjelma ohjelma = new AutoOhjelma();
		ohjelma.naytaValikko();	
	}

}
