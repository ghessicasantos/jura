package model;

import enums.StatusTasks;

import java.time.LocalDate;

public class Task {

    private String taskTitle;
    private String description;
    private LocalDate startDate;
    private LocalDate finishDate;
    private StatusTasks status;
    private User assignedUser;
    private Project project;

public Task(String taskTitle,
            String description,
            LocalDate startDate,
            LocalDate finishDate,
            StatusTasks status,
            User assignedUser,
            Project project) {

    this.taskTitle = taskTitle;
    this.description = description;
    this.startDate = startDate;
    this.finishDate = finishDate;
    this.status = status;
    this.assignedUser = assignedUser;
    this.project = project;

    }

    public String getTaskTitle() {
        return taskTitle;
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

    public StatusTasks getStatus() {
        return status;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public Project getProject() {
        return project;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
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

    public void setStatus(StatusTasks status) {
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
                "taskTitle='" + taskTitle + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", status=" + status +
                ", assignedUser=" + assignedUser +
                ", project=" + project +
                '}';
    }
}
