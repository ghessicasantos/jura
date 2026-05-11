package model;

public class TeamMember {

    private User user;
    private boolean active;

    public TeamMember(User user) {

        this.user = user;
        this.active = true;
    }

    public User getUser() {
        return user;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

}
