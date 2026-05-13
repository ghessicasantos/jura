package repository;


import model.Team;
import model.TeamMember;
import model.User;
import service.TeamService;
import service.UserService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TeamMemberRepository {

    private UserService userService;

    private TeamService teamService;

    public TeamMemberRepository(UserService userService,TeamService teamService){
        this.userService = userService;
        this.teamService = teamService;
    }

    public void saveTeamMember(TeamMember teamMember) throws IOException {
        File file = new File("team_member.csv");

        boolean fileExists = file.exists();

        FileWriter writer = new FileWriter(file,true);

        if(!fileExists){
            writer.write("team_member_email;team;member_status\n");
        }

        writer.write(teamMember.getUser().getEmail()+ ";"+
                        teamMember.getTeam().getTeamName()+ ";"+
                        teamMember.isActive()+ "\n"
                );
        writer.close();
    }

    public List<TeamMember> loadTeamMembers() throws IOException{

        File file = new File("team_member.csv");

        List<TeamMember> teamMembers = new ArrayList<>();

        if (file.exists()){

        BufferedReader reader = new BufferedReader(new FileReader("team_member.csv"));

        String line;

        boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                firstLine = false;
                continue;
                }
            String[] data = line.split(";");

                User member = userService.findUserByEmail(data[0]);
                Team team = teamService.findTeamByName(data[1]);
                boolean active = Boolean.parseBoolean(data[2]);


            TeamMember teamMember = new TeamMember(
                    member,
                    team,
                    active
                    );

                teamMembers.add(teamMember);
            }
            reader.close();
        }
        return teamMembers;
    }
}
