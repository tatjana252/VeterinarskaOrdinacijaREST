/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Ljubimac;
import domen.Poseta;
import domen.Stavkaposete;
import javax.ejb.Stateless;



/**
 *
 * @author hp
 */
@Stateless
public class SystemOperationSacuvajPosetu extends AbstractSystemOperation {

    public SystemOperationSacuvajPosetu() {
        super(Poseta.class);
    }

    @Override
    public void execute(Object object) throws Exception {
            Poseta poseta = (Poseta) object;
            if (em.find(Ljubimac.class, poseta.getLjubimacid().getLjubimacid()) == null) {
                throw new Exception();
            }
            poseta.setPosetaid(0);
            if(poseta.getStavkaposeteList().isEmpty()){
                throw new Exception();
            }
                    
            for (Stavkaposete stavkaposete : poseta.getStavkaposeteList()) {
                stavkaposete.setPoseta(poseta);
            }
            em.persist(poseta);
            em.flush();
    }

   
    
}
