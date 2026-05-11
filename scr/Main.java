import enums.ProfileType;
import model.Team;
import model.TeamMember;
import model.User;
import service.TeamService;
import service.UserService;

import java.sql.SQLOutput;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        User admin =
                new User("Pedro", "13970108780", "pedro_cs13@hotmail.com", "Analista de Dados", "Coffees1397", "130916Pg", "Coffes", ProfileType.admin);

        User collaborator =
                new User("Ghessica", "10871930790", "ghessicas@gmail.com", "Analytics Engineer", "GhessCafe", "130916Gp", "Ghess", ProfileType.colaborator);

        UserService userService =
                new UserService();

        String updateUserField = userService.updateUserField(
                collaborator,
                collaborator,
                "Nome Completo",
                "João Alencar"
        );


        System.out.println(updateUserField);

        //TeamService testes
        TeamService teamService = new TeamService();

        Team backendTeam = teamService.createTeam("Backend", "time responsável pela API", admin, LocalDate.now());

        System.out.println(backendTeam.getTeamName());

        String addMember = teamService.addMember(admin, backendTeam, collaborator);

        System.out.println(addMember);

        for (TeamMember member : backendTeam.getMembers()){
            System.out.println(member.getUser().getFullName());
        }

        String removeMember = teamService.removeMember(admin,backendTeam,collaborator);

        System.out.println(removeMember);
    }
}