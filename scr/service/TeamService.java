package service;

import model.Team;
import model.TeamMember;
import model.User;

import java.time.LocalDate;

public class TeamService {

    public Team createTeam(String teamName, String description, User teamOwner, LocalDate createdAt){
        Team newTeam = new Team(teamName,description,teamOwner,createdAt);
        return newTeam;
    }

    public String addMember(User loggedUser,Team team,User newMember){
        if(!loggedUser.canEditUser()){
            return "Sem permissão";
        }
        if(team.getMembers().contains(newMember)){
            return "usuário já cadastrado.";
        }
        TeamMember teamMember = new TeamMember(newMember);
        team.getMembers().add(teamMember);
        return "Novo membro adicionado.";
    }

    public String removeMember(User loggedUser,Team team,User oldMember){

        if(!loggedUser.canEditUser()){
            return "Sem permissão";
        }
        if(team.getMembers().contains(oldMember)){
            TeamMember teamMember = new TeamMember(oldMember);
            teamMember.deactivate();
        }
        return "Membro removido -> "+ oldMember.getFullName();
    }
}
