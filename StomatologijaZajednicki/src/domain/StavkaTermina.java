package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StavkaTermina extends AbstractDomainObject {

    private Termin termin;
    private int rb;
    private Usluga usluga;
    private String napomena;
    private double cena;
    private double iznos;

    public StavkaTermina() {
    }

    public StavkaTermina(Termin termin, int rb, Usluga usluga,
            String napomena, double cena, double iznos) {
        this.termin = termin;
        this.rb = rb;
        this.usluga = usluga;
        this.napomena = napomena;
        this.cena = cena;
        this.iznos = iznos;
    }

    public Termin getTermin() {
        return termin;
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    public Usluga getUsluga() {
        return usluga;
    }

    public void setUsluga(Usluga usluga) {
        this.usluga = usluga;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    @Override
    public String nazivTabele() {
        return " StavkaTermina ";
    }

    @Override
    public String alijas() {
        return " st ";
    }

    @Override
    public String join() {
        return " JOIN TERMIN T ON (T.TERMINID = ST.TERMINID) "
                + " JOIN STOMATOLOG S ON (S.STOMATOLOGID = t.STOMATOLOGID) "
                + " JOIN Klijent kl ON (kl.KlijentID = t.KlijentID) "
                + " JOIN TipKlijenta tk ON (tk.TipKlijentaID = kl.TipKlijentaID) "
                + " JOIN USLUGA U ON (U.USLUGAID = ST.USLUGAID) ";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();
        while (rs.next()) {
            Usluga u = new Usluga(
                    rs.getInt("u.USLUGAID"),
                    rs.getString("u.Naziv"),
                    rs.getString("u.Opis"),
                    rs.getDouble("u.Cena"),
                    rs.getInt("u.TrajanjeMin")
            );
            Stomatolog s = new Stomatolog(
                    rs.getInt("s.STOMATOLOGID"),
                    rs.getString("s.Ime"),
                    rs.getString("s.Prezime"),
                    rs.getString("s.Username"),
                    rs.getString("s.Password")
            );
            TipKlijenta tk = new TipKlijenta(
                    rs.getInt("tk.TipKlijentaID"),
                    rs.getString("tk.Naziv")
            );
            Klijent kl = new Klijent(
                    rs.getInt("kl.KlijentID"),
                    rs.getString("kl.Ime"),
                    rs.getString("kl.Prezime"),
                    rs.getString("kl.Email"),
                    rs.getString("kl.Telefon"),
                    tk
            );
            Termin t = new Termin(
                    rs.getInt("t.TerminID"),
                    rs.getTimestamp("t.DatumVremePocetka"),
                    rs.getDouble("t.IznosBezPopusta"),
                    rs.getDouble("t.Popust"),
                    rs.getDouble("t.KonacanIznos"),
                    s, kl, new ArrayList<>()
            );
            StavkaTermina st = new StavkaTermina(
                    t,
                    rs.getInt("st.Rb"),
                    u,
                    rs.getString("st.Napomena"),
                    rs.getDouble("st.Cena"),
                    rs.getDouble("st.Iznos")
            );

            lista.add(st);
        }
        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (TerminID, Rb, UslugaID, Napomena, Cena, Iznos) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return termin.getTerminID() + ", "
                + rb + ", "
                + usluga.getUslugaID() + ", "
                + "'" + napomena + "', "
                + cena + ", " + iznos;
    }

    @Override
    public String vrednostiZaUpdate() {
        return " uslugaID = " + usluga.getUslugaID() + ", "
                + "Napomena = '" + napomena + "', "
                + "Cena = " + cena + ", "
                + "Iznos = " + iznos + " ";
    }

    @Override
    public String uslov() {
        return " TerminID = " + termin.getTerminID() + " AND Rb = " + rb;
    }

    @Override
    public String dodatniUslov() {
        return " WHERE T.TerminID = " + termin.getTerminID();
    }

    @Override
    public String orderBy() {
        return " ORDER BY RB ASC ";
    }
}
