package erserver.modules.dependencies;

import erserver.modules.testtypes.Patient;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class InboundPatientControllerTest {

    @Test
    public void testInboundPatientXmlConvertion() {
        String xmlForInbound = "<Inbound>\n" +
                "    <Patient>\n" +
                "        <TransportId>1</TransportId>\n" +
                "        <Name>John Doe</Name>\n" +
                "        <Condition>heart arrhythmia</Condition>\n" +
                "        <Priority>YELLOW</Priority>\n" +
                "        <Birthdate></Birthdate>\n" +
                "    </Patient>\n" +
                "</Inbound>";
        ArrayList<Patient> patients = InboundPatientController.getPatientsFromXml(xmlForInbound);
        assertNotNull(patients);
        assertEquals(1, patients.size());
        Patient patient = patients.get(0);
        assertEquals(1, patient.getTransportId());
        assertEquals("John Doe", patient.getName());
        assertEquals("heart arrhythmia", patient.getCondition());
        assertEquals(Priority.YELLOW, patient.getPriority());
        assertNull(patient.getBirthDate());

    }


}