/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controller.ClientController;
import domain.TipKlijenta;
import domain.Usluga;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Asus
 */
public class TableModelTipaKlijenta extends AbstractTableModel implements Runnable {

    private ArrayList<TipKlijenta> lista;
    private String[] kolone = {"ID", "Naziv"};
    private String parametarNaziv = "";
    private TipKlijenta tipKlijenta = new TipKlijenta(-1, "");

    public TableModelTipaKlijenta() {
        try {
            lista = ClientController.getInstance()
                    .getAllTipKlijenta(new TipKlijenta(-1, ""));
        } catch (Exception ex) {
            Logger.getLogger(TableModelTipaKlijenta.class.getName()).log(Level.SEVERE, null, ex);
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
        TipKlijenta tk = lista.get(row);

        switch (column) {
            case 0:
                return tk.getTipKlijentaID();
            case 1:
                return tk.getNaziv();
            
            default:
                return null;
        }
    }

    public TipKlijenta getSelectedTipKlijenta(int row) {
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
            Logger.getLogger(TableModelTipaKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setParametarNaziv(String naziv) {
        this.parametarNaziv = naziv;
        refreshTable();
    }

    public void refreshTable() {
        try {
            
            tipKlijenta.setNaziv(parametarNaziv.toLowerCase());
            
            lista = ClientController.getInstance().getAllTipKlijenta(tipKlijenta);
            fireTableDataChanged();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<TipKlijenta> getLista() {
        return lista;
    }

}
