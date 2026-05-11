package controller;

import java.util.Scanner;

public class Controller {

    private Scanner scan = new Scanner(System.in);
    private UserController userController;

    public void start() {
    this.userController = new UserController();
        while (true) {
            System.out.println("Seja bem-vindo");

            System.out.println("O que deseja fazer?");

            System.out.println("Seja bem-vindo");

            System.out.println("1 - Criar usuário");

            System.out.println("2 - Editar usuário");

            int option = Integer.parseInt(scan.nextLine());

            if (option == 1) {
                userController.createUserMenu();
            } else if (option == 2) {
                userController.editUserMenu();
            } else {
                break;
            }
        }
    }
}