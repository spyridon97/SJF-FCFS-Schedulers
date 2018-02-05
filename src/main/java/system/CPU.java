package system;

public class CPU {
    /* περιέχει τη διεργασία που χρησιμοποιεί τη CPU την τρέχουσα στιγμή */
    private static Process runningProcess;

    /* περιέχει τη χρονική στιγμή της επόμενης διακοπής */
    private static int timeToNextContextSwitch;

    /* περιέχει τη χρονική στιγμή έναρξης της τελευταίας διεργασίας */
    private static int lastProcessStartTime;

    /* constructor - αρχικοποίηση πεδίων */
    public CPU() {
        runningProcess = null;
        lastProcessStartTime = -1;
        timeToNextContextSwitch = -1;
    }

    /* εισαγωγή της διεργασίας προς εκτέλεση στη CPU */
    public void addProcess(Process process) {
        runningProcess = process;
        lastProcessStartTime = Clock.showTime();
        //System.out.println("Added process pid " + process.getPid());
        timeToNextContextSwitch = -1;
    }

    /* παίρνουμε τη χρονική στιγμή έναρξης της τελευταίας διεργασίας */
    public static int getLastProcessStartTime() {
        return lastProcessStartTime;
    }

    /* εξαγωγή της τρέχουσας διεργασίας απο τη CPU */
    public Process removeProcess() {
        Process temp = runningProcess;
        runningProcess = null;
        //System.out.println("Removed process pid " + temp.getPid());
        return temp;
    }

    /* εκτέλεση της διεργασίας με αντίστοιχη μείωση του χρόνου εκτέλεσής της */
    public void execute() {
        if (runningProcess != null) {
            runningProcess.changeCpuRemainingTime(1);
        }
    }

    /* επιστρέφουμε την διεργασία που υπάρχει μέσα στη CPU για να κάνουμε ελέγχους ή αλλαγές σε αυτήν */
    public Process observeCurrentProcess() {
        return runningProcess;
    }
}
