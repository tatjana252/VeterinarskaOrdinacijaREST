/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author hp
 */
@XmlRootElement
@XmlSeeAlso({Ljubimac.class, Poseta.class})
public class LjubimacSaPosetama implements Serializable{
    Ljubimac lj;
    List<Poseta> posete;

    public LjubimacSaPosetama() {
    }

    public LjubimacSaPosetama(Ljubimac lj, List<Poseta> posete) {
        this.lj = lj;
        this.posete = posete;
    }
    
    public Ljubimac getLj() {
        return lj;
    }

    public void setLj(Ljubimac lj) {
        this.lj = lj;
    }

    public List<Poseta> getPosete() {
        return posete;
    }

    public void setPosete(List<Poseta> posete) {
        this.posete = posete;
    }
    
    
}
