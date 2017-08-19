/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.usluga;

import domen.Ljubimac;
import domen.Request;
import domen.Tipusluge;
import java.util.List;
import java.util.logging.Level;
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
    public Response countAll(Request request) {
        try {
            List<Tipusluge> tipoviUsluge = em.createQuery("SELECT tu FROM Tipusluge tu").getResultList();
            GenericEntity<List<Tipusluge>> ge = new GenericEntity<List<Tipusluge>>(tipoviUsluge) {
            };
            return Response.ok(ge).build();
        } catch (NoResultException ne) {
             loggerWrapper.getLogger().log(Level.WARNING, "get_service_type_error", new Object[]{request.getKorisnik().getKorisnikid(), ((Ljubimac) request.getRequestObject()).getLjubimacid() + " " + ((Ljubimac) request.getRequestObject()).getIme()});
          
            String odg = createMessage(request.getLanguage(), "get_service_type_error");
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
