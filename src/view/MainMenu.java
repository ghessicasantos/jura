package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

import java.sql.SQLException;

public class MainMenu {

    public void show(Stage stage, User loggedUser){
        Label title = new Label("Menu principal");

        Button userMenu = new Button("Editar Usuário");
        Button teamMenu = new Button("Time");
        Button projectMenu = new Button("Projeto");
        Button tasksMenu = new Button("Tarefas");
        Button teamMemberMenu = new Button("Membros do time");
        Button reportMenu = new Button("Relatório");

        userMenu.setOnAction(e ->{
            UserScreen userScreen = new UserScreen();
            try {
                userScreen.editUserShow(stage,loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        teamMenu.setOnAction(e -> {
            TeamScreen teamScreen = new TeamScreen();
            try {
                teamScreen.show(stage, loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        projectMenu.setOnAction(e -> {
            ProjectScreen projectScreen = new ProjectScreen();
            try {
                projectScreen.show(stage, loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        tasksMenu.setOnAction(e -> {
            TaskScreen taskScreen = new TaskScreen();
            try {
                taskScreen.show(stage, loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        teamMemberMenu.setOnAction(e -> {
            TeamMemberScreen teamMemberScreen = new TeamMemberScreen();
            try {
                teamMemberScreen.show(stage, loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        reportMenu.setOnAction(e -> {
            ReportScreen reportScreen = new ReportScreen();
            try {
                reportScreen.show(stage, loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(title,userMenu,teamMenu,projectMenu,tasksMenu,teamMemberMenu,reportMenu);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root,500,800);

        stage.setScene(scene);
        stage.setTitle("sistema");
        stage.show();
    }
}
