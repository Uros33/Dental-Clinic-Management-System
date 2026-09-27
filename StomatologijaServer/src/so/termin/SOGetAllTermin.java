/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.termin;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.StavkaTermina;
import domain.Termin;
import java.util.ArrayList;
import so.AbstractSO;

/**
 *
 * @author Asus
 */
public class SOGetAllTermin extends AbstractSO {

    private ArrayList<Termin> lista;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Termin)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Termin!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        ArrayList<AbstractDomainObject> termini = DBBroker.getInstance().select(ado);
        lista = (ArrayList<Termin>) (ArrayList<?>) termini;

        for (Termin trenutniTermin : lista) {

            StavkaTermina stavkaTermina = new StavkaTermina();
            stavkaTermina.setTermin(trenutniTermin);

            ArrayList<StavkaTermina> stavkeTrenutnogTermina
                    = (ArrayList<StavkaTermina>) (ArrayList<?>) DBBroker
                            .getInstance().select(stavkaTermina);

            trenutniTermin.setStavkeTermina(stavkeTrenutnogTermina);
        }

    }

    public ArrayList<Termin> getLista() {
        return lista;
    }

}
