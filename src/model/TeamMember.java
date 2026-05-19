package model;

public class TeamMember {

    private User user;
    private boolean active;
    private Team team;

    public TeamMember(User user,Team team, boolean active) {

        this.user= user;
        this.team = team;
        this.active = active;
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

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

}
