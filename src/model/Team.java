package model;

import enums.ProfileType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Team {

    private String teamName;
    private String description;
    private User teamOwner;
    private List<TeamMember> members;
    private List<Project> projects;
    private LocalDate createdAt;

    public Team(String teamName,
                String description,
                User teamOwner,
                LocalDate createdAt){

        this.teamName = teamName;
        this.description = description;
        this.teamOwner = teamOwner;
        this.members = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.createdAt = createdAt;
    }

    public String getTeamName() {
        return teamName;
    }

    public User getTeamOwner() {
        return teamOwner;
    }

    public String getDescription() {
        return description;
    }

    public List<TeamMember> getMembers() {
        return members;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTeamOwner(User teamOwner) {
        this.teamOwner = teamOwner;
    }

    public void setMembers(List<TeamMember> members) {
        this.members = members;
    }

    public void addMember(TeamMember teamMember){
        members.add(teamMember);
    }

   public void addProject(Project project){
        projects.add(project);
   }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public boolean canEditTeamLevel1(User user) {

        if (user.getProfileType() == ProfileType.MANAGER) {
            return true;
        }
        return false;
    }

    public boolean canEditTeamLevel2(User user) {

        if (user.getProfileType() == ProfileType.MANAGER || user.getProfileType() == ProfileType.ADMIN) {
            return true;
        }
        return false;
    }

}
