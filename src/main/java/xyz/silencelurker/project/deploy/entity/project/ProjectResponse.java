package xyz.silencelurker.project.deploy.entity.project;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * 项目状态反馈
 * 
 * @author Silence_Lurker
 */
@Data
@Entity
public class ProjectResponse {
    @Id
    @UuidGenerator
    private String id;
    private String response;
    private LocalDateTime responseTime;
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;
    @ElementCollection
    private List<String> filesLocation;
}
