package harjoittelu1;

import static org.junit.Assert.*;

import org.junit.Test;

public class JUnitTestaaTyontekija {

	@Test
	public void testGetNimi() {
		Tyontekija tyontekija = new Tyontekija();
		tyontekija.setNimi("Paavo");
		assertEquals("Paavo", tyontekija.getNimi());
	}

	@Test
	public void testGetOsoite() {
		Tyontekija tyontekija = new Tyontekija();
		tyontekija.setOsoite("Helsinki");
		assertEquals("Helsinki", tyontekija.getOsoite());
	}

	@Test
	public void testGetKoko() {
		Tyontekija tyontekija = new Tyontekija();
		Koko koko = new Koko(180, 80);
		tyontekija.setKoko(koko);
		assertEquals(koko, tyontekija.getKoko());

	}

	@Test
	public void testGetTuntipalkka() {
		Tyontekija tyontekija = new Tyontekija();
		tyontekija.setTuntipalkka(15.00);
		assertEquals(15.00, tyontekija.getTuntipalkka(), 0.01);
	}
}
