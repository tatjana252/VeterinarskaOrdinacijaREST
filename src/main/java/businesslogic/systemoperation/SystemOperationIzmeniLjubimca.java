/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Ljubimac;
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
            Vlasnik vlasnik = ljubimac.getVlasnikid();
            if (ljubimac.getVlasnikid().getVlasnikid() == -1) {
                em.persist(vlasnik);
                vlasnik = (Vlasnik) em.createQuery("SELECT v FROM Vlasnik v WHERE v.jmbg = :jmbg AND v.ime = :ime AND v.prezime = :prezime")
                        .setParameter("jmbg", vlasnik.getJmbg())
                        .setParameter("ime", vlasnik.getIme())
                        .setParameter("prezime", vlasnik.getPrezime())
                        .getSingleResult();
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
