/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import businesslogic.systemoperation.AbstractSystemOperation;
import businesslogic.systemoperation.SystemOperationIzmeniLjubimca;
import businesslogic.systemoperation.SystemOperationIzmeniUslugu;
import businesslogic.systemoperation.SystemOperationLogin;
import businesslogic.systemoperation.SystemOperationObrisiUslugu;
import businesslogic.systemoperation.SystemOperationPretraziLjubimce;
import businesslogic.systemoperation.SystemOperationPretraziPosete;
import businesslogic.systemoperation.SystemOperationPretraziUsluge;
import businesslogic.systemoperation.SystemOperationPrikaziLjubimca;
import businesslogic.systemoperation.SystemOperationPrikaziPosetu;
import businesslogic.systemoperation.SystemOperationPrikaziTipUsluge;
import businesslogic.systemoperation.SystemOperationPrikaziUslugu;
import businesslogic.systemoperation.SystemOperationSacuvajLjubimca;
import businesslogic.systemoperation.SystemOperationSacuvajPosetu;
import businesslogic.systemoperation.SystemOperationSacuvajUslugu;
import businesslogic.systemoperation.SystemOperationUcitajLjubimce;
import businesslogic.systemoperation.SystemOperationUcitajPosete;
import businesslogic.systemoperation.SystemOperationUcitajTipoveUsluga;
import businesslogic.systemoperation.SystemOperationUcitajUsluge;
import businesslogic.systemoperation.SystemOperationUcitajVlasnike;
import businesslogic.systemoperation.SystemOperationUcitajVrsteZivotinja;
import domen.Korisnik;
import domen.Ljubimac;
import domen.LjubimacSaPosetama;
import domen.Poseta;
import domen.Request;
import domen.Search;
import domen.Stavkaposete;
import domen.Tipusluge;
import domen.Usluga;
import domen.Vlasnik;
import domen.Vrstazivotinje;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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

/**
 *
 * @author hp
 */
@Stateless
@Path("veterinarskaordinacija")
public class Kontrolor{

    @EJB
    private SystemOperationUcitajUsluge systemOperationUcitajUsluge;

    @EJB
    private SystemOperationUcitajVrsteZivotinja systemOperationUcitajVrsteZivotinja;

    @EJB
    private SystemOperationUcitajVlasnike systemOperationUcitajVlasnike;

    @EJB
    private SystemOperationPrikaziTipUsluge systemOperationPrikaziTipUsluge;

    @EJB
    private SystemOperationUcitajTipoveUsluga systemOperationUcitajTipoveUsluga;

    @EJB
    private SystemOperationPrikaziUslugu systemOperationPrikaziUslugu;

    @EJB
    private SystemOperationPretraziUsluge systemOperationPretraziUsluge;

    @EJB
    private SystemOperationPrikaziPosetu systemOperationPrikaziPosetu;

    @EJB
    private SystemOperationPretraziPosete systemOperationPretraziPosete;

    @EJB
    private SystemOperationPrikaziLjubimca systemOperationPrikaziLjubimca;

    @EJB
    private SystemOperationPretraziLjubimce systemOperationPretraziLjubimce;

    @EJB
    private SystemOperationUcitajPosete systemOperationUcitajPosete;

    @EJB
    private SystemOperationUcitajLjubimce systemOperationUcitajLjubimce;

    @EJB
    private SystemOperationSacuvajUslugu systemOperationSacuvajUslugu;

    @EJB
    private SystemOperationSacuvajPosetu systemOperationSacuvajPosetu;

    @EJB
    private SystemOperationSacuvajLjubimca systemOperationSacuvajLjubimca;

    @EJB
    private SystemOperationObrisiUslugu systemOperationObrisiUslugu;

    @EJB
    private SystemOperationIzmeniUslugu systemOperationIzmeniUslugu;

    @EJB
    private SystemOperationIzmeniLjubimca systemOperationIzmeniLjubimca;
    
    
    @EJB
    private SystemOperationLogin systemOperationLogin;

    @Inject
    protected LoggerWrapper loggerWrapper;

    @PersistenceContext(unitName = "VeterinarskaOrdinacijaREST")
    private EntityManager em;




