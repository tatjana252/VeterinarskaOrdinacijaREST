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
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author hp
 */
public class SystemOperationSacuvajLjubimcaTest {

    EntityManager em;
    SystemOperationSacuvajLjubimca so;

    public SystemOperationSacuvajLjubimcaTest() {

    }

    @Before
    public void setUp() {
        em = Mockito.mock(EntityManager.class);
        so = new SystemOperationSacuvajLjubimca();
        so.setEm(em);
    }

    @Test
    public void testExecuteLjubimacSaNovimVlasnikom() throws Exception {
        Vlasnik noviVlasnik = new Vlasnik(-1);
        Ljubimac ljubimac = new Ljubimac();
        ljubimac.setVlasnikid(noviVlasnik);
        so.execute(ljubimac);
        Mockito.verify(em, Mockito.times(1)).persist(noviVlasnik);
        Mockito.verify(em, Mockito.times(1)).persist(ljubimac);
    }

    @Test
    public void testExecuteLjubimacSaPostojecimVlasnikom() throws Exception {
        Vlasnik postojeciVlasnik = new Vlasnik(10);
        Ljubimac ljubimac = new Ljubimac();
        ljubimac.setVlasnikid(postojeciVlasnik);
        so.execute(ljubimac);
        Mockito.verify(em, Mockito.never()).persist(postojeciVlasnik);
        Mockito.verify(em, Mockito.times(1)).persist(ljubimac);
    }

}
