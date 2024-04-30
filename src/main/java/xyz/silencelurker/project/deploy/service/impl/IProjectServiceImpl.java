package xyz.silencelurker.project.deploy.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

import xyz.silencelurker.project.deploy.entity.account.AdminAccount;
import xyz.silencelurker.project.deploy.entity.account.BaseAccountInfo;
import xyz.silencelurker.project.deploy.entity.account.WorkAccount;
import xyz.silencelurker.project.deploy.entity.dict.Status;
import xyz.silencelurker.project.deploy.entity.project.Project;
import xyz.silencelurker.project.deploy.entity.project.ProjectResponse;
import xyz.silencelurker.project.deploy.repository.project.ProjectRepository;
import xyz.silencelurker.project.deploy.repository.project.ProjectResponseRepository;
import xyz.silencelurker.project.deploy.service.IProjectService;

/**
 * @author Silence_Lurker
 */
@Service
public class IProjectServiceImpl implements IProjectService {

    @Resource
    private ProjectRepository projectRepository;
    @Resource
    private ProjectResponseRepository projectResponseRepository;

    @Override
    public Project createNewProject(BaseAccountInfo creator, Project newProject) {
        if (newProject == null) {
            newProject = new Project();
            newProject.setTitle("未命名项目");
            newProject.setDescription("未填写项目描述");
            newProject.setPrice(0);
            newProject.setRequireScore(0);
            newProject.setStartTime(LocalDateTime.now());
            newProject.setEndTime(LocalDateTime.now().plusDays(7));
        }
        newProject.setDeployer(creator);
        newProject.setStatus(Status.CREATE);
        projectRepository.save(newProject);

        return newProject;
    }

    @Override
    public Project startNewProject(Project startedProject, BaseAccountInfo creator) {
        if (startedProject.getDeployer().equals(creator)) {
            startedProject.setStatus(Status.START);
            projectRepository.save(startedProject);
            return startedProject;
        }
        return null;
    }

    @Override
    public Project setWaitingForPickup(Project waitingForPickup, BaseAccountInfo creator) {
        if (waitingForPickup.getDeployer().equals(creator)) {
            waitingForPickup.setStatus(Status.WAITING_FOR_PICKUP);
            projectRepository.save(waitingForPickup);
            return waitingForPickup;
        }
        return null;
    }

    @Override
    public Project pickUpProject(Project pickedUpProject, WorkAccount picker) {
        if (pickedUpProject.getRequireScore() <= picker.getScore()) {
            pickedUpProject.setWorker(picker);
            pickedUpProject.setStatus(Status.PICKED_UP);
            projectRepository.save(pickedUpProject);
            return pickedUpProject;
        }
        return null;

    }

    @Override
    public Project processing(Project processingProject, WorkAccount processor) {
        if (processingProject.getWorker().equals(processor)) {
            processingProject.setStatus(Status.PROCESSING);
            projectRepository.save(processingProject);
            return processingProject;
        }
        return null;
    }

    @Override
    public Project processing(Project processingProject, WorkAccount processor, ProjectResponse resp) {
        if (processingProject.getWorker().equals(processor)) {
            processingProject.setStatus(Status.PROCESSING);
            var responses = processingProject.getResponses();
            responses.add(resp);
            processingProject.setResponses(responses);
            projectRepository.save(processingProject);
            return processingProject;
        }
        return null;
    }

    @Override
    public Project completedProject(Project completedProject, WorkAccount completer,
            List<AdminAccount> confirmers) {
        boolean flag = true;
        for (AdminAccount admin : confirmers) {
            if (!"admin".equals(admin.getRole())) {
                flag = false;
            }
        }
        if (completedProject.getWorker().equals(completer) && flag) {
            completedProject.setStatus(Status.COMPLETED);
            completedProject.setConfirmer(confirmers);
            projectRepository.save(completedProject);
            return completedProject;
        }
        return null;

    }

    @Override
    public Project cancelProject(Project cancelProject, BaseAccountInfo canceler) {
        if (cancelProject.getDeployer().equals(canceler)) {
            cancelProject.setStatus(Status.CANCEL);
            projectRepository.save(cancelProject);
            return cancelProject;
        }
        return null;

    }

    @Override
    public Project failedProject(Project failedProject, BaseAccountInfo failer) {
        if (failedProject.getWorker().equals(failer)) {
            failedProject.setStatus(Status.FAILED);
            projectRepository.save(failedProject);
            return failedProject;
        }
        return null;

    }

    @Override
    public List<Project> getAllProjects(BaseAccountInfo searcher) {
        return projectRepository.findAllByRequireScoreLessThanEqual(searcher.getScore());

    }

    @Override
    public ProjectResponse getProjectResponse(String responseId) {
        return projectResponseRepository.findById(responseId).orElse(null);
    }

    @Override
    public ProjectResponse getProjectResponseByResponse(String response) {
        return projectResponseRepository.findByResponse(response);
    }

    @Override
    public void saveProjectResponse(ProjectResponse response) {
        projectResponseRepository.save(response);
    }

}
