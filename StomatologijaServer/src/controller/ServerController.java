/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.Klijent;
import domain.Stomatolog;
import domain.Termin;
import domain.TipKlijenta;
import domain.Usluga;
import java.util.ArrayList;
import so.klijent.SOAddKlijent;
import so.klijent.SODeleteKlijent;
import so.klijent.SOGetAllKlijent;
import so.klijent.SOUpdateKlijent;
import so.login.SOLogin;
import so.tip_klijenta.SOGetAllTipKlijenta;
import so.termin.SOAddTermin;
import so.termin.SODeleteTermin;
import so.termin.SOGetAllTermin;
import so.termin.SOUpdateTermin;
import so.usluga.SOAddUsluga;
import so.usluga.SODeleteUsluga;
import so.usluga.SOGetAllUsluga;
import so.usluga.SOUpdateUsluga;

/**
 *
 * @author Asus
 */
public class ServerController {

    private static ServerController instance;
    private ArrayList<Stomatolog> ulogovaniStomatolozi = new ArrayList<>();

    private ServerController() {
    }

    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }

    public ArrayList<Stomatolog> getUlogovaniStomatolozi() {
        return ulogovaniStomatolozi;
    }

    public void setUlogovaniStomatolozi(ArrayList<Stomatolog> ulogovaniStomatolozi) {
        this.ulogovaniStomatolozi = ulogovaniStomatolozi;
    }

    public Stomatolog login(Stomatolog stomatolog) throws Exception {
        SOLogin so = new SOLogin();
        so.templateExecute(stomatolog);
        return so.getUlogovani();
    }

    public void addKlijent(Klijent klijent) throws Exception {
        (new SOAddKlijent()).templateExecute(klijent);
    }

    public void addUsluga(Usluga usluga) throws Exception {
        (new SOAddUsluga()).templateExecute(usluga);
    }
    
    public void addTermin(Termin t) throws Exception {
        (new SOAddTermin()).templateExecute(t);
    }

    public void deleteKlijent(Klijent klijent) throws Exception {
        (new SODeleteKlijent()).templateExecute(klijent);
    }
    
    public void deleteUsluga(Usluga usluga) throws Exception {
        (new SODeleteUsluga()).templateExecute(usluga);
    }

    public void deleteTermin(Termin t) throws Exception {
        (new SODeleteTermin()).templateExecute(t);
    }

    public void updateKlijent(Klijent klijent) throws Exception {
        (new SOUpdateKlijent()).templateExecute(klijent);
    }
    
    public void updateUsluga(Usluga usluga) throws Exception {
        (new SOUpdateUsluga()).templateExecute(usluga);
    }

    public void updateTermin(Termin t) throws Exception {
        (new SOUpdateTermin()).templateExecute(t);
    }

    public ArrayList<Klijent> getAllKlijent(Klijent klijent) throws Exception {
        SOGetAllKlijent so = new SOGetAllKlijent();
        so.templateExecute(klijent);
        return so.getLista();
    }

    public ArrayList<Termin> getAllTermin(Termin termin) throws Exception {
        SOGetAllTermin so = new SOGetAllTermin();
        so.templateExecute(termin);
        return so.getLista();
    }

    public ArrayList<Usluga> getAllUsluga(Usluga usluga) throws Exception {
        SOGetAllUsluga so = new SOGetAllUsluga();
        so.templateExecute(usluga);
        return so.getLista();
    }

    public ArrayList<TipKlijenta> getAllTipKlijenta(TipKlijenta tipKlijenta) throws Exception {
        SOGetAllTipKlijenta so = new SOGetAllTipKlijenta();
        so.templateExecute(tipKlijenta);
        return so.getLista();
    }

    public void logout(Stomatolog ulogovani) {
        ulogovaniStomatolozi.remove(ulogovani);
    }

}
