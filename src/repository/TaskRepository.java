package repository;

import enums.StatusTasks;
import model.Task;
import model.User;
import model.Project;
import service.UserService;
import service.ProjectService;
import enums.CsvFile;


import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private static final String FILE_PATH = CsvFile.TASK.getFileName();

    public void saveTask(Task task) throws IOException {
        File file = new File(FILE_PATH);

        boolean fileExists = file.exists();

        FileWriter writer = new FileWriter(file,true);

        if(!fileExists){
            writer.write("task_title;description;start_date;finish_date;status;assigned_user;project; \n");
        }

        writer.write(task.getTaskTitle()+ ";"+
                        task.getDescription()+ ";"+
                        task.getStartDate()+ ";"+
                        task.getFinishDate()+ ";"+
                        task.getStatus()+ ";"+
                        task.getAssignedUser().getEmail()+ ";"+
                        task.getProject().getProjectName()+ ";"
                );
        writer.close();
    }

    public List<Task> loadTask() throws IOException {

        UserService userService = new UserService();

        ProjectService projectService = new ProjectService();

        File file = new File(FILE_PATH);

        List<Task> tasks = new ArrayList<>();

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
                if (data.length < 7) {
                    continue;
                }

                
            User assignedUser = userService.findUserByEmail(data[5]);

            Project project = projectService.findProjectByName(data[6]);

            Task task = new Task(
                    data[0],
                    data[1],
                    LocalDate.parse(data[2]),
                    LocalDate.parse(data[3]),
                    StatusTasks.valueOf(data[4]),
                    assignedUser,
                    project);

                tasks.add(task);
            }
            reader.close();
        }
        return tasks;
    }
}
