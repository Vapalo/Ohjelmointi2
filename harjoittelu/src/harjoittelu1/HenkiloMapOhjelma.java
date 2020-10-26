package harjoittelu1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class HenkiloMapOhjelma {
	private Lukija lukija = new Lukija();
	private HashMap<String, Henkilo> henkilot = new HashMap<String, Henkilo>();

	public HenkiloMapOhjelma() {
		valikko();
	}

	public void valikko() {
		Scanner input = new Scanner(System.in);
		int valinta = -1;

		do {
			System.out.println("\n1. Lisää henkilö ");
			System.out.println("2. Näytä henkilön tiedot ");
			System.out.println("3. Muuta henkilön nimi ja osoite ");
			System.out.println("4. Muuta henkilön koko ");
			System.out.println("5. Näytä kaikki henkilöt ");
			System.out.println("0. Lopetus");
			System.out.print("Anna valintasi (0-5): ");
			valinta = input.nextInt();

			switch (valinta) {
			case 1:
				lisaaHenkilo();
				break;
			case 2:
				naytaHloTiedot();
				break;
			case 3:
				muutaNimiJaOsoite();
			case 4:
				muutaKoko();
				break;
			case 5:
				naytaKaikkiTiedot();
				break;
			case 0:
				break;
			default:
				System.out.println("Virheellinen valinta");
			}
		} while (valinta != 0);
	}

	public void lisaaHenkilo() {
		Henkilo hlo = new Henkilo();
		Koko koko = new Koko();
		hlo.setNimi(lukija.lueTeksti("Anna nimi: "));
		if (henkilot.containsKey(hlo.getNimi())) {
			System.out.println("Olet jo lisännyt henkilön nimellä: " + henkilot.get(hlo.getNimi()));
			return;
		}
		hlo.setOsoite(lukija.lueTeksti("Anna osoite: "));
		koko.setPituus(lukija.lueDesimaaliluku("Anna pituus: "));
		koko.setPaino(lukija.lueKokonaisluku("Anna paino: "));
		hlo.setKoko(koko);
		henkilot.put(hlo.getNimi(), hlo);
	}

	public void naytaHloTiedot() {
		String nimi = lukija.lueTeksti("Anna näytettävän henkilön nimi: ");
		if (henkilot.containsKey(nimi)) {
			System.out.println(henkilot.get(nimi));
		} else {
			System.out.println("Henkilöä ei ole");
		}

	}

	public void muutaNimiJaOsoite() {
		String muutettavanimi = lukija.lueTeksti("Anna perustietoja muutettavan henkilön nimi: ");
		if (henkilot.containsKey(muutettavanimi)) {
			Henkilo muutettava = henkilot.get(muutettavanimi);
			Koko koko = muutettava.getKoko();
			henkilot.remove(muutettavanimi);
			Henkilo hlo = new Henkilo();
			hlo.setNimi(lukija.lueTeksti("Anna uusi nimi: "));
			hlo.setOsoite(lukija.lueTeksti("Anna uusi osoite: "));
			hlo.setKoko(koko);
			henkilot.put(hlo.getNimi(), hlo);
		} else {
			System.out.println("Henkilöä ei ole");
		}
	}

	public void muutaKoko() {
		String nimi = lukija.lueTeksti("Anna kokoa muutettavan henkilön nimi: ");
		if (henkilot.containsKey(nimi)) {
			Henkilo kokomuutos = henkilot.get(nimi);
			Koko koko = kokomuutos.getKoko();
			henkilot.remove(kokomuutos);
			koko.setPituus(lukija.lueDesimaaliluku("Anna uusi pituus: "));
			koko.setPaino(lukija.lueKokonaisluku("Anna uusi paino: "));
			kokomuutos.setKoko(koko);
			henkilot.put(kokomuutos.getNimi(), kokomuutos);
		} else {
			System.out.println("Henkilöä ei ole");
		}
	}

	public void naytaKaikkiTiedot() {
		Set<String> tiedot = henkilot.keySet();
		Iterator<String> i = tiedot.iterator();
		while (i.hasNext()) {
			Henkilo hlo = henkilot.get(i.next());
			System.out.println("\nNimi: " + hlo.getNimi());
			System.out.println("Osoite: " + hlo.getOsoite());
			System.out.println("Pituus: " + hlo.getKoko().getPituus());
			System.out.println("Paino: " + hlo.getKoko().getPaino());
			System.out.println("Painoindeksi: " + hlo.getKoko().getPainoindeksi());
		}
	}

	public static void main(String[] args) {
		new HenkiloMapOhjelma();
	}

}
