/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Asus
 */
public class Sertifikat extends AbstractDomainObject {
    
    private int sertifikatID;
    private String naziv;

    public Sertifikat(int sertifikatID, String naziv) {
        this.sertifikatID = sertifikatID;
        this.naziv = naziv;
    }

    public Sertifikat() {
    }
    
    @Override
    public String toString() {
        return naziv;
    }

    @Override
    public String nazivTabele() {
        return " Sertifikat ";
    }

    @Override
    public String alijas() {
        return " s ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            Sertifikat s = new Sertifikat(rs.getInt("SertifikatID"),
                    rs.getString("s.naziv"));

            lista.add(s);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (naziv) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + naziv + "' ";
    }
    
    @Override
    public String vrednostiZaUpdate() {
        return " naziv = '" + naziv + "' ";
    }
    
    @Override
    public String uslov() {
        return " sertifikatID = " + sertifikatID;
    }

    @Override
    public String dodatniUslov() {
        return "";
    }

    @Override
    public String orderBy() {
        return " ORDER BY SERTIFIKATID ASC ";
    }

    public int getSertifikatID() {
        return sertifikatID;
    }

    public void setSertifikatID(int sertifikatID) {
        this.sertifikatID = sertifikatID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    
}
