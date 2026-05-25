package view;

import enums.ProfileType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Team;
import model.User;
import repository.UserRepository;
import service.TeamService;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TeamScreen {

    public void show(Stage stage, User loggedUser) throws SQLException {
        Label title = new Label("Times");

        Button createButton = new Button("Criar time");
        createButton.setOnAction(event -> {
            try {
                createTeamShow(stage, loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button removeButton = new Button("Remover time");
        removeButton.setOnAction(event -> {
            try {
                removeTeamShow(stage, loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button editButton = new Button("Editar time");
        editButton.setOnAction(event -> {
            try {
                editTeamShow(stage, loggedUser);
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
        stage.setTitle("Times");
        stage.show();
    }

    public void createTeamShow(Stage stage,User loggedUser) throws SQLException {

        UserRepository userRepository = new UserRepository();
        TeamService teamService = new TeamService();
        Label title = new Label ("Criar time");

        Label teamNameLabel = new Label("Nome do time:");
        TextField teamNameField = new TextField();

        Label discriptionLabel = new Label("Descrição:");
        TextField discriptionField = new TextField();
        discriptionField.setPromptText("Ex: Time responsável por análises.");

        Label teamOwnerLabel = new Label("Responsável pelo time:");
        ComboBox<User> userComboBox =  new ComboBox<>();
        List<User> users = userRepository.loadUsers();
        userComboBox.getItems().addAll(users);

        Label feedback = new Label();

        Button saveButton = new Button("Salvar");
        saveButton.setOnAction(event ->{
            try {
                User selectedUser = userComboBox.getValue();

                Team team = new Team(
                        teamNameField.getText(),
                        discriptionField.getText(),
                        selectedUser,
                        LocalDate.now()
                        );

                teamService.createTeam(team);
                feedback.setText("Time criado com sucesso");
            } catch (SQLException ex) {
                feedback.setText("Erro ao criar o time");
                throw new RuntimeException(ex);

            }
            VBox createLayout = new VBox(10);
            createLayout.getChildren().add(feedback);
        });

        Button backButton = new Button("Voltar");
        backButton.setOnAction(actionEvent -> {
            try {
                show(stage, loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });


        VBox createLayout = new VBox(10);

        createLayout.getChildren().addAll(
                title,
                teamNameLabel,
                teamNameField,
                discriptionLabel,
                discriptionField,
                teamOwnerLabel,
                userComboBox,
                saveButton,
                backButton,
                feedback
        );

        Scene createScene = new Scene(createLayout, 500, 800);

        stage.setScene(createScene);
    }

    public void removeTeamShow(Stage stage, User loggedUser) throws SQLException {
        TeamService teamService = new TeamService();

        Label title = new Label("Remover time");

        ComboBox<Team> teamComboBox = new ComboBox<>();
        teamComboBox.getItems().addAll(teamService.getTeams());
        teamComboBox.setPromptText("Time");

        Label feedback = new Label();

        Button removeButton = new Button("Remover");
        removeButton.setOnAction(event -> {
            try {
                Team selectedTeam = teamComboBox.getValue();
                feedback.setText(teamService.removeTeam(selectedTeam));
                teamComboBox.getItems().remove(selectedTeam);
                teamComboBox.setValue(null);
            } catch (SQLException ex) {
                feedback.setText("Erro ao remover time");
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
        root.getChildren().addAll(title, teamComboBox, feedback, removeButton, backButton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 800);
        stage.setScene(scene);
        stage.setTitle("Remover time");
        stage.show();
    }

    public void editTeamShow(Stage stage, User loggedUser) throws SQLException {
        TeamService teamService = new TeamService();
        UserRepository userRepository = new UserRepository();

        Label title = new Label("Editar time");

        ComboBox<Team> teamComboBox = new ComboBox<>();
        teamComboBox.getItems().addAll(teamService.getTeams());
        teamComboBox.setPromptText("Time");

        TextField teamNameField = new TextField();
        teamNameField.setPromptText("Novo nome");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Nova descricao");

        ComboBox<User> ownerComboBox = new ComboBox<>();
        ownerComboBox.getItems().addAll(userRepository.loadUsers());
        ownerComboBox.setPromptText("Novo responsavel");

        Label feedback = new Label();

        Button saveButton = new Button("Salvar");
        saveButton.setOnAction(event -> {
            Team selectedTeam = teamComboBox.getValue();

            if (selectedTeam == null) {
                feedback.setText("Selecione um time");
                return;
            }

            try {
                StringBuilder result = new StringBuilder();

                if (!teamNameField.getText().isBlank()) {
                    result.append(teamService.updateTeamName(selectedTeam, teamNameField.getText())).append("\n");
                }
                if (!descriptionField.getText().isBlank()) {
                    result.append(teamService.updateTeamDescription(selectedTeam, descriptionField.getText())).append("\n");
                }
                if (ownerComboBox.getValue() != null) {
                    result.append(teamService.changeTeamOwner(loggedUser, selectedTeam, ownerComboBox.getValue())).append("\n");
                }

                if (result.length() == 0) {
                    feedback.setText("Nenhuma informacao informada");
                } else {
                    feedback.setText(result.toString());
                }
            } catch (SQLException ex) {
                feedback.setText("Erro ao editar time");
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
                new Label("Time:"),
                teamComboBox,
                new Label("Preencha apenas o que deseja alterar:"),
                new Label("Nome:"),
                teamNameField,
                new Label("Descricao:"),
                descriptionField,
                new Label("Responsavel:"),
                ownerComboBox,
                feedback,
                saveButton,
                backButton
        );
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 800);
        stage.setScene(scene);
        stage.setTitle("Editar time");
        stage.show();
    }


 }

