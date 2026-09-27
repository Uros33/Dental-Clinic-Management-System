package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Termin extends AbstractDomainObject {

    private int terminID;
    private Date datumVremePocetka;
    private double iznosBezPopusta;
    private double popust;
    private double konacanIznos;
    private Stomatolog stomatolog;
    private Klijent klijent;
    private ArrayList<StavkaTermina> stavkeTermina;

    public Termin() {
    }

    public Termin(int terminID, Date datumVremePocetka, double iznosBezPopusta, double popust, double konacanIznos, 
            Stomatolog stomatolog, Klijent klijent, ArrayList<StavkaTermina> stavkeTermina) {
        this.terminID = terminID;
        this.datumVremePocetka = datumVremePocetka;
        this.iznosBezPopusta = iznosBezPopusta;
        this.popust = popust;
        this.konacanIznos = konacanIznos;
        this.stomatolog = stomatolog;
        this.klijent = klijent;
        this.stavkeTermina = stavkeTermina;
    }

    public int getTerminID() {
        return terminID;
    }

    public void setTerminID(int terminID) {
        this.terminID = terminID;
    }

    public Date getDatumVremePocetka() {
        return datumVremePocetka;
    }

    public void setDatumVremePocetka(Date datumVremePocetka) {
        this.datumVremePocetka = datumVremePocetka;
    }

    public double getIznosBezPopusta() {
        return iznosBezPopusta;
    }

    public void setIznosBezPopusta(double iznosBezPopusta) {
        this.iznosBezPopusta = iznosBezPopusta;
    }

    public double getPopust() {
        return popust;
    }

    public void setPopust(double popust) {
        this.popust = popust;
    }

    public double getKonacanIznos() {
        return konacanIznos;
    }

    public void setKonacanIznos(double konacanIznos) {
        this.konacanIznos = konacanIznos;
    }

    public Stomatolog getStomatolog() {
        return stomatolog;
    }

    public void setStomatolog(Stomatolog stomatolog) {
        this.stomatolog = stomatolog;
    }

    public Klijent getKlijent() {
        return klijent;
    }

    public void setKlijent(Klijent klijent) {
        this.klijent = klijent;
    }

    @Override
    public String nazivTabele() {
        return " termin ";
    }

    @Override
    public String alijas() {
        return " t ";
    }

    @Override
    public String join() {
        return " JOIN STOMATOLOG S ON (S.STOMATOLOGID = t.STOMATOLOGID) "
                + " JOIN Klijent kl ON (kl.KlijentID = t.KlijentID) "
                + " JOIN TipKlijenta tk ON (tk.TipKlijentaID = kl.TipKlijentaID) ";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();
        while (rs.next()) {
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
            lista.add(t);
        }
        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (DatumVremePocetka, IznosBezPopusta, Popust, KonacanIznos, StomatologID, KlijentID) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + new Timestamp(datumVremePocetka.getTime()) + "', "
                + iznosBezPopusta + ", " + popust + ", " + konacanIznos + ", "
                + stomatolog.getStomatologID() + ", " + klijent.getKlijentID();
    }

    @Override
    public String vrednostiZaUpdate() {
        return " DatumVremePocetka = '" + new Timestamp(datumVremePocetka.getTime()) + "', "
                + "IznosBezPopusta = " + iznosBezPopusta + ", "
                + "KonacanIznos = " + konacanIznos + " ";
    }

    @Override
    public String uslov() {
        return " terminID = " + terminID;
    }

    @Override
    public String dodatniUslov() {
        if (terminID != -1) {
            return " WHERE terminID = " + terminID;
        }
        return " WHERE LOWER(KL.IME) LIKE '%" + klijent.getIme() + "%' "
                + "AND LOWER(KL.PREZIME) LIKE '%" + klijent.getPrezime()+ "%'";
    }

    @Override
    public String orderBy() {
        return " ORDER BY TERMINID ASC ";
    }

    public ArrayList<StavkaTermina> getStavkeTermina() {
        return stavkeTermina;
    }

    public void setStavkeTermina(ArrayList<StavkaTermina> stavkeTermina) {
        this.stavkeTermina = stavkeTermina;
    }
}
