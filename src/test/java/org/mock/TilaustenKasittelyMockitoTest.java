package org.mock;

import org.junit.jupiter.api.*;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TilaustenKasittelyMockitoTest {
    
    @Mock
    IHinnoittelija hinnoittelijaMock;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Hinta < 100
    @Test
    public void testaaMockHintaAlle100() {
        
        // Arrange
        float alkuSaldo = 100.0f;
        float listaHinta = 30.0f;
        float alennus = 20.0f;
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - alennus / 100));
        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TDD in Action", listaHinta);

        // Record
        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote)).thenReturn(alennus);
        
        // Act
        TilaustenKasittely kasittelija = new TilaustenKasittely();
        kasittelija.setHinnoittelija(hinnoittelijaMock);
        kasittelija.kasittele(new Tilaus(asiakas, tuote));
        
        // Assert
        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        verify(hinnoittelijaMock).getAlennusProsentti(asiakas, tuote);
    }

    // Hinta = 100
    @Test
    public void testaaMockHinta100() {

        // Arrange
        float alkuSaldo = 100.0f;
        float listaHinta = 100.0f;
        float alennus = 20.0f;
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - alennus / 100));
        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TDD in Action", listaHinta);

        // Record
        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote)).thenReturn(alennus);

        // Act
        TilaustenKasittely kasittelija = new TilaustenKasittely();
        kasittelija.setHinnoittelija(hinnoittelijaMock);
        kasittelija.kasittele(new Tilaus(asiakas, tuote));

        // Assert
        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        verify(hinnoittelijaMock).getAlennusProsentti(asiakas, tuote);
    }
}
