package oslomet.testing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhetController {
    @InjectMocks
    // denne skal testes
    private Sikkerhet sikkerhetsController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal også Mock'es
    private MockHttpSession session;

    //Skjønner ikke helt denne, fått hjelp av TA. Må ha det med for å kjøre integrasjonstesting på sessions.
    @Before
    public void initSessions() {
        Map<String, Object> attributes = new HashMap<String, Object>();

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            }
        }).when(session).getAttribute(anyString());

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());
    }

    /*------------------------ LOGG INN --------------------------------*/
    //Tester sjekkLoggInn metoden i sikkerhets controlleren (Sikkerhet.java)
    //Hvis bruker er logget inn = "OK"

    @Test
    public void sjekkLoggInn_LoggetInn_OK() {
        //arrange: sett opp testobjektene og klargjør forutsetningene for testen
        //arrange brukes for å teste setup’en
        //man burde lage hvert nødvendige objekt her om det trengs
        //I tillegg burde du legge til mocks for avhengigheter (when – then)

        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("OK");

        //act:(utfør selve testens arbeid)
        //Act brukes for å kalle test-metoden

        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "HeiHei");

        //assert (bekreft resultatet)
        //Assert er «hjertet av testen»
        //Her kan vi verifisere at resultatet av metodekallet vi gjør returnerer det vi ønsker at det skal

        assertEquals("OK", resultat);

    }

    //Tester sjekk LoggInn (Ikke Logget Inn - Feil Personnummer Eller Passord)
    @Test

    public void sjekkLoggInn_FeilPersonnummerEllerPassord() {
        //arrange
        Mockito.when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("Feil i personnummer eller passord");

        //act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678908", "HeiHeiHei");

        //assert
        assertEquals("Feil i personnummer eller passord", resultat);
    }

    // Tester sjekk LoggInn (Ikke Logget Inn - Feil Med Regex Personnummer)

    @Test

    public void sjekkLoggInn_FeilRegExPersNr() {
        //act
        //for kort persNr

        String resultat1 = sikkerhetsController.sjekkLoggInn("123", "HeiHallo");

        //for langt persNr

        String resultat2 = sikkerhetsController.sjekkLoggInn("123456789012345", "HeiHallo");

        //symbol/tegn i persNr

        String resultat3 = sikkerhetsController.sjekkLoggInn("&%¤())%¤#%", "HeiHallo");

        //bokstaver i persNr

        String resultat4 = sikkerhetsController.sjekkLoggInn("JegErBest", "HeiHallo");

        //assert

        assertEquals("Feil i personnummer", resultat1);
        assertEquals("Feil i personnummer", resultat2);
        assertEquals("Feil i personnummer", resultat3);
        assertEquals("Feil i personnummer", resultat4);

    }

    //Tester sjekk LoggInn (Ikke Logget Inn - Feil Med Regex Passord)

    @Test
    public void sjekkLoggInn_FeilMedRegExPassord() {
        //act

        //Tomt passord
        String resultat1 = sikkerhetsController.sjekkLoggInn("12345678901", "");

        //For langt passord
        String resultat2 = sikkerhetsController.sjekkLoggInn("12345678901", "HeiPåDegDuErTøff123455678901234567890");

        //For kort passord
        String resultat3 = sikkerhetsController.sjekkLoggInn("12345678901", "Hei1");

        //assert
        assertEquals("Feil i passord", resultat1);
        assertEquals("Feil i passord", resultat2);
        assertEquals("Feil i passord", resultat3);
    }


    /*------------------------ LOGG UT --------------------------------*/

    /* Tester metoden "loggUt" */
    @Test
    public void LoggUt(){
        //arrange
        session.setAttribute("Innlogget", null);
        sikkerhetsController.loggUt();

        //act
        String resultat = sikkerhetsController.loggetInn();

        //assert
        assertNull(resultat);
    }


    /*------------------------ LOGG INN ADMIN --------------------------------*/

    // Tester LoggInn Admin
    // Logget Inn = OK

    @Test
    public void LoggInnAdmin_LoggetInn_OK() {

        //arrange
        session.setAttribute("Innlogget", "Admin");

        //act
        String resultat = sikkerhetsController.loggInnAdmin("Admin", "Admin");

        //assert
        assertEquals("Logget inn", resultat);

    }

    // Tester LoggInn Admin (Ikke Logget Inn - Feil)

    @Test
    public void LoggInnAdmin_IkkeLoggetInn() {
        //arrange
        session.setAttribute("Innlogget", null);

        //act
        String resultat = sikkerhetsController.loggInnAdmin("", "");

        //assert
        assertEquals("Ikke logget inn", resultat);

    }

    /*------ Logget Inn ------*/

    // Tester Logget Inn (Logget Inn - OK)

    @Test
    public void LoggetInn_LoggInn_OK() {
        //arrange
        session.setAttribute("Innlogget", "12345678901");

        //act
        String resultat = sikkerhetsController.loggetInn();

        //assert
        assertEquals("12345678901", resultat);
    }

    // Tester Logget Inn (Logget Inn - Feil)

    @Test
    public void LoggetInn_IkkeLoggetInn() {
        //arrange
        session.setAttribute(null, null);

        //act
        String resultat = sikkerhetsController.loggetInn();

        //assert
        assertNull(resultat);

    }

}



