/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erserver.modules.dependencies;

import erserver.modules.testtypes.Patient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alexander
 */
public class AlertScannerTest {
    
    public AlertScannerTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of scan method, of class AlertScanner.
     */
    @Test
    public void testScan() {
        final InboundPatientSourceDouble inboundPatientSource = new InboundPatientSourceDouble();
        Patient patient = createTestPatient(1, Priority.RED);
        inboundPatientSource.addInboundPatient(patient);
        
        AlertScanner alertScanner = new AlertScannerTestingSubClass(inboundPatientSource);
        
        
        alertScanner.scan();
        
        
    }

    private Patient createTestPatient(int transportId, Priority priority) {
        final Patient patient = new Patient();
        patient.setPriority(priority);
        patient.setTransportId(transportId);
        return patient;
    }
    
}
