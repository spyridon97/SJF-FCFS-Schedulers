package system;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReadyProcessesList {
    /* η λίστα που περιέχει τις έτοιμες διεργασίες */
    private static List<Process> processList;

    /* ο constructor της κλάσης */
    public ReadyProcessesList() {
        processList = new ArrayList<>();
    }

    /* προσθήκη μίας νέας έτοιμης διεργασίας στη λίστα */
    public void addProcess(Process process) {
        processList.add(process);
    }

    /* επιστροφή της διεργασίας της οποίας η σειρά είναι να εκτελεστεί στη CPU σύμφωνα με τον εκάστοτε αλγόριθμό δρομολόγησης */
    public Process getProcessToRunInCPU() {
        Process temp = processList.get(0);
        processList.remove(0);
        return temp;
    }

    /* gets the CpuRemainingTime of the first process in the ready queue */
    public int getCpuRemainingTimeOfFirstProcess() {
        return processList.get(0).getCpuRemainingTime();
    }

    /* checks if the ready queue is empty */
    public boolean isEmpty() {
        return processList.isEmpty();
    }

    /* εκτύπωση του περιεχομένου της λίστας στην οθόνη */
    public void printList() {
        for (Process p : processList) {
            System.out.println(p.getPid());
        }
    }

    /* επιστρέφει το μέγεθος της ready Queue */
    public static int getListSize() {
        return processList.size();
    }

    /* ταξινομεί κατα αύξουσα σειρά ως προς το CPURemaining time τις διεργασίες */
    public void sortList(Comparator<Process> readySort) {
        processList.sort(readySort);
    }
}
