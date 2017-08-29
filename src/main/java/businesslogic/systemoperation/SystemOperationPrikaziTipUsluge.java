/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Poseta;
import domen.Tipusluge;
import javax.ejb.Stateless;



/**
 *
 * @author hp
 */
@Stateless
public class SystemOperationPrikaziTipUsluge extends AbstractSystemOperation {

    public SystemOperationPrikaziTipUsluge() {
        super(null);
    }

    @Override
    public void execute(Object object) throws Exception {
         Tipusluge zahtev = (Tipusluge) object;
            Tipusluge tu = (Tipusluge) 
                    em.createQuery("SELECT tu FROM Tipusluge tu WHERE tu.naziv = :naziv ")
                            .setParameter("naziv", zahtev.getNaziv())
                            .getSingleResult();
            result = tu;
    }

    
    
}
