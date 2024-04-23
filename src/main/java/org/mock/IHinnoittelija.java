package org.mock;

public interface IHinnoittelija {
	float getAlennusProsentti(Asiakas asiakas, Tuote tuote);
	void setAlennusProsentti(Asiakas asiakas, float prosentti);
	void aloita();
	void lopeta();
}
