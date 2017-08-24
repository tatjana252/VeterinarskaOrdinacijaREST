/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.service;

import domen.Ljubimac;
import domen.Poseta;
import domen.Request;
import domen.Search;
import domen.Stavkaposete;
import domen.StavkaposetePK;
import domen.Vlasnik;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
import logger.LoggerWrapper;
import service.AbstractFacade;
import service.usluga.UslugaFacadeREST;

/**
 *
 * @author hp
 */
@Stateless
@Path("poseta")
public class PosetaFacadeREST extends AbstractFacade<Poseta> {

    @PersistenceContext(unitName = "VeterinarskaOrdinacijaREST")
    private EntityManager em;

    public PosetaFacadeREST() {
        super(Poseta.class);
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
            if (!(request.getRequestObject() instanceof Poseta)) {
                throw new Exception();
            }
            Poseta poseta = (Poseta) request.getRequestObject();
            if (em.find(Ljubimac.class, poseta.getLjubimacid().getLjubimacid()) == null) {
                throw new Exception();
            }
            poseta.setPosetaid(0);
            for (Stavkaposete stavkaposete : poseta.getStavkaposeteList()) {
                stavkaposete.setPoseta(poseta);
            }
            em.persist(poseta);
            
            em.flush();
            loggerWrapper.getLogger().log(Level.INFO, "user_pet_visit_saved", new Object[]{request.getKorisnik().getKorisnikid(), poseta.getPosetaid()});
            return Response.ok(createMessage(request.getLanguage(), "pet_visit_saved")).build();
        } catch (Exception e) {
            loggerWrapper.getLogger().log(Level.INFO, "user_pet_visit_saved", new Object[]{request.getKorisnik().getKorisnikid(), ((Poseta) request.getRequestObject()).getDatum(), ((Poseta) request.getRequestObject()).getLjubimacid(), ((Poseta) request.getRequestObject()).getStavkaposeteList()});
            return Response.status(Response.Status.NOT_FOUND).entity(createMessage(request.getLanguage(), "pet_visit_not_saved")).build();
        }
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
    public Response countAll(Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            String posete = String.valueOf(em.createQuery("SELECT p FROM Poseta p").getResultList().size());
            loggerWrapper.getLogger().log(Level.FINE, "user_get_pet_visits", new Object[]{request.getKorisnik()});
            return Response.ok(posete).build();
        } catch (Exception ne) {
            loggerWrapper.getLogger().log(Level.INFO, "user_get_pet_visits_failed", new Object[]{request.getKorisnik()});
            return Response.status(Response.Status.NOT_FOUND).entity(createMessage(request.getLanguage(), "get_pet_visits_failed")).build();
        }
    }

    @Override
    @POST
    @Path("prikazi")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response prikazi(Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            Poseta poseta = (Poseta) request.getRequestObject();
            poseta = em.find(Poseta.class, poseta.getPosetaid());
            loggerWrapper.getLogger().log(Level.FINE, "user_show_pet_visit", new Object[]{request.getKorisnik().getKorisnikid(), poseta.getPosetaid()});
            return Response.ok(poseta).build();
        } catch (Exception ex) {
            loggerWrapper.getLogger().log(Level.INFO, "user_show_pet_visit_failed", new Object[]{request.getKorisnik().getKorisnikid()});
            return Response.status(Response.Status.NOT_FOUND).entity(createMessage(request.getLanguage(), "show_pet_visit_failed")).build();
        }
    }

    @Override
    @POST
    @Path("pretraga")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response pretrazi(Request request) {
        try {
            for (Map.Entry<String, Object> en : ((Search) request.getRequestObject()).getFilters().entrySet()) {
                String key = en.getKey();
                Object value = en.getValue();
                if (key.contains("datum")) {
                    String[] dt = value.toString().split("\\.");
                    String datumSQL = "";
                    if (dt.length > 0) {
                        for (int i = dt.length - 1; i >= 0; i--) {
                            datumSQL += dt[i];
                            datumSQL += "-";
                        }
                    } else {
                        datumSQL += value.toString();
                    }
                    datumSQL = datumSQL.replaceAll("-$", "");
                    ((Search) request.getRequestObject()).getFilters().replace(key, datumSQL);
                }
            }
            List<Poseta> posete = search((Search) request.getRequestObject());
            GenericEntity<List<Poseta>> ge = new GenericEntity<List<Poseta>>(posete) {
            };
            
            loggerWrapper.getLogger().log(Level.FINE, "user_pet_visit_search", new Object[]{request.getKorisnik()});
            return Response.ok(ge).build();
        } catch (NoResultException e) {
            loggerWrapper.getLogger().log(Level.INFO, "user_pet_visit_search_failed", new Object[]{request.getKorisnik()});
            return Response.status(Response.Status.NOT_FOUND).entity(createMessage(request.getLanguage(), "pet_visit_search_failed")).build();
        }
    }

}
