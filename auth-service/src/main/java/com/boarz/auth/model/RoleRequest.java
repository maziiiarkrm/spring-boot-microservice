package com.boarz.auth.model;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class RoleRequest {

    @Min(0)
    private long id;
    @NotEmpty
    @NotBlank
    private String name;
    private String description;

    public RoleRequest() {
    }

    public RoleRequest(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

