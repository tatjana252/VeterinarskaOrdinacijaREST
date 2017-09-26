/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Ljubimac;
import domen.Usluga;
import domen.Vlasnik;
import javax.ejb.Stateless;

@Stateless
public class SystemOperationIzmeniLjubimca extends AbstractSystemOperation<Ljubimac> {

    public SystemOperationIzmeniLjubimca() {
        super(Ljubimac.class);
    }

    @Override
    public void execute(Object object) throws Exception {
            Ljubimac ljubimac = (Ljubimac) object;
            if(em.find(Ljubimac.class, ljubimac.getLjubimacid()) == null){
                throw new Exception();
            }
            Vlasnik vlasnik = ljubimac.getVlasnikid();
            if (ljubimac.getVlasnikid().getVlasnikid() == -1) {
                em.persist(vlasnik);
                em.flush();
                ljubimac.setVlasnikid(vlasnik);
            }else{
                em.merge(vlasnik);
            }
            if (ljubimac.getSifracipa() != null && ljubimac.getSifracipa().isEmpty()) {
                ljubimac.setSifracipa(null);
            }
            em.merge(ljubimac);
            em.flush();

    }

}
