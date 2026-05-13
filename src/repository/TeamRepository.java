package repository;

import enums.StatusProjects;
import model.Project;
import model.Team;
import model.TeamMember;
import model.User;
import service.TeamService;
import service.UserService;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeamRepository {

    private UserService userService;

    private TeamService teamService;

    private TeamMemberRepository teamMemberRepository;

    private Team team;

    public TeamRepository(UserService userService,TeamService teamService){
        this.userService = userService;
        this.teamService = teamService;

    }

    public void saveTeam(Team team) throws IOException {
        File file = new File("team.csv");

        boolean fileExists = file.exists();

        FileWriter writer = new FileWriter(file,true);

        if(!fileExists){
            writer.write("team_name;description;team_owner_email;created_at\n");
        }

        writer.write(team.getTeamName()+ ";"+
                        team.getDescription()+ ";"+
                        team.getTeamOwner().getEmail()+ ";"+
                        team.getCreatedAt()+ "\n"
                );
        writer.close();
    }

    public List<Team> loadProject() throws IOException{

        File file = new File("team.csv");

        List<Team> teams = new ArrayList<>();

        if (file.exists()){

        BufferedReader reader = new BufferedReader(new FileReader("team.csv"));

        String line;

        boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                firstLine = false;
                continue;
                }
            String[] data = line.split(";");

                User teamOwner = userService.findUserByEmail(data[2]);
                LocalDate createdAt = LocalDate.parse(data[3]);


            Team team = new Team(
                    data[0],
                    data[1],
                    teamOwner,
                    createdAt
                    );

                List<TeamMember> allMembers = teamMemberRepository.loadTeamMembers();

                for(TeamMember member: allMembers){
                   if(member.getTeam().getTeamName().equalsIgnoreCase(team.getTeamName())){
                       team.addMember(member);
                   }
                }
            } teams.add(team);
            reader.close();
        }
        return teams;
    }
}
