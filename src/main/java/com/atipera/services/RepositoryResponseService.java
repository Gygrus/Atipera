package com.atipera.services;

import com.atipera.models.BranchInfo;
import com.atipera.models.RepositoryInfo;
import com.atipera.models.UserRepositoriesResponse;
import io.github.cdimascio.dotenv.Dotenv;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class RepositoryResponseService {

    public ResponseEntity getResponse(String user){
        RepositoryService service = new RepositoryService();
        Dotenv dotenv = Dotenv.configure().load();
        service.getClient().setOAuth2Token(dotenv.get("GITHUB_TOKEN"));
        UserRepositoriesResponse userRepositoriesResponse = new UserRepositoriesResponse();
        userRepositoriesResponse.setUser(user);
        ArrayList<RepositoryInfo> repositoriesDetails = new ArrayList<>();
        try {
            for (Repository repo : service.getRepositories(user)) {
                if (repo.isFork()) continue;
                ArrayList<BranchInfo> branchDetails = new ArrayList<>();
                for (RepositoryBranch branch : service.getBranches(repo)){
                    branchDetails.add(new BranchInfo(branch.getName(), branch.getCommit().getSha()));
                }
                repositoriesDetails.add(
                        new RepositoryInfo(repo.getName(), repo.getOwner().getLogin(), branchDetails)
                );
            }

            return ResponseEntity.status(200).body(new UserRepositoriesResponse(user, repositoriesDetails));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("Something went wrong");
        }
    }
}
