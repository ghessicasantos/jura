import enums.ProfileType;
import model.User;
import service.UserService;

public class Main {

    public static void main(String[] args) {

        User admin =
                new User("Pedro", "13970108780","pedro_cs13@hotmail.com","Analista de Dados","Coffees1397","130916Pg","Coffes",ProfileType.admin);

        User collaborator =
                new User("Ghessica", "10871930790","ghessicas@gmail.com","Analytics Engineer","GhessCafe","130916Gp","Ghess", ProfileType.colaborator);

        UserService userService =
                new UserService();

     String result =  userService.updateUserField(
                admin,
                collaborator,
                "Nome",
                "João Alencar"
        );


        System.out.println( result +" --> "+
                collaborator.getProfileType()
        );
    }
}
