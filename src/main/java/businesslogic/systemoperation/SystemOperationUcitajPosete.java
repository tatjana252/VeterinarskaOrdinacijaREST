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
public class SystemOperationUcitajPosete extends AbstractSystemOperation {

    public SystemOperationUcitajPosete() {
        super(Poseta.class);
    }

    @Override
    public void execute(Object object) throws Exception {
        result = String.valueOf(em.createNamedQuery("Poseta.findAll").getResultList().size());
    }


    
}
