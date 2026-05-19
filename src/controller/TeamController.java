package controller;

import model.Team;
import model.User;
import service.TeamMemberService;
import service.TeamService;
import service.UserService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class TeamController {

    Scanner scan;

    private UserService userService;

    private TeamService teamService;

    private TeamMemberController teamMemberController;

    public TeamController(
            UserService userService,
            TeamService teamService
    ) {
        this(userService, teamService, new Scanner(System.in));
    }

    public TeamController(
            UserService userService,
            TeamService teamService,
            Scanner scan
    ) {
        this.userService = userService;
        this.teamService = teamService;
        this.scan = scan;
        this.teamMemberController = new TeamMemberController(
                userService,
                new TeamMemberService(userService, teamService),
                scan
        );
    }
    public void teamMenuActions(User loggedUser) throws SQLException {

        while (true) {
            System.out.println("O que deseja fazer?");
            System.out.println("1 - Criar time");
            System.out.println("2 - Editar time");
            System.out.println("3 - Membros do time");
            System.out.println("4 - Voltar");

            int option = Integer.parseInt(scan.nextLine());

            if (option == 1) {
                createTeamMenu();
            } else if (option == 2) {
                editTeamMenu(loggedUser);
            } else if (option == 3) {
                teamMemberController.teamMemberMenuActions(loggedUser);
            } else if (option == 4) {
                break;

            }
        }
    }
    public void createTeamMenu() throws SQLException {

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

    public void editTeamMenu(User loggedUser) throws SQLException {

            System.out.println("Digite o nome do time que deseja editar:");
            teamService.getTeams().forEach(team -> System.out.println(team.getTeamName()));
            String teamName = scan.nextLine();
            Team targetTeam = teamService.findTeamByName(teamName);
            if (targetTeam == null) {
                System.out.println("Time nao encontrado.");
                return;
            }
        while (true){
           System.out.println("Qual informação deseja atualizar?");
           System.out.println("1 - Nome");
           System.out.println("2 - Descrição");
           System.out.println("3 - Responsável");
           System.out.println("4 - Voltar");


           int option = Integer.parseInt(scan.nextLine());

           if(option == 1){
               System.out.println("Digite o novo nome:");
               String newName = scan.nextLine();
               String nameUpdated =  teamService.updateTeamName(targetTeam, newName);
               System.out.println(nameUpdated);
           }
           else if(option == 2){
               System.out.println("Digite a nova descrição:");
               String newDescription = scan.nextLine();
               String descriptionUpdated = teamService.updateTeamDescription(targetTeam, newDescription);
               System.out.println(descriptionUpdated);
           } else if (option == 3) {
               System.out.println("Digite o novo responsável:");
               String newOwner = scan.nextLine();
               User newOwnerUser = userService.findUserByEmail(newOwner);
               String ownerUpdated = teamService.changeTeamOwner(loggedUser, targetTeam, newOwnerUser);
               System.out.println(ownerUpdated);
           } else if (option == 4) {
               break;
           }
        }
    }
}
