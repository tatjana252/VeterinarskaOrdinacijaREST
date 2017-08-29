/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Ljubimac;
import domen.Vlasnik;
import javax.ejb.Stateless;


/**
 *
 * @author hp
 */
@Stateless
public class SystemOperationSacuvajLjubimca extends AbstractSystemOperation{

    public SystemOperationSacuvajLjubimca() {
        super(Ljubimac.class);
    }

    @Override
    public void execute(Object object) throws Exception {
        Ljubimac entity = (Ljubimac) object;
        Vlasnik vlasnik = entity.getVlasnikid();
            if(entity.getSifracipa() != null && entity.getSifracipa().isEmpty()){
                entity.setSifracipa(null);
            }
            if (entity.getVlasnikid().getVlasnikid() == -1) {
                em.persist(vlasnik);
                vlasnik = (Vlasnik) em.createQuery("SELECT v FROM Vlasnik v WHERE v.jmbg = :jmbg AND v.ime = :ime AND v.prezime = :prezime")
                        .setParameter("jmbg", vlasnik.getJmbg())
                        .setParameter("ime", vlasnik.getIme())
                        .setParameter("prezime", vlasnik.getPrezime())
                        .getSingleResult();
                entity.setVlasnikid(vlasnik);
            }
            em.persist(entity);
            em.flush();
    }


    
}
