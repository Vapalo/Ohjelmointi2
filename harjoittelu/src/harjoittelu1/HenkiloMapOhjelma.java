package harjoittelu1;

import java.util.Scanner;

public class HenkiloMapOhjelma {

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

			}
		} while (valinta != 0);
	}

	public static void main(String[] args) {
	}

}
