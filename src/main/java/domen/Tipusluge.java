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
@Table(name = "tipusluge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipusluge.findAll", query = "SELECT t FROM Tipusluge t")
    , @NamedQuery(name = "Tipusluge.findByTipuslugeid", query = "SELECT t FROM Tipusluge t WHERE t.tipuslugeid = :tipuslugeid")
    , @NamedQuery(name = "Tipusluge.findByNaziv", query = "SELECT t FROM Tipusluge t WHERE t.naziv = :naziv")})
public class Tipusluge implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tipuslugeid")
    private Integer tipuslugeid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "naziv")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipuslugeid")
    private List<Usluga> uslugaList;

    public Tipusluge() {
    }

    public Tipusluge(Integer tipuslugeid) {
        this.tipuslugeid = tipuslugeid;
    }

    public Tipusluge(Integer tipuslugeid, String naziv) {
        this.tipuslugeid = tipuslugeid;
        this.naziv = naziv;
    }

    public Integer getTipuslugeid() {
        return tipuslugeid;
    }

    public void setTipuslugeid(Integer tipuslugeid) {
        this.tipuslugeid = tipuslugeid;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlTransient
    public List<Usluga> getUslugaList() {
        return uslugaList;
    }

    public void setUslugaList(List<Usluga> uslugaList) {
        this.uslugaList = uslugaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipuslugeid != null ? tipuslugeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipusluge)) {
            return false;
        }
        Tipusluge other = (Tipusluge) object;
        if ((this.tipuslugeid == null && other.tipuslugeid != null) || (this.tipuslugeid != null && !this.tipuslugeid.equals(other.tipuslugeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return naziv;
    }
    
}
