/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Ljubimac;
import domen.Poseta;
import domen.Stavkaposete;
import domen.Vlasnik;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


/**
 *
 * @author hp
 */
public class SystemOperationSacuvajPosetuTest {

    EntityManager em;

    SystemOperationSacuvajPosetu so;

    public SystemOperationSacuvajPosetuTest() {
    }

    @Before
    public void setUp() {
        em = Mockito.mock(EntityManager.class);
        so = new SystemOperationSacuvajPosetu();
        so.setEm(em);
        Mockito.when(em.find(Mockito.eq(Ljubimac.class), Matchers.any())).then(new Answer<Ljubimac>() {
            @Override
            public Ljubimac answer(InvocationOnMock invocation) throws Throwable {
                int ljubimacid = (int) invocation.getArguments()[1];
                if(ljubimacid == -1) return null;
                else return new Ljubimac(ljubimacid);
            }
        });
    }

    @Test(expected = Exception.class)
    public void testExecuteLjubimacKojiViseNePostoji() throws Exception {
        Poseta p = new Poseta(0);
        p.setLjubimacid(new Ljubimac(-1));
        p.setStavkaposeteList(new ArrayList<>());
        p.getStavkaposeteList().add(new Stavkaposete());
        so.execute(p);
    }

    @Test(expected = Exception.class)
    public void testExecuteBezListe() throws Exception {
        Poseta p = new Poseta(0);
        p.setLjubimacid(new Ljubimac(-1));
        p.setStavkaposeteList(new ArrayList<>());
        p.getStavkaposeteList().add(new Stavkaposete());
        so.execute(p);
    }
    
    @Test(expected = Exception.class)
    public void testExecuteSaPraznomListom() throws Exception {
        Poseta p = new Poseta(0);
        p.setLjubimacid(new Ljubimac(-1));
        p.setStavkaposeteList(new ArrayList<>());
        p.getStavkaposeteList().add(new Stavkaposete());
        so.execute(p);
    }

    @Test
    public void testExecuteSuccessful() throws Exception {
        Poseta p = new Poseta(0);
        p.setLjubimacid(new Ljubimac(1));
        p.setStavkaposeteList(new ArrayList<>());
        p.getStavkaposeteList().add(new Stavkaposete());
        so.execute(p);
        Mockito.verify(em).persist(p);
    }

}
