package erserver.modules.dependencies;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class StaffAssignmentManagerTest {
    @Test
    public void testGetPhysiciansOnDuty() {
        StaffProviderDouble staffRepo = new StaffProviderDouble();
        staffRepo.addStaff(new Staff(1, "Peter Doctor", StaffRole.DOCTOR));
        staffRepo.addStaff(new Staff(2, "Frank Resident", StaffRole.RESIDENT));
        StaffAssignmentManager staffAssignmentManager = new StaffAssignmentManager(staffRepo, new BedProviderDouble());
        List<Staff> physiciansOnDuty = staffAssignmentManager.getPhysiciansOnDuty();

        assertNotNull(physiciansOnDuty);
        assertEquals(2, physiciansOnDuty.size());
        assertEquals(1, physiciansOnDuty.get(0).getStaffId());
        assertEquals(2, physiciansOnDuty.get(1).getStaffId());


    }

}