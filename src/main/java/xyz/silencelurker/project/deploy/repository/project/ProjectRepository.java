package xyz.silencelurker.project.deploy.repository.project;

import xyz.silencelurker.project.deploy.entity.project.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Silence_Lurker
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    /**
     * 根据评分查询项目
     * 
     * @param requireScore
     * @return
     */
    List<Project> findAllByRequireScoreLessThanEqual(double requireScore);
}
