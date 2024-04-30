package xyz.silencelurker.project.deploy.controller;

import static xyz.silencelurker.project.deploy.service.tool.TokenUtil.decodeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import xyz.silencelurker.project.deploy.entity.account.BaseAccountInfo;
import xyz.silencelurker.project.deploy.entity.account.WorkAccount;
import xyz.silencelurker.project.deploy.entity.project.Project;
import xyz.silencelurker.project.deploy.entity.project.ProjectResponse;
import xyz.silencelurker.project.deploy.service.IAccountInfoService;
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
    @Resource
    private IAccountInfoService accountInfoService;

    @GetMapping("")
    public ResponseEntity<?> helloWorld() {
        return ResponseEntity.ok("Hello World!");
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewProject(@CookieValue String token, Project newProject) {
        var accountInfo = decodeToken(token);
        BaseAccountInfo cr = accountInfoService.findByAdminAccountUsername(accountInfo.get("username"));

        if (cr == null) {
            cr = accountInfoService.findByWorkAccountUsername(accountInfo.get("username"));
        }
        if (cr == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(null);
        }
        if (newProject == null) {
            newProject = new Project();
        }

        return ResponseEntity.ok(projectService.createNewProject(cr, newProject));
    }

    @PostMapping("/start")
    public ResponseEntity<?> startNewProject(@CookieValue String token, Project startedProject) {

        BaseAccountInfo cr = accountInfoService.findByAdminAccountUsername(decodeToken(token).get("username"));
        if (cr == null) {
            cr = accountInfoService.findByWorkAccountUsername(decodeToken(token).get("username"));
        }
        if (cr == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(null);
        }
        return ResponseEntity.ok(projectService.startNewProject(startedProject, cr));
    }

    @PostMapping("/setWaitingForPickup")
    public ResponseEntity<?> waitingForPickup(@CookieValue String token, Project waitingForPickup) {
        BaseAccountInfo cr = accountInfoService.findByAdminAccountUsername(decodeToken(token).get("username"));
        if (cr == null) {
            cr = accountInfoService.findByWorkAccountUsername(decodeToken(token).get("username"));
        }
        if (cr == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(null);
        }
        return ResponseEntity.ok(projectService.setWaitingForPickup(waitingForPickup, cr));
    }

    @PostMapping("/pickUp")
    public ResponseEntity<?> pickUpProject(@CookieValue String token, Project pickedUpProject) {

        WorkAccount cr = accountInfoService.findByWorkAccountUsername(decodeToken(token).get("username"));

        if (cr == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(null);
        }
        return ResponseEntity.ok(projectService.pickUpProject(pickedUpProject, cr));
    }

    @PostMapping("/processing")
    public ResponseEntity<?> processing(@CookieValue String token, Project processingProject) {
        WorkAccount worker = accountInfoService.findByWorkAccountUsername(decodeToken(token).get("username"));

        return ResponseEntity.ok(projectService.processing(processingProject, worker));
    }

    @PostMapping("/processing/response")
    public ResponseEntity<?> processing(@CookieValue String token, Project processingProject, String responseId) {
        WorkAccount worker = accountInfoService.findByWorkAccountUsername(decodeToken(token).get("username"));
        var pr = projectService.getProjectResponse(responseId);

        projectService.processing(processingProject, worker, projectService.getProjectResponse(responseId));

        return ResponseEntity.ok(projectService.processing(processingProject, worker, pr));
    }

    /**
     * 创建一个反馈并将id作为结果返回
     * 
     * @param token
     * @param projectId
     * @param response
     * @return
     */
    @PostMapping("/response/create")
    public ResponseEntity<?> createResponse(@CookieValue String token, @RequestParam String projectId,
            @RequestBody ProjectResponse response, MultipartFile file) {

        WorkAccount account = accountInfoService.findByWorkAccountUsername(decodeToken(token).get("username"));
        if (account == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(null);
        }
        if (file != null) {
            response.getFilesLocation().add(file.getOriginalFilename());
        }

        var saveFile = new File(System.getProperty("xyz.silencelurker.response.savedir"),
                file.getOriginalFilename() + LocalDateTime.now());

        try (FileOutputStream fos = new FileOutputStream(saveFile)) {
            saveFile.createNewFile();
            response.getFilesLocation().add(saveFile.getName());
            var fis = file.getInputStream();

            while (fis.available() > 0) {
                fos.write(fis.read());
            }

            projectService.saveProjectResponse(response);
            response = projectService.getProjectResponseByResponse(response.getResponse());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(null);
        }

        return ResponseEntity.ok().body(response.getId());
    }
}
