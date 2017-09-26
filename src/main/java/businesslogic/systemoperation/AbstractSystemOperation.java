/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Korisnik;
import domen.Search;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.swing.SortOrder;
import logger.LoggerWrapper;

/**
 *
 * @author student
 * @param <T>
 */

public class AbstractSystemOperation<T> {

    @PersistenceContext(unitName = "VeterinarskaOrdinacijaREST")
    protected EntityManager em;
     
    private Class<T> entityClass;

    public AbstractSystemOperation() {
       
    }

    public AbstractSystemOperation(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected Object result = null;

    public Object getResult() {
        return result;
    }
    
    public  void execute(Object object) throws Exception{
        
    }

    protected List<T> search(Search search) {
        String nazivKlase = entityClass.getSimpleName();
        String nazivKlaseLowerCase = nazivKlase.toLowerCase();
        String query = "SELECT " + nazivKlaseLowerCase + " FROM " + nazivKlase + " " + nazivKlaseLowerCase + " WHERE ";
        for (Map.Entry<String, Object> entry : search.getFilters().entrySet()) {
            String key = entry.getKey();
            query += " CAST(" + nazivKlaseLowerCase + "." + key + " AS CHAR(255))";
            key = key.replace(".", "");
            query += " LIKE :" + key;
            query += " AND ";
        }
        query = query.replaceAll(" WHERE $", "");
        query = query.replaceAll(" AND $", "");
        if (search.getSortField() != null) {
            query += " ORDER BY " + nazivKlaseLowerCase + "." + search.getSortField();
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

    protected String createSearchQuery(Search search) {
        String nazivKlase = entityClass.getSimpleName();
        String nazivKlaseLowerCase = nazivKlase.toLowerCase();
        String query = "SELECT " + nazivKlaseLowerCase + " FROM " + nazivKlase + " " + nazivKlaseLowerCase + " WHERE ";
        for (Map.Entry<String, Object> entry : search.getFilters().entrySet()) {
            String key = entry.getKey();
            query += " CAST(" + nazivKlaseLowerCase + "." + key + " AS CHAR(255))";
            key = key.replace(".", "");
            query += " LIKE :" + key;
            query += " AND ";
        }
        query = query.replaceAll(" WHERE $", "");
        query = query.replaceAll(" AND $", "");
        if (search.getSortField() != null) {
            query += " ORDER BY " + nazivKlaseLowerCase + "." + search.getSortField();
            if (SortOrder.DESCENDING.equals(search.getSortOrder())) {
                query += " DESC";
            }
        }
        return query;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    

    
    
}
