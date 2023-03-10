package oslomet.testing;
//Andrea - ny test


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn() {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523", 720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105020123456", "01010110523", 100_500, "Sparekonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn() {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }



    /* skrevet av andrea */

    // Sjekker for hent Transaksjoner hvis bruker er logget inn.

    @Test
    public void hentTransaksjoner_LoggetInn(){
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345", 100.5,
                "2015-03-15", "Fjordkraft", "1", "105010123456");
        Transaksjon transaksjon2 = new Transaksjon(2, "20102012345", 400.4,
                "2015-03-20", "Skagen", "1", "105010123456");
        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        Konto konti = new Konto("01010110523", "105010123456", 720, "Lønnskonto",
                "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(konti);

        // act
        Konto resultat = bankController.hentTransaksjoner("", "", "");

        // assert
        assertEquals(konti, resultat);
    }

    // Sjekker for hent Transaksjoner hvis bruker ikke er logget inn.
    @Test
    public void hentTransaksjoner_IkkeLoggetInn(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        Konto resultat = bankController.hentTransaksjoner("", "", "");

        // assert
        assertNull(resultat);
    }

    // Sjekker for hent Saldo hvis bruker er logget inn.
    @Test
    public void hentSaldi_LoggetInn(){
        // arrange
        List<Konto> saldi = new ArrayList<>();

        Konto konto1 = new Konto("105010123456", "01010110523", 720,
                "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105020123456", "01010110523", 100500,
                "Sparekonto", "NOK", null);
        saldi.add(konto1);
        saldi.add(konto2);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.hentSaldi(anyString())).thenReturn(saldi);

        // act
        List<Konto> resultat = bankController.hentSaldi();


        // assert
        assertEquals(saldi, resultat);
    }

    // Sjekker for hent Saldo hvis bruker ikke er logget inn.
    @Test
    public void hentSaldi_IkkeLoggetInn(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);
    }

    // Sjekker for hent betalinger hvis bruker er logget inn.
    @Test
    public void hentBetalinger_LoggetInn(){
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345", 100.5,
                "2015-03-15", "Fjordkraft", "1", "105010123456");
        Transaksjon transaksjon2 = new Transaksjon(2, "20102012345", 400.4,
                "2015-03-20", "Skagen", "1", "105010123456");
        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertEquals(transaksjoner, resultat);
    }

    // Sjekker for hent betalinger hvis bruker ikke er logget inn.
    @Test
    public void hentBetalinger_IkkeLoggetInn(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertNull(resultat);
    }


    // Tester Utfør betaling hvis logget inn
    @Test
    public void utforBetaling_LoggetInn(){
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345", 100.5,
                "2015-03-15", "Fjordkraft", "1", "105010123456");
        Transaksjon transaksjon2 = new Transaksjon(2, "20102012345", 400.4,
                "2015-03-20", "Skagen", "1", "105010123456");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.utforBetaling(anyInt())).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(transaksjoner.get(0).getTxID());

        // assert
        assertEquals(transaksjoner, resultat);
    }

    // Tester Utfør betaling hvis ikke logget inn
    @Test
    public void utforBetaling_IkkeLoggetInn(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(12);

        // assert
        assertNull(resultat);
    }


    //Tester Registrer Betaling dersom logget inn
    @Test
    public void registrerBetaling_LoggetInn(){
        // arrange

        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345", 100.5,
                "2015-03-15", "Fjordkraft", "1", "105010123456");


        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.registrerBetaling(transaksjon1)).thenReturn("OK");


        // act
        String resultat = bankController.registrerBetaling(transaksjon1);

        // assert
        assertEquals("OK", resultat);
    }

    //Tester Registrer betaling - ikke logget inn
    @Test
    public void registrerBetaling_IkkeLoggetInn(){
        // arrange
        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345", 100.5,
                "2015-03-15", "Fjordkraft", "1", "105010123456");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = bankController.registrerBetaling(transaksjon1);

        // assert
        assertNull(resultat);
    }

    //Tester endre kundeinfor - logget inn
    @Test
    public void endreKundeInfo_LoggetInn(){
        //arrange

        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = bankController.endre(kunde1);

        // assert
        assertEquals("OK", resultat);

    }

    //Tester endre kundeinfo - ikke logget inn
    @Test
    public void endreKundeInfo_IkkeLoggetInn(){
        // arrange
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = bankController.endre(kunde1);

        // assert
        assertNull(resultat);
    }

}
