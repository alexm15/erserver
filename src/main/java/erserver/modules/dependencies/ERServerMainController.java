package erserver.modules.dependencies;

import erserver.modules.hardunderstand.DivergenceController;
import java.util.Timer;
import java.util.TimerTask;

public class ERServerMainController {

   private static StaffAssignmentManager staffAssignmentManager;
   private static InboundPatientController inboundPatientController;
   private static AlertScanner alertScanner;
   private static DivergenceController divergenceController;

   static {
      staffAssignmentManager = new StaffAssignmentManager();
      EmergencyResponseService emergencyTransportService = new EmergencyResponseService("http://localhost", 4567, 1000);
      inboundPatientController = new InboundPatientController(emergencyTransportService);
      alertScanner = new AlertScanner(staffAssignmentManager, inboundPatientController);
      divergenceController = new DivergenceController();
      
      TimerTask alertTask = new TimerTask() {
         @Override
         public void run() {
            alertScanner.scan();
         }
      };
      Timer timer = new Timer();
      timer.schedule(alertTask, 1000, 30000);
      
      TimerTask divergernceCheck = new TimerTask() {
         @Override
         public void run() {
            divergenceController.check();
         }
      };
      Timer divergenceTimer = new Timer();
      divergenceTimer.schedule(divergernceCheck, 1000, 60000);
   }

   public InboundPatientController getInboundPatientController() {
      return inboundPatientController;
   }

   public StaffAssignmentManager getStaffAssignmentManager() {
      return staffAssignmentManager;
   }



}
