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
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

    @POST
    @Path("pretraga")
    @Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML})
    @Override
    public Response pretrazi(Request request) {
        Search search = (Search) request.getRequestObject();
        List<Usluga> result = search(search);
        GenericEntity<List<Usluga>> gt = new GenericEntity<List<Usluga>>(result) {
        };
        return Response.ok(gt).build();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    @POST
    @Path("sacuvaj")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response sacuvaj(Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            if (!(request.getRequestObject() instanceof Usluga)) {
                throw new Exception();
            }
            Usluga usluga = (Usluga) request.getRequestObject();
            if (!em.createQuery("SELECT u.uslugaid FROM Usluga u WHERE u.naziv = :naziv AND u.tipuslugeid = :tipuslugeid")
                    .setParameter("naziv", usluga.getNaziv()).setParameter("tipuslugeid", usluga.getTipuslugeid())
                    .setMaxResults(1)
                    .getResultList()
                    .isEmpty()) {
                throw new Exception();
            }
            em.persist(usluga);
            loggerWrapper.getLogger().log(Level.INFO, "user_service_saved", new Object[]{request.getKorisnik().getKorisnikid(), usluga.getUslugaid() + " " + usluga.getNaziv()});
            return Response.ok(createMessage(request.getLanguage(), "service_saved")).build();
        } catch (Exception ex) {
            Logger.getLogger(UslugaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            loggerWrapper.getLogger().log(Level.WARNING, "user_service_not_saved", new Object[]{request.getKorisnik().getKorisnikid()});
            String odg = createMessage(request.getLanguage(), "service_not_saved");
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @Override
    @POST
    @Path("izmeni")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response izmeni(Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            System.out.println(em.createQuery("SELECT u FROM Usluga u WHERE u.uslugaid = :uslugaid").setParameter("uslugaid", ((Usluga)request.getRequestObject()).getUslugaid()).getSingleResult());
            if(em.find(Usluga.class, ((Usluga)request.getRequestObject()).getUslugaid()) == null){
                throw new Exception();
            }
            em.merge((Usluga) request.getRequestObject());
            loggerWrapper.getLogger().log(Level.INFO, "user_service_changed", new Object[]{request.getKorisnik().getKorisnikid(), ((Usluga) request.getRequestObject()).getUslugaid() + " " + ((Usluga) request.getRequestObject()).getNaziv()});
            return Response.ok(createMessage(request.getLanguage(), "service_changed")).build();
            
        } catch (Exception ex) {
            Logger.getLogger(UslugaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);

            String odg = createMessage(request.getLanguage(), "service_not_changed");
            loggerWrapper.getLogger().log(Level.WARNING, "user_service_not_changed", new Object[]{request.getKorisnik().getKorisnikid(), ((Usluga) request.getRequestObject()).getUslugaid() + " " + ((Usluga) request.getRequestObject()).getNaziv()});
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }

    }

    @Override
    @POST
    @Path("obrisi")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response obrisi(Request request) {
        try {
            System.out.println("obrisi");
            checkIfUserIsLoggedIn(request.getKorisnik());
            Usluga usluga = (Usluga) request.getRequestObject();
            Usluga u = em.find(Usluga.class, usluga.getUslugaid());
            em.remove(u);
                System.out.println("PRVA"+em.find(Usluga.class, usluga.getUslugaid()));
        
            em.flush();
                System.out.println("DRUGA"+em.find(Usluga.class, usluga.getUslugaid()));
        
            loggerWrapper.getLogger().log(Level.INFO, "user_service_deleted", new Object[]{request.getKorisnik().getKorisnikid(), ((Usluga) request.getRequestObject()).getUslugaid() + " " + ((Usluga) request.getRequestObject()).getNaziv()});
            return Response.ok(createMessage(request.getLanguage(), "service_deleted")).build();
        } catch (Exception ex) {
            Logger.getLogger(UslugaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);

            String odg = createMessage(request.getLanguage(), "service_not_deleted");
            loggerWrapper.getLogger().log(Level.WARNING, "user_service_not_deleted", new Object[]{request.getKorisnik().getKorisnikid(), ((Usluga) request.getRequestObject()).getUslugaid() + " " + ((Usluga) request.getRequestObject()).getNaziv()});
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @Override
    @POST
    @Path("vratisve")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response countAll(Request request) {
        try {
            List<Usluga> usluge = em.createQuery("SELECT u FROM Usluga u ORDER BY u.tipuslugeid ASC").getResultList();
            GenericEntity<List<Usluga>> ge = new GenericEntity<List<Usluga>>(usluge) {
            };
            return Response.ok(ge).build();
        } catch (NoResultException ex) {
            Logger.getLogger(UslugaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            String odg = createMessage(request.getLanguage(), "service_search_error");
            loggerWrapper.getLogger().log(Level.WARNING, "user_service_search_error", new Object[]{request.getKorisnik().getKorisnikid(), ((Usluga) request.getRequestObject()).getUslugaid() + " " + ((Usluga) request.getRequestObject()).getNaziv()});
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @Override
    @POST
    @Path("prikazi")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response prikazi(Request request) {
        try {
            if (!(request.getRequestObject() instanceof Usluga)) {
                throw new Exception();
            }
            Usluga usluga = (Usluga) request.getRequestObject();
            Usluga u = (Usluga) em.createQuery("SELECT u FROM Usluga u WHERE u.uslugaid = :uslugaid").setParameter("uslugaid", usluga.getUslugaid()).getSingleResult();
            GenericEntity<Usluga> gt = new GenericEntity<Usluga>(u) {
            };
            loggerWrapper.getLogger().log(Level.INFO, "user_show_service", new Object[]{request.getKorisnik().getKorisnikid(), ((Usluga) request.getRequestObject()).getUslugaid() + " " + u.getNaziv()});
            return Response.ok(gt).build();
        } catch (Exception ne) {
            String odg = createMessage(request.getLanguage(), "show_service_error");
            Logger.getLogger(UslugaFacadeREST.class.getName()).log(Level.SEVERE, null, ne);
            //loggerWrapper.getLogger().log(Level.WARNING, "user_show_service_error", new Object[]{request.getKorisnik().getKorisnikid()});
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

}
