/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "korisnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k")
    , @NamedQuery(name = "Korisnik.findByKorisnikid", query = "SELECT k FROM Korisnik k WHERE k.korisnikid = :korisnikid")
    , @NamedQuery(name = "Korisnik.findByPass", query = "SELECT k FROM Korisnik k WHERE k.pass = :pass")})
public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "korisnikid")
    private String korisnikid;
    @Size(max = 100)
    @Column(name = "pass")
    private String pass;

    public Korisnik() {
    }

    public Korisnik(String korisnikid) {
        this.korisnikid = korisnikid;
    }

    public String getKorisnikid() {
        return korisnikid;
    }

    public void setKorisnikid(String korisnikid) {
        this.korisnikid = korisnikid;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (korisnikid != null ? korisnikid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.korisnikid == null && other.korisnikid != null) || (this.korisnikid != null && !this.korisnikid.equals(other.korisnikid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.Korisnik[ korisnikid=" + korisnikid + " ]";
    }
    
}
