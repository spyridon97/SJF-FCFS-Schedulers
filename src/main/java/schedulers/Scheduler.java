package schedulers;

import system.Process;

public interface Scheduler {
    void addProcessToReadyList(Process process);

    void scheduleProcesses();
}
