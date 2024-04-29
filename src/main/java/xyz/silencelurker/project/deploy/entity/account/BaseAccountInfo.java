package xyz.silencelurker.project.deploy.entity.account;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * @author Silence_Lurker
 */
@Data
@Entity
public abstract class BaseAccountInfo {
    @Id
    @UuidGenerator
    String id;
    String username;
    String password;
    String phone;
    String email;

    /**
     * 权重分\信任度
     */
    double score;
}
