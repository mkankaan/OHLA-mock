package org.mock;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TilaustenKasittelyFakeTest {

    @Test
    public void testaaKasittelijaWithFakeHinnoittelija() {

        // Arrange
        float alkuSaldo = 100.0f;
        float listaHinta = 30.0f;
        float alennus = 20.0f;
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - alennus / 100));
        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TDD in Action", listaHinta);
        IHinnoittelija hinnoittelija = new FakeHinnoittelija(alennus);

        // Act
        TilaustenKasittely kasittelija = new TilaustenKasittely();
        kasittelija.setHinnoittelija(hinnoittelija);
        kasittelija.kasittele(new Tilaus(asiakas, tuote));

        // Assert
        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001); }
}