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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import session.Session;
import transfer.Request;
import transfer.Response;
import transfer.util.ResponseStatus;
import transfer.util.Operation;

/**
 *
 * @author Asus
 */
public class ClientController {

    private static ClientController instance;

    private ClientController() {
    }

    public static ClientController getInstance() {
        if (instance == null) {
            instance = new ClientController();
        }
        return instance;
    }

    public Stomatolog login(Stomatolog stomatolog) throws Exception {
        return (Stomatolog) sendRequest(Operation.LOGIN, stomatolog);
    }

    public void logout(Stomatolog ulogovani) throws Exception {
        sendRequest(Operation.LOGOUT, ulogovani);
    }

    public void addKlijent(Klijent klijent) throws Exception {
        sendRequest(Operation.ADD_KLIJENT, klijent);
    }

    public void addUsluga(Usluga usluga) throws Exception {
        sendRequest(Operation.ADD_USLUGA, usluga);
    }

    public void addTermin(Termin t) throws Exception {
        sendRequest(Operation.ADD_TERMIN, t);
    }
    
    public void deleteKlijent(Klijent klijent) throws Exception {
        sendRequest(Operation.DELETE_KLIJENT, klijent);
    }

    public void deleteUsluga(Usluga usluga) throws Exception {
        sendRequest(Operation.DELETE_USLUGA, usluga);
    }

    public void deleteTermin(Termin t) throws Exception {
        sendRequest(Operation.DELETE_TERMIN, t);
    }
    
    public void updateKlijent(Klijent klijent) throws Exception {
        sendRequest(Operation.UPDATE_KLIJENT, klijent);
    }

    public void updateUsluga(Usluga usluga) throws Exception {
        sendRequest(Operation.UPDATE_USLUGA, usluga);
    }

    public void updateTermin(Termin t) throws Exception {
        sendRequest(Operation.UPDATE_TERMIN, t);
    }
    
    public ArrayList<Klijent> getAllKlijent(Klijent klijent) throws Exception {
        return (ArrayList<Klijent>) sendRequest(Operation.GET_ALL_KLIJENT, klijent);
    }

    public ArrayList<Termin> getAllTermin(Termin termin) throws Exception {
        return (ArrayList<Termin>) sendRequest(Operation.GET_ALL_TERMIN, termin);
    }

    public ArrayList<TipKlijenta> getAllTipKlijenta(TipKlijenta tipKlijenta) throws Exception {
        return (ArrayList<TipKlijenta>) sendRequest(Operation.GET_ALL_TIP_KLIJENTA, tipKlijenta);
    }

    public ArrayList<Usluga> getAllUsluga(Usluga usluga) throws Exception {
        return (ArrayList<Usluga>) sendRequest(Operation.GET_ALL_USLUGA, usluga);
    }

    private Object sendRequest(int operation, Object data) throws Exception {
        Request request = new Request(operation, data);

        // Posalji zahtev
        ObjectOutputStream out = new ObjectOutputStream(Session.getInstance().getSocket().getOutputStream());
        out.writeObject(request);

        // Primi odgovor
        ObjectInputStream in = new ObjectInputStream(Session.getInstance().getSocket().getInputStream());
        Response response = (Response) in.readObject();

        if (response.getResponseStatus().equals(ResponseStatus.Error)) {
            throw response.getException();
        } else {
            return response.getData();
        }

    }

}
