package view;

import enums.ProfileType;
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
            MainMenu mainMenu = new MainMenu();
            mainMenu.show(stage,loggedUser);
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
                backButton
        );

        Scene createScene = new Scene(createLayout, 500, 800);

        stage.setScene(createScene);
    }


 }

