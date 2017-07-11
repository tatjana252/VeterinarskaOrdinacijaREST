/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domen.Ljubimac;
import domen.Vlasnik;
import java.util.List;
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
    
//
//    @POST
//    @Override
//    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public void create(Ljubimac entity) {
//        super.create(entity);
//    }
//
//    @PUT
//    @Path("{id}")
//    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public void edit(@PathParam("id") Integer id, Ljubimac entity) {
//        super.edit(entity);
//    }
//
//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Integer id) {
//        super.remove(super.find(id));
//    }
//
//    @GET
//    @Path("{id}")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Ljubimac find(@PathParam("id") Integer id) {
//        return super.find(id);
//    }
//
//    @GET
//    @Override
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public List<Ljubimac> findAll() {
//        return super.findAll();
//    }
//
//    @GET
//    @Path("{from}/{to}")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public List<Ljubimac> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }
//
//    @GET
//    @Path("count")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String countREST() {
//        return String.valueOf(super.count());
//    }
//
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
//     @POST
//    @Path("vlasnik/sacuvaj")
//    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response sacuvajVlasnika(Vlasnik entity) {
//        try {
//
//            if (!em.createQuery("SELECT v.vlasnikid FROM Vlasnik v")
//                    .setParameter("naziv", entity.getNaziv()).setParameter("tipuslugeid", entity.getTipuslugeid())
//                    .setMaxResults(1)
//                    .getResultList()
//                    .isEmpty()) {
//                throw new Exception();
//            }
//            create(entity);
//            return Response.ok("Usluga je sačuvana!").build();
//        } catch (Exception e) {
//            Logger.getLogger(UslugaFacadeREST.class.getName()).log(Level.SEVERE, null, e);
//            String odg = "Sistem ne može da sačuva uslugu!";
//            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
//        }
//        em.persist(entity);
//    }
    
}
