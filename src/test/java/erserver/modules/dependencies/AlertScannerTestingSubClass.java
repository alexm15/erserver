/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erserver.modules.dependencies;

import erserver.modules.testtypes.Patient;

/**
 *
 * @author Alexander
 */
public class AlertScannerTestingSubClass extends AlertScanner {

    private Patient criticalPatient;

    
    public AlertScannerTestingSubClass(InboundPatientSource inboundPatientSource) {
        super(null, inboundPatientSource);
    }

    @Override
    protected void alertForNewCriticalPatient(Patient patient) {
        this.criticalPatient = patient;
    }
    
    
}
