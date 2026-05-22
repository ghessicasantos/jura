package view;

import enums.StatusProjects;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Project;
import model.Team;
import model.User;
import service.ProjectService;
import service.TeamService;
import service.UserService;

import java.sql.SQLException;

public class ProjectScreen {

    public void show(Stage stage, User loggedUser) throws SQLException {
        Label title = new Label("Projetos");

        Button createButton = new Button("Criar projeto");
        createButton.setOnAction(event -> {
            try {
                createProjectShow(stage, loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button editButton = new Button("Editar projeto");
        editButton.setOnAction(event -> {
            try {
                editProjectShow(stage, loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button removeButton = new Button("Remover projeto");
        removeButton.setOnAction(event -> {
            try {
                removeProjectShow(stage, loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button backButton = new Button("Voltar");
        backButton.setOnAction(event -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.show(stage, loggedUser);
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(title, createButton, editButton, removeButton, backButton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 800);
        stage.setScene(scene);
        stage.setTitle("Projetos");
        stage.show();
    }

    public void createProjectShow(Stage stage, User loggedUser) throws SQLException {
        ProjectService projectService = new ProjectService();
        UserService userService = new UserService();
        TeamService teamService = new TeamService();

        Label title = new Label("Criar projeto");

        TextField projectNameField = new TextField();
        projectNameField.setPromptText("Nome do projeto");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Descricao");

        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Data de inicio");

        DatePicker finishDatePicker = new DatePicker();
        finishDatePicker.setPromptText("Data de termino");

        ComboBox<StatusProjects> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(StatusProjects.values());
        statusComboBox.setPromptText("Status");

        ComboBox<User> managerComboBox = new ComboBox<>();
        managerComboBox.getItems().addAll(userService.getUsers());
        managerComboBox.setPromptText("Responsavel");

        ComboBox<Team> teamComboBox = new ComboBox<>();
        teamComboBox.getItems().addAll(teamService.getTeams());
        teamComboBox.setPromptText("Time");

        Label feedback = new Label();

        Button saveButton = new Button("Salvar");
        saveButton.setOnAction(event -> {
            try {
                if (projectNameField.getText().isBlank()
                        || descriptionField.getText().isBlank()
                        || startDatePicker.getValue() == null
                        || finishDatePicker.getValue() == null
                        || statusComboBox.getValue() == null
                        || managerComboBox.getValue() == null
                        || teamComboBox.getValue() == null) {
                    feedback.setText("Preencha todos os campos");
                    return;
                }

                projectService.createProject(
                        projectNameField.getText(),
                        descriptionField.getText(),
                        startDatePicker.getValue(),
                        finishDatePicker.getValue(),
                        statusComboBox.getValue(),
                        managerComboBox.getValue(),
                        teamComboBox.getValue()
                );
                feedback.setText("Projeto criado com sucesso");
            } catch (IllegalArgumentException ex) {
                feedback.setText(ex.getMessage());
            } catch (SQLException ex) {
                feedback.setText("Erro ao criar projeto");
                throw new RuntimeException(ex);
            }
        });

        Button backButton = new Button("Voltar");
        backButton.setOnAction(event -> {
            try {
                show(stage, loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(
                title,
                new Label("Nome:"),
                projectNameField,
                new Label("Descricao:"),
                descriptionField,
                new Label("Data de inicio:"),
                startDatePicker,
                new Label("Data de termino:"),
                finishDatePicker,
                new Label("Status:"),
                statusComboBox,
                new Label("Responsavel:"),
                managerComboBox,
                new Label("Time:"),
                teamComboBox,
                feedback,
                saveButton,
                backButton
        );
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 800);
        stage.setScene(scene);
        stage.setTitle("Criar projeto");
        stage.show();
    }

    public void editProjectShow(Stage stage, User loggedUser) throws SQLException {
        ProjectService projectService = new ProjectService();
        UserService userService = new UserService();
        TeamService teamService = new TeamService();

        Label title = new Label("Editar projeto");

        ComboBox<Project> projectComboBox = new ComboBox<>();
        projectComboBox.getItems().addAll(projectService.getProjects());
        projectComboBox.setPromptText("Projeto");

        TextField projectNameField = new TextField();
        projectNameField.setPromptText("Novo nome");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Nova descricao");

        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Nova data de inicio");

        DatePicker finishDatePicker = new DatePicker();
        finishDatePicker.setPromptText("Nova data de termino");

        ComboBox<StatusProjects> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(StatusProjects.values());
        statusComboBox.setPromptText("Novo status");

        ComboBox<User> managerComboBox = new ComboBox<>();
        managerComboBox.getItems().addAll(userService.getUsers());
        managerComboBox.setPromptText("Novo responsavel");

        ComboBox<Team> teamComboBox = new ComboBox<>();
        teamComboBox.getItems().addAll(teamService.getTeams());
        teamComboBox.setPromptText("Novo time");

        Label feedback = new Label();

        Button saveButton = new Button("Salvar");
        saveButton.setOnAction(event -> {
            Project selectedProject = projectComboBox.getValue();
            if (selectedProject == null) {
                feedback.setText("Selecione um projeto");
                return;
            }

            try {
                StringBuilder result = new StringBuilder();

                if (!projectNameField.getText().isBlank()) {
                    result.append(projectService.changeProjectName(loggedUser, selectedProject, projectNameField.getText())).append("\n");
                }
                if (!descriptionField.getText().isBlank()) {
                    result.append(projectService.changeDescription(loggedUser, selectedProject, descriptionField.getText())).append("\n");
                }
                if (startDatePicker.getValue() != null) {
                    result.append(projectService.changeStartDate(loggedUser, selectedProject, startDatePicker.getValue())).append("\n");
                }
                if (finishDatePicker.getValue() != null) {
                    result.append(projectService.changeFinishDate(loggedUser, selectedProject, finishDatePicker.getValue())).append("\n");
                }
                if (statusComboBox.getValue() != null) {
                    result.append(projectService.changeStatus(loggedUser, selectedProject, statusComboBox.getValue())).append("\n");
                }
                if (managerComboBox.getValue() != null) {
                    result.append(projectService.changeProjectManager(loggedUser, selectedProject, managerComboBox.getValue())).append("\n");
                }
                if (teamComboBox.getValue() != null) {
                    result.append(projectService.changeProjectTeam(loggedUser, selectedProject, teamComboBox.getValue())).append("\n");
                }

                if (result.length() == 0) {
                    feedback.setText("Nenhuma informacao informada");
                } else {
                    feedback.setText(result.toString());
                }
            } catch (SQLException ex) {
                feedback.setText("Erro ao editar projeto");
                throw new RuntimeException(ex);
            }
        });

        Button backButton = new Button("Voltar");
        backButton.setOnAction(event -> {
            try {
                show(stage, loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(saveButton, backButton);
        buttons.setAlignment(Pos.CENTER);

        VBox root = new VBox(10);
        root.getChildren().addAll(
                title,
                new Label("Projeto:"),
                projectComboBox,
                new Label("Preencha apenas o que deseja alterar:"),
                new Label("Nome:"),
                projectNameField,
                new Label("Descricao:"),
                descriptionField,
                new Label("Data de inicio:"),
                startDatePicker,
                new Label("Data de termino:"),
                finishDatePicker,
                new Label("Status:"),
                statusComboBox,
                new Label("Responsavel:"),
                managerComboBox,
                new Label("Time:"),
                teamComboBox,
                feedback,
                buttons
        );
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 800);
        stage.setScene(scene);
        stage.setTitle("Editar projeto");
        stage.show();
    }

    public void removeProjectShow(Stage stage, User loggedUser) throws SQLException {
        ProjectService projectService = new ProjectService();

        Label title = new Label("Remover projeto");

        ComboBox<Project> projectComboBox = new ComboBox<>();
        projectComboBox.getItems().addAll(projectService.getProjects());
        projectComboBox.setPromptText("Projeto");

        Label feedback = new Label();

        Button removeButton = new Button("Remover");
        removeButton.setOnAction(event -> {
            try {
                Project selectedProject = projectComboBox.getValue();
                feedback.setText(projectService.removeProject(selectedProject));
                projectComboBox.getItems().remove(selectedProject);
                projectComboBox.setValue(null);
            } catch (SQLException ex) {
                feedback.setText("Erro ao remover projeto");
                throw new RuntimeException(ex);
            }
        });

        Button backButton = new Button("Voltar");
        backButton.setOnAction(event -> {
            try {
                show(stage, loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(title, projectComboBox, feedback, removeButton, backButton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 800);
        stage.setScene(scene);
        stage.setTitle("Remover projeto");
        stage.show();
    }
}
