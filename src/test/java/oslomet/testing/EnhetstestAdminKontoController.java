package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

    //Sjekker for å hente konto hvis man ER logget inn.
    @Test
    public void hentAlleKonti_loggetInn(){
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

    //Sjekker hent alle kontoer hvis man IKKE er logget inn.
    @Test
    public void hentAlleKonti_ikkeLoggetInn(){

        //Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //Act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        //Assert
        assertNull(resultat);
    }

    @Test
    public void registrerKonto_loggetInn(){

    }

    @Test
    public void registrerKonto_ikkeLoggetInn(){

    }

    @Test
    public void endreKonto_loggetInn(){

    }

    @Test
    public void endreKonto_ikkeLoggetInn(){

    }

    @Test
    public void slettKonto_loggetInn(){

    }

    @Test
    public void slettKonto_ikkeLoggetInn(){

    }
}
