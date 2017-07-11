/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "ljubimac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ljubimac.findAll", query = "SELECT l FROM Ljubimac l")
    , @NamedQuery(name = "Ljubimac.findByLjubimacid", query = "SELECT l FROM Ljubimac l WHERE l.ljubimacid = :ljubimacid")
    , @NamedQuery(name = "Ljubimac.findByIme", query = "SELECT l FROM Ljubimac l WHERE l.ime = :ime")
    , @NamedQuery(name = "Ljubimac.findBySifracipa", query = "SELECT l FROM Ljubimac l WHERE l.sifracipa = :sifracipa")
    , @NamedQuery(name = "Ljubimac.findByDatumrodjenja", query = "SELECT l FROM Ljubimac l WHERE l.datumrodjenja = :datumrodjenja")
    , @NamedQuery(name = "Ljubimac.findByRasa", query = "SELECT l FROM Ljubimac l WHERE l.rasa = :rasa")
    , @NamedQuery(name = "Ljubimac.findByPol", query = "SELECT l FROM Ljubimac l WHERE l.pol = :pol")})
public class Ljubimac implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ljubimacid")
    private Integer ljubimacid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ime")
    private String ime;
    @Size(max = 15)
    @Column(name = "sifracipa")
    private String sifracipa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumrodjenja")
    @Temporal(TemporalType.DATE)
    private Date datumrodjenja;
    @Size(max = 100)
    @Column(name = "rasa")
    private String rasa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "pol")
    private String pol;
    @JoinColumn(name = "vrstazivotinjeid", referencedColumnName = "vrstazivotinjeid")
    @ManyToOne(optional = false)
    private Vrstazivotinje vrstazivotinjeid;
    @JoinColumn(name = "vlasnikid", referencedColumnName = "vlasnikid")
    @ManyToOne(optional = false)
    private Vlasnik vlasnikid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ljubimacid")
    private List<Poseta> posetaList;

    public Ljubimac() {
    }

    public Ljubimac(Integer ljubimacid) {
        this.ljubimacid = ljubimacid;
    }

    public Ljubimac(Integer ljubimacid, String ime, Date datumrodjenja, String pol) {
        this.ljubimacid = ljubimacid;
        this.ime = ime;
        this.datumrodjenja = datumrodjenja;
        this.pol = pol;
    }

    public Integer getLjubimacid() {
        return ljubimacid;
    }

    public void setLjubimacid(Integer ljubimacid) {
        this.ljubimacid = ljubimacid;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getSifracipa() {
        return sifracipa;
    }

    public void setSifracipa(String sifracipa) {
        this.sifracipa = sifracipa;
    }

    public Date getDatumrodjenja() {
        return datumrodjenja;
    }

    public void setDatumrodjenja(Date datumrodjenja) {
        this.datumrodjenja = datumrodjenja;
    }

    public String getRasa() {
        return rasa;
    }

    public void setRasa(String rasa) {
        this.rasa = rasa;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public Vrstazivotinje getVrstazivotinjeid() {
        return vrstazivotinjeid;
    }

    public void setVrstazivotinjeid(Vrstazivotinje vrstazivotinjeid) {
        this.vrstazivotinjeid = vrstazivotinjeid;
    }

    public Vlasnik getVlasnikid() {
        return vlasnikid;
    }

    public void setVlasnikid(Vlasnik vlasnikid) {
        this.vlasnikid = vlasnikid;
    }

    @XmlTransient
    public List<Poseta> getPosetaList() {
        return posetaList;
    }

    public void setPosetaList(List<Poseta> posetaList) {
        this.posetaList = posetaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ljubimacid != null ? ljubimacid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ljubimac)) {
            return false;
        }
        Ljubimac other = (Ljubimac) object;
        if ((this.ljubimacid == null && other.ljubimacid != null) || (this.ljubimacid != null && !this.ljubimacid.equals(other.ljubimacid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.Ljubimac[ ljubimacid=" + ljubimacid + " ]";
    }
    
}
