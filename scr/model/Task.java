package model;

import enums.StatusProjects;

import java.time.LocalDate;

public class Tasks {

    private String taskName;
    private String description;
    private LocalDate startDate;
    private LocalDate finishDate;
    private StatusProjects status;
    private User assignedUser;
    private Project projectOwner;


}
