package model;

import enums.ProfileType;

public class User {

    private String fullName;
    private String cpf;
    private String email;
    private String cargo;
    private String login;
    private String password;
    private String profileName;
    private ProfileType profileType;



    public User (String fullName,
                  String cpf,
                  String email,
                  String cargo,
                  String login,
                  String password,
                  String profileName,
                  ProfileType profileType
                  ){
        this.fullName = fullName;
        this.cpf = cpf;
        this.email = email;
        this.cargo = cargo;
        this.login = login;
        this.password = password;
        this.profileName = profileName;
        this.profileType = profileType;

    }

    public String getFullName() {
        return fullName;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getCargo() {
        return cargo;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getProfileName() {
        return profileName;
    }

    public ProfileType getProfileType() {
        return profileType;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setProfileType(ProfileType profileType) {
        this.profileType = profileType;
    }

    public boolean canEditUser(){
        if(profileType == profileType.manager ){
        return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Users{" +
                "fullName='" + fullName + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", cargo='" + cargo + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", profileName='" + profileName + '\'' +
                ", profileType=" + profileType +
                '}';
    }
}
