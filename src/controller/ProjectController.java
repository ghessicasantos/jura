package controller;

import enums.StatusProjects;
import model.Project;
import model.Team;
import model.User;
import service.ProjectService;
import service.TeamService;
import service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ProjectController {

    Scanner scan;

    private ProjectService  projectService;

    private UserService userService;

    private TeamService teamService;

    private TeamController teamController;

    public ProjectController(
            ProjectService projectService,
            UserService userService,
            TeamService teamService,
            TeamController teamController

    ) {
        this(projectService, userService, teamService, teamController, new Scanner(System.in));
    }

    public ProjectController(
            ProjectService projectService,
            UserService userService,
            TeamService teamService,
            TeamController teamController,
            Scanner scan

    ) {

        this.projectService = projectService;
        this.userService = userService;
        this.teamService = teamService;
        this.teamController = teamController;
        this.scan = scan;
    }

    public void projectMenuAction() throws IOException {

        while(true) {

            System.out.println("O que deseja fazer?");
            System.out.println("1 - Criar projeto");
            System.out.println("2 - Editar projeto");
            System.out.println("3 - Voltar");

            int option = Integer.parseInt(scan.nextLine());

            if (option == 1) {
                createProjectMenu();
            } else {
                break;

            }
        }
    }

    public void createProjectMenu() throws IOException {

            System.out.println("Qual o nome do novo projeto?");
            String projectName = scan.nextLine();
            System.out.println("Qual a descrição do projeto?");
            String projectDescription = scan.nextLine();

            LocalDate projectStartDate = null;
            while (true){
                try {
                    System.out.println("Qual a data de início do projeto?");
                    projectStartDate = LocalDate.parse(scan.nextLine());
                    break;
                } catch (DateTimeParseException e ){
                    System.out.println("Data inválida. Tente novamente");
                }
            }

            LocalDate projectFinishDate = null;
            while (true){
                try {
                System.out.println("Qual a data de término do projeto?");
                    projectFinishDate = LocalDate.parse(scan.nextLine());
                break;
                } catch (DateTimeParseException e ){
                System.out.println("Data inválida. Tente novamente");
                 }
             }

            System.out.println("Qual o Status inicial do projeto?");
            for (StatusProjects status : StatusProjects.values()) {
                System.out.println(status);
            }
            StatusProjects projectStatus = StatusProjects.valueOf(scan.nextLine().toUpperCase());
            System.out.println("Qual o dono do projeto?");
            for (User user : userService.getUsers()) {
                System.out.println(user.getFullName() + " | "+ user.getEmail());
            }
            System.out.println("Insira o email do owner:");
            User projectOwner = userService.findUserByEmail(scan.nextLine());
            if (projectOwner == null) {
                System.out.println("Dono do projeto nao encontrado.");
                return;
            }
            System.out.println("Qual time pertence esse projeto?");
            if(teamService.getTeams().isEmpty()) {
                System.out.println("Ainda não existem times disponíveis. Crie um time para prosseguir");
                teamController.createTeamMenu();
                return;
            } else {
                for (Team teams : teamService.getTeams()) {
                    System.out.println(teams);
                }
                Team projectTeam = teamService.findTeamByName(scan.nextLine());
                if (projectTeam == null) {
                    System.out.println("Time nao encontrado.");
                    return;
                }

                Project newProjet;
                try {
                    newProjet = projectService.createProject(projectName, projectDescription, projectStartDate, projectFinishDate, projectStatus, projectOwner, projectTeam);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                System.out.println("Novo projeto criado->" + newProjet.getProjectName());
            }
    }

    public void editTeamMenu(User loggedUser){

        while (true){
           System.out.println("Qual informação deseja atualizar?");
           System.out.println("1 - Nome");
           System.out.println("2 - Descrição");
           System.out.println("3 - Data de Início");
           System.out.println("4 - Data de Finalização");
           System.out.println("5 - Status");
           System.out.println("6 - Gerente");
           System.out.println("7 - Time");
           

           int option = Integer.parseInt(scan.nextLine());

           if(option == 1){
               System.out.println("Digite o novo nome:");
               String newName = scan.nextLine();
               String nameUpdated =  projectService.updateProjectName(loggedUser,newName);
               System.out.println(nameUpdated);

           }
           else if(option == 2){
               System.out.println("Digite a nova descrição:");
               String newDescription = scan.nextLine();
               String descriptionUpdated = projectService.updateProjectDescription(loggedUser,newDescription);
               System.out.println(descriptionUpdated);

           } else if (option == 3) {
               System.out.println("Digite a nova data de início:");
               String newDate = scan.nextLine();
               String dateUpdated = projectService.updateProjectStartDate(loggedUser,newDate);
               System.out.println(dateUpdated);

           } else if (option == 4) {
               System.out.println("Digite o novo data de finalização:");
               String newDate = scan.nextLine();
               String dateUpdated = projectService.updateProjectFinishDate(loggedUser,newDate);
               System.out.println(dateUpdated);

            } else if (option == 5) {
               System.out.println("Digite o novo status:");
               String newStatus = scan.nextLine();
               String statusUpdated = projectService.updateProjectStatus(loggedUser,newStatus);
               System.out.println(statusUpdated);

            } else if (option == 6) {
               System.out.println("Digite o novo gerente:");
               String newProjectManager = scan.nextLine();
               String projectManagerUpdated = projectService.setProjectManager(loggedUser,newProjectManager);
               System.out.println(projectManagerUpdated);

            } else if (option == 7) {
               System.out.println("Digite o novo time:");
               String newTeam = scan.nextLine();
               String teamUpdated = projectService.updateProjectTeam(loggedUser,newTeam);
               System.out.println(teamUpdated);
           
            } else
               break;
        }
    }
}
