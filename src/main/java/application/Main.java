package application;

import schedulers.FCFSScheduler;
import schedulers.SJFScheduler;
import schedulers.Scheduler;
import system.ProcessGenerator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int choice;
        Scanner input = new Scanner(System.in);
        ProcessGenerator processGenerator = new ProcessGenerator("input.txt", false, 5);
        System.out.println("Give a number of process in order to run the Simulation:");
        int number = input.nextInt();
        Simulation.calculateSimulationCycles(number);
        for (int i = 0; i < number; i++) {
            ProcessGenerator.createProcess();
            ProcessGenerator.StoreProcessToFile();
        }

        do {
            System.out.println("Choose the Scheduling algorithm that you want to use for the Simulation:");
            System.out.println("1) FCFS \n2) SJF non-preemptive\n3) SJF preemptive\n4) EXIT program");
            choice = input.nextInt();
            Statistics.resetExperiment();
            if (choice == 1) {
                Statistics.setOutputFile("fcfsResults.txt");
                Scheduler fcfs = new FCFSScheduler();
                Simulation fcfsSim = new Simulation(fcfs, "input.txt", true);
                fcfsSim.runSimulation();
            } else if (choice == 2) {
                Statistics.setOutputFile("sjfNonResults.txt");
                Scheduler sjfNon = new SJFScheduler(false);
                Simulation sjfNonSim = new Simulation(sjfNon, "input.txt", true);
                sjfNonSim.runSimulation();
            } else if (choice == 3) {
                Statistics.setOutputFile("sjfResults.txt");
                Scheduler sjf = new SJFScheduler(true);
                Simulation sjfSim = new Simulation(sjf, "input.txt", true);
                sjfSim.runSimulation();
            } else if (choice != 1 && choice != 2 && choice != 3 && choice != 4) {
                System.out.println("Give correct input\n");
            }
        } while (choice != 4);
    }
}
