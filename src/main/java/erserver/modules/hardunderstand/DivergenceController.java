package erserver.modules.hardunderstand;

import erserver.modules.dependencies.*;
import erserver.modules.dependencies.vendorpagersystem.PagerSystem;
import erserver.modules.dependencies.vendorpagersystem.PagerTransport;
import erserver.modules.testtypes.Patient;

import java.util.List;
import java.util.stream.Collectors;

public class DivergenceController {

    private static final String ADMIN_ON_CALL_DEVICE = "111-111-1111";

    private boolean redDivergence;
    private boolean yellowDivergence;
    private boolean greenDivergence;
    int redCount;
    int yellowCount;
    int greenCount;
    private int allowedCount;
    private int redBedOverflowAllowed;
    private int yellowBedOverflowAllowed;
    private int greenBedOverflowAllowed;
    private final InboundPatientSource inboundPatientSource;
    private final StaffAssignmentManager staffManager;

    public DivergenceController() {
        this(new ERServerMainController().getInboundPatientController(), new ERServerMainController().getStaffAssignmentManager());

    }

    public DivergenceController(InboundPatientSource inboundPatientSource, StaffAssignmentManager staffManager) {
        this.inboundPatientSource = inboundPatientSource;
        this.staffManager = staffManager;
        this.redDivergence = false;
        this.yellowDivergence = false;
        this.greenDivergence = false;
        this.redCount = 0;
        this.yellowCount = 0;
        this.greenCount = 0;
        this.allowedCount = 3;
        this.redBedOverflowAllowed = 0;
        this.yellowBedOverflowAllowed = 1;
        this.greenBedOverflowAllowed = 4;
    }

    public void check() {
        int[] redStaffRequired = {1, 2};
        int[] yellowStaffRequired = {1, 1};
        int[] greenStaffRequired = {0, 1};
        boolean redIncremented = false;
        boolean yellowIncremented = false;
        boolean greenIncremented = false;
        List<Bed> beds = staffManager.getAvailableBeds();

        int criticalBedsAvailable = calculateCriticalBedsAvailable(beds);

        //Determine number of inbound patients for each priority
        int redInboundCount = 0;
        int yellowInboundCount = 0;
        int greenInboundCount = 0;
        List<Patient> patients = patientsAffectingDivergence(inboundPatientSource.currentInboundPatients());
        InboundPriorityCalculator priorityCalculator = new InboundPriorityCalculator(patients);

        int[] availableStaff = calculateAvailableDoctorsAndNurses(staffManager.getAvailableStaff());

        redIncremented = shouldIncrementDiversionForRedPriority(redInboundCount, criticalBedsAvailable, redIncremented);

        // Increment green or yellow and green diversion if not enough non crit beds available
        if (yellowInboundCount + greenInboundCount > (beds.size() - criticalBedsAvailable + yellowBedOverflowAllowed + greenBedOverflowAllowed)) {
            if ((greenInboundCount > (beds.size() - criticalBedsAvailable + greenBedOverflowAllowed)) && (yellowInboundCount <= (beds.size() - criticalBedsAvailable + yellowBedOverflowAllowed))) {
                greenCount++;
                greenIncremented = true;
            } else {
                greenCount++;
                yellowCount++;
                greenIncremented = true;
                yellowIncremented = true;
            }
        }

        int[] neededStaff = new NeededStaffCalculator(redStaffRequired, yellowStaffRequired, greenStaffRequired,
                redInboundCount, yellowInboundCount, greenInboundCount).overall();

        // Determine if diversion increments need to occur based on available docs vs needed docs.
        if (neededStaff[0] > availableStaff[0]) {
            int diff = neededStaff[0] - availableStaff[0];
            if ((greenInboundCount * greenStaffRequired[0]) >= diff) {
                if (!greenIncremented) {
                    greenIncremented = true;
                    greenCount++;
                }
            } else {
                int both = (yellowInboundCount * yellowStaffRequired[0]) + (greenInboundCount * greenStaffRequired[0]);
                if (both >= diff) {
                    if (!greenIncremented) {
                        greenIncremented = true;
                        greenCount++;
                    }
                    if (!yellowIncremented) {
                        yellowIncremented = true;
                        yellowCount++;
                    }
                } else {
                    if (!greenIncremented) {
                        greenIncremented = true;
                        greenCount++;
                    }
                    if (!yellowIncremented) {
                        yellowIncremented = true;
                        yellowCount++;
                    }
                    if (!redIncremented) {
                        redIncremented = true;
                        redCount++;
                    }
                }
            }
        }

        // Determine if diversion increments need to occur based on available nurses vs needed nurses.
        if (neededStaff[1] > availableStaff[1]) {
            int diff = neededStaff[1] - availableStaff[1];
            if ((greenInboundCount * greenStaffRequired[1]) >= diff) {
                if (!greenIncremented) {
                    greenIncremented = true;
                    greenCount++;
                }
            } else {
                int both = (yellowInboundCount * yellowStaffRequired[1]) + (greenInboundCount * greenStaffRequired[1]);
                if (both >= diff) {
                    if (!greenIncremented) {
                        greenIncremented = true;
                        greenCount++;
                    }
                    if (!yellowIncremented) {
                        yellowIncremented = true;
                        yellowCount++;
                    }
                } else {
                    if (!greenIncremented) {
                        greenIncremented = true;
                        greenCount++;
                    }
                    if (!yellowIncremented) {
                        yellowIncremented = true;
                        yellowCount++;
                    }
                    if (!redIncremented) {
                        redIncremented = true;
                        redCount++;
                    }
                }
            }
        }

        DivergenceReportBuilder reportBuilder = new DivergenceReportBuilder(redInboundCount, yellowInboundCount,
                greenInboundCount, availableStaff, neededStaff, beds.size(), criticalBedsAvailable);
        String digergenceReport = reportBuilder.buildReport();

        executeDivergence(new EmergencyResponseService("http://localhost", 4567, 1000), digergenceReport, new PagerSystemAlertTransmitter(), redIncremented, yellowIncremented, greenIncremented);
    }

