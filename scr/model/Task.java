package model;

import enums.StatusProjects;
import enums.StatusTask;

import java.time.LocalDate;

public class Task {

    private String taskName;
    private String description;
    private LocalDate startDate;
    private LocalDate finishDate;
    private StatusTask status;
    private User assignedUser;
    private Project project;

public Task(String taskName,
            String description,
            LocalDate startDate,
            LocalDate finishDate,
            StatusTask status,
            User assignedUser,
            Project project){

    this.taskName = taskName;
    this.description = description;
    this.startDate = startDate;
    this.finishDate = finishDate;
    this.status = status;
    this.assignedUser = assignedUser;
    this.project = project;

    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public StatusTask getStatus() {
        return status;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public Project getProject() {
        return project;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", status=" + status +
                ", assignedUser=" + assignedUser +
                ", project=" + project +
                '}';
    }
}
