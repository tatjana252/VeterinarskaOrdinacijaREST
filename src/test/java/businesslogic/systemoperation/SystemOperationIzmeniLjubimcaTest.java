/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Ljubimac;
import domen.Vlasnik;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author hp
 */
public class SystemOperationIzmeniLjubimcaTest {

    EntityManager em;

    SystemOperationIzmeniLjubimca so;

    public SystemOperationIzmeniLjubimcaTest() {
    }

    @Before
    public void setUp() {
        em = Mockito.mock(EntityManager.class);
        so = new SystemOperationIzmeniLjubimca();
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

    @After
    public void tearDown() {
    }

    @Test
    public void testExecuteNoviVlasnik() throws Exception {
        Ljubimac ljubimac = new Ljubimac(1);
        Vlasnik noviVlasnik = new Vlasnik(-1);
        ljubimac.setVlasnikid(noviVlasnik);
        so.execute(ljubimac);
        Mockito.verify(em).persist(noviVlasnik);
        Mockito.verify(em).merge(ljubimac);
        Mockito.verify(em, Mockito.never()).merge(noviVlasnik);
    }

    @Test
    public void testExecuteStariVlasnik() throws Exception {
        Ljubimac ljubimac = new Ljubimac(1);
        Vlasnik stariVlasnik = new Vlasnik(1);
        ljubimac.setVlasnikid(stariVlasnik);
        so.execute(ljubimac);
        Mockito.verify(em, Mockito.never()).persist(stariVlasnik);
        Mockito.verify(em).merge(ljubimac);
        Mockito.verify(em).merge(stariVlasnik);
    }

    @Test(expected = Exception.class)
    public void testExecuteNoviLjubimac() throws Exception {
        Ljubimac noviLjubimac = new Ljubimac(-1);
        Vlasnik stariVlasnik = new Vlasnik(1);
        noviLjubimac.setVlasnikid(stariVlasnik);
        so.execute(noviLjubimac);
    }

}
