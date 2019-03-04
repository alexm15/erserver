/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erserver.modules.hardunderstand;

import erserver.modules.dependencies.AlertTransmitterDouble;
import erserver.modules.dependencies.Bed;
import erserver.modules.dependencies.BedProviderDouble;
import erserver.modules.dependencies.InboundPatientSourceDouble;
import erserver.modules.dependencies.Priority;
import erserver.modules.dependencies.Staff;
import erserver.modules.dependencies.StaffAssignmentManager;
import erserver.modules.dependencies.StaffProviderDouble;
import erserver.modules.dependencies.StaffRole;
import erserver.modules.testtypes.Patient;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alexander
 */
public class DivergenceControllerTest {

    private DivergenceController controller;
    private List<Patient> incomingPatients;
    private StaffAssignmentManager staffAssignmentManager;
    private InboundPatientSourceDouble inboundPatientSource;
    private String addedReport = " Situation Status Report...";

    @Before
    public void setUp() {
        inboundPatientSource = new InboundPatientSourceDouble();
        staffAssignmentManager = new StaffAssignmentManager(new StaffProviderDouble(), new BedProviderDouble());
        controller = new DivergenceController(inboundPatientSource, staffAssignmentManager);
        incomingPatients = new ArrayList<>();
    }

    @After
    public void tearDown() {
        controller = null;
        incomingPatients = null;
    }

    @Test
    public void testSomeMethod() {
        Patient nonEmergencyPatient = createPatient(Priority.GREEN, "non-emergency situation, patient is ambulatory");
        incomingPatients.add(nonEmergencyPatient);
        incomingPatients.add(createPatient(Priority.RED, "non-emergency situation, patient is ambulatory"));
        incomingPatients.add(createPatient(Priority.YELLOW, "non-emergency situation, patient is ambulatory"));
        incomingPatients.add(createPatient(Priority.GREEN, "ambulatory bleeding"));
        incomingPatients.add(createPatient(Priority.GREEN, "non-emergency, but unable to walk"));
        List<Patient> filteredList = controller.patientsAffectingDivergence(incomingPatients);
        assertEquals(4, filteredList.size());
        assertEquals(false, filteredList.contains(nonEmergencyPatient));

    }

    @Test
    public void executeDivergence_enters_divergence_for_redPriority_when_redCount_is_over_4_and_redCount_has_been_incremented() {
        DivergenceSystemDouble divergenceSystemDouble = new DivergenceSystemDouble();
        AlertTransmitterDouble alertTransmitterDouble = new AlertTransmitterDouble();
        controller.redCount = 4;
        
        controller.executeDivergence(divergenceSystemDouble, addedReport, alertTransmitterDouble, true, false, false);
        
        assertEquals(true, divergenceSystemDouble.isDivertingPatientWith(Priority.RED));
        assertEquals(1, alertTransmitterDouble.getAlertReviecedAck().size());
        assertEquals("111-111-1111 : Entered divergence for RED priority patients! Situation Status Report...", alertTransmitterDouble.getAlertReviecedAck().get(0));
        assertEquals(0, controller.getRedCount());
        assertEquals(true, controller.isDivertingRedPatients());
    }
    
    @Test
    public void executeDivergence_ends_divergence_for_redPriority_when_previously_in_divergence_for_red_and_red_has_not_been_incremented() {
        DivergenceSystemDouble divergenceSystemDouble = new DivergenceSystemDouble();
        AlertTransmitterDouble alertTransmitterDouble = new AlertTransmitterDouble();
        controller.redCount = 4;
        controller.executeDivergence(divergenceSystemDouble, addedReport, alertTransmitterDouble, true, false, false);
        
        controller.executeDivergence(divergenceSystemDouble, addedReport, alertTransmitterDouble, false, false, false);
        
        
        assertEquals(false, divergenceSystemDouble.isDivertingPatientWith(Priority.RED));
        assertEquals(1, alertTransmitterDouble.getAlertRevieced().size());
        assertEquals("111-111-1111 : Ended divergence for RED priority patients.", alertTransmitterDouble.getAlertRevieced().get(0));
        assertEquals(0, controller.getRedCount());
        assertEquals(false, controller.isDivertingRedPatients());
    }

    
    @Test
    public void executeDivergence_enters_divergence_for_yellowPriority_when_yellowCount_is_over_4_and_yellowCount_has_been_incremented() {
        DivergenceSystemDouble divergenceSystemDouble = new DivergenceSystemDouble();
        AlertTransmitterDouble alertTransmitterDouble = new AlertTransmitterDouble();
        controller.yellowCount = 4;
        
        controller.executeDivergence(divergenceSystemDouble, addedReport, alertTransmitterDouble, false, true, false);
        
        assertEquals(true, divergenceSystemDouble.isDivertingPatientWith(Priority.YELLOW));
        assertEquals(1, alertTransmitterDouble.getAlertReviecedAck().size());
        assertEquals("111-111-1111 : Entered divergence for YELLOW priority patients!", alertTransmitterDouble.getAlertReviecedAck().get(0));
        assertEquals(0, controller.getYellowCount());
        assertEquals(true, controller.isDivertingYellowPatients());
    }
    
