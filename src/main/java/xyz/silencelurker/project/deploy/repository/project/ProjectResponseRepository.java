package xyz.silencelurker.project.deploy.repository.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.silencelurker.project.deploy.entity.project.ProjectResponse;

/**
 * @author Silence_Lurker
 */
@Repository
public interface ProjectResponseRepository extends JpaRepository<ProjectResponse, String> {

}
