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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "usluga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usluga.findAll", query = "SELECT u FROM Usluga u ORDER BY u.tipuslugeid ASC")
    , @NamedQuery(name = "Usluga.findByUslugaid", query = "SELECT u FROM Usluga u WHERE u.uslugaid = :uslugaid")
    , @NamedQuery(name = "Usluga.findByNaziv", query = "SELECT u FROM Usluga u WHERE u.naziv = :naziv")
    , @NamedQuery(name = "Usluga.findByCena", query = "SELECT u FROM Usluga u WHERE u.cena = :cena")})
public class Usluga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "uslugaid")
    private Integer uslugaid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private double cena;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usluga")
    private List<Stavkaposete> stavkaposeteList;
    @JoinColumn(name = "tipuslugeid", referencedColumnName = "tipuslugeid")
    @ManyToOne(optional = false)
    private Tipusluge tipuslugeid;

    public Usluga() {
    }

    public Usluga(Integer uslugaid) {
        this.uslugaid = uslugaid;
    }

    public Usluga(Integer uslugaid, String naziv, double cena) {
        this.uslugaid = uslugaid;
        this.naziv = naziv;
        this.cena = cena;
    }

    public Integer getUslugaid() {
        return uslugaid;
    }

    public void setUslugaid(Integer uslugaid) {
        this.uslugaid = uslugaid;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    @XmlTransient
    public List<Stavkaposete> getStavkaposeteList() {
        return stavkaposeteList;
    }

    public void setStavkaposeteList(List<Stavkaposete> stavkaposeteList) {
        this.stavkaposeteList = stavkaposeteList;
    }

    public Tipusluge getTipuslugeid() {
        return tipuslugeid;
    }

    public void setTipuslugeid(Tipusluge tipuslugeid) {
        this.tipuslugeid = tipuslugeid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uslugaid != null ? uslugaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usluga)) {
            return false;
        }
        Usluga other = (Usluga) object;
        if ((this.uslugaid == null && other.uslugaid != null) || (this.uslugaid != null && !this.uslugaid.equals(other.uslugaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.Usluga[ uslugaid=" + uslugaid + " ]";
    }
    
}
