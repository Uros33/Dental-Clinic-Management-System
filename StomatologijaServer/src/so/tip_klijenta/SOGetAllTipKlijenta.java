/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.tip_klijenta;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.TipKlijenta;
import java.util.ArrayList;
import so.AbstractSO;

/**
 *
 * @author Asus
 */
public class SOGetAllTipKlijenta extends AbstractSO {

    private ArrayList<TipKlijenta> lista;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof TipKlijenta)) {
            throw new Exception("Prosledjeni objekat nije instanca klase TipKlijenta!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        ArrayList<AbstractDomainObject> tipoviKlijenta = DBBroker.getInstance().select(ado);
        lista = (ArrayList<TipKlijenta>) (ArrayList<?>) tipoviKlijenta;
    }

    public ArrayList<TipKlijenta> getLista() {
        return lista;
    }

}
