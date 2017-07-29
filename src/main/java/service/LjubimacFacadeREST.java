/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domen.Ljubimac;
import domen.Poseta;
import domen.Request;
import domen.Search;
import domen.Vlasnik;
import domen.Vrstazivotinje;
import java.util.ArrayList;
import java.util.List;
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
    public Response ucitajSve(Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            List<Ljubimac> usluge = em.createQuery("SELECT lj FROM Ljubimac lj ORDER BY lj.ime ASC").getResultList();
            GenericEntity<List<Ljubimac>> ge = new GenericEntity<List<Ljubimac>>(usluge) {
            };
            return Response.ok(ge).build();
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
            GenericEntity<List<Poseta>> gt = new GenericEntity<List<Poseta>>(lj.getPosetaList()) {
            };
            return Response.ok(gt).build();
        } catch (NoResultException ne) {
            Logger.getLogger(LjubimacFacadeREST.class.getName()).log(Level.SEVERE, null, ne);
            List<Poseta> posete = new ArrayList<>();
            GenericEntity<List<Poseta>> gt = new GenericEntity<List<Poseta>>(posete) {
            };
            return Response.ok(gt).build();
        } catch (Exception ne) {
            Logger.getLogger(LjubimacFacadeREST.class.getName()).log(Level.SEVERE, null, ne);
            String odg = "Sistem ne može da prikaze ljubimca!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @POST
    @Path("izmeni")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
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
            return Response.ok().build();
        } catch (Exception ne) {
            String odg = "Sistem ne može da prikaze ljubimca!";
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
