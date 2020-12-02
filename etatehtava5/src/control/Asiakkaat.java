package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import model.Asiakas;
import model.dao.Dao;

@WebServlet("/asiakkaat/*")
public class Asiakkaat extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Asiakkaat() {
		super();
		System.out.println("Asiakkaat.Asiakkaat()");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Asiakkaat.doGet()");
		String pathInfo = request.getPathInfo(); //Tässä otetaan vastaan jsp documentin pyynt�, eli syötetty hakusana
		System.out.println("polku: " + pathInfo);
		
		Dao dao = new Dao();
		ArrayList<Asiakas> asiakkaat;  //Luodaan arraylisti johon asiakkaat syötetään
		String strJSON = "";
		
		if(pathInfo==null) { //Haetaan kaikki asiakkaat jos pathInfo, eli hakusana on tyhjä
			asiakkaat = dao.listaaKaikki();
			strJSON = new JSONObject().put("asiakkaat", asiakkaat).toString();
			
		} else if (pathInfo.indexOf("haeyksi") != -1) { //Jos polusta löytyy haeyksi, eli haetaan vain yhden asiakkaan tiedot
			
			String asiakas_idstr = pathInfo.replace("/haeyksi/", ""); //Poistetaan /haeyksi/ jotta saadaan urli oikeaan muotoon asiakasidn kanssa
			int asiakas_id = Integer.parseInt(asiakas_idstr);
			Asiakas asiakas = dao.etsiAsiakas(asiakas_id);
			
			JSONObject JSON = new JSONObject(); //Luodaan uusi JSON objekti ja syötetään sille asiakas objektista haetut tiedot
			JSON.put("asiakas_id", asiakas_id); //Annetaan asiakkaan id json stringille, jotta muutaAsiakas löytää asiakkaan id:n jonka perusteella
			JSON.put("etunimi", asiakas.getEtunimi()); //muutoksia asiakkaaseen tehdään
			JSON.put("sukunimi", asiakas.getSukunimi());
			JSON.put("puhelin", asiakas.getPuhelin());
			JSON.put("sposti", asiakas.getSposti());
			strJSON = JSON.toString();
			
		} else {
			String hakusana = pathInfo.replace("/", ""); //Tässä poistetaan kauttaviiva hakusanan edestä
			asiakkaat = dao.listaaKaikki(hakusana); //Laitetaan hakusanan mukaiset asiakkaat arraylistiin
			strJSON = new JSONObject().put("asiakkaat", asiakkaat).toString();
		}
	
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(strJSON);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Asiakkaat.doPost()");
		JSONObject jsonObj = new JsonStrToObj().convert(request);
		Asiakas asiakas = new Asiakas();
		asiakas.setEtunimi(jsonObj.getString("etunimi"));
		asiakas.setSukunimi(jsonObj.getString("sukunimi"));
		asiakas.setPuhelin(jsonObj.getString("puhelin"));
		asiakas.setSposti(jsonObj.getString("sposti"));
		
		PrintWriter out = response.getWriter();	
		response.setContentType("application/json");
		Dao dao = new Dao();
		if(dao.lisaaAsiakas(asiakas)) { //Tämä metodi palauttaa true tai false, eli 1 tai 0
			out.println("{ \"response\":1 }"); //Asiakkaan lisääminen onnistui
		} else {
			out.println("{ \"response\":0}"); //Asiakkaan lisääminen epäonnistui
		}
		
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Asiakkaat.doPut()");
		JSONObject jsonObj = new JsonStrToObj().convert(request);
		Asiakas asiakas = new Asiakas();
		
		System.out.println(jsonObj);
		
		int asiakas_id = Integer.parseInt((String) jsonObj.get("asiakasid")); //Muutetaan asiakkaan id numeraaliseksi json stringistä
		//int asiakas_id= Integer.parseInt(asiakas_idstr);

		
		
		asiakas.setEtunimi(jsonObj.getString("etunimi")); //Luodaan json objekti käyttäjän antamilla tiedoilla
		asiakas.setSukunimi(jsonObj.getString("sukunimi"));
		asiakas.setPuhelin(jsonObj.getString("puhelin"));
		asiakas.setSposti(jsonObj.getString("sposti"));
		
		PrintWriter out = response.getWriter();	
		response.setContentType("application/json");
		Dao dao = new Dao();
		if(dao.muutaAsiakas(asiakas, asiakas_id)) { //viedään sekä muutettava id että luotu json objekti eteenpäin daolle muutettavaksi
			out.println("{ \"response\":1 }"); //Asiakkaan muutos onnistui
		} else {
			out.println("{ \"response\":0}"); //Asiakkaan muutos epäonnistui
		}
		
	}
		
	

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Asiakkaat.doDelete()");
		String pathInfo = request.getPathInfo();
		System.out.println("polku:" + pathInfo);
		String poistettavaNimi = pathInfo.replace("/", "");
		int poistettavaID = Integer.parseInt(poistettavaNimi);
		Dao dao = new Dao();
		
		PrintWriter out = response.getWriter();	
		response.setContentType("application/json");
		
		if(dao.poistaAsiakas(poistettavaID)) { //Tämä metodi palauttaa true tai false, eli 1 tai 0
			out.println("{ \"response\":1 }"); //Asiakkaan poisto onnistui
		} else {
			out.println("{ \"response\":0}"); //Asiakkaan poisto epäonnistui
		}
		
		
	}

}