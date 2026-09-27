/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.login;

import controller.ServerController;
import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Stomatolog;
import java.util.ArrayList;
import so.AbstractSO;

/**
 *
 * @author Asus
 */
public class SOLogin extends AbstractSO {

    Stomatolog ulogovani;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Stomatolog)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Stomatolog!");
        }

        Stomatolog s = (Stomatolog) ado;

        for (Stomatolog stomatolog : ServerController.getInstance().getUlogovaniStomatolozi()) {
            if (stomatolog.getUsername().equals(s.getUsername())) {
                throw new Exception("Ovaj stomatolog je vec ulogovan na sistem!");
            }
        }

    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {

        Stomatolog s = (Stomatolog) ado;

        ArrayList<Stomatolog> listaZap
                = (ArrayList<Stomatolog>) (ArrayList<?>) DBBroker.getInstance().select(ado);

        for (Stomatolog stomatolog : listaZap) {
            if (stomatolog.getUsername().equals(s.getUsername())
                    && stomatolog.getPassword().equals(s.getPassword())) {
                ulogovani = stomatolog;
                ServerController.getInstance().getUlogovaniStomatolozi().add(stomatolog);
                return;
            }
        }

        throw new Exception("Ne postoji stomatolog sa tim kredencijalima.");

    }

    public Stomatolog getUlogovani() {
        return ulogovani;
    }

}
