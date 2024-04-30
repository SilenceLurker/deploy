package xyz.silencelurker.project.deploy.service;

import java.util.List;

import jakarta.annotation.Nullable;
import xyz.silencelurker.project.deploy.entity.account.AdminAccount;
import xyz.silencelurker.project.deploy.entity.account.BaseAccountInfo;
import xyz.silencelurker.project.deploy.entity.account.WorkAccount;
import xyz.silencelurker.project.deploy.entity.project.Project;
import xyz.silencelurker.project.deploy.entity.project.ProjectResponse;

/**
 * @author Silence_Lurker
 */
public interface IProjectService {
    /**
     * 创建新项目
     * 
     * @param creator
     * @param newProject
     * @return
     */
    Project createNewProject(BaseAccountInfo creator, @Nullable Project newProject);

    /**
     * 开始新项目
     * 
     * @param startedProject
     * @param creator
     * @return
     */
    Project startNewProject(Project startedProject, BaseAccountInfo creator);

    /**
     * 设置项目为等待接取
     * 
     * @param waitingForPickup
     * @param creator
     * @return
     */
    Project setWaitingForPickup(Project waitingForPickup, BaseAccountInfo creator);

    /**
     * 接取项目
     * 
     * @param pickedUpProject
     * @param picker
     * @return
     */
    Project pickUpProject(Project pickedUpProject, WorkAccount picker);

    /**
     * 设置项目进行中
     * 
     * @param processingProject
     * @param processor
     * @return
     */
    Project processing(Project processingProject, WorkAccount processor);

    /**
     * 带反馈信息的项目状态提交
     * 
     * @param processingProject
     * @param processor
     * @param resp
     * @return
     */
    Project processing(Project processingProject, WorkAccount processor, ProjectResponse resp);

    /**
     * 设置项目已完成
     * 
     * @param completedProject
     * @param completer
     * @param confirmers
     * @return
     */
    Project completedProject(Project completedProject, WorkAccount completer, List<AdminAccount> confirmers);

    /**
     * 设置项目已取消
     * 
     * @param cancelProject
     * @param canceler
     * @return
     */
    Project cancelProject(Project cancelProject, BaseAccountInfo canceler);

    /**
     * 设置项目失败
     * 
     * @param failedProject
     * @param failer
     * @return
     */
    Project failedProject(Project failedProject, BaseAccountInfo failer);

    /**
     * 获取所有项目
     * 
     * @param searcher
     * @return
     */
    List<Project> getAllProjects(BaseAccountInfo searcher);

    /**
     * 通过id查询项目反馈
     * 
     * @param responseId
     * @return
     */
    ProjectResponse getProjectResponse(String responseId);

    /**
     * 通过反馈内容查询反馈
     * 
     * @param response
     * @return
     */
    ProjectResponse getProjectResponseByResponse(String response);

    /**
     * 保存反馈信息
     * 
     * @param response
     */
    void saveProjectResponse(ProjectResponse response);
}
