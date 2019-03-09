package erserver.modules.dependencies;

import java.util.ArrayList;
import java.util.List;

public class BedProviderDouble implements BedProvider {
    private List<Bed> beds;

    public BedProviderDouble() {
        this.beds = new ArrayList<>();
    }

    public void addBed(Bed bed) {
        beds.add(bed);
    }

    @Override
    public List<Bed> getAllBeds() {
        return beds;
    }
}
