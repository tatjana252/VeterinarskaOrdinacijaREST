/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.usluga;

import domen.Request;
import domen.Search;
import domen.Tipusluge;
import domen.Usluga;
import java.util.HashMap;
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
@Path("tipusluge")
public class TipUslugeFacadeREST extends AbstractFacade<Tipusluge> {

    @PersistenceContext(unitName = "VeterinarskaOrdinacijaREST")
    private EntityManager em;

    public TipUslugeFacadeREST() {
        super(Tipusluge.class);
    }

    @POST
    @Path("ucitajsve")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_XML)
    @Override
    public Response ucitajSve(Request request) {
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

    @POST
    @Path("prikazi")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_XML)
    @Override
    public Response prikazi(Request request) {
        try {
            Tipusluge zahtev = (Tipusluge) request.getRequestObject();
            Tipusluge tu = (Tipusluge) 
                    em.createQuery("SELECT tu FROM Tipusluge tu WHERE tu.naziv = :naziv ")
                            .setParameter("naziv", zahtev
                            .getNaziv())
                            .getSingleResult();
            GenericEntity<Tipusluge> gt = new GenericEntity<Tipusluge>(tu) {
            };
            return Response.ok(gt).build();
        } catch (NoResultException ne) {
            String odg = "Sistem ne može da prikaze tip usluge!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @Override
    public Response sacuvaj(Request request) {
        return null;
    }

    @Override
    public Response izmeni(Request request) {
        return null;
    }

    @Override
    public Response obrisi(Request request) {
        return null;
    }

    @Override
    public Response pretrazi(Request request) {
        return null;
    }

   
}
