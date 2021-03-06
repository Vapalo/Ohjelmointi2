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

import model.Vene;
import model.dao.Dao;

/**
 * Servlet implementation class Veneet
 */
@WebServlet("/veneet/*")
public class Veneet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Veneet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Veneet.doGet()");
		String pathInfo = request.getPathInfo();
		System.out.println("polku: " + pathInfo);

		Dao dao = new Dao();
		ArrayList<Vene> veneet; // Tyhj� arraylisti b�kk�rist� saatuja tietoja varten
		String strJSON = "";

		if (pathInfo == null) {
			veneet = dao.listaaKaikki();
			strJSON = new JSONObject().put("veneet", veneet).toString();

		} else if (pathInfo.indexOf("haeyksi") != -1) { // Jos haetaan vain yksi
			String tunnusString = pathInfo.replace("/haeyksi/", "");
			int tunnus = Integer.parseInt(tunnusString);
			Vene vene = dao.etsiVene(tunnus);
			// Etsit��n tunnuksen perusteella muutettava vene

			JSONObject JSON = new JSONObject();
			JSON.put("tunnus", tunnus);
			JSON.put("nimi", vene.getNimi());
			JSON.put("merkkimalli", vene.getMerkkimalli());
			JSON.put("pituus", vene.getPituus());
			JSON.put("leveys", vene.getLeveys());
			JSON.put("hinta", vene.getHinta());
			strJSON = JSON.toString();

		} else {

			String hakusana = pathInfo.replace("/", "");
			veneet = dao.listaaKaikki(hakusana);
			strJSON = new JSONObject().put("veneet", veneet).toString();
		}

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(strJSON);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Veneet.doPost()");

		JSONObject jsonObj = new JsonStrToObj().convert(request);
		// Muutetaan html pyynn�n sis�lt� jsoniksi
		Vene vene = new Vene(); // Tyhj� vene johon json laitetaan

		vene.setNimi(jsonObj.getString("nimi"));
		vene.setMerkkimalli(jsonObj.getString("merkkimalli"));
		vene.setPituus(jsonObj.getDouble("pituus"));
		vene.setLeveys(jsonObj.getDouble("leveys"));
		vene.setHinta(jsonObj.getInt("hinta"));

		PrintWriter out = response.getWriter();
		Dao dao = new Dao();

		if (dao.lisaaVene(vene)) {
			out.println("{\"response\": 1}");
		} else {
			out.println("{ \"response\": 0}");
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Veneet.doPut()");
		JSONObject jsonObj = new JsonStrToObj().convert(request); //Muutetaan http pyynt� jsoniksi
		Vene vene = new Vene();
		
		int tunnus = Integer.parseInt((String) jsonObj.get("tunnus"));
		//Parsesoidaan veneen tunnus kokonaisluvuksi merkkijonosta
		
		vene.setNimi(jsonObj.getString("nimi"));
		vene.setMerkkimalli(jsonObj.getString("merkkimalli"));
		vene.setPituus(jsonObj.getDouble("pituus"));
		vene.setLeveys(jsonObj.getDouble("leveys"));
		vene.setHinta(jsonObj.getInt("hinta"));
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		
		Dao dao = new Dao();
		
		if (dao.muutaVene(vene, tunnus)) {
			out.println("{ \"response\": 1}"); //Muutos onnistui
		} else {
			out.println("{ \"response\": 0}"); //Muutos epaonnistui
		}
		
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Veneet.doDelete()");
		String pathInfo = request.getPathInfo();
		System.out.println("polku: " + pathInfo);
		String poistettavaStr = pathInfo.replace("/", "");
		int poistettavaID = Integer.parseInt(poistettavaStr);

		Dao dao = new Dao();
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");

		if (dao.poistaVene(poistettavaID)) {
			out.println("{ \"response\":1 }"); // Palauttaa 1 jos onnistuu
		} else {
			out.println("{ \"response\":0 }"); // Palauttaa 0 jos ei onnistu
		}
	}

}
