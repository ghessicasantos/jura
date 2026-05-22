package service;

import enums.ProfileType;
import model.User;
import repository.UserRepository;
import java.util.List;
import java.sql.SQLException;
import java.util.Scanner;


public class UserService {



    private UserRepository userRepository;

    public UserService() throws SQLException {
        this.userRepository = new UserRepository();
        this.users = userRepository.loadUsers();
    }

    private List<User> users;

    public void addUser(User user) {
        users.add(user);
    }

    public String createUser(User user) throws SQLException {
        addUser(user);

        userRepository.saveUser(user);

        return "Usuário criado.";
    }

    private void listUsers() {
        for (User user : users) {
            System.out.println(user);
        }
    }

    public List<User> getUsers(){
        return users;
    }

    private User findUserByLogin(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public String removeUser(String login) throws SQLException {
        User userToRemove = findUserByLogin(login);

        if (userToRemove == null) {
            return "Usuario nao encontrado.";
        }

        userRepository.saveUserHistory(userToRemove);
        userRepository.deleteUser(userToRemove);
        users.remove(userToRemove);

        return "Usuario removido.";
    }

    public void updateUser(User loggedUser) throws SQLException {
            System.out.println("Chamando update");
            userRepository.saveUserHistory(loggedUser);
            userRepository.updateUser(loggedUser);
            System.out.println("Terminou update");

    }



    public String recoverPassword(String email, String cpf, String newPassword) throws SQLException {
        User user = findUserByEmail(email);

        if (user == null) {
            return "Usuario nao encontrado.";
        }
        if (!user.getCpf().equals(cpf)) {
            return "CPF invalido para esse usuario.";
        }
        if (newPassword == null || newPassword.isBlank()) {
            return "A nova senha nao pode ser vazia.";
        }

        userRepository.saveUserHistory(user);
        user.setPassword(newPassword);
        userRepository.updateUserPassword(user);

        return "Senha atualizada. Realize o login novamente.";
    }

    public User findUserByEmail(String email){
        for(User user : users){
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    public User login(String email, String password){
        User user = findUserByEmail(email);

        if(user == null){
            return null;
        }
        if (!user.getPassword().equals(password)){
            return null;
        }
        return user;
    }

}
