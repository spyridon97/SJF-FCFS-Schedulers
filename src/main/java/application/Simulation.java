package application;

import schedulers.Scheduler;
import system.CPU;
import system.Clock;
import system.NewProcessTemporaryList;
import system.Process;
import system.ProcessGenerator;
import system.ReadyProcessesList;

import java.util.List;

public class Simulation {
    private static int simulationCycles;
    // Because we average burst time of the processes is 10.5 initialize the cycles that every process will need with 11
    // in case there is an experimental error
    private static final int CYCLES_PER_PROCESS = 11;

    private static CPU cpu;

    private static Clock clock;

    private static ProcessGenerator processGenerator;

    private static NewProcessTemporaryList processTemporaryList;

    private static ReadyProcessesList readyProcessesList;

    private static Scheduler scheduler;

    public Simulation(Scheduler scheduler, String processFileName, Boolean readFromFile) {
        Simulation.scheduler = scheduler;
        cpu = new CPU();
        clock = new Clock();
        processGenerator = new ProcessGenerator(processFileName, readFromFile, 5);
        processTemporaryList = new NewProcessTemporaryList();
        readyProcessesList = new ReadyProcessesList();

        List<Process> processList = ProcessGenerator.parseProcessFile();
        for (Process process : processList) {
            NewProcessTemporaryList.addNewProcess(process);
        }

    }

    public static void calculateSimulationCycles(int numberOfProcess) {
        simulationCycles = numberOfProcess * CYCLES_PER_PROCESS;
    }

    public void runSimulation() {
        Statistics statistics = new Statistics("results.txt");
        while (Clock.showTime() < simulationCycles) {
            Clock.Time_Run();
        }

        Statistics.exportStatistics();
        statistics.WriteStatistics2File();
    }

    public static int getSimulationCycles() {
        return simulationCycles;
    }

    public static CPU getCpu() {
        return cpu;
    }

    public static Clock getClock() {
        return clock;
    }

    public static ProcessGenerator getProcessGenerator() {
        return processGenerator;
    }

    public static NewProcessTemporaryList getProcessTemporaryList() {
        return processTemporaryList;
    }

    public static ReadyProcessesList getReadyProcessesList() {
        return readyProcessesList;
    }

    public static Scheduler getScheduler() {
        return scheduler;
    }

}
