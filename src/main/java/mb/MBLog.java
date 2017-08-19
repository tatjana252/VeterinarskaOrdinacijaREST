/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author hp
 */
@Named(value = "mbLog")
@SessionScoped
public class MBLog implements Serializable{

    private List<String> fileContent;
    //private String[] poslednjiLogovi;
    private Queue poslednjiLogovi;

    public Queue getPoslednjiLogovi() {
        return poslednjiLogovi;
    }
    
    public MBLog() {
    }

    @PostConstruct
    public void init() {
        try {
            fileContent = new ArrayList<>();
            poslednjiLogovi = new LinkedList();
            loadFile("C:\\Users\\hp\\Desktop\\LogovanjeInfo.txt");
        } catch (ParseException ex) {
            Logger.getLogger(MBLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadFile(String path) throws ParseException {
        fileContent.clear();
        File f = new File(path);
        BufferedReader r = null;
        try {
            r = new BufferedReader(new FileReader(f));
            String s = "";
            while ((s = r.readLine()) != null) {
                fileContent.add(s);
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (r != null) {
                try {
                    r.close();
                } catch (IOException e) {
                }
            }
        }

    }
    public List<String> getFileContent() {
        return fileContent;
    }

    public void setFileContent(List<String> fileContent) {
        this.fileContent = fileContent;
    }

    public void dodajLog(String str) {
        //RequestContext.getCurrentInstance().update(":logForm");
       // FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":logForm");
        //fileContent.add(e)
    }
}