    @POST
    @Path("izmeniLjubimca")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response izmeniLjubimca(domen.Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            Ljubimac ljubimac = (Ljubimac) request.getRequestObject();
            systemOperationIzmeniLjubimca.execute(ljubimac);
            loggerWrapper.getLogger().log(Level.INFO, "user_pet_changed", new Object[]{request.getKorisnik().getKorisnikid(), (ljubimac.getLjubimacid() + " " + ljubimac.getIme())});
            return Response.ok(createMessage(request.getLanguage(), "pet_changed")).build();
        } catch (Exception ne) {
            String odg = createMessage(request.getLanguage(), "pet_not_changed");
            java.util.logging.Logger.getLogger(Kontrolor.class.getName()).log(java.util.logging.Level.SEVERE, null, ne);
            loggerWrapper.getLogger().log(Level.WARNING, "user_pet_not_changed", new Object[]{request.getKorisnik().getKorisnikid(), ((Ljubimac) request.getRequestObject()).getLjubimacid() + " " + ((Ljubimac) request.getRequestObject()).getIme()});
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }
    
    @POST
    @Path("izmeniUslugu")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response izmeniUslugu(Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            systemOperationIzmeniUslugu.execute((Usluga) request.getRequestObject());
            loggerWrapper.getLogger().log(Level.INFO, "user_service_changed", new Object[]{request.getKorisnik().getKorisnikid(), ((Usluga) request.getRequestObject()).getUslugaid() + " " + ((Usluga) request.getRequestObject()).getNaziv()});
            return Response.ok(createMessage(request.getLanguage(), "service_changed")).build();
        } catch (Exception ex) {
            String odg = createMessage(request.getLanguage(), "service_not_changed");
            loggerWrapper.getLogger().log(Level.WARNING, "user_service_not_changed", new Object[]{request.getKorisnik().getKorisnikid(), ((Usluga) request.getRequestObject()).getUslugaid() + " " + ((Usluga) request.getRequestObject()).getNaziv()});
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response login(Request request) throws Exception {
        try {
            Korisnik k = request.getKorisnik();
            systemOperationLogin.execute(k);
            loggerWrapper.getLogger().log(Level.INFO, "user_login", new Object[]{request.getKorisnik().getKorisnikid()});
            return Response.ok(systemOperationLogin.getResult()).build();
        } catch (NoResultException ne) {
            loggerWrapper.getLogger().log(Level.INFO, "user_login_failed", new Object[]{request.getKorisnik().getKorisnikid(), request.getKorisnik().getPass()});
            return Response.status(Response.Status.NOT_FOUND).entity(createMessage(request.getLanguage(), "login_failed")).build();
        }

    }
    
    @POST
    @Path("obrisiUslugu")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response obrisiUslugu(Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            Usluga usluga = (Usluga) request.getRequestObject();
            systemOperationObrisiUslugu.execute(usluga);
            loggerWrapper.getLogger().log(Level.INFO, "user_service_deleted", new Object[]{request.getKorisnik().getKorisnikid(), ((Usluga) request.getRequestObject()).getUslugaid() + " " + ((Usluga) request.getRequestObject()).getNaziv()});
            return Response.ok(createMessage(request.getLanguage(), "service_deleted")).build();
        } catch (Exception ex) {
            String odg = createMessage(request.getLanguage(), "service_not_deleted");
            loggerWrapper.getLogger().log(Level.WARNING, "user_service_not_deleted", new Object[]{request.getKorisnik().getKorisnikid(), ((Usluga) request.getRequestObject()).getUslugaid() + " " + ((Usluga) request.getRequestObject()).getNaziv()});
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }
    
    @POST
    @Path("sacuvajLjubimca")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response sacuvajLjubimca(domen.Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            Ljubimac entity = (Ljubimac) request.getRequestObject();
            systemOperationSacuvajLjubimca.execute(entity);
            loggerWrapper.getLogger().log(Level.INFO, "user_pet_saved", new Object[]{request.getKorisnik().getKorisnikid(), entity.getLjubimacid() + " " + entity.getIme()});
            return Response.ok(createMessage(request.getLanguage(), "pet_saved")).build();
        } catch (Exception e) {
            loggerWrapper.getLogger().log(Level.WARNING, "user_pet_not_saved", new Object[]{request.getKorisnik().getKorisnikid()});
            String odg = createMessage(request.getLanguage(), "pet_not_saved");
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }
    
    @POST
    @Path("sacuvajPosetu")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response sacuvajPosetu(Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            Poseta poseta = (Poseta) request.getRequestObject();
            systemOperationSacuvajPosetu.execute(poseta);
            loggerWrapper.getLogger().log(Level.INFO, "user_pet_visit_saved", new Object[]{request.getKorisnik().getKorisnikid(), poseta.getPosetaid()});
            return Response.ok(createMessage(request.getLanguage(), "pet_visit_saved")).build();
        } catch (Exception e) {
            loggerWrapper.getLogger().log(Level.INFO, "user_pet_visit_saved", new Object[]{request.getKorisnik().getKorisnikid(), ((Poseta) request.getRequestObject()).getDatum(), ((Poseta) request.getRequestObject()).getLjubimacid(), ((Poseta) request.getRequestObject()).getStavkaposeteList()});
            return Response.status(Response.Status.NOT_FOUND).entity(createMessage(request.getLanguage(), "pet_visit_not_saved")).build();
        }
    }
    
