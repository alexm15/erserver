/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erserver.modules.hardunderstand;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alexander
 */
public class DivergenceReportBuilderTest {
    
    public DivergenceReportBuilderTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testReport() {
        DivergenceReportBuilder builder = new DivergenceReportBuilder(1, 3, 4, new int[]{3,5}, new int[]{3,8}, 10, 4);
        final String expectedRaport = "\nSituation Status: \n" +
                "Inbound patients requiring beds: 1 Red, 3 Yellow, 4 Green.\n" +
                "Available Docs/Nurses: 3/5\n" +
                "Needed Docs/Nurses: 3/8\n" +
                "Available Total Beds/Crit Beds: 10/4";
        
        assertEquals(expectedRaport, builder.buildReport());
        
    }
    
}
