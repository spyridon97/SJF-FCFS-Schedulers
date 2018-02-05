package schedulers;

import application.Simulation;
import system.CPU;
import system.Process;

import static system.Process.CpuRemainingTime;

public class SJFScheduler implements Scheduler {
    // the value which indicates if the scheduler is preemptive or not
    private boolean isPreemptive;

    /**
     * trivial contstructor
     *
     * @param isPreemptive the value which indicates if the scheduler is preemptive or not
     */
    public SJFScheduler(boolean isPreemptive) {
        this.isPreemptive = isPreemptive;
    }

    /**
     * adds the process in the ready queue and then sorts the readyQueue
     *
     * @param process that is going to be added in the readyQueue
     */
    @Override
    public void addProcessToReadyList(Process process) {
        process.setProcessState(1);
        Simulation.getReadyProcessesList().addProcess(process);

        // we sort the readyQueue according to CpuRemainingTime even if it's preemptive or not.
        // if it's preemptive we are gonna get the correct results and if it's not, we will do the same
        // because the CpuRemainingTime of the process will be equal to the TotalRemainingTime since
        // any of the processes in the readyQueue had ever been in Cpu.
        Simulation.getReadyProcessesList().sortList(CpuRemainingTime);
    }

    /**
     * it's the function that runs the scheduler of SJF and executes the swaps between the cpu and the readyQueue
     */
    @Override
    public void scheduleProcesses() {
        CPU cpu = Simulation.getCpu();

        // if SJF is preemptive then
        if (isPreemptive) {
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
            // if there is a process in the Cpu and the remaining time of it is > 0
            else if (cpu.observeCurrentProcess().getCpuRemainingTime() > 0) {
                // and if there is process in the ready queue
                if (!Simulation.getReadyProcessesList().isEmpty()) {
                    // and the cpu remaining time is less than the one which is located in the cpu
                    if (Simulation.getReadyProcessesList().getCpuRemainingTimeOfFirstProcess() < cpu.observeCurrentProcess().getCpuRemainingTime()) {
                        // then we swap out the process back to ready queue
                        cpu.observeCurrentProcess().setProcessState(1);
                        Simulation.getReadyProcessesList().addProcess(cpu.removeProcess());

                        // we swap in the first process of the readyQueue
                        Process process = Simulation.getReadyProcessesList().getProcessToRunInCPU();
                        process.setProcessState(2);
                        cpu.addProcess(process);

                        //  and we sort the ready Queue
                        Simulation.getReadyProcessesList().sortList(CpuRemainingTime);
                    }
                }
            }

        }
        // if SJF is NOT preemptive then
        else {
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

            // if there is a process in the Cpu and the remaining time of it is > 0 we do nothing.
            // That's SJF no preemptive
        }
    }
}
