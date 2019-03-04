/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erserver.modules.hardunderstand;

import erserver.modules.dependencies.DivergenceSystem;
import erserver.modules.dependencies.Priority;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alexander
 */
public class DivergenceSystemDouble implements DivergenceSystem{

    private Map<Priority, Boolean> divertionPriorities;
    public DivergenceSystemDouble() {
        divertionPriorities = new HashMap<>();
    }

    @Override
    public void removeInboundDiversion(Priority priority) {
        divertionPriorities.put(priority, false);
    }

    @Override
    public void requestInboundDiversion(Priority priority) {
        divertionPriorities.put(priority, true);
    }

    public boolean isDivertingPatientWith(Priority priority) {
        return divertionPriorities.get(priority);
    }
    
    
    
}
