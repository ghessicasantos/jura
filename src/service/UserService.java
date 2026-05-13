package service;

import enums.ProfileType;
import model.User;
import org.jetbrains.annotations.NotNull;
import repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserService {



    private UserRepository userRepository;

    public UserService() throws IOException {
        this.userRepository = new UserRepository();
        this.users = userRepository.loadUsers();
    }

    private List<User> users;

    public void addUser(User user) {
        users.add(user);
    }

    public String createUser(User user) throws IOException {
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

    public void removeUser(String login) {
        User userToRemove = null;

        for (User user : users) {
            if (user.getLogin().equals(login)) {
                userToRemove = user;
            }
        }
        users.remove(userToRemove);
    }

    public String updateUserProfileType(@NotNull User loggedUser, String newProfileType) {
        if (loggedUser.canEditUser()) {
            ProfileType profileType = ProfileType.valueOf(newProfileType);
            loggedUser.setProfileType(profileType);
            return "Tipo de Perfil atualizado";
        }
        return "Não foi possível concluir a operação";
    }

    public String updateUserFullName(@NotNull User loggedUser, String newFullName) {
        loggedUser.setFullName(newFullName);
        return "Nome atualizado.";
    }

    public String updateUserEmail(@NotNull User loggedUser, String newEmail) {
        loggedUser.setEmail(newEmail);
        return "email atualizado.";
    }

    public String updateUserCargo(@NotNull User loggedUser, String newCargo) {
        loggedUser.setCargo(newCargo);
        return "Cargo atualizado.";
    }

    public String updateUserPassword(@NotNull User loggedUser, @NotNull User targetUser, String newPassword) {
            if (loggedUser == targetUser) {
                targetUser.setPassword(newPassword);
                return "Password atualizado.";
            }
            return "Não foi possível concluir a operação";
    }

    public String updateUserProfileName(@NotNull User loggedUser, String newProfileName) {
            loggedUser.setProfileName(newProfileName);
                return "Nome do Perfil atualizado.";
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