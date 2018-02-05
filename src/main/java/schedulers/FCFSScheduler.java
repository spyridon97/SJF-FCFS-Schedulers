package schedulers;

import application.Simulation;
import system.CPU;
import system.Process;

public class FCFSScheduler implements Scheduler {

    /**
     * trivial constructor
     */
    public FCFSScheduler() {

    }

    /**
     * @param process that is going to be added in the readyQueue
     */
    @Override
    public void addProcessToReadyList(Process process) {
        process.setProcessState(1);
        Simulation.getReadyProcessesList().addProcess(process);
        // there is no need to sort anything in readyQueue of FCFS. we just get the first process of the readyQueue
    }

    /**
     * it's the function that runs the scheduler of FCFS and executes the swaps between the cpu and the readyQueue
     */
    @Override
    public void scheduleProcesses() {
        CPU cpu = Simulation.getCpu();

        // if there is no process in cpu
        if (cpu.observeCurrentProcess() == null) {
            // and there one in the readyQueue we swap in the top priority process in Cpu
            if (!Simulation.getReadyProcessesList().isEmpty()) {
                Process process = Simulation.getReadyProcessesList().getProcessToRunInCPU();
                process.setProcessState(2);
                cpu.addProcess(process);
            }
        }
        // if there is a process in the cpu and the remaining time of it is 0
        else if (cpu.observeCurrentProcess().getCpuRemainingTime() == 0) {
            // we remove the process
            cpu.observeCurrentProcess().setProcessState(3);
            cpu.removeProcess();

            // and add the next one (if there is one) which is located at the start of the queue (readyQueue)
            if (!Simulation.getReadyProcessesList().isEmpty()) {
                Process process = Simulation.getReadyProcessesList().getProcessToRunInCPU();
                process.setProcessState(2);
                cpu.addProcess(process);
            }
        }

        // if there is a process in the Cpu and the remaining time of it is > 0 we do nothing. That's FCFS
    }
}
