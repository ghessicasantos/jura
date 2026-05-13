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
import java.util.Scanner;

public class ProjectController {

    Scanner scan = new Scanner(System.in);

    private ProjectService  projectService;

    private UserService userService;

    private TeamService teamService;

    public ProjectController(
            ProjectService projectService,
            UserService userService
    ) {

        this.projectService = projectService;
        this.userService = userService;
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
            System.out.println("Qual a data de início do projeto?");
            LocalDate projectStartDate = LocalDate.parse(scan.nextLine());
            System.out.println("Qual a previsão de término do projeto?");
            LocalDate projectFinishDate = LocalDate.parse(scan.nextLine());
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
            System.out.println("Qual time pertence esse projeto?");
            for (Team teams : teamService.getTeams()){
                System.out.println(teams);
            }
            Team projectTeam = teamService.findTeamByName(scan.nextLine());

            Project newProjet = projectService.createProject(projectName,projectDescription,projectStartDate,projectFinishDate,projectStatus,projectOwner,projectTeam);

            System.out.println("Novo projeto criado->" + newProjet.getProjectName());

    }
}
