package org.mock;

import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TilaustenKasittelyMockitoTest {

    private final String ALENNUS = "alennus";
    
    @Mock
    IHinnoittelija hinnoittelijaMock;

    @Spy
    Map<String, Float> alennusSpy = Map.of(ALENNUS, 20.0f);
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

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
        float alkuSaldo = 1000.0f;
        float listaHinta = 100.0f;
        float alennus = 20.0f;
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - (alennus + 5) / 100));
        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TDD in Action", listaHinta);

        // Record
        doReturn(alennusSpy.get(ALENNUS)).when(hinnoittelijaMock).getAlennusProsentti(asiakas, tuote);

        doAnswer(i -> {
            alennusSpy = new HashMap<>();
            alennusSpy.put(ALENNUS, alennus);
            return null;
        }).when(hinnoittelijaMock).aloita();

        doAnswer(i -> {
            float prosentti = (float) i.getArguments()[1];
            alennusSpy.put(ALENNUS, prosentti);
            return null;
        }).when(hinnoittelijaMock).setAlennusProsentti(any(Asiakas.class), any(Float.class));

        doAnswer(i -> {
            alennusSpy.clear();
            return null;
        }).when(hinnoittelijaMock).lopeta();

        // Act
        TilaustenKasittely kasittelija = new TilaustenKasittely();
        kasittelija.setHinnoittelija(hinnoittelijaMock);
        kasittelija.kasittele(new Tilaus(asiakas, tuote));

        // Assert
        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        verify(hinnoittelijaMock).getAlennusProsentti(asiakas, tuote);
    }

    // Hinta > 100
    @Test
    public void testaaMockHintaYli100() {

        // Arrange
        float alkuSaldo = 1000.0f;
        float listaHinta = 300.0f;
        float alennus = 20.0f;
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - (alennus + 5) / 100));
        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TDD in Action", listaHinta);

        // Record
        doReturn(alennusSpy.get(ALENNUS)).when(hinnoittelijaMock).getAlennusProsentti(asiakas, tuote);

        doAnswer(i -> {
            alennusSpy = new HashMap<>();
            alennusSpy.put(ALENNUS, alennus);
            return null;
        }).when(hinnoittelijaMock).aloita();

        doAnswer(i -> {
            float prosentti = (float) i.getArguments()[1];
            alennusSpy.put(ALENNUS, prosentti);
            return null;
        }).when(hinnoittelijaMock).setAlennusProsentti(any(Asiakas.class), any(Float.class));

        doAnswer(i -> {
            alennusSpy.clear();
            return null;
        }).when(hinnoittelijaMock).lopeta();

        // Act
        TilaustenKasittely kasittelija = new TilaustenKasittely();
        kasittelija.setHinnoittelija(hinnoittelijaMock);
        kasittelija.kasittele(new Tilaus(asiakas, tuote));

        // Assert
        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        verify(hinnoittelijaMock).getAlennusProsentti(asiakas, tuote);
    }
}
