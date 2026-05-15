package repository;


import model.Team;
import model.TeamMember;
import model.User;
import service.TeamService;
import service.UserService;
import enums.CsvFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TeamMemberRepository {

    private UserService userService;

    private TeamService teamService;

    private static final String FILE_PATH = CsvFile.TEAM_MEMBER.getFileName();

    public TeamMemberRepository(UserService userService){
        this.userService = userService;

    }

    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }

    public void saveTeamMember(TeamMember teamMember) throws IOException {
        File file = new File(FILE_PATH);

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

        File file = new File(FILE_PATH);

        List<TeamMember> teamMembers = new ArrayList<>();

        if (file.exists()){

        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));

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

                if (member == null || team == null) {
                    continue;
                }


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
