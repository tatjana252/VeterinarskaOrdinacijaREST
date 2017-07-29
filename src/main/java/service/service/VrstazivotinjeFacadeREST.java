/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.service;

import domen.Request;
import domen.Vrstazivotinje;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
@Path("vrstazivotinje")
public class VrstazivotinjeFacadeREST extends AbstractFacade<Vrstazivotinje> {

    @PersistenceContext(unitName = "VeterinarskaOrdinacijaREST")
    private EntityManager em;

    public VrstazivotinjeFacadeREST() {
        super(Vrstazivotinje.class);
    }

    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Response sacuvaj(Request request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response izmeni(Request request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response obrisi(Request request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @POST
    @Path("vratisve")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response ucitajSve(Request request) {
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

    @Override
    public Response prikazi(Request request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response pretrazi(Request request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
