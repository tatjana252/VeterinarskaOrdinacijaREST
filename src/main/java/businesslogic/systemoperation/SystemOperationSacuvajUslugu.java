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
public class SystemOperationSacuvajUslugu extends AbstractSystemOperation{

    public SystemOperationSacuvajUslugu() {
        super(Usluga.class);
    }

    @Override
    public void execute(Object object) throws Exception {
        Usluga usluga = (Usluga) object;
        if (!em.createQuery("SELECT u.uslugaid FROM Usluga u WHERE u.naziv = :naziv AND u.tipuslugeid = :tipuslugeid")
                    .setParameter("naziv", usluga.getNaziv()).setParameter("tipuslugeid", usluga.getTipuslugeid())
                    .setMaxResults(1)
                    .getResultList()
                    .isEmpty()) {
                throw new Exception();
            }
            em.persist(usluga);
    }



    
}
