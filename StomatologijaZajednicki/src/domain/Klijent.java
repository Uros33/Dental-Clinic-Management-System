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
public class Klijent extends AbstractDomainObject {

    private int klijentID;
    private String ime;
    private String prezime;
    private String email;
    private String telefon;
    private TipKlijenta tipKlijenta;

    @Override
    public String toString() {
        return ime + " " + prezime + " (Tip klijenta: " + tipKlijenta + ")";
    }

    public Klijent(int klijentID, String ime, String prezime, String email, String telefon, TipKlijenta tipKlijenta) {
        this.klijentID = klijentID;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.telefon = telefon;
        this.tipKlijenta = tipKlijenta;
    }

    public Klijent() {
    }

    @Override
    public String nazivTabele() {
        return " Klijent ";
    }

    @Override
    public String alijas() {
        return " k ";
    }

    @Override
    public String join() {
        return " JOIN TIPKLIJENTA TK ON (TK.TIPKLIJENTAID = K.TIPKLIJENTAID) ";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {

            TipKlijenta tk = new TipKlijenta(rs.getInt("tipKlijentaID"),
                    rs.getString("tk.naziv"));

            Klijent k = new Klijent(rs.getInt("klijentID"), rs.getString("ime"),
                    rs.getString("prezime"), rs.getString("email"),
                    rs.getString("telefon"), tk);

            lista.add(k);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (Ime, Prezime, email, telefon, tipKlijentaID) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + ime + "', '" + prezime + "', "
                + "'" + email + "', '" + telefon + "', " + tipKlijenta.getTipKlijentaID() + " ";
    }

    @Override
    public String vrednostiZaUpdate() {
        return " email = '" + email + "', telefon = '" + telefon + "', "
                + " tipKlijentaID = " + tipKlijenta.getTipKlijentaID();
    }

    @Override
    public String uslov() {
        return " klijentID = " + klijentID;
    }

    @Override
    public String dodatniUslov() {
        if (klijentID != -1) {
            return " WHERE KLIJENTID = " + klijentID;
        }
        return " WHERE LOWER(IME) LIKE '%" + ime + "%' "
                + "AND LOWER(PREZIME) LIKE '%" + prezime + "%' "
                + "AND LOWER(EMAIL) LIKE '%" + email + "%' ";
    }

    @Override
    public String orderBy() {
        return " ORDER BY KLIJENTID ASC ";
    }

    public int getKlijentID() {
        return klijentID;
    }

    public void setKlijentID(int klijentID) {
        this.klijentID = klijentID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public TipKlijenta getTipKlijenta() {
        return tipKlijenta;
    }

    public void setTipKlijenta(TipKlijenta tipKlijenta) {
        this.tipKlijenta = tipKlijenta;
    }

}
