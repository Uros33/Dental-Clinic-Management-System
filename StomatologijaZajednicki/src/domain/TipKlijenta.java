package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TipKlijenta extends AbstractDomainObject {

    private int tipKlijentaID;
    private String naziv;

    public TipKlijenta() {
    }

    public TipKlijenta(int tipKlijentaID, String naziv) {
        this.tipKlijentaID = tipKlijentaID;
        this.naziv = naziv;
    }

    public int getTipKlijentaID() {
        return tipKlijentaID;
    }

    public void setTipKlijentaID(int tipKlijentaID) {
        this.tipKlijentaID = tipKlijentaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return naziv;
    }

    @Override
    public String nazivTabele() {
        return " TipKlijenta ";
    }

    @Override
    public String alijas() {
        return " tk ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();
        while (rs.next()) {
            TipKlijenta tk = new TipKlijenta(
                    rs.getInt("tk.TipKlijentaID"),
                    rs.getString("tk.Naziv"));

            lista.add(tk);
        }
        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (Naziv) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + naziv + "' ";
    }

    @Override
    public String vrednostiZaUpdate() {
        return " Naziv = '" + naziv + "' ";
    }

    @Override
    public String uslov() {
        return " TipKlijentaID = " + tipKlijentaID;
    }

    @Override
    public String dodatniUslov() {
        if (tipKlijentaID != -1) {
            return " WHERE tipKlijentaID = " + tipKlijentaID;
        }
        return " WHERE LOWER(NAZIV) LIKE '%" + naziv + "%' ";
    }

    @Override
    public String orderBy() {
        return " ORDER BY TIPKLIJENTAID ASC ";
    }
}
