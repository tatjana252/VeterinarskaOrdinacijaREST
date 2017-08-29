/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Korisnik;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;



/**
 *
 * @author hp
 */

@Stateless
public class SystemOperationLogin extends AbstractSystemOperation<Korisnik>{

    public SystemOperationLogin() {
        super(Korisnik.class);
    }

    public SystemOperationLogin(EntityManager em) {
        this.em = em;
    }

    @Override
    public void execute(Object object) throws Exception {
        System.out.println("EntityManager " +em);
        Korisnik k = (Korisnik) object;
        Korisnik korisnik = (Korisnik) em.createQuery("SELECT k from Korisnik k WHERE k.korisnikid = :korisnikid AND k.pass = :pass")
                    .setParameter("korisnikid", k.getKorisnikid())
                    .setParameter("pass", k.getPass()).getSingleResult();
        result = korisnik;

    }



    
}
