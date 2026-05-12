package controller;

import model.User;
import service.UserService;

import java.io.IOException;
import java.util.Scanner;

public class Controller {

    private Scanner scan = new Scanner(System.in);

    private UserController userController;

    private UserService userService;

    public Controller() throws IOException{
        this.userService = new UserService();
        this.userController = new UserController(userService);
    }

    public void start() throws IOException{

        User loggedUser = null;

        System.out.println("Seja bem-vindo");
        System.out.println("Realize o login");
        while (loggedUser == null) {
        System.out.println("Digite seu Email:");

        String email = scan.nextLine();

        System.out.println("Digite sua senha:");
        String password = scan.nextLine();
        loggedUser = userService.login(email, password);

        if(loggedUser == null){
                System.out.println("Usuário ou senha inválidos");
            }
        }
        while (true) {

            System.out.println("O que deseja fazer?");

            System.out.println("1 - Criar usuário");

            System.out.println("2 - Editar usuário");

            int option = Integer.parseInt(scan.nextLine());

            if (option == 1) {
                userController.createUserMenu();
            } else if (option == 2) {
                userController.editUserMenu(loggedUser);
            } else {
                break;
            }
        }
    }
}