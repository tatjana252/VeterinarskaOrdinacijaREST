/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Poseta;
import domen.Search;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;


/**
 *
 * @author hp
 */
@Stateless
public class SystemOperationPretraziPosete extends AbstractSystemOperation {

    public SystemOperationPretraziPosete() {
        super(domen.Poseta.class);
    }

    @Override
    public void execute(Object object) throws Exception {
        Search search = (Search) object;
        for (Map.Entry<String, Object> en : search.getFilters().entrySet() ) {
                String key = en.getKey();
                Object value = en.getValue();
                if (key.contains("datum")) {
                    String[] dt = value.toString().split("\\.");
                    String datumSQL = "";
                    if (dt.length > 0) {
                        for (int i = dt.length - 1; i >= 0; i--) {
                            datumSQL += dt[i];
                            datumSQL += "-";
                        }
                    } else {
                        datumSQL += value.toString();
                    }
                    datumSQL = datumSQL.replaceAll("-$", "");
                    search.getFilters().replace(key, datumSQL);
                }
            }
            result = search(search);
    }

    

    
}
