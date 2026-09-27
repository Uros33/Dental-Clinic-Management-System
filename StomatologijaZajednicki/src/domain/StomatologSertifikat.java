/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Asus
 */
public class StomatologSertifikat extends AbstractDomainObject {

    private Sertifikat sertifikat;
    private Stomatolog stomatolog;
    private Date datumIzdavanja;

    public StomatologSertifikat(Sertifikat sertifikat, Stomatolog stomatolog, Date datumIzdavanja) {
        this.sertifikat = sertifikat;
        this.stomatolog = stomatolog;
        this.datumIzdavanja = datumIzdavanja;
    }

    public StomatologSertifikat() {
    }

    @Override
    public String nazivTabele() {
        return " StomatologSertifikat ";
    }

    @Override
    public String alijas() {
        return " ss ";
    }

    @Override
    public String join() {
        return " JOIN STOMATOLOG S ON (S.STOMATOLOGID = SS.STOMATOLOGID) "
                + "JOIN SERTIFIKAT SE ON (SE.SERTIFIKATID = SS.SERTIFIKATID) ";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {

            Stomatolog stom = new Stomatolog(
                    rs.getInt("StomatologID"),
                    rs.getString("Ime"),
                    rs.getString("Prezime"),
                    rs.getString("Username"),
                    rs.getString("Password")
            );

            Sertifikat sert = new Sertifikat(
                    rs.getInt("SertifikatID"),
                    rs.getString("Naziv")
            );

            StomatologSertifikat ss = new StomatologSertifikat(
                    sert,
                    stom,
                    rs.getDate("DatumIzdavanja")
            );

            lista.add(ss);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (SertifikatID, StomatologID, DatumIzdavanja) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return sertifikat.getSertifikatID() + ", "
                + stomatolog.getStomatologID() + ", "
                + "'" + new java.sql.Date(datumIzdavanja.getTime()) + "'";
    }

    @Override
    public String vrednostiZaUpdate() {
        return "";
    }

    @Override
    public String uslov() {
        return " StomatologID = " + stomatolog.getStomatologID()
                + " AND SertifikatID = " + sertifikat.getSertifikatID();
    }

    @Override
    public String dodatniUslov() {
        return "";
    }

    @Override
    public String orderBy() {
        return "";
    }

    public Sertifikat getSertifikat() {
        return sertifikat;
    }

    public void setSertifikat(Sertifikat sertifikat) {
        this.sertifikat = sertifikat;
    }

    public Stomatolog getStomatolog() {
        return stomatolog;
    }

    public void setStomatolog(Stomatolog stomatolog) {
        this.stomatolog = stomatolog;
    }

    public Date getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(Date datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }
}