/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Singleton;
import mb.MBLog;

/**
 *
 * @author hp
 */

@Startup
@Singleton
public class LoggerWrapper {
    
    @Inject
    MBLog mbLog;
    
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME, "internationalization.messages");
    
    @PostConstruct
    public void init(){
        try {
            new MyLogger().setup();
        } catch (IOException ex) {
            Logger.getLogger(LoggerWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Logger getLogger() {
        mbLog.dodajLog("dodajem log");
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
    
    
    
    
    
}
