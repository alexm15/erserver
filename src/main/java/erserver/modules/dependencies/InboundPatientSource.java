/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erserver.modules.dependencies;

import erserver.modules.testtypes.Patient;
import java.util.List;

/**
 *
 * @author Alexander
 */
public interface InboundPatientSource {

    List<Patient> currentInboundPatients();

    void informOfPatientArrival(int transportId);
    
}
