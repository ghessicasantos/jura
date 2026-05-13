package Testes;
/*

import enums.ProfileType;
import enums.StatusProjects;
import model.Project;
import model.Team;
import model.TeamMember;
import model.User;
import service.ProjectService;
import service.TeamService;
import service.UserService;

import java.time.LocalDate;

public class Testes {

    import enums.ProfileType;
import enums.StatusProjects;
import model.Project;
import model.Team;
import model.TeamMember;
import model.User;
import service.ProjectService;
import service.TeamService;
import service.UserService;

import java.sql.SQLOutput;
import java.time.LocalDate;

            User manager =
                    new User("Carla","1092138080","carla_dasta@gmail.com","PM","carla123","1020304","Carlinha", ProfileType.manager);

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

            String changeOwner = teamService.changeTeamOwner(admin,backendTeam,collaborator);

            System.out.println(changeOwner);

            String changeOwner2 = teamService.changeTeamOwner(manager,backendTeam,admin);

            System.out.println(changeOwner2);

            //Teste ProjectService

            ProjectService projectService = new ProjectService();

            Project newProject = projectService.createProject("Projeto Kira","Criação de uma nova linha de crédito",LocalDate.now(),LocalDate.now(), StatusProjects.PLANEJADO,admin,backendTeam);

            System.out.println("Novo projeto criado -> " +  newProject.getProjectName());

            String changeProjectName = projectService.changeProjectName(admin,newProject,"Projeto kurama");

            System.out.println(changeProjectName);
        }
    }
}
**/