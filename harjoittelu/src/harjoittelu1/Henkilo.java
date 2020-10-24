package harjoittelu1;

public class Henkilo {
	private String nimi, osoite;
	private Koko koko;

	public Henkilo() { // new Henkilo();

	}

	public Henkilo(String nimi, String osoite) {// new Henkilo("matti", "katu 10");
		this.nimi = nimi;
		this.osoite = osoite;
	}

	public Henkilo(String nimi) {// new Henkilo("matti");
		this.nimi = nimi;
	}

	public Henkilo(String nimi, String osoite, Koko koko) {
		this.nimi = nimi;
		this.osoite = osoite;
		this.koko = koko;
	}

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	public String getOsoite() {
		return osoite;
	}

	public void setOsoite(String osoite) {
		this.osoite = osoite;
	}

	public Koko getKoko() {
		return koko;
	}

	public void setKoko(Koko koko) {
		this.koko = koko;
	}

	@Override
	public String toString() {
		return "Henkilo [nimi=" + nimi + ", osoite=" + osoite + ", koko=" + koko + "]";
	}
}
