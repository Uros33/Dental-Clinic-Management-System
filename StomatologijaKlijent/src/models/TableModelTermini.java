/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controller.ClientController;
import domain.Klijent;
import domain.Stomatolog;
import domain.Termin;
import domain.TipKlijenta;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Asus
 */
public class TableModelTermini extends AbstractTableModel implements Runnable {

    private ArrayList<Termin> lista;
    private String[] kolone = {"ID", "Klijent", "Datum i vreme", "Ukupna cena"};
    private String parametarIme = "";
    private String parametarPrezime = "";
    private Termin termin = new Termin(-1, null, 0, 0, 0,
            new Stomatolog(-1, "", "", "", ""),
            new Klijent(-1, "", "", "", "", new TipKlijenta(-1, "")), null);

    public TableModelTermini() {
        try {
            lista = ClientController.getInstance()
                    .getAllTermin(new Termin(-1, null, 0, 0, 0,
                            new Stomatolog(-1, "", "", "", ""),
                            new Klijent(-1, "", "", "", "", new TipKlijenta(-1, "")), null));
        } catch (Exception ex) {
            Logger.getLogger(TableModelTermini.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        Termin t = lista.get(row);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        switch (column) {
            case 0:
                return t.getTerminID();
            case 1:
                return t.getKlijent();
            case 2:
                return sdf.format(t.getDatumVremePocetka());
            case 3:
                return t.getKonacanIznos() + "din";

            default:
                return null;
        }
    }

    public Termin getSelectedTermin(int row) {
        return lista.get(row);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(10000);
                refreshTable();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(TableModelTermini.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setParametarIme(String ime) {
        this.parametarIme = ime;
        refreshTable();
    }

    public void setParametarPrezime(String prezime) {
        this.parametarPrezime = prezime;
        refreshTable();
    }

    public void refreshTable() {
        try {
            
            termin.getKlijent().setIme(parametarIme.toLowerCase());
            termin.getKlijent().setPrezime(parametarPrezime.toLowerCase());
            
            lista = ClientController.getInstance().getAllTermin(termin);
            fireTableDataChanged();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Termin> getLista() {
        return lista;
    }

}
