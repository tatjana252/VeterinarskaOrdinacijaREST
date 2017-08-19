package logger;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import javax.ejb.Stateless;
import javax.inject.Inject;
import mb.MBLog;


public class MyHtmlFormatter extends Formatter implements Serializable{
    

    
    @Override
    public String format(LogRecord rec) {
        StringBuilder buf = new StringBuilder(1000);
        // colorize any levels >= WARNING in red
        
        if (rec.getLevel().intValue() >= Level.WARNING.intValue()) {
            buf.append(" ");
            buf.append(rec.getLevel());
            buf.append(" ");
        } else {
            buf.append(" ");
            buf.append(rec.getLevel());
            buf.append(" ");
        }

        buf.append(" ");
        buf.append(calcDate(rec.getMillis()));
        buf.append(" ");
        buf.append(formatMessage(rec));
        buf.append(" \n");
     //   mbLog.dodajLog(buf.toString());
        
        return buf.toString();
    }

    private String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }

}
