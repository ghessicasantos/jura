package service;

import enums.StatusTasks;
import model.*;
import repository.TaskRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TaskService {

    private TaskRepository taskRepository;

    public TaskService() throws SQLException {
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
    ) throws SQLException {

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

    public String changeTaskTitle(Task targetTask,String newTaskTitle) throws SQLException {
        String oldTaskTitle = targetTask.getTaskTitle();
        taskRepository.saveTaskHistory(targetTask);
        targetTask.setTaskTitle(newTaskTitle);
        taskRepository.updateTask(targetTask, oldTaskTitle);
        return "Título da tarefa definido -> " + newTaskTitle;
    }

    public String changeDescription(Task targetTask,String newTaskDescription) throws SQLException {
        String oldTaskTitle = targetTask.getTaskTitle();
        taskRepository.saveTaskHistory(targetTask);
        targetTask.setDescription(newTaskDescription);
        taskRepository.updateTask(targetTask, oldTaskTitle);
        return "Descricao definida: " + newTaskDescription;
    }

    public String changeStartDate(Task targetTask, LocalDate newStartDate) throws SQLException {
        String oldTaskTitle = targetTask.getTaskTitle();
        taskRepository.saveTaskHistory(targetTask);
        targetTask.setStartDate(newStartDate);
        taskRepository.updateTask(targetTask, oldTaskTitle);
        return "Data de inicio definida -> " + newStartDate;
    }

    public String changeFinishDate(Task targetTask,LocalDate newFinishDate) throws SQLException {
        String oldTaskTitle = targetTask.getTaskTitle();
        taskRepository.saveTaskHistory(targetTask);
        targetTask.setFinishDate(newFinishDate);
        taskRepository.updateTask(targetTask, oldTaskTitle);
        return "Data de termino definida -> " + newFinishDate;
    }

    public String changeStatus(Task targetTask,StatusTasks newTaskStatus) throws SQLException {
        String oldTaskTitle = targetTask.getTaskTitle();
        taskRepository.saveTaskHistory(targetTask);
        targetTask.setStatus(newTaskStatus);
        taskRepository.updateTask(targetTask, oldTaskTitle);
        return "Novo Status definido -> " + newTaskStatus;
    }

    public String changeAssignedUser(Task targetTask,User newAssignedUser) throws SQLException {
        String oldTaskTitle = targetTask.getTaskTitle();
        taskRepository.saveTaskHistory(targetTask);
        targetTask.setAssignedUser(newAssignedUser);
        taskRepository.updateTask(targetTask, oldTaskTitle);
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

    public String removeTask(Task task) throws SQLException {
        if (task == null) {
            return "Tarefa nao encontrada.";
        }

        taskRepository.saveTaskHistory(task);
        taskRepository.deleteTask(task);
        tasks.remove(task);

        return "Tarefa removida.";
    }
    
}

