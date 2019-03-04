/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erserver.modules.hardunderstand;

import erserver.modules.dependencies.Priority;
import erserver.modules.testtypes.Patient;
import java.util.List;

/**
 *
 * @author Alexander
 */
public class InboundPriorityCalculator {


    private int redInboundCount;
    private int yellowInboundCount;
    private int greenInboundCount;
    private List<Patient> patients;

    public InboundPriorityCalculator(List<Patient> patients) {
        this.redInboundCount = 0;
        this.yellowInboundCount = 0;
        this.greenInboundCount = 0;
        this.patients = patients;
    }
    
    public void calculate() {
        for (Patient patient : patients) {
            if (Priority.RED.equals(patient.getPriority())) {
                redInboundCount++;
            } else if (Priority.YELLOW.equals(patient.getPriority())) {
                yellowInboundCount++;
            } else if (Priority.GREEN.equals(patient.getPriority())) {
                greenInboundCount++;
            }
        }
    }
    
    

    public int getRedInboundCount() {
        return redInboundCount;
    }

    public int getYellowInboundCount() {
        return yellowInboundCount;
    }

    public int getGreenInboundCount() {
        return greenInboundCount;
    }

    

}
