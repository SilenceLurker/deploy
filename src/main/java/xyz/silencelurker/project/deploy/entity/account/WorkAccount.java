package xyz.silencelurker.project.deploy.entity.account;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Silence_Lurker
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class WorkAccount extends BaseAccountInfo {
    private String role = "work";
    private String sex;
}
