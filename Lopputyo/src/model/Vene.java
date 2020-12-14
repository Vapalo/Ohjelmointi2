package model;

public class Vene {
	private int hinta, tunnus;
	private double pituus, leveys;
	private String nimi, merkkimalli;

	public Vene() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vene(int hinta, int tunnus, double pituus, double leveys, String nimi, String merkkimalli) {
		super();
		this.hinta = hinta;
		this.tunnus = tunnus;
		this.pituus = pituus;
		this.leveys = leveys;
		this.nimi = nimi;
		this.merkkimalli = merkkimalli;
	}

	public int getHinta() {
		return hinta;
	}

	public void setHinta(int hinta) {
		this.hinta = hinta;
	}

	public int getTunnus() {
		return tunnus;
	}

	public void setTunnus(int tunnus) {
		this.tunnus = tunnus;
	}

	public double getPituus() {
		return pituus;
	}

	public void setPituus(double pituus) {
		this.pituus = pituus;
	}

	public double getLeveys() {
		return leveys;
	}

	public void setLeveys(double leveys) {
		this.leveys = leveys;
	}

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	public String getMerkkimalli() {
		return merkkimalli;
	}

	public void setMerkkimalli(String merkkimalli) {
		this.merkkimalli = merkkimalli;
	}

	@Override
	public String toString() {
		return "Vene [hinta=" + hinta + ", tunnus=" + tunnus + ", pituus=" + pituus + ", leveys=" + leveys + ", nimi="
				+ nimi + ", merkkimalli=" + merkkimalli + "]";
	}

}