    @POST
    @Path("sacuvajUslugu")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response sacuvajUslugu(Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            Usluga usluga = (Usluga) request.getRequestObject();
            systemOperationSacuvajUslugu.execute(usluga);
            loggerWrapper.getLogger().log(Level.INFO, "user_service_saved", new Object[]{request.getKorisnik().getKorisnikid(), usluga.getUslugaid() + " " + usluga.getNaziv()});
            return Response.ok(createMessage(request.getLanguage(), "service_saved")).build();
        } catch (Exception ex) {
            loggerWrapper.getLogger().log(Level.WARNING, "user_service_not_saved", new Object[]{request.getKorisnik().getKorisnikid()});
            String odg = createMessage(request.getLanguage(), "service_not_saved");
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }
    
    @POST
    @Path("ucitajLjubimce")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response ucitajLjubimce(Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            systemOperationUcitajLjubimce.execute(null);
            String odg = systemOperationUcitajLjubimce.getResult().toString();
            return Response.ok(odg).build();
        } catch (Exception ex) {
            String odg = "Sistem ne može da učita ljubimce!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }
    
     @POST
    @Path("ucitajPosete")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response ucitajPosete(Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            systemOperationUcitajPosete.execute(null);
            String odg = systemOperationUcitajPosete.getResult().toString();
            loggerWrapper.getLogger().log(Level.FINE, "user_get_pet_visits", new Object[]{request.getKorisnik()});
            return Response.ok(odg).build();
        } catch (Exception ne) {
            loggerWrapper.getLogger().log(Level.INFO, "user_get_pet_visits_failed", new Object[]{request.getKorisnik()});
            return Response.status(Response.Status.NOT_FOUND).entity(createMessage(request.getLanguage(), "get_pet_visits_failed")).build();
        }
    }
    
