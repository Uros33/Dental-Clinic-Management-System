package so.termin;

import db.DBBroker;
import domain.Klijent;
import domain.StavkaTermina;
import domain.Stomatolog;
import domain.Termin;
import domain.TipKlijenta;
import domain.Usluga;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SOAddTerminTest {

    private SOAddTermin so;
    private Termin termin;

    @Before
    public void setUp() {
        so = new SOAddTermin();
        termin = new Termin();
    }

    @After
    public void tearDown() throws SQLException {
        DBBroker.getInstance().getConnection()
                .createStatement()
                .executeUpdate("DELETE FROM termin WHERE KlijentID = 1 AND StomatologID = 1 AND IznosBezPopusta = 9999");

        DBBroker.getInstance().getConnection().commit();

        so = null;
        termin = null;
    }

    private Date datumUBuducnosti() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        return c.getTime();
    }

    private Date datumUProslosti() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -1);
        return c.getTime();
    }

    private Termin napraviIspravanTermin() {
        Stomatolog stomatolog = new Stomatolog(1, "Uros", "Djokic", "uros", "uros");

        TipKlijenta tip = new TipKlijenta(1, "Standardni");
        Klijent klijent = new Klijent(1, "Jelena", "Stojković", "jelena.st@gmail.com", "0602921392", tip);

        Usluga usluga = new Usluga(1, "Stomatološki pregled",
                "Kompletan pregled i plan terapije", 2000, 20);

        termin = new Termin();
        termin.setDatumVremePocetka(datumUBuducnosti());
        termin.setIznosBezPopusta(9999);
        termin.setPopust(0);
        termin.setKonacanIznos(9999);
        termin.setStomatolog(stomatolog);
        termin.setKlijent(klijent);

        ArrayList<StavkaTermina> stavke = new ArrayList<>();
        StavkaTermina stavka = new StavkaTermina();
        stavka.setRb(1);
        stavka.setUsluga(usluga);
        stavka.setNapomena("Test pregled");
        stavka.setCena(9999);
        stavka.setIznos(9999);

        stavke.add(stavka);
        termin.setStavkeTermina(stavke);

        return termin;
    }

    @Test
    public void testPredusloviIspravni() throws Exception {
        termin = napraviIspravanTermin();

        so.validate(termin);
    }

    @Test(expected = Exception.class)
    public void testPredusloviNeispravanObjekat() throws Exception {
        so.validate(new Klijent());
    }

    @Test(expected = Exception.class)
    public void testPredusloviDatumUProslosti() throws Exception {
        termin = napraviIspravanTermin();
        termin.setDatumVremePocetka(datumUProslosti());

        so.validate(termin);
    }

    @Test(expected = Exception.class)
    public void testPredusloviTerminBezStavki() throws Exception {
        termin = napraviIspravanTermin();
        termin.setStavkeTermina(new ArrayList<StavkaTermina>());

        so.validate(termin);
    }

    @Test
    public void testIzvrsiOperacijuUspesno() throws Exception {
        termin = napraviIspravanTermin();

        so.templateExecute(termin);

        assertTrue(termin.getTerminID() > 0);
        assertEquals(9999, termin.getIznosBezPopusta(), 0.01);
        assertEquals(1, termin.getStavkeTermina().size());
    }

    @Test(expected = Exception.class)
    public void testIzvrsiOperacijuNeuspesno() throws Exception {
        termin = napraviIspravanTermin();
        termin.setDatumVremePocetka(datumUProslosti());

        so.templateExecute(termin);
    }
}