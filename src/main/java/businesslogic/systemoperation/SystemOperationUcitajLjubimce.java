/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;


import domen.Ljubimac;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.Stateless;


/**
 *
 * @author hp
 */
@Stateless
public class SystemOperationUcitajLjubimce extends AbstractSystemOperation{

    public SystemOperationUcitajLjubimce() {
        super(Ljubimac.class);
    }

    @Override
    public void execute(Object object) throws Exception {
        result = String.valueOf(em.createNamedQuery("Ljubimac.findAll").getResultList().size());
          
    }

   

    
}
