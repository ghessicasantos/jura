package model;

import enums.ProfileType;
import enums.StatusProjects;
import org.jetbrains.annotations.NotNull;

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
    private Team teamOwner;


    public Project (String projectName,
                    String description,
                    LocalDate startDate,
                    LocalDate finishDate,
                    StatusProjects status,
                    User projectManager,
                    Team team
                    ){

        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.status = status;
        this.projectManager = projectManager;
        this.tasks = new ArrayList<>();
        this.teamOwner = team;
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

    public Team getTeamOwner() {
        return teamOwner;
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

    public void listTask(){
        for (Task task : tasks){
            System.out.println(task.getTaskName());
        }
    }

    public void setTeamOwner(Team teamOwner) {
        this.teamOwner = teamOwner;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public boolean canEditTeamLevel1(@NotNull User user){

        if(user.getProfileType() == ProfileType.MANAGER ){
            return true;
        }
        return false;
    }

    public boolean canEditTeamLevel2(@NotNull User user){

        if(user.getProfileType() == ProfileType.MANAGER || user.getProfileType() == ProfileType.ADMIN ){
            return true;
        }
        return false;
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