    @Test
    public void executeDivergence_ends_divergence_for_yellowPriority_when_previously_in_divergence_for_yellow_and_yellow_has_not_been_incremented() {
        DivergenceSystemDouble divergenceSystemDouble = new DivergenceSystemDouble();
        AlertTransmitterDouble alertTransmitterDouble = new AlertTransmitterDouble();
        controller.yellowCount = 4;
        controller.executeDivergence(divergenceSystemDouble, addedReport, alertTransmitterDouble, false, true, false);
        
        controller.executeDivergence(divergenceSystemDouble, addedReport, alertTransmitterDouble, false, false, false);
        
        assertEquals(false, divergenceSystemDouble.isDivertingPatientWith(Priority.YELLOW));
        assertEquals(1, alertTransmitterDouble.getAlertRevieced().size());
        assertEquals("111-111-1111 : Ended divergence for YELLOW priority patients.", alertTransmitterDouble.getAlertRevieced().get(0));
        assertEquals(0, controller.getYellowCount());
        assertEquals(false, controller.isDivertingYellowPatients());
    }
    
    @Test
    public void executeDivergence_enters_divergence_for_greenPriority_when_greenCount_is_over_4_and_green_has_been_incremented() {
        DivergenceSystemDouble divergenceSystemDouble = new DivergenceSystemDouble();
        AlertTransmitterDouble alertTransmitterDouble = new AlertTransmitterDouble();
        controller.greenCount = 4;
        
        controller.executeDivergence(divergenceSystemDouble, addedReport, alertTransmitterDouble, false, false, true);
        
        assertEquals(true, divergenceSystemDouble.isDivertingPatientWith(Priority.GREEN));
        assertEquals(1, alertTransmitterDouble.getAlertReviecedAck().size());
        assertEquals("111-111-1111 : Entered divergence for GREEN priority patients!", alertTransmitterDouble.getAlertReviecedAck().get(0));
        assertEquals(0, controller.getGreenCount());
        assertEquals(true, controller.isDivertingGreenPatients());
    }
    
    @Test
    public void executeDivergence_ends_divergence_for_greenPriority_when_previously_in_divergence_for_green_and_green_has_not_been_incremented() {
        DivergenceSystemDouble divergenceSystemDouble = new DivergenceSystemDouble();
        AlertTransmitterDouble alertTransmitterDouble = new AlertTransmitterDouble();
        controller.greenCount = 4;
        controller.executeDivergence(divergenceSystemDouble, addedReport, alertTransmitterDouble, false, false, true);
        
        controller.executeDivergence(divergenceSystemDouble, addedReport, alertTransmitterDouble, false, false, false);
        
        assertEquals(false, divergenceSystemDouble.isDivertingPatientWith(Priority.GREEN));
        assertEquals(1, alertTransmitterDouble.getAlertRevieced().size());
        assertEquals("111-111-1111 : Ended divergence for GREEN priority patients.", alertTransmitterDouble.getAlertRevieced().get(0));
        assertEquals(0, controller.getGreenCount());
        assertEquals(false, controller.isDivertingGreenPatients());
    }
    
    @Test
    public void criticalBedCalculation() {
        List<Bed> beds = new ArrayList<>();
        beds.add(new Bed(1, true));
        beds.add(new Bed(2, true));
        beds.add(new Bed(3, true));
        beds.add(new Bed(4, false));
        beds.add(new Bed(5, false));
        beds.add(new Bed(6, false));
        int calculateCriticalBedsAvailable = controller.calculateCriticalBedsAvailable(beds);
        
        assertEquals(3, calculateCriticalBedsAvailable);
    }
    
    @Test
    public void calculateAvailableDocsAndNurses() {
        List<Staff> staffList = new ArrayList<>();
        staffList.add(new Staff(1, "Peter", StaffRole.DOCTOR));
        staffList.add(new Staff(2, "Peter", StaffRole.DOCTOR));
        staffList.add(new Staff(3, "Peter", StaffRole.DOCTOR));
        staffList.add(new Staff(4, "Hellen", StaffRole.NURSE));
        staffList.add(new Staff(5, "Peter", StaffRole.NURSE));
        staffList.add(new Staff(6, "Peter", StaffRole.NURSE));
        int[] availableStaff = controller.calculateAvailableDoctorsAndNurses(staffList);
        assertEquals(3, availableStaff[0]);
        assertEquals(3, availableStaff[1]);
    }
    
    

    private Patient createPatient(Priority priority, String condition) {
        Patient patient = new Patient();
        patient.setPriority(priority);
        patient.setCondition(condition);
        return patient;
    }

}
