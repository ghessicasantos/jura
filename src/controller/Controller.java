package controller;

import model.User;
import repository.TeamMemberRepository;
import service.ProjectService;
import service.TeamService;
import service.UserService;

import java.io.IOException;
import java.util.Scanner;

public class Controller {

    private Scanner scan = new Scanner(System.in);

    private UserController userController;

    private UserService userService;

    private TeamMemberRepository teamMemberRepository;

    private ProjectService projectService;

    private ProjectController projectController;

    private TeamController teamController;

    private TeamService teamService;

    public Controller() throws IOException{
        this.userService = new UserService();
        this.userController = new UserController(userService, scan);
        this.teamMemberRepository = new TeamMemberRepository(userService);
        this.projectService = new ProjectService();
        this.teamService = new TeamService(userService,teamMemberRepository);
        this.teamController = new TeamController(projectService,userService,teamService, scan);
        this.projectController = new ProjectController(projectService,userService,teamService,teamController, scan);
    }

    public void start() throws IOException{

        User loggedUser = null;

        System.out.println("Seja bem-vindo");
        System.out.println("Realize o login");
        while (loggedUser == null) {
        System.out.println("Digite seu Email:");

        String email = scan.nextLine();

        System.out.println("Digite sua senha:");
        String password = scan.nextLine();
        loggedUser = userService.login(email, password);

        if(loggedUser == null){
                System.out.println("Usuário ou senha inválidos");
            }
        }
        while (true){
            System.out.println("Selecione uma das opções abaixo:");
            System.out.println("1 - Usuário");
            System.out.println("2 - Team");
            System.out.println("3 - Task");
            System.out.println("4 - Projeto");
            System.out.println("5 - Sair");

            int option = Integer.parseInt(scan.nextLine());

            if (option == 1) {
                userController.userMenuActions(loggedUser);

            } else if (option == 2) {
                teamController.teamMenuActions();

            } else if(option == 4){
                projectController.projectMenuAction();

            } else if (option == 5) {
                break;

            }
        }
    }
}
