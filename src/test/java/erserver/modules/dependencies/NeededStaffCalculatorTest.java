/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erserver.modules.dependencies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alexander
 */
public class NeededStaffCalculatorTest {
    
    public NeededStaffCalculatorTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void nursesRequiredReducedByOneWhenOverFive() {
        NeededStaffCalculator neededStaff = new NeededStaffCalculator(new int[] {1,2}, new int[] {1,1}, new int[] {0,1}, 1, 3, 3);
        int[] neededOverall = neededStaff.overall();
        assertEquals(4, neededOverall[0]);
        assertEquals(7, neededOverall[1]);
    }
    
    @Test
    public void docsAndNurseValuesCalculatedCorrectly() {
        NeededStaffCalculator neededStaff = new NeededStaffCalculator(new int[] {1,2}, new int[] {1,1}, new int[] {0,1}, 1, 1, 1);
        int[] neededOverall = neededStaff.overall();
        assertEquals(2, neededOverall[0]);
        assertEquals(4, neededOverall[1]);
    }
    
}
