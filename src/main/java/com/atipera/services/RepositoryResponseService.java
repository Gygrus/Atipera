package com.atipera.services;

import com.atipera.models.BranchInfo;
import com.atipera.models.RepositoryInfo;
import com.atipera.models.RequestExceptionResponse;
import com.atipera.models.UserRepositoriesResponse;
import io.github.cdimascio.dotenv.Dotenv;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepositoryResponseService {

    public ResponseEntity<?> getResponse(String acceptHeader, String user){
        // handle invalid headers
        if (acceptHeader.equals("application/xml")) return ResponseEntity
                .status(406)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                new RequestExceptionResponse(
                        "This API call needs Action: application/json header. Provided value: application/xml",
                        406));
        // I don't know what requirement is for Action header other than application/xml or application/json,
        // so I write this redundant block below to show that this case should be somehow handled as well
        if (!(acceptHeader.equals("application/json") || acceptHeader.equals("*/*"))) return ResponseEntity
                .status(406)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                new RequestExceptionResponse(
                        "This API call needs Action: application/json header. Provided value: " + acceptHeader,
                        406));

        RepositoryService service = new RepositoryService();
        // get API key
        Dotenv dotenv = Dotenv.configure().load();
        service.getClient().setOAuth2Token(dotenv.get("GITHUB_TOKEN"));

        UserRepositoriesResponse userRepositoriesResponse = new UserRepositoriesResponse();
        userRepositoriesResponse.setUser(user);
        ArrayList<RepositoryInfo> repositoriesDetails = new ArrayList<>();
        List<Repository> userRepositories = null;
        // handle incorrect user
        try {
            userRepositories = service.getRepositories(user);
        } catch (IOException e) {
            return ResponseEntity.status(404).body(new RequestExceptionResponse("Provided user does not exist", 404));
        }
        // prepare response
        try {
            assert userRepositories != null;
            for (Repository repo : userRepositories) {
                if (repo.isFork()) continue;
                ArrayList<BranchInfo> branchDetails = new ArrayList<>();
                for (RepositoryBranch branch : service.getBranches(repo)){
                    branchDetails.add(new BranchInfo(branch.getName(), branch.getCommit().getSha()));
                }
                repositoriesDetails.add(
                        new RepositoryInfo(repo.getName(), repo.getOwner().getLogin(), branchDetails)
                );
            }

            return ResponseEntity
                    .status(200)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UserRepositoriesResponse(user, repositoriesDetails));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new RequestExceptionResponse("Something went wrong", 400));
        }
    }
}
