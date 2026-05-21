package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
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

    public LoginScreen() throws SQLException {
    }

    @Override
    public void start(Stage stage) throws Exception {

        Label title = new Label("Login");

        TextField emailField = new TextField();
        emailField.setPromptText("Digite seu email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Digite sua senha");

        Button loginButton = new Button("Entrar");
        Label feedback = new Label();

        loginButton.setOnAction(e -> {

            String email = emailField.getText();
            String senha = passwordField.getText();

            User loggedUser = userService.login(email,senha);
            if (loggedUser != null){
                feedback.setText("Usuário logado");
            } else {
                feedback.setText("usuário nao encontrado");
            }
        });

        VBox root = new VBox(10);

        root.getChildren().addAll(title, emailField, passwordField, loginButton,feedback);

        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 300);

        stage.setScene(scene);
        stage.setTitle("Sistema");
        stage.show();


    }
    public static void main(String[] args) {
        launch();
    }
}
