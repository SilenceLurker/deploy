package xyz.silencelurker.project.deploy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import xyz.silencelurker.project.deploy.entity.account.AdminAccount;
import xyz.silencelurker.project.deploy.entity.account.BaseAccountInfo;
import xyz.silencelurker.project.deploy.entity.account.WorkAccount;
import xyz.silencelurker.project.deploy.repository.account.AdminAccountRepository;
import xyz.silencelurker.project.deploy.repository.account.WorkAccountRepository;
import xyz.silencelurker.project.deploy.service.IAccountInfoService;

/**
 * @author Silence_Lurker
 */
@Service
public class IAccountInfoServiceImpl implements IAccountInfoService {

    @Resource
    private AdminAccountRepository adminAccountInfoRepository;
    @Resource
    private WorkAccountRepository workAccountInfoRepository;

    @Override
    public BaseAccountInfo login(String username, String password) {
        return workAccountInfoRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

    @Override
    public AdminAccount adminLogin(String username, String password) {
        return adminAccountInfoRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

    @Override
    public boolean register(BaseAccountInfo newAccount) {
        if (workAccountInfoRepository.findByUsername(newAccount.getUsername()).isEmpty()) {
            workAccountInfoRepository.save((WorkAccount) newAccount);
            return true;
        }
        return false;
    }

    @Override
    public boolean adminRegister(AdminAccount newAccount) {
        if (adminAccountInfoRepository.findByUsername(newAccount.getUsername()).isEmpty()) {
            adminAccountInfoRepository.save(newAccount);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(BaseAccountInfo newAccount) {
        if (!workAccountInfoRepository.findByUsername(newAccount.getUsername()).isEmpty()) {
            return false;
        }
        workAccountInfoRepository.save((WorkAccount) newAccount);
        return true;
    }

    @Override
    public boolean delete(String id) {
        if (!workAccountInfoRepository.findById(id).isEmpty()) {
            workAccountInfoRepository.deleteById(id);
            return true;
        }
        if (!adminAccountInfoRepository.findById(id).isEmpty()) {
            adminAccountInfoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<BaseAccountInfo> findAll() {
        var list = new ArrayList<BaseAccountInfo>();
        list.addAll(workAccountInfoRepository.findAll());
        list.addAll(adminAccountInfoRepository.findAll());
        return list;
    }

    @Override
    public WorkAccount findByWorkAccountUsername(String username) {
        return workAccountInfoRepository.findByUsername(username).orElse(null);

    }

    @Override
    public AdminAccount findByAdminAccountUsername(String username) {
        return adminAccountInfoRepository.findByUsername(username).orElse(null);
    }

}
