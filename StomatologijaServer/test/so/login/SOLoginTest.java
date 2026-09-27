package so.login;

import controller.ServerController;
import domain.Klijent;
import domain.Stomatolog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SOLoginTest {

    private SOLogin so;
    private Stomatolog stomatolog;

    @Before
    public void setUp() {
        so = new SOLogin();

        stomatolog = new Stomatolog();
        stomatolog.setIme("");
        stomatolog.setPrezime("");
        stomatolog.setUsername("uros");
        stomatolog.setPassword("uros");
        stomatolog.setStomatologID(-1);

        ServerController.getInstance().getUlogovaniStomatolozi().clear();
    }

    @After
    public void tearDown() {
        ServerController.getInstance().getUlogovaniStomatolozi().clear();

        so = null;
        stomatolog = null;
    }

    @Test
    public void testValidateIspravanObjekat() throws Exception {
        so.validate(stomatolog);
    }

    @Test(expected = Exception.class)
    public void testValidateNeispravanObjekat() throws Exception {
        so.validate(new Klijent());
    }

    @Test(expected = Exception.class)
    public void testStomatologVecUlogovan() throws Exception {
        Stomatolog ulogovani = new Stomatolog();
        ulogovani.setUsername("uros");
        ulogovani.setPassword("uros");
        ulogovani.setStomatologID(1);

        ServerController.getInstance().getUlogovaniStomatolozi().add(ulogovani);

        so.validate(stomatolog);
    }

    @Test
    public void testIzvrsiOperacijuUspesno() throws Exception {
        so.templateExecute(stomatolog);

        assertNotNull(so.getUlogovani());
        assertEquals("uros", so.getUlogovani().getUsername());
        assertEquals("uros", so.getUlogovani().getPassword());
        assertEquals(1, ServerController.getInstance().getUlogovaniStomatolozi().size());
    }

    @Test(expected = Exception.class)
    public void testIzvrsiOperacijuNeuspesnoPogresnaLozinka() throws Exception {
        stomatolog.setPassword("pogresna");
        so.templateExecute(stomatolog);
    }

    @Test(expected = Exception.class)
    public void testIzvrsiOperacijuNeuspesnoNepostojeciUsername() throws Exception {
        stomatolog.setUsername("nepostojeci");
        stomatolog.setPassword("nepostojeci");

        so.templateExecute(stomatolog);
    }
}