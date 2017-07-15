/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domen.Ljubimac;
import domen.Poseta;
import domen.Search;
import domen.Tipusluge;
import domen.Usluga;
import domen.Vlasnik;
import domen.Vrstazivotinje;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.swing.SortOrder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.usluga.UslugaFacadeREST;

/**
 *
 * @author hp
 */
@Stateless
@Path("ljubimac")
public class LjubimacFacadeREST extends AbstractFacade<Ljubimac> {

    @PersistenceContext(unitName = "VeterinarskaOrdinacijaREST")
    private EntityManager em;

    public LjubimacFacadeREST() {
        super(Ljubimac.class);
    }

    @GET
    @Path("vlasnik/ucitajVlasnike")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response ucitajVlasnike() {
        try {
            List<Vlasnik> vlasnici = em.createQuery("SELECT v FROM Vlasnik v").getResultList();
            GenericEntity<List<Vlasnik>> ge = new GenericEntity<List<Vlasnik>>(vlasnici) {
            };
            return Response.ok(ge).build();
        } catch (NoResultException ne) {
            String odg = "Sistem ne može da učita vlasnike!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @GET
    @Path("vrstezivotinja")
    @Produces(MediaType.APPLICATION_XML)
    public Response ucitajVrsteZivotinja() {
        try {
            List<Vrstazivotinje> vrstazivotinje = em.createQuery("SELECT vz FROM Vrstazivotinje vz").getResultList();
            GenericEntity<List<Vrstazivotinje>> ge = new GenericEntity<List<Vrstazivotinje>>(vrstazivotinje) {
            };
            return Response.ok(ge).build();
        } catch (NoResultException ne) {
            String odg = "Sistem ne može da učita vrste životinja!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @POST
    @Path("sacuvaj")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response sacuvajLjubimca(Ljubimac entity) {
        try {
            Vlasnik vlasnik = entity.getVlasnikid();
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
            return Response.ok("Ljubimac je sačuvan!").build();
        } catch (Exception e) {
            Logger.getLogger(LjubimacFacadeREST.class.getName()).log(Level.SEVERE, null, e);
            String odg = "Ljubimac nije sačuvan!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @POST
    @Path("pretraga")
    @Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML})
    public Response pretrazi(Search search) {
        String query = "SELECT lj FROM Ljubimac lj WHERE ";
        for (Map.Entry<String, Object> entry : search.getFilters().entrySet()) {
            String key = entry.getKey();
            query += " CAST( lj." + key + " AS CHAR(255))";
            key = key.replace(".", "");
            query += " LIKE :" + key;
            query += " AND ";
        }
        query = query.replaceAll(" WHERE $", "");
        query = query.replaceAll(" AND $", "");
        if (search.getSortField() != null) {

            query += " ORDER BY lj." + search.getSortField();
            if (SortOrder.DESCENDING.equals(search.getSortOrder())) {
                query += " DESC";
            }
        }
        TypedQuery<Ljubimac> q = em.createQuery(query, Ljubimac.class);
        for (Map.Entry<String, Object> entry : search.getFilters().entrySet()) {
            String key = entry.getKey();
            key = key.replace(".", "");
            Object value = entry.getValue();
            q.setParameter(key, "%" + value + "%");
        }
        List<Ljubimac> result = q.setFirstResult(search.getFirst()).setMaxResults(search.getPageSize()).getResultList();
        GenericEntity<List<Ljubimac>> gt = new GenericEntity<List<Ljubimac>>(result) {
        };
        return Response.ok(gt).build();
    }

    @GET
    @Path("vratisve")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response ucitajLjubimce() {
        try {
            List<Ljubimac> usluge = em.createQuery("SELECT lj FROM Ljubimac lj ORDER BY lj.ime ASC").getResultList();
            GenericEntity<List<Ljubimac>> ge = new GenericEntity<List<Ljubimac>>(usluge) {
            };
            return Response.ok(ge).build();
        } catch (NoResultException ne) {
            String odg = "Sistem ne može da učita ljubimce!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @POST
    @Path("prikazi")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response prikaziLjubimca(Ljubimac ljubimac) {
        try {
            Ljubimac lj = (Ljubimac) em.createQuery("SELECT lj FROM Ljubimac lj WHERE lj.ljubimacid = :ljubimacid").setParameter("ljubimacid", ljubimac.getLjubimacid()).getSingleResult();
            
            GenericEntity<List<Poseta>> gt = new GenericEntity<List<Poseta>>(lj.getPosetaList()) {
            };
            return Response.ok(gt).build();
        } catch (NoResultException ne) {
            
            String odg = "Sistem ne može da prikaze ljubimca!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }
    
    @POST
    @Path("izmeni")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response izmeniLjubimca(Ljubimac ljubimac) {
        try {
            Vlasnik vlasnik = ljubimac.getVlasnikid();
            if (ljubimac.getVlasnikid().getVlasnikid() == -1) {
                em.persist(vlasnik);
                vlasnik = (Vlasnik) em.createQuery("SELECT v FROM Vlasnik v WHERE v.jmbg = :jmbg AND v.ime = :ime AND v.prezime = :prezime")
                        .setParameter("jmbg", vlasnik.getJmbg())
                        .setParameter("ime", vlasnik.getIme())
                        .setParameter("prezime", vlasnik.getPrezime())
                        .getSingleResult();
                ljubimac.setVlasnikid(vlasnik);
            }
            em.merge(ljubimac);
            return Response.ok().build();
        } catch (NoResultException ne) {
            String odg = "Sistem ne može da prikaze ljubimca!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
