package harjoittelu1;

public class TyontekijaOhjelma {

	public static void main(String[] args) {
		Tyontekija tyontekija = new Tyontekija();
		Lukija lukija = new Lukija();
		tyontekija.setNimi(lukija.lueTeksti("Anna nimi: "));
		tyontekija.setOsoite(lukija.lueTeksti("Anna osoite: "));
		tyontekija.setTuntipalkka(lukija.lueDesimaaliluku("Anna tuntipalkka: "));

		System.out.println("\nNimi: " + tyontekija.getNimi());
		System.out.println("Osoite: " + tyontekija.getOsoite());
		System.out.println("Tuntipalkka: " + tyontekija.getTuntipalkka());
	}

}
