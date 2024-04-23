package org.mock;

import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.*;

public class TilaustenKasittelyMockitoTest {

    private final String ALENNUS = "alennus";
    private float alennus = 20.0f;

    @Mock
    IHinnoittelija hinnoittelijaMock;

    @Spy
    Map<String, Float> alennusSpy = Map.of(ALENNUS, alennus);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        doReturn(alennusSpy.get(ALENNUS)).when(hinnoittelijaMock).getAlennusProsentti(any(Asiakas.class), any(Tuote.class));

        doAnswer(i -> {
            alennusSpy = new HashMap<>();
            alennusSpy.put(ALENNUS, alennus);
            return null;
        }).when(hinnoittelijaMock).aloita();

        doAnswer(i -> {
            alennusSpy.put(ALENNUS, (float) i.getArguments()[1]);
            return null;
        }).when(hinnoittelijaMock).setAlennusProsentti(any(Asiakas.class), any(Float.class));

        doAnswer(i -> {
            alennusSpy.clear();
            return null;
        }).when(hinnoittelijaMock).lopeta();
    }

    @ParameterizedTest(name="Tuotteen hinta {1}")
    @CsvSource({
            "100.0f, 30.0f, 0",
            "1000.0f, 100.0f, 5",
            "1000.0f, 300.0f, 5",
    })
    public void testaaKasittelijaWithMockitoHinnoittelija(float alkuSaldo, float listaHinta, int lisaprosentti) {

        // Arrange
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - (alennus + lisaprosentti) / 100));
        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TDD in Action", listaHinta);

        // Act
        TilaustenKasittely kasittelija = new TilaustenKasittely();
        kasittelija.setHinnoittelija(hinnoittelijaMock);
        kasittelija.kasittele(new Tilaus(asiakas, tuote));

        // Assert
        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        verify(hinnoittelijaMock).getAlennusProsentti(asiakas, tuote);
    }
}
