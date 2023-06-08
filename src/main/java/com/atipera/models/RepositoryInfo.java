package com.atipera.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryInfo {
    private String repositoryName;
    private String ownerLogin;
    private ArrayList<BranchInfo> branchInfos;
}
