/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domen.Korisnik;
import domen.Ljubimac;
import domen.Request;
import domen.Search;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.swing.SortOrder;
import javax.ws.rs.core.Response;

/**
 *
 * @author hp
 */
public abstract class AbstractFacade<T> {
    
    @PersistenceContext(unitName = "VeterinarskaOrdinacijaREST")
    private EntityManager em;

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager(){
        return em;
    };
    
    public abstract Response sacuvaj(Request request);
    
    public abstract Response izmeni(Request request);

    public abstract Response obrisi(Request request);

    public abstract Response ucitajSve();

    public abstract Response prikazi(Request request);
    
    public abstract Response pretrazi(Search search);

    protected void checkIfUserIsLoggedIn(Korisnik korisnik) throws Exception{
        Korisnik k = (Korisnik) em.createQuery("SELECT k FROM Korisnik k WHERE k.korisnikid = :korisnikid and k.pass = :pass").setParameter("korisnikid", korisnik.getKorisnikid()).setParameter("pass", korisnik.getPass()).getSingleResult();
        if(k == null){
            throw new Exception("Niste ulogovani!");
        }
    }
    
    protected List<T> search(Search search){
        String nazivKlase = entityClass.getSimpleName();
        String nazivKlaseLowerCase = nazivKlase.toLowerCase();
        String query = "SELECT "+nazivKlaseLowerCase+ " FROM "+ nazivKlase+" "+nazivKlaseLowerCase +" WHERE ";
        for (Map.Entry<String, Object> entry : search.getFilters().entrySet()) {
            String key = entry.getKey();
            query += " CAST("+nazivKlaseLowerCase+"." + key + " AS CHAR(255))";
            key = key.replace(".", "");
            query += " LIKE :" + key;
            query += " AND ";
        }
        query = query.replaceAll(" WHERE $", "");
        query = query.replaceAll(" AND $", "");
        if (search.getSortField() != null) {
            query += " ORDER BY "+nazivKlaseLowerCase+"." + search.getSortField();
            if (SortOrder.DESCENDING.equals(search.getSortOrder())) {
                query += " DESC";
            }
        }
        TypedQuery<T> q = em.createQuery(query, entityClass);
        for (Map.Entry<String, Object> entry : search.getFilters().entrySet()) {
            String key = entry.getKey();
            key = key.replace(".", "");
            Object value = entry.getValue();
            q.setParameter(key, "%" + value + "%");
        }
        return q.setFirstResult(search.getFirst()).setMaxResults(search.getPageSize()).getResultList();
    }
    
}
