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
public class SOAddUsluga extends AbstractSO {

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Usluga)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Usluga!");
        }

        Usluga u = (Usluga) ado;

        if (u.getCena() <= 0) {
            throw new Exception("Cena usluge mora biti veca od 0!");
        }

        if (u.getTrajanjeMin() < 15 || u.getTrajanjeMin() > 180) {
            throw new Exception("Trajanje usluge mora biti izmedju 15 i 180 minuta!");
        }

        ArrayList<Usluga> usluge = (ArrayList<Usluga>) (ArrayList<?>) DBBroker.getInstance()
                .select(new Usluga(-1, "", "", 0, 0));

        for (Usluga usluga : usluge) {
            if (usluga.getNaziv().equals(u.getNaziv())) {
                throw new Exception("Usluga sa tim nazivom vec postoji!");
            }
        }

    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        DBBroker.getInstance().insert(ado);
    }

}
