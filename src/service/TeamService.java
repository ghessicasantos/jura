package service;

import enums.ProfileType;
import model.Team;
import model.TeamMember;
import model.User;
import repository.TeamMemberRepository;
import repository.TeamRepository;
import repository.UserRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TeamService {
    private UserRepository userRepository;

    private TeamRepository teamRepository;

    private TeamMemberRepository teamMemberRepository;

    public TeamService() throws SQLException {
        this.userRepository = new UserRepository();
        this.teamRepository = new TeamRepository(userRepository);
        this.teams = teamRepository.loadTeams();
        this.teamMemberRepository = new TeamMemberRepository(userRepository);
        this.teamMemberRepository.setTeamService(teamRepository);

        for (TeamMember member : teamMemberRepository.loadTeamMembers()) {
            Team team = findTeamByName(member.getTeam().getTeamName());

            if (team != null) {
                TeamMember teamMember = new TeamMember(member.getUser(), team, member.isActive());
                team.addMember(teamMember);
            }
        }
    }

    private List<Team> teams;

    public Team createTeam(Team team) throws SQLException {
        if (team.getTeamName() == null || team.getTeamName().isBlank()) {
            throw new IllegalArgumentException("Informe o nome do time.");
        }
        if (team.getDescription() == null || team.getDescription().isBlank()) {
            throw new IllegalArgumentException("Informe a descricao do time.");
        }
        if (team.getTeamOwner() == null) {
            throw new IllegalArgumentException("Responsavel pelo time nao encontrado.");
        }
        if (findTeamByName(team.getTeamName()) != null) {
            throw new IllegalArgumentException("Ja existe um time com esse nome.");
        }

        teamRepository.saveTeam(team);
        teams.add(team);

        return team;
    }

    String deniedPermissionString = "Sem permissão";

    public String addMember(User loggedUser,Team team,User newMember) throws SQLException {
        if(!team.canEditTeamLevel2(loggedUser)){
            return deniedPermissionString
            ;
        }
        if(findTeamMember(team, newMember) != null){
            return "usuário já cadastrado.";
        }
        TeamMember teamMember = new TeamMember(newMember, team,true);
        team.getMembers().add(teamMember);
        teamMemberRepository.saveTeamMember(teamMember);
        return "Novo membro adicionado.";
    }

    public String removeMember(User loggedUser,Team team,User oldMember) throws SQLException {

        if(!team.canEditTeamLevel2(loggedUser)){
            return deniedPermissionString
            ;
        }
        TeamMember teamMember = findTeamMember(team, oldMember);
        if(teamMember != null){
            teamMemberRepository.saveTeamMemberHistory(teamMember);
            teamMember.deactivate();
            teamMemberRepository.updateTeamMemberStatus(teamMember);
        }
        return "Membro removido do time -> "+ oldMember.getFullName();
    }

    public String changeTeamOwner(User loggedUser,Team team,User newTeamOwner) throws SQLException {

        if(!team.canEditTeamLevel1(loggedUser)){
            return deniedPermissionString
            ;
        }
        if(newTeamOwner.getProfileType() != ProfileType.ADMIN && newTeamOwner.getProfileType() != ProfileType.MANAGER){
            return "Usuário não pode ser Lider de time. Verifique o perfil do usário.";
        }
        String oldTeamName = team.getTeamName();
        teamRepository.saveTeamHistory(team);
        team.setTeamOwner(newTeamOwner);
        teamRepository.updateTeam(team, oldTeamName);
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

    public int countActiveMembers(Team team) {
        int total = 0;

        for (TeamMember member : team.getMembers()) {
            if (member.isActive()) {
                total++;
            }
        }

        return total;
    }

    public String removeTeam(Team team) throws SQLException {
        if (team == null) {
            return "Time nao encontrado.";
        }

        teamRepository.saveTeamHistory(team);
        teamRepository.deleteTeam(team);
        teams.remove(team);

        return "Time removido.";
    }

    private TeamMember findTeamMember(Team team, User user) {
        for (TeamMember member : team.getMembers()) {
            if (member.getUser().equals(user)) {
                return member;
            }
        }
        return null;
    }

    public String updateTeamName(Team team, String newName) throws SQLException {
        String oldTeamName = team.getTeamName();
        teamRepository.saveTeamHistory(team);
        team.setTeamName(newName);
        teamRepository.updateTeam(team, oldTeamName);
        return "Nome atualizado.";
    }

    public String updateTeamDescription(Team team, String newDescription) throws SQLException {
        String oldTeamName = team.getTeamName();
        teamRepository.saveTeamHistory(team);
        team.setDescription(newDescription);
        teamRepository.updateTeam(team, oldTeamName);
        return "Descrição atualizada.";
    }

 }
