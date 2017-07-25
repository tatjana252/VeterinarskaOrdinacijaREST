/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "vlasnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vlasnik.findAll", query = "SELECT v FROM Vlasnik v")
    , @NamedQuery(name = "Vlasnik.findByVlasnikid", query = "SELECT v FROM Vlasnik v WHERE v.vlasnikid = :vlasnikid")
    , @NamedQuery(name = "Vlasnik.findByJmbg", query = "SELECT v FROM Vlasnik v WHERE v.jmbg = :jmbg")
    , @NamedQuery(name = "Vlasnik.findByIme", query = "SELECT v FROM Vlasnik v WHERE v.ime = :ime")
    , @NamedQuery(name = "Vlasnik.findByPrezime", query = "SELECT v FROM Vlasnik v WHERE v.prezime = :prezime")
    , @NamedQuery(name = "Vlasnik.findByAdresa", query = "SELECT v FROM Vlasnik v WHERE v.adresa = :adresa")
    , @NamedQuery(name = "Vlasnik.findByBrojtelefona", query = "SELECT v FROM Vlasnik v WHERE v.brojtelefona = :brojtelefona")})
public class Vlasnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vlasnikid")
    private Integer vlasnikid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "jmbg")
    private String jmbg;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ime")
    private String ime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "prezime")
    private String prezime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 225)
    @Column(name = "adresa")
    private String adresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "brojtelefona")
    private String brojtelefona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vlasnikid")
    private List<Ljubimac> ljubimacList;

    public Vlasnik() {
    }

    public Vlasnik(Integer vlasnikid) {
        this.vlasnikid = vlasnikid;
    }

    public Vlasnik(Integer vlasnikid, String jmbg, String ime, String prezime, String adresa, String brojtelefona) {
        this.vlasnikid = vlasnikid;
        this.jmbg = jmbg;
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.brojtelefona = brojtelefona;
    }

    public Integer getVlasnikid() {
        return vlasnikid;
    }

    public void setVlasnikid(Integer vlasnikid) {
        this.vlasnikid = vlasnikid;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getBrojtelefona() {
        return brojtelefona;
    }

    public void setBrojtelefona(String brojtelefona) {
        this.brojtelefona = brojtelefona;
    }

    @XmlTransient
    public List<Ljubimac> getLjubimacList() {
        return ljubimacList;
    }

    public void setLjubimacList(List<Ljubimac> ljubimacList) {
        this.ljubimacList = ljubimacList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vlasnikid != null ? vlasnikid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vlasnik)) {
            return false;
        }
        Vlasnik other = (Vlasnik) object;
        if ((this.vlasnikid == null && other.vlasnikid != null) || (this.vlasnikid != null && !this.vlasnikid.equals(other.vlasnikid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.Vlasnik[ vlasnikid=" + vlasnikid + " ]";
    }
    
}
