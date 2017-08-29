/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Ljubimac;
import domen.Search;
import javax.ejb.Stateless;


/**
 *
 * @author hp
 */
@Stateless
public class SystemOperationPretraziLjubimce extends AbstractSystemOperation {

    public SystemOperationPretraziLjubimce() {
        super(Ljubimac.class);
    }

    @Override
    public void execute(Object object) throws Exception {
        Search search = (Search) object;
        result = search(search);
    }


    
}
