package com.boarz.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class UserResponse {

    private long id;

    private String username;

    private String password;

    private long creationDate;

    private List<RoleResponse> roles;

}
