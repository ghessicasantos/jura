package service;

import enums.ProfileType;
import model.Team;
import model.TeamMember;
import model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeamService {

    private List<Team> teams = new ArrayList<>();

    public Team createTeam(String teamName, String description, User teamOwner, LocalDate createdAt){
        Team newTeam = new Team(teamName,description,teamOwner,createdAt);
        return newTeam;
    }

    public String addMember(User loggedUser,Team team,User newMember){
        if(!team.canEditTeamLevel2(loggedUser)){
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

        if(!team.canEditTeamLevel2(loggedUser)){
            return "Sem permissão";
        }
        if(team.getMembers().contains(oldMember)){
            TeamMember teamMember = new TeamMember(oldMember);
            teamMember.deactivate();
        }
        return "Membro removido do time -> "+ oldMember.getFullName();
    }

    public String changeTeamOwner(User loggedUser,Team team,User newTeamOwner){

        if(!team.canEditTeamLevel1(loggedUser)){
            return "Sem permissão";
        }
        if(newTeamOwner.getProfileType() != ProfileType.ADMIN && newTeamOwner.getProfileType() != ProfileType.MANAGER){
            return "Usuário não pode ser Lider de time. Verifique o perfil do usário.";
        }
        team.setTeamOwner(newTeamOwner);
        return "Novo responsável definido -> " + newTeamOwner.getFullName();
    }

    public Team findTeamByName(String teamName){

        for(Team team : teams){

            if(team.getTeamName().equalsIgnoreCase(teamName)){
                return team;
            }
        }
        return null;
    }

    public List<Team> getTeams(){
        return teams;
    }
 }
