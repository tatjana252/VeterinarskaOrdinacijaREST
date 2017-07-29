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
import java.util.List;
import java.util.Map;
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

            System.out.println("Da li se posalje lista " + poseta.getStavkaposeteList());
            for (Stavkaposete stavkaposete : poseta.getStavkaposeteList()) {
                stavkaposete.setStavkaposetePK(new StavkaposetePK());
                System.out.println(stavkaposete.getUsluga());
                stavkaposete.setPoseta(poseta);
                System.out.println("Ovo je poseta" + stavkaposete.getPoseta());
//                em.persist(stavkaposete);
            }
            em.persist(poseta);
            em.flush();
            return Response.ok("Usluga je sačuvana!").build();
        } catch (Exception e) {
            Logger.getLogger(UslugaFacadeREST.class.getName()).log(Level.SEVERE, null, e);
            String odg = "Sistem ne može da sačuva uslugu!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
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
    public Response ucitajSve(Request request) {
        try {
            List<Poseta> usluge = em.createQuery("SELECT p FROM Poseta p").getResultList();
            GenericEntity<List<Poseta>> ge = new GenericEntity<List<Poseta>>(usluge) {
            };
            return Response.ok(ge).build();
        } catch (NoResultException ne) {
            String odg = "Sistem ne može da učita usluge!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

    @Override
    public Response prikazi(Request request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                    System.out.println("L"+dt.length);
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
                    System.out.println("DATUMSQL " + datumSQL);
                    System.out.println(key + datumSQL);
                    ((Search) request.getRequestObject()).getFilters().replace(key, datumSQL);
                }
            }
            List<Poseta> posete = search((Search) request.getRequestObject());
            GenericEntity<List<Poseta>> ge = new GenericEntity<List<Poseta>>(posete) {
            };
            System.out.println("poslati je " + posete.size());
            return Response.ok(ge).build();
        } catch (NoResultException e) {
            Logger.getLogger(UslugaFacadeREST.class.getName()).log(Level.SEVERE, null, e);
            String odg = "Sistem ne može da učita usluge!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }

}
