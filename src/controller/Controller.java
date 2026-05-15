package controller;

import model.User;
import service.ProjectService;
import service.TeamService;
import service.UserService;
import service.TaskService;

import java.io.IOException;
import java.util.Scanner;

public class Controller {

    private Scanner scan = new Scanner(System.in);

    private UserController userController;

    private UserService userService;

    private ProjectService projectService;

    private ProjectController projectController;

    private TeamController teamController;

    private TeamService teamService;

    private TaskService taskService;

    private TaskController taskController;


    public Controller() throws IOException{
        this.userService = new UserService();
        this.userController = new UserController(userService, scan);
        this.projectService = new ProjectService();
        this.teamService = new TeamService(userService);
        this.teamController = new TeamController(userService,teamService, scan);
        this.taskService = new TaskService();
        this.taskController = new TaskController(taskService, projectService, userService, scan);
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
                teamController.teamMenuActions(loggedUser);
            
            } else if (option == 3) {
                taskController.taskMenuActions();

            } else if(option == 4){
                projectController.projectMenuAction(loggedUser);

            } else if (option == 5) {
                break;

            }
        }
    }
}
