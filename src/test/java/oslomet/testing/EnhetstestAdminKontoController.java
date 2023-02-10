package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;

import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

//MARGRETE
@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    private AdminKontoController adminKontoController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    //Sjekker for å hente konto hvis bruker er logget inn.
    @Test
    public void hentAlleKonti_loggetInnGodkjent(){

        // Oppretter en liste av typen Konto.
        List<Konto> konti = new ArrayList<>();

        Konto konti1 = new Konto("2805199609234", "01010123560",
                10_000, "Sparekonto", "NOK", null);

        //Legger konti1 inn i "konti"-listen.
        konti.add(konti1);

        //Mock
        when(sjekk.loggetInn()).thenReturn("105010123456");

        //Mock
        when(repository.hentAlleKonti()).thenReturn(konti);

        //Act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        //Assert.
        assertEquals(konti, resultat);
    }

    //Sjekker hent alle kontoer hvis bruker ikke er logget inn. Skal returnere null/ingenting.
    @Test
    public void hentAlleKonti_ikkeLoggetInnFeil(){

        //Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //Act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        //Assert
        assertNull(resultat);
    }

    // Sjekker registrert konto hvis bruker er logget inn.
    @Test
    public void registrerKonto_loggetInnGodkjent(){

        //Oppretter en konto
        Konto konto1 = new Konto("2805199609234", "01010123560",
                10_000, "Sparekonto", "NOK", null);

        //Mock
        when(sjekk.loggetInn()).thenReturn("2805199609234");

        //Mock
        when(repository.registrerKonto(konto1)).thenReturn("Godkjent");

        // Act
        String resultat = adminKontoController.registrerKonto(konto1);

        // Assert
        assertEquals("Godkjent", resultat);

    }

    // Sjekker registrertKonto hvis ikke bruker er logget inn. Skal returnere "Ikke innlogget".
    @Test
    public void registrerKonto_ikkeLoggetInnFeil(){

        Konto konto1 = new Konto("2805199609234", "01010123560",
                10_000, "Sparekonto", "NOK", null);

        // Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String resultat = adminKontoController.registrerKonto(konto1);

        // Assert
        assertEquals("Ikke innlogget", resultat);

    }

    // Sjekker for endreKonto hvis bruker er logget inn.
    @Test
    public void endreKonto_loggetInnGodkjent(){

        // Oppretter en konto av typen "Konto".
        Konto konto1 = new Konto("2805199609234", "01010123560",
                10_000, "Sparekonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("2805199609234");

        when(repository.endreKonto(any(Konto.class))).thenReturn("Godkjent");

        // Act
        String resultat = adminKontoController.endreKonto(konto1);

        // Assert
        assertEquals("Godkjent", resultat);

    }

    // Sjekker for endreKonto hvis bruker ikke er logget inn.
    @Test
    public void endreKonto_ikkeLoggetInnFeil(){

        Konto konto1 = new Konto("2805199609234", "01010123560",
                10_000, "Sparekonto", "NOK", null);

        // Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String resultat = adminKontoController.endreKonto(konto1);

        // Assert
        assertEquals("Ikke innlogget", resultat);
    }

    // Sjekker for slett konto hvis bruker er logget inn.
    @Test
    public void slettKonto_loggetInnGodkjent(){

        Konto konto1 = new Konto("2805199609234", "01010123560",
                10_000, "Sparekonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("2805199609234");

        when(repository.slettKonto(anyString())).thenReturn("Godkjent");

        // Act
        String resultat = adminKontoController.slettKonto(konto1.getKontonummer());

        // Assert
        assertEquals("Godkjent", resultat);

    }

    // Sjekker for slett konto hvis bruker ikke er logget inn.
    @Test
    public void slettKonto_ikkeLoggetInnFeil(){

        Konto konto1 = new Konto("2805199609234", "01010123560",
                10_000, "Sparekonto", "NOK", null);

        // Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String resultat = adminKontoController.slettKonto(konto1.getKontonummer());

        // Assert
        assertEquals("Ikke innlogget", resultat);
        
    }
}
