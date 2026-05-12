package service;

import enums.ProfileType;
import enums.StatusProjects;
import model.Project;
import model.Team;
import model.User;
import org.jetbrains.annotations.NotNull;
import repository.ProjectRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {

    private ProjectRepository projectRepository;

    List<Team> teams = new ArrayList<>();
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
        if (projects.contains(projectName)){
            return null;
        }
        if (projectManager.getProfileType() == ProfileType.COLLABORATOR){
            return null;
        }

        Project newProject = new Project(projectName,description,startDate,finishDate,status,projectManager,team);

        projects.add(newProject);

        projectRepository.saveProject(newProject);

        return newProject;
    }

    public String changeProjectName(User loggedUser,@NotNull Project targetProject,String newProjectName){
        if(!targetProject.canEditTeamLevel2(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)){
            return "Usuário não possui escopo para esta operação.";
        }
        for (Project project : projects){
            if(project.getProjectName().equalsIgnoreCase(newProjectName)){
                return "Nome do projeto já existe.";
            }
        }
        targetProject.setProjectName(newProjectName);
        return "Nome do projeto alterado -> " + newProjectName;
    }

    public String changeDescription(User loggedUser,@NotNull Project targetProject,String newProjectDescription){
        if(!targetProject.canEditTeamLevel2(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)){
            return "Usuário não possui escopo para esta operação.";
            }
        targetProject.setDescription(newProjectDescription);
        return "Nova descrição definida.";
    }

    public String changeStartDate(User loggedUser, @NotNull Project targetProject, LocalDate newStartDate){
        if(!targetProject.canEditTeamLevel2(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)){
            return "Usuário não possui escopo para esta operação.";
        }
        targetProject.setStartDate(newStartDate);
        return "Nova data de início definida -> " + newStartDate;
    }

    public String changeFinishDate(User loggedUser,@NotNull Project targetProject,LocalDate newFinishDate){
        if(!targetProject.canEditTeamLevel2(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)){
            return "Usuário não possui escopo para esta operação.";
        }
        targetProject.setFinishDate(newFinishDate);
        return "Nova data de término definida -> " + newFinishDate;
    }

    public String changeStatus(User loggedUser,@NotNull Project targetProject,StatusProjects newProjectStatus) {
        if (!targetProject.canEditTeamLevel2(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)) {
            return "Usuário não possui escopo para esta operação.";
        }
        targetProject.setStatus(newProjectStatus);
        return "Novo Status definido -> " + newProjectStatus;
    }

    public String changeProjectManager(User loggedUser,@NotNull Project targetProject,User newProjectManager) {
        if (!targetProject.canEditTeamLevel1(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)) {
            return "Usuário não possui escopo para esta operação.";
        }
        targetProject.setProjectManager(newProjectManager);
        return "Novo responsável pelo projeto definido -> " + newProjectManager;
    }

    public String changeProjectTeam(User loggedUser,@NotNull Project targetProject,Team newProjectTeam) {
        if (!targetProject.canEditTeamLevel1(loggedUser) && !targetProject.getProjectManager().equals(loggedUser)) {
            return "Usuário não possui escopo para esta operação.";
        }
        for (Team team : teams){
            if (team.getTeamName().equalsIgnoreCase(newProjectTeam.getTeamName())){
                targetProject.setTeamOwner(newProjectTeam);
                return "Novo responsável pelo projeto definido -> " + newProjectTeam;
            }
        }
        return "time não encontrado.";
    }
}
