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

        if (field.equals("Perfil")) {
            if (loggedUser.canEditUser()) {
                ProfileType profileType  = ProfileType.valueOf(newValueField);
            targetUser.setProfileType(profileType);
                return "Cargo atualizado";
            }
            return "Sem permissão";
        }
        else if (field.equals("Nome Completo")) {
            targetUser.setFullName(newValueField);
        }
        else if(field.equals("Email")){
            targetUser.setEmail(newValueField);
        }
         else if(field.equals("Cargo")){
        targetUser.setCargo(newValueField);
         }


        else {

        }
    }
    //Construir as outras formas de uptar dados do usuario.
}
