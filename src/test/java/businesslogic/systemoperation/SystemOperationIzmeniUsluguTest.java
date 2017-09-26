/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic.systemoperation;

import domen.Usluga;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author hp
 */
public class SystemOperationIzmeniUsluguTest {
    
    EntityManager em;
    
    SystemOperationIzmeniUslugu so;
    
    Usluga u;
       
    @Before
    public void setUp() {
        em = Mockito.mock(EntityManager.class);
        so = new SystemOperationIzmeniUslugu();
        so.setEm(em);
        Mockito.when(em.find(Mockito.eq(Usluga.class), Matchers.any())).then(new Answer<Usluga>() {
            @Override
            public Usluga answer(InvocationOnMock invocation) throws Throwable {
                int uslugaid = (int) invocation.getArguments()[1];
                if(uslugaid == -1){
                    return null;
                }
                else{
                    return new Usluga(uslugaid);
                }
            }
        });
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Usluga usluga = (Usluga) invocation.getArguments()[0];
                if(usluga == null){
                    throw new Exception();
                }
                return null;
            }
        }).when(em).merge(Matchers.any(Usluga.class));
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of execute method, of class SystemOperationIzmeniUslugu.
     */
    @Test
    public void testExecuteSuccessful() throws Exception {
        u = new Usluga(1);
        so.execute(u);
        Mockito.verify(em).merge(u);
    }
    
    @Test(expected = Exception.class)
    public void testExecuteFail() throws Exception {
        u = new Usluga(-1);
        so.execute(u);     
    }
    
}
