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
    public void scanAlertsForPriorityRedPatients() {
        final InboundPatientSourceDouble inboundPatientSource = new InboundPatientSourceDouble();
        final AlertTransmitterDouble alertTransmitterDouble = new AlertTransmitterDouble();

        inboundPatientSource.addInboundPatient(createTestPatient(11, Priority.RED, ""));
        inboundPatientSource.addInboundPatient(createTestPatient(12, Priority.YELLOW, "mild stroke"));

        AlertScanner alertScanner = new AlertScanner(null, inboundPatientSource, alertTransmitterDouble);
        alertScanner.scan();

        assertEquals(1, alertTransmitterDouble.getAlertReviecedAck().size());
        assertEquals("111-111-1111 : New inbound critical patient: 11", alertTransmitterDouble.getAlertReviecedAck().get(0));

    }

    @Test
    public void scanAlertsForYellowHeartArrhythmiaPatients() {
        final InboundPatientSourceDouble inboundPatientSource = new InboundPatientSourceDouble();
        final AlertTransmitterDouble alertTransmitterDouble = new AlertTransmitterDouble();

        inboundPatientSource.addInboundPatient(createTestPatient(11, Priority.GREEN, ""));
        inboundPatientSource.addInboundPatient(createTestPatient(12, Priority.YELLOW, "heart arrhythmia"));

        AlertScanner alertScanner = new AlertScanner(null, inboundPatientSource, alertTransmitterDouble);
        alertScanner.scan();

        assertEquals(1, alertTransmitterDouble.getAlertRevieced().size());
        assertEquals("111-111-1111 : New inbound critical patient: 12", alertTransmitterDouble.getAlertRevieced().get(0));
    }

    @Test
    public void onlyTransmitOnceForGivenInboundPatient() {
        final InboundPatientSourceDouble inboundPatientSource = new InboundPatientSourceDouble();
        final AlertTransmitterDouble alertTransmitterDouble = new AlertTransmitterDouble();

        inboundPatientSource.addInboundPatient(createTestPatient(11, Priority.GREEN, ""));
        inboundPatientSource.addInboundPatient(createTestPatient(12, Priority.YELLOW, "heart arrhythmia"));

        AlertScanner alertScanner = new AlertScanner(null, inboundPatientSource, alertTransmitterDouble);
        alertScanner.scan();
        alertScanner.scan();

        assertEquals(1, alertTransmitterDouble.getAlertRevieced().size());
        assertEquals("111-111-1111 : New inbound critical patient: 12", alertTransmitterDouble.getAlertRevieced().get(0));
    }
    
    private Patient createTestPatient(int transportId, Priority priority, String condition) {
        final Patient patient = new Patient();
        patient.setPriority(priority);
        patient.setTransportId(transportId);
        patient.setCondition(condition);

        return patient;
    }

}
