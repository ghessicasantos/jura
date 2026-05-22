package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.awt.*;
import java.sql.SQLException;

import javafx.scene.control.Label;
import model.User;
import service.UserService;



public class LoginScreen extends Application {

    UserService userService = new UserService();
    MainMenu mainMenu = new MainMenu();

    public LoginScreen() throws SQLException {
    }

    @Override
    public void start(Stage stage) throws Exception {
        Label feedback = new Label();
        Label title = new Label("Login");

        TextField emailField = new TextField();
        emailField.setPromptText("Digite seu email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Digite sua senha");
        Button loginButton = new Button("Entrar");
        loginButton.setOnAction(e -> {

            String email = emailField.getText();
            String senha = passwordField.getText();

            User loggedUser = userService.login(email,senha);
            if (loggedUser != null){
                mainMenu.show(stage,loggedUser);
            } else {
                feedback.setText("Usuário nao encontrado");
            }
        });
        Button createUserButton = new Button("Criar usuário");
        createUserButton.setOnAction(e -> {

            UserScreen userScreen = new UserScreen();
            try {
                userScreen.showCreateUserScreen(stage);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });


        VBox rootv = new VBox(10);
        HBox rooth = new HBox(10);
        rootv.getChildren().addAll(title,emailField,passwordField,feedback);

        rooth.getChildren().addAll(loginButton,createUserButton);
        rootv.getChildren().add(rooth);

        rootv.setAlignment(Pos.CENTER);
        rooth.setAlignment(Pos.CENTER);

        Scene scene = new Scene(rootv, 500, 800);

        stage.setScene(scene);
        stage.setTitle("Sistema");
        stage.show();


    }
    public static void main(String[] args) {
        launch();
    }
}
