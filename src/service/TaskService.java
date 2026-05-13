package service;

import enums.ProfileType;
import enums.StatusTask;
import model.*;

import java.time.LocalDate;

public class TaskService {

    Task createTask(String taskName, String description, LocalDate startDate, LocalDate finishDate, StatusTask status, User loggedUser, Project project){

        Task newTask = new Task(taskName,description,startDate,finishDate,status,loggedUser,project);
    return newTask;
    }

    public String addAssignedUser(User loggedUser,Task task,User newAssignedUser){

         if (task.getAssignedUser().equals(newAssignedUser)){
         return "Usuário já está definido.";
        }
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
}

