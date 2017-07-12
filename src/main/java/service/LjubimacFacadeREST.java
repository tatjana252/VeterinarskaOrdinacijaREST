/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domen.Ljubimac;
import domen.Tipusluge;
import domen.Usluga;
import domen.Vlasnik;
import domen.Vrstazivotinje;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
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
            if(entity.getVlasnikid().getVlasnikid() == -1){
                System.out.println("Vlasnik je sacuvan");
                em.persist(vlasnik);
                vlasnik = (Vlasnik) em.createQuery("SELECT v FROM Vlasnik v WHERE v.jmbg = :jmbg AND v.ime = :ime AND v.prezime = :prezime")
                        .setParameter("jmbg", vlasnik.getJmbg())
                        .setParameter("ime", vlasnik.getIme())
                        .setParameter("prezime", vlasnik.getPrezime())
                        .getSingleResult();
                System.out.println("Vracam vlasnika iz baze");
                entity.setVlasnikid(vlasnik);
            }
            System.out.println("Cuvam ljubimca");
            em.persist(entity);
            System.out.println("Ljubimac je sacuvan");
            return Response.ok("Ljubimac je sačuvan!").build();
        } catch (Exception e) {
            Logger.getLogger(LjubimacFacadeREST.class.getName()).log(Level.SEVERE, null, e);
            String odg = "Ljubimac nije sačuvan!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }




    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
