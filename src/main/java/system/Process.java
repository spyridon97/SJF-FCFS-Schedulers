package system;

import application.Statistics;

import java.util.Comparator;

public class Process {
    /*περιέχει το κωδικό της διεργασίας*/
    private int pid;
    /* περιέχει την χρονική στιγμή άφιξης της διεργασίας στο σύστημα */
    private int arrivalTime;
    /* περιέχει το συνολικό χρόνο απασχόλησης της CPU από τη διεργασία */
    private int cpuTotalTime;
    /* περιέχει τον εναπομείναντα χρόνο απασχόλησης της CPU από τη διεργασία */
    private int cpuRemainingTime;
    /* περιέχει την τρέχουσα κατάσταση της διεργασίας: 0 – Created/New, 1 – Ready/Waiting, 2 – Running, 3 –
    Terminated */
    private int currentState;
    /* χρόνος απόκρισης */
    private int responseTime;
    /* χρόνος επιστροφής*/
    private int turnAroundTime;
    /* χρόνος αναμονής στο readyQueue*/
    private int waitingTime;
    /* πρώτη φορά που χρησιμοποιήσε την cpu*/
    private int cpuFirstTimeUsed;

    /* constructor – αρχικοποίηση των πεδίων */
    public Process(int pid, int arrivalTime, int cpuBurstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.cpuTotalTime = cpuBurstTime;
        this.cpuRemainingTime = cpuBurstTime;
    }

    // επιστρέφει τον εναπομίναντα χρόνο του cpu κατεγησμού
    public int getCpuRemainingTime() {
        return cpuRemainingTime;
    }

    // επιστρέφει τον κωδικό της διεργασίας
    public int getPid() {
        return pid;
    }

    // επιστρέφει την παρούσα κατάσταση της διεργασίας
    public int getCurrentState() {
        return currentState;
    }

    /* θέτει την κατάσταση της διεργασίας ίση με την παράμετρο newState (αλλαγή της κατάστασής της) */
    public void setProcessState(int newState) {
        currentState = newState;
    }

    // επιστρέφει την χρονική στιγμή της άφηξης της διεργασίας
    public int getArrivalTime() {
        return arrivalTime;
    }

    //Επιστρέφει τον συνολικό χρόνο του CPU κατεγισμού
    public int getCpuTotalTime() {
        return cpuTotalTime;
    }

    /* επιστρέφει τον χρόνο απόκρισης*/
    public int getResponseTime() {
        return responseTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    /* επιστρέφει τον χρόνο επιστροφής */
    public int getTurnAroundtime() {
        return turnAroundTime;
    }

    /* αλλάζει τον εναπομείναντα χρόνο απασχόλησης της CPU από τη διεργασία */
    public void changeCpuRemainingTime(int CPUBurstUsed) {

        if (cpuRemainingTime == cpuTotalTime) {
            cpuFirstTimeUsed = Clock.showTime();
            responseTime = cpuFirstTimeUsed - arrivalTime;
        }
        cpuRemainingTime = cpuRemainingTime - CPUBurstUsed;
        if (cpuRemainingTime == 0) {
            turnAroundTime = Clock.showTime() - arrivalTime + 1;
            waitingTime = Clock.showTime() - arrivalTime - cpuTotalTime + 1;
            Statistics.getStatisticsFromAFinishedProcess(this);
        }
    }

    /* a comparator which helps us to sort the processes according to their CpuRemainingTime */
    public static Comparator<Process> CpuRemainingTime = new Comparator<Process>() {
        @Override
        public int compare(Process p1, Process p2) {
            return p1.getCpuRemainingTime() - p2.getCpuRemainingTime();
        }
    };

}

