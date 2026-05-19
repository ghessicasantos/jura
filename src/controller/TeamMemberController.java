package controller;

import model.Team;
import model.TeamMember;
import model.User;
import service.TeamMemberService;
import service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TeamMemberController {

    private Scanner scan;
    private UserService userService;
    private TeamMemberService teamMemberService;

    public TeamMemberController(
            UserService userService,
            TeamMemberService teamMemberService,
            Scanner scan
    ) {
        this.userService = userService;
        this.teamMemberService = teamMemberService;
        this.scan = scan;
    }

    public void teamMemberMenuActions(User loggedUser) throws SQLException {
        while (true) {
            System.out.println("O que deseja fazer?");
            System.out.println("1 - Listar membros por time");
            System.out.println("2 - Adicionar membro ao time");
            System.out.println("3 - Remover membro do time");
            System.out.println("4 - Voltar");

            int option = Integer.parseInt(scan.nextLine());

            if (option == 1) {
                listMembersByTeamMenu();
            } else if (option == 2) {
                addMemberMenu(loggedUser);
            } else if (option == 3) {
                removeMemberMenu(loggedUser);
            } else if (option == 4) {
                break;
            }
        }
    }

    private void listMembersByTeamMenu() {
        Team team = readTeam();
        if (team == null) {
            return;
        }

        List<TeamMember> members = teamMemberService.listActiveMembersByTeam(team);

        if (members.isEmpty()) {
            System.out.println("Esse time ainda nao possui membros ativos.");
            return;
        }

        for (TeamMember member : members) {
            System.out.println(member.getUser().getFullName() + " | " + member.getUser().getEmail());
        }
    }

    private void addMemberMenu(User loggedUser) throws SQLException {
        Team team = readTeam();
        if (team == null) {
            return;
        }

        User newMember = readUser();
        String result = teamMemberService.addMember(loggedUser, team, newMember);
        System.out.println(result);
    }

    private void removeMemberMenu(User loggedUser) throws SQLException {
        Team team = readTeam();
        if (team == null) {
            return;
        }

        User oldMember = readUser();
        String result = teamMemberService.removeMember(loggedUser, team, oldMember);
        System.out.println(result);
    }

    private Team readTeam() {
        List<Team> teams = teamMemberService.getTeams();

        if (teams.isEmpty()) {
            System.out.println("Ainda nao existem times cadastrados.");
            return null;
        }

        System.out.println("Times cadastrados:");
        for (Team team : teams) {
            System.out.println("- " + team.getTeamName());
        }

        System.out.println("Digite o nome do time:");
        String teamName = scan.nextLine();
        Team team = teamMemberService.findTeamByName(teamName);

        if (team == null) {
            System.out.println("Time nao encontrado.");
        }

        return team;
    }

    private User readUser() {
        System.out.println("Digite o email do usuario:");
        String email = scan.nextLine();
        User user = userService.findUserByEmail(email);

        if (user == null) {
            System.out.println("Usuario nao encontrado.");
        }

        return user;
    }
}
