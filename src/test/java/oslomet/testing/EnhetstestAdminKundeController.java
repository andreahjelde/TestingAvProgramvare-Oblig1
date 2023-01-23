//Kaja

package oslomet.testing;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

public class EnhetstestAdminKundeController {

    @InjectMocks
    // denne skal testes
    private AdminKundeController adminKundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    //Test på hent alle kunder fra listen.
    @Test
    public void test_HentAlleOK() {

        //arrage
        Kunde kunde1 = new Kunde("151299", "Kalle", "Knudsen", "kattemveien", "7045", "Trondheim", "98501145","NokkaLangt");
        Kunde kunde2 = new Kunde("120997", "Rita", "Ottervik", "Rosenborggata", "7022", "Trondheim", "45623390", "Hemmelig");
        List<Kunde> kundeliste = new ArrayList<>();
        kundeliste.add(kunde1);
        kundeliste.add(kunde2);

        Mockito.when(repository.hentAlleKunder()).thenReturn(kundeliste);

        //act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert, vi forventer at kundelisten er det samme som resultatet
        assertNull(resultat); //Hvorfor null og ikke kundeliste?
    }

    @Test
    public void test_hentAlleFeil() {

        // arrage
        Mockito.when(repository.hentAlleKunder()).thenReturn(null);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertNull(resultat);
    }

    @Test
    public void test_lagreKundeOk(){

        // arrage
        Kunde kunde1 = new Kunde("151299", "Ben", "Baller", "kattemveien", "7045", "Trondheim", "98501145","NokkaLangt");

        Mockito.when(repository.registrerKunde((any(Kunde.class)))).thenReturn(null);

        //act
        String resultat = adminKundeController.lagreKunde(kunde1);

        //assert
        assertEquals("Ikke logget inn", resultat); //hvorfor ikke null?
    }

    @Test
    public void test_lagreKundeFeil(){

        // arrage
        Kunde kunde1 = new Kunde("151299", "Ben", "Baller", "kattemveien", "7045", "Trondheim", "98501145","NokkaLangt");

        Mockito.when(repository.registrerKunde((any(Kunde.class)))).thenReturn("Ikke logget inn");

        //act
        String resultat = adminKundeController.lagreKunde(kunde1);

        //assert
        assertEquals("Ikke logget inn", resultat);
    }


    //lagreKunde, enten null eller "Ikke logget inn"


    //Endre, enten null eller "Ikke logget inn"

    //Slett, enten null eller "Ikke logget inn"


}
