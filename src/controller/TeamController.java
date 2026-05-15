package controller;

import model.Team;
import model.User;
import service.ProjectService;
import service.TeamService;
import service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class TeamController {

    Scanner scan;

    private ProjectService projectService;

    private UserService userService;

    private TeamService teamService;

    public TeamController(
            ProjectService projectService,
            UserService userService,
            TeamService teamService
    ) {
        this(projectService, userService, teamService, new Scanner(System.in));
    }

    public TeamController(
            ProjectService projectService,
            UserService userService,
            TeamService teamService,
            Scanner scan
    ) {

        this.projectService = projectService;
        this.userService = userService;
        this.teamService = teamService;
        this.scan = scan;
    }
    public void teamMenuActions() throws IOException {

        while (true) {
            System.out.println("O que deseja fazer?");
            System.out.println("1 - Criar time");
            System.out.println("2 - Editar time");
            System.out.println("3 - Voltar");

            int option = Integer.parseInt(scan.nextLine());

            if (option == 1) {
                createTeamMenu();
            } else {
                break;

            }
        }
    }
    public void createTeamMenu() throws IOException {

        System.out.println("Qual o nome do time?");
        String name = scan.nextLine();
        System.out.println("Qual a descrição do time?");
        String description = scan.nextLine();
        System.out.println("Qual o email do responsável pelo time?");
        User teamOwner = userService.findUserByEmail(scan.nextLine());
        if (teamOwner == null) {
            System.out.println("Responsavel pelo time nao encontrado.");
            return;
        }
        LocalDate createdAt = LocalDate.now();

        Team newTeam;
        try {
            newTeam = teamService.createTeam(name,description,teamOwner,createdAt);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Novo Time criado --> "  + newTeam.getTeamName());


    }
}
