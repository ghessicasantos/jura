package service;

import enums.ProfileType;
import model.Team;
import model.TeamMember;
import model.User;
import repository.TeamMemberRepository;
import repository.TeamRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeamService {

    private TeamRepository teamRepository;

    private List<Team> teams = new ArrayList<>();

    public TeamService(UserService userService) throws IOException {
        this(userService, new TeamMemberRepository(userService));
    }

    public TeamService(UserService userService, TeamMemberRepository teamMemberRepository) throws IOException {
        this.teamRepository = new TeamRepository(userService,teamMemberRepository);
        teamMemberRepository.setTeamService(this);
        this.teams = teamRepository.loadTeams();
    }

    public Team createTeam(String teamName, String description, User teamOwner, LocalDate createdAt) throws IOException {
        if (teamOwner == null) {
            throw new IllegalArgumentException("Responsavel pelo time nao encontrado.");
        }
        if (findTeamByName(teamName) != null) {
            throw new IllegalArgumentException("Ja existe um time com esse nome.");
        }

        Team newTeam = new Team(teamName,description,teamOwner,createdAt);

        teamRepository.saveTeam(newTeam);
        teams.add(newTeam);

        return newTeam;
    }

    public String addMember(User loggedUser,Team team,User newMember){
        if(!team.canEditTeamLevel2(loggedUser)){
            return "Sem permissão";
        }
        if(findTeamMember(team, newMember) != null){
            return "usuário já cadastrado.";
        }
        TeamMember teamMember = new TeamMember(newMember, team,true);
        team.getMembers().add(teamMember);
        return "Novo membro adicionado.";
    }

    public String removeMember(User loggedUser,Team team,User oldMember){

        if(!team.canEditTeamLevel2(loggedUser)){
            return "Sem permissão";
        }
        TeamMember teamMember = findTeamMember(team, oldMember);
        if(teamMember != null){
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

    private TeamMember findTeamMember(Team team, User user) {
        for (TeamMember member : team.getMembers()) {
            if (member.getUser().equals(user)) {
                return member;
            }
        }
        return null;
    }

    public String updateTeamName(User loggedUser, String newName) {
        loggedUser.setTeamName(newName);
        return "Nome atualizado.";
    }

    public String updateTeamDescription(User loggedUser, String newDescription) {
        loggedUser.setDescription(newDescription);
        return "Descrição atualizada.";
    }

    public String updateTeamOwner(User loggedUser, String newOner) {
        loggedUser.setTeamOwner(newCargo);
        return "Responsável atualizado.";
    }
 }
