package service;

import enums.ProfileType;
import enums.StatusTask;
import model.*;

import java.time.LocalDate;

public class TaskService {

    private TaskRepository taskRepository;

    List<Task> tasks = new ArrayList<>();

    public TaskService() throws IOException{
        this.taskRepository = new TaskRepository();

        this.tasks = taskRepository.loadTask();
    }

    public Task createTask(String taskTitle, 
                            String description, 
                            LocalDate startDate, 
                            LocalDate finishDate, 
                            StatusTask status, 
                            User assignedUser, 
                            Project project,
                            Team team
    )  throws IOException {

    if (assignedUser == null) {
        throw new IllegalArgumentException("Responsavel pelo projeto nao encontrado.");
    }
    if (team == null) {
        throw new IllegalArgumentException("Time do projeto nao encontrado.");
    }
    if (findProjectByName(project) != null){
        throw new IllegalArgumentException("Ja existe um projeto com esse nome.");
    }
    if (assignedUser.getProfileType() == ProfileType.COLLABORATOR){
        throw new IllegalArgumentException("Colaborador nao pode ser responsavel pelo projeto.");
    }

    Task newTask = new Task(taskTitle,description,startDate,finishDate,status,assignedUser,project, team);
    
    tasks.add(newTask);

    taskRepository.saveTask(newTask);
    
    return newTask;
    }

    public String changeTaskTitle(User loggedUser, Task targetTask,String newTaskTitle){
        targetTask.setTaskTitle(newTaskTitle);
        return "Título da tarefa definido -> " + newTaskTitle;
    }

    public String changeDescription(User loggedUser, Task targetTask,String newTaskDescription){
        targetTask.setDescription(newTaskDescription);
        return "Descricao definida: " + newTaskDescription;
    }

    public String changeStartDate(User loggedUser, Task targetTask, LocalDate newStartDate){
        targetTask.setStartDate(newStartDate);
        return "Data de inicio definida -> " + newStartDate;
    }

    public String changeFinishDate(User loggedUser, Task targetTask,LocalDate newFinishDate){
        targetTask.setFinishDate(newFinishDate);
        return "Data de termino definida -> " + newFinishDate;
    }

    public String changeStatus(User loggedUser, Task targetTask,StatusTask newTaskStatus) {
        targetTask.setStatus(newTaskStatus);
        return "Novo Status definido -> " + newTaskStatus;
    }

    public String changeAssignedUser(User loggedUser, Task targetTask,User newAssignedUser) {
        targetTask.setAssignedUser(newAssignedUser);
        return "Responsavel pela tarefa definido -> " + newAssignedUser.getName();
    }

    public String changeTaskTeam(User loggedUser, Task targetTask,Team newTaskTeam) {
        if (newTaskTeam == null) {
            return "time nao encontrado.";
        }
        targetTask.setTeam(newTaskTeam);
        return "Time da tarefa definido -> " + newTaskTeam.getTeamName();
    }
    
    public String updateTaskTitle(User loggedUser, String newName) {
        loggedUser.setTaskTitle(newTitle);
        return "Título atualizado.";
    }

    public String updateTaskDescription(User loggedUser, String newDescription) {
        loggedUser.setDescription(newDescription);
        return "Descrição atualizada.";
    }

    public String updateTaskStartDate(User loggedUser, String newDate) {
        loggedUser.setStartDate(newDate);
        return "Data de início atualizada.";
    }

    public String updateTaskFinishDate(User loggedUser, String newDate) {
        loggedUser.setFinishDate(newDate);
        return "Data de finalização atualizada.";
    }

    public String updateTaskStatus(User loggedUser, String newStatus) {
        loggedUser.setStatus(newStatus);
        return "Status atualizado.";
    }

    public String updateTaskAssignedUser(User loggedUser, String newAssignedUser) {
        loggedUser.setAssignedUser(newAssignedUser);
        return "Responsável pela tarefa atualizado.";
    }

    public String updateTaskTeam(User loggedUser, String newTeam) {
        loggedUser.setTeamOwner(newOwner);
        return "Time atualizado.";
    }
}

