package service;

import enums.ProfileType;
import enums.StatusProjects;
import model.Project;
import model.Team;
import model.User;
import repository.ProjectRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService() throws SQLException {
        this.projectRepository = new ProjectRepository();
        this.projects = projectRepository.loadProject();
    }

    List<Project> projects;

    public Project createProject(String projectName,
                                String description,
                                LocalDate startDate,
                                LocalDate finishDate,
                                StatusProjects status,
                                User projectManager,
                                Team team
    ) throws SQLException {

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

    String deniedPermissionString = "Usuário não possui escopo para esta operacão.";

    public String changeProjectName(User loggedUser, Project targetProject,String newProjectName) throws SQLException {
        if(!targetProject.canEditTeamLevel2(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)){
            return deniedPermissionString;
        }
        for (Project project : projects){
            if(project.getProjectName().equalsIgnoreCase(newProjectName)){
                return "Nome do projeto ja existe.";
            }
        }
        projectRepository.saveProjectHistory(targetProject);
        targetProject.setProjectName(newProjectName);
        return "Nome do projeto alterado -> " + newProjectName;
    }

    public String changeDescription(User loggedUser, Project targetProject,String newProjectDescription) throws SQLException {
        if(!targetProject.canEditTeamLevel2(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)){
            return deniedPermissionString;
        }
        projectRepository.saveProjectHistory(targetProject);
        targetProject.setDescription(newProjectDescription);
        projectRepository.saveProject(targetProject);
        return "Nova descricao definida.";
    }

    public String changeStartDate(User loggedUser, Project targetProject, LocalDate newStartDate) throws SQLException {
        if(!targetProject.canEditTeamLevel2(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)){
            return deniedPermissionString;
        }
        projectRepository.saveProjectHistory(targetProject);
        targetProject.setStartDate(newStartDate);
        return "Nova data de inicio definida -> " + newStartDate;
    }

    public String changeFinishDate(User loggedUser, Project targetProject,LocalDate newFinishDate) throws SQLException {
        if(!targetProject.canEditTeamLevel2(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)){
            return deniedPermissionString;
        }
        projectRepository.saveProjectHistory(targetProject);
        targetProject.setFinishDate(newFinishDate);
        return "Nova data de termino definida -> " + newFinishDate;
    }

    public String changeStatus(User loggedUser, Project targetProject,StatusProjects newProjectStatus) throws SQLException {
        if (!targetProject.canEditTeamLevel2(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)) {
            return deniedPermissionString;
        }
        projectRepository.saveProjectHistory(targetProject);
        targetProject.setStatus(newProjectStatus);
        return "Novo Status definido -> " + newProjectStatus;
    }

    public String changeProjectManager(User loggedUser, Project targetProject,User newProjectManager) throws SQLException {
        if (!targetProject.canEditTeamLevel1(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)) {
            return deniedPermissionString;
        }
        projectRepository.saveProjectHistory(targetProject);
        targetProject.setProjectManager(newProjectManager);
        return "Novo responsavel pelo projeto definido -> " + newProjectManager.getFullName();
    }

    public String changeProjectTeam(User loggedUser, Project targetProject,Team newProjectTeam) throws SQLException {
        if (!targetProject.canEditTeamLevel1(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)) {
            return deniedPermissionString;
        }
        if (newProjectTeam == null) {
            return "time nao encontrado.";
        }
        projectRepository.saveProjectHistory(targetProject);
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

    public List<Project> getProjects() {
        return projects;
    }

}
