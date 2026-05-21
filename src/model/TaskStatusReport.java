package model;

import enums.StatusTasks;

import java.util.EnumMap;
import java.util.Map;

public class TaskStatusReport {

    private String projectName;
    private Map<StatusTasks, Integer> tasksByStatus;

    public TaskStatusReport(String projectName) {
        this.projectName = projectName;
        this.tasksByStatus = new EnumMap<>(StatusTasks.class);

        for (StatusTasks status : StatusTasks.values()) {
            tasksByStatus.put(status, 0);
        }
    }

    public String getProjectName() {
        return projectName;
    }

    public Map<StatusTasks, Integer> getTasksByStatus() {
        return tasksByStatus;
    }

    public void setStatusTotal(StatusTasks status, int total) {
        tasksByStatus.put(status, total);
    }

    public int getTotalTasks() {
        int total = 0;

        for (Integer statusTotal : tasksByStatus.values()) {
            total += statusTotal;
        }

        return total;
    }
}
