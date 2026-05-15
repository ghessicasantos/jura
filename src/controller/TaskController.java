package controller;

import enums.StatusTask;
import model.Project;
import model.Team;
import model.User;
import model.Task;
import service.ProjectService;
import service.TaskService;
import service.TeamService;
import service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class TaskController {

    Scanner scan;

    private TaskService  TaskService;

    private UserService userService;

    private TeamService teamService;

    private ProjectService  projectService;

    private TeamController teamController;

    public TaskController(
            TaskService  taskService,
            ProjectService projectService,
            UserService userService,
            TeamService teamService,
            TeamController teamController

    ) {
        this(taskService, projectService, userService, teamService, teamController, new Scanner(System.in));
    }

    public ProjectController(
            TaskService  taskService,
            ProjectService projectService,
            UserService userService,
            TeamService teamService,
            TeamController teamController,
            Scanner scan

    ) {

        this.taskService = taskService
        this.projectService = projectService;
        this.userService = userService;
        this.teamService = teamService;
        this.teamController = teamController;
        this.scan = scan;
    }

    public void taskMenuAction() throws IOException {

        while(true) {

            System.out.println("O que deseja fazer?");
            System.out.println("1 - Criar tarefa");
            System.out.println("2 - Editar tarefa");
            System.out.println("3 - Voltar");

            int option = Integer.parseInt(scan.nextLine());

            if (option == 1) {
                createTaskMenu();
            } else if (option == 2) {
                editTaskMenu();
            }
            } else {
                break;

            }
        }

    public void createTaskMenu() throws IOException {

            System.out.println("Qual o título da nova tarefa?");
            String taskTitle = scan.nextLine();
            System.out.println("Qual a descrição da tarefa?");
            String description = scan.nextLine();

            LocalDate startDate = null;
            while (true){
                try {
                    System.out.println("Qual a data de início da tarefa?");
                    startDate = LocalDate.parse(scan.nextLine());
                    break;
                } catch (DateTimeParseException e ){
                    System.out.println("Data inválida. Tente novamente");
                }
            }

            LocalDate finishDate = null;
            while (true){
                try {
                System.out.println("Qual a data de término da tarefa?");
                    finishDate = LocalDate.parse(scan.nextLine());
                break;
                } catch (DateTimeParseException e ){
                System.out.println("Data inválida. Tente novamente");
                 }
             }

            System.out.println("Qual o Status inicial da tarefa?");
            for (StatusTasks status : StatusTasks.values()) {
                System.out.println(status);
            }
            StatusTasks taskStatus = StatusTasks.valueOf(scan.nextLine().toUpperCase());
            System.out.println("Qual o dono da tarefa?");
            for (User user : userService.getUsers()) {
                System.out.println(user.getFullName() + " | "+ user.getEmail());
            }
            System.out.println("Insira o email do owner:");
            User taskOwner = userService.findUserByEmail(scan.nextLine());
            if (taskOwner == null) {
                System.out.println("Dono do projeto nao encontrado.");
                return;
            }
            System.out.println("Qual time pertence essa tarefa?");
            if(teamService.getTeams().isEmpty()) {
                System.out.println("Ainda não existem times disponíveis. Crie uma tarefa para prosseguir");
                teamController.createTeamMenu();
                return;
            } else {
                for (Team teams : teamService.getTeams()) {
                    System.out.println(teams);
                }
                Team taskTeam = teamService.findTeamByName(scan.nextLine());
                if (taskTeam == null) {
                    System.out.println("Time nao encontrado.");
                    return;
                }

                Task newTask;
                try {
                    newTask = taskService.createTask(taskTitle, taskDescription, taskStartDate, taskFinishDate, taskStatus, taskOwner, taskTeam);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                System.out.println("Nova tarefa criada->" + newTask.getTaskTitle());
            }
    }

    public void editTaskMenu(User loggedUser){

        while (true){
           System.out.println("Qual informação deseja atualizar?");
           System.out.println("1 - Título");
           System.out.println("2 - Descrição");
           System.out.println("3 - Data de Início");
           System.out.println("4 - Data de Finalização");
           System.out.println("5 - Status");
           System.out.println("6 - Owner");
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
