/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author hp
 */

@XmlRootElement
@XmlSeeAlso({Ljubimac.class, Poseta.class, Usluga.class})
public class Request implements Serializable{
    private Korisnik korisnik;
    private Object requestObject;

    public Request() {
    }

    public Request(Korisnik korisnik, Object requestObject) {
        this.korisnik = korisnik;
        this.requestObject = requestObject;
    }
    
    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Object getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(Object requestObject) {
        this.requestObject = requestObject;
    }
    
    
}
