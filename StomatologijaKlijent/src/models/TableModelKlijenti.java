/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controller.ClientController;
import domain.Klijent;
import domain.TipKlijenta;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Asus
 */
public class TableModelKlijenti extends AbstractTableModel implements Runnable {

    private ArrayList<Klijent> lista;
    private String[] kolone = {"ID", "Ime", "Prezime", "Email", "Telefon", "Tip klijenta"};
    private String parametarIme = "";
    private String parametarPrezime = "";
    private String parametarEmail = "";
    private Klijent klijent = new Klijent(-1, "", "", "", "", new TipKlijenta(-1, ""));

    public TableModelKlijenti() {
        try {
            lista = ClientController.getInstance()
                    .getAllKlijent(new Klijent(-1, "", "", "", "", new TipKlijenta(-1, "")));
        } catch (Exception ex) {
            Logger.getLogger(TableModelKlijenti.class.getName()).log(Level.SEVERE, null, ex);
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
        Klijent k = lista.get(row);

        switch (column) {
            case 0:
                return k.getKlijentID();
            case 1:
                return k.getIme();
            case 2:
                return k.getPrezime();
            case 3:
                return k.getEmail();
            case 4:
                return k.getTelefon();
            case 5:
                return k.getTipKlijenta();

            default:
                return null;
        }
    }

    public Klijent getSelectedKlijent(int row) {
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
            Logger.getLogger(TableModelKlijenti.class.getName()).log(Level.SEVERE, null, ex);
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

    public void setParametarEmail(String email) {
        this.parametarEmail = email;
        refreshTable();
    }

    public void refreshTable() {
        try {
            
            klijent.setIme(parametarIme.toLowerCase());
            klijent.setPrezime(parametarPrezime.toLowerCase());
            klijent.setEmail(parametarEmail.toLowerCase());
            
            lista = ClientController.getInstance().getAllKlijent(klijent);
            fireTableDataChanged();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Klijent> getLista() {
        return lista;
    }

}
