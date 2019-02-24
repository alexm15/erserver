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
public class BedProviderDouble implements BedProvider {

    private List<Bed> beds;

    public BedProviderDouble() {
        beds = new ArrayList<>();
    }

    public void addBed(Bed bed) {
        beds.add(bed);
    }

    @Override
    public List<Bed> getAllBeds() {
        return beds;
    }
    
    
}
