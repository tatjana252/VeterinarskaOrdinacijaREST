/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Usluga;
import javax.ejb.Stateless;

/**
 *
 * @author hp
 */
@Stateless
public class SystemOperationIzmeniUslugu extends AbstractSystemOperation {

    public SystemOperationIzmeniUslugu() {
        super(Usluga.class);
    }

    @Override
    public void execute(Object object) throws Exception {
        Usluga usluga = (Usluga) object;
        if (em.find(Usluga.class, usluga.getUslugaid()) == null) {
            throw new Exception();
        }
        em.merge(usluga);

    }

}
