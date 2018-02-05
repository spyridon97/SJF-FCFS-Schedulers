package system;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import application.Simulation;

public class NewProcessTemporaryList {

    /* η λίστα που περιέχει τις νέες διεργασίες */
    private static List<Process> processList;

    /* constructor - αρχικοποίηση της λίστας και άλλων πεδίων */
    public NewProcessTemporaryList() {
        processList = new LinkedList<>();
    }

    /* εισαγωγή μιας νέας διεργασίας στη λίστα */
    public static void addNewProcess(Process process) {
        processList.add(process);
    }

    /* επιστροφή της πρώτης διεργασίας της λίστας */
    public static Process getFirst() {
        Process process = processList.get(0);
        processList.remove(0);
        return process;
    }

    // Επιστρέφει το μέγεθος της λίστας
    public static int listSize() {
        return processList.size();
    }

    /* εκτύπωση της λίστας με τις νέες διεργασίες στην οθόνη */
    public static void printList() {
        System.out.println("Ουρά διεργασιών");
        System.out.println("pid\tarrivalTime\tcpuTotalTime\tcpuRemainingTime\tCurrentState");
        for (Process process : processList) {
            System.out.printf("%d\t%d\t%d\t%d\n", process.getPid(), process.getArrivalTime(), process.getCpuRemainingTime(), process.getCurrentState());
        }
    }

    /* μεταφέρουμε όλες τις διεργασίες που έχουν ίσο arrival time με τον χρόνο του ρολογιού */
    public List<Process> migrateProcesses() {
        List<Process> migrateList = new ArrayList<>();
        for (Process p : processList) {
            if (p.getArrivalTime() == Simulation.getClock().showTime()) {
                migrateList.add(p);
            }
        }

        return migrateList;
    }
}
