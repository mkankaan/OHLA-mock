
package org.mock;

public class TilaustenKasittely {
	
    private IHinnoittelija hinnoittelija;

    public void setHinnoittelija(IHinnoittelija hinnoittelija) {
        this.hinnoittelija = hinnoittelija;
    }

    /*
    public void kasittele(Tilaus tilaus) {
        float alennusProsentti = hinnoittelija.getAlennusProsentti(tilaus.getAsiakas(), tilaus.getTuote());
        float alennusHinta = tilaus.getTuote().getHinta() * (1 - (alennusProsentti / 100));
        tilaus.getAsiakas().setSaldo(tilaus.getAsiakas().getSaldo() - alennusHinta);
    }
    */

    public void kasittele(Tilaus tilaus) {
        Asiakas asiakas = tilaus.getAsiakas();
        Tuote tuote = tilaus.getTuote();
        hinnoittelija.aloita();
        float prosentti = hinnoittelija.getAlennusProsentti(asiakas, tuote);

        if (tuote.getHinta() >= 100) {
            prosentti += 5;
            hinnoittelija.setAlennusProsentti(asiakas, prosentti);
        }

        float alennusHinta = tuote.getHinta() * (1 - (prosentti / 100));
        asiakas.setSaldo(asiakas.getSaldo() - alennusHinta);
        hinnoittelija.lopeta();
    }
}
