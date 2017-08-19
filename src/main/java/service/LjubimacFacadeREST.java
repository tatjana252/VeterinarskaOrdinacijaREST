/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domen.Ljubimac;
import domen.LjubimacSaPosetama;
import domen.Poseta;
import domen.Request;
import domen.Search;
import domen.Usluga;
import domen.Vlasnik;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @POST
    @Path("sacuvaj")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public Response sacuvaj(domen.Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            Ljubimac entity = (Ljubimac) request.getRequestObject();
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
            em.flush();
            loggerWrapper.getLogger().log(Level.INFO, "user_pet_saved", new Object[]{request.getKorisnik().getKorisnikid(), entity.getLjubimacid() + " " + entity.getIme()});
            return Response.ok(createMessage(request.getLanguage(), "pet_saved")).build();

        } catch (Exception e) {
            loggerWrapper.getLogger().log(Level.WARNING, "user_pet_not_saved", new Object[]{request.getKorisnik().getKorisnikid()});
            String odg = createMessage(request.getLanguage(), "pet_not_saved");
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
        List<Ljubimac> result = search(search);
        GenericEntity<List<Ljubimac>> gt = new GenericEntity<List<Ljubimac>>(result) {
        };
        return Response.ok(gt).build();
    }

    @POST
    @Path("vratisve")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public Response countAll(Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            String ljubimci = String.valueOf(em.createQuery("SELECT lj FROM Ljubimac lj ORDER BY lj.ime ASC").getResultList().size());
            return Response.ok(ljubimci).build();
        } catch (NoResultException ne) {
            String odg = "Sistem ne može da učita ljubimce!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        } catch (Exception ex) {
            String odg = "Sistem ne može da učita ljubimce!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @POST
    @Path("prikazi")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public Response prikazi(domen.Request request) {
        try {
            Ljubimac ljubimac = (Ljubimac) request.getRequestObject();
            Ljubimac lj = (Ljubimac) em.createQuery("SELECT lj FROM Ljubimac lj WHERE lj.ljubimacid = :ljubimacid").setParameter("ljubimacid", ljubimac.getLjubimacid()).getSingleResult();
            LjubimacSaPosetama ljsp = new LjubimacSaPosetama(lj, lj.getPosetaList());
            GenericEntity<LjubimacSaPosetama> gt = new GenericEntity<LjubimacSaPosetama>(ljsp) {
            };

            loggerWrapper.getLogger().log(Level.INFO, "user_show_pet", new Object[]{request.getKorisnik().getKorisnikid(), ((Ljubimac) request.getRequestObject()).getLjubimacid() + " " + ((Ljubimac) request.getRequestObject()).getIme()});
            return Response.ok(gt).build();
        }  catch (Exception ne) {
            String odg = createMessage(request.getLanguage(), "show_pet_error");
            loggerWrapper.getLogger().log(Level.WARNING, "user_pet_service_error", new Object[]{request.getKorisnik().getKorisnikid(), ((Ljubimac) request.getRequestObject()).getLjubimacid() + " " + ((Ljubimac) request.getRequestObject()).getIme()});
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @Override
    public Response izmeni(domen.Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            Ljubimac ljubimac = (Ljubimac) request.getRequestObject();
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
            loggerWrapper.getLogger().log(Level.INFO, "user_pet_changed", new Object[]{request.getKorisnik().getKorisnikid(), (ljubimac.getLjubimacid() + " " + ljubimac.getIme())});
            return Response.ok(createMessage(request.getLanguage(), "pet_changed")).build();
        } catch (Exception ne) {
            String odg = createMessage(request.getLanguage(), "pet_not_changed");
            loggerWrapper.getLogger().log(Level.WARNING, "user_pet_not_changed", new Object[]{request.getKorisnik().getKorisnikid(), ((Ljubimac) request.getRequestObject()).getLjubimacid() + " " + ((Ljubimac) request.getRequestObject()).getIme()});
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Response obrisi(domen.Request request) {
        return null;
    }

}
