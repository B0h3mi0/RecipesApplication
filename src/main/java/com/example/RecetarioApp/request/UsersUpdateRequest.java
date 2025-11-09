package com.example.RecetarioApp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersUpdateRequest {


    private String username;

    private String password;

    private boolean active;
}


