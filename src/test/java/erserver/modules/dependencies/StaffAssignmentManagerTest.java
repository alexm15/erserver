/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erserver.modules.dependencies;

import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alexander
 */
public class StaffAssignmentManagerTest {
    
    public StaffAssignmentManagerTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getPhysiciansOnDuty_ReturnsDoctorsAndResidents() {
        final StaffProviderDouble staffProvider = new StaffProviderDouble();
        staffProvider.addShiftStaff(new Staff(1, "Peter Doctor", StaffRole.DOCTOR));
        staffProvider.addShiftStaff(new Staff(2, "Hans Resident", StaffRole.RESIDENT));
        
        StaffAssignmentManager manager = new StaffAssignmentManager(staffProvider, new BedProviderDouble());
        
        List<Staff> physiciansOnDuty = manager.getPhysiciansOnDuty();
        
        assertThat(physiciansOnDuty.size(), is(2));
        assertThat(physiciansOnDuty.get(0).getStaffId(), is(1));
        assertThat(physiciansOnDuty.get(1).getStaffId(), is(2));
    }
    
    @Test
    public void getPhysiciansOnDuty_Dont_Return_Nurses() {
        final StaffProviderDouble staffProvider = new StaffProviderDouble();
        staffProvider.addShiftStaff(new Staff(1, "Peter Nurse", StaffRole.NURSE));
        
        StaffAssignmentManager manager = new StaffAssignmentManager(staffProvider, new BedProviderDouble());
        
        List<Staff> physiciansOnDuty = manager.getPhysiciansOnDuty();
        
        assertThat(physiciansOnDuty.size(), is(0));
    }
    
}
