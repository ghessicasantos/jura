package model;

public class TeamMember {

    private User user;
    private boolean active;
    private Team team;

    public TeamMember(User user,Team team, boolean active) {

        this.user= user;
        this.team = team;
        this.active = true;
    }

    public User getUser() {
        return user;
    }

    public Team getTeam(){
        return  team;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

}
