package com.spg.applicationTask.api.model;

import java.time.ZonedDateTime;

public final class Task {

    private int id;
    private String title;
    private String description;
    private ZonedDateTime createdDate;
    private ZonedDateTime dueDate;
    private String status;
    private User assignee;
}
