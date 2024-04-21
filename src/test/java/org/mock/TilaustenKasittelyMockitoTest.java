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
    
    @Test
    public void testaaKasittelijaWithMockitoHinnoittelija() {
        
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
}
