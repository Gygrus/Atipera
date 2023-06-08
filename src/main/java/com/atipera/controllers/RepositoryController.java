package com.atipera.controllers;

import com.atipera.models.UserRepositoriesResponse;
import com.atipera.services.RepositoryResponseService;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
//@CrossOrigin(origins = "http://localhost:8081/")
@RequestMapping("api/")
public class RepositoryController {
    private final RepositoryResponseService repositoryResponseService;

    RepositoryController(RepositoryResponseService repositoryResponseService) {
        this.repositoryResponseService = repositoryResponseService;
    }

    @GetMapping(path = "userrepositories/{user}")
    @ResponseBody
    ResponseEntity<?> userRepositories(@RequestHeader(HttpHeaders.ACCEPT) String acceptHeader,
                                       @PathVariable String user) {
        return this.repositoryResponseService.getResponse(acceptHeader, user);
    }
}
