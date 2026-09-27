/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import domain.StavkaTermina;
import domain.Usluga;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Asus
 */
public class TableModelStavkeTermina extends AbstractTableModel {

    private ArrayList<StavkaTermina> lista;
    private String[] kolone = {"Rb", "Usluga", "Iznos", "Napomena"};
    private int rb;

    public TableModelStavkeTermina() {
        lista = new ArrayList<>();
    }

    public TableModelStavkeTermina(ArrayList<StavkaTermina> stavkeTermina) {
        lista = stavkeTermina;
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int i) {
        return kolone[i];
    }

    @Override
    public Object getValueAt(int row, int column) {
        StavkaTermina st = lista.get(row);

        switch (column) {
            case 0:
                return st.getRb();
            case 1:
                return st.getUsluga().getNaziv();
            case 2:
                return st.getIznos() + "din";
            case 3:
                return st.getNapomena();

            default:
                return null;
        }
    }

    public void dodajStavku(StavkaTermina st) {
        rb = lista.size();
        st.setRb(++rb);
        lista.add(st);
        fireTableDataChanged();
    }

    public void obrisiStavku(int row) {
        lista.remove(row);

        rb = 0;
        for (StavkaTermina stavkaTermina : lista) {
            stavkaTermina.setRb(++rb);
        }

        fireTableDataChanged();
    }

    public double vratiUkupanIznos() {
        double ukupanIznos = 0;

        for (StavkaTermina stavkaTermina : lista) {
            ukupanIznos += stavkaTermina.getIznos();
        }

        return ukupanIznos;
    }

    public ArrayList<StavkaTermina> getLista() {
        return lista;
    }

    public boolean postojiUsluga(Usluga u) {
        for (StavkaTermina stavkaTermina : lista) {
            if (stavkaTermina.getUsluga().equals(u)) {
                return true;
            }
        }
        return false;
    }

}