    private boolean shouldIncrementDiversionForRedPriority(int redInboundCount, int criticalBedsAvailable, boolean redIncremented) {
        // Increment red priority diversion if not enough crit beds for inbound red priority patients
        if (redInboundCount > (criticalBedsAvailable + redBedOverflowAllowed)) {
            redCount++;
            redIncremented = true;
        }
        return redIncremented;
    }

    int[] calculateAvailableDoctorsAndNurses(List<Staff> availableStaffList) {
        int[] availableStaff = {0, 0};
        for (Staff cur : availableStaffList) {
            if (StaffRole.DOCTOR.equals(cur.getRole())) {
                availableStaff[0]++;
            } else if (StaffRole.NURSE.equals(cur.getRole())) {
                availableStaff[1]++;
            }
        }
        return availableStaff;
    }

    void executeDivergence(DivergenceSystem divergenceSystem, String digergenceReport, AlertTransmitter alertTransmitter, boolean redIncremented, boolean yellowIncremented, boolean greenIncremented) {
        if (redIncremented) {
            if (needsToDivert(redCount, redDivergence)) {
                redDivergence = true;
                divergenceSystem.requestInboundDiversion(Priority.RED);
                sendDivergencePage(alertTransmitter, "Entered divergence for RED priority patients!" + digergenceReport, true);
                redCount = 0;
            }
        } else {
            redCount = 0;
            if (redDivergence) {
                divergenceSystem.removeInboundDiversion(Priority.RED);
                sendDivergencePage(alertTransmitter, "Ended divergence for RED priority patients.", false);
                redDivergence = false;
            }
        }
        if (yellowIncremented) {
            if (needsToDivert(yellowCount, yellowDivergence)) {
                enterDivergenceModeFor(divergenceSystem, alertTransmitter, Priority.YELLOW);
            }
        } else {
            yellowCount = 0;
            if (yellowDivergence) {
                divergenceSystem.removeInboundDiversion(Priority.YELLOW);
                sendDivergencePage(alertTransmitter, "Ended divergence for YELLOW priority patients.", false);
                yellowDivergence = false;
            }
        }
        if (greenIncremented) {
            if (needsToDivert(greenCount, greenDivergence)) {
                greenDivergence = true;
                divergenceSystem.requestInboundDiversion(Priority.GREEN);
                sendDivergencePage(alertTransmitter, "Entered divergence for GREEN priority patients!", true);
                greenCount = 0;
            }
        } else {
            greenCount = 0;
            if (greenDivergence) {
                divergenceSystem.removeInboundDiversion(Priority.GREEN);
                sendDivergencePage(alertTransmitter, "Ended divergence for GREEN priority patients.", false);
                greenDivergence = false;
            }
        }
    }

    private void enterDivergenceModeFor(DivergenceSystem divergenceSystem, AlertTransmitter alertTransmitter, Priority priority) {
        yellowDivergence = true;
        divergenceSystem.requestInboundDiversion(priority);
        sendDivergencePage(alertTransmitter, "Entered divergence for " + priority.toString() + " priority patients!", true);
        yellowCount = 0;
    }

    private boolean needsToDivert(int inboundCount, boolean inDivergenceMode) {
        return (inboundCount > allowedCount) && !inDivergenceMode;
    }

    boolean isDivertingRedPatients() {
        return redDivergence;
    }

    boolean isDivertingYellowPatients() {
        return yellowDivergence;
    }

    boolean isDivertingGreenPatients() {
        return greenDivergence;
    }

    int getRedCount() {
        return redCount;
    }

    int getYellowCount() {
        return yellowCount;
    }

    int getGreenCount() {
        return greenCount;
    }

    int calculateCriticalBedsAvailable(List<Bed> beds) {
        int criticalBedsAvailable = 0;
        for (Bed bed : beds) {
            if (bed.isCriticalCare()) {
                criticalBedsAvailable++;
            }
        }
        return criticalBedsAvailable;
    }

    private void sendDivergencePage(AlertTransmitter transport, String text, boolean requireAck) {
        try {
            if (requireAck) {
                transport.transmitRequiringAcknowledgement(ADMIN_ON_CALL_DEVICE, text);
            } else {
                transport.transmit(ADMIN_ON_CALL_DEVICE, text);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    public List<Patient> patientsAffectingDivergence(List<Patient> incomingPatients) {
        return incomingPatients.stream()
                .filter((patient) -> {
                    return !(patient.getCondition().toLowerCase().contains("ambulatory") && patient.getCondition().toLowerCase().contains("non-emergency"))
                            || !(patient.getPriority().equals(Priority.GREEN));
                })
                .collect(Collectors.toList());
    }

}
