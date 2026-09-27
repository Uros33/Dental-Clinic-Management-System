package so.klijent;

import db.DBBroker;
import domain.Klijent;
import domain.TipKlijenta;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SOAddKlijentTest {

    private SOAddKlijent so;
    private Klijent klijent;

    @Before
    public void setUp() {
        so = new SOAddKlijent();
        klijent = new Klijent();
    }

    @After
    public void tearDown() throws SQLException {
        DBBroker.getInstance().getConnection()
                .createStatement()
                .executeUpdate("DELETE FROM klijent WHERE email LIKE 'test%' OR telefon LIKE '069999%'");

        DBBroker.getInstance().getConnection().commit();

        so = null;
        klijent = null;
    }

    @Test
    public void testPredusloviIspravni() throws Exception {
        klijent.setIme("Test");
        klijent.setPrezime("Klijent");
        klijent.setEmail("testklijent@gmail.com");
        klijent.setTelefon("0699999991");
        klijent.setTipKlijenta(new TipKlijenta(1, "Standardni"));

        so.validate(klijent);
    }

    @Test(expected = Exception.class)
    public void testPredusloviNeispravanObjekat() throws Exception {
        so.validate(new TipKlijenta());
    }

    @Test(expected = Exception.class)
    public void testPredusloviPogresanEmail() throws Exception {
        klijent.setIme("Test");
        klijent.setPrezime("Klijent");
        klijent.setEmail("testgmail.com");
        klijent.setTelefon("0699999992");
        klijent.setTipKlijenta(new TipKlijenta(1, "Standardni"));

        so.validate(klijent);
    }

    @Test(expected = Exception.class)
    public void testPredusloviPogresanTelefon() throws Exception {
        klijent.setIme("Test");
        klijent.setPrezime("Klijent");
        klijent.setEmail("testtelefon@gmail.com");
        klijent.setTelefon("123456789");
        klijent.setTipKlijenta(new TipKlijenta(1, "Standardni"));

        so.validate(klijent);
    }

    @Test(expected = Exception.class)
    public void testPredusloviDuplikatEmail() throws Exception {
        klijent.setIme("Jelena");
        klijent.setPrezime("Stojković");
        klijent.setEmail("jelena.st@gmail.com");
        klijent.setTelefon("0699999993");
        klijent.setTipKlijenta(new TipKlijenta(1, "Standardni"));
        klijent.setKlijentID(-1);

        so.validate(klijent);
    }

    @Test(expected = Exception.class)
    public void testPredusloviDuplikatTelefon() throws Exception {
        klijent.setIme("Jelena");
        klijent.setPrezime("Stojković");
        klijent.setEmail("jelena.st@gmail.com");
        klijent.setTelefon("0602921392");
        klijent.setTipKlijenta(new TipKlijenta(1, "Standardni"));
        klijent.setKlijentID(-1);

        so.validate(klijent);
    }

    @Test
    public void testIzvrsiOperacijuUspesno() throws Exception {
        klijent.setIme("Test");
        klijent.setPrezime("Klijent");
        klijent.setEmail("testuspesno@gmail.com");
        klijent.setTelefon("0699999994");
        klijent.setTipKlijenta(new TipKlijenta(1, "Standardni"));

        so.templateExecute(klijent);

        assertEquals("testuspesno@gmail.com", klijent.getEmail());
    }

    @Test(expected = Exception.class)
    public void testIzvrsiOperacijuNeuspesno() throws Exception {
        klijent.setIme("Test");
        klijent.setPrezime("Klijent");
        klijent.setEmail("pogresanemail");
        klijent.setTelefon("0699999995");
        klijent.setTipKlijenta(new TipKlijenta(1, "Standardni"));

        so.templateExecute(klijent);
    }
}