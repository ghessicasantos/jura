package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Team;
import model.TeamMember;
import model.User;
import repository.TeamRepository;
import repository.UserRepository;
import service.TeamMemberService;
import service.TeamService;
import service.UserService;

import java.sql.SQLException;
import java.util.List;

public class TeamMemberScreen {

    public void show(Stage stage, User loggedUser) throws SQLException {
        UserRepository userRepository = new UserRepository();
        TeamRepository teamRepository = new TeamRepository(userRepository);
        TeamService teamService = new TeamService();
        UserService userService = new UserService();
        TeamMemberService teamMemberService = new TeamMemberService(userRepository, teamRepository);

        Label title = new Label("Membros do time");

        ComboBox<Team> teamComboBox = new ComboBox<>();
        teamComboBox.getItems().addAll(teamService.getTeams());
        teamComboBox.setPromptText("Time");

        ComboBox<User> userComboBox = new ComboBox<>();
        userComboBox.getItems().addAll(userService.getUsers());
        userComboBox.setPromptText("Usuario");

        Label feedback = new Label();

        Button listButton = new Button("Listar membros");
        listButton.setOnAction(event -> {
            Team selectedTeam = teamComboBox.getValue();
            if (selectedTeam == null) {
                feedback.setText("Selecione um time");
                return;
            }

            List<TeamMember> members = teamMemberService.listActiveMembersByTeam(selectedTeam);
            if (members.isEmpty()) {
                feedback.setText("Esse time ainda nao possui membros ativos");
                return;
            }

            StringBuilder result = new StringBuilder();
            for (TeamMember member : members) {
                result.append(member.getUser().getFullName())
                        .append(" | ")
                        .append(member.getUser().getEmail())
                        .append("\n");
            }
            feedback.setText(result.toString());
        });

        Button addButton = new Button("Adicionar");
        addButton.setOnAction(event -> {
            try {
                String result = teamMemberService.addMember(loggedUser, teamComboBox.getValue(), userComboBox.getValue());
                feedback.setText(result);
            } catch (SQLException ex) {
                feedback.setText("Erro ao adicionar membro");
                throw new RuntimeException(ex);
            }
        });

        Button removeButton = new Button("Remover");
        removeButton.setOnAction(event -> {
            try {
                String result = teamMemberService.removeMember(loggedUser, teamComboBox.getValue(), userComboBox.getValue());
                feedback.setText(result);
            } catch (SQLException ex) {
                feedback.setText("Erro ao remover membro");
                throw new RuntimeException(ex);
            }
        });

        Button backButton = new Button("Voltar");
        backButton.setOnAction(event -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.show(stage, loggedUser);
        });

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(listButton, addButton, removeButton);
        buttons.setAlignment(Pos.CENTER);

        VBox root = new VBox(10);
        root.getChildren().addAll(
                title,
                new Label("Time:"),
                teamComboBox,
                new Label("Usuario:"),
                userComboBox,
                buttons,
                feedback,
                backButton
        );
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 800);
        stage.setScene(scene);
        stage.setTitle("Membros do time");
        stage.show();
    }
}
