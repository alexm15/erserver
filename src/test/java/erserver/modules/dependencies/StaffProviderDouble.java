/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erserver.modules.dependencies;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander
 */
public class StaffProviderDouble implements StaffProvider {

    private List<Staff> shiftStaff;

    public StaffProviderDouble() {
        shiftStaff = new ArrayList<>();
    }
    
    public void addShiftStaff(Staff staff) {
        shiftStaff.add(staff);
    }

    @Override
    public List<Staff> getShiftStaff() {
        return shiftStaff;
    }
    
}
