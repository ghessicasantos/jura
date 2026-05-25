package service;

import model.Team;
import model.TeamMember;
import model.User;
import repository.TeamMemberRepository;
import repository.TeamRepository;
import repository.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamMemberService {

    private static final String DENIED_PERMISSION = "Sem permissao.";

    private TeamMemberRepository teamMemberRepository;
    private TeamService teamService;

    public TeamMemberService(
            UserRepository userRepository,
            TeamRepository teamRepository
    ) {
        this.teamMemberRepository = new TeamMemberRepository(userRepository);
        this.teamMemberRepository.setTeamService(teamRepository);
    }

    public List<TeamMember> listActiveMembersByTeam(Team team) {
        List<TeamMember> activeMembers = new ArrayList<>();

        for (TeamMember member : team.getMembers()) {
            if (member.isActive()) {
                activeMembers.add(member);
            }
        }

        return activeMembers;
    }

    public String addMember(User loggedUser, Team team, User newMember) throws SQLException {
        if (team == null) {
            return "Time nao encontrado.";
        }
        if (newMember == null) {
            return "Usuario nao encontrado.";
        }
        if (!team.canEditTeamLevel2(loggedUser)) {
            return DENIED_PERMISSION;
        }

        TeamMember teamMember = findTeamMember(team, newMember);

        if (teamMember != null && teamMember.isActive()) {
            return "Usuario ja cadastrado.";
        }

        if (teamMember != null) {
            teamMemberRepository.saveTeamMemberHistory(teamMember);
            teamMember.activate();
            teamMemberRepository.updateTeamMemberStatus(teamMember);
            return "Membro reativado no time.";
        }

        TeamMember newTeamMember = new TeamMember(newMember, team, true);
        team.addMember(newTeamMember);
        teamMemberRepository.saveTeamMember(newTeamMember);

        return "Novo membro adicionado.";
    }

    public String removeMember(User loggedUser, Team team, User oldMember) throws SQLException {
        if (team == null) {
            return "Time nao encontrado.";
        }
        if (oldMember == null) {
            return "Usuario nao encontrado.";
        }
        if (!team.canEditTeamLevel2(loggedUser)) {
            return DENIED_PERMISSION;
        }

        TeamMember teamMember = findTeamMember(team, oldMember);

        if (teamMember == null) {
            return "Membro nao encontrado no time.";
        }
        if (!teamMember.isActive()) {
            return "Membro ja esta inativo no time.";
        }

        teamMemberRepository.saveTeamMemberHistory(teamMember);
        teamMember.deactivate();
        teamMemberRepository.updateTeamMemberStatus(teamMember);

        return "Membro removido do time -> " + oldMember.getFullName();
    }

    public Team findTeamByName(String teamName) {
        return teamService.findTeamByName(teamName);
    }

    public List<Team> getTeams() {
        return teamService.getTeams();
    }

    private TeamMember findTeamMember(Team team, User user) {
        for (TeamMember member : team.getMembers()) {
            if (member.getUser().getEmail().equalsIgnoreCase(user.getEmail())) {
                return member;
            }
        }
        return null;
    }
}
