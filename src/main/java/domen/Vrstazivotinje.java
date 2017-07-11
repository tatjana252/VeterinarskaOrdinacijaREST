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
@Table(name = "vrstazivotinje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vrstazivotinje.findAll", query = "SELECT v FROM Vrstazivotinje v")
    , @NamedQuery(name = "Vrstazivotinje.findByVrstazivotinjeid", query = "SELECT v FROM Vrstazivotinje v WHERE v.vrstazivotinjeid = :vrstazivotinjeid")
    , @NamedQuery(name = "Vrstazivotinje.findByNaziv", query = "SELECT v FROM Vrstazivotinje v WHERE v.naziv = :naziv")})
public class Vrstazivotinje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vrstazivotinjeid")
    private Integer vrstazivotinjeid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "naziv")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vrstazivotinjeid")
    private List<Ljubimac> ljubimacList;

    public Vrstazivotinje() {
    }

    public Vrstazivotinje(Integer vrstazivotinjeid) {
        this.vrstazivotinjeid = vrstazivotinjeid;
    }

    public Vrstazivotinje(Integer vrstazivotinjeid, String naziv) {
        this.vrstazivotinjeid = vrstazivotinjeid;
        this.naziv = naziv;
    }

    public Integer getVrstazivotinjeid() {
        return vrstazivotinjeid;
    }

    public void setVrstazivotinjeid(Integer vrstazivotinjeid) {
        this.vrstazivotinjeid = vrstazivotinjeid;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
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
        hash += (vrstazivotinjeid != null ? vrstazivotinjeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vrstazivotinje)) {
            return false;
        }
        Vrstazivotinje other = (Vrstazivotinje) object;
        if ((this.vrstazivotinjeid == null && other.vrstazivotinjeid != null) || (this.vrstazivotinjeid != null && !this.vrstazivotinjeid.equals(other.vrstazivotinjeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.Vrstazivotinje[ vrstazivotinjeid=" + vrstazivotinjeid + " ]";
    }
    
}
