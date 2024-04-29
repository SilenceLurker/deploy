package xyz.silencelurker.project.deploy.repository.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import xyz.silencelurker.project.deploy.entity.account.BaseAccountInfo;

/**
 * @author Silence_Lurker
 */
@NoRepositoryBean
public interface BaseAccountRepository<T extends BaseAccountInfo> extends JpaRepository<T, String> {

    /**
     * 通过用户名查找
     * 
     * @param username
     * @return
     */
    Optional<T> findByUsername(String username);

    /**
     * 通过用户名和密码查找
     * 
     * @param username
     * @param password
     * @return
     */
    Optional<T> findByUsernameAndPassword(String username, String password);
}
