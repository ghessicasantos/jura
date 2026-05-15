package service;

import enums.StatusTasks;
import model.*;
import repository.TaskRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class TaskService {

    private TaskRepository taskRepository;

    public TaskService() throws IOException{
        this.taskRepository = new TaskRepository();
        this.tasks = taskRepository.loadTask();
    }

    List<Task> tasks;
    
    public Task createTask(String taskTitle, 
                            String description, 
                            LocalDate startDate, 
                            LocalDate finishDate, 
                            StatusTasks status, 
                            User assignedUser, 
                            Project project
    )  throws IOException {

    if (assignedUser == null) {
        throw new IllegalArgumentException("Responsavel pela tarefa nao encontrado.");
    }

    if (project == null) {
        throw new IllegalArgumentException("Projeto nao encontrado.");
    }

    Task newTask = new Task(taskTitle,description,startDate,finishDate,status,assignedUser,project);
    
    tasks.add(newTask);

    taskRepository.saveTask(newTask);
    
    return newTask;
    }

    public String changeTaskTitle(Task targetTask,String newTaskTitle){
        targetTask.setTaskTitle(newTaskTitle);
        return "Título da tarefa definido -> " + newTaskTitle;
    }

    public String changeDescription(Task targetTask,String newTaskDescription){
        targetTask.setDescription(newTaskDescription);
        return "Descricao definida: " + newTaskDescription;
    }

    public String changeStartDate(Task targetTask, LocalDate newStartDate){
        targetTask.setStartDate(newStartDate);
        return "Data de inicio definida -> " + newStartDate;
    }

    public String changeFinishDate(Task targetTask,LocalDate newFinishDate){
        targetTask.setFinishDate(newFinishDate);
        return "Data de termino definida -> " + newFinishDate;
    }

    public String changeStatus(Task targetTask,StatusTasks newTaskStatus) {
        targetTask.setStatus(newTaskStatus);
        return "Novo Status definido -> " + newTaskStatus;
    }

    public String changeAssignedUser(Task targetTask,User newAssignedUser) {
        targetTask.setAssignedUser(newAssignedUser);
        return "Responsavel pela tarefa definido -> " + newAssignedUser.getFullName();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task findTaskByTitle(String taskTitle) {
        for (Task task : tasks) {
            if (task.getTaskTitle().equalsIgnoreCase(taskTitle)) {
                return task;
            }
        }
        return null;
    }
    
}

