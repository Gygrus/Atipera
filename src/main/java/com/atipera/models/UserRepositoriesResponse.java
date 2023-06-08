package com.atipera.models;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRepositoriesResponse {
    private String user;
    private ArrayList<RepositoryInfo> repositoriesDetails;

    public UserRepositoriesResponse(String user) {
        this.user = user;
    }
}
