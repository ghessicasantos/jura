package service;

import enums.ProfileType;
import enums.StatusProjects;
import model.Project;
import model.Team;
import model.User;
import repository.ProjectRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {

    private ProjectRepository projectRepository;

    List<Project> projects = new ArrayList<>();

    public ProjectService() throws IOException{
        this.projectRepository = new ProjectRepository();

        this.projects = projectRepository.loadProject();
    }

    public Project createProject(String projectName,
                                String description,
                                LocalDate startDate,
                                LocalDate finishDate,
                                StatusProjects status,
                                User projectManager,
                                Team team
    ) throws IOException {

        if (projectManager == null) {
            throw new IllegalArgumentException("Responsavel pelo projeto nao encontrado.");
        }
        if (team == null) {
            throw new IllegalArgumentException("Time do projeto nao encontrado.");
        }
        if (findProjectByName(projectName) != null){
            throw new IllegalArgumentException("Ja existe um projeto com esse nome.");
        }
        if (projectManager.getProfileType() == ProfileType.COLLABORATOR){
            throw new IllegalArgumentException("Colaborador nao pode ser responsavel pelo projeto.");
        }

        Project newProject = new Project(projectName,description,startDate,finishDate,status,projectManager,team);

        projects.add(newProject);

        projectRepository.saveProject(newProject);

        return newProject;
    }

    public String changeProjectName(User loggedUser, Project targetProject,String newProjectName){
        if(!targetProject.canEditTeamLevel2(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)){
            return "Usuario nao possui escopo para esta operacao.";
        }
        for (Project project : projects){
            if(project.getProjectName().equalsIgnoreCase(newProjectName)){
                return "Nome do projeto ja existe.";
            }
        }
        targetProject.setProjectName(newProjectName);
        return "Nome do projeto alterado -> " + newProjectName;
    }

    public String changeDescription(User loggedUser, Project targetProject,String newProjectDescription){
        if(!targetProject.canEditTeamLevel2(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)){
            return "Usuario nao possui escopo para esta operacao.";
        }
        targetProject.setDescription(newProjectDescription);
        return "Nova descricao definida.";
    }

    public String changeStartDate(User loggedUser, Project targetProject, LocalDate newStartDate){
        if(!targetProject.canEditTeamLevel2(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)){
            return "Usuario nao possui escopo para esta operacao.";
        }
        targetProject.setStartDate(newStartDate);
        return "Nova data de inicio definida -> " + newStartDate;
    }

    public String changeFinishDate(User loggedUser, Project targetProject,LocalDate newFinishDate){
        if(!targetProject.canEditTeamLevel2(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)){
            return "Usuario nao possui escopo para esta operacao.";
        }
        targetProject.setFinishDate(newFinishDate);
        return "Nova data de termino definida -> " + newFinishDate;
    }

    public String changeStatus(User loggedUser, Project targetProject,StatusProjects newProjectStatus) {
        if (!targetProject.canEditTeamLevel2(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)) {
            return "Usuario nao possui escopo para esta operacao.";
        }
        targetProject.setStatus(newProjectStatus);
        return "Novo Status definido -> " + newProjectStatus;
    }

    public String changeProjectManager(User loggedUser, Project targetProject,User newProjectManager) {
        if (!targetProject.canEditTeamLevel1(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)) {
            return "Usuario nao possui escopo para esta operacao.";
        }
        targetProject.setProjectManager(newProjectManager);
        return "Novo responsavel pelo projeto definido -> " + newProjectManager;
    }

    public String changeProjectTeam(User loggedUser, Project targetProject,Team newProjectTeam) {
        if (!targetProject.canEditTeamLevel1(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)) {
            return "Usuario nao possui escopo para esta operacao.";
        }
        if (newProjectTeam == null) {
            return "time nao encontrado.";
        }
        targetProject.setTeamOwner(newProjectTeam);
        return "Novo time do projeto definido -> " + newProjectTeam.getTeamName();
    }

    public Project findProjectByName(String projectName) {
        for (Project project : projects) {
            if (project.getProjectName().equalsIgnoreCase(projectName)) {
                return project;
            }
        }
        return null;
    }
}
