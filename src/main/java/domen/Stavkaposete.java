/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "stavkaposete")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stavkaposete.findAll", query = "SELECT s FROM Stavkaposete s")
    , @NamedQuery(name = "Stavkaposete.findByPosetaid", query = "SELECT s FROM Stavkaposete s WHERE s.stavkaposetePK.posetaid = :posetaid")
    , @NamedQuery(name = "Stavkaposete.findByUslugaid", query = "SELECT s FROM Stavkaposete s WHERE s.stavkaposetePK.uslugaid = :uslugaid")
    , @NamedQuery(name = "Stavkaposete.findByStavkaposeteid", query = "SELECT s FROM Stavkaposete s WHERE s.stavkaposetePK.stavkaposeteid = :stavkaposeteid")
    , @NamedQuery(name = "Stavkaposete.findByOpis", query = "SELECT s FROM Stavkaposete s WHERE s.opis = :opis")})
public class Stavkaposete implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StavkaposetePK stavkaposetePK;
    @Size(max = 1000)
    @Column(name = "opis", length = 1000)
    private String opis;
    @JoinColumn(name = "uslugaid", referencedColumnName = "uslugaid", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usluga usluga;
    @JoinColumn(name = "posetaid", referencedColumnName = "posetaid", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Poseta poseta;

    public Stavkaposete() {
    }

    public Stavkaposete(StavkaposetePK stavkaposetePK) {
        this.stavkaposetePK = stavkaposetePK;
    }

    public Stavkaposete(int posetaid, int uslugaid, int stavkaposeteid) {
        this.stavkaposetePK = new StavkaposetePK(posetaid, uslugaid, stavkaposeteid);
    }

    public StavkaposetePK getStavkaposetePK() {
        return stavkaposetePK;
    }

    public void setStavkaposetePK(StavkaposetePK stavkaposetePK) {
        this.stavkaposetePK = stavkaposetePK;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Usluga getUsluga() {
        return usluga;
    }

    public void setUsluga(Usluga usluga) {
        this.usluga = usluga;
    }

    public Poseta getPoseta() {
        return poseta;
    }

    @XmlTransient
    public void setPoseta(Poseta poseta) {
        this.poseta = poseta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stavkaposetePK != null ? stavkaposetePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stavkaposete)) {
            return false;
        }
        Stavkaposete other = (Stavkaposete) object;
        if ((this.stavkaposetePK == null && other.stavkaposetePK != null) || (this.stavkaposetePK != null && !this.stavkaposetePK.equals(other.stavkaposetePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.Stavkaposete[ stavkaposetePK=" + stavkaposetePK + " ]";
    }
    
}
