package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import system.Process;
import system.ReadyProcessesList;

public class Statistics {
    /* ο τρέχων μέσος χρόνος αναμονής των διεργασιών προς εκτέλεση */
    private static float averageWaitingTime = 0;

    /* ο τρέχων συνολικός χρόνος αναμονής διεργασιών */
    private static int totalWaitingTime = 0;

    /* ο μέσος χρόνος απόκρισης */
    private static float averageResponseTime = 0;

    /* ο συνολικός χρόνος απόκρισης */
    private static int totalResponseTime = 0;

    /* ο μέσος χρόνος επιστροφής */
    private static float averageTurnAroundTime = 0;

    /* ο συνολικός χρόνος επιστροφής */
    private static int totalTurnAroundTime = 0;

    /* το τρέχον μέγιστο πλήθος διεργασιών προς εκτέλεση */
    private static int maximumLengthOfReadyProcessesList = 0;

    /* ο τρέχων συνολικός αριθμός διεργασιών */
    private static int totalNumberOfProcesses = 0;

    /* αρχείο που αποθηκεύονται τα στατιστικά δεδομένα */
    private static File outputFile;

    /* constructor της κλάσης */
    public Statistics(String filename) {

    }

    /*
     * ελέγχει το μήκος της λίστας έτοιμων διεργασιών και ενημερώνει, αν είναι απαραίτητο, την τιμή της μεταβλητής
     * maximumLengthOfReadyProcessesList
     */
    public static void UpdateMaximumListLength() {
        if (ReadyProcessesList.getListSize() > maximumLengthOfReadyProcessesList) {
            maximumLengthOfReadyProcessesList = ReadyProcessesList.getListSize();
        }
    }

    /* θέτουμε το όνομα του outPut file */
    public static void setOutputFile(String name) {
        outputFile = new File(name);
    }

    /* κάνει reset το πείραμα της προσομοίωσης */
    public static void resetExperiment() {
        averageWaitingTime = 0;
        totalWaitingTime = 0;
        averageResponseTime = 0;
        totalResponseTime = 0;
        averageTurnAroundTime = 0;
        totalTurnAroundTime = 0;
        maximumLengthOfReadyProcessesList = 0;
        totalNumberOfProcesses = 0;
    }

    /* υπολογίζει τον μέσο χρόνο αναμονής */
    private static void calculateAverageWaitingTime() {
        averageWaitingTime = (float) (1.0 * totalWaitingTime / totalNumberOfProcesses);
    }

    /* υπολογίζει τον μέσο χρόνο αναμονής */
    private static void calculateAverageResponseTime() {
        averageResponseTime = (float) (1.0 * totalResponseTime / totalNumberOfProcesses);
    }

    /* υπολογίζει τον μέσο χρόνο αναμονής */
    private static void calculateAverageTurnAroundTime() {
        averageTurnAroundTime = (float) (1.0 * totalTurnAroundTime / totalNumberOfProcesses);
    }

    /* παίρνει τα στατιστικά απο μία τελειωμένη διεργασία */
    public static void getStatisticsFromAFinishedProcess(Process process) {
        totalNumberOfProcesses++;
        totalResponseTime += process.getResponseTime();
        totalWaitingTime += process.getWaitingTime();
        totalTurnAroundTime += process.getTurnAroundtime();
    }

    /* εξάγει τα στατιστικά */
    public static void exportStatistics() {
        calculateAverageResponseTime();
        calculateAverageTurnAroundTime();
        calculateAverageWaitingTime();
    }

    /* προσθέτει μια νέα γραμμή με τα τρέχοντα στατιστικά στο αρχείο outputFile */
    public void WriteStatistics2File() {
        System.out.println("\nWrite Results to the File of the Scheduler\n");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(String.format("Average Waiting Time: %.2f\n", averageWaitingTime));
            writer.write(String.format("Maximum Size of Ready Queue: %d\n", maximumLengthOfReadyProcessesList));
            writer.write(String.format("Average Response Time: %.2f\n", averageResponseTime));
            writer.write(String.format("Average Turnaround Time: %.2f\n", averageTurnAroundTime));
        } catch (IOException e) {
            System.out.println("Something went wrong");
            System.exit(1);
        }
    }
}
