package controller;

import enums.StatusTasks;
import model.Project;
import model.User;
import model.Task;
import service.ProjectService;
import service.TaskService;
import service.UserService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class TaskController {

    Scanner scan;

    private TaskService  taskService;

    private UserService userService;

    private ProjectService  projectService;

    public TaskController(
            TaskService  taskService,
            ProjectService projectService,
            UserService userService

    ) {
        this(taskService, projectService, userService, new Scanner(System.in));
    }

    public TaskController(
            TaskService  taskService,
            ProjectService projectService,
            UserService userService,
            Scanner scan

    ) {

        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
        this.scan = scan;
    }

    public void taskMenuActions() throws SQLException {

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
            } else {
                break;
            }
        }
    }

    public void createTaskMenu() throws SQLException {

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
            System.out.println("Qual projeto pertence essa tarefa?");
            for (Project projects : projectService.getProjects()) {
                    System.out.println(projects.getProjectName());
                }
                Project taskProject = projectService.findProjectByName(scan.nextLine());
                if (taskProject == null) {
                    System.out.println("Projeto nao encontrado.");
                    return;
                }

                Task newTask;
                try {
                    newTask = taskService.createTask(taskTitle, description, startDate, finishDate, taskStatus, taskOwner, taskProject);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                System.out.println("Nova tarefa criada->" + newTask.getTaskTitle());
            }

    public void editTaskMenu() throws SQLException {

            System.out.println("Digite o título da tarefa que deseja editar:");
            taskService.getTasks().forEach(task -> System.out.println(task.getTaskTitle()));
            String taskTitle = scan.nextLine();
            Task targetTask = taskService.findTaskByTitle(taskTitle);
            if (targetTask == null) {
                System.out.println("Tarefa nao encontrada.");
                return;
            }

        while (true){
           System.out.println("Qual informação deseja atualizar?");
           System.out.println("1 - Título");
           System.out.println("2 - Descrição");
           System.out.println("3 - Data de Início");
           System.out.println("4 - Data de Finalização");
           System.out.println("5 - Status");
           System.out.println("6 - Owner");
           System.out.println("7 - Voltar");
           

           int option = Integer.parseInt(scan.nextLine());

           if(option == 1){
               System.out.println("Digite o novo título:");
               String newName = scan.nextLine();
               String nameUpdated =  taskService.changeTaskTitle(targetTask,newName);
               System.out.println(nameUpdated);

           }
           else if(option == 2){
               System.out.println("Digite a nova descrição:");
               String newDescription = scan.nextLine();
               String descriptionUpdated = taskService.changeDescription( targetTask,newDescription);
               System.out.println(descriptionUpdated);

           } else if (option == 3) {
               System.out.println("Digite a nova data de início:");
               String newDate = scan.nextLine();
               LocalDate newStartDate = LocalDate.parse(newDate);
               String dateUpdated = taskService.changeStartDate(targetTask,newStartDate);
               System.out.println(dateUpdated);

           } else if (option == 4) {
               System.out.println("Digite o novo data de finalização:");
               String newDate = scan.nextLine();
               LocalDate newFinishDate = LocalDate.parse(newDate);
               String dateUpdated = taskService.changeFinishDate(targetTask,newFinishDate);
               System.out.println(dateUpdated);

            } else if (option == 5) {
               System.out.println("Digite o novo status:");
               String newStatus = scan.nextLine();
               StatusTasks newTaskStatus = StatusTasks.valueOf(newStatus.toUpperCase());
               String statusUpdated = taskService.changeStatus(targetTask,newTaskStatus);
               System.out.println(statusUpdated);

            } else if (option == 6) {
               System.out.println("Digite o novo responsável:");
               User newTaskOwner = userService.findUserByEmail(scan.nextLine());
               String taskOwnerupdated = taskService.changeAssignedUser(targetTask, newTaskOwner);
               System.out.println(taskOwnerupdated);

            } else if (option == 7) {
                break;
            }
        }
    }
}
