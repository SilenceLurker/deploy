package xyz.silencelurker.project.deploy.service;

import java.util.List;

import xyz.silencelurker.project.deploy.entity.account.AdminAccount;
import xyz.silencelurker.project.deploy.entity.account.BaseAccountInfo;
import xyz.silencelurker.project.deploy.entity.account.WorkAccount;

/**
 * @author Silence_Lurker
 */
public interface IAccountInfoService {

    /**
     * 登录
     * 
     * @param username
     * @param password
     * @return
     */
    BaseAccountInfo login(String username, String password);

    /**
     * 管理员登录
     * 
     * @param username
     * @param password
     * @return
     */
    AdminAccount adminLogin(String username, String password);

    /**
     * 注册
     * 
     * @param newAccount
     * @return
     */
    boolean register(BaseAccountInfo newAccount);

    /**
     * 添加管理员
     * 
     * @param newAccount
     * @return
     */
    boolean adminRegister(AdminAccount newAccount);

    /**
     * 更新
     * 
     * @param newAccount
     * @return
     */
    boolean update(BaseAccountInfo newAccount);

    /**
     * 删除
     * 
     * @param id
     * @return
     */
    boolean delete(String id);

    /**
     * 获取全部
     * 
     * @return
     */
    List<BaseAccountInfo> findAll();

    /**
     * 通过用户名搜索工作账号
     * 
     * @param username
     * @return
     */
    WorkAccount findByWorkAccountUsername(String username);

    /**
     * 通过用户名搜索管理员
     * 
     * @param username
     * @return
     */
    AdminAccount findByAdminAccountUsername(String username);

}
