package ohjelma;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class YksinkertaisinMahdollinen {

	public static void main(String[] args) {
		Connection con = null; 
		ResultSet rs = null;
		PreparedStatement stmtPrep=null;		 
    	String path = System.getProperty("user.dir")+"/";    	
    	String url = "jdbc:sqlite:"+path+"Autot.sqlite";    	
    	try {	       
    		Class.forName("org.sqlite.JDBC");
	        con = DriverManager.getConnection(url);	
	        String sqlStr="SELECT * FROM autot";
	        stmtPrep = con.prepareStatement(sqlStr);        		
    		rs = stmtPrep.executeQuery();						
			while(rs.next()){
				System.out.print(rs.getString(1) +"\t\t");
				System.out.print(rs.getString(2)+"\t\t");
				System.out.print(rs.getString(3)+"\t\t");	
				System.out.print(rs.getInt(4)+"\t\t");	
				System.out.println();
			}			
			con.close();
	     }catch (Exception e){	
	    	 System.out.println("Yhteyden avaus epäonnistui.");
	        e.printStackTrace();	         
	     }    		
	}
}
