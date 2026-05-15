package controller;

import enums.ProfileType;
import model.User;
import service.UserService;

import java.io.IOException;
import java.util.Scanner;

public class UserController {

    private Scanner scan;
    private UserService userService;

    public UserController(UserService userService, Scanner scan) throws IOException {
        this.userService = userService;
        this.scan = scan;
    }

    public void userMenuActions(User user) throws IOException {
        while (true) {

            System.out.println("O que deseja fazer?");

            System.out.println("1 - Criar usuário");

            System.out.println("2 - Editar usuário");

            System.out.println("3 - Voltar");

            int option = Integer.parseInt(scan.nextLine());

            if (option == 1) {
                createUserMenu();
            } else if (option == 2) {
                editUserMenu(user);
            } else {
                break;
            }
        }
    }

    public void createUserMenu() throws IOException {

        System.out.println("Digite o nome completo:");
        String name = scan.nextLine();

        System.out.println("CPF:");
        String cpf = scan.nextLine();

        System.out.println("email:");
        String email = scan.nextLine();

        System.out.println("Cargo que ocupa:");
        String cargo = scan.nextLine();

        System.out.println("Login:");
        String login = scan.nextLine();

        System.out.println("Senha:");
        String password = scan.nextLine();

        System.out.println("Nome do perfil:");
        String profileName = scan.nextLine();


        System.out.println("Escolha o perfil:");
        for(ProfileType profile : ProfileType.values()){
            System.out.println("-" + profile);
        }
        String profileInput = scan.nextLine();

        ProfileType profileType = ProfileType.valueOf(profileInput.toUpperCase());

        User user = new User(name,cpf,email,cargo,login,password,profileName,profileType);

        userService.createUser(user);

        System.out.println("Usuário criado: " + user.getProfileName());

    }

    public void editUserMenu(User loggedUser){

        while (true){
           System.out.println("Qual informação deseja atualizar?");
           System.out.println("1 - Nome Completo");
           System.out.println("2 - Email");
           System.out.println("3 - Cargo");
           System.out.println("4 - Nome do Perfil");
           System.out.println("5 - Tipo de perfil");
           System.out.println("6 - Sair");

           int option = Integer.parseInt(scan.nextLine());

           if(option == 1){
               System.out.println("Digite o nome completo:");
               String newFullName = scan.nextLine();
               String nameupdated =  userService.updateUserFullName(loggedUser,newFullName);
               System.out.println(nameupdated);

           }
           else if(option == 2){
               System.out.println("Digite o novo email:");
               String newEmail = scan.nextLine();
               String emailupdated = userService.updateUserEmail(loggedUser,newEmail);
               System.out.println(emailupdated);
           } else if (option == 3) {
               System.out.println("Digite o novo cargo:");
               String newCargo = scan.nextLine();
               String cargoupdated = userService.updateUserCargo(loggedUser,newCargo);
               System.out.println(cargoupdated);
           } else if (option == 4) {
               System.out.println("Digite o novo nome de perfil:");
               String newProfilename = scan.nextLine();
               String profileNameUpdated = userService.updateUserProfileName(loggedUser,newProfilename);
               System.out.println(profileNameUpdated);
           } else if (option == 5) {
               System.out.println("Escolha o novo tipo de perfil:");
               for(ProfileType profile : ProfileType.values()){
                   System.out.println("-" + profile);
               }
               String profileInput = scan.nextLine();
               String profiletypeUpdated = userService.updateUserProfileType(loggedUser,profileInput);
               System.out.println(profiletypeUpdated);
           } else if (option == 6) {
               break;
           }
        }
    }
}
