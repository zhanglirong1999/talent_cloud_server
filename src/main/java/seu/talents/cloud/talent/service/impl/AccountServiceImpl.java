package seu.talents.cloud.talent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.talents.cloud.talent.common.CONST;
import seu.talents.cloud.talent.exception.BizException;
import seu.talents.cloud.talent.model.dao.entity.Account;
import seu.talents.cloud.talent.model.dao.mapper.AccountMapper;
import seu.talents.cloud.talent.model.dto.post.AdminDTO;
import seu.talents.cloud.talent.model.dto.post.Register;
import seu.talents.cloud.talent.service.AccountService;
import seu.talents.cloud.talent.util.ConstantUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void registerUser(Register register,String accountId) {
        Account account = new Account();
        account.setAccountId(accountId);
        if(accountMapper.selectByPrimaryKey(account)==null){
            throw new BizException(ConstantUtil.BizExceptionCause.NOT_USER);
        }
        account.setName(register.getName());
        account.setPhone(register.getPhone());
        account.setCollage(register.getCollege());
        account.setGradYear(register.getGradYear());
        account.setGradDegree(register.getGradDegree());
        account.setCompany(register.getCompany());
        account.setJob(register.getJob());
        account.setCanRecom(register.getCanRecom());
        account.setAvatar(CONST.avatar);
        accountMapper.updateByPrimaryKeySelective(account);
    }

    @Override
    public Object getUserInfo(String accountId) {
        Account account = accountMapper.selectByPrimaryKey(accountId);
        if(account==null)
        {
            throw new BizException(ConstantUtil.BizExceptionCause.NOT_USER);
        }
        String avatar =null;
        if(account.getAvatar()==null){
            avatar = CONST.avatar;
        }else {
            avatar = account.getAvatar();
        }
        Map<String,Object> map = new HashMap<>();

        map.put("name",account.getName());
        map.put("avatar",avatar);
        map.put("gradYear",account.getGradYear());
        map.put("company",account.getCompany());
        map.put("job",account.getJob());
        map.put("college",account.getCollage());
        return map;
    }

    @Override
    public String adminLogin(AdminDTO adminDTO) {
        Account account = accountMapper.getAdmin(adminDTO.getUsername(),adminDTO.getPassword());
        if(account==null){
            throw new BizException(ConstantUtil.BizExceptionCause.ERROR_ADMIN);
        }
        String accountId = account.getAccountId();
        return accountId;
    }


}