    @POST
    @Path("pretraziLjubimce")
    @Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML})
    public Response pretraziLjubimce(Request request) {
        try {
            Search search = (Search) request.getRequestObject();
            systemOperationPretraziLjubimce.execute(search);
            List<Ljubimac> result = (List<Ljubimac>) systemOperationPretraziLjubimce.getResult();
            if(result.isEmpty()){
                throw new Exception();
            }
            GenericEntity<List<Ljubimac>> gt = new GenericEntity<List<Ljubimac>>(result) {
            };
            return Response.ok(gt).build();
        } catch (Exception ex) {
             String odg = createMessage(request.getLanguage(), "pet_search_error");
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }
    
    @POST
    @Path("prikaziLjubimca")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response prikaziLjubimca(domen.Request request) {
        try {
            Ljubimac ljubimac = (Ljubimac) request.getRequestObject();
           systemOperationPrikaziLjubimca.execute(ljubimac);
           Ljubimac lju = (Ljubimac) systemOperationPrikaziLjubimca.getResult();
           LjubimacSaPosetama lj = new LjubimacSaPosetama(lju);
            GenericEntity<LjubimacSaPosetama> gt = new GenericEntity<LjubimacSaPosetama>(lj) {
            };
          //  loggerWrapper.getLogger().log(Level.INFO, "user_show_pet", new Object[]{request.getKorisnik().getKorisnikid(), ((Ljubimac) request.getRequestObject()).getLjubimacid() + " " + ((Ljubimac) request.getRequestObject()).getIme()});
            return Response.ok(gt).build();
        }  catch (Exception ne) {
            String odg = createMessage(request.getLanguage(), "show_pet_error");
            loggerWrapper.getLogger().log(Level.WARNING, "user_show_service_error", new Object[]{request.getKorisnik().getKorisnikid(), ((Ljubimac) request.getRequestObject()).getLjubimacid() + " " + ((Ljubimac) request.getRequestObject()).getIme()});
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }
    
    @POST
    @Path("pretraziPosete")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response pretraziPosete(Request request) {
        try {
            systemOperationPretraziPosete.execute(request.getRequestObject());
            List<Poseta> posete = (List<Poseta>) systemOperationPretraziPosete.getResult();
            if(posete.isEmpty()){
                throw new Exception();
            }
            GenericEntity<List<Poseta>> ge = new GenericEntity<List<Poseta>>(posete) {
            };
            
            loggerWrapper.getLogger().log(Level.FINE, "user_pet_visit_search", new Object[]{request.getKorisnik()});
            return Response.ok(ge).build();
        } catch (Exception e) {
            loggerWrapper.getLogger().log(Level.INFO, "user_visit_search_error", new Object[]{request.getKorisnik()});
            return Response.status(Response.Status.NOT_FOUND).entity(createMessage(request.getLanguage(), "visit_search_error")).build();
        }
    }
    
    @POST
    @Path("prikaziPosetu")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response prikaziPosetu(Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
            systemOperationPrikaziPosetu.execute(request.getRequestObject());
            Poseta poseta = (Poseta) systemOperationPrikaziPosetu.getResult();
         //   loggerWrapper.getLogger().log(Level.FINE, "user_show_pet_visit", new Object[]{request.getKorisnik().getKorisnikid(), poseta.getPosetaid()});
            return Response.ok(poseta).build();
        } catch (Exception ex) {
            loggerWrapper.getLogger().log(Level.INFO, "user_show_pet_visit_error", new Object[]{request.getKorisnik().getKorisnikid()});
            return Response.status(Response.Status.NOT_FOUND).entity(createMessage(request.getLanguage(), "show_pet_visit_error")).build();
        }
    }
    
    @POST
    @Path("pretraziUsluge")
    @Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML})
    public Response pretraziUsluge(Request request) {
        try {
            systemOperationPretraziUsluge.execute(request.getRequestObject());
            List<Usluga> result = (List<Usluga>) systemOperationPretraziUsluge.getResult();
            if(result.isEmpty()){
                throw new Exception();
            }
            GenericEntity<List<Usluga>> gt = new GenericEntity<List<Usluga>>(result) {
            };
            return Response.ok(gt).build();
        } catch (Exception ex) {
            Logger.getLogger(Kontrolor.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).entity(createMessage(request.getLanguage(), "service_search_error")).build();
        }
    }
    
    @POST
    @Path("prikaziUslugu")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response prikaziUslugu(Request request) {
        try {
            Usluga usluga = (Usluga) request.getRequestObject();
            systemOperationPrikaziUslugu.execute(usluga);
            Usluga u = (Usluga) systemOperationPrikaziUslugu.getResult();
            GenericEntity<Usluga> gt = new GenericEntity<Usluga>(u) {
            };
           // loggerWrapper.getLogger().log(Level.INFO, "user_show_service", new Object[]{request.getKorisnik().getKorisnikid(), ((Usluga) request.getRequestObject()).getUslugaid() + " " + u.getNaziv()});
            return Response.ok(gt).build();
        } catch (Exception ne) {
            String odg = createMessage(request.getLanguage(), "show_service_error");
            //loggerWrapper.getLogger().log(Level.WARNING, "user_show_service_error", new Object[]{request.getKorisnik().getKorisnikid()});
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }
    
    @POST
    @Path("ucitajTipoveUsluga")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_XML)
    public Response ucitajTipoveUsluga(Request request) {
        try {
            systemOperationUcitajTipoveUsluga.execute(null);
            List<Tipusluge> tipoviUsluge = (List<Tipusluge>) systemOperationUcitajTipoveUsluga.getResult();
            GenericEntity<List<Tipusluge>> ge = new GenericEntity<List<Tipusluge>>(tipoviUsluge) {
            };
            return Response.ok(ge).build();
        } catch (Exception ne) {
             loggerWrapper.getLogger().log(Level.WARNING, "get_service_type_error", new Object[]{request.getKorisnik().getKorisnikid(), ((Ljubimac) request.getRequestObject()).getLjubimacid() + " " + ((Ljubimac) request.getRequestObject()).getIme()});
          
            String odg = createMessage(request.getLanguage(), "get_service_type_error");
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }
    
    @POST
    @Path("prikaziTipUsluge")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_XML)
    public Response prikaziTipUsluge(Request request) {
        try {
           systemOperationPrikaziTipUsluge.execute(request.getRequestObject());
           Tipusluge tu = (Tipusluge) systemOperationPrikaziTipUsluge.getResult();
            GenericEntity<Tipusluge> gt = new GenericEntity<Tipusluge>(tu) {
            };
            return Response.ok(gt).build();
        } catch (Exception ne) {
            String odg = "Sistem ne može da prikaze tip usluge!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }
    
    @POST
    @Path("ucitajVlasnike")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response ucitajVlasnike(Request request) {
        try {
            checkIfUserIsLoggedIn(request.getKorisnik());
           systemOperationUcitajVlasnike.execute(null);
           List<Vlasnik> vlasnici = (List<Vlasnik>) systemOperationUcitajVlasnike.getResult();
            GenericEntity<List<Vlasnik>> ge = new GenericEntity<List<Vlasnik>>(vlasnici) {
            };
            return Response.ok(ge).build();
        } catch (Exception ex) {
            String odg = "Sistem ne može da učita ljubimce!";
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }
    
    @POST
    @Path("ucitajVrsteZivotinja")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response ucitajVrsteZivotinja(Request request) {
        try {
           systemOperationUcitajVrsteZivotinja.execute(null);
            List<Vrstazivotinje> vrstazivotinje = (List<Vrstazivotinje>) systemOperationUcitajVrsteZivotinja.getResult();
            GenericEntity<List<Vrstazivotinje>> ge = new GenericEntity<List<Vrstazivotinje>>(vrstazivotinje) {
            };
            return Response.ok(ge).build();
        } catch (Exception ne) {
            loggerWrapper.getLogger().log(Level.WARNING, "get_pet_species_error", new Object[]{request.getKorisnik().getKorisnikid()});
            String odg = createMessage(request.getLanguage(), "get_pet_species_error");
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }
    
    @POST
    @Path("ucitajUsluge")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response ucitajUsluge(Request request) {
        try {
            systemOperationUcitajUsluge.execute(null);
            List<Usluga> usluge = (List<Usluga>) systemOperationUcitajUsluge.getResult();
            GenericEntity<List<Usluga>> ge = new GenericEntity<List<Usluga>>(usluge) {
            };
            return Response.ok(ge).build();
        } catch (Exception ex) {
            String odg = createMessage(request.getLanguage(), "service_search_error");
            loggerWrapper.getLogger().log(Level.WARNING, "user_service_search_error", new Object[]{request.getKorisnik().getKorisnikid(), ((Usluga) request.getRequestObject()).getUslugaid() + " " + ((Usluga) request.getRequestObject()).getNaziv()});
            return Response.status(Response.Status.NOT_FOUND).entity(odg).build();
        }
    }
    

    protected void checkIfUserIsLoggedIn(Korisnik korisnik) throws Exception {
        Korisnik k = (Korisnik) em.createQuery("SELECT k FROM Korisnik k WHERE k.korisnikid = :korisnikid and k.pass = :pass").setParameter("korisnikid", korisnik.getKorisnikid()).setParameter("pass", korisnik.getPass()).getSingleResult();
        if (k == null) {
            throw new Exception("Niste ulogovani!");
        }
    }

    protected String createMessage(String language, String message) {
        Locale locale;
        if (language.equals("sr")) {
            locale = new Locale("sr", "RS");
        } else {
            locale = new Locale(language);
        }
        ResourceBundle bundle = ResourceBundle.getBundle("internationalization.messages", locale);
        return bundle.getString(message);
    }



}
