/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "poseta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Poseta.findAll", query = "SELECT p FROM Poseta p ORDER BY p.datum DESC")
    , @NamedQuery(name = "Poseta.findByPosetaid", query = "SELECT p FROM Poseta p WHERE p.posetaid = :posetaid ORDER BY p.datum DESC")
    , @NamedQuery(name = "Poseta.findByDatum", query = "SELECT p FROM Poseta p WHERE p.datum = :datum ORDER BY p.datum DESC")})
public class Poseta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "posetaid", nullable = false)
    private Integer posetaid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date datum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poseta")
    private List<Stavkaposete> stavkaposeteList;
    @JoinColumn(name = "ljubimacid", referencedColumnName = "ljubimacid", nullable = false)
    @ManyToOne(optional = false)
    private Ljubimac ljubimacid;
    
    public String getDatumString(){
        return new SimpleDateFormat("dd.MM.yyyy").format(datum);
    }
    
    public Poseta() {
    }

    public Poseta(Integer posetaid) {
        this.posetaid = posetaid;
    }

    public Poseta(Integer posetaid, Date datum) {
        this.posetaid = posetaid;
        this.datum = datum;
    }

    public Integer getPosetaid() {
        return posetaid;
    }

    public void setPosetaid(Integer posetaid) {
        this.posetaid = posetaid;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    @XmlInverseReference(mappedBy="stavkaposete")
    public List<Stavkaposete> getStavkaposeteList() {
        return stavkaposeteList;
    }

    public void setStavkaposeteList(List<Stavkaposete> stavkaposeteList) {
        this.stavkaposeteList = stavkaposeteList;
    }

    public Ljubimac getLjubimacid() {
        return ljubimacid;
    }

    public void setLjubimacid(Ljubimac ljubimacid) {
        this.ljubimacid = ljubimacid;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (posetaid != null ? posetaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Poseta)) {
            return false;
        }
        Poseta other = (Poseta) object;
        if ((this.posetaid == null && other.posetaid != null) || (this.posetaid != null && !this.posetaid.equals(other.posetaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.Poseta[ posetaid=" + posetaid + " ]";
    }
    
}
