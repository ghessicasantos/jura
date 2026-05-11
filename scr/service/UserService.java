package service;

import enums.ProfileType;
import model.User;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;


public class UserService {

    private List<User> users = new ArrayList<>();

    private void addUser(User user){
        users.add(user);
    }

    private void listUsers(){
        for(User user : users){
            System.out.println(user);
        }
    }

    private User findUserByLogin(String login){
        for(User user: users){
            if(user.getLogin().equals(login)){
                return user;
            }
        }  return null;
    }

    public void removeUser(String login){
        User userToRemove = null;

        for (User user : users){
            if(user.getLogin().equals(login)){
                userToRemove = user;
            }
        }
        users.remove(userToRemove);
    }

    public String updateUserField(@NotNull User loggedUser, @NotNull User targetUser, @NotNull String field, String newValueField) {

        if (field.equals("Tipo de Perfil")) {
            if (loggedUser.canEditUser()) {
                ProfileType profileType = ProfileType.valueOf(newValueField);
                targetUser.setProfileType(profileType);
                return "Tipo de Perfil atualizado";
            }
            return "Sem permissão";
        } else if (field.equals("Nome Completo")) {
            if (loggedUser == targetUser) {
                targetUser.setFullName(newValueField);
                return "Nome atualizado.";
            }
        } else if (field.equals("Email")) {
            if (loggedUser == targetUser) {
                targetUser.setEmail(newValueField);
                return "email atualizado.";
            }
        } else if (field.equals("Cargo")) {
            targetUser.setCargo(newValueField);
            return "Cargo atualizado.";
        } else if (field.equals("Password")) {
            if (loggedUser == targetUser) {
                targetUser.setPassword(newValueField);
                return "Password atualizado.";
            }
        } else if (field.equals("Nome do Perfil")) {
            if (loggedUser == targetUser) {
                targetUser.setProfileName(newValueField);
                return "Nome do Perfil atualizado.";
            }
        }
        return "Erro ao executar a operação";
    }

    public User findUserByEmail(String email){
        for(User user : users){
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    public String login(String email, String password){
        User user = findUserByEmail(email);

        if(user == null){
            return "Usuário nao encontrado";
        }
        if (!user.getPassword().equals(password)){
            return "Senha inválida";
        }
        return "Login realizado";
    }
}
