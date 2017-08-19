/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

/**
 *
 * @author hp
 */
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class MyLogger {
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static private FileHandler fileHTML;
    static private Formatter formatterHTML;

    static public void setup() throws IOException {
        
        // get the global logger to configure it
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        // suppress the logging output to the console
        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler("C:\\Users\\hp\\Desktop\\LogovanjeInfo.txt", true);
            
        fileHTML = new FileHandler("C:\\Users\\hp\\Desktop\\Logging.html");

        // create a TXT formatter
        formatterHTML = new MyHtmlFormatter();
        
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterHTML);
        logger.addHandler(fileTxt);
        logger.setUseParentHandlers(false);
        // create an HTML formatter
        formatterHTML = new MyHtmlFormatter();
        fileHTML.setFormatter(formatterHTML);
        logger.addHandler(fileHTML);
    }
    
    
}
