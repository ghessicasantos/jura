package repository;

import enums.StatusProjects;
import model.Project;
import model.Team;
import model.User;
import service.TeamService;
import service.UserService;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeamRepository {

    public void saveProject(Project project) throws IOException {
        File file = new File("team.csv");

        boolean fileExists = file.exists();

        FileWriter writer = new FileWriter(file,true);

        if(!fileExists){
            writer.write("team_name;description;member;finish_date;status;project_manager;team_owner \n");
        }

        writer.write(project.getProjectName()+ ";"+
                        project.getDescription()+ ";"+
                        project.getStartDate()+ ";"+
                        project.getFinishDate()+ ";"+
                        project.getStatus()+ ";"+
                        project.getProjectManager()+ ";"+
                        project.getTeamOwner()+ "\n"
                );
        writer.close();
    }

    public List<Project> loadProject() throws IOException{

        UserService userService = new UserService();

        TeamService teamService = new TeamService();

        File file = new File("project.csv");

        List<Project> projects = new ArrayList<>();

        if (file.exists()){

        BufferedReader reader = new BufferedReader(new FileReader("project.csv"));

        String line;

        boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                firstLine = false;
                continue;
                }
            String[] data = line.split(";");

                User projectManager = userService.findUserByEmail(data[5]);
                Team projectTeam = teamService.findTeamByName(data[6]);


            Project project = new Project(
                    data[0],
                    data[1],
                    LocalDate.parse(data[2]),
                    LocalDate.parse(data[3]),
                    StatusProjects.valueOf(data[4]),
                    projectManager,
                    projectTeam);

                projects.add(project);
            }
            reader.close();
        }
        return projects;
    }
}
