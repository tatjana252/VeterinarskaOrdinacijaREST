/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author hp
 */
@Embeddable
public class StavkaposetePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "posetaid", nullable = false, insertable = false, updatable = false)
    private int posetaid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "uslugaid", nullable = false, insertable = false, updatable = false)
    private int uslugaid;
    @Basic(optional = false)
    @Column(name = "stavkaposeteid", nullable = false)
    private int stavkaposeteid;

    public StavkaposetePK() {
    }

    public StavkaposetePK(int posetaid, int uslugaid, int stavkaposeteid) {
        this.posetaid = posetaid;
        this.uslugaid = uslugaid;
        this.stavkaposeteid = stavkaposeteid;
    }

    public int getPosetaid() {
        return posetaid;
    }

    public void setPosetaid(int posetaid) {
        this.posetaid = posetaid;
    }

    public int getUslugaid() {
        return uslugaid;
    }

    public void setUslugaid(int uslugaid) {
        this.uslugaid = uslugaid;
    }

    public int getStavkaposeteid() {
        return stavkaposeteid;
    }

    public void setStavkaposeteid(int stavkaposeteid) {
        this.stavkaposeteid = stavkaposeteid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) posetaid;
        hash += (int) uslugaid;
        hash += (int) stavkaposeteid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StavkaposetePK)) {
            return false;
        }
        StavkaposetePK other = (StavkaposetePK) object;
        if (this.posetaid != other.posetaid) {
            return false;
        }
        if (this.uslugaid != other.uslugaid) {
            return false;
        }
        if (this.stavkaposeteid != other.stavkaposeteid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.StavkaposetePK[ posetaid=" + posetaid + ", uslugaid=" + uslugaid + ", stavkaposeteid=" + stavkaposeteid + " ]";
    }
    
}
