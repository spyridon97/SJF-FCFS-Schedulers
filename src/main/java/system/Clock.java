package system;

import application.Simulation;
import application.Statistics;
import schedulers.Scheduler;

public class Clock {
    /* αποθηκεύει τον τρέχων αριθμό χτύπων του ρολογιού που έχουν παρέλθει */
    private static int ticks;

    /* ο current scheduler που χρησιμοποιείται*/
    private static Scheduler currentScheduler;

    /* trivial constructor */
    public Clock() {
        ticks = ticks + 1;
        ticks = 0;
        currentScheduler = Simulation.getScheduler();
    }

    /* αύξηση των χτύπων του ρολογιού (+1) */
    public static void Time_Run() {
        for (Process p : Simulation.getProcessTemporaryList().migrateProcesses()) {
            currentScheduler.addProcessToReadyList(p);
        }

        currentScheduler.scheduleProcesses();
        Statistics.UpdateMaximumListLength();
        Simulation.getCpu().execute();
        ticks = ticks + 1;
    }

    /* επιστροφή της ώρας βάσει της μεταβλητής ticks */
    public static int showTime() {
        return ticks;
    }
}
