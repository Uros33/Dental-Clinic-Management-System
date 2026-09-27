/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Asus
 */
public class Stomatolog extends AbstractDomainObject {

    private int stomatologID;
    private String ime;
    private String prezime;
    private String username;
    private String password;

    public Stomatolog() {
    }

    public Stomatolog(int stomatologID, String ime, String prezime, String username, String password) {
        this.stomatologID = stomatologID;
        this.ime = ime;
        this.prezime = prezime;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Stomatolog other = (Stomatolog) obj;
        if (!Objects.equals(this.stomatologID, other.stomatologID)) {
            return false;
        }
        return true;
    }

    public int getStomatologID() {
        return stomatologID;
    }

    public void setStomatologID(int stomatologID) {
        this.stomatologID = stomatologID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    @Override
    public String nazivTabele() {
        return " Stomatolog ";
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
            Stomatolog s = new Stomatolog(rs.getInt("stomatologID"),
                    rs.getString("Ime"), rs.getString("Prezime"),
                    rs.getString("Username"), rs.getString("Password"));

            lista.add(s);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (Ime, Prezime, Username, Password) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + ime + "', '" + prezime + "', "
                + "'" + username + "', '" + password + "'";
    }

    @Override
    public String vrednostiZaUpdate() {
        return " Ime = '" + ime + "', Prezime = '" + prezime + "', "
                + "Username = '" + username + "', Password = '" + password + "' ";
    }

    @Override
    public String uslov() {
        return " stomatologID = " + stomatologID;
    }

    @Override
    public String dodatniUslov() {
        if (stomatologID != -1) {
            return " WHERE stomatologID = " + stomatologID;
        }
        return " WHERE LOWER(IME) LIKE '%" + ime + "%' "
                + "AND LOWER(PREZIME) LIKE '%" + prezime + "%' "
                + "AND LOWER(USERNAME) LIKE '%" + username + "%' ";
    }

    @Override
    public String orderBy() {
        return " ORDER BY STOMATOLOGID ASC ";
    }

}
