package view;

import enums.StatusTasks;
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
import model.Task;
import model.User;
import service.ProjectService;
import service.TaskService;
import service.UserService;

import java.sql.SQLException;

public class TaskScreen {

    public void show(Stage stage, User loggedUser) throws SQLException {
        Label title = new Label("Tarefas");

        Button createButton = new Button("Criar tarefa");
        createButton.setOnAction(event -> {
            try {
                createTaskShow(stage, loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button editButton = new Button("Editar tarefa");
        editButton.setOnAction(event -> {
            try {
                editTaskShow(stage, loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button removeButton = new Button("Remover tarefa");
        removeButton.setOnAction(event -> {
            try {
                removeTaskShow(stage, loggedUser);
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
        stage.setTitle("Tarefas");
        stage.show();
    }

    public void createTaskShow(Stage stage, User loggedUser) throws SQLException {
        TaskService taskService = new TaskService();
        UserService userService = new UserService();
        ProjectService projectService = new ProjectService();

        Label title = new Label("Criar tarefa");

        TextField titleField = new TextField();
        titleField.setPromptText("Titulo da tarefa");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Descricao");

        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Data de inicio");

        DatePicker finishDatePicker = new DatePicker();
        finishDatePicker.setPromptText("Data de termino");

        ComboBox<StatusTasks> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(StatusTasks.values());
        statusComboBox.setPromptText("Status");

        ComboBox<User> userComboBox = new ComboBox<>();
        userComboBox.getItems().addAll(userService.getUsers());
        userComboBox.setPromptText("Responsavel");

        ComboBox<Project> projectComboBox = new ComboBox<>();
        projectComboBox.getItems().addAll(projectService.getProjects());
        projectComboBox.setPromptText("Projeto");

        Label feedback = new Label();

        Button saveButton = new Button("Salvar");
        saveButton.setOnAction(event -> {
            try {
                if (titleField.getText().isBlank()
                        || descriptionField.getText().isBlank()
                        || startDatePicker.getValue() == null
                        || finishDatePicker.getValue() == null
                        || statusComboBox.getValue() == null
                        || userComboBox.getValue() == null
                        || projectComboBox.getValue() == null) {
                    feedback.setText("Preencha todos os campos");
                    return;
                }

                taskService.createTask(
                        titleField.getText(),
                        descriptionField.getText(),
                        startDatePicker.getValue(),
                        finishDatePicker.getValue(),
                        statusComboBox.getValue(),
                        userComboBox.getValue(),
                        projectComboBox.getValue()
                );
                feedback.setText("Tarefa criada com sucesso");
            } catch (IllegalArgumentException ex) {
                feedback.setText(ex.getMessage());
            } catch (SQLException ex) {
                feedback.setText("Erro ao criar tarefa");
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
                new Label("Titulo:"),
                titleField,
                new Label("Descricao:"),
                descriptionField,
                new Label("Data de inicio:"),
                startDatePicker,
                new Label("Data de termino:"),
                finishDatePicker,
                new Label("Status:"),
                statusComboBox,
                new Label("Responsavel:"),
                userComboBox,
                new Label("Projeto:"),
                projectComboBox,
                feedback,
                saveButton,
                backButton
        );
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 800);
        stage.setScene(scene);
        stage.setTitle("Criar tarefa");
        stage.show();
    }

    public void editTaskShow(Stage stage, User loggedUser) throws SQLException {
        TaskService taskService = new TaskService();
        UserService userService = new UserService();

        Label title = new Label("Editar tarefa");

        ComboBox<Task> taskComboBox = new ComboBox<>();
        taskComboBox.getItems().addAll(taskService.getTasks());
        taskComboBox.setPromptText("Tarefa");

        TextField titleField = new TextField();
        titleField.setPromptText("Novo titulo");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Nova descricao");

        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Nova data de inicio");

        DatePicker finishDatePicker = new DatePicker();
        finishDatePicker.setPromptText("Nova data de termino");

        ComboBox<StatusTasks> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(StatusTasks.values());
        statusComboBox.setPromptText("Novo status");

        ComboBox<User> userComboBox = new ComboBox<>();
        userComboBox.getItems().addAll(userService.getUsers());
        userComboBox.setPromptText("Novo responsavel");

        Label feedback = new Label();

        Button saveButton = new Button("Salvar");
        saveButton.setOnAction(event -> {
            Task selectedTask = taskComboBox.getValue();
            if (selectedTask == null) {
                feedback.setText("Selecione uma tarefa");
                return;
            }

            try {
                StringBuilder result = new StringBuilder();

                if (!titleField.getText().isBlank()) {
                    result.append(taskService.changeTaskTitle(selectedTask, titleField.getText())).append("\n");
                }
                if (!descriptionField.getText().isBlank()) {
                    result.append(taskService.changeDescription(selectedTask, descriptionField.getText())).append("\n");
                }
                if (startDatePicker.getValue() != null) {
                    result.append(taskService.changeStartDate(selectedTask, startDatePicker.getValue())).append("\n");
                }
                if (finishDatePicker.getValue() != null) {
                    result.append(taskService.changeFinishDate(selectedTask, finishDatePicker.getValue())).append("\n");
                }
                if (statusComboBox.getValue() != null) {
                    result.append(taskService.changeStatus(selectedTask, statusComboBox.getValue())).append("\n");
                }
                if (userComboBox.getValue() != null) {
                    result.append(taskService.changeAssignedUser(selectedTask, userComboBox.getValue())).append("\n");
                }

                if (result.length() == 0) {
                    feedback.setText("Nenhuma informacao informada");
                } else {
                    feedback.setText(result.toString());
                }
            } catch (SQLException ex) {
                feedback.setText("Erro ao editar tarefa");
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
                new Label("Tarefa:"),
                taskComboBox,
                new Label("Preencha apenas o que deseja alterar:"),
                new Label("Titulo:"),
                titleField,
                new Label("Descricao:"),
                descriptionField,
                new Label("Data de inicio:"),
                startDatePicker,
                new Label("Data de termino:"),
                finishDatePicker,
                new Label("Status:"),
                statusComboBox,
                new Label("Responsavel:"),
                userComboBox,
                feedback,
                buttons
        );
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 800);
        stage.setScene(scene);
        stage.setTitle("Editar tarefa");
        stage.show();
    }

    public void removeTaskShow(Stage stage, User loggedUser) throws SQLException {
        TaskService taskService = new TaskService();

        Label title = new Label("Remover tarefa");

        ComboBox<Task> taskComboBox = new ComboBox<>();
        taskComboBox.getItems().addAll(taskService.getTasks());
        taskComboBox.setPromptText("Tarefa");

        Label feedback = new Label();

        Button removeButton = new Button("Remover");
        removeButton.setOnAction(event -> {
            try {
                Task selectedTask = taskComboBox.getValue();
                feedback.setText(taskService.removeTask(selectedTask));
                taskComboBox.getItems().remove(selectedTask);
                taskComboBox.setValue(null);
            } catch (SQLException ex) {
                feedback.setText("Erro ao remover tarefa");
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
        root.getChildren().addAll(title, taskComboBox, feedback, removeButton, backButton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 800);
        stage.setScene(scene);
        stage.setTitle("Remover tarefa");
        stage.show();
    }
}
