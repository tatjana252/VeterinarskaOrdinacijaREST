/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.korisnik;

import domen.Korisnik;
import domen.Request;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.AbstractFacade;

/**
 *
 * @author hp
 */
@Stateless
@Path("korisnik")
public class KorisnikFacadeREST extends AbstractFacade<Korisnik> {


    public KorisnikFacadeREST() {
        super(Korisnik.class);
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response login(Korisnik k) throws Exception {
        try {
            Korisnik korisnik = (Korisnik) getEntityManager().createQuery("SELECT k from Korisnik k WHERE k.korisnikid = :korisnikid AND k.pass = :pass")
                    .setParameter("korisnikid", k.getKorisnikid())
                    .setParameter("pass", k.getPass()).getSingleResult();
            return Response.ok(korisnik).build();
        } catch (NoResultException ne) {
            String odg = "Podaci nisu dobri!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }

    }


    @Override
    public Response sacuvaj(Request request) {return null;
    }

    @Override
    public Response izmeni(Request request) {return null;
    }

    @Override
    public Response obrisi(Request request) {return null;
    }

  

    @Override
    public Response prikazi(Request request) {
        return null;
    }


    @Override
    public Response ucitajSve(Request request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response pretrazi(Request request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
