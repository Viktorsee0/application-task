package com.spg.applicationTask.api.model;

import java.util.List;

public final class Project {

    private int id;
    private String name;
    private String description;
    private User creator;
    private List<User> developers;
    private List<Task> tasks;
}
