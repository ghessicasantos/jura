package model;

import enums.StatusProjects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project {

    private String projectName;
    private String description;
    private LocalDate startDate;
    private LocalDate finishDate;
    private StatusProjects status;
    private User projectManager;
    private List<Task> tasks;


    public Project (String projectName,
                    String description,
                    LocalDate startDate,
                    LocalDate finishDate,
                    StatusProjects status,
                    User projectManager
                    ){

        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.status = status;
        this.projectManager = projectManager;
        this.tasks = new ArrayList<>();
    }

    public String getProjectName() {
        return projectName;
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

    public StatusProjects getStatus() {
        return status;
    }

    public User getProjectManager() {
        return projectManager;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public void setStatus(StatusProjects status) {
        this.status = status;
    }

    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTasks(Task task){
        tasks.add(task);
    }

    @Override
    public String toString() {
        return "Projects{" +
                "projectName='" + projectName + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", status=" + status +
                ", projectManager=" + projectManager +
                '}';
    }
}
