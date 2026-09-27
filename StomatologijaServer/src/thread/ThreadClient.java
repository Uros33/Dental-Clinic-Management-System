/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import controller.ServerController;
import domain.Klijent;
import domain.Stomatolog;
import domain.Termin;
import domain.TipKlijenta;
import domain.Usluga;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import transfer.Request;
import transfer.Response;
import transfer.util.ResponseStatus;
import transfer.util.Operation;

/**
 *
 * @author Asus
 */
public class ThreadClient extends Thread {

    private Socket socket;

    ThreadClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                // primiZahtev();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Request request = (Request) in.readObject();
                // switch case
                Response response = handleRequest(request);
                // posaljiOdgovor();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response handleRequest(Request request) {
        Response response = new Response(null, null, ResponseStatus.Success);
        try {
            switch (request.getOperation()) {
                case Operation.ADD_KLIJENT:
                    ServerController.getInstance().addKlijent((Klijent) request.getData());
                    break;
                case Operation.ADD_USLUGA:
                    ServerController.getInstance().addUsluga((Usluga) request.getData());
                    break;
                case Operation.ADD_TERMIN:
                    ServerController.getInstance().addTermin((Termin) request.getData());
                    break;
                case Operation.DELETE_KLIJENT:
                    ServerController.getInstance().deleteKlijent((Klijent) request.getData());
                    break;
                case Operation.DELETE_USLUGA:
                    ServerController.getInstance().deleteUsluga((Usluga) request.getData());
                    break;
                case Operation.DELETE_TERMIN:
                    ServerController.getInstance().deleteTermin((Termin) request.getData());
                    break;
                case Operation.UPDATE_KLIJENT:
                    ServerController.getInstance().updateKlijent((Klijent) request.getData());
                    break;
                case Operation.UPDATE_USLUGA:
                    ServerController.getInstance().updateUsluga((Usluga) request.getData());
                    break;
                case Operation.UPDATE_TERMIN:
                    ServerController.getInstance().updateTermin((Termin) request.getData());
                    break;
                case Operation.GET_ALL_KLIJENT:
                    response.setData(ServerController.getInstance().getAllKlijent((Klijent) request.getData()));
                    break;
                case Operation.GET_ALL_TIP_KLIJENTA:
                    response.setData(ServerController.getInstance().getAllTipKlijenta((TipKlijenta) request.getData()));
                    break;
                case Operation.GET_ALL_TERMIN:
                    response.setData(ServerController.getInstance().getAllTermin((Termin) request.getData()));
                    break;
                case Operation.GET_ALL_USLUGA:
                    response.setData(ServerController.getInstance().getAllUsluga((Usluga) request.getData()));
                    break;
                case Operation.LOGIN:
                    Stomatolog stomatolog = (Stomatolog) request.getData();
                    Stomatolog stom = ServerController.getInstance().login(stomatolog);
                    response.setData(stom);
                    break;
                case Operation.LOGOUT:
                    Stomatolog ulogovani = (Stomatolog) request.getData();
                    ServerController.getInstance().logout(ulogovani);
                    break;
                default:
                    return null;
            }
        } catch (Exception ex) {
            response.setResponseStatus(ResponseStatus.Error);
            response.setException(ex);
        }
        return response;
    }

}
