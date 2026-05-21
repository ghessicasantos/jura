package controller;

import enums.StatusTasks;
import model.Project;
import model.TaskStatusReport;
import service.ReportService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ReportController {

    private Scanner scan;
    private ReportService reportService;

    public ReportController(ReportService reportService, Scanner scan) {
        this.reportService = reportService;
        this.scan = scan;
    }

    public void reportMenuActions() throws SQLException {
        while (true) {
            System.out.println("O que deseja fazer?");
            System.out.println("1 - Ver tarefas por status em todos os projetos");
            System.out.println("2 - Ver tarefas por status de um projeto");
            System.out.println("3 - Voltar");

            int option = Integer.parseInt(scan.nextLine());

            if (option == 1) {
                showAllProjectsTaskStatusReport();
            } else if (option == 2) {
                showProjectTaskStatusReport();
            } else if (option == 3) {
                break;
            }
        }
    }

    private void showAllProjectsTaskStatusReport() throws SQLException {
        List<TaskStatusReport> reports = reportService.getTaskStatusReportForAllProjects();

        if (reports.isEmpty()) {
            System.out.println("Ainda nao existem projetos cadastrados.");
            return;
        }

        for (TaskStatusReport report : reports) {
            printReport(report);
        }
    }

    private void showProjectTaskStatusReport() throws SQLException {
        List<Project> projects = reportService.getProjects();

        if (projects.isEmpty()) {
            System.out.println("Ainda nao existem projetos cadastrados.");
            return;
        }

        System.out.println("Projetos cadastrados:");
        for (Project project : projects) {
            System.out.println("- " + project.getProjectName());
        }

        System.out.println("Digite o nome do projeto:");
        String projectName = scan.nextLine();

        TaskStatusReport report = reportService.getTaskStatusReportByProject(projectName);

        if (report == null) {
            System.out.println("Projeto nao encontrado.");
            return;
        }

        printReport(report);
    }

    private void printReport(TaskStatusReport report) {
        System.out.println("Projeto: " + report.getProjectName());

        for (StatusTasks status : StatusTasks.values()) {
            System.out.println(status + ": " + report.getTasksByStatus().get(status));
        }

        System.out.println("Total: " + report.getTotalTasks());
        System.out.println();
    }
}
