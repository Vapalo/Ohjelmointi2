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
		ArrayList<Vene> veneet; // Tyhjä arraylisti bäkkäristä saatuja tietoja varten
		String strJSON = "";

		if (pathInfo == null) {
			veneet = dao.listaaKaikki();
			strJSON = new JSONObject().put("veneet", veneet).toString();
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
		// Muutetaan html pyynnön sisältö jsoniksi
		Vene vene = new Vene(); // Tyhjä vene johon json laitetaan

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
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
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
