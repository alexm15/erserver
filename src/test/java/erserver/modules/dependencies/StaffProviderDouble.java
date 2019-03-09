package erserver.modules.dependencies;

import java.util.ArrayList;
import java.util.List;

public class StaffProviderDouble implements StaffProvider {
    private List<Staff> shiftStaff;

    public StaffProviderDouble() {
        this.shiftStaff = new ArrayList<>();

    }

    public void addStaff(Staff staff) {
        shiftStaff.add(staff);
    }

    @Override
    public List<Staff> getShiftStaff() {
        return shiftStaff;
    }
}
