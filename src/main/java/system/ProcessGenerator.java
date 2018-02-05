package system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ProcessGenerator {

    private static int pid = 0;
    /* αρχείο που αποθηκεύονται τα δεδομένα των νέων διεργασιών */
    private static File inputFile;
    private static Process newProcess;
    private static int maximumCPUBurst;
    private static int intervalBetweenNewProcess;

    /*
     * constructor της κλάσης; αν readFile == false δημιουργεί το αρχείο inputFile με όνομα filename για αποθήκευση,
     * αλλιώς ανοίγει το αρχείο inputFile για ανάγνωση
     */
    public ProcessGenerator(String filename, boolean readFile, int anIntervalBetweenNewProcess) {
        this(filename, readFile);
        maximumCPUBurst = 20;
        intervalBetweenNewProcess = anIntervalBetweenNewProcess;
    }

    /*
     * another constructor of the class
     */
    public ProcessGenerator(String filename, boolean readFile) {
        inputFile = new File(filename);
        if (readFile) {
            inputFile.setWritable(false);
            inputFile.setReadable(true);
        } else {
            inputFile.delete();
            inputFile.setWritable(true);
            inputFile.setReadable(false);
        }
    }

    /* δημιουργία μιας νέας διεργασίας με (ψευδο-)τυχαία χαρακτηριστικά */
    public static Process createProcess() {
        Random random = new Random();
        int cpuTotalTime = random.nextInt(maximumCPUBurst) + 1;
        Process process = new Process(pid, random.nextInt(intervalBetweenNewProcess) + pid * intervalBetweenNewProcess, cpuTotalTime);
        pid++;
        newProcess = process;
        inputFile.setReadable(true);
        return process;
    }

    /* αποθήκευση των στοιχείων της νέας διεργασίας στο αρχείο inputFile */
    public static void StoreProcessToFile() {
        if (newProcess != null) {
            String line = String.format("%d %d %d\n", newProcess.getPid(), newProcess.getArrivalTime(), newProcess.getCpuTotalTime());
            try (BufferedWriter reader = new BufferedWriter(new FileWriter(inputFile, true))) {
                reader.write(line);
            } catch (Exception e) {
                System.out.println("Something went wrong");
                System.exit(1);
            }
        }
        newProcess = null;
    }

    /* ανάγνωση των στοιχείων νέων διεργασιών από το αρχείο inputFile */
    public static List<Process> parseProcessFile() {
        List<Process> processList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Scanner scanner = new Scanner(line);
                Process newProcess = new Process(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                processList.add(newProcess);
                scanner.close();
            }
        } catch (Exception e) {
            System.out.println("SXXXomething Went Wrong");
            System.exit(0);
        }
        return processList;
    }
}
