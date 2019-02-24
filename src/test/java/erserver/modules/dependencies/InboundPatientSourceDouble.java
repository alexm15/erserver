/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erserver.modules.dependencies;

import erserver.modules.testtypes.Patient;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander
 */
public class InboundPatientSourceDouble implements InboundPatientSource {

    private List<Patient> inboundPatients;
    private int patientArrivedTransportId;

    public InboundPatientSourceDouble() {
        inboundPatients = new ArrayList<>();
    }

    
    public void addInboundPatient(Patient patient) {
        inboundPatients.add(patient);
    }
    
    @Override
    public List<Patient> currentInboundPatients() {
        return inboundPatients;
    }

    @Override
    public void informOfPatientArrival(int transportId) {
        this.patientArrivedTransportId = transportId;
    }

    public int getPatientArrivedTransportId() {
        return patientArrivedTransportId;
    }
    
    
    
}
