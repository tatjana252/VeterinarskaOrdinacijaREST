/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Tipusluge;
import domen.Usluga;
import java.util.List;
import javax.ejb.Stateless;



/**
 *
 * @author hp
 */
@Stateless
public class SystemOperationUcitajTipoveUsluga extends AbstractSystemOperation {

    public SystemOperationUcitajTipoveUsluga() {
        super(Usluga.class);
    }

    @Override
    public void execute(Object object) throws Exception {
        result = em.createNamedQuery("Tipusluge.findAll").getResultList();
    }


    
}
