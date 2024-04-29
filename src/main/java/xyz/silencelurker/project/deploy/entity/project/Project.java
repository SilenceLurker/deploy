package xyz.silencelurker.project.deploy.entity.project;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import xyz.silencelurker.project.deploy.entity.account.AdminAccount;
import xyz.silencelurker.project.deploy.entity.account.BaseAccountInfo;
import xyz.silencelurker.project.deploy.entity.account.WorkAccount;
import xyz.silencelurker.project.deploy.entity.dict.Status;

/**
 * @author Silence_Lurker
 */
@Data
@Entity
public class Project {
    @Id
    @UuidGenerator
    private String id;
    private String title;

    @OneToOne
    private BaseAccountInfo deployer;
    @OneToOne
    private WorkAccount worker;

    private double price;
    private String description;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private File upload;
    private File confirm;

    private double requireScore;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany
    private List<ProjectResponse> responses = new ArrayList<>();

    /**
     * 认证人
     */
    @OneToMany
    private List<AdminAccount> confirmer;
}
