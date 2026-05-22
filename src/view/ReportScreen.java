package view;

import enums.StatusProjects;
import enums.StatusTasks;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Project;
import model.TaskStatusReport;
import model.User;
import service.ProjectService;
import service.ReportService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class ReportScreen {

    public void show(Stage stage, User loggedUser) throws SQLException {
        ProjectService projectService = new ProjectService();
        ReportService reportService = new ReportService(projectService);

        Label title = new Label("Relatorio");

        StringBuilder reportText = new StringBuilder();

        reportText.append("Projetos por status:\n");
        for (Map.Entry<StatusProjects, Integer> item : reportService.getProjectsByStatus().entrySet()) {
            reportText.append(item.getKey()).append(": ").append(item.getValue()).append("\n");
        }

        reportText.append("\nTarefas por status:\n");
        for (Map.Entry<StatusTasks, Integer> item : reportService.getTasksByStatus().entrySet()) {
            reportText.append(item.getKey()).append(": ").append(item.getValue()).append("\n");
        }

        reportText.append("\nDesempenho por projeto:\n");
        for (Project project : reportService.getProjects()) {
            TaskStatusReport taskReport = reportService.getTaskStatusReportByProject(project.getProjectName());

            reportText.append("\nProjeto: ").append(project.getProjectName()).append("\n");
            reportText.append("Status: ").append(project.getStatus()).append("\n");
            reportText.append("Gerente: ").append(project.getProjectManager().getFullName()).append("\n");
            reportText.append("Equipe: ").append(project.getTeamOwner().getTeamName()).append("\n");
            reportText.append("Data de inicio: ").append(project.getStartDate()).append("\n");
            reportText.append("Data de termino prevista: ").append(project.getFinishDate()).append("\n");
            reportText.append("Situacao do prazo: ").append(getDeadlineStatus(project)).append("\n");
            reportText.append("Total de tarefas: ").append(taskReport.getTotalTasks()).append("\n");

            for (Map.Entry<StatusTasks, Integer> item : taskReport.getTasksByStatus().entrySet()) {
                reportText.append(item.getKey()).append(": ").append(item.getValue()).append("\n");
            }
        }

        Label report = new Label(reportText.toString());

        Button backButton = new Button("Voltar");
        backButton.setOnAction(event -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.show(stage, loggedUser);
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(title, report, backButton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 800);
        stage.setScene(scene);
        stage.setTitle("Relatorio");
        stage.show();
    }

    private String getDeadlineStatus(Project project) {
        if (project.getStatus() == StatusProjects.CONCLUIDO) {
            return "Concluido";
        }
        if (project.getStatus() == StatusProjects.CANCELADO) {
            return "Cancelado";
        }
        if (project.getFinishDate().isBefore(LocalDate.now())) {
            return "Atrasado";
        }
        return "No prazo";
    }
}
