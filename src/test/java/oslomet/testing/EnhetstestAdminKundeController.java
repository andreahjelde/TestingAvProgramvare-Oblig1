//Kaja

package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
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

        List<Kunde> kundeliste = new ArrayList<>();

        //arrage
        Kunde kunde1 = new Kunde("151299", "Kalle", "Knudsen", "kattemveien", "7045", "Trondheim", "98501145","NokkaLangt");
        Kunde kunde2 = new Kunde("120997", "Rita", "Ottervik", "Rosenborggata", "7022", "Trondheim", "45623390", "Hemmelig");

        kundeliste.add(kunde1);
        kundeliste.add(kunde2);

        when(sjekk.loggetInn()).thenReturn("151299");

        when(repository.hentAlleKunder()).thenReturn(kundeliste);

        //act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertEquals(kundeliste, resultat);
    }

    @Test
    public void test_hentAlleFeil() {

        // arrage
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertNull(resultat);
    }

    @Test
    public void test_lagreKundeOk(){

        // arrage
        Kunde kunde1 = new Kunde("151299", "Kalle", "Knudsen", "kattemveien", "7045", "Trondheim", "98501145","NokkaLangt");

        when(sjekk.loggetInn()).thenReturn("151299");
        when(repository.registrerKunde((any(Kunde.class)))).thenReturn(null);

        //act
        String resultat = adminKundeController.lagreKunde(kunde1);

        //assert
        assertNull(resultat);
    }

    @Test
    public void test_lagreKundeFeil(){

        // arrage
        Kunde kunde1 = new Kunde("151299", "Kalle", "Knudsen", "kattemveien", "7045", "Trondheim", "98501145","NokkaLangt");

        when(repository.registrerKunde((any(Kunde.class)))).thenReturn("Ikke logget inn");

        //act
        String resultat = adminKundeController.lagreKunde(kunde1);

        //assert
        assertEquals("Ikke logget inn", resultat);
    }


    //lagreKunde, enten null eller "Ikke logget inn"


    //Endre, enten null eller "Ikke logget inn"

    //Slett, enten null eller "Ikke logget inn"


}
