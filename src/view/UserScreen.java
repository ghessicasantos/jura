package view;

import enums.ProfileType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import model.User;
import service.UserService;

import java.sql.SQLException;

public class UserScreen {

    public void editUserShow(Stage stage,User loggedUser) throws SQLException {

        UserService userService = new UserService();

        Label title = new Label ("Editar Perfil");
        Label editUserTitle = new Label("Editar Perfil");

        Label editUserInstructions = new Label("Qual informação do seu usuário deseja editar?");

        Label fullName = new Label("Nome: " + loggedUser.getFullName());
        TextField newFullNameField = new TextField();
        newFullNameField.setPromptText("Digite o novo nome");

        Label email = new Label("Email: " + loggedUser.getEmail());
        TextField newEmailField = new TextField();
        newEmailField.setPromptText("Digite o novo email");

        Label cargo = new Label("Cargo: "+ loggedUser.getCargo());
        TextField newCargo = new TextField();
        newCargo.setPromptText("Digite o novo cargo");

        Label profileName = new Label("Nome do perfil: " + loggedUser.getProfileName());
        TextField newProfileName = new TextField();
        newProfileName.setPromptText("Digite o novo nome do perfil");

        Label profileType = new Label("Tipo do perfil: " + loggedUser.getProfileType().toString());
        ComboBox<ProfileType> profileBox = new ComboBox<>();
        profileBox.getItems().addAll(ProfileType.values());
        profileBox.setPromptText("Tipo de Perfil");

        Label feedback = new Label();
        Button saveButton = new Button("Salvar");
        saveButton.setOnAction(actionEvent1 ->{
            if(!newFullNameField.getText().isBlank()){
                loggedUser.setFullName(newFullNameField.getText());
            }
            if(!newEmailField.getText().isBlank()){
                loggedUser.setEmail(newEmailField.getText());
            }
            if(!newCargo.getText().isBlank()){
                loggedUser.setCargo(newCargo.getText());
            }
            if(!newProfileName.getText().isBlank()){
                loggedUser.setProfileName(newProfileName.getText());
            }
            if(profileBox.getValue() != null){
                loggedUser.setProfileType(profileBox.getValue());
            }

            try {System.out.println(loggedUser.getEmail());
                userService.updateUser(loggedUser);
                feedback.setText("Usuário atualizado");

            } catch (SQLException e) {
                feedback.setText("Erro ao concluir a operação");
                throw new RuntimeException(e);
                    }




        });
        Button back = new Button("Voltar");
        back.setOnAction(actionEvent -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.show(stage,loggedUser);
        });

        Button removeButton = new Button("Remover usuario");
        removeButton.setOnAction(actionEvent -> {
            try {
                feedback.setText(userService.removeUser(loggedUser.getLogin()));
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.start(stage);
            } catch (Exception e) {
                feedback.setText("Erro ao remover usuario");
                throw new RuntimeException(e);
            }
        });

        VBox rootv = new VBox(15);
        HBox rooth = new HBox(15);
        rootv.getChildren().addAll(
                title,
                fullName,
                newFullNameField,
                email,
                newEmailField,
                cargo,
                newCargo,
                profileName,
                newProfileName,
                profileType,
                profileBox,
                feedback);
        rooth.getChildren().addAll(saveButton,removeButton,back);
        rootv.setAlignment(Pos.CENTER);
        rootv.getChildren().add(rooth);
        rooth.setAlignment(Pos.CENTER);
        Scene scene = new Scene(rootv,500,800);

        stage.setScene(scene);
        stage.setTitle("Usuários");
        stage.show();


    }
    public void showCreateUserScreen(Stage stage) throws SQLException {
        UserService userService = new UserService();

            Label createUserTitle = new Label("Novo usuário");

            Label fullNameLabel = new Label("Nome completo:");
            TextField fullNameField = new TextField();
            fullNameField.setPromptText("Nome completo");

            Label cpfLabel = new Label("CPF:");
            TextField cpfField = new TextField();
            cpfField.setPromptText("CPF");

            Label emailLabel = new Label("Email:");
            TextField emailField = new TextField();
            emailField.setPromptText("Email");

            Label cargoLabel = new Label("Cargo que ocupa");
            TextField cargoField = new TextField();
            cargoField.setPromptText("Cargo que ocupa");

            Label loginLabel = new Label("Login:");
            TextField loginField = new TextField();
            loginField.setPromptText("login");

            Label passwordLabel = new Label("Senha de acesso");
            TextField passwordField = new TextField();
            passwordField.setPromptText("Senha");

            Label profileNameLabel = new Label("Nome do Perfil:");
            TextField profileNameField = new TextField();
            profileNameField.setPromptText("Nome do pefil");

            Label profileTypeLabel = new Label("Tipo de perfil");
            ComboBox<ProfileType> profileBox = new ComboBox<>();
            profileBox.getItems().addAll(ProfileType.values());
            profileBox.setPromptText("Tipo");

            Label feedback = new Label();

            Button saveButton = new Button("Salvar");
            saveButton.setOnAction(event ->{
                try {
                    ProfileType profileTypeField = profileBox.getValue();

                    User user = new User(
                            fullNameField.getText(),
                            cpfField.getText(),
                            emailField.getText(),
                            cargoField.getText(),
                            loginField.getText(),
                            passwordField.getText(),
                            profileNameField.getText(),
                            profileTypeField);

                    userService.createUser(user);
                    feedback.setText("Usuário criado com sucesso");
                } catch (SQLException ex) {
                    feedback.setText("Erro ao criar usuário");
                    throw new RuntimeException(ex);

                }
                VBox createLayout = new VBox(10);
                createLayout.getChildren().add(feedback);
            });

            Button backButton = new Button("Voltar");
            backButton.setOnAction(e -> {

                LoginScreen loginScreen = null;
                try {
                    loginScreen = new LoginScreen();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    loginScreen.start(stage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            });

            VBox createLayout = new VBox(10);

            createLayout.getChildren().addAll(
                    createUserTitle,
                    fullNameLabel,
                    fullNameField,
                    cpfLabel,
                    cpfField,
                    emailLabel,
                    emailField,
                    cargoLabel,
                    cargoField,
                    loginLabel,
                    loginField,
                    passwordLabel,
                    passwordField,
                    profileNameLabel,
                    profileNameField,
                    profileTypeLabel,
                    profileBox,
                    saveButton,
                    backButton,
                    feedback
            );

            Scene createScene = new Scene(createLayout, 500, 800);

            stage.setScene(createScene);
    }
}
