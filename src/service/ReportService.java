package service;

import model.Project;
import model.TaskStatusReport;
import repository.ReportRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportService {

    private ReportRepository reportRepository;
    private ProjectService projectService;

    public ReportService(ProjectService projectService) {
        this.reportRepository = new ReportRepository();
        this.projectService = projectService;
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
}
