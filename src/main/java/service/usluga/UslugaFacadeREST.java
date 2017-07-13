/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.usluga;

import domen.Search;
import domen.Tipusluge;
import domen.Usluga;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.SortOrder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.AbstractFacade;

/**
 *
 * @author hp
 */
@Stateless
@Path("usluga")
public class UslugaFacadeREST extends AbstractFacade<Usluga> {

    @PersistenceContext(unitName = "VeterinarskaOrdinacijaREST")
    private EntityManager em;

    public UslugaFacadeREST() {
        super(Usluga.class);
    }

    @GET
    @Path("tipoviusluga")
    @Produces(MediaType.APPLICATION_XML)
    public Response ucitajTipoveUsluga() {
        try {
            List<Tipusluge> tipoviUsluge = em.createQuery("SELECT tu FROM Tipusluge tu").getResultList();
            GenericEntity<List<Tipusluge>> ge = new GenericEntity<List<Tipusluge>>(tipoviUsluge) {
            };
            return Response.ok(ge).build();
        } catch (NoResultException ne) {
            String odg = "Sistem ne može da učita tipove usluga!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @GET
    @Path("{naziv}")
    @Produces(MediaType.APPLICATION_XML)
    public Response vratiTipUsluge(@PathParam("naziv") String naziv) {
        try {
            Tipusluge tu = (Tipusluge) em.createQuery("SELECT tu FROM Tipusluge tu WHERE tu.naziv = :naziv ").setParameter("naziv", naziv).getSingleResult();
            GenericEntity<Tipusluge> gt = new GenericEntity<Tipusluge>(tu) {
            };
            return Response.ok(gt).build();
        } catch (NoResultException ne) {
            String odg = "Sistem ne može da prikaze tip usluge!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @GET
    @Path("vratisve")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response ucitajUsluge() {
        try {
            List<Usluga> usluge = em.createQuery("SELECT u FROM Usluga u ORDER BY u.tipuslugeid ASC").getResultList();
            GenericEntity<List<Usluga>> ge = new GenericEntity<List<Usluga>>(usluge) {
            };
            return Response.ok(ge).build();
        } catch (NoResultException ne) {
            String odg = "Sistem ne može da učita usluge!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @POST
    @Path("sacuvaj")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response sacuvajUslugu(Usluga entity) {
        try {

            if ( !em.createQuery("SELECT u.uslugaid FROM Usluga u WHERE u.naziv = :naziv AND u.tipuslugeid = :tipuslugeid")
                    .setParameter("naziv", entity.getNaziv()).setParameter("tipuslugeid", entity.getTipuslugeid())
                    .setMaxResults(1)
                    .getResultList()
                    .isEmpty()) {
                throw new Exception();
            }
            create(entity);
            return Response.ok("Usluga je sačuvana!").build();
        } catch (Exception e) {
            Logger.getLogger(UslugaFacadeREST.class.getName()).log(Level.SEVERE, null, e);
            String odg = "Sistem ne može da sačuva uslugu!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @POST
    @Path("prikazi")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response prikaziUslugu(Usluga usluga) {
        try {
            System.out.println("dobijena usluga " + usluga.getUslugaid() + usluga.getNaziv() + usluga.getCena());

            Usluga u = (Usluga) em.createQuery("SELECT u FROM Usluga u WHERE u.uslugaid = :uslugaid").setParameter("uslugaid", usluga.getUslugaid()).getSingleResult();
            System.out.println("vracena usluga " + u.getNaziv() + u.getCena());
            GenericEntity<Usluga> gt = new GenericEntity<Usluga>(u) {
            };
            return Response.ok(gt).build();
        } catch (NoResultException ne) {
            String odg = "Sistem ne može da prikaze uslugu!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @POST
    @Path("pretraga")
    @Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML})
    public Response pretrazi(Search search) {
        String query = "SELECT u FROM Usluga u WHERE ";
        for (Map.Entry<String, Object> entry : search.getFilters().entrySet()) {
            String key = entry.getKey();
            query += " CAST( u." + key + " AS CHAR(255))";
            key = key.replace(".", "");
            query += " LIKE :" + key;
            query += " AND ";
        }
        query = query.replaceAll(" WHERE $", "");
        query = query.replaceAll(" AND $", "");
        if (search.getSortField() != null) {

            query += " ORDER BY u." + search.getSortField();
            if (SortOrder.DESCENDING.equals(search.getSortOrder())) {
                query += " DESC";
            }
        }
        TypedQuery<Usluga> q = em.createQuery(query, Usluga.class);
        for (Map.Entry<String, Object> entry : search.getFilters().entrySet()) {
            String key = entry.getKey();
            key = key.replace(".", "");
            Object value = entry.getValue();
            q.setParameter(key, "%" + value + "%");
        }
        List<Usluga> result = q.setFirstResult(search.getFirst()).setMaxResults(search.getPageSize()).getResultList();
        GenericEntity<List<Usluga>> gt = new GenericEntity<List<Usluga>>(result) {
        };
        return Response.ok(gt).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void promeni(@PathParam("id") Integer id, Usluga usluga) {
        em.merge(usluga);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
