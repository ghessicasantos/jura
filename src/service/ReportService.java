package service;

import enums.StatusProjects;
import enums.StatusTasks;
import model.Project;
import model.Task;
import model.Team;
import model.TaskStatusReport;
import repository.ReportRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportService {

    private ReportRepository reportRepository;
    private ProjectService projectService;
    private TeamService teamService;
    private TaskService taskService;

    public ReportService(ProjectService projectService) throws SQLException {
        this.reportRepository = new ReportRepository();
        this.projectService = projectService;
        this.teamService = new TeamService();
        this.taskService = new TaskService();
    }

    public Map<StatusProjects, Integer> getProjectsByStatus() throws SQLException {
        return reportRepository.countProjectsByStatus();
    }

    public Map<StatusTasks, Integer> getTasksByStatus() throws SQLException {
        return reportRepository.countTasksByStatus();
    }

    public List<TaskStatusReport> getTaskStatusReportForAllProjects() throws SQLException {
        Map<String, TaskStatusReport> reportsByProject = reportRepository.countTasksByStatusAndProject();
        List<TaskStatusReport> reports = new ArrayList<>();

        for (Project project : projectService.getProjects()) {
            TaskStatusReport report = reportsByProject.get(project.getProjectName());

            if (report == null) {
                report = new TaskStatusReport(project.getProjectName());
            }

            reports.add(report);
        }

        return reports;
    }

    public TaskStatusReport getTaskStatusReportByProject(String projectName) throws SQLException {
        Project project = projectService.findProjectByName(projectName);

        if (project == null) {
            return null;
        }

        return reportRepository.countTasksByStatusForProject(project.getProjectName());
    }

    public List<Project> getProjects() {
        return projectService.getProjects();
    }

    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    public List<Team> getTeams() {
        return teamService.getTeams();
    }

    public int countActiveMembers(Team team) {
        return teamService.countActiveMembers(team);
    }

    public List<Project> getProjectsByTeam(Team team) {
        return projectService.getProjectsByTeam(team);
    }
}
