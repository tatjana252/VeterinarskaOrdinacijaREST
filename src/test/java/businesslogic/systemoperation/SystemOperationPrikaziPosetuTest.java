/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Poseta;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
public class SystemOperationPrikaziPosetuTest {
    
    EntityManager em;

    SystemOperationPrikaziPosetu so;
    
    Poseta p;
    
    
    public SystemOperationPrikaziPosetuTest() {
        so = new SystemOperationPrikaziPosetu();
        em = Mockito.mock(EntityManager.class);
        so.setEm(em);
    }
    
    @Before
    public void setUp() {
        Mockito.doAnswer(new Answer<Poseta>() {
                @Override
                public Poseta answer(InvocationOnMock invocation) throws Throwable {
                    Poseta p = (Poseta) invocation.getArguments()[0];
                    if(p == null) throw new Exception();
                    
                    return null;
                }
            }).when(em).refresh(p);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testExecuteSuccessful() throws Exception {
       p = new Poseta(1);
        Poseta prikaziPosetu = new Poseta();
        Mockito.when(em.find(Poseta.class, p.getPosetaid())).thenReturn(prikaziPosetu);
        so.execute(p);
        Poseta rezultat = (Poseta) so.getResult();
        Mockito.verify(em).find(Poseta.class, p.getPosetaid());
        assertSame(prikaziPosetu, rezultat);
    }
    
    @Test(expected = Exception.class)
    public void testExecuteError() throws Exception{
            p = new Poseta(1);
            Mockito.when(em.find(Poseta.class, p.getPosetaid())).thenReturn(null);    
            so.execute(p);       
    }
}
