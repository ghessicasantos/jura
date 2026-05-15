package model;

import enums.StatusProjects;
import enums.StatusTask;

import java.time.LocalDate;

public class Task {

    private String taskTitle;
    private String description;
    private LocalDate startDate;
    private LocalDate finishDate;
    private StatusTask status;
    private User assignedUser;
    private Project project;
    private Team team;

public Task(String taskTitle,
            String description,
            LocalDate startDate,
            LocalDate finishDate,
            StatusTask status,
            User assignedUser,
            Project project,
            Team team){

    this.taskTitle = taskTitle;
    this.description = description;
    this.startDate = startDate;
    this.finishDate = finishDate;
    this.status = status;
    this.assignedUser = assignedUser;
    this.project = project;
    this.team = team;

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

    public StatusTask getStatus() {
        return status;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public Project getProject() {
        return project;
    }

    public Team getTeam() {
        return team;
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

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setTeam(Team team) {
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
                ", team=" + team +
                '}';
    }
}
