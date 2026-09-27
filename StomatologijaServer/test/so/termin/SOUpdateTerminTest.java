package so.termin;

import domain.Klijent;
import domain.StavkaTermina;
import domain.Stomatolog;
import domain.Termin;
import domain.TipKlijenta;
import domain.Usluga;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import db.DBBroker;
import java.sql.SQLException;
import org.junit.After;

public class SOUpdateTerminTest {

    private SOUpdateTermin so;
    private Termin termin;

    @Before
    public void setUp() {
        so = new SOUpdateTermin();
        termin = new Termin();
    }

    @After
    public void tearDown() throws SQLException {
        if (termin != null && termin.getTerminID() > 0) {
            DBBroker.getInstance().getConnection()
                    .createStatement()
                    .executeUpdate(
                        "DELETE FROM termin WHERE TerminID = " + termin.getTerminID()
                    );

            DBBroker.getInstance().getConnection().commit();
        }

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

    private Termin napraviTerminZaUpdate() throws Exception {

        SOAddTermin soAdd = new SOAddTermin();

        Stomatolog stomatolog = new Stomatolog(1, "Uros", "Djokic", "uros", "uros");

        TipKlijenta tip = new TipKlijenta(1, "Standardni");

        Klijent klijent = new Klijent(1, "Jelena", "Stojković", "jelena.st@gmail.com", "0602921392", tip);

        Usluga usluga = new Usluga(1, "Stomatološki pregled", "Kompletan pregled", 2000, 20);

        Termin t = new Termin();
        t.setDatumVremePocetka(datumUBuducnosti());
        t.setIznosBezPopusta(5000);
        t.setPopust(0);
        t.setKonacanIznos(5000);
        t.setKlijent(klijent);
        t.setStomatolog(stomatolog);

        ArrayList<StavkaTermina> stavke = new ArrayList<>();

        StavkaTermina st = new StavkaTermina();
        st.setRb(1);
        st.setUsluga(usluga);
        st.setNapomena("Test");
        st.setCena(5000);
        st.setIznos(5000);

        stavke.add(st);

        t.setStavkeTermina(stavke);

        soAdd.templateExecute(t);

        return t;
    }

    @Test
    public void testPredusloviIspravni() throws Exception {

        termin = napraviTerminZaUpdate();

        so.validate(termin);
    }

    @Test(expected = Exception.class)
    public void testPredusloviNeispravanObjekat() throws Exception {
        so.validate(new Klijent());
    }

    @Test(expected = Exception.class)
    public void testPredusloviDatumUProslosti() throws Exception {

        termin = napraviTerminZaUpdate();

        termin.setDatumVremePocetka(datumUProslosti());

        so.validate(termin);
    }

    @Test(expected = Exception.class)
    public void testPredusloviBezStavki() throws Exception {

        termin = napraviTerminZaUpdate();

        termin.setStavkeTermina(new ArrayList<StavkaTermina>());

        so.validate(termin);
    }

    @Test
    public void testIzvrsiOperacijuUspesno() throws Exception {

        termin = napraviTerminZaUpdate();

        termin.setKonacanIznos(8000);
        termin.setIznosBezPopusta(8000);

        termin.getStavkeTermina().get(0).setNapomena("Izmenjena napomena");
        termin.getStavkeTermina().get(0).setCena(8000);
        termin.getStavkeTermina().get(0).setIznos(8000);

        so.templateExecute(termin);

        assertEquals(8000,
                termin.getKonacanIznos(),
                0.01);

        assertEquals(
                "Izmenjena napomena",
                termin.getStavkeTermina().get(0).getNapomena()
        );
    }

    @Test(expected = Exception.class)
    public void testIzvrsiOperacijuNeuspesno() throws Exception {

        termin = napraviTerminZaUpdate();

        termin.setDatumVremePocetka(datumUProslosti());

        so.templateExecute(termin);
    }

}