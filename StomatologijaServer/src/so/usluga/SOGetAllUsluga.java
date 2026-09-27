/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.usluga;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Usluga;
import java.util.ArrayList;
import so.AbstractSO;

/**
 *
 * @author Asus
 */
public class SOGetAllUsluga extends AbstractSO {

    private ArrayList<Usluga> lista;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Usluga)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Usluga!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        ArrayList<AbstractDomainObject> usluge = DBBroker.getInstance().select(ado);
        lista = (ArrayList<Usluga>) (ArrayList<?>) usluge;
    }

    public ArrayList<Usluga> getLista() {
        return lista;
    }

}
