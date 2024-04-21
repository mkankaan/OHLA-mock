
package org.mock;

public class TilaustenKasittely {
	
    private IHinnoittelija hinnoittelija;

    public void setHinnoittelija(IHinnoittelija hinnoittelija) {
        this.hinnoittelija = hinnoittelija;
    }

    public void kasittele(Tilaus tilaus) {
        float alennusProsentti = hinnoittelija.getAlennusProsentti(tilaus.getAsiakas(), tilaus.getTuote());
        float alennusHinta = tilaus.getTuote().getHinta() * (1 - (alennusProsentti / 100));
        tilaus.getAsiakas().setSaldo(tilaus.getAsiakas().getSaldo() - alennusHinta);
    }

}
