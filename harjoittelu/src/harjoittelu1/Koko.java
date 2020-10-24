package harjoittelu1;

public class Koko {
	private double pituus;
	private int paino;

	public Koko() {

	}

	public Koko(double pituus, int paino) {
		this.pituus = pituus;
		this.paino = paino;
	}

	public double getPituus() {
		return pituus;
	}

	public void setPituus(double pituus) {
		this.pituus = pituus;
	}

	public int getPaino() {
		return paino;
	}

	public void setPaino(int paino) {
		this.paino = paino;
	}

	public double getPainoindeksi() {
		double painoInd = paino / Math.pow(pituus, 2);
		painoInd = Math.round(painoInd * 100) / 100.0; // pyöristys kahteen des.
		return painoInd;
	}

	@Override
	public String toString() {
		return "Koko [pituus=" + pituus + ", paino=" + paino + "]";
	}

}
