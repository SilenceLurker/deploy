package xyz.silencelurker.project.deploy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import xyz.silencelurker.project.deploy.service.IProjectService;

/**
 * @author Silence_Lurker
 */
@CrossOrigin
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Resource
    private IProjectService projectService;

    public ResponseEntity<?> helloWorld() {
        return ResponseEntity.ok("Hello World!");
    }

}
