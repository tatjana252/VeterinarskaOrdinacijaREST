/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Vlasnik;
import javax.ejb.Stateless;



/**
 *
 * @author hp
 */
@Stateless
public class SystemOperationUcitajVlasnike extends AbstractSystemOperation {

    public SystemOperationUcitajVlasnike() {
        super(Vlasnik.class);
    }

    @Override
    public void execute(Object object) throws Exception {
     result = em.createNamedQuery("Vlasnik.findAll").getResultList();
            }

    
    
}
