package enums;

public enum CsvFile {
    USER("users.csv"),
    TASK("tasks.csv"),
    PROJECT("projects.csv"),
    TEAM_MEMBER("team_members.csv"),
    TEAM("teams.csv");


    private final String fileName;

    CsvFile(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
