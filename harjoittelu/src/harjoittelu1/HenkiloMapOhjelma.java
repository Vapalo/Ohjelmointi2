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
		Set<String> nimet = henkilot.keySet();
		Iterator<String> i = nimet.iterator();
		while (i.hasNext()) {
			System.out.println(henkilot.get(i.next()));
		}
	}

	public static void main(String[] args) {
		new HenkiloMapOhjelma();
	}

}
