/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Poseta;
import javax.ejb.Stateless;

/**
 *
 * @author hp
 */
@Stateless
public class SystemOperationPrikaziPosetu extends AbstractSystemOperation {

    public SystemOperationPrikaziPosetu() {
        super(Poseta.class);
    }

    @Override
    public void execute(Object object) throws Exception {
        Poseta poseta = (Poseta) object;
        poseta = em.find(Poseta.class, poseta.getPosetaid());
        em.refresh(poseta);
        result = poseta;
    }

}
