package repository;

import enums.ProfileType;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public void saveUser(User user) throws IOException {
        File file = new File("user.csv");

        boolean fileExists = file.exists();

        FileWriter writer = new FileWriter(file,true);

        if(!fileExists){
            writer.write("full_name;cpf;email;cargo;login;password;profile_name;profile_type \n");
        }

        writer.write(user.getFullName()+ ";"+
                        user.getCpf()+ ";"+
                        user.getEmail()+ ";"+
                        user.getCargo()+ ";"+
                        user.getLogin()+ ";"+
                        user.getPassword()+ ";"+
                        user.getProfileName()+ ";"+
                        user.getProfileType()+ "\n"
                );
        writer.close();
    }

    public List<User> loadUsers() throws IOException{

        File file = new File("user.csv");

        List<User> users = new ArrayList<>();

        if (file.exists()){

        BufferedReader reader = new BufferedReader(new FileReader("user.csv"));

        String line;

        boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                firstLine = false;
                continue;
                }
            String[] data = line.split(";");

            User user = new User(
                    data[0],
                    data[1],
                    data[2],
                    data[3],
                    data[4],
                    data[5],
                    data[6],
                    ProfileType.valueOf(data[7]));

                users.add(user);
            }
            reader.close();
        }
        if(users.isEmpty()){
                List<User> defaultUser = new ArrayList<>();

                User admin = new User("admin","00000000000","admin.email@email.com","admin","admin","12345678!@","admin",ProfileType.ADMIN);

                defaultUser.add(admin);

                saveUser(admin);

                return defaultUser;

            }
        return users;
    }
}
