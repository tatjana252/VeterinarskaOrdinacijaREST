/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Ljubimac;
import javax.ejb.Stateless;

/**
 *
 * @author hp
 */
@Stateless
public class SystemOperationPrikaziLjubimca extends AbstractSystemOperation {

    public SystemOperationPrikaziLjubimca() {
        super(null);
    }

    @Override
    public void execute(Object object) throws Exception {
        Ljubimac ljubimac = (Ljubimac) object;
        Ljubimac lj = (Ljubimac) em.find(Ljubimac.class, ljubimac.getLjubimacid());
        em.refresh(lj);
        result = lj;
    }

}
